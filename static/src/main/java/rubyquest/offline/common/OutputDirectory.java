/*
 * Created on 12/09/2009
 */
package rubyquest.offline.common;

import java.io.File;
import java.io.FileNotFoundException;

import rubyquest.io.FindDirectory;

public class OutputDirectory {

	private final File directory;

	public File getDirectory() {
		return directory;
	}

	public OutputDirectory() throws FileNotFoundException {
		final File root = FindDirectory.findParent("static");
		final File output = new File(root, "output");
		output.mkdir();
		this.directory = output;
	}
}
