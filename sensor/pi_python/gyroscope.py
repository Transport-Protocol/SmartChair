import smbus
import math

# Power Management
power_mgmt_1 = 0x6b

# detect sudo i2cdetect -y 1
gyro_address_first = 0x68
gyro_address_second = 0x69


bus = smbus.SMBus(1)
bus.write_byte_data(gyro_address_first, power_mgmt_1, 0)
bus.write_byte_data(gyro_address_second, power_mgmt_1, 0)


def read_word_2c(gyro_address, reg):
    h = bus.read_byte_data(gyro_address, reg)
    l = bus.read_byte_data(gyro_address, reg + 1)
    value = (h << 8) + l

    if value >= 0x8000:
        return -((65535 - value) + 1)
    else:
        return value


def get_accelerator_values():
    acc_list = []

    acceleration_x1_out = read_word_2c(gyro_address_first, 0x3b)
    acceleration_y1_out = read_word_2c(gyro_address_first, 0x3d)
    acceleration_z1_out = read_word_2c(gyro_address_first, 0x3f)
    acceleration_x2_out = read_word_2c(gyro_address_second, 0x3b)
    acceleration_y2_out = read_word_2c(gyro_address_second, 0x3d)
    acceleration_z2_out = read_word_2c(gyro_address_second, 0x3f)

    acc_list.append(acceleration_x1_out)
    acc_list.append(acceleration_y1_out)
    acc_list.append(acceleration_z1_out)
    acc_list.append(acceleration_x2_out)
    acc_list.append(acceleration_y2_out)
    acc_list.append(acceleration_z2_out)

    return acc_list


def get_gyro_values():
    gyro_list = []
    gyro_x1_out = read_word_2c(gyro_address_first, 0x43)
    gyro_y1_out = read_word_2c(gyro_address_first, 0x45)
    gyro_z1_out = read_word_2c(gyro_address_first, 0x47)
    gyro_x2_out = read_word_2c(gyro_address_second, 0x43)
    gyro_y2_out = read_word_2c(gyro_address_second, 0x45)
    gyro_z2_out = read_word_2c(gyro_address_second, 0x47)

    gyro_list.append(gyro_x1_out)
    gyro_list.append(gyro_y1_out)
    gyro_list.append(gyro_z1_out)
    gyro_list.append(gyro_x2_out)
    gyro_list.append(gyro_y2_out)
    gyro_list.append(gyro_z2_out)

    return gyro_list

