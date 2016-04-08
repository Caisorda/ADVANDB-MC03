package mc03.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import mc03.Main;
import mc03.view.SoftwareNotification;

public class QueryListController implements Initializable {
	 @FXML private ListView<String> queryListView;
	 @FXML Button cropVolume;
	 @FXML Button numHouseholds;
	 @FXML Button hasInsurance;
	 @FXML Button totalIncomeInsurance;
	 @FXML Button totalFarmland;
	 @FXML Button handlenumHouseholdsCropType;
	 @FXML Button handlecropVolumeCropType;
	 @FXML Button listQueries;
	 @FXML Button updateButton;
	 private List<String> queryList;
	 private  ObservableList<String> qAttr = FXCollections.observableArrayList();
	 private MainController mc;
	 private List<String> getQueryAttributes()
	  { 
		 List<String> attr = new ArrayList<>();
		 attr.add("1");
		 return attr;
	  }
	 
	 public void initialize(MainController mc){
		 System.out.println("QueryListController.java: initialize w/o @Override was called");
		 this.queryListView.getItems().addAll(qAttr);
		 queryList = new ArrayList<>();
		 this.mc=mc;
		 
	 }
	 
	 public void handleUpdateButton() throws IOException{
		 FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("view/Update.fxml"));
			

			loader.setLocation(Main.class.getResource("view/Update.fxml"));
			Parent root = loader.load();
			UpdateController controller =
					loader.<UpdateController>getController();
			controller.initialize(this);  
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Update");
			stage.show();
	 }
	 @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 System.out.println("QueryListController.java: initialize w @Override was called");
		 this.queryListView.getItems().addAll(qAttr);
		 queryList = new ArrayList<>();
	}
	 
	public  void addList(String str){
		qAttr.add(str);
		this.queryListView.getItems().addAll(qAttr);
		qAttr.clear();
	}
	
	public void addQuery(String str){
		queryList.add(str);
	}
	public void handleListQueries(){
		try{
		mc.addTransaction(queryList.get(0));
		System.out.println("added to transaction: "+queryList.get(0));}
		catch(IndexOutOfBoundsException e){
			SoftwareNotification.notifyError("please select query."); 
			System.out.println("error sa add transaction pre.");
		}
		Stage stage = (Stage) listQueries.getScene().getWindow();
		stage.close();
	}
	
	 public void handleCropVolumeButton() throws IOException{
			FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("view/FirstQuery.fxml"));
			

			loader.setLocation(Main.class.getResource("view/FirstQuery.fxml"));
			Parent root = loader.load();
			FirstQueryController controller =
					loader.<FirstQueryController>getController();
			controller.initialize(this,"Crop Volume");  
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Crop Volume");
			stage.show();
	 }
	 
	 public void handleNumberHouseholds() throws IOException{
		 System.out.println("number households has been clicked.");
		 FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("view/FirstQuery.fxml"));
			
		 loader.setLocation(Main.class.getResource("view/FirstQuery.fxml"));
			Parent root = loader.load();
			FirstQueryController controller =
					loader.<FirstQueryController>getController();
			controller.initialize(this,"Crop per Household");  
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Number of Households");
			stage.show();
	 }
	 public void handleHasInsurance() throws IOException{
		 System.out.println("Has insurance has been clicked.");
		 FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("view/FirstQuery.fxml"));
			
		 loader.setLocation(Main.class.getResource("view/FirstQuery.fxml"));
			Parent root = loader.load();
			FirstQueryController controller =
					loader.<FirstQueryController>getController();
			controller.initialize(this,"Households that have Insurance");  
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Number of Households");
			stage.show();
	 }
	 
	 public void totalIncomeHasInsurance() throws IOException{
		 System.out.println("total income that have  insurance has been clicked.");
		 FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("view/FirstQuery.fxml"));
			
		 loader.setLocation(Main.class.getResource("view/FirstQuery.fxml"));
			Parent root = loader.load();
			FirstQueryController controller =
					loader.<FirstQueryController>getController();
			controller.initialize(this,"total income of households that have Insurance");  
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Total Income of Households");
			stage.show();
	 }
	 public void totalFarmlandArea() throws IOException{
		 System.out.println("Total farmland.");
		 FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("view/FirstQuery.fxml"));
			
		 loader.setLocation(Main.class.getResource("view/FirstQuery.fxml"));
			Parent root = loader.load();
			FirstQueryController controller =
					loader.<FirstQueryController>getController();
			controller.initialize(this,"Total farmland");  
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Total farmland   of Households");
			stage.show();
	 }
	 public void handlenumHouseholdsCropType(){
		 addList("number of households that produce each crop type.");
		 String query ="select (CASE WHEN croptype=1 THEN 'SUGAR CANE' "+
			        "WHEN croptype=2 THEN 'PALAY' "+
			        "WHEN croptype=3 THEN 'CORN' "+
			        "WHEN croptype=4 THEN 'COFFEE' "+
			        "ELSE 'OTHER' "+
					 "END) crop_name, count(hh.id) "+
					 "from hpq_hh hh "+
					 "inner join hpq_crop crop "+
					 " on(crop.hpq_hh_id = hh.id) "+
					 " where croptype is not null "+
					 "group by crop.croptype";
		 
		 addQuery(query);
	 }
	 public void handlecropVolumeCropType(){
		 addList("Crop volume per crop type.");
		 String query = "select (CASE WHEN croptype=1 THEN 'SUGAR CANE' "+
                 		"WHEN croptype=2 THEN 'PALAY' "+
                 		"WHEN croptype=3 THEN 'CORN' "+
                 		"WHEN croptype=4 THEN 'COFFEE' "+
                 		"END) crop_name, sum(crop_vol) "+
						"from hpq_crop crop "+
						"where croptype is not null "+
						"group by crop.croptype";


		 addQuery(query);
	 }	
	 
	 
}
