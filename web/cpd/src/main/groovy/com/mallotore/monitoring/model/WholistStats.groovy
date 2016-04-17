package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class WholistStats {
    String user
    String device
    String time
    String host
}