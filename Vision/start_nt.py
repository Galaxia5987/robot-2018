import time
from networktables import NetworkTables
import os
import netifaces as ni
# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)
while not ni.ifaddresses('eth0')[ni.AF_INET][0]['addr'] is None:
    time.sleep(1)
ip = 'roboRIO-5987-FRC.local'
#ip='192.168.13.136'
NetworkTables.initialize(server=ip)
time.sleep(1)
sd = NetworkTables.getTable("SmartDashboard")
sd.putBoolean("Raspberry Start", False)
start = sd.getAutoUpdateValue('Raspberry Start', False)
while not start.value:
    time.sleep(1)
print('execute')
os.chdir('/home/pi/Desktop/Vision')
command='sudo python3 vision_class.py -s'
os.system(command)
sd.putBoolean("Raspberry Start",False)
