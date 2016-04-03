package com.mallotore.configuration

import groovy.transform.Immutable

@Immutable(copyWith = true)
class ServerConfiguration {
    String id
    String name
    String ip
    String port
    String service
}