package com.mallotore

class Main {
    
    public static void main(String[] args) throws Exception {
        new Monitor().processCommand(args);
        new Disk().CreateStats();
        new Cpu().stats();
    } 
}

