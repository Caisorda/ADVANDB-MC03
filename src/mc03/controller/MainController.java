package mc03.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mc03.Constants;
import mc03.LockManager;
import mc03.Main;
import mc03.QueryHandler;
import mc03.reciever;
import mc03.sender;
import mc03.model.Container;
import mc03.model.DBConnection;
import mc03.model.Machine;
import mc03.model.Transaction;
import mc03.network.Client;
import mc03.network.Server;
import mc03.network.ServerMessageSender;
import mc03.view.SoftwareNotification;

public class MainController {
	@FXML private VBox rootVBox;
	@FXML Button queryButton;
	 @FXML  ListView<String> transactionsList;
	 private List<Transaction> transactions;
	 @FXML TextField transactionField;
	 @FXML Text 	 ipAddress;
	 
	 @FXML RadioButton readUncommitedRButton;
	 @FXML RadioButton readCommitedRButon;
	 @FXML RadioButton repeatableReadRBUtton;
	 @FXML RadioButton serializableRButton;
	 
	 @FXML ToggleGroup isolationLevel;
	 String isolationLevel2;
	public void initializeVariables() throws UnknownHostException{
	transactions = new ArrayList<>();
	String temp =""+ InetAddress.getLocalHost();
	ipAddress.setText(temp);

		isolationLevel.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

				if (isolationLevel.getSelectedToggle() ==  readCommitedRButon) {
					isolationLevel2="Set to Read Committed";
					SoftwareNotification.notifySuccess("Isolation level set to:"+isolationLevel2);
					QueryHandler.getInstance().setIsolationLevel(Connection.TRANSACTION_READ_COMMITTED);
				} else if(isolationLevel.getSelectedToggle() ==  readUncommitedRButton) {
					isolationLevel2="Now set to Read Uncommitted";
					SoftwareNotification.notifySuccess("Isolation level set to:"+isolationLevel2);
					QueryHandler.getInstance().setIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED);
				} else if(isolationLevel.getSelectedToggle() ==  repeatableReadRBUtton) {
					isolationLevel2="Now set to Repeatable Read";
					SoftwareNotification.notifySuccess("Isolation level set to:"+isolationLevel2);
					QueryHandler.getInstance().setIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ);
				} else if(isolationLevel.getSelectedToggle() ==  serializableRButton) {
					isolationLevel2="Now set to Serializable";
					SoftwareNotification.notifySuccess("Isolation level set to:"+isolationLevel2);
					QueryHandler.getInstance().setIsolationLevel(Connection.TRANSACTION_SERIALIZABLE);

				}

			}
		});

	}
	public void addTransaction(String query, String type, List<Integer> columns){
		String temp = null;
		Exception e = null;
		try{
		temp = transactionField.getText();
		if(temp ==""){
		throw e;}
		}catch(Exception f)
		{
			System.out.println("Please input a transaction id.");
		}
		 Transaction tran = new Transaction();
		tran.setId(Integer.parseInt(temp));
		 tran.setName(temp);
		 tran.setQuery(query);
		tran.setQueryType(type);
		for(int i: columns)
			tran.addColumnToLock(i);
		 transactions.add(tran);
		System.out.println(tran.getId() + " / " + tran.getName() + " / " + tran.getQueries());
		this.transactionsList.getItems().addAll("ID: " + temp + "; Transaction:  " + query);

		QueryHandler.getInstance().addTransaction(temp, Container.getInstance().getDatabaseName());
		
	}

	public void handleLocalExecution() {
		DBConnection.getInstance();
		
		for (Transaction t : transactions) {
			QueryHandler.getInstance().commitTransaction(t.getId() + "");
		}

		for (Transaction t : transactions) {
			ResultSet rs = null;
			if(t.getQueryType().equals("read")){
				for(int i : t.getLockedColumns())
					LockManager.getInstance().readLock(i, t.getName());
				 rs = QueryHandler.getInstance().readQuery(t.getId() + "", t.getQueries());
			}else if(t.getQueryType().equals("write")){
				for(int i : t.getLockedColumns())
					LockManager.getInstance().writeLock(i, t.getName());
				QueryHandler.getInstance().writeQuery(t.getId() + "", t.getQueries());
			}
			
			
			LockManager.getInstance().unLock(t.getName());
			QueryHandler.getInstance().commitTransaction(t.getId() + "");
			System.out.println("MainController.java: Executing Transaction ID " + t.getId());
			SoftwareNotification.notifySuccess("Successfully clicked Local button.");
			SoftwareNotification.notifySuccess("Isolation level is:"+isolationLevel2);
			
			
			
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass()
						.getResource("view/ResultsWindow.fxml"));


				loader.setLocation(Main.class.getResource("view/ResultsWindow.fxml"));
				Parent root = loader.load();
				ResultsWindowController controller =
						loader.getController();
				System.out.println("IS IT NULL??? " + controller);
				controller.initializeData(rs);
				
				
				
				
				
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Query List");
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		transactions.clear();
		transactionsList.getItems().clear();
	}

	public void handleGlobalExecution() {
		for (Transaction t : transactions) {
			QueryHandler.getInstance().commitTransaction(t.getId() + "");
	
		sender man = new sender();
//		 

		man.setadress("localhost");
		man.send("DATA ~ "+man.resultData(t.getQueries()));
//		for (Machine m : Server.getInstance().getMachines()) {
//
//			ServerMessageSender.sendMessage(" DATA ~ " + man.resultData(query), m);	
//		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		man.DoQuery(t.getQueries());	}
		SoftwareNotification.notifySuccess("Successfully clicked Global button.");
	}
	
	public void HandleQueryButton(ActionEvent e) throws IOException{
		if (transactionField.getText().isEmpty()) {
			SoftwareNotification.notifyError("Please enter a Transaction ID.");
		} else {

			FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("view/QueryList.fxml"));


			loader.setLocation(Main.class.getResource("view/QueryList.fxml"));
			Parent root = loader.load();
			QueryListController controller =
					loader.<QueryListController>getController();
			controller.initialize(this);
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Query List");
			stage.show();
		}
	}
	
}
