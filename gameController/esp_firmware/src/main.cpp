#include "include/main.hpp"

ArduinoKeyboard ak;

WiFiServer server(PORT);  //Creating a server instance to be used

WiFiClient serverClient;
Session session(&ak);



void HANG(){
  LOGLN("HANG")
  while(1) delay(500);
}


void setup() {

  SETUP_LOGGING
  LOGLN("\n\nStartup . . .")

  setupConfig();
  connectToWiFi();
  setupServer();
  delay(6000);
  LOGLN("Ready! ")

}

inline void connectToWiFi(){
  LOG("Connectiong to ")  LOG(getSSID())  LOG(" PW: ")  LOG(getPWD()) LOG(" . . . ")

  WiFi.begin(getSSID(), getPWD());
  uint8_t i;
  for(i = 20;i && WiFi.status() != WL_CONNECTED; i--){
    delay(500);
  }
  if(!i){
    LOGLN("[NOK]")
    HANG();
  }
  LOGLN("[OK]")
}

inline void setupServer(){
  LOG("Setting up Server ( ") LOG(WiFi.localIP()) LOG(":") LOG(PORT) LOG(" ) . . . ")
  server.begin();
  server.setNoDelay(true);
  LOGLN("[OK]")
}


long last =millis();
void loop() {
  long now = millis();
  ak.update(now-last);
  last=now;
  checkForIncommingConnections();
  checkIncommingData();
}


inline void checkForIncommingConnections(){
  uint8_t i;
  //check if there are any new clients
  if (server.hasClient()){
    if (!serverClient || !serverClient.connected()){    // The slot is disconnected or empty
      if(serverClient) serverClient.stop();             // the slot was nit empty so it must be stopped
      serverClient = server.available();                // input the Available clinert to the slot
      session.reset();
      LOG("Verbindung angenommen : ") LOG(serverClient.remoteIP().toString()) LOG(":") LOGLN(serverClient.remotePort())
      return;

    }
    WiFiClient cl = server.available();
    LOG("Eingehende Verbindung verworfen : ") LOG(cl.remoteIP().toString()) LOG(":") LOGLN(cl.remotePort())
    cl.stop();
  }
}

inline void checkIncommingData(){
  uint8_t i;
  if (serverClient && serverClient.connected()){
    if(serverClient.available()){
      processRequest(&serverClient);
    }
  }
}

inline void processRequest(WiFiClient *client){
  session.process(client);
}
