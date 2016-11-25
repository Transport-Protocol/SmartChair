#ifndef __ARDUINO_KEYBOARD_H_
#define __ARDUINO_KEYBOARD_H_

#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>

#include "myLogging.h"


/**
 * @brief Used to send keyboard commands to the Arduino Leonardo
 * stores the state of all keys and Performes the activation and
 */
class ArduinoKeyboard{
  public:
    /**
     * @brief Initialises the Arduino Keyboard.
     */
    ArduinoKeyboard();

    /**
     * @brief Updates the internal state, should be caled cyclicly
     * @param timePassed the time passed since the last call of this function
     * internaly modifies the timer and active arrays, also sends commands direktly to the Leonardo vie Serail1
     */
    void update(uint16_t timePassed);

    /**
     * @brief can be used to set the state of an ascii key, for spezial keyys see arduino leonardo keyboard spezifikation
     * @param ascii the ascii code for the key, for spezial keys see leonardo keyboard documentation
     * @param time The duration opf the time press in milliseconds, can be 0 to deactivate a key
     * Sends direktley a command via Serail1
     */
    void setState(uint8_t ascii,uint16_t time);

  private:

    uint8_t  active[256];     /**< @brief used tp store the active ascii codes */
    uint16_t timer [257];     /**< @brief used to store the counter of an key, the index of an ascicode is ascii+1 */
    uint16_t activeCount=0;   /**< @brief stores the number of pressed keys */

};

#endif
