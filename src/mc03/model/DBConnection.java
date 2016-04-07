package mc03.model;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.*;

public class DBConnection {
	private static DBConnection instance = null;

	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/";
	private String database = "";
	private String username = "";
	private String password = "";

	public static synchronized DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}

		return instance;
	}

	public static synchronized Connection getConnection(String schema) {

		if (instance == null) {
			instance = new DBConnection();
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("DBConnection.java: Location name is: " + Container.getInstance().getDatabaseName());
			return DriverManager.getConnection(instance.getUrl() + schema + Container.getInstance().getDatabaseName(), instance.getUsername(),
					instance.getPassword());
		} catch (Exception e) {
			System.out.println("Couldnt connect");
			e.printStackTrace();
		}

		return null;

	}

	public String getUrl() {
		return url;
	}

	/**
	 * returns database name
	 *
	 * @return database name
	 */
	public String getDatabase() {
		return database;
	}

	public String getUsername() {
		return username;
	}

	private String getPassword() {
		return password;
	}

}