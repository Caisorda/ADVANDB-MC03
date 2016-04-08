package mc03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class TransactionLogger {
	public static TransactionLogger instance;
	
	private TransactionLogger(){
		
	}
	
	public static TransactionLogger getInstance(){
		if(instance == null){
			instance = new TransactionLogger();
		}
		return instance;
	}
	
	public void logChanges(String change, String request){
        GregorianCalendar greg = new GregorianCalendar();
        String logEntry = change + " " + greg.get(GregorianCalendar.HOUR) + ":" 
                + String.format("%02d", greg.get(GregorianCalendar.MINUTE)) 
                + (greg.get(GregorianCalendar.AM_PM)==1?"PM":"AM") + " " 
                + (greg.get(GregorianCalendar.MONTH)+1) + "/" 
                + greg.get(GregorianCalendar.DAY_OF_MONTH) + "/" 
                + greg.get(GregorianCalendar.YEAR);
        String everything = "";
        try(BufferedReader br = new BufferedReader(new FileReader("dblog.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dblog.txt", false)))) {
            out.println(logEntry);
            out.println(everything);
        }catch (IOException e) {
             e.printStackTrace();
        }
        
        everything = "";
        request = (greg.get(GregorianCalendar.MONTH)+1) + "/" 
                + greg.get(GregorianCalendar.DAY_OF_MONTH) + "/" 
                + greg.get(GregorianCalendar.YEAR) 
                + greg.get(GregorianCalendar.HOUR_OF_DAY) + ":" 
                + String.format("%02d", greg.get(GregorianCalendar.MINUTE)) + ":" 
                + String.format("%02d", greg.get(GregorianCalendar.SECOND)) + ":"
                + "~" + request;
        
        try(BufferedReader br = new BufferedReader(new FileReader("technicallog.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("technicallog.txt", false)))) {
            out.println(logEntry);
            out.println(everything);
        }catch (IOException e) {
             e.printStackTrace();
        }
        
    } 
	
	public ArrayList<String> getLogs(Date lastDate){
		ArrayList<String> logs = new ArrayList();
		
		try(BufferedReader br = new BufferedReader(new FileReader("technicallog.txt"))) {
			String line = br.readLine();
			String handle[] = line.split("~");
			Date logDate = parseDate(handle[0]);
            while (line != null && lastDate.before(lastDate)) {
                logs.add(line);
                line = br.readLine();
            }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return logs;
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
