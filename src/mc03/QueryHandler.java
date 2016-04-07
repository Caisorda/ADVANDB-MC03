package mc03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import mc03.model.DBConnection;



public class QueryHandler {
	private HashMap<String, Connection> transactions;
	private static QueryHandler instance;
	
	private QueryHandler(){
		transactions = new HashMap();
	}
	
	public static QueryHandler getInstance(){
		if (instance == null) {
            instance = new QueryHandler();
        }
        return instance;
	}
	
	public void addTransaction(String transID, Connection transaction){
		transactions.put(transID, transaction);
	}
	
	public void writeQuery(String transID, String query){
		Connection con = transactions.get(transID);
		try {
			PreparedStatement prepped = con.prepareStatement(query);
			prepped.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet readQuery(String transID, String query){
		Connection con = transactions.get(transID);
		ResultSet results = null;
		try {
			PreparedStatement prepped = con.prepareStatement(query);
			results = prepped.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
}
