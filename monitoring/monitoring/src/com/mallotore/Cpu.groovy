package com.mallotore

import org.hyperic.sigar.CpuPerc
import org.hyperic.sigar.Sigar

class Cpu {

    def stats(){
        Sigar sigar = new Sigar();

        def cpu = sigar.getCpu();
        System.out.println("idle: " + cpu.getIdle());//get overall CPU idle
        System.out.println("irq: " + cpu.getIrq());
        System.out.println("nice: " + cpu.getNice());
        System.out.println("soft irq: " + cpu.getSoftIrq());
        System.out.println("stolen: " + cpu.getStolen());
        System.out.println("sys: " + cpu.getSys());
        System.out.println("total: " + cpu.getTotal());
        System.out.println("user: " + cpu.getUser());
        System.out.println();


        def perc = sigar.getCpuPerc();
        System.out.println("overall CPU usage");
        System.out.println("system idle: " + perc.getIdle());//get current CPU idle rate
        System.out.println("conbined: "+ perc.getCombined());//get current CPU usage


        def cpuPercs = sigar.getCpuPercList();
        System.out.println("each CPU usage");
        for (CpuPerc cpuPerc : cpuPercs) {
            System.out.println("system idle: " + cpuPerc.getIdle());//get current CPU idle rate
            System.out.println("conbined: "+ cpuPerc.getCombined());//get current usage
            System.out.println();
        }
    }
}

