//server settings
var express = require('express')
var app = express();
var path = require('path');

var __projectRoot = __dirname + '/../';

app.use(express.static(__projectRoot));

app.get('/', function (req, res) {
    res.sendFile(path.join(__projectRoot + '/index.html'));
});

//database variables
var influx = require('influx')
var username = 'root'
var password = 'root'
var database = 'clarc_s'

var dbClient = influx({host: 'localhost', username: username, password: password, database: database})

//some constants
const PORT = 8000;
const PRESSURE_INTERVAL = 500;
const TEMPERATURE_INTERVAL = 500;

//server starts listening
var server = app.listen(PORT, function () {

    var host = server.address().address
    var port = server.address().port

    console.log('Express app listening at http://%s:%s', host, port)
})

//socket connection
var io = require('socket.io').listen(server);

io.sockets.on('connection', function(socket) {
    console.log('[socket.io] Ein neuer Client (Browser) hat sich verbunden.\n');
    socket.emit('message', "hello Client, How are you?")

    socket.on('message', function(message) {
        console.log(message);
    });

    var pressureIntervalID = setInterval(function() {
        socket.emit('pressure', JSON.stringify(getPressure()));
    }, PRESSURE_INTERVAL);

    var temperatureIntervalID = setInterval(function() {
        socket.emit('temperature', JSON.stringify(getTemperature()));
    }, TEMPERATURE_INTERVAL);
    
});


var lastPressure, lastTemperature;
function getPressure() {
    var query = 'select * from pressure group by * order by desc limit 1';
    dbClient.query(query, function (err, result) {
        result = result[0][0];
        var data =
        {
            cid: result.ChairUUID,
            time: result.time,
            p: {}
        }
        for(var i = 0; i < 10; i++) {
            data.p[i] = result[i];
        }

        lastPressure = data;

    })
    return lastPressure;
}

function getTemperature() {
    var query = 'select * from temperature group by * order by desc limit 1';
    dbClient.query(query, function (err, result) {
        result = result[0][0];
        var data =
        {
            cid: result.ChairUUID,
            time: result.time,
            t: {}
        }
        for(var i = 0; i < 1; i++) {
            data.t[i] = result.temp;
        }

        lastTemperature = data;

    })
    return lastTemperature;
}


/* generate testdata, if no db is available */
/*
function generatePressure() {
    var pressure = { p: {} };
    for(var i = 0; i < 10; i++) {
        pressure.p[i] = Math.random();
    }
    return pressure;
}

function generateTemperature() {
    temperature = { t: {}};
    temperature.t[0] = Math.floor(Math.random()*(20-5+1)+5);
    return temperature;
}
*/