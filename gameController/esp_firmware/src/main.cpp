/** @file main.hpp
 *  @brief implementation of the Main Controll File.
 *
 *  @author Lukas Lühr (hexagon2206)
 *  @bug No known bugs.
 *  @todo Aceppt Multiple Clients ? the implementation is prepered for multiple clients, but still some changes are nessesary
 */

 #include "include/main.hpp"

ArduinoKeyboard ak;

WiFiServer server(PORT);  //Creating a server instance to be used

WiFiClient serverClient;
Session session(&ak);



void HANG(){
  LOGLN("HANG")
  while(1) delay(500);
}

bool configMode = false;
void setup() {

  //pinMode(5, INPUT);
  pinMode(5, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(0, OUTPUT);


  pinMode(14, INPUT_PULLUP);

  delay(2);
  digitalWrite(4, 1);

  SETUP_LOGGING
  LOGLN("\n\nStartup . . .")
  uint8_t i = 0xff;

  while(i && digitalRead(14)){
    i--;
  }
  if(i){
    LOGLN("entering config Mode")
    configMode=true;
    #ifndef DO_LOGGING
      Serial.begin(115200);
    #endif
    Serial.setTimeout(10*1000*60*60*24);  //10 Tage timeout
  }else{
    setupConfig();
    connectToWiFi();
    setupServer();
    delay(6000);
    LOGLN("Ready! ")
    digitalWrite(5, 1);
  }
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

char readData[MEMORY_SIZE];
char *name=readData,*pass;


int readuntill(char *target,int targetSize,char terminator){
  int l =0;
  while(true){
    while(0 == Serial.available());
    target[l] = Serial.read();
    if(target[l]==terminator){
      target[l]='\0';
      return l+1;
    }else{
      l++;
    }
  }
}

long last =millis();
void loop() {
  if(!configMode){
    long now = millis();
    ak.update(now-last);
    last=now;
    checkForIncommingConnections();
    checkIncommingData();
  }else{
    while(0==Serial.available());
    char t = Serial.read();
    int l = readuntill(name,MEMORY_SIZE,t);
    pass=name+l;
    readuntill(pass,MEMORY_SIZE-l,t);
    LOG("NEW SSID : ")LOGLN(name)
    LOG("NEW PWD  : ")LOGLN(pass)
    INIT_CONFIG_FROM_STRING(name,pass);
  }
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
      digitalWrite(4, 0);
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
