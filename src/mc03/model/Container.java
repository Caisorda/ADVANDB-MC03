package mc03.model;

public class Container {
	private String locationName;
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
		if (locationName.equals("Central")) {
			return "db_hpq";
		} else if (locationName.equals("Marinduque")) {
			return "db_hpq_marinduque";
		} else if (locationName.equals("Palawan")) {
			return "db_hpq_palawan ";
		}
		return null;
	}

}
