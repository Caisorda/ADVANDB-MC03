package mc03;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mc03.controller.LoginController;
import mc03.controller.MainController;

public class Main extends Application{
    private Stage primaryStage;
    private AnchorPane root;
    final private ExecutorService executorService = Executors.newSingleThreadExecutor();
	
public void start(Stage primaryStage) throws Exception{
	this.primaryStage= primaryStage;
	try{
	FXMLLoader loader= new FXMLLoader();
	loader.setLocation(Main.class.getResource("view/Login.fxml"));
	LoginController controller = loader.<LoginController>getController();
	//controller.initialize();
//Parent root= FXMLLoader.load(getClass().getResource("cypher.view/Main_Menu.fxml"));
	root=loader.load();
	
	Scene scene = new Scene(root);
primaryStage.setTitle("LOGIN");
primaryStage.setScene(scene);
primaryStage.setResizable(false);
primaryStage.show();	             
reciever r = new reciever(9876,1024,1024);
executorService.execute(r);

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
