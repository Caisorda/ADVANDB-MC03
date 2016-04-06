package mc03.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mc03.Main;

public class LoginController {
	
	@FXML CheckBox centralCheckbox;
	@FXML CheckBox marinduqueCheckbox;
	@FXML CheckBox palawanCheckbox;
	@FXML Button   connectButton;
	@FXML Text 	   firstText;
	@FXML Text     secondText;
	@FXML Text	   ipAddress;
	public void HandleConnect() throws IOException{
	
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
	}
	public void initialize() throws UnknownHostException {
		// TODO Auto-generated method stub
		System.out.println("haal");
		String temp = ""+InetAddress.getLocalHost();
		ipAddress.setText(temp);
	}
	
	

}
