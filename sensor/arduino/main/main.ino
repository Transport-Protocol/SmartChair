#include <inttypes.h>

int sensorPin0 = A0;    // pressure seat 
int sensorPin1 = A1;    // pressure back rest
int sensorPin2 = A2;    // distance
int addPin0   = 8;
int addPin1   = 9;
int addPin2   = 10;

#define BUFFER_SIZE 8
#define BAUDRATE 38400

/*void setup(void)
 * Will be Called once of the Micro Controller Powers Up
 */
void setup() {
    Serial.begin(BAUDRATE); // init serail Connection

    pinMode(addPin0, OUTPUT); // Sets the Pins used for the Multiplexer address line to Output
    pinMode(addPin1, OUTPUT);
    pinMode(addPin2, OUTPUT); 
  
    digitalWrite(addPin0, LOW); //And Puts them in a defined state
    digitalWrite(addPin1, LOW);
    digitalWrite(addPin2, LOW);

    pinMode(11,OUTPUT);
    pinMode(12,INPUT);
}

/* void loop(void)
 * the Main loop, reads all sensors, and checks if it needs to send, if so, it sends 
 */

void loop() {
    readSensors();
    sendData();
}


//******** ZACWire ****************
/* uint8_t readByte(int,bool*)
 * parameters:
 *  signalPin : The Pin for the ZACWire Data Line
 *  error     : A Pointer to a variable, wich will be set to true in case of an error.
 * returns:
 *  8 bit value read ofer the ZACWire protocoll
 */
uint8_t readByte(int signalPin,bool *error){
    uint16_t byte = 0;
    bool correkt = true;

    // startsequence
    while(digitalRead(signalPin));
    while(!digitalRead(signalPin));
        
    // read 8 bits and a parity bit
    for(int i = 0; i < 9; ++i) {

        while(digitalRead(signalPin));
        delayMicroseconds(60);

        if(digitalRead(signalPin)) {
            byte |= 1 << (8 - i);
            correkt=!correkt;
        } else {
            while(!digitalRead(signalPin));
        }
    }
    
    if(!correkt) {
        *error=true;
    }

    return (uint8_t)(byte >> 1); // return just the date byte
}

/*uint8_t readWord(int,bool*)
 * intern wird das ReadByte verwendet.
 * parameters:
 *  signalPin : The Pin for the ZACWire Data Line
 *  error     : A Pointer to a variable, wich will be set to true in case of an error.
 * returns:
 *  8 bit value read ofer the ZACWire protocoll 
 */
 
uint16_t readWord(int signalPin,bool *error){
  uint8_t byte1 = readByte(signalPin,error);
  uint8_t byte2 = readByte(signalPin,error);
  return (byte1 << 8) | byte2;
}


/* float readTemperature(int,signalPin)
 * Reads the Themperature form the Sensor and Convertes it to degrees Celsius
 * Parameter:
 *  powerPin  : The Pin, wich is used to Provide the Sensor with Power
 *  signalPin : The Pin for the ZACWire Data Line
 * returns :
 *  NAN if there was na error
 *  the Temperatur in degree Celsius.
 */
float readTemperature(int powerPin,int signalPin){
  
  bool error=false;
  digitalWrite(powerPin, HIGH);  //Aktivate sensor 
  delayMicroseconds(60);  // etwas zeit für den sensor
  uint16_t word = readWord(signalPin,&error);    
  digitalWrite(powerPin, LOW);  //Deaktivate sensor
  if(error)return NAN;
  return (word / 2047.0f * 200.0f) - 50.0f; // from data sheet
}

int sensorValues[17][BUFFER_SIZE];
int hoehe[BUFFER_SIZE];
int sensorIndex=0;

/* void readSensors(void)
 * reads pressure and distance sensors connected to the arduino and stores the value in an array.
 */
void readSensors(){
  int address=0;
  
  // get pressure
  while(address<8){
    digitalWrite(addPin0, (address & 0x1)?HIGH:LOW);
    digitalWrite(addPin1, (address & 0x2)?HIGH:LOW);
    digitalWrite(addPin2, (address & 0x4)?HIGH:LOW);
    
    sensorValues[    address][sensorIndex] = analogRead(sensorPin0);
    sensorValues[8 + address][sensorIndex] = analogRead(sensorPin1);
    address+=1;
  }
  
  // get distance
  sensorValues[16][sensorIndex] = analogRead(sensorPin2);

  // index wrap around
  sensorIndex++;
  if(sensorIndex==BUFFER_SIZE){
    sensorIndex=0;
  }
}

//************* Serial response *********************
/* void sendData(void)
 * returns : 
 *  Checks if there is a data waiting in the serial buffer, if so, the Functions removes all waiting date and returns true
 *  if there was no data waiting, it returnes false.
 * 
 */
void sendData(){
  while(Serial.available()){
    switch(Serial.read()){
      case 'D':
        sendDistanceData();
        break;
      case 'P':
        sendPressureData();
        break;
      case 'T':
        calculateAndSendThemperatureData();
        break;
    }
  }
}


/*int avarage(int)
 * parameters :
 *  index: the index of the analog sensor
 * returns:
 *  The Avarage value of the sensor over the las 16 messurements
 */
int avarage(int index){
  int toret=0;
  for(int i = 0;i<BUFFER_SIZE;i++){
    toret+=sensorValues[index][i];
  }
  return toret/BUFFER_SIZE;
}


/* void sendPressureData(void)
 * Calculates the avarage value of all the Connected sensors and sends it over serial
 */
void sendPressureData(){
  Serial.println("P");
  for(int i = 0;i<16;i++){
    Serial.println(avarage(i));
  }    
}

/* void sendDistanceData(void)
 * Calculates the  avarage value of all the Connected sensors and sends it over serial
 */
void sendDistanceData(){
  Serial.println("D");
  Serial.println(avarage(16));
  Serial.flush();
    
}

/* void sendThemperatureData(void)
 * Calculates the  avarage value of all the Connected sensors and sends it over serial
 */
float temperature = 0.0;
void calculateAndSendThemperatureData(){
  // read temperature from sensor
  float temp = readTemperature(11,12);
  if(!isnan(temp)) {
    temperature=temp;
  }
  
  Serial.println("T");  
  Serial.println(temperature);
  Serial.flush();
}

