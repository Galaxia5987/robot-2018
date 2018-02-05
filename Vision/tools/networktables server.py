import time
from networktables import NetworkTables

# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)

NetworkTables.initialize()

def valueChanged(table, key, value, isNew):
    print("valueChanged: key: '%s'; value: %s; isNew: %s" % (key, value, isNew))

def connectionListener(connected, info):
    print(info, '; Connected=%s' % connected)

NetworkTables.addConnectionListener(connectionListener, immediateNotify=True)
sd = NetworkTables.getTable("Vision")
sd.addEntryListener(valueChanged)

def set_item(key, value):
        """
        Summary: Add a value to SmartDashboard.

        Parameters:
            * key : The name the value will be stored under and displayed.
            * value : The information the key will hold.
            * value_type : The type of the value it recieved (string, integer, boolean, etc.).
        """
        value_type = type(value)
        if value_type is str:
            sd.putString(key, value)
        elif value_type is int or value_type is float:
            sd.putNumber(key, value)
        elif value_type is bool:
            sd.putBoolean(key, value)

while True:
    command=input('should be as such: Key, Value :> ')
    command.split(',')
    set_item(command[0],str(command[1]))
