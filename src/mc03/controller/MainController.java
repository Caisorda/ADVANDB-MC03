package mc03.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import mc03.Main;
import mc03.model.Transaction;
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
	transactions = new ArrayList();
	String temp =""+ InetAddress.getLocalHost();
	ipAddress.setText(temp);
	
	}
	public void addTransaction(String query){
		String temp = null;
		Exception e = null;
		try{
		temp = transactionField.getText();
		if(temp ==""){
		throw e;}
		}catch(Exception f)
		{
			System.out.println("Please input trasaction id.");
		}
		 Transaction tran = new Transaction();
		 tran.setName(temp);
		 tran.setQuery(query);
		 transactions.add(tran);
		this.transactionsList.getItems().addAll("Transaction; id:"+temp);
		
	}
	
	public void HandleQueryButton(ActionEvent e) throws IOException{
		
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
