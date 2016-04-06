package mc03;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mc03.controller.MainController;

public class Main extends Application{
    private Stage primaryStage;
    private VBox root;
	
public void start(Stage primaryStage) throws Exception{
	this.primaryStage= primaryStage;
	try{
	FXMLLoader loader= new FXMLLoader();
	loader.setLocation(Main.class.getResource("view/Main.fxml"));
	MainController controller = loader.<MainController>getController();
//Parent root= FXMLLoader.load(getClass().getResource("cypher.view/Main_Menu.fxml"));
	root=loader.load();
	
	Scene scene = new Scene(root);
primaryStage.setTitle("Non Restoring Division");
primaryStage.setScene(scene);
primaryStage.setResizable(false);
primaryStage.show();	             


}
	catch(IOException e){
		e.printStackTrace();
	}

}
  
  public static void main(String[] args)
  {	
	  launch(args);
  }
}
