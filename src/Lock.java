import java.util.ArrayList;

public class Lock {
	private boolean isWriting;
	private int numReaders;
	private ArrayList<String> transactions;
	
	public Lock(){
		this.transactions = new ArrayList();
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
	
	public synchronized void readLock(){
		while(isWriting){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numReaders++;
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
