package mc03.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FirstQueryController {
	 @FXML  RadioButton municipalityButton;
	 @FXML	RadioButton barangayButton;
	 @FXML 	RadioButton	zoneButton;
	 @FXML ToggleGroup group = new ToggleGroup();
	 @FXML ToggleGroup cropTypeGroup = new ToggleGroup();
	 @FXML 	RadioButton	sugarCaneButton;
	 @FXML 	RadioButton	palayButton;
	 @FXML 	RadioButton	cornButton;
	 @FXML 	RadioButton	coffeeButton;
	 @FXML  AnchorPane	cropTypePane;
	 @FXML  Button 		addQueryButton;
	 QueryListController qlc;
	 private String str;
	 private String query;
	 private String selected;
	 private String crop;
	 private int checker;
   
 public void initialize( QueryListController qlc,String str){
	 this.str=str;
	 checker =0;
	 this.qlc=qlc;
	 
	 cropTypePane.setVisible(false);
	 group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		    	if(checker ==1){
		    	if (group.getSelectedToggle() !=  null) {
		    		 cropTypePane.setVisible(true);
		         }}
		         if (group.getSelectedToggle() ==  municipalityButton) {
		             System.out.println("municipalityButton is clicked...");
		             selected = "mun";
		         }
		         else if (group.getSelectedToggle() ==  barangayButton) {
		             System.out.println("barangayButton is clicked...");
		             selected = "brgy";
		         }
		         else if (group.getSelectedToggle() ==  zoneButton) {
		             System.out.println("zoneButton is clicked...");
		             selected = "zone";
		         }

		     } 
		});
	 cropTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		    
		         if (cropTypeGroup.getSelectedToggle() ==  sugarCaneButton) {
		             System.out.println("sugarCaneButton is clicked...");
		             crop="1";
		         }
		         else if (cropTypeGroup.getSelectedToggle() ==  palayButton) {
		             System.out.println("palayButton is clicked...");
		             crop="2";
		         }
		         else if (cropTypeGroup.getSelectedToggle() ==  coffeeButton) {
		             System.out.println("coffeeButton is clicked...");
		             crop="3";
		         }
		         else if (cropTypeGroup.getSelectedToggle() ==  cornButton) {
		             System.out.println("cornButton is clicked...");
		             crop="4";
		         }

		     } 
		});

 }
 
 public void handleAddQueryButton(){
initializeQuery();
this.qlc.addList(str);
this.qlc.addQuery(query);
System.out.println("handle query button has been clicked.");
Stage stage = (Stage) addQueryButton.getScene().getWindow();
stage.close();
 }
 public void initializeQuery(){
	 if (str == "Crop Volume"){
		 System.out.println("query is relted to crop volume.");
		 
		query = "select hh."+selected+", sum(crop.crop_vol) as volume" +
		"from hpq_hh hh"+
		" inner join hpq_crop crop"+
		" on(crop.hpq_hh_id = hh.id)"+
		" where croptype ="+crop+
		" group by hh."+selected;

		 checker=1;
	 }
	 else if(str =="Crop per Household"){
		 System.out.println("query is relted to crop per household..");
		 
		 query = " select hh."+selected+", count(hh.id) as count"+
				 "from hpq_hh hh"+
				 "inner join hpq_crop crop"+
				 "on(crop.hpq_hh_id = hh.id)"+
				 "where croptype ="+crop+
				 " group by hh."+selected;
		 
		 checker =1;

	 }
	 else if(str  == "Households that have Insurance"){
		 System.out.println("query is relted to households that have insurance..");
		 
		 query = "select hh."+selected+", count(hh.id) as count"+
				" from hpq_hh hh"+
				" where irfa_crop = 2"+
				 " group by hh."+selected;

	 }
	 else if(str =="total income of households that have Insurance"){
		 System.out.println("query is relted to total income per households.");
		 
		 query = "select hh."+selected+", sum(totin) as income"+
				 "from hpq_hh hh"+
				 "where irfa_crop = 2"+
				 "group by hh."+selected;

	 }
	 else if (str == "Total farmland"){
		 System.out.println("query is relted tototal fram land..");
		 
		 query = "	select "+selected+"	, sum(nalp) as landarea"+
				" from hpq_hh hh"+
				 "group by hh."+selected;

	 }
 }
 
}
