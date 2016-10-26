import MessageGenerator as msg_gen
import serial
import time
# import gyroscope
import sys
import threading

import find_serialport


# serial port initialisation
port = serial.Serial(find_serialport.get_serial_port(), 9600, timeout=None)
# port = serial.Serial("/dev/ttyUSB0", 9600, timeout=None)
# port = serial.Serial("/dev/tty.wchusbserial410", 9600, timeout=None)
# port = serial.Serial("com3", 9600, timeout=None)
# port = False

gyroscope = False

# generated Lists for easier json String building
pressure_sensor_ids = list(range(0, 10))
acceleration_sensor_ids = list(range(0, 6))
distance_sensor_ids = list(range(0, 1))
test = list(range(0, 16))

# mutual exclusion for serial connection
serial_mutex = threading.RLock()


def acceleration_sensor(timestamp):
    print("acceleration_sensor")
    json_list = []

    # get json
    json_list.append(
        msg_gen.pack_to_json(1, timestamp, "acceleration", acceleration_sensor_ids, gyroscope.get_accelerator_values()))
    json_list.append(
        msg_gen.pack_to_json(1, timestamp, "gyroscope", acceleration_sensor_ids, gyroscope.get_gyro_values()))

    return json_list


def sound_sensor(timestamp):
    print("sound_sensor()")
    json_list = []
    # get Values
    value = [1]
    # Validation

    # get json
    json_list.append(msg_gen.pack_to_json(1, timestamp, "sound", [0], value))
    return json_list


def temperature(timestamp):
    # print("temperatur()")
    start_sign = b'T'
    start_sign_ord = b'ord("T")'

    json_list = []

    serial_mutex.acquire()

    before = time.time()
    serial_establish_connection(port, start_sign_ord, start_sign)

    # print("start_sequence valid")
    temperature = port.read(6)

    print("temperature: Serial Time needed: " + str(time.time() - before))
    serial_mutex.release()

    # get json
    temperature_value = [float(temperature)]
    json_list.append(msg_gen.pack_to_json(1, timestamp, "temperature", [0], temperature_value))
    return json_list


def pressure(timestamp):
    # print("pressure()")
    json_list = []
    start_sign = b'P'
    start_sign_ord = b'ord("P")'

    serial_mutex.acquire()

    before = time.time()
    serial_establish_connection(port, start_sign_ord, start_sign)

    analogs = []
    analog_values = []

    i = 0
    while i < 16:
        analogs.append(port.readline())
        i += 1

    print("pressure: Serial Time needed: " + str(time.time()-before))
    serial_mutex.release()

    j = 0
    while j < 16:

        if j == 4:
            j += 4

        if j == 14:
            break

        analog_values.append(int(analogs[j]))
        j += 1

    json_list.append(msg_gen.pack_to_json(1, timestamp, "pressure", pressure_sensor_ids, analog_values))

    return json_list


def distance_sensor(timestamp):
    print("distance()")

    json_list = []
    start_sign = b'D'
    start_sign_ord = b'ord("D")'

    serial_mutex.acquire()

    before = time.time()
    serial_establish_connection(port, start_sign_ord, start_sign)

    # print("start_sequence valid")
    temperature = port.read(6)

    print("Distance: Serial Time needed: " + str(time.time() - before))
    serial_mutex.release()

    # get json
    temperature_value = [float(temperature)]
    json_list.append(msg_gen.pack_to_json(1, timestamp, "temperature", [0], temperature_value))
    return json_list


def serial_establish_connection(specific_port, start_sign_ord, start_sign):

    specific_port.write(start_sign_ord)
    specific_port.flush()

    while not specific_port.inWaiting():
        time.sleep(0.001)

    start_sequence = specific_port.read()

    while start_sequence != start_sign:
        # print("start_sequence not valid")
        time.sleep(0.001)
        start_sequence = specific_port.read()

    specific_port.read(2)
