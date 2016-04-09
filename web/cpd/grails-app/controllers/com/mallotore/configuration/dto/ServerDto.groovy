package com.mallotore.configuration.dto

import grails.validation.Validateable

class ServerDto implements Validateable{
	String id
	String name
    String ip
    int port
    int probeInterval

    static constraints = {
        name blank: false, unique: true
        ip blank: false, unique: true
        probeInterval min: 30, max: Integer.MAX_VALUE
    }
}
