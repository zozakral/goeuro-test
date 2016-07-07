package location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author zslavova
 *
 */
public class LocationTest {
	private static final String MUNICH_JSON = "Munich.json";
	private static final String VARNA_JSON = "Varna.json";
	private static final String SOFIA_JSON = "Sofia.json";
	private static final String SERVICE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
	private static final String EXPECTED_HEADER_LINE = "_id, name, type, latitude, longitude";
	private static final String EXPECTED_FIRST_LINE = "376946, Munich, location, 48.13743, 11.57549";
	private static final String EXPECTED_SECOND_LINE = "313870, Munich, airport, 48.354597, 11.785065";

	private LocationFinder urlLocationFinder;
	private LocationFinder fileLocationFinder;
	private String resourcePath;

	@Before
	public void initialize() throws URISyntaxException {
		LocationResource urlResource = new GoEuroLocationResource(SERVICE_URL);
		urlLocationFinder = new GoEuroLocationFinder(urlResource);

		ClassLoader classLoader = getClass().getClassLoader();
		Path path = Paths.get(classLoader.getResource(MUNICH_JSON).toURI());
		String pathInString = path.toAbsolutePath().toString();
		resourcePath = pathInString.substring(0, pathInString.indexOf(MUNICH_JSON));
		LocationResource fileResource = new FileLocationResource(resourcePath);
		fileLocationFinder = new GoEuroLocationFinder(fileResource);
	}

	@Test
	public void canSendFindRequestRequest() throws IOException {
		String locationName = "zoza";
		List<Location> foundLocation = urlLocationFinder.findLocation(locationName);
		assertTrue(foundLocation.isEmpty());
	}

	@Test
	public void checkCountOfReturnedResults() throws IOException {
		List<Location> foundLocation = fileLocationFinder.findLocation(MUNICH_JSON);
		assertEquals(foundLocation.size(), 2);
		foundLocation = fileLocationFinder.findLocation(VARNA_JSON);
		assertEquals(foundLocation.size(), 7);
		foundLocation = fileLocationFinder.findLocation(SOFIA_JSON);
		assertEquals(foundLocation.size(), 5);

	}

	@Test
	public void checkIfResultContainsSearchedString() throws IOException {
		String searchedLocationName = "Varna";
		List<Location> foundLocations = urlLocationFinder.findLocation(searchedLocationName);
		for (Location location : foundLocations) {
			String locationName = location.getName();
			assertTrue(locationName.contains(searchedLocationName));
		}
	}

	@Test
	public void checkIfAllFieldsAreSet() throws IOException {
		BigDecimal locationId = new BigDecimal("376946");
		String locationName = "Munich";
		String locationFullName = "Munich, Germany";
		String locationType = "location";
		String locationCountry = "Germany";
		BigDecimal locationLatitude = new BigDecimal("48.13743");
		BigDecimal locationLongitude = new BigDecimal("11.57549");

		List<Location> foundLocations = fileLocationFinder.findLocation(MUNICH_JSON);
		Location firstLocation = foundLocations.iterator().next();
		assertTrue(firstLocation.getId().equals(locationId));
		assertTrue(firstLocation.getName().equals(locationName));
		assertTrue(firstLocation.getFullName().equals(locationFullName));
		assertTrue(firstLocation.getType().equals(locationType));
		assertTrue(firstLocation.getCountry().equals(locationCountry));
		assertTrue(firstLocation.getLatitude().equals(locationLatitude));
		assertTrue(firstLocation.getLongitude().equals(locationLongitude));
	}

	@Test
	public void checkExtractedFile() throws IOException {
		List<Location> foundLocations = fileLocationFinder.findLocation(MUNICH_JSON);

		Path path = extractLocationsToCsvFile(foundLocations);
		assertTrue(Files.exists(path));

		checkCsvFormat(path);
		Files.delete(path);
	}

	private Path extractLocationsToCsvFile(List<Location> foundLocations) throws IOException {
		String extractedFileName = "ExtractedLocations.csv";
		Path path = Paths.get(resourcePath + extractedFileName);
		if (Files.exists(path))
			Files.delete(path);
		LocationExtractor extractor = new LocationExtractorToCsvFile(resourcePath + extractedFileName);
		extractor.extract(foundLocations);
		return path;
	}

	private void checkCsvFormat(Path path) throws IOException {
		List<String> readLinesFromFile = Files.readAllLines(path);
		Iterator<String> readLinesIterator = readLinesFromFile.iterator();

		assertEquals(readLinesFromFile.size(), 3);
		assertTrue(readLinesIterator.next().equals(EXPECTED_HEADER_LINE));
		assertTrue(readLinesIterator.next().equals(EXPECTED_FIRST_LINE));
		assertTrue(readLinesIterator.next().equals(EXPECTED_SECOND_LINE));
	}
}
