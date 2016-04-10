package com.mallotore.configuration

class Server {
	String uuid
    String name
    String ip
    int port
    int probeIntervalInSeconds
    boolean connectivityAlert
    int diskPercentageAlert

    static mapping =  {
		version false
		table name: "server_configuration", schema: "cpd"
		id generator: "assigned", name: "uuid"
	}

    static constraints = {
        name blank: false, nullable: false
        ip blank: false, nullable: false
        probeIntervalInSeconds min: 30, max: Integer.MAX_VALUE
        diskPercentageAlert max: 99
    }
}