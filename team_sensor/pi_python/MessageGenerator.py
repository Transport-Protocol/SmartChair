import json


def pack_to_json(version, sensor_type, id_nr, values):

    value_string = '"values": ['

    for id_num in id_nr:
        value_string += '{"id": ' + str(id_num) + ', "value": ' + str(values[id_num]) + '}'

        if id_num < len(id_nr) - 1:
            value_string += ', '

    value_string += "]}"
    json_msg = '{"version": ' + str(version) + '", sensortype": "' + sensor_type + '", ' + value_string

    return json_msg
