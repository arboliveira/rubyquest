/*
 * Created on 2011-09-11
 */
package rubyquest.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class FileInputStreamFactory implements InputStreamFactory {

	private final File file;

	public FileInputStreamFactory(final File file) {
		this.file = file;
	}

	@Override
	public InputStream open() throws IOException {
		return new FileInputStream(file);
	}

}
