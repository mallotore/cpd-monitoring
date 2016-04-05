package com.mallotore.configuration

class Temperature {
	int id
    long probeIntervalInSeconds

    static mapping =  {
		version false
		table name: "temperature_configuration", schema: "cpd"
		id generator: "increment", name: "id"
	}

    static constraints = {
        probeIntervalInSeconds nullable: false, min: 1
    }
}