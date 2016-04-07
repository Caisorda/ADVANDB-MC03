package mc03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import mc03.model.DBConnection;



public class QueryHandler {
	public static int READ_UNCOMMITTED = 1;
	public static int READ_COMMITTED = 2;
	public static int REPEATABLE_READ = 3;
	public static int SERIALIZABLE = 4;
	private int isolationLevel;
	private HashMap<String, Connection> transactions;
	private static QueryHandler instance;
	
	private QueryHandler(){
		transactions = new HashMap();
	}

	public synchronized void setIsolationLevel(int level, String transID){
		this.isolationLevel = level;
		Connection conn = transactions.get(transID);
		try {
			conn.setTransactionIsolation(level);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getIsolationLevel(){
		return this.isolationLevel;
	}
	
	public static QueryHandler getInstance(){
		if (instance == null) {
            instance = new QueryHandler();
        }
        return instance;
	}
	
	public synchronized void addTransaction(String transID, String schema){
		Connection transaction = DBConnection.getConnection(schema);
		try {
			transaction.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transactions.put(transID, transaction);
	}
	
	public synchronized void writeQuery(String transID, String query){
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
	
	public synchronized void commitTransaction(String transID){
		Connection con = transactions.get(transID);
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		transactions.remove(transID);
	}
	
	public synchronized void abortTransaction(String transID){
		Connection con = transactions.get(transID);
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		transactions.remove(transID);
	}
}
