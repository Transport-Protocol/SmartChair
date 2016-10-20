import MessageGenerator as msg_gen
import serial
import time
import gyroscope
import sys
import threading

import find_serialport

sys.path.append('/home/chair/git/SmartChair/pi_python/py-beacon')
from proximity import Scanner

scanner = Scanner(loops=3)

# port = serial.Serial("/dev/tty.wchusbserial410", 9600, timeout=None)
port = serial.Serial(find_serialport.get_serial_port(), 9600, timeout=None)
# port = serial.Serial("com3", 9600, timeout=None)
port = False

pressure_sensor_ids = list(range(0, 10))
acceleration_sensor_ids = list(range(0, 3))
test = list(range(0, 16))
serial_mutex = threading.RLock()

def distance_sensor(timestamp):
    json_list = []
    print("distance_sensor()")
    # get Values
    value = [1]
    # Validation

    # get json
    json_list.append(msg_gen.pack_to_json(1, timestamp, "distance", [0], value))
    return json_list


def acceleration_sensor(timestamp):
    json_list = []
    print("acceleration_sensor")

    # get json
    json_list.append(
        msg_gen.pack_to_json(1, timestamp, "acceleration", acceleration_sensor_ids, gyroscope.get_accelerator_values()))
    json_list.append(
        msg_gen.pack_to_json(1, timestamp, "gyroscope", acceleration_sensor_ids, gyroscope.get_gyro_values()))

    return json_list


def sound_sensor(timestamp):
    json_list = []
    print("sound_sensor()")
    # get Values
    value = [1]
    # Validation

    # get json
    json_list.append(msg_gen.pack_to_json(1, timestamp, "sound", [0], value))
    return json_list


def location(timestamp):
    json_list = []
    print("location()")
    # get Values

    while True:
        for beacon in scanner.scan():
            # get values
            split_arr = beacon.split(',')

            # build json string
            json = msg_gen.pack_location_to_json(1, timestamp, "location", str(split_arr[1]),
                                                 str(split_arr[2]), str(split_arr[3]), str(split_arr[5]))

            # add to queue
            json_list.append(json)

    return json_list


def serial_sensors(timestamp):
    # print("port is open: ", port.is_open)
    json_list = []

    serial_mutex.acquire()

    port.write(b'ss')
    port.flush()

    while not port.inWaiting():
        time.sleep(0.001)
        # print("waiting for lines")

    start_sequence = port.read()
    # print(start_sequence)

    while not start_sequence == b'G':
        # print("start_sequence not valid")
        time.sleep(0.001)
        start_sequence = port.read()

    port.read(2)

    # print("start_sequence valid")
    analogs = []
    analog_values = []

    i = 0
    while i < 16:
        analogs.append(port.readline())
        i += 1

    temperature = port.read(6)

    serial_mutex.release()

    j = 0
    while j < 16:

        if j == 4:
            j += 4

        if j == 14:
            break

        analog_values.append(int(analogs[j]))
        j += 1

    temperature_value = [float(temperature)]

    # print("analogs: ", analog_values)
    # print("temperature: ", temperature_value)
    json_list.append(msg_gen.pack_to_json(1, timestamp, "pressure", pressure_sensor_ids, analog_values))
    json_list.append(msg_gen.pack_to_json(1, timestamp, "temperature", [0], temperature_value))

    return json_list
