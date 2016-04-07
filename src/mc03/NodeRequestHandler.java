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
		
		if (handle[0].equals("QUERY")) {
			// run query
			// QUERY~<transaction ID>~<query type>~<actualy query>~<columns
			// used>
			if (handle[2].equals("read")) {
				for (int i = 4; i < handle.hashCode(); i++) {
					manager.readLock(Integer.parseInt(handle[i]), handle[1]);
				}
				queryHandler.readQuery(handle[1], handle[2]);
			} else if (handle[2].equals("write")) {
				for (int i = 4; i < handle.hashCode(); i++) {
					manager.writeLock(Integer.parseInt(handle[i]), handle[1]);
				}
				queryHandler.writeQuery(handle[1], handle[2]);
			}
		} else if (handle[0].equals("RECON")) {
			// node reconnect
		} else if (handle[0].equals("ABORT")) {
			// abort transaction
		} else if (handle[0].equals("COMMIT")) {
			// commit transaction
		} else if (handle[0].equals("DATA")) {
			// result set from read query
		} else if (handle[0].equals("READY")) {
			// coordinator asks if transaction can be committed
		} else if (handle[0].equals("LOG")) {
			// receive log record
		} else if (handle[0].equals("ISOLVL")) {
			int isolationLevel = Integer.parseInt(handle[1]);
			manager.setIsolationLevel(isolationLevel);
		} else if (handle[0].equals("START")) {
			DBConnection.getInstance();
			queryHandler.addTransaction(handle[1], DBConnection.getConnection(this.nodeSchema));
		}
	}
}
