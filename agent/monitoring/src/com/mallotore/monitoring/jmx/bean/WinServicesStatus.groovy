package com.mallotore.monitoring.jmx.bean

import com.mallotore.monitoring.jmx.dto.WinService
import java.util.Arrays;
import java.util.List;
import org.hyperic.sigar.win32.Service;
import org.hyperic.sigar.win32.Win32Exception;

class WinServicesStatus {
    
    List<WinService> servicesStatus;
    
    def WinServicesStatus(){
        servicesStatus = []
    }
    
    List<WinService> getAllServicesStatus(){
        if(!servicesStatus) refreshInformation()
        return servicesStatus
    }
    
    void refreshInformation(){
        if(org.hyperic.sigar.OperatingSystem.IS_WIN32){
            def services = Service.getServiceNames()
            servicesStatus = services.collect{
                getServiceStatus(it)
            }
        }
    }
    
    private WinService getServiceStatus(String name){
        try{
            def service = new Service(name)
            def winService = new WinService(name: name, 
                                            status: service.getStatusString())
            service.close()
            
            return winService
        }catch(Exception ex){
            new WinService(name: name, status: 'access denied')
        }
    }
}

