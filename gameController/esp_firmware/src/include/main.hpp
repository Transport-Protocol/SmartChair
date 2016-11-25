#ifndef __MAIN_H__
#define __MAIN_H__
#include <Arduino.h>

#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>

#include "config.h"
#include "myLogging.h"
#include "eepromConfig.h"
#include "protocoll.hpp"
#include "arduinoKeyboard.hpp"

void HANG();


void setup();

inline void connectToWiFi();

inline void setupSS();

inline void setupServer();


void loop();


inline void checkForIncommingConnections();

inline void checkIncommingData();

inline void processRequest(WiFiClient *client);

#endif
