package mc03.model;

import java.util.ArrayList;
import java.util.List;


public class Transaction {
	
	List<String> queries = new ArrayList<>();
	String name;
	int id;
	
	
	public List<String> getQueries(){
			return this.queries;
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
