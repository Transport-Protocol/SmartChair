var socket = io('http://127.0.0.1:8000');

socket.on('connect', function() {
    console.log('connected to server');
    document.querySelector('#info').innerHTML = 'connection established!';
    socket.emit('data');
});

socket.on('pressure', function(message) {
    updatePressure(JSON.parse(message));
});

socket.on('temperature', function(message) {
    updateTemperature(count, JSON.parse(message));
    count++;
});

function disconnect() {
    socket.emit('stop');
}

function updatePressure(pressureJSON) {
	for(var i in pressureJSON.p) {
    	var dom = document.querySelector('#p'+i) //&& document.querySelector('#'+i).innerHTML = 'x'//data[i];
    	if(dom) {
    		dom.innerHTML = pressureJSON.p[i];
    	}
    }
}


//chartstuff

google.charts.load('current', {'packages':['line']});
google.charts.setOnLoadCallback(initializeDataChart);

var MAX_DATA_POINTS = 20;
var data; 
var chart;
var options = {
	chart: {
	  title: 'Room Temperature',
	},
	gridlines: {
		count: MAX_DATA_POINTS,
		
	},
	legend: 'none',
	width: 900,
	height: 500
};

function initializeDataChart() {
	data = new google.visualization.DataTable();

	data.addColumn('number', 'Temperature');
	data.addColumn('number', 'Temperature');

	chart = new google.charts.Line(document.getElementById('chart_div'));
}

function updateTemperature(time, temperatureJSON) {
	if(!(data && chart)) return;

	if(data.getNumberOfRows() == MAX_DATA_POINTS) {
		data.removeRow(0);
	} 
	console.log(temperatureJSON)
	data.addRow([time, temperatureJSON.t[0]]);

	if(data.getNumberOfRows() > 0) {
    	chart.draw(data, options);
	}
	console.log(data);
}

//test data stuff
var count = 0;
function generateTestData() {
	count++;
	//floor(rnd*(max-min+1)+min)
	var res = { 0: Math.random()*(30+5+1)-5 }
	return res;
}