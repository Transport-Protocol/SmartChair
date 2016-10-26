#!/bin/python

import os


def get_serial_port():
    output = os.popen("""ls /dev/ | egrep 'wchusbserial|ttyUSB'""").read()

    output_lines = output.splitlines()

    output_lines.sort()

    if len(output_lines) > 0:
        return "/dev/" + output_lines[-1]
    else:
        return False

if __name__ == "__main__":
    print(get_serial_port())
