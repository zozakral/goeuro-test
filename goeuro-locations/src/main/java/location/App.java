package location;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class App {
	private static final String THE_RESULT_HAS_BEEN_SAVED_IN = "The result has been saved in :\n";

	private static final String COMMAND_HELP_MESSAGE = "This command send a search request for a given location name to "
			+ "\"http://api.goeuro.com/api/v2/position/suggest/en/\" and save the result in a given CSV file.\n\n"
			+ "goeuro [searched location name] [CSV file fullname]\n";
	
	private static final String SERVICE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
	private static GoEuroLocationFinder urlLocationFinder;

	public static void main(String[] args) {
		if (args.length != 2)
			System.out.println(COMMAND_HELP_MESSAGE);
		else {
			Path path = Paths.get(args[1]);
			try {
				initialize();

				List<Location> foundLocations = urlLocationFinder.findLocation(args[0]);
				LocationExtractor extractor = new LocationExtractorToCsvFile(args[1]);
				extractor.extract(foundLocations);
				if (Files.exists(path))
					System.out.println(THE_RESULT_HAS_BEEN_SAVED_IN + path.toAbsolutePath());

			} catch (FileAlreadyExistsException x) {
				System.err.format("file named %s" + " already exists%n", path.toAbsolutePath());
			} catch (IOException x) {
				System.err.format("createFile error: %s%n", x);
			}
		}
	}

	private static void initialize() {
		LocationResource urlResource = new GoEuroLocationResource(SERVICE_URL);
		urlLocationFinder = new GoEuroLocationFinder(urlResource);
	}
}
