package mc03.network;

import mc03.Constants;
import mc03.controller.LoginController;
import mc03.model.Machine;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private static Client instance = null;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private LoginController loginController;
    private static int portNum;

    public static void newInstance(String siteLocation) {
        switch (siteLocation) {
            case Constants.CENTRAL: portNum = Constants.PORT_CENTRAL; break;
            case Constants.MARINDUQUE: portNum = Constants.PORT_MARINDUQUE; break;
            case Constants.PALAWAN: portNum = Constants.PORT_PALAWAN; break;
        }

        instance = new Client();
    }

    public static Client getInstance() {
        return instance;
    }

    private Client() {
        executorService.execute(new ClientMessageListener());
        System.out.println("Client.java: Executed ClientMessageListener.java");
    }

    public static void sendMessage(String message, String ipAddress) {
        try {
            System.out.println("sendMessage() @ Client.java: Sending " + message + " to " + ipAddress);
            byte[] sendData;

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(ipAddress);

            sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Constants.PORT_SERVER);

            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    private class ClientMessageListener implements Runnable {
        private DatagramSocket datagramSocket;
        private byte[] receiveData, sendData;
        private boolean isRunning = false;

        public ClientMessageListener() {
            try {
                datagramSocket = new DatagramSocket(portNum);
                System.out.println("ClientMessageListener has started @ " + InetAddress.getLocalHost().getHostAddress());
                isRunning = true;
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            DatagramPacket datagramPacket;
            String receivedMessage;
            int connectedMachinesCounter = 1;

            while (true) {
                receiveData = new byte[Constants.BUFFER_SIZE];
                datagramPacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    datagramSocket.receive(datagramPacket);
                    receivedMessage = new String(datagramPacket.getData()).trim();
                    String[] tokens = receivedMessage.split("~");

                    switch (tokens[0]) {
                        case Constants.MSG_CONNECT:
                            if (tokens[1].equals(Constants.MSG_OK)) {
                                System.out.println("ClientMessageListener.java: The server has accepted my connection!");
                                loginController.updateConnectionList(0, "", datagramPacket.getAddress().getHostAddress());
                            }
                            break;
                        case Constants.MSG_NEW_DUDE:
                            if (tokens[1].equals(Constants.CENTRAL)) {
                                System.out.println("ClientMessageListener.java: A new node has connected to the server I'm in!");
                                loginController.updateConnectionList(connectedMachinesCounter, tokens[1], tokens[2]);
                            } else if (tokens[1].equals(Constants.MARINDUQUE)) {
                                System.out.println("ClientMessageListener.java: A new node has connected to the server I'm in!");
                                loginController.updateConnectionList(connectedMachinesCounter, tokens[1], tokens[2]);
                            } else if (tokens[1].equals(Constants.PALAWAN)) {
                                System.out.println("ClientMessageListener.java: A new node has connected to the server I'm in!");
                                loginController.updateConnectionList(connectedMachinesCounter, tokens[1], tokens[2]);
                            }
                            connectedMachinesCounter++;
                            break;
                        default: break;
                    }

                    System.out.println("ClientMessageListener.java: Received from " + datagramPacket.getAddress().getHostAddress() + ": " + receivedMessage.trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
