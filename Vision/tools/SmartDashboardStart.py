import sys
import time
from networktables import NetworkTables
import os
# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)

ip = 'roboRIO-{TEAM}-FRC.local'.format(TEAM=5987)
#ip='192.168.13.136'
NetworkTables.initialize(server=ip)

sd = NetworkTables.getTable("SmartDashboard")
start = sd.getAutoUpdateValue('Raspberry Start', False)
stream= sd.getAutoUpdateValue('Raspberry Stream', False)
local= sd.getAutoUpdateValue('Raspberry Local', False)
while not start.value:
    time.sleep(1)
print('execute')
os.chdir('/home/pi/Desktop/basic Vision/Vision')
command='python3 vision_class.py'
if local.value:
    command+=' -l'
if stream.value:
    command+=' -s'
print(command)
os.system(command)
