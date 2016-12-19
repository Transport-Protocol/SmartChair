/** @file eepromConfig.cpp
 *  @brief implementation of the eeprom reader and writer functions .
 *  @note not Object oriented
 *
 *  @author Lukas Lühr (hexagon2206)
 *  @bug No known bugs.
 *  @todo Write a Objecct oriented more generic version ?
 */

 #include "include/eepromConfig.h"

#define MEMORY_SIZE 128

char MEM[MEMORY_SIZE];
char *gssid;
char *gpwd;

#ifdef INIT_CONFIG
  char *initSsid = (char*)WLAN_AP;
  char *initPassword=(char*)WLAN_PW;
#endif

void INIT_CONFIG_FROM_STRING(char *nSsid,char *nPwd){
  LOG("Writing Config [ '")LOG(nSsid)LOG("' - '")LOG(nPwd)LOG("']. . . ")
  int i;
  int addr=0;
  while(*nSsid){
    EEPROM.write(addr,*nSsid);
    addr++; nSsid++;
  }
  EEPROM.write(addr,*nSsid);
  addr++;
  while(*nPwd){
    EEPROM.write(addr,*nPwd);
    addr++; nPwd++;
  }
  EEPROM.write(addr,*nPwd);
  EEPROM.commit();
  LOGLN("[OK]")
}


void readConfig(){
  LOG("Reading config . . .")
  gssid=MEM;
  int addr=0;
  char c;
  while(c=EEPROM.read(addr)){
    *(MEM+addr)=c;
    addr++;
  }
  *(MEM+addr)='\0';
  addr++;
  gpwd=MEM+addr;
  while(c=EEPROM.read(addr)){
    *(MEM+addr)=c;
    addr++;
  }
  *(MEM+addr)='\0';
  LOGLN("[OK]")
}


void setupConfig(){
  LOG("Starting EEPROM . . . ")
  EEPROM.begin(MEMORY_SIZE);
  LOGLN("[OK]")
  #ifdef INIT_CONFIG
    INIT_CONFIG_FROM_STRING(initSsid,initPassword);
  #endif
  readConfig();
}

const char *getSSID(){
  return gssid;
}

const char *getPWD(){
  return gpwd;
}
