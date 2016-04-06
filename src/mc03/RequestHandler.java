package mc03;

public class RequestHandler {
	
	private LockManager manager;
	private QueryHandler queryHandler;
	
	public RequestHandler(){
		manager = LockManager.getInstance();
		queryHandler = QueryHandler.getInstance();
	}
	
	public void interpret(String request){
		String handle[] = request.split("~");
		
		if(handle[0].equals("QUERY")){
			//run query
		}else if(handle[0].equals("RECON")){
			//node reconnect
		}else if(handle[0].equals("ABORT")){
			//abort transaction
		}else if(handle[0].equals("COMMIT")){
			//commit transaction
		}else if(handle[0].equals("GO")){
			//vote to commit transaction
		}else if(handle[0].equals("STOP")){
			//vote to abort transaction
		}else if(handle[0].equals("DATA")){
			//result set from read query
		}else if(handle[0].equals("READY")){
			//coordinator asks if transaction can be committed
		}else if(handle[0].equals("LOG")){
			//receive log record
		}else if(handle[0].equals("ISOLVL")){
			int isolationLevel = Integer.parseInt(handle[1]);
			manager.setIsolationLevel(isolationLevel);
		}else if(handle[0].equals("START")){
//			queryHandler.addTransaction(handle[1], new DBConnection());
		}
	}
}
