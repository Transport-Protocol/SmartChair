#include <Arduino.h>

/*
 Keyboard to serial Application
 */

#include "Keyboard.h"
#include "SoftwareSerial.h"

SoftwareSerial mySerial(10, 11); // RX, TX



void setup() {
  Keyboard.begin();
  mySerial.begin(9600);
  while(!mySerial);

  delay(5000);
  while(mySerial.available()){
    mySerial.read();
  }
}

char action=-1;
char inChar=-1;

void loop() {
  if (mySerial.available()) {
     if(action == -1){
      action = mySerial.read();
     }else{
      inChar = mySerial.read();
     }
     if(-1!=inChar){
      if(0x01 == action){
        Keyboard.press(inChar);
      }else if(0x00 == action ){
        Keyboard.release(inChar);
      }
      action = inChar = -1;
    }
  }
}
