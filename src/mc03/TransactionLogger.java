package mc03;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;

public class TransactionLogger {
	public TransactionLogger instance;
	
	private TransactionLogger(){
		
	}
	
	public TransactionLogger getInstance(){
		if(instance == null){
			this.instance = new TransactionLogger();
		}
		return this.instance;
	}
	
	public void logChanges(String change){
        GregorianCalendar greg = new GregorianCalendar();
        String logEntry = change + " " + greg.get(GregorianCalendar.HOUR) + ":" 
                + String.format("%02d", greg.get(GregorianCalendar.MINUTE)) 
                + (greg.get(GregorianCalendar.AM_PM)==1?"PM":"AM") + " " 
                + (greg.get(GregorianCalendar.MONTH)+1) + "/" 
                + greg.get(GregorianCalendar.DAY_OF_MONTH) + "/" 
                + greg.get(GregorianCalendar.YEAR);
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)))) {
            out.println(logEntry);
        }catch (IOException e) {
             e.printStackTrace();
        }
    }   
}
