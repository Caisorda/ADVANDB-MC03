package mc03;

import java.util.HashMap;

import model.DBConnection;

public class QueryHandler {
	private HashMap<String, DBConnection> transactions;
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
	
	public void addTransaction(String transID, DBConnection transaction){
		transactions.put(transID, transaction);
	}
}
