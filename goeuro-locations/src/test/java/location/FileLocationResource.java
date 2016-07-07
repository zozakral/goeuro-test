package location;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLocationResource implements LocationResource {

	private String resourceLocation;

	public FileLocationResource(String resourceLocation) {
		super();
		this.resourceLocation = resourceLocation;
	}

	@Override
	public InputStream executeRequest(String name) throws IOException {
		Path path = Paths.get(resourceLocation + name);
		InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
		return inputStream;
	}

}
