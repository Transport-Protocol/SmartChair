#ifndef __PROTOCOLL_H__
#define __PROTOCOLL_H__
#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>

#include "myLogging.h"
#include "messages.hpp"
#include "endianUtil.hpp"

#include "arduinoKeyboard.hpp"

#define PROTOCOLL_VERSION (0x1<<15 | 0x1)
#define PROTOCOLL_MAGIC   (0xbaba)

class Session {
  public:

    // Creates an instance of the Session using Serial1
    Session(ArduinoKeyboard *ak);

    // tries to read packages form the spezified client
    void process(WiFiClient *client);

    // sends a Session ID to the client, and uses it as the curent session ID
    void sendSessionId(WiFiClient *client,uint16_t sid);

    //setzt diese Session zurück, kann verwendet werden, wenn die verbindung zum client unterbrochen wird.
    void reset();

  private:

    ArduinoKeyboard *ak;

    uint16_t version   = PROTOCOLL_VERSION ;
    uint16_t MagicNum  = PROTOCOLL_MAGIC;

    uint16_t sessionId = 0;

    uint8_t  avaitingFields = 0;
    uint32_t currentPaketID   = 0;

};

#endif
