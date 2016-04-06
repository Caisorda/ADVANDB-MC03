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

public class QueryListController implements Initializable {
	 @FXML private ListView<String> queryListView;
	 @FXML Button cropVolume;
	 @FXML Button numHouseholds;
	 @FXML Button hasInsurance;
	 @FXML Button totalIncomeInsurance;
	 @FXML Button totalFarmland;
	 @FXML Button handlenumHouseholdsCropType;
	 @FXML Button handlecropVolumeCropType;
	 private List<String> queryList;
	 private  ObservableList<String> qAttr = FXCollections.observableArrayList();
	 
	 private List<String> getQueryAttributes()
	  { 
		 List<String> attr = new ArrayList();
		 attr.add("1");
		 return attr;
	  }
	 
	 @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 this.queryListView.getItems().addAll(qAttr);
		 queryList = new ArrayList();
	}
	 
	public  void addList(String str){
		qAttr.add(str);
		this.queryListView.getItems().addAll(qAttr);
		qAttr.clear();
	}
	
	public void addQuery(String str){
		queryList.add(str);
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
	 }
	 public void handlecropVolumeCropType(){
		 addList("Crop volume per crop type.");
	 }	
	 
	 
}
