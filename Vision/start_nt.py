import time
from networktables import NetworkTables
import os
# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)

ip = 'roboRIO-5987-FRC.local'
#ip='192.168.13.136'
NetworkTables.initialize(server=ip)
time.sleep(1)
sd = NetworkTables.getTable("SmartDashboard")
sd.putBoolean("Raspberry Start", False)
sd.putBoolean("Raspberry Not Stream", False)
sd.putBoolean("Raspberry Local", False)
start = sd.getAutoUpdateValue('Raspberry Start', False)
stream= sd.getAutoUpdateValue('Raspberry Not Stream', False)
local= sd.getAutoUpdateValue('Raspberry Local', False)
while True:
    while not start.value:
        time.sleep(1)
    print('execute')
    os.chdir('/home/pi/Desktop/Vision')
    command='sudo python3 vision_class.py'
    if local.value:
        command+=' -l'
    if not stream.value:
        command+=' -s'
    print(command)
    os.system(command)
    sd.putBoolean("Raspberry Start",False)
