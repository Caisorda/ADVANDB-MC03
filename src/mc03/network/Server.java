package mc03.network;

import mc03.Constants;
import mc03.CoordinatorRequestHandler;
import mc03.LockManager;
import mc03.QueryHandler;
import mc03.RecoveryHandler;
import mc03.TransactionLogger;
import mc03.controller.LoginController;
import mc03.model.Container;
import mc03.model.DBConnection;
import mc03.model.Machine;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {


    private static Server serverInstance = null;
    private final List<Machine> machines = new ArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static boolean isRunning = false;
    private LockManager manager = LockManager.getInstance();
    private QueryHandler queryHandler = QueryHandler.getInstance();
    private TransactionLogger tranLogger = TransactionLogger.getInstance();
    private static HashMap<String,Integer> goCount = new HashMap();
    private RecoveryHandler recoveryHandler = RecoveryHandler.getInstance();
    
    public static void main(String[] args) {
        Server.getInstance().initializeComponents();
    }

    public List<Machine> getMachines() {
    	return machines;
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
    
    public ArrayList<Machine> getMachines() {
    	return (ArrayList<Machine>) machines;
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
                	System.out.println("in while loop");
                    datagramSocket.receive(datagramPacket);
                	System.out.println("received");
                    receivedMessage = new String(datagramPacket.getData()).trim();
                    String[] tokens = receivedMessage.split("~");
					System.out.println(receivedMessage);

					switch (tokens[0]) {
					case Constants.MSG_CONNECT:
						Machine newMachine = new Machine(tokens[1], datagramPacket.getAddress().getHostAddress());
						addMachine(newMachine);
						ServerMessageSender.sendMessage(Constants.MSG_CONNECT + "~" + Constants.MSG_OK, newMachine);

						// Update other machines about this new guy
						for (Machine m : machines) {
							if (m != newMachine) {
								ServerMessageSender.sendMessage(
										Constants.MSG_NEW_DUDE + "~" + m.getMachineLocation() + "~" + m.getIpAddress(),
										newMachine);
								ServerMessageSender.sendMessage(Constants.MSG_NEW_DUDE + "~"
										+ newMachine.getMachineLocation() + "~" + newMachine.getIpAddress(), m);
							}
						}
						break;
					case "DATA":
						String qData = getName(tokens[1].trim());
						String[] parts = qData.split("/");
						int numCol = Integer.parseInt(parts[0].trim());
						String[] columnNames = parts[1].split(":");
						
						datagramSocket.receive(datagramPacket);
						System.out.println("RECEIVED: FROM" + datagramPacket.getAddress());

						System.out.println("MOOO!" + datagramPacket.getLength());
						this.receiveData = this.trim(this.receiveData, datagramPacket.getLength());

						byte[] tempData = new byte[datagramPacket.getLength()];
						for (int i = 0; i < datagramPacket.getLength(); i++) {
							tempData[i] = receiveData[i];
						}

						ByteArrayInputStream bais = new ByteArrayInputStream(tempData);
						DataInputStream in = new DataInputStream(bais);

						while (in.available() > 0) {
							String element = in.readUTF();
							
							//insert arraylist here.
							System.out.println("TTTT:" + element);
							// results.add(element);
						}
						break;
					case "QUERY":
						if (tokens[2].equals("read")) {
							for (int i = 4; i < tokens.hashCode(); i++) {
								manager.readLock(Integer.parseInt(tokens[i]), tokens[1]);
							}
						} else if (tokens[2].equals("write")) {
							for (int i = 4; i < tokens.hashCode(); i++) {
								manager.writeLock(Integer.parseInt(tokens[i]), tokens[1]);
							}
						}
						tranLogger.logChanges(tokens[0] + " " + tokens[1] + " " + tokens[3], receivedMessage);
						for (Machine m : machines) {
							if (!Container.getInstance().getLocationName().equals(m.getMachineLocation())) {
								ServerMessageSender.sendMessage(receivedMessage, m);
								ServerMessageSender.sendMessage("READY~" + tokens[1], m);
							}
						}
						goCount.put(tokens[1], 0);
						new Thread(){
							public void run(){
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(goCount.get(tokens[1]) == 3){
									goCount.remove(tokens[1]);
								}else{
									for (Machine m : machines) {
										if (!Container.getInstance().getLocationName().equals(m.getMachineLocation())) {
											ServerMessageSender.sendMessage(Constants.MSG_GLOBAL_ABORT + "~" + tokens[1], m);
										}
									}
								}
							}
						}.start();
						break;
					case "RECON":
						ServerMessageSender.SendArrayList(tranLogger.getLogs(tranLogger.parseDate(tokens[1])), tokens[2]);
						break;
					case "GO":
						if (goCount.containsKey(tokens[1])) {
							int go = goCount.get(tokens[1]) + 1;
							if(go == 3){
								for (Machine m : machines) {
									if (!Container.getInstance().getLocationName().equals(m.getMachineLocation())) {
										ServerMessageSender.sendMessage(Constants.MSG_GLOBAL_COMMIT + "~" + tokens[1], m);
									}
								}
								goCount.remove(tokens[1]);
							}
							else goCount.put(tokens[1], go);
						}
						
						break;
					case "STOP":
						goCount.remove(tokens[1]);
						for (Machine m : machines) {
							if (!Container.getInstance().getLocationName().equals(m.getMachineLocation())) {
								ServerMessageSender.sendMessage(Constants.MSG_GLOBAL_ABORT + "~" + tokens[1], m);
							}
						}
						tranLogger.logChanges("ABORT " + tokens[1], receivedMessage);
						break;
					case "LOG":
						
						byte[] receiveData = new byte[1024];
						DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
						receiveData = this.trim(receiveData, receivePacket.getLength());

						byte[] logData = new byte[receivePacket.getLength()];
						for (int i = 0; i < receivePacket.getLength(); i++) {
							logData[i] = receiveData[i];
						}

						ByteArrayInputStream logis = new ByteArrayInputStream(logData);
						DataInputStream dis = new DataInputStream(logis);
						ArrayList<String> logs = new ArrayList();
						while (dis.available() > 0) {
							String element = dis.readUTF();
							logs.add(element);
							//insert arraylist here.
							System.out.println("TTTT:" + element);
						}
						RecoveryHandler.getInstance().recoverState(logs);
						tranLogger.logChanges("Received log from " + tokens[1], receivedMessage);
						break;
					case "ISOLVL":
						int isolationLevel = Integer.parseInt(tokens[1]);
						queryHandler.setIsolationLevel(isolationLevel, tokens[2]);
						for (Machine m : machines) {
							if (!Container.getInstance().getLocationName().equals(m.getMachineLocation())) {
								ServerMessageSender.sendMessage(receivedMessage, m);
							}
						}
						tranLogger.logChanges("Isolation level set to " + tokens[1], receivedMessage);
						break;
					case "START":
						DBConnection.getInstance();
						queryHandler.addTransaction(tokens[1], Container.getInstance().getDatabaseName());
						for (Machine m : machines) {
							if (!Container.getInstance().getLocationName().equals(m.getMachineLocation())) {
								ServerMessageSender.sendMessage(receivedMessage, m);
							}
						}
						tranLogger.logChanges("New Transaction: " + tokens[1], receivedMessage);
						break;
//					case "DATA FINISH":
//						if (tokens[2].equals("read")) {
//							queryHandler.readQuery(tokens[1], tokens[3]);
//						} else if (tokens[2].equals("write")) {
//							queryHandler.writeQuery(tokens[1], tokens[3]);
//						}
//						manager.unLock(tokens[1]);
//						break;
					default:
						break;
					}

                    System.out.println("ServerListener.java: Received from " + datagramPacket.getAddress().getHostAddress() + ": " + receivedMessage.trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    	public String getName(String req) {
    		String res = "ERROR";
    		String[] arr;
    		arr = req.split("~");
    		res = arr[1];
    		return res;
    	}

        public byte[] trim(byte[] bytes, int i) {

    		return Arrays.copyOf(bytes, i + 1);
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
