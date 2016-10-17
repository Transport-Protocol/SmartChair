import json


def pack_to_json(version, timestamp, sensor_type, id_nr, values):
    value_string = '"values": ['

    for id_num in id_nr:
        value_string += '{"id": ' + str(id_num) + ', "value": ' + str(values[id_num]) + '}'

        if id_num < len(id_nr) - 1:
            value_string += ', '

    json_msg = '{"version": ' + str(version) + ', "timestamp": ' + \
               timestamp + ', "sensortype": "' + sensor_type + '", ' + value_string + "]}"

    return json_msg


def pack_location_to_json(version, timestamp, sensor_type, uuid, major, minor, db):
    beacon_json = '{"uuid" : "' + uuid + '", "major" : "' + major + '", "minor" : "' + minor + '", "dB" : "' + db + '}'

    json_msg = '{"version": ' + str(version) + ', "timestamp": ' + \
               timestamp + ', "sensortype": "' + sensor_type + '", "values": ' + beacon_json + "]}"

    return json_msg
