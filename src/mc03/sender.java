/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc03;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author miguel
 */
public class sender {
    String address="localhost";
    public sender(){
    
    	
    }
    

    public void setadress(String adrez){
    	address = adrez;
    	
    }
    
    public void send(String patatas){
        try {
            char x = 0;
            byte[] sendData = new byte[1024];

            System.out.println("SENDING"+patatas);
            
            DatagramSocket clientSocket = new DatagramSocket();
            
           //address="10.100.217.172";
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
    
    
    
    public void DoQuery(String query) {
        
        
        
        try {
  
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hpq", "root", "DLSU");
            Statement stmt = con.createStatement();

            long startTime = System.currentTimeMillis();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmetadata = rs.getMetaData();
            int columns = rsmetadata.getColumnCount();

            DefaultTableModel dtm = new DefaultTableModel();
            Vector columns_name = new Vector();
            Vector data_rows = new Vector();

            for (int i = 1; i < columns + 1; i++) {
                columns_name.addElement(rsmetadata.getColumnName(i));
            }
            dtm.setColumnIdentifiers(columns_name);

            
            
            
           //storing it 
    ArrayList<String> arrayList = new ArrayList<String>(); 
    while (rs.next()) {              
        int i = 1;
        while(i <= columns) {
            arrayList.add(rs.getString(i++));
        }
//        System.out.println(rs.getString(1));
//        System.out.println(rs.getString(2));
//        System.out.println(rs.getString(3));                    
//        System.out.println(rs.getString(4));
}
            

 
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
       

            //convert to bytearray
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baos);

            for (String element : arrayList) {
                //System.out.println("msg:" + element);
                if(element!=null)
                out.writeUTF(element);
            }
            byte[] bytes = baos.toByteArray();
        
        
            
            toprint(bytes);
            
            
         
            
                        DatagramSocket clientSocket;
        try {
            clientSocket = new DatagramSocket();
         
            String address = "localhost";
            InetAddress IPAddress = InetAddress.getByName(address);
            
            byte[] sendData = new byte[1024];
 

            //  sentence=sentence+x;
            sendData = bytes;
            System.out.println("LEEEEENGTH!"+sendData.length);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

                clientSocket.send(sendPacket);


            clientSocket.close();
    }catch (Exception ex) {
            System.out.println("Error:5555 " + ex);
            ex.printStackTrace();
        }
            
            
            
            
            
    }catch (Exception ex) {
            System.out.println("Error:5555 " + ex);
            ex.printStackTrace();
        }
        
        
     
        //send bytes to place
            

        
        
        
        
    }
public String resultData(String query){
	
	String qData="";
	try{

    Class.forName("com.mysql.jdbc.Driver");
    Connection con = con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hpq", "root", "DLSU");
    Statement stmt = con.createStatement();
    long startTime = System.currentTimeMillis();
    ResultSet rs = stmt.executeQuery(query);
    ResultSetMetaData rsmetadata = rs.getMetaData();
    int columns = rsmetadata.getColumnCount();

    qData=""+ columns+" / ";

    for (int i = 1; i < columns + 1; i++) {
        qData=qData+rsmetadata.getColumnName(i)+" : ";
    }

	}catch (Exception ex) {
        System.out.println("Error:5555 " + ex);
        ex.printStackTrace();
    }
	return qData;
}
    
    
    
    
    
    
public void toprint(byte[] bite){


ByteArrayInputStream bais = new ByteArrayInputStream(bite);
                DataInputStream in = new DataInputStream(bais);
        try {
            while (in.available() > 0) {
                String element = in.readUTF();
                System.out.println("AAA:"+element);
            }       } catch (IOException ex) {
            Logger.getLogger(sender.class.getName()).log(Level.SEVERE, null, ex);
        }


}






}
