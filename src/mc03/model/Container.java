package mc03.model;

import mc03.Constants;

import java.util.ArrayList;
import java.util.List;

public class Container {
	private String locationName;
	private String blah;
	private static Container instance = null;

	public static Container getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new Container();
		}

		return instance;
	}

	public Container() {

	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getDatabaseName() {
		System.out.println("Container.java: locationName is: " + locationName);
		if (locationName.equals(Constants.CENTRAL)) {
			return "db_hpq";
		} else if (locationName.equals(Constants.MARINDUQUE)) {
			return "db_hpq_marinduque";
		} else if (locationName.equals(Constants.PALAWAN)) {
			return "db_hpq_palawan ";
		}

		return null;
	}

}
