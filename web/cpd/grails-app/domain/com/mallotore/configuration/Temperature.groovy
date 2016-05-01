package com.mallotore.configuration

class Temperature {
	int id
    int probeIntervalInSeconds
    boolean connectivityAlert
    int overTemperatureAlert

    static mapping =  {
		version false
		table name: "temperature_configuration", schema: "cpd"
		id generator: "increment", name: "id"
	}

    static constraints = {
        probeIntervalInSeconds min: 1, max: Integer.MAX_VALUE
    }
}