package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class ServerProbeRequest {
    String ip
    String port
}