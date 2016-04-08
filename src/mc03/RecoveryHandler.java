package mc03;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecoveryHandler {
	
	private QueryHandler handler;
	private LockManager manager;
	private RequestHandler reqHandler;
	private static RecoveryHandler instance;
	private ArrayList<String> lostTransactions;
	
	private RecoveryHandler(){
		handler = QueryHandler.getInstance();
		manager = LockManager.getInstance();
		reqHandler = new NodeRequestHandler("Marinduque");
	}
	
	public static RecoveryHandler getInstance(){
		if (instance == null) {
            instance = new RecoveryHandler();
        }
        return instance;
	}
	
	public ArrayList<String> getUnfinishedTransactions(){
		try(BufferedReader br = new BufferedReader(new FileReader("dblog.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null || !line.equals("CHECKPOINT")) {
            	if(line.startsWith("READY")){
            		String handle[] = line.split(" ");
            		lostTransactions.add(handle[1]);
            	}else if(line.startsWith("COMMIT") || line.startsWith("ABORT")){
            		String handle[] = line.split(" ");
            		if(lostTransactions.contains(handle[1]))
            			lostTransactions.remove(handle[1]);
            	}
            }
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return lostTransactions;
	}
	
	public void recoverState(ArrayList<String> logs){
		for(String tran : logs){
			String handle[] = tran.split(" ");
			if(handle[1].equals("COMMIT") && lostTransactions.contains(handle[2])){
				lostTransactions.remove(handle[2]);
				handler.commitTransaction(handle[2]);
				manager.unLock(handle[2]);
			} else if(handle[1].equals("ABORT") && lostTransactions.contains(handle[2])){
				lostTransactions.remove(handle[2]);
				handler.abortTransaction(handle[2]);
				manager.unLock(handle[2]);
			} 
			else{
				tran = tran.substring(handle[0].length()+1);
				reqHandler.interpret(tran);
			}
		}
	}
	
	public Date getLastDateTime(){
		try (BufferedReader br = new BufferedReader(new FileReader("technicallog.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			String handle[] = line.split("~");
			return parseDate(handle[0]);
			
            
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return null;
	}
	
	public Date parseDate(String dateString){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(dateString);
			System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
