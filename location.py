#
# by Taka Wang
# edited by Hauke Buhr
#

import pika
import time
import Communicator
from proximity import *


def pack_location_to_json(version, timestamp, sensor_type, uuid, major, minor, db):
    beacon_json = '{"uuid" : "' + uuid + '", "major" : "' + major + '", "minor" : "' + minor + '", "dB" : "' + db + '}'

    json_msg = '{"version": ' + str(version) + ', "timestamp": ' + \
               str(timestamp) + ', "sensortype": "' + sensor_type + '", "values": ' + beacon_json + "]}"

    return json_msg

communicator = Communicator.RabbitMQCommunicator("127.0.0.1", "sg.ex.sensor_values",
                                                 "sg.rk.sensor_values")
#communicator = Communicator.CommunicatorDummy()

communicator.setup_connection()

scanner = Scanner(loops=3)
while True:
    version = 1

    for beacon in scanner.scan():
        timestamp = time.time()
        split_arr = beacon.split(',')
        json = pack_location_to_json(version, timestamp, "location", str(split_arr[1]),
                                             str(split_arr[2]), str(split_arr[3]), str(split_arr[5]))
        communicator.send(json)
