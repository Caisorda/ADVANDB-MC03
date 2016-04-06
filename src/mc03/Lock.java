package mc03;
import java.util.ArrayList;

public class Lock {
	private boolean isWriting;
	private int numReaders;
	private ArrayList<String> transactions;
	private LockManager manager;
	
	public Lock(){
		this.transactions = new ArrayList();
		manager = manager.getInstance();
	}
	
	public synchronized void writeLock(String transID){
		while(isWriting || numReaders > 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isWriting = true;
		transactions.add(transID);
	}
	
	public synchronized void readLock(String transID){
		while(isWriting){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numReaders++;
		transactions.remove(transID);
	}
	
	public synchronized void unlock(String transID){
		if(transactions.contains(transID)){
			if(isWriting){
				isWriting = false;
				notifyAll();
			}
			else{
				numReaders--;
				if(numReaders == 0)
					notifyAll();
			}
			transactions.remove(transID);
		}
	}
	
	
}
