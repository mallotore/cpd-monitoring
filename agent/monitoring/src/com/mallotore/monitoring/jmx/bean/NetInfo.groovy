package com.mallotore.monitoring.jmx.bean

import com.mallotore.monitoring.jmx.dto.*

import org.hyperic.sigar.NetInterfaceConfig
import org.hyperic.sigar.SigarException
import org.hyperic.sigar.Sigar

class NetInfo {

    private final Sigar sigar
    public NetStats stats
    
    def NetInfo(){
        stats = []
        sigar = new Sigar()
    }

    NetStats getStats(){
        refreshInformation()

        return stats
    }

    void refreshInformation(){
        NetInterfaceConfig config = this.sigar.getNetInterfaceConfig(null)
        org.hyperic.sigar.NetInfo info = this.sigar.getNetInfo();
        stats = new NetStats(primaryInterface: config.getName(),
                                primaryIpAddress: config.getAddress(),
                                primaryMacAddress: config.getHwaddr(),
                                primaryNetMAsk: config.getNetmask(),
                                hostName: info.getHostName(),
                                domainName: info.getDomainName(),
                                defaultGateway: info.getDefaultGateway(),
                                primaryDns: info.getPrimaryDns(),
                                secondaryDns: info.getSecondaryDns())
    }
}