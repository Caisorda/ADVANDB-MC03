import java.util.HashMap;

public class LockManager {
	public static int READ_UNCOMMITTED = 1;
	public static int READ_COMMITTED = 2;
	public static int REPEATABLE_READ = 3;
	public static int SERIALIZABLE = 4;
	private HashMap<Integer, Lock> locks;
	private int isolationLevel;
	private static LockManager instance;
	
	private LockManager(){
		locks = new HashMap();
		for(int i = 0; i < 11; i++){
			locks.put(i, new Lock());
		}
	}
	
	public LockManager getInstance(){
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
}
