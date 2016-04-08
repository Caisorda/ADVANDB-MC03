package mc03.controller;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import mc03.model.Container;
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
	 String query;
	 ArrayList<Integer> columns;
	public void initialize( QueryListController qlc){
		this.qlc= qlc;
		columns = new ArrayList();
		 group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		    	if (group.getSelectedToggle() !=  null) {
		             SoftwareNotification.notifySuccess("You can only update one table at a time.");
		         }
		    	   if (group.getSelectedToggle() ==  hpq_hh_id_radioButton) {
			             System.out.println("municipalityButton is clicked...");
			             hh_id_textField.setEditable(false);
			             query = "UPDATE hpq_crop SET ";
			         }
		    	   else if(group.getSelectedToggle() ==  hh_id_radioButton) {
			             System.out.println("municipalityButton is clicked...");
			             hpq_hh_id_textField.setEditable(false);
			             query = "UPDATE hpq_hh SET ";
			         }
		     } 
		});
	}
	
	public void handleUpdate(){

		if(!"".equals(totinTextField.getText())){
			if(query.equals("UPDATE hpq_hh SET ") || query.equals("UPDATE hpq_crop SET "))
				query  = query + "totin = "  +totinTextField.getText();
			else query  = query + "," +  "totin = " +  totinTextField.getText();
			columns.add(5);
		}
		if(!"".equals(nalpTextField.getText())){
			if(query.equals("UPDATE hpq_hh SET ") || query.equals("UPDATE hpq_crop SET "))
				query  = query + "nalp = "  +nalpTextField.getText();
			else query  = query + "," +  "nalp = " +  nalpTextField.getText();
			columns.add(6);
		}
		if(!"".equals(ifraTextField.getText())){
			if(query.equals("UPDATE hpq_hh SET ") || query.equals("UPDATE hpq_crop SET "))
				query  = query + "irfa = "  +ifraTextField.getText();
			else query  = query + "," +  "irfa = " +  ifraTextField.getText();
			columns.add(7);
		}
		if(!"".equals(crop_volTextField.getText())){
			if(query.equals("UPDATE hpq_hh SET ") || query.equals("UPDATE hpq_crop SET "))
				query  = query + "crop_vol = "  +crop_volTextField.getText();
			else query  = query + "," +  "crop_vol = " +  crop_volTextField.getText();
			columns.add(10);
		}
		if(!"".equals(hpq_hh_id_textField.getText())){
			if(!query.contains("WHERE"))
				query  = query + " WHERE hpq_hh_id = " + hpq_hh_id_textField.getText();
			columns.add(8);
		}
		if(!"".equals(hh_id_textField.getText())){
			if(!query.contains("WHERE"))
				query  = query + " WHERE id = " + hh_id_textField.getText();
			columns.add(0);
		}
		System.out.println("handle update button has been clicked.");
		Stage stage = (Stage) updateQueryButton.getScene().getWindow();
		qlc.addQuery(query, "write", columns);
		this.qlc.addList(query);
		stage.close();
	}
	
	
}
