package mc03.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import mc03.view.SoftwareNotification;

public class UpdateController {
	@FXML TextField totinTextField;
	@FXML TextField	nalpTextField;
	@FXML TextField ifraTextField;
	@FXML TextField crop_volTextField;
	
	@FXML TextField hpq_hh_id_textField;
	@FXML TextField hh_id_textField;
	
	@FXML RadioButton hpq_hh_id_radioButton;
	@FXML RadioButton hh_id_radioButton;
	
	@FXML ToggleGroup group;
	
	@FXML Button 	updateQueryButton;
	 QueryListController qlc;
	public void initialize( QueryListController qlc){
		this.qlc= qlc;
		
		 group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		    	if (group.getSelectedToggle() !=  null) {
		             SoftwareNotification.notifySuccess("You can only update one table at a time.");
		         }
		    	   if (group.getSelectedToggle() ==  hpq_hh_id_radioButton) {
			             System.out.println("municipalityButton is clicked...");
			             hh_id_textField.setEditable(false);
			         }
		    	   else if(group.getSelectedToggle() ==  hh_id_radioButton) {
			             System.out.println("municipalityButton is clicked...");
			             hpq_hh_id_textField.setEditable(false);
			         }
		     } 
		});
	}
	
	public void handleUpdate(){
//		qlc.addQuery("...");
//		this.qlc.addList("...");
		System.out.println("handle update button has been clicked.");
		Stage stage = (Stage) updateQueryButton.getScene().getWindow();
		stage.close();
	}
	
	
}
