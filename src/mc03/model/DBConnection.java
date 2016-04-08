package mc03.model;

import mc03.Constants;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static DBConnection instance = null;

	private static String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/";
	private String database = "";
	private String username = Constants.DB_USERNAME;
	private String password = Constants.DB_PASSWORD;

	public static synchronized DBConnection getInstance() {
		if (instance == null) {
			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println(" new dbconnect");
			instance = new DBConnection();
		}

		return instance;
	}

	public static synchronized Connection getConnection(String schema) {

		if (instance == null) {
			instance = new DBConnection();
		}
		
		try {
			System.out.println("DBConnection.java: Location name is: " + Container.getInstance().getDatabaseName());
			String newUrl = getInstance().url + Container.getInstance().getDatabaseName();
			System.out.println(newUrl + " chenelyn");
			return DriverManager.getConnection(newUrl, getInstance().username, getInstance().password);
		} catch (Exception e) {
			System.out.println("DBConnection.java: Couldn\'t connect");
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
