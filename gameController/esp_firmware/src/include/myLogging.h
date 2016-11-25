#ifndef __MY_LOGGING_H__
#define  __MY_LOGGING_H__

#include "config.h"

#define SETUP_LOGGING
#define LOG(X)
#define LOGLN(X)

#ifdef DO_LOGGING
  #include <ESP8266WiFi.h>

  #undef SETUP_LOGGING
  #undef LOG
  #undef LOGLN
  #define SETUP_LOGGING Serial.begin(115200);
  #define LOG(X) Serial.print(X);
  #define LOGLN(X) Serial.println(X);
#endif

#endif
