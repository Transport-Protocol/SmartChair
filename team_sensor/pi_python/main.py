#!/usr/bin/python

## Requires pika for queue connection

import queue as QueuePackage
import Communicators as communicators
import Threads as own_threads
import HAL


json_queue = QueuePackage.Queue()

# Get the communicator and Setup the connection
communicator = communicators.CommunicatorDummy()
# communicator = communicators.RabbitMQCommunicator("127.0.0.1", "sg.ex.sensor_values", "sg.rk.sensor_values", json_queue)
# communicator = communicators.SocketCommunicator("141.22.80.72", 15000)
communicator.setup_connection()

# create threads
serial_sensors_thread = own_threads.SensorEvaluator(1, "SensorEvaluator_pressure", 1, json_queue, HAL.serial_sensors)
communicator_thread = own_threads.MQ_Communicator(10, "MQ_Communicator", 10, json_queue, communicator)

# start the communicator thread
serial_sensors_thread.start()
communicator_thread.start()



# join all threads
serial_sensors_thread.join()
communicator_thread.join()









