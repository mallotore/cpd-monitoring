package com.mallotore.configuration

class Server {
	String uuid
    String name
    String ip
    String port
    String service

    static mapping =  {
		version false
		table name: "server_configuration", schema: "cpd"
		id generator: "assigned", name: "uuid"
	}

    static constraints = {
        name blank: false, unique: true
        ip blank: false, unique: true
        port blank: false
        service blank: true, nullable: true
    }
}