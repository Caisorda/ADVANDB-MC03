package mc03;

import mc03.model.DBConnection;

public class CoordinatorRequestHandler implements RequestHandler{

	sender send;
	
	public CoordinatorRequestHandler(){
		
	}
	
	@Override
	public void interpret(String request) {
		String handle[] = request.split("~");
		
		if(handle[0].equals("QUERY")){
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
			
		}else if(handle[0].equals("RECON")){
			//node reconnect
		}else if(handle[0].equals("GO")){
			//wait for everyone else to send go then send commit
		}else if(handle[0].equals("STOP")){
			//immeadiately send abort
		}else if(handle[0].equals("LOG")){
			//receive log record
			tranLogger.logChanges("Received log from " + handle[1], request); 
		}else if(handle[0].equals("ISOLVL")){
			int isolationLevel = Integer.parseInt(handle[1]);
			queryHandler.setIsolationLevel(isolationLevel, handle[2]);
			tranLogger.logChanges("Isolation level set to " + handle[1], request);
		} else if (handle[0].equals("START")) {
			DBConnection.getInstance();
			queryHandler.addTransaction(handle[1], "db_hpq");
			tranLogger.logChanges("New Transaction: " + handle[1], request);
		} else if(handle[0].equals("DATA FINISH")){
			if (handle[2].equals("read")) {
				queryHandler.readQuery(handle[1], handle[3]);
			} else if (handle[2].equals("write")) {
				queryHandler.writeQuery(handle[1], handle[3]);
			}
			manager.unLock(handle[1]);
		}
	}

}
