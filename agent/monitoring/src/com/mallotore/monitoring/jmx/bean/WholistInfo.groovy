package com.mallotore.monitoring.jmx.bean

import com.mallotore.monitoring.jmx.dto.*
import java.util.Date
import org.hyperic.sigar.Who
import org.hyperic.sigar.SigarException
import org.hyperic.sigar.Sigar
import java.text.SimpleDateFormat

class WholistInfo {

    private final Sigar sigar
    public List<WholistStats> stats
    
    def WholistInfo(){
        stats = []
        sigar = new Sigar()
    }

    List<WholistStats> getStats(){
        refreshInformation()

        return stats
    }

    void refreshInformation(){
        def who = sigar.getWhoList()
        stats = []
        for (int i=0; i<who.length; i++) {
            String host = who[i].getHost()
            if (host.length() != 0) {
                host = "(" + host + ")"
            }
            stats << new WholistStats(user: who[i].getUser(),
                            device: who[i].getDevice(),
                            time: getTime(who),
                            host: host)
        }
        stats 
    }

    private String getTime(who) {
        try{
            def time = who[i]?.getTime() * 1000
            if (time == 0) {
            return "unknown";
            }
            String fmt = "MMM dd HH:mm";
            return new SimpleDateFormat(fmt).format(new Date(time));
            }
        catch(all){
            return "unknown"
        }
    }
}