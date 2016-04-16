package com.mallotore.monitoring.jmx.bean

import com.mallotore.monitoring.jmx.dto.*
import org.hyperic.sigar.CpuPerc
import org.hyperic.sigar.Sigar
import org.hyperic.sigar.SigarLoader
import org.hyperic.sigar.SigarException
import org.hyperic.sigar.CpuInfo

class CpuInfo {
    
    private final Sigar sigar
    private final int NOT_DESIRED = -1
    public CpuStats stats
    
    def CpuInfo(){
        stats = []
        sigar = new Sigar()
    }

    CpuStats getStats(){
        refreshInformation()

        return stats
    }
    
    void refreshInformation(){
        def infos = this.sigar.getCpuInfoList()
        def cpus = this.sigar.getCpuPercList()
        def info = infos[0]
        def isValidData = isValidData(info.getTotalCores(), info.getTotalSockets(), info.getCoresPerSocket())
        def cpusPercentages = cpus.collect{ cpu ->
            gatherCpuPercentage(cpu)
        }
        stats = new CpuStats(cacheSize: Sigar.FIELD_NOTIMPL ? info.getCacheSize() : NOT_DESIRED,
        				vendor: info.getVendor(),
        				model: info.getModel(),
	        			mhz: info.getMhz(),
	        			totalCpus: isValidData ? info.getTotalCores() : NOT_DESIRED,
	        			physicalCpus: isValidData ? info.getTotalSockets() : NOT_DESIRED,
	        			coresPerCpu: isValidData ? info.getCoresPerSocket() : NOT_DESIRED,
	        			cpusPercentages: cpusPercentages, 
	        			totals: gatherCpuPercentage(this.sigar.getCpuPerc()))
    }

    private isValidData(totalCores, totalSockets, coresPerSocket){
    	totalCores != totalSockets || coresPerSocket > totalCores
    }

    private gatherCpuPercentage(CpuPerc cpu){
    	new CpuPercentage([
                    userTime: CpuPerc.format(cpu.getUser()),
				    sysTime: CpuPerc.format(cpu.getSys()),
				    idleTime: CpuPerc.format(cpu.getIdle()),
				    waitTime: CpuPerc.format(cpu.getWait()),
				    niceTime: CpuPerc.format(cpu.getNice()),
				    combined: CpuPerc.format(cpu.getCombined()),
				    irqTime: CpuPerc.format(cpu.getIrq()),
				    softIrqTime: SigarLoader.IS_LINUX ? CpuPerc.format(cpu.getSoftIrq()) : NOT_DESIRED,
				    stolenTime: SigarLoader.IS_LINUX ? CpuPerc.format(cpu.getStolen()) : NOT_DESIRED
            ])
    }
}