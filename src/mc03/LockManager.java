package mc03;
import java.util.HashMap;

public class LockManager {
	public static int READ_UNCOMMITTED = 1;
	public static int READ_COMMITTED = 2;
	public static int REPEATABLE_READ = 3;
	public static int SERIALIZABLE = 4;
	private HashMap<Integer, Lock> locks;
	private int isolationLevel;
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
	
	public void setIsolationLevel(int level){
		this.isolationLevel = level;
	}
	
	public int getIsolationLevel(){
		return this.isolationLevel;
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
	}
	
//	public void queryLock(String queryType, int[] columns){
//		switch(this.isolationLevel){
//			case 1: if(queryType.equalsIgnoreCase("write"))
//		}
//	}
}
