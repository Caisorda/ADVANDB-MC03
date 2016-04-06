package mc03.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateController {
	@FXML TextField totinTextField;
	@FXML TextField	nalpTextField;
	@FXML TextField ifraTextField;
	@FXML TextField crop_volTextField;
	
	@FXML Button 	updateQueryButton;
	 QueryListController qlc;
	public void initialize( QueryListController qlc){
		this.qlc= qlc;
	}
	public void handleUpdate(){
		

		this.qlc.addList(str);
		this.qlc.addQuery(query);
		System.out.println("handle update button has been clicked.");
		Stage stage = (Stage) updateQueryButton.getScene().getWindow();
		stage.close();
	}
}
