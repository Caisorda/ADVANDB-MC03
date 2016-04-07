package mc03;

import mc03.model.DBConnection;

public class CoordinatorRequestHandler implements RequestHandler{

	@Override
	public void interpret(String request) {
		String handle[] = request.split("~");
		
		if(handle[0].equals("QUERY")){
			//run query
		}else if(handle[0].equals("RECON")){
			//node reconnect
		}else if(handle[0].equals("GO")){
			//vote to commit transaction
		}else if(handle[0].equals("STOP")){
			//vote to abort transaction
		}else if(handle[0].equals("DATA")){
			//result set from read query
		}else if(handle[0].equals("READY")){
			//node asks to ready transaction
		}else if(handle[0].equals("LOG")){
			//receive log record
		}else if(handle[0].equals("ISOLVL")){
			int isolationLevel = Integer.parseInt(handle[1]);
			manager.setIsolationLevel(isolationLevel);
		} else if (handle[0].equals("START")) {
			DBConnection.getInstance();
			queryHandler.addTransaction(handle[1], DBConnection.getConnection("db_hpq"));
		}
	}

}
