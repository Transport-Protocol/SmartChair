#include <inttypes.h>

uint8_t readByte(int signalPin,bool *error){
    uint16_t byte = 0;
    bool correkt = true;
    
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

uint16_t readWord(int signalPin,bool *error){
  uint8_t byte1 = readByte(signalPin,error);
  uint8_t byte2 = readByte(signalPin,error);
  return (byte1 << 8) | byte2;
}


float readTemperature(int powerPin,int signalPin){
  bool error=false;
  digitalWrite(powerPin, HIGH);  //Aktivate sensor 
  delayMicroseconds(60);  // etwas zeit für den sensor
  uint16_t word = readWord(signalPin,&error);    
  digitalWrite(powerPin, LOW);  //Deaktivate sensor
  if(error)return NAN;
  return (word / 2047.0f * 200.0f) - 50.0f; // from data sheet
}


void setup() {
    Serial.begin(9600); // set up the serial port
    pinMode(11,OUTPUT);
    pinMode(12,INPUT);
    
    Serial.println("start");
    
}

void loop() {
    Serial.print("read ...");
    // read temperature from sensor
    float temperature = readTemperature(11,12);
    Serial.println("[OK]");

    // check if there was an error and inform the user 
    if(isnan(temperature)) {
        Serial.println("There was an error reading the sensor!");
    } else {
        // seems ok - print the measured temperature
        Serial.print("Temperature: ");
        Serial.print(temperature);
        Serial.println(" C");
    }
    delay(500);
}

