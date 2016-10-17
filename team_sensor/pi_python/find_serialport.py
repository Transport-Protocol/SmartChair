#!/bin/python

import os


def get_serial_port():
    output = os.popen("""ls /dev/ | egrep 'wchusbserial|ttyUSB'""").read()
    print(output)
    print(type(output))

    outputLines = output.splitlines()
    print(outputLines)
    print(type(outputLines))

    outputLines.sort()

    print(outputLines)

    if len(outputLines) > 0 :
        return outputLines[-1]
    else:
        return False
