package mc03.network;

import mc03.Constants;
import mc03.model.Machine;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

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

	public static void SendArrayList(ArrayList<String> arraylist, String ipaddress) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);

		for (String element : arraylist) {
			// System.out.println("msg:" + element);
			if (element != null)
				try {
					out.writeUTF(element);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		byte[] bytes = baos.toByteArray();

//		toprint(bytes);

		DatagramSocket clientSocket;
		try {
			clientSocket = new DatagramSocket();

			// String address = "localhost";
			InetAddress IPAddress = InetAddress.getByName(ipaddress);

			byte[] sendData = new byte[1024];

			// sentence=sentence+x;
			sendData = bytes;
			System.out.println("LEEEEENGTH!" + sendData.length);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

			clientSocket.send(sendPacket);

			clientSocket.close();
		} catch (Exception ex) {
			System.out.println("Error:5555 " + ex);
			ex.printStackTrace();
		}

	}
}
