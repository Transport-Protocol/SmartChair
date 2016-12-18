#ifndef __CONFIG_H__
#define __CONFIG_H__


#define MEMORY_SIZE 128     /**< defines the Size of the EEPROM to read */

#include <EEPROM.h>
#include "myLogging.h"


/**
 * @brief initalsies the eeprom and reads password and SSID from
 * Passwort and SSID must be stored in the eeprom otherwise it courses problems
 * @note  if INIT_CONFIG is defined, then ist also wirtes the spezified values to the eeprom
 */
void setupConfig();

/**
 * @brief  returnes the SSID
 * @return the SSID as stored in the EEPROM
 */
const char *getSSID();

/**
 * @brief  returnes the Password
 * @return the Password as stored in the EEPROM
 */
const char *getPWD();


void INIT_CONFIG_FROM_STRING(char *nSsid,char *nPwd);


void readConfig();


#endif
