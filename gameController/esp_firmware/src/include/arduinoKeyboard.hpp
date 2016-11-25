#ifndef __ARDUINO_KEYBOARD_H_
#define __ARDUINO_KEYBOARD_H_

#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>

#include "myLogging.h"

struct activation{
  uint8_t key;
  uint16_t duration;
};

//Always uses Serial1
class ArduinoKeyboard{
  public:
    ArduinoKeyboard();
    void update(uint16_t timePassed);
    void setState(uint8_t ascii,uint16_t time);

  private:

    uint8_t  active[256];
    uint16_t timer [257];
    uint16_t activeCount=0;

};

#endif
