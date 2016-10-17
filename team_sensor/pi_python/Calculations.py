
def calc_resistor(voltage_value):
    voltageRPressure = (1024.0 - voltage_value)/204.8
    rVar = (voltageRPressure*100000.0)/(5.0 - voltageRPressure)

    return int(rVar)