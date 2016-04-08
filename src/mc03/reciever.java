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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mc03.controller.FirstQueryController;
import mc03.controller.LoginController;
import mc03.controller.ResultsWindowController;
import mc03.model.Container;

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
	ResultsWindowController  controller;
	List<String> results;
	sender man ;
	int numcol;
	String[] colnames;
	public reciever(int portnumber, int recieverbuffersize, int sentDatasize) {
		man=new sender();
		Container.getInstance().getDatabaseName();
			requestHandler = new NodeRequestHandler("Marindique");
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

//	public ResultsWindowController getController() {
//		return controller;
//	}
//
//	public void setController(ResultsWindowController controller) {
//		this.controller = controller;
//	}

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
	
	public void openResultsWindow(List<String> results,String[] colnames,int numcol) throws IOException{
		System.out.println("1111");
		FXMLLoader loader = new FXMLLoader(getClass()
				.getResource("view/ResultsWindow.fxml"));
		
		
		loader.setLocation(Main.class.getResource("view/ResultsWindow.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultsWindowController controller =
				loader.<ResultsWindowController>getController();
		controller.initializeData(results,colnames,numcol);  
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Resu11111lts");
		stage.show();

				
			}

	public void run() {

		try {
		results=new ArrayList();
			while (true) {
				String sentence = "";
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

				//serverSocket.setSoTimeout(5000);
				System.out.println("OPEN to type:"+this.type);
				serverSocket.receive(receivePacket);

				if (this.type == 1) {
					 sentence = new String(receivePacket.getData());
					System.out.println("RECEIVED: FROM" + receivePacket.getAddress());
					System.out.println(receivePacket.getAddress().toString().substring(1) + " -:- " + sentence.trim());

					System.out.println("request___" + this.getrequest(sentence.trim()));
					System.out.println("messag____" + this.getname(sentence.trim()));
					
					Container.getInstance().setblah(this.getname(sentence.trim()) ) ;
					
					

					if (this.getrequest(sentence.trim()).equals("DATA")) {
						//DOM: this.getname(sentence.trim()) would give you the column number and name
						//controller.initializeData();
						System.out.println("put in list: "+ this.getname(sentence.trim()));
						String qData=this.getname(sentence.trim());
						String[] parts = qData.split("/");
						 numcol=Integer.parseInt(parts[0].trim());
						 colnames =  parts[1].split(":");
						System.out.println("number:"+numcol+" number"+colnames[0]+colnames[1]);
						
						this.type=0;
						
						
						
						
						}

						
						
					
					
					
					//requestHandler.interpret(sentence);

					
					
				}
				
				if (this.type == 0) {
					serverSocket.receive(receivePacket);
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
						
						//insert arraylist here.
						System.out.println("TTTT:" + element);
						results.add(element);
					}
					
					//
				//	System.out.println("looP?");
					 Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
 
		try {
			openResultsWindow(results, colnames, numcol);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("ddd");
	

						}         
					});
					

					this.type=1;
				}

			}
		} catch (IOException ex) {
			Logger.getLogger(Advandb3.class.getName()).log(Level.SEVERE, null, ex);
		}
        
        
        
        
    }
    
}
