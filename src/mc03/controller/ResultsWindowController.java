 package mc03.controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ResultsWindowController {
	
	@FXML TableView results;
	
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
	        this.results.getColumns().add(column);
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
	      this.results.setItems(data);
	      this.results.refresh();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	  }
	
	
}
