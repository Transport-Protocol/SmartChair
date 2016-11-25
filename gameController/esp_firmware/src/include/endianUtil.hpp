#ifndef __PACKAGE_UTIL_H__
#define __PACKAGE_UTIL_H__

#include <ESP8266WiFi.h>
//#include <machine/endian.h>


/**
 * @brief convertes the byte array from netowrk byte order to  uint16_t
 * @param p a pointer to a byte array of length 2
 * @return the Host byte order representation of p
 * @note implementation is byte order agnostic
 */
uint16_t toUint16(uint8_t*p);

/**
 * @brief convertes the byte array from netowrk byte order to  uint32_t, onely the rorst 3 bytes are use however
 * @param p a pointer to a byte array of length 3
 * @return the Host byte order representation of p
 * @note implementation is byte order agnostic
 */
uint32_t toUint24(uint8_t*p);

/**
 * @brief convertes from uint16_t to network byte order, stores the result at d
 * @param t a pointer to a byte array of length 2 , the result will be stored here
 * @param d the Host byte order data to be converted
 * @note implementation is byte order agnostic
 */
void toBigEndian16(uint8_t *t,uint16_t d);

/**
 * @brief convertes from uint32_t to network byte order, stores the result at d
 * @param t a pointer to a byte array of length 3 , the result will be stored here
 * @param d the Host byte order data to be converted, to higest value byte will be ignored
 * @note implementation is byte order agnostic
 */
 void toBigEndian24(uint8_t *t,uint32_t d);

 /**
  * @brief convertes from uint32_t to network byte order, stores the result at d
  * @param t a pointer to a byte array of length 4 , the result will be stored here
  * @param d the Host byte order data to be converted
  * @note implementation is byte order agnostic
  */
  void toBigEndian32(uint8_t *t,uint32_t d);

#endif
