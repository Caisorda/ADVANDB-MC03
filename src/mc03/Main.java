package mc03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main
  extends Application
{
  public void start(Stage primaryStage)
    throws Exception
  {
    FXMLLoader loader = 
      new FXMLLoader(getClass()
      .getResource("view/Main.fxml"));
    Parent root = (Parent)loader.load();
    Scene scene = new Scene(root);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("ADVANDB MCO3");
    primaryStage.setResizable(false);
    primaryStage.show();
  }
  
  public static void main(String[] args)
  {
    launch(args);
  }
}
