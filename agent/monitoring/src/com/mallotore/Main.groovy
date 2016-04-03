package com.mallotore

import com.mallotore.monitoring.jmx.JmxBeanExporter

class Main {
    
    public static void main(String[] args) throws Exception {
        
        new JmxBeanExporter().export()
        
        while(true){
           Thread.sleep(Long.MAX_VALUE); 
        }
    }
}

