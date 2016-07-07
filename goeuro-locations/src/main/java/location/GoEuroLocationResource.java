package location;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GoEuroLocationResource implements LocationResource {

	private String resourceLocation;

	public GoEuroLocationResource(String resourceLocation) {
		super();
		this.resourceLocation = resourceLocation;
	}

	@Override
	public InputStream executeRequest(String name) throws IOException {
		URL url = new URL(resourceLocation + name);
		InputStream inputStream = url.openStream();
		return inputStream;
	}
}
