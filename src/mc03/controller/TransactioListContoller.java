package mc03.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mc03.model.Transaction;

public class TransactioListContoller {
	@FXML private TextField filterTextField;
	@FXML private ListView<Transaction> transactionListView;

	@FXML private void initialize() {		
		
	}
	
	public void resize(double width, double height) {
		Parent parent = transactionListView.getParent();
		parent.prefWidth(width);
		parent.prefHeight(height);
		
		transactionListView.setPrefWidth(width);
		transactionListView.setPrefHeight(height);
	}
	
	public double getWidth() {
		return transactionListView.getWidth();
	}
	
	public double getHeight() {
		return  transactionListView.getHeight();
	}
	
	public TextField getFilterTextField() {
		return filterTextField;
	}
	
	public ListView<Transaction> getListView() {
		return transactionListView;
	}
	
}


