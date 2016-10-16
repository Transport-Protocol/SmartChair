var socket = io('http://127.0.0.1:8000');

socket.on('connect', function() {
    console.log('connected to server');
    document.querySelector('#info').innerHTML = 'connection established!';
    socket.emit('data');
});

socket.on('data', function(message) {
    updateData(JSON.parse(message));
});

function updateData(data) {
    for(var i in data) {
    	var dom = document.querySelector('#'+i) //&& document.querySelector('#'+i).innerHTML = 'x'//data[i];
    	if(dom) {
    		dom.innerHTML = data[i];
    	}
    }
    updateTemperatureData(count, generateTestData());
}

function disconnect() {
    socket.emit('stop');
}


//chartstuff

google.charts.load('current', {'packages':['line']});
google.charts.setOnLoadCallback(initializeDataChart);

var MAX_DATA_POINTS = 10;
var data; 
var chart;
var options = {
	chart: {
	  title: 'Room Temperature',
	},
	gridlines: {
		count: MAX_DATA_POINTS,
		
	},
	width: 900,
	height: 500
};

function initializeDataChart() {
	data = new google.visualization.DataTable();

	data.addColumn('number', 'Temperature');
	data.addColumn('number', 'Temperature');

	chart = new google.charts.Line(document.getElementById('chart_div'));
}

function updateTemperatureData(time, value) {
	if(!(data && chart)) return;

	if(data.getNumberOfRows() == MAX_DATA_POINTS) {
		data.removeRow(0);
	} 

	data.addRow([time, value]);

	if(data.getNumberOfRows() > 0) {
    	chart.draw(data, options);
	}
}

//test data stuff
var count = 0;
function generateTestData() {
	count++;
	//floor(rnd*(max-min+1)+min)
	var res = Math.random()*(30+5+1)-5
	console.log(!isNaN(res));
	return res;
}