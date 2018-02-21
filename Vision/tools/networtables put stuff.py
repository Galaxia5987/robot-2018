from networktables import NetworkTables
NetworkTables.initialize(server='10.59.87.23')
table = NetworkTables.getTable("Vision")
data=input("Press any key to try: ")
table.putString("Filter Mode",data)
_=input("Lets Hope It Works")
