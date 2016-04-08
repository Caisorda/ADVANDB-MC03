package mc03.model;

import java.util.ArrayList;
import java.util.List;


public class Transaction {
	
	String query;
	String queryType;
	String name;
	int id;
	private static List<Integer> lockedColumns = new ArrayList<>();

	public Transaction(){
		lockedColumns = new ArrayList<>();
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}


	public static void addColumnToLock(int columnId) {
		lockedColumns.add(columnId);
	}

	public static void removeColumnLock(int columnId){
		lockedColumns.remove(columnId);
	}

	public List<Integer> getLockedColumns(){
		return lockedColumns;
	}

	public String getQueries(){
			return this.query;
	}
	
	public void setQuery(String query){
		this.query=query;
	}
	
	public String getName(){
		return this.name;
	}
	public int getId(){
		return this.id;
	}
	
	public void setName(String name){
		this.name=name;
	}
	public void setId(int id){
		this.id = id;
	}
	

}
