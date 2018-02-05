from networktables import NetworkTables
NetworkTables.initialize(server='192.168.13.102')
table = NetworkTables.getTable("Vision")
data=input("Press any key to try: ")
table.putString("Filter Mode",data)
_=input("Lets Hope It Works")
