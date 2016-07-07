
package location;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LocationExtractorToCsvFile implements LocationExtractor {
	private String fileName;

	public LocationExtractorToCsvFile(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public void extract(List<Location> locations) throws IOException {
		List<String> rows = getLocationsAsCsvRows(locations);
		Path path = Paths.get(fileName);
		Files.createFile(path);
		BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
				StandardOpenOption.APPEND);
		for (String line : rows){
			bufferedWriter.write(line);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}
	
	private List<String> getLocationsAsCsvRows(List<Location> locations) throws IOException {
		String comma = ", ";
		String headerLine = Location._ID + comma +
				            Location.NAME + comma + 
				            Location.TYPE + comma + 
				            Location.LATITUDE + comma + 
				            Location.LONGITUDE ;
		List<String> locationsInCsvFormat = new ArrayList<>();
		locationsInCsvFormat.add(headerLine);
		for(Location location : locations){
			String csvLocation = location.getId().toString() + comma  + 
						  location.getName() + comma + 
						  location.getType() + comma +
						  location.getLatitude() + comma +
						  location.getLongitude();
			locationsInCsvFormat.add(csvLocation);
		}
		return locationsInCsvFormat;
	}
}
