/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc03;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class reciever implements Runnable {


    DatagramSocket serverSocket;
    byte[] receiveData = new byte[1024];  
    byte[] sendData = new byte[1024];
    
    public reciever(int portnumber, int recieverbuffersize, int sentDatasize){
            
        try {
            serverSocket = new DatagramSocket(portnumber);
        } catch (SocketException ex) {
            Logger.getLogger(Advandb3.class.getName()).log(Level.SEVERE, null, ex);
        }
        receiveData = new byte[recieverbuffersize];
        sendData = new byte[sentDatasize];
    }
    public void run() {
      
        
        try {
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                System.out.println("OPEN");
                serverSocket.receive(receivePacket);
                
                String sentence = new String(receivePacket.getData());
                System.out.println(receivePacket.getAddress().toString().substring(1)+" : " + sentence.trim());
               // gui.setIpAddress(receivePacket.getAddress().toString().substring(1));
              //  gui.listenButton.setText("reply");
               // gui.handler.client.setadress(receivePacket.getAddress().toString().substring(1));
               
                  System.out.println("RECEIVED: FROM" + receivePacket.getAddress());
               // rhandler.request(sentence.trim(),receivePacket.getAddress().toString().substring(1));

                //System.out.println(rhandler);
            }
        } catch (IOException ex) {
            Logger.getLogger(Advandb3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
