import argparse

parser = argparse.ArgumentParser()


parser.add_argument('--length', type=float, help="This flag enables sen")
parser.add_argument('--bool', type=float, help="This flag enables sen")

args = parser.parse_args()

print(args.length)
if args.bool is not None:
    print(args.bool)


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
                                                            " Interval in Seconds! Advised default = 1.0")

