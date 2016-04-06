package mc03;

import java.sql.Date;

public class Log {
	Date date;
	String transID;
	String query;
	
	public Log(Date date, String transID, String query) {
		super();
		this.date = date;
		this.transID = transID;
		this.query = query;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTransID() {
		return transID;
	}
	public void setTransID(String transID) {
		this.transID = transID;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	
}
