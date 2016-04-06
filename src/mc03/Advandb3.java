/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class Advandb3 {

    /**
     * @param args the command line arguments
     */
    

    public static void main(String[] args) throws SocketException {
        // TODO code application logic here
        
//        Advandb3 test = new Advandb3(9876, 1024, 1024);
//        MyThread creation = new MyThread();
        
        
        
        Thread catcher = new Thread(new reciever(9876, 1024, 1024));
        catcher.start();
        sender man = new sender();

            man.send("PM :caloy");
            man.send("daadn");
            man.send("sendagain");
        
    }

    
    
}

    
    
   
    
    
    
    