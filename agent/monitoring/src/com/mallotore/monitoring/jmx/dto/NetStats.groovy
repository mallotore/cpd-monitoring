package com.mallotore.monitoring.jmx.dto

class NetStats implements Serializable {
    String primaryInterface
    String primaryIpAddress
    String primaryMacAddress
    String primaryNetMAsk
    String hostName
    String domainName
    String defaultGatewayInterface
    String defaultGateway
    String primaryDns
    String secondaryDns
}