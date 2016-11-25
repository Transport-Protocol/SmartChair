#ifndef __CONFIG_H__
#define __CONFIG_H__


#define MEMORY_SIZE 128     // defines the Size of the EEPROM to read

#include <EEPROM.h>
#include "myLogging.h"

// initalsies the eeprom and other stuff,
// if INIT_CONFIG is defined, then ist also wirtes the spezified values to the eeprom
void setupConfig();

const char *getSSID();
const char *getPWD();



#endif
