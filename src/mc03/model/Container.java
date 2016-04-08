package mc03.model;

import mc03.Constants;

public class Container {
	private String locationName;
	private static Container instance = null;
	private String blah;
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
	
	public String getblah(){
		return this.blah;
	}
	
	public void setblah(String x){
		this.blah=x;
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
