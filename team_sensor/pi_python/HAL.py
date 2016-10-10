import MessageGenerator as msg_gen
import serial
import io
import time

# port = serial.Serial("/dev/tty.wchusbserial410", 9600, timeout=None)
port = serial.Serial("/dev/ttyUSB0", 9600, timeout=None)
# port = serial.Serial("com3", 9600, timeout=None)

pressure_sensor_ids = list(range(0, 10))
acceleration_sensor_ids = list(range(0, 2))
test = list(range(0, 16))


def distance_sensor():
    json_list = []
    print("distance_sensor()")
    # get Values
    value = [1]
    # Validation

    # get json
    json_list.append(msg_gen.pack_to_json(1, "distance", [0], value))
    return json_list


def acceleration_sensor():
    json_list = []
    print("acceleration_sensor")
    # get Values
    values = [1,2]
    # Validation

    # get json
    json_list.append(msg_gen.pack_to_json(1, "acceleration", acceleration_sensor_ids, values))
    return json_list


def sound_sensor():
    json_list = []
    print("sound_sensor()")
    # get Values
    value = [1]
    # Validation

    # get json
    json_list.append(msg_gen.pack_to_json(1, "sound", [0], value))
    return json_list


def serial_sensors():

    print("port is open: ", port.is_open)
    json_list = []

    port.write(b'ss')
    port.flush()

    while not port.inWaiting():
        time.sleep(0.01)
        # print("waiting for lines")

    start_sequence = port.read()
    print(start_sequence)

    while not start_sequence == b'G':
        print("start_sequence not valid")
        time.sleep(0.01)
        start_sequence = port.read()

    port.read(2)

    print("start_sequence valid")
    analogs = []
    analog_values = []

    i = 0
    while i < 16:
        analogs.append(port.readline())
        i += 1

    temperature = port.read(6)

    j = 0
    while j < 16:

        if j == 4:
            j += 4

        if j == 14:
            break

        analog_values.append(int(analogs[j]))
        j += 1


    temperature_value = []
    temperature_value.append(float(temperature))

    print("analogs: ", analog_values)
    print("temperature: ", temperature_value)
    json_list.append(msg_gen.pack_to_json(1, "pressure", pressure_sensor_ids, analog_values))
    json_list.append(msg_gen.pack_to_json(1, "temperature", [0], temperature_value))

    return json_list
