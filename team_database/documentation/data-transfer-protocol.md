# Data Transfer Protocol: Server <-> Web Client

## process:

client: connect to server
server: on connect start sending
server: send data every x seconds (format of message see "data format")

## data format

message type: 'dataPressure';
message (stringified):
{ 
	cid: id,
	time: timestamp,
	p: {
		0: float (int?),
		1: float,
		2: float,
		3: float,
		4: float,
		5: float,
		6: float,
		7: float,
		8: float,
		9: float
	}
}

message type: 'dataTemperature';
message (stringified):
{ 
	cid: id,
	time: timestamp,
	t: {
		0: float °C
	}	
}



