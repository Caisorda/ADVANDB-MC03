package mc03.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mc03.Main;
import mc03.model.Container;
import mc03.view.SoftwareNotification;

public class LoginController implements Initializable {

    @FXML
    ToggleGroup chosenSite;
    @FXML
    RadioButton radioCentral;
    @FXML
    RadioButton radioMarinduque;
    @FXML
    RadioButton radioPalawan;
    @FXML
    Button connectButton;
    @FXML
    Text firstText;
    @FXML
    Text secondText;
    @FXML
    Text ipAddress;

    private boolean checker;
    private String siteLocationName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            System.out.println("haal");
            String temp = "" + InetAddress.getLocalHost();
            ipAddress.setText(temp);
        } catch (UnknownHostException e) {
            System.out.println("initialize() @ LoginController.java: An UnknownHostException occurred.");
            e.printStackTrace();
        }
        
        
        
        chosenSite.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		    	
		    	   if (chosenSite.getSelectedToggle() ==  radioCentral) {
		    		   siteLocationName = radioCentral.getText();
			         }
		    	   else if(chosenSite.getSelectedToggle() ==  radioMarinduque) {
		    		   siteLocationName = radioMarinduque.getText();
			             
			         }
		    	   else if(chosenSite.getSelectedToggle() ==  radioPalawan) {
		    		   siteLocationName = radioPalawan.getText();
			             
			         }
		    	  
		     } 
		});
    }

    public void HandleConnect() throws IOException { 
    	if (chosenSite.getSelectedToggle() ==  null) {
			             SoftwareNotification.notifyError("Please select server first.");
			         }else{
//        if (radioCentral.isSelected()) {
//            siteLocationName = radioCentral.getText();
//        } else if (radioMarinduque.isSelected()) {
//            siteLocationName = radioMarinduque.getText();
//        } else if (radioPalawan.isSelected()) {
//            siteLocationName = radioPalawan.getText();
//        }
        System.out.println("HandleConnect() @ LoginController.java: User is logging in as: " + siteLocationName);
        // And then you can use siteLocationName to wherever you need it.

        // -----

        System.out.println("connect has been clicked.");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("view/Main.fxml"));

        loader.setLocation(Main.class.getResource("view/Main.fxml"));
        Parent root = loader.load();
        MainController controller =
                loader.<MainController>getController();
        controller.initializeVariables();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("ADVANDB MC03");
        stage.show(); 
        SoftwareNotification.notifySuccess("IP Address: "+ipAddress.getText());
        SoftwareNotification.notifySuccess("Logged in as: "+siteLocationName+
        		" IP Address: "+ipAddress.getText());
        Container.getInstance().setLocationName(siteLocationName);
			         }
    }
    /*
    * Description: Updates the UI with active connections with other machines.
    * Sample output: "1) Marinduque @ 192.168.1.101" or "2) Palawan @ 192.168.1.102"
    * Parameters:
    *   connectionNumber - can only be either 1 or 2 para sa '1)' and '2)' in the UI
    *   siteLocationName - if connection is from Central, Marinduque, or Palawan
    *   ipAddress - the IP address of the other site
    * */
    public void updateConnectionList(int connectionNumber, String siteLocationName, String ipAddress) {
        String str = "";
        if (connectionNumber == 1) {
            str += "1) " + siteLocationName + " @ " + ipAddress;
            firstText.setText(str);
        } else if (connectionNumber == 2) {
            str += "2) " + siteLocationName + " @ " + ipAddress;
            secondText.setText(str);
        }
    }

    // Returns 'Central', 'Marinduque', or 'Palawan' without quotes.
    public String getSiteLocationName() {
        return siteLocationName;
    }

}
