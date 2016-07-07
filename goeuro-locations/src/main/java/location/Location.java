package location;

import java.math.BigDecimal;

public class Location {
	public static final String _ID = "_id";
	public static final String NAME = "name";
	public static final String FULL_NAME = "fullName";
	public static final String TYPE = "type";
	public static final String COUNTRY = "country";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String GEO_POSITION = "geo_position";
	
	private BigDecimal id;
	private String name;
	private String fullName;
	private String type;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String country;

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
