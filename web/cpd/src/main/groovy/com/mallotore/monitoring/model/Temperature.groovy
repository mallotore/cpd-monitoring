package com.mallotore.monitoring.model

class Temperature {
    private _id
    private creationDate = new Date()
    private temperature

    def state(){
        new TemperatureState(temperature: temperature,
                            creationDate: creationDate)
    }
}