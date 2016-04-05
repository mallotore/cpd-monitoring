package com.mallotore.monitoring.jmx.bean

import org.hyperic.sigar.ProcMem
import org.hyperic.sigar.Sigar
import org.hyperic.sigar.ptql.ProcessFinder

class ServicesStatus {
    
    static final APACHE2 = '^(https?d.*|[Aa]pache2?)$'
    static final IIS = "W3SVC"
    static final MYSQL = "mysqld"
    static final TOMCAT = "org.apache.catalina"
    static final NOT_FOUND = -1
    
    private final sigar
    private final finder

    long appache2ProccesId
    long appacheTomcatProccesId
    long mysqlProccesId
    long iisProccesId

    def ServicesStatus(){
        sigar = new Sigar()
        finder = new ProcessFinder(sigar)
    }
    
    long getApache2ProccessId(){
        appache2ProccesId = tryToFindProcess(){
            finder.findSingleProcess("State.Name.re=${APACHE2}")
        }
        appache2ProccesId
    }
    
    long getIISProccessId(){
        iisProccesId = NOT_FOUND
        if(org.hyperic.sigar.OperatingSystem.IS_WIN32){
           iisProccesId = tryToFindProcess(){
                finder.findSingleProcess("Pid.Service.eq=${IIS}")    
            }
        }
        iisProccesId
    }
    
    long getMysqlProccessId(){
        mysqlProccesId = tryToFindProcess(){
            finder.findSingleProcess("State.Name.eq=${MYSQL}")   
        }
        mysqlProccesId
    }

    long getApacheTomcatProccessId(){
        appacheTomcatProccesId = tryToFindProcess(){
            finder.findSingleProcess("State.Name.sw=java,Args.*.ct=${TOMCAT}")    
        }
        appacheTomcatProccesId
    }

    private tryToFindProcess(Closure closure){
        try{
            return closure()
        }catch(org.hyperic.sigar.SigarException ex){
            return NOT_FOUND
        }
    }
}


