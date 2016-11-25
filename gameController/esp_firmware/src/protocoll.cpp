#include "include/protocoll.hpp"

Session::Session(ArduinoKeyboard *ak){
  this->ak = ak;
}

void Session::sendSessionId(WiFiClient *client,uint16_t sid){
    this->sessionId = sid;
    connectionResponse response = createConnectionResponse(this->version,this->sessionId);
    LOG("SESSION ID : ")LOGLN(this->sessionId)
    client->write(((const char *)(&response)),sizeof(connectionResponse) );
}

void Session::reset(){
  this->sessionId       = 0;
  this->avaitingFields  = 0;
  this->currentPaketID  = 0;
}

void Session::process(WiFiClient *client){
  if(0==this->sessionId){        // We expact a Connection Msg, because we dont have a Session ID
    if(sizeof(connectionMsg)<=client->available()){
      connectionMsg MSG;
      client->read(((uint8_t *)(&MSG)), sizeof(connectionMsg));
      if(toUint16(MSG.version) == this->version && toUint16(MSG.magicNumber) == this->MagicNum){  //Check if the Given Version number and Magic number match the expacted one
        LOGLN("SessionID an Client ausgegeben")
        sendSessionId(client,1);
      }else{
        LOG("Wrong Version:H")LOG(*MSG.version)LOG(",L")LOG(*(MSG.version+1))LOG(" Or magic:H")LOG(*MSG.magicNumber)LOG(",L")LOGLN(*(MSG.magicNumber+1))
        sendSessionId(client,0);
      }
    }
  }else if(0==this->avaitingFields){  //We have a session ID but No avaitingFields, thereform we expact a message header
    if(sizeof(messageHeader)<=client->available()){
      LOGLN("Reading Header")
      messageHeader MSG;

      client->read(((uint8_t *)(&MSG)), 6);

      LOG("SessionID : ")LOGLN(toUint16(MSG.sessionId))
      LOG("PaketNR : ")LOGLN(toUint24(MSG.paketNR))
      LOG("Datacount : ")LOGLN(MSG.dataCount)

      if(toUint16(MSG.sessionId) == this->sessionId){
        if(MSG.dataCount){
          LOG("Field Cont:")LOGLN(MSG.dataCount)
          this->avaitingFields = MSG.dataCount;
          this->currentPaketID = toUint24(MSG.paketNR);
        }else{
          LOGLN("Closing Session")
          sendSessionId(client,0);
          client->stop();
          reset();
        }
      }else{
        //TODO : wrong session ID, just ignore it ?
      }
    }
  }else{ //We expact Fields.
    if(sizeof(deviceCommand)<=client->available()){
      this->avaitingFields--;
      LOGLN("REeading Field")
      deviceCommand MSG;
      client->read(((uint8_t *)(&MSG)), sizeof(deviceCommand));
      uint16_t d = toUint16(MSG.device);
      uint16_t t = toUint16(MSG.data);

      if((d&0xFF00) == 0x0100){
        this->ak->setState((uint8_t)d, t);
        LOG("send key to keyboard")
      }
      LOG("Befehl erhalten , device: ")LOG(d)LOG(" data:")LOGLN(t)
    }
  }
}
