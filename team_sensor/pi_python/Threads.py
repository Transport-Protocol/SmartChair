import threading
import HAL
import time


class MQCommunicator (threading.Thread):
    def __init__(self, thread_id, name, counter, json_queue, communicator):
        threading.Thread.__init__(self)
        self.thread_id = thread_id
        self.name = name
        self.counter = counter
        self.q = json_queue
        self.communicator = communicator

        self.setDaemon()

    def run(self):
        while True:
            to_send = self.q.get()
            self.communicator.send(to_send)


class SensorEvaluator (threading.Thread):
    def __init__(self, thread_id, name, counter, interval_in_sec, json_queue, hal_function):
        threading.Thread.__init__(self)
        self.thread_id = thread_id
        self.name = name
        self.counter = counter
        self.q = json_queue
        self.function = hal_function
        self.interval_in_sec = interval_in_sec

        self.setDaemon()

    def run(self):
        while True:
            time.sleep(self.interval_in_sec)
            to_send = self.function()

            for json in to_send:
                self.q.put(json)
