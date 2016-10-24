#!/bin/python

import os


def get_serial_port():
    output = os.popen("""ls /dev/ | egrep 'wchusbserial|ttyUSB'""").read()
    print(output)
    print(type(output))

    output_lines = output.splitlines()
    print(output_lines)
    print(type(output_lines))

    output_lines.sort()

    print(output_lines)

    if len(output_lines) > 0:
        return output_lines[-1]
    else:
        return False

if __name__ == "__main__":
    print(get_serial_port())
