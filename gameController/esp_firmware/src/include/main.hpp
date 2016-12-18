/** @file main.hpp
 *  @brief Prototypes for the Main Controll File.
 *
 *  @author Lukas Lühr (hexagon2206)
 *  @bug No known bugs.
 *  @todo Aceppt Multiple Clients ? the implementation is prepered for multiple clients, but still some changes are nessesary
 */

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

/**
 * @brief stopes the system
 * @note will never return
 */
void HANG();

/**
 * @brief called on setup
 * calles the other setup rotines and performes some logging.
 */
void setup();

/**
 * @brief connects to the in the eeprom spezified network
 */
inline void connectToWiFi();

/**
 * @brief setus up a TCP server as spezified in config.h
 */
inline void setupServer();

/**
 * @brief The main loop
 * cales the other main loop functions like checkForIncommingConnections , checkIncommingData or processRequest
 */
void loop();

/**
 * @brief Acepts incomming TCP conections
 * also closes connections, if the maximum number of connections is reached
 * @note this aplication can be used as a Multi client TCP server, it is deactivated in dthe config
 */
inline void checkForIncommingConnections();

/**
 * @brief checks all open connections for data and prozesses the client if there is any
 */
inline void checkIncommingData();

/**
 * @brief prozesses a Package from a client, uses the protocoll implementation
 */
inline void processRequest(WiFiClient *client);

#endif
