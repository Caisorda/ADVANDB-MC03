package mc03;

import mc03.model.DBConnection;

public class NodeRequestHandler implements RequestHandler{
	
	String nodeSchema;
	
	public NodeRequestHandler(String nodeSchema){
		super();
		this.nodeSchema = nodeSchema;
	}
	
	public void interpret(String request){
		String handle[] = request.split("~");
		
		if (handle[0].equals("QUERY") || handle[0].equals("DATA")) {
			// run query
			// QUERY~<transaction ID>~<query type>~<query itself>~<columns
			// used>
			if (handle[2].equals("read")) {
				for (int i = 4; i < handle.hashCode(); i++) {
					manager.readLock(Integer.parseInt(handle[i]), handle[1]);
				}
			} else if (handle[2].equals("write")) {
				for (int i = 4; i < handle.hashCode(); i++) {
					manager.writeLock(Integer.parseInt(handle[i]), handle[1]);
				}
			}
			tranLogger.logChanges(handle[0] + " " + handle[1] + " " + handle[3], request);
		} else if (handle[0].equals("RECON")) {
			tranLogger.getLogs(tranLogger.parseDate(handle[1]));
			//send logs to node
		} else if (handle[0].equals("ABORT")) {
			queryHandler.abortTransaction(handle[1]);
			manager.unLock(handle[1]);
			tranLogger.logChanges("ABORT " + handle[1], request);
		} else if (handle[0].equals("COMMIT")) {
			queryHandler.commitTransaction(handle[1]);
			if (handle[2].equals("read")) {
				queryHandler.readQuery(handle[1], handle[3]);
			} else if (handle[2].equals("write")) {
				queryHandler.writeQuery(handle[1], handle[3]);
			}
			manager.unLock(handle[1]);
			tranLogger.logChanges("COMMIT " + handle[1], request);
		} else if (handle[0].equals("READY")) {
			//pop up window to ask user whether
//			tranLogger.logChanges("Voted to " + <put decision here> + "transaction: " + handle[1]); 
		} else if (handle[0].equals("LOG")) {
//			recoveryHandler.recoverState(logs);
			tranLogger.logChanges("Received log from " + handle[1], request); 
		} else if (handle[0].equals("ISOLVL")) {
			int isolationLevel = Integer.parseInt(handle[1]);
			queryHandler.setIsolationLevel(isolationLevel, handle[2]);
			tranLogger.logChanges("Isolation level set to " + handle[1], request);
		} else if (handle[0].equals("START")) {
			DBConnection.getInstance();
			queryHandler.addTransaction(handle[1], this.nodeSchema);
			tranLogger.logChanges("New Transaction: " + handle[1], request);
		} /*else if(handle[0].equals("DATA FINISH")){
			if (handle[2].equals("read")) {
				queryHandler.readQuery(handle[1], handle[3]);
			} else if (handle[2].equals("write")) {
				queryHandler.writeQuery(handle[1], handle[3]);
			}
			manager.unLock(handle[1]);
			tranLogger.logChanges("Result set received from transaction: " + handle[1], request);
		}*/
	}
}
