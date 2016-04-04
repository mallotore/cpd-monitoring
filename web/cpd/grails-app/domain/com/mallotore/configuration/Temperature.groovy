package com.mallotore.configuration

class Temperature {
	int id
    int intervalInSeconds

    static mapping =  {
		version false
		table name: "temperature_configuration", schema: "cpd"
		id generator: "increment", name: "id"
	}

    static constraints = {
        intervalInSeconds min: 1
    }
}