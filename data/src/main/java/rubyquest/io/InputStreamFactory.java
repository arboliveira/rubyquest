package rubyquest.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamFactory {

	InputStream open() throws IOException;
}
