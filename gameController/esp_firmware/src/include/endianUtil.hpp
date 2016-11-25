#ifndef __PACKAGE_UTIL_H__
#define __PACKAGE_UTIL_H__

#include <ESP8266WiFi.h>
//#include <machine/endian.h>


// convertes the byte array spezified by P to a uint16_t
uint16_t toUint16(uint8_t*p);

// convertes the byte array spezified by P to a uint24_t stored as a uint32_t
uint32_t toUint24(uint8_t*p);

// Writes the datat d to the address t as big endian.
void toBigEndian16(uint8_t *t,uint16_t d);

// Writes the datat d to the address t as big endian, d is used as a uint24_t.
void toBigEndian24(uint8_t *t,uint32_t d);

// Writes the datat d to the address t as big endian.
void toBigEndian32(uint8_t *t,uint32_t d);

#endif
