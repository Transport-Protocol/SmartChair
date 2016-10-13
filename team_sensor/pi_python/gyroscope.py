import smbus
import math

# Power Management
power_mgmt_1 = 0x6b

# detect sudo i2cdetect -y 1
gyro_address = 0x68

# Scaling Factor Acceleration
scale = 16384.0

bus = smbus.SMBus(1)
bus.write_byte_data(gyro_address, power_mgmt_1, 0)


# read 16 bit
def read_word(reg):
    h = bus.read_byte_data(gyro_address, reg)
    l = bus.read_byte_data(gyro_address, reg + 1)
    value = (h << 8) + l
    return value


def read_word_2c(reg):
    val = read_word(reg)

    if val >= 0x8000:
        return -((65535 - val) + 1)
    else:
        return val


def dist(a, b):
    return math.sqrt((a*a) + (b*b))


def get_y_rotation(x, y, z):
    radians = math.atan2(x, dist(y, z))
    return -math.degrees(radians)


def get_x_rotation(x, y, z):
    radians = math.atan2(y, dist(x, z))
    return math.degrees(radians)


def get_accelerator_values():
    acc_list = []
    acceleration_x_out = read_word_2c(0x3b)
    acceleration_y_out = read_word_2c(0x3d)
    acceleration_z_out = read_word_2c(0x3f)

    acc_list.append(acceleration_x_out)
    acc_list.append(acceleration_y_out)
    acc_list.append(acceleration_z_out)

    return acc_list


def get_gyro_values():
    gyro_list = []
    gyro_x_out = read_word_2c(0x43)
    gyro_y_out = read_word_2c(0x45)
    gyro_z_out = read_word_2c(0x47)

    gyro_list.append(gyro_x_out)
    gyro_list.append(gyro_y_out)
    gyro_list.append(gyro_z_out)

    return gyro_list


def get_rotation_values():
    rotate_list = []
    acceleration_x_out = read_word_2c(0x3b)/scale
    acceleration_y_out = read_word_2c(0x3d)/scale
    acceleration_z_out = read_word_2c(0x3f)/scale

    x_rotation = get_x_rotation(acceleration_x_out, acceleration_y_out, acceleration_z_out)
    y_rotation = get_y_rotation(acceleration_x_out, acceleration_y_out, acceleration_z_out)

    rotate_list.append(x_rotation, y_rotation)
