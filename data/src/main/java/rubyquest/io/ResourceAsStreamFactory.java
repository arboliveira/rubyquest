package rubyquest.io;

import java.io.IOException;
import java.io.InputStream;

public class ResourceAsStreamFactory implements InputStreamFactory {

	private final String name;

	public ResourceAsStreamFactory(final String name) {
		this.name = name;
	}

	@Override
	public InputStream open() throws IOException {
		return this.getClass().getResourceAsStream(name);
	}

}
