package mc03.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mc03.Main;
public class MainController {
	@FXML private VBox rootVBox;
	@FXML Button queryButton;
	public void initializeVariables(){
		
		
	}
	
	public void HandleQueryButton(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass()
				.getResource("view/QueryList.fxml"));
		

		loader.setLocation(Main.class.getResource("view/QueryList.fxml"));
		Parent root = loader.load();
		QueryListController controller =
				loader.<QueryListController>getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Query List");
		stage.show();
	}
	
}
