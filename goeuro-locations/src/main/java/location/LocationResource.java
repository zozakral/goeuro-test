package location;

import java.io.IOException;
import java.io.InputStream;

public interface LocationResource {
	InputStream executeRequest(String name) throws IOException;
}
