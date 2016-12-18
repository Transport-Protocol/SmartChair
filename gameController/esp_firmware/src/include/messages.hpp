/** @file messages.hpp
 *  @brief defines Packages as transfered ofer the network.
 *
 *  @author Lukas Lühr (hexagon2206)
 *  @bug No known bugs.
 */

#ifndef __MESSAGES_H__
#define __MESSAGES_H__

#include <ESP8266WiFi.h>
#include "endianUtil.hpp"

// A message, as defined in this file, can be read from a byte stream direktly.
// For dokumentation on the message types look at the protocoll spezification
// For each Message there is also a function implemented wich creaetes one from falues.


/**
 * @brief A connection massage as described in the Protokoll spezification
 * @note  Can be read directley form the Data Sream
 */
struct connectionMsg{
    uint8_t version[2];     /**< @brief 2 bytes for the version */
    uint8_t magicNumber[2]; /**< @brief 2 bytes for the magic Number */
} __attribute__((packed));  //this line is to prevent the compiler form inserting pedding

/**
 * @brief creates a Connection massage, that can be directly send
 * @param version the version of the Protocoll
 * @param magicNumber The magic number spezified in the Protocoll
 */
connectionMsg createConnectionMsg(uint16_t version,uint16_t magicNumber);


/**
 * @brief The Response for A Connection massage
 * @note  Can be read directley form the Data Sream
 */
struct connectionResponse{
  uint8_t version[2];   /**< @brief 2 bytes for the version */
  uint8_t sessionId[2]; /**< @brief 2 bytes for the session ID*/
} __attribute__((packed));

/**
 * @brief creates a Connection Response massage, that can be directly send
 * @param version the version of the Protocoll
 * @param sessionId the session ID used for all further corospondence
 */
connectionResponse createConnectionResponse(uint16_t version,uint16_t sessionId);

/**
 * @brief A Massage haeder as used for all normal comand messages
 * @note  Can be read directley form the Data Sream
 */
 struct messageHeader{
  uint8_t sessionId[2]; /**< @brief 2 bytes for the session ID */
  uint8_t paketNR[3];   /**< @brief 3 bytes paket number */
  uint8_t dataCount;    /**< @brief 1 byte data count */
} __attribute__((packed));

/**
 * @brief creates a Connection massage, that can be directly send
 * @param sessionId the session ID
 * @param paketNR the Paket Number, can be used for error reports or replies
 * @param dataCount the number of data fields following the header
 */
messageHeader createMessageHeader(uint16_t sessionId,uint32_t paketNR,uint8_t dataCount);

/**
 * @brief A Device command as spezified in the Protocoll
 * @note  Can be read directley form the Data Sream
 */
struct deviceCommand{
  uint8_t device[2];    /**< @brief 2 bytes for device */
  uint8_t data[2];      /**< @brief 2 bytes for device data */
} __attribute__((packed));

/**
 * @brief creates a Connection massage, that can be directly send
 * @param device the device ID of the device to write data to
 * @param data the data to write to the Device
 */
deviceCommand createDeviceCommand(uint16_t device,uint16_t data);

#endif
