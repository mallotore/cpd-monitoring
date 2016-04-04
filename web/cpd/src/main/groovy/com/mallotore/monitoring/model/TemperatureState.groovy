package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class TemperatureState {
	String _id
	//todo: should be float
	String temperature
	Date creationDate
}