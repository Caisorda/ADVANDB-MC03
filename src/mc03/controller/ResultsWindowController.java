 package mc03.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import mc03.Main;
import mc03.reciever;
import mc03.sender;
import mc03.view.SoftwareNotification;

public class ResultsWindowController implements Initializable{
	
	@FXML TableView tblResults;
	
	
	
	public void openResultsWindow(List<String> results,String[] colnames,int numcol) throws IOException{
		
		System.out.println("\n\n\n\n\n\n\n****************************");
		System.out.println("RECEIVED>");
		System.out.println("colnames: " +colnames[0]);
		FXMLLoader loader = new FXMLLoader(getClass()
				.getResource("view/ResultsWindow.fxml"));
		
		
		loader.setLocation(Main.class.getResource("view/ResultsWindow.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultsWindowController controller =
				loader.<ResultsWindowController>getController();
		controller.initializeData(results,colnames,numcol);  
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Resu11111lts");
		stage.show();
	}
	public void getData(){
		
	}
	public void initializeData( ResultSet resultSet)
	  {
	    try
	    {
	   
	      
	      ResultSetMetaData metaData = resultSet.getMetaData();
	      for (int i = 0; i < metaData.getColumnCount(); i++)
	      {
	        TableColumn column = 
	          new TableColumn(metaData.getColumnLabel(i + 1));
	        
	        final int j = i;
	        column.setCellValueFactory(new Callback()
	        {
	            @Override
	            public Object call(Object p) {
	            Object o = ((ObservableList)((TableColumn.CellDataFeatures<ObservableList, String>)p).getValue()).get(j);
	            return new SimpleStringProperty(o == null ? "NULL" : o.toString());
	          }
	        });
	        this.tblResults.getColumns().add(column);
	      }
	      ObservableList<ObservableList> data = 
	        FXCollections.observableArrayList();
	      while (resultSet.next())
	      {
	        ObservableList<String> row = 
	          FXCollections.observableArrayList();
	        for (int i = 0; i < metaData.getColumnCount(); i++) {
	          row.add(resultSet.getString(i + 1));
	        }
	        data.add(row);
	      }
	      this.tblResults.setItems(data);
	      this.tblResults.refresh();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	  }
	public void initializeData(List<String> list,String[] columnName,int numColumns){
		
		try{
		  for (int i = 0; i < numColumns; i++)
	      {
	        TableColumn column = 
	          new TableColumn(columnName[i]);
	        
	        final int j = i;
	        column.setCellValueFactory(new Callback()
	        {
	            @Override
	            public Object call(Object p) {
	            Object o = ((ObservableList)((TableColumn.CellDataFeatures<ObservableList, String>)p).getValue()).get(j);
	            return new SimpleStringProperty(o == null ? "NULL" : o.toString());
	          }
	        });
	        this.tblResults.getColumns().add(column);
	      }
		  ObservableList<ObservableList> data = 
			        FXCollections.observableArrayList();
		  System.out.println(list.size());ObservableList<String> row = null;
		  
		  int count=0;
		 for(int i=0;i<list.size();i++)
	      {
			  System.out.println("loop:"+i);
			  System.out.println(list.size()); 
			  if((i)%2==0){
				  System.out.println("start"); 
		      row = FXCollections.observableArrayList();
		      System.out.println("Loop #" + i + " : " + list.get(i));
		      row.add(list.get(i));
			 
			  }else{
				  System.out.println("last"); 
			  row.add(list.get(i));
			  data.add(row);
			  }
			  
			 
//	        ObservableList<String> row = 
//	          FXCollections.observableArrayList();
//	     int  temp=i;
//	        for (int x = 0; x < numColumns; x++) {
//	        	System.out.println("loop?");
//	        	row.add(list.get(i));
//	        	temp++;;
//	        
//	         System.out.println("count: " +count+"I: "+i);
//	        }
//	        System.out.println("count: " + count);
//	        count++;
//	        
//	        if (i%numColumns==0)
//	        data.add(row);
	      }
		 
		 

		 this.tblResults.setItems(data);
	     this.tblResults.refresh();
		}catch(Exception e){
			SoftwareNotification.notifyError("Error @ Results Window Controller.");
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		
	
		
	}
	
}
