package com.mallotore.monitoring.jmx.bean

import com.mallotore.monitoring.jmx.dto.*

import org.hyperic.sigar.SigarProxy
import org.hyperic.sigar.SigarNotImplementedException
import org.hyperic.sigar.util.PrintfFormat
import java.text.SimpleDateFormat
import java.util.Date
import org.hyperic.sigar.SigarException
import org.hyperic.sigar.Sigar

class UptimeInfo {

    private final Sigar sigar
    public UptimeStats stats
    
    def UptimeInfo(){
        stats = []
        sigar = new Sigar()
    }

    UptimeStats getStats(){
        refreshInformation()

        return stats
    }

    void refreshInformation(){
        double uptime = sigar.getUptime().getUptime()
        stats = new UptimeStats(uptime: formatUptime(uptime),
                            loadAverage: loadAverage())
    }

    private loadAverage(){
        double[] avg = sigar.getLoadAverage()
        try {
            def loadAvg
            loadAvg[0] = new Double(avg[0])
            loadAvg[1] = new Double(avg[1])
            loadAvg[2] = new Double(avg[2])
            return loadAvg
        }catch(all){
             return "(load average unknown)"
        }
    }

    private static String formatUptime(double uptime) {
        String retval = "";

        int days = (int)uptime / (60*60*24);
        int minutes, hours;

        if (days != 0) {
            retval += days + " " + ((days > 1) ? "days" : "day") + ", "
        }

        minutes = (int)uptime / 60;
        hours = minutes / 60;
        hours %= 24;
        minutes %= 60;

        if (hours != 0) {
            retval += hours + ":" + minutes;
        }
        else {
            retval += minutes + " min";
        }

        return retval;
    }
}