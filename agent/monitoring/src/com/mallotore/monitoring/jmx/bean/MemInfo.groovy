package com.mallotore.monitoring.jmx.bean

import org.hyperic.sigar.Mem
import org.hyperic.sigar.Swap
import org.hyperic.sigar.SigarException
import com.mallotore.monitoring.jmx.dto.*
import org.hyperic.sigar.Sigar

class MemInfo {

    private final Sigar sigar
    public MemStats stats
    
    def MemInfo(){
        stats = []
        sigar = new Sigar()
    }

    MemStats getStats(){
        refreshInformation()

        return stats
    }
    
    void refreshInformation(){
        Mem mem   = this.sigar.getMem()
        Swap swap = this.sigar.getSwap()
        stats = new MemStats(
                        memTotal: mem.getTotal(),
                        memUsed: mem.getUsed(),
                        memFree: mem.getFree(),
                        swapTotal: swap.getTotal(),
                        swapUsed: swap.getUsed(),
                        swapFree: swap.getFree())
    }
}