package rubyquest.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * http://www.educery.com/papers/patterns/resource/resource.manager.htm
 */
public interface OutputStreamUsage {

	void use(OutputStream out) throws IOException;
}