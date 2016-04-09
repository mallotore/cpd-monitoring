package com.mallotore.configuration

class Server {
	String uuid
    String name
    String ip
    int port
    int probeIntervalInSeconds

    static mapping =  {
		version false
		table name: "server_configuration", schema: "cpd"
		id generator: "assigned", name: "uuid"
	}

    static constraints = {
        name blank: false, unique: true
        ip blank: false, unique: true
        probeIntervalInSeconds min: 30, max: Integer.MAX_VALUE
    }
}