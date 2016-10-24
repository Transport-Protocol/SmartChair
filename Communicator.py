import pika

class RabbitMQCommunicator:
    def __init__(self, ip_address, mq_exchange_name, mq_routing_keys):
        self.connection = False
        self.channel = False
        self.ip_address = ip_address
        self.mq_exchange_name = mq_exchange_name
        self.mq_routing_keys = mq_routing_keys

    def setup_connection(self):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host=self.ip_address))
        self.channel = self.connection.channel()

    def close_connection(self):
        self.connection.close()

    def send(self, to_send):
        self.channel.basic_publish(exchange=self.mq_exchange_name,
                                   routing_key=self.mq_routing_keys,
                                   body=to_send)


class CommunicatorDummy:
    def __init__(self):
        self.name = "Dummy"
        print(self.name + "created!")

    def setup_connection(self):
        print(self.name + "setup!")

    def close_connection(self):
        print(self.name + "close!")

    def send(self, to_send):
        print(self.name + ":" + to_send)