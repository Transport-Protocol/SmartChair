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

    /**
     * @brief Creates an instance of the Session
     * @param ak 47
     */
    Session(ArduinoKeyboard *ak);


    /**
     * @brief tries to read packages form the spezified client
     * @param client the client to read data from
     */
    void process(WiFiClient *client);

    /**
     * @brief sends a Session ID to the client, and uses it as the curent session ID
     * @param client to send the data to
     * @param sid the sesion Id to be send to the client, can be 0 to close the session
     */
    void sendSessionId(WiFiClient *client,uint16_t sid);

    /**
     * @brief resets the curent session
     * @note doesn`t close it acording to the protocoll
     */
    void reset();

  private:

    ArduinoKeyboard *ak;

    uint16_t version   = PROTOCOLL_VERSION ;
    uint16_t MagicNum  = PROTOCOLL_MAGIC;

    uint16_t sessionId = 0;

    uint8_t  avaitingFields = 0;      /**< The number of fields, still to receavce */
    uint32_t currentPaketID   = 0;    /**< The curent package ID, can be used to reprot problems with devices */

};

#endif
