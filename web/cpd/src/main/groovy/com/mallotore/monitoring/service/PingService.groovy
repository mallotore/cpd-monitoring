package com.mallotore.monitoring.service

import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException

public class PingService
{
    static boolean pingToHost(ip){
    	
        try {
	      InetAddress inet = InetAddress.getByName(ip)
	      return inet.isReachable(5000)

	    } catch ( Exception e ) {
	      return false;
	    }
    }
}