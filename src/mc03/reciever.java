/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc03;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
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
	public int type = 1;
	RequestHandler requestHandler;

	public reciever(int portnumber, int recieverbuffersize, int sentDatasize) {

		try {
			serverSocket = new DatagramSocket(portnumber);
		} catch (SocketException ex) {
			Logger.getLogger(Advandb3.class.getName()).log(Level.SEVERE, null, ex);
		}
		receiveData = new byte[recieverbuffersize];
		sendData = new byte[sentDatasize];
	}

//	public reciever(int portnumber, int recieverbuffersize, int sentDatasize, RequestHandler reqHandler) {
//		try {
//			serverSocket = new DatagramSocket(portnumber);
//		} catch (SocketException ex) {
//			Logger.getLogger(Advandb3.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		receiveData = new byte[recieverbuffersize];
//		sendData = new byte[sentDatasize];
//		this.requestHandler = reqHandler; 
//	}
	
	public String getrequest(String req) {
		String res = "ERROR";
		String[] arr;
		arr = req.split(" ");
		res = arr[0];
		return res;
	}

	public String getname(String req) {
		String res = "ERROR";
		String[] arr;
		arr = req.split("~");
		res = arr[1];
		return res;
	}

	public byte[] trim(byte[] bytes, int i) {

		return Arrays.copyOf(bytes, i + 1);
	}

	public void run() {

		try {
			while (true) {
				String sentence = "";
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				System.out.println("OPEN");
				//serverSocket.setSoTimeout(5000);
				serverSocket.receive(receivePacket);

				if (this.type == 1) {
					 sentence = new String(receivePacket.getData());
					System.out.println("RECEIVED: FROM" + receivePacket.getAddress());
					System.out.println(receivePacket.getAddress().toString().substring(1) + " -:- " + sentence.trim());

					System.out.println("request___" + this.getrequest(sentence.trim()));
					System.out.println("messag____" + this.getname(sentence.trim()));
					if (this.getrequest(sentence.trim()).equals("DATA")) {
						//DOM: this.getname(sentence.trim()) would give you the column number and name
						}
					if (this.getrequest(sentence.trim()).equals("AGREE")) {	
						//setting the thing to receive the result set
						this.type = 0;
					}
						
						
					
					
					
					//requestHandler.interpret(sentence);

					
					
				} else if (this.type == 0) {

					System.out.println("RECEIVED: FROM" + receivePacket.getAddress());

					System.out.println("MOOO!" + receivePacket.getLength());
					this.receiveData = this.trim(this.receiveData, receivePacket.getLength());

					byte[] tempData = new byte[receivePacket.getLength()];
					for (int i = 0; i < receivePacket.getLength(); i++) {
						tempData[i] = receiveData[i];
					}

					ByteArrayInputStream bais = new ByteArrayInputStream(tempData);
					DataInputStream in = new DataInputStream(bais);

					while (in.available() > 0) {
						String element = in.readUTF();

						System.out.println("TTTT:" + element);

					}
					
					
					
					
					
					try{
						String handle[] = sentence.split("~");
						this.type = 1;
						//requestHandler.interpret("DATA FINISH~" + handle[1] + "~" + handle[2] 
							//					+ "~" + handle[3]);
					}catch(Exception e){
						e.printStackTrace();
					}
				}

			}
		} catch (IOException ex) {
			Logger.getLogger(Advandb3.class.getName()).log(Level.SEVERE, null, ex);
		}
        
        
        
        
    }
    
}
