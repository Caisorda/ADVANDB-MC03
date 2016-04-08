package mc03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mc03.controller.LoginController;
import mc03.controller.MainController;
import mc03.network.Client;

public class Main extends Application{
    private Stage primaryStage;
    private AnchorPane root;
    final private ExecutorService executorService = Executors.newSingleThreadExecutor();

    List<String> queryList = new ArrayList();
    String[] columnName;
    int numColumns;
//Thread catcher = new Thread(new reciever(Constants.PORT_SERVER, 1024, 1024));

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
primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	@Override
	public void handle(WindowEvent event) {
		Platform.exit();
		System.exit(0);
	}
});
primaryStage.show();	             
 List<String> queryList = new ArrayList();

CommunicationHandler comhandler = new CommunicationHandler();
 


//sender man = new sender();
//catcher.start();
//String query ="select (CASE WHEN croptype=1 THEN 'SUGAR CANE'"+
//        "WHEN croptype=2 THEN 'PALAY'"+
//        "WHEN croptype=3 THEN 'CORN'"+
//        "WHEN croptype=4 THEN 'COFFEE'"+
//        "ELSE 'OTHER'"+
//		 "END) crop_name, count(hh.id)"+
//		 "from hpq_hh hh "+
//		 "inner join hpq_crop crop"+
//		 " on(crop.hpq_hh_id = hh.id)"+
//		 " where croptype is not null "+
//		 "group by crop.croptype";

//man.DoQuery(query);

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
