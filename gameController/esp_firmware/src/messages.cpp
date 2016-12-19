/** @file messages.cpp
 *  @brief implements function for Package creation .
 *
 *  @author Lukas Lühr (hexagon2206)
 *  @bug No known bugs.
 */

 #include "include/messages.hpp"

connectionMsg createConnectionMsg(uint16_t version,uint16_t magicNumber){
  connectionMsg msg;
  toBigEndian16(msg.version,version);
  toBigEndian16(msg.magicNumber,magicNumber);
  return msg;
}


connectionResponse createConnectionResponse(uint16_t version,uint16_t sessionId){
  connectionResponse msg;
  toBigEndian16(msg.version,version);
  toBigEndian16(msg.sessionId,sessionId);
  return msg;
}

messageHeader createMessageHeader(uint16_t sessionId,uint32_t paketNR,uint8_t dataCount){
  messageHeader msg;
  toBigEndian16(msg.sessionId,sessionId);
  toBigEndian24(msg.paketNR,paketNR);
  msg.dataCount=dataCount;
  return msg;
}

deviceCommand createDeviceCommand(uint16_t device,uint16_t data){
  deviceCommand msg;
  toBigEndian16(msg.device,device);
  toBigEndian16(msg.data,data);
  return msg;
}
