package mc03.model;

import java.util.ArrayList;
import java.util.List;


public class Transaction {
	
	String query;
	String name;
	int id;
	
	
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
