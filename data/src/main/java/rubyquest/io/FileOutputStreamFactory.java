/*
 * Created on 2011-09-11
 */
package rubyquest.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class FileOutputStreamFactory implements OutputStreamFactory {

	private final File file;

	public FileOutputStreamFactory(final File file) {
		this.file = file;
	}

	@Override
	public OutputStream open() throws IOException {
		file.getParentFile().mkdirs();
		return new FileOutputStream(file);
	}

}
