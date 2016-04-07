package mc03.network;

import mc03.Constants;
import mc03.controller.LoginController;
import mc03.model.Machine;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    private static Server serverInstance = null;
    private final List<Machine> machines = new ArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static boolean isRunning = false;

    public static void main(String[] args) {
        Server.getInstance().initializeComponents();
    }

    public static Server getInstance() {
        if (serverInstance == null) {
            serverInstance = new Server();
        }

        return serverInstance;
    }

    public void initializeComponents() {
        if (!isRunning)
            executorService.execute(new ServerListener());
    }

    public void addMachine(Machine machine) {
        if (!machines.contains(machine))
            machines.add(machine);
    }

    private class ServerListener implements Runnable {
        private DatagramSocket datagramSocket;
        private byte[] receiveData, sendData;

        @Override
        public void run() {
            DatagramPacket datagramPacket;
            String receivedMessage;

            while (true) {
                receiveData = new byte[Constants.BUFFER_SIZE];
                datagramPacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    datagramSocket.receive(datagramPacket);
                    receivedMessage = new String(datagramPacket.getData()).trim();
                    String[] tokens = receivedMessage.split("~");

                    switch(tokens[0]) {
                        case Constants.MSG_CONNECT:
                            Machine newMachine = new Machine(tokens[1], datagramPacket.getAddress().getHostAddress());
                            addMachine(newMachine);
                            ServerMessageSender.sendMessage(Constants.MSG_CONNECT + "~" + Constants.MSG_OK, newMachine);

                            // Update other machines about this new guy
                            for (Machine m : machines) {
                                if (m != newMachine) {
                                    ServerMessageSender.sendMessage(Constants.MSG_NEW_DUDE + "~" + m.getMachineLocation() + "~" + m.getIpAddress(), newMachine);
                                    ServerMessageSender.sendMessage(Constants.MSG_NEW_DUDE + "~" + newMachine.getMachineLocation() + "~" + newMachine.getIpAddress(), m);
                                }
                            }
                            break;
                        default: break;
                    }

                    System.out.println("ServerListener.java: Received from " + datagramPacket.getAddress().getHostAddress() + ": " + receivedMessage.trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public ServerListener() {
            try {
                datagramSocket = new DatagramSocket(Constants.PORT_SERVER);
                System.out.println("Server has started @ " + InetAddress.getLocalHost().getHostAddress());
                isRunning = true;
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

}
