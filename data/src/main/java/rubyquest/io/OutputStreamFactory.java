package rubyquest.io;

import java.io.IOException;
import java.io.OutputStream;

public interface OutputStreamFactory {

	OutputStream open() throws IOException;
}
