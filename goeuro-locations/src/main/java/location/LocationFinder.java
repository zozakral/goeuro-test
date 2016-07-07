package location;

import java.io.IOException;
import java.util.List;

public interface LocationFinder {
	List<Location> findLocation(String name) throws IOException;
}