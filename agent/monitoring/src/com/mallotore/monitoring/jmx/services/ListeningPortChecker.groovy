package com.mallotore.monitoring.jmx.service

class ListeningPortChecker {

    static boolean serverListening(String host, int port){
        Socket socket;
		try{
			socket = new Socket(host, port);
            socket.close()
			return true
		}
		catch (Exception e ){
            try{
                if(socket != null){
                    socket.close()    
                }
            }catch(Exception){}
			return false
		}
	}
}