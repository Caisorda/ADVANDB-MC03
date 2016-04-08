package mc03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mc03.model.Container;
import mc03.model.DBConnection;
import mc03.model.DBSomething;


public class QueryHandler {
	public static int READ_UNCOMMITTED = 1;
	public static int READ_COMMITTED = 2;
	public static int REPEATABLE_READ = 3;
	public static int SERIALIZABLE = 4;
	private int isolationLevel;
	private HashMap<String, Connection> transactions = new HashMap<>();
	private List<DBSomething> somethingList = new ArrayList<>();
	private static QueryHandler instance = null;
	
	private QueryHandler() {
		DBConnection.getInstance();
		System.out.println("Transactions is: " + transactions);
	}

	public synchronized void setIsolationLevel(int level) {
		try {
			this.isolationLevel = level;
			for (DBSomething somethang : somethingList) {
				Connection con = somethang.getConn();
				con.setTransactionIsolation(level);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized void setIsolationLevel(int level, String transId) {
		try {
			Connection conn = transactions.get(transId);
			conn.setTransactionIsolation(level);
			this.isolationLevel = level;

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getIsolationLevel(){
		return this.isolationLevel;
	}
	
	public static QueryHandler getInstance(){
		if (instance == null) {
			System.out.println("QueryHandler.java: Creating a new instance");
            instance = new QueryHandler();
        }
        return instance;
	}
	
	public synchronized void addTransaction(String transID, String schema){
		Connection transaction = DBConnection.getConnection(schema);
		try {
			transaction.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			transaction.prepareStatement("getInstance().url + Container.getInstance().getDatabaseName()");
			System.out.println("Successfully quiried halskdfj");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//transactions.put(transID, transaction);
		somethingList.add(new DBSomething(transID, transaction));
		System.out.println(transactions.size());
	}

	public synchronized void writeQuery(String transID, String query){
			Connection con = null;

			for (DBSomething somethang : somethingList) {
				if (somethang.getTransID().equals(transID)) {
					con = somethang.getConn();
				}
			}
		try {
			PreparedStatement prepped = con.prepareStatement(query);
			prepped.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet readQuery(String transID, String query){
		Connection con = null;

		for (DBSomething somethang : somethingList) {
			if (somethang.getTransID().equals(transID)) {
				con = somethang.getConn();
			}
		}
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
			Connection con = null;

			for (DBSomething somethang : somethingList) {
				if (somethang.getTransID().equals(transID)) {
					con = somethang.getConn();
				}
			}
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		transactions.remove(transID);
	}

	public synchronized void abortTransaction(String transID){
		Connection con = null;

		for (DBSomething somethang : somethingList) {
			if (somethang.getTransID().equals(transID)) {
				con = somethang.getConn();
			}
		}

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		transactions.remove(transID);
	}
}
