package mc03.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mc03.Constants;
import mc03.Main;
import mc03.QueryHandler;
import mc03.model.Container;
import mc03.model.DBConnection;
import mc03.model.Transaction;
import mc03.network.Client;
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

	public void initializeVariables() throws UnknownHostException{
	transactions = new ArrayList<>();
	String temp =""+ InetAddress.getLocalHost();
	ipAddress.setText(temp);

	}
	public void addTransaction(String query){
		System.out.println("HELLOLOLO");
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
		 transactions.add(tran);
		System.out.println(tran.getId() + " / " + tran.getName() + " / " + tran.getQueries());
		this.transactionsList.getItems().addAll("ID: " + temp + "; Transaction:  " + query);

		QueryHandler.getInstance().addTransaction(temp, Container.getInstance().getDatabaseName());
		
	}

	public void handleLocalExecution() {
		DBConnection.getInstance();
		SoftwareNotification.notifySuccess("Successfully clicked Local button.");
		for (Transaction t : transactions) {
			QueryHandler.getInstance().commitTransaction(t.getId() + "");
		}

		for (Transaction t : transactions) {
			ResultSet rs = QueryHandler.getInstance().readQuery(t.getId() + "", t.getQueries());
			System.out.println("MainController.java: Executing Transaction ID " + t.getId());
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
	}

	public void handleGlobalExecution() {
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
