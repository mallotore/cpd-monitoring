package com.mallotore.configuration

import groovy.transform.Immutable

@Immutable(copyWith = true)
class ServerConfiguration {
    String id
    String name
    String ip
    int port
    int probeIntervalInSeconds
    boolean connectivityAlert
    int diskPercentageAlert
}