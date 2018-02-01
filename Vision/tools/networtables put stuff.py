from networktables import NetworkTables
NetworkTables.initialize(server='10.59.87.87')
table = NetworkTables.getTable("Vision")