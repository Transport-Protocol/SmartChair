#!/usr/bin/python3

import argparse
import queue

import Communicators
import HAL
import Threads

# Define here all supported sensors.
sensor = ["pressure", "temperature", "acceleration", "distance"]

parser = argparse.ArgumentParser()

parser.add_argument("--rabbitMQ",
                    action="store_true", dest="rabbit", default=False,
                    help="This flag causes json strings to send to rabbitMQ.")

parser.add_argument("--socket",
                    action="store_true", dest="socket", default=False,
                    help="This flag causes json strings to send by socket.")

for entry in sensor:
    parser.add_argument("--" + entry, type=float,
                        help="This flag enables " + entry + " sensor. You can enter an interval after the flag."
                                                            " Interval in Seconds! example = 1.0")

args = parser.parse_args()

# get collect all needed sensor threads
json_queue = queue.Queue()
threads = []

if args.pressure is not None:
    print("pressure active!")
    threads.append(
        Threads.SensorEvaluator(1, "SensorEvaluator_pressure", 1, args.pressure, json_queue, HAL.pressure))

if args.temperature is not None:
    print("temperature active!")
    threads.append(
        Threads.SensorEvaluator(2, "SensorEvaluator_temperature", 2, args.temperature, json_queue, HAL.temperature))

if args.acceleration is not None:
    print("acceleration active!")
    threads.append(
        Threads.SensorEvaluator(3, "SensorEvaluator_accelerator", 3, args.acceleration, json_queue, HAL.acceleration_sensor))

if args.distance is not None:
    print("distance active!")
    threads.append(
        Threads.SensorEvaluator(3, "SensorEvaluator_distance", 4, args.distance, json_queue, HAL.acceleration_sensor))

# acquire right communication medium
if args.rabbit:
    print("rabbit active!")
    communicator = communicator = Communicators.RabbitMQCommunicator("127.0.0.1", "sg.ex.sensor_values",
                                                                     "sg.rk.sensor_values", json_queue)
elif args.socket:
    print("socket active!")
    communicator = Communicators.SocketCommunicator("141.22.80.72", 15000)

else:
    communicator = Communicators.CommunicatorDummy()

threads.append(Threads.MQCommunicator(10, "MQ_Communicator", 10, json_queue, communicator))

# start the communicator thread
for thread in threads:
    thread.start()

# join all threads
for thread in threads:
    thread.join()
