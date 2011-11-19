package rubyquest.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * http://www.educery.com/papers/patterns/resource/resource.manager.htm
 */
public interface InputStreamUsage<T> {

	T use(InputStream in) throws IOException;
}