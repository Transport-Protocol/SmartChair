//some imports of libraries
const Influx = require('influx')
const express = require('express')
const path = require('path');

const app = express()
var favicon = require('serve-favicon');

//some constants
const PORT = 8000;
const PRESSURE_INTERVAL = 500;
const TEMPERATURE_INTERVAL = 500;
//database constants
const dbUsername = 'root'
const dbPassword = 'root'
const database = 'clarc_s'

app.use(favicon(path.join(__dirname,'favicon.ico')));

//rootfolder of our project
const __projectRoot = __dirname + '/../';
app.use(express.static(__projectRoot));
//send index.html, when a client connects
app.get('/', function (req, res) {
    res.sendFile(path.join(__projectRoot + '/index.html'));
});

/**
 * Create a new Influx client. it uses the settings defined above.
 */
const dbClient = new Influx.InfluxDB({
  host: 'localhost',
  database: database,
  username: dbUsername,
  password: dbPassword
})

/**
 * start serving our app to clients
 */
var server = app.listen(PORT, function () {

    var host = server.address().address
    var port = server.address().port

    console.log('Express app listening at http://%s:%s', host, port)
})

/**
 * import socket-io
 */
var io = require('socket.io').listen(server);

/**
 * when a client connects we currently just push all data, no matter to which chair they belong or what kind of data it is
 */
io.on('connection', function(socket) {

    console.log('[socket.io] Ein neuer Client (Browser) hat sich verbunden.\n');

   socket.on('message', function(data) {
        console.log(data);
   });

    socket.on('getChairs', function(data) {
        sendChairs(socket)
    });

    socket.on('getPressure', function(whereUUID) {
        var pressureIntervalID = setInterval(function() {
            sendPressure(socket, whereUUID);
        }, PRESSURE_INTERVAL);
    })

    socket.on('getTemperature', function(whereUUID) {
        var temperatureIntervalID = setInterval(function() {
            sendTemperature(socket, whereUUID);
        }, TEMPERATURE_INTERVAL);
    })

    socket.on('getFirstXTemperatures', function(amount, whereUUID) {
        sendFirstXTemperature(socket, amount, whereUUID);
    })
});

/** 
 * querries the for the newest pressure data (ignoring to which chair it belongs) database 
 * and sends the data over a socket to the client
 */
function sendPressure(socket, whereUUID) {
    const query = 'select * from pressure where ChairUUID = \'' + whereUUID + '\' order by desc limit 1';
    dbClient.query(query).then(result => {
      //receive data and format it
      result = result[0];
      var data =
      {
          cid: result.ChairUUID,
          time: result.time,
          p: {}
      }
      for(var i = 0; i < 10; i++) {
          data.p[i] = result[i];
      }
      //and then send it
    /*
      console.log('sendPressure: data: ' + data);
      console.log('sendPressure: JSON.stringify(data): ' + JSON.stringify(data));
     */
      socket.emit('pressure', JSON.stringify(data));
      console.log('Pressure sent');
    }).catch(err => {
      console.log("sendPressure: " + err);
      res.status(500).send(err.stack)
    })
}

function sendFirstXTemperature(socket, amount, whereUUID) {
    const query = 'select * from temperature where ChairUUID = \'' + whereUUID + '\' order by desc limit ' + amount;
    dbClient.query(query).then(result => {
      //receive data and format it
      result = result[0];
      var data =
      {
          cid: result.ChairUUID,
          time: result.time,
          t: {}
      }
      for(var i = 0; i < 1; i++) {
          data.t[i] = result.temp;
      }
      //and then send it
      socket.emit('temperature', JSON.stringify(data));
    }).catch(err => {
      console.log("sendTemperature: " + err);
      res.status(500).send(err.stack)
    })
}

function sendTemperature(socket, whereUUID) {
    const query = 'select * from temperature where ChairUUID = \'' + whereUUID + '\' order by desc limit 1';
    dbClient.query(query).then(result => {
        //receive data and format it
        result = result[0];
    var data =
    {
        cid: result.ChairUUID,
        time: new Date(result.time),
        t: {}
    }
    for(var i = 0; i < 1; i++) {
        data.t[i] = result.temp;
    }
    //and then send it
    socket.emit('temperature', JSON.stringify(data));
}).catch(err => {
        console.log("sendTemperature: " + err);
    res.status(500).send(err.stack)
})
}

/*
JSON format
 */
/*
function sendChairs(socket) {
    const query = 'SHOW TAG VALUES FROM temperature WITH KEY = ChairUUID';
    dbClient.query(query).then(result => {
        //receive data and format it
        var data =
        {
            c: {}
        }
        for(var i = 0; i < result.length; i++) {
            if(result.empty) {
                i = result.length;
            } else {
                data.c[i] = result[i].ChairUUID;
            }
        }
        //and then send it
        socket.emit('chairs', JSON.stringify(data));

    }).catch(err => {
            console.log("sendChairs: " + err);
        res.status(500).send(err.stack)
    })
}
*/
function sendChairs(socket) {
    const query = 'SHOW TAG VALUES FROM pressure WITH KEY = ChairUUID';
    dbClient.query(query).then(result => {
        //receive data and format it
        var chairsString = [];
        for(var i = 0; i < result.length; i++) {
        if(result.empty) {
            i = result.length;
        } else {
            chairsString[i] = result[i].value;
        }
    }
    //and then send it
    socket.emit('chairs', chairsString);

}).catch(err => {
        console.log("sendChairs: " + err);
    res.status(500).send(err.stack)
})
}
