package mc03.network;

import mc03.Constants;
import mc03.model.Machine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerMessageSender {

    public static void sendMessage(String message, Machine machine) {
        try {
            System.out.println("sendMessage() @ ServerMessageSender.java: Sending \'" + message + "\' to " + machine.getMachineLocation() + "@" + machine.getIpAddress());
            byte[] sendData;

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(machine.getIpAddress());

            sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, machine.getPortNum());

            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
