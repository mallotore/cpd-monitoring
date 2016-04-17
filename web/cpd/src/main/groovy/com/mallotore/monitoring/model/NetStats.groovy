package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class NetStats {
    String primaryInterface
    String primaryIpAddress
    String primaryMacAddress
    String primaryNetMAsk
    String hostName
    String domainName
    String defaultGateway
    String primaryDns
    String secondaryDns
}