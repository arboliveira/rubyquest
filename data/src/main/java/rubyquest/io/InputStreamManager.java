package rubyquest.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * http://www.educery.com/papers/patterns/resource/resource.manager.htm
 */
public class InputStreamManager {

	private final InputStreamFactory inFactory;

	public InputStreamManager(final InputStreamFactory inFactory) {
		this.inFactory = inFactory;
	}

	public <T> T openUsing(final InputStreamUsage<T> usage) throws IOException {
		final InputStream in = inFactory.open();
		try {
			return usage.use(in);
		} finally {
			in.close();
		}
	}
}