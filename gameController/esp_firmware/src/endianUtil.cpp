/** @file endianUtil.cpp
 *  @brief implemetation of Host to Network Byte order Conversion and the Other way around.
 *
 *  @author Lukas Lühr (hexagon2206)
 *  @bug No known bugs.
 */

#include "include/endianUtil.hpp"

uint16_t toUint16(uint8_t*p){
    return (*p << 8) | *(p+sizeof(uint8_t));
}

uint32_t toUint24(uint8_t*p){
    //  return (((uint32_t)*p)     << (8*sizeof(uint8_t)*2)) |
    //         (((uint32_t)*(p+1)) << (8*sizeof(uint8_t))) |
    //          ((uint32_t)*(p+2));
      return((uint32_t)p[0])<<16|((uint32_t)p[1])<<8|((uint32_t)p[2]);
}


void toBigEndian16(uint8_t *t,uint16_t d){
  *(t+1)=(uint8_t)d;
  *t    =(uint8_t)(d>>8);
}

void toBigEndian24(uint8_t *t,uint32_t d){
  *(t+2)=(uint8_t)d;
  *(t+1)=(uint8_t)(d>>8);
  *t    =(uint8_t)(d>>16);
}

void toBigEndian32(uint8_t *t,uint32_t d){
  *(t+3)=(uint8_t)d;
  *(t+2)=(uint8_t)(d>>8);
  *(t+1)=(uint8_t)(d>>16);
  *t    =(uint8_t)(d>>24);
}
