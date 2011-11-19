package rubyquest.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * http://www.educery.com/papers/patterns/resource/resource.manager.htm
 */
public class OutputStreamManager {

	private final OutputStreamFactory outFactory;

	public OutputStreamManager(final OutputStreamFactory outFactory) {
		this.outFactory = outFactory;
	}

	public void openUsing(final OutputStreamUsage usage) throws IOException {
		final OutputStream out = outFactory.open();
		try {
			usage.use(out);
		} finally {
			out.close();
		}
	}
}