package location;

import java.io.IOException;
import java.util.List;

public interface LocationExtractor {
	void extract(List<Location> locations) throws IOException;
}
