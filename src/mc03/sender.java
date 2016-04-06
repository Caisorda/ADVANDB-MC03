/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc03;

import java.io.IOException;
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
public class sender {
    
    public sender(){
    
    }
    
    public void send(String patatas) throws SocketException {
        try {
            char x = 0;
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            System.out.println("SENDING"+patatas);
            
            DatagramSocket clientSocket = new DatagramSocket();
            String address = "localhost";
            //address="10.100.210.64";
            InetAddress IPAddress = InetAddress.getByName(address);

            String sentence = patatas;

            //  sentence=sentence+x;
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

                clientSocket.send(sendPacket);


            clientSocket.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(sender.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
    
    
    
    
}
