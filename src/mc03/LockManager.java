package mc03;
import java.util.HashMap;

public class LockManager {
	private HashMap<Integer, Lock> locks;
	private static LockManager instance;
	private HashMap<String, String> transactionLocks;
	
	private LockManager(){
		locks = new HashMap();
		for(int i = 0; i < 11; i++){
			locks.put(i, new Lock());
		}
		transactionLocks = new HashMap();
	}
	
	public static LockManager getInstance(){
		if (instance == null) {
            instance = new LockManager();
        }
        return instance;
	}
	
	public void readLock(int column, String transID){
		locks.get(column).readLock(transID);
		if(transactionLocks.containsKey(transID)){
			String locks = transactionLocks.get(transID);
			locks = locks + "," + column;
			transactionLocks.put(transID, locks);
		}else{
			transactionLocks.put(transID, ""+column);
		}
	}
	
	public void writeLock(int column, String transID){
		locks.get(column).writeLock(transID);
		if(transactionLocks.containsKey(transID)){
			String locks = transactionLocks.get(transID);
			locks = locks + "," + column;
			transactionLocks.put(transID, locks);
		}else{
			transactionLocks.put(transID, ""+column);
		}
	}
	
	public void unLock(String transID){
		if(transactionLocks.containsKey(transID)){
			String lockedColumns[] = transactionLocks.get(transID).split(",");
			for(int i=0; i<lockedColumns.length; i++){
				locks.get(Integer.parseInt(lockedColumns[i])).unlock(transID);
			}
			transactionLocks.remove(transID);
			
			if(transactionLocks.isEmpty()){
				//TransactionLogger.getInstance().logChanges("CHECKPOINT");
			}
		}
	}
	
//	public void queryLock(String queryType, int[] columns){
//		switch(this.isolationLevel){
//			case 1: if(queryType.equalsIgnoreCase("write"))
//		}
//	}
}
