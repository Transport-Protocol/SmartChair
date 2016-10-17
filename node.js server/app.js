var express = require('express')

var app = express()

app.use(express.static('www'));

var server = app.listen(8000, function () {

    var host = server.address().address
    var port = server.address().port

    console.log('Express app listening at http://%s:%s', host, port)

})


// Loading socket.io
var io = require('socket.io').listen(server);

// When a client connects, we note it in the console
io.sockets.on('connection', function (socket) {
    console.log('A client is connected!');
	socket.emit('connect');

    var dataIntervalId;
    // When the server receives a “message” type signal from the client   
    socket.on('data', function () {
    	console.log('received data');
    	dataIntervalId = setInterval(function() {
    		socket.emit('data', JSON.stringify(generateData()));
    	}, 50);
	});
    socket.on('stop', function() {
    		clearInterval(dataIntervalId);
    		socket.emit('stop','stop');
    }); 
});

function generateData() {
	var data = {};
	for(var i = 0; i < 10; i++) {
		data["p"+i] = Math.floor(Math.random()*(20-5+1)+5);
	}
	data.t1 = Math.floor(Math.random()*(20-5+1)+5);
	return data;
}