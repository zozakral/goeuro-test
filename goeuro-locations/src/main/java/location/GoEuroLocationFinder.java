package location;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class GoEuroLocationFinder implements LocationFinder {
	private LocationResource resource;

	
	public GoEuroLocationFinder(LocationResource resource) {
		super();
		this.resource = resource;
	}

	@Override
	public List<Location> findLocation(String name) throws IOException {
		InputStream inputStrem = resource.executeRequest(name);
		JsonReader jsonReader = Json.createReader(inputStrem);
		JsonArray jsonLocationList = jsonReader.readArray();
		
		List<Location> foundLocations = new ArrayList<>();

		for (JsonObject jsonLocation : jsonLocationList.getValuesAs(JsonObject.class)) {
			Location location = createLocationObject(jsonLocation);
			foundLocations.add(location);
		}
		
		return foundLocations;
	}

	private Location createLocationObject(JsonObject jsonLocation) {
		Location location = new Location();
		
		location.setId(jsonLocation.getJsonNumber(Location._ID).bigDecimalValue());
		location.setName(jsonLocation.getString(Location.NAME));
		location.setFullName(jsonLocation.getString(Location.FULL_NAME));
		location.setType(jsonLocation.getString(Location.TYPE));
		location.setCountry(jsonLocation.getString(Location.COUNTRY));
		
		JsonObject geoPosition = jsonLocation.getJsonObject(Location.GEO_POSITION);		
		location.setLatitude(geoPosition.getJsonNumber(Location.LATITUDE).bigDecimalValue());
		location.setLongitude(geoPosition.getJsonNumber(Location.LONGITUDE).bigDecimalValue());
		
		return location;
	}

}
