/*
 * Created on 02/04/2010
 */
package rubyquest.generateresources;

import java.io.File;
import java.io.FileNotFoundException;

import rubyquest.io.FindDirectory;

public class TargetRubyquestDataDownloadDirectory {

	private final File directory;

	public File getDirectory() {
		return directory;
	}

	public TargetRubyquestDataDownloadDirectory() throws FileNotFoundException {
		final File root = FindDirectory.findParent("data");
		final File targetrdd = new File(root, "input/rubyquest-data-download");
		targetrdd.mkdir();
		this.directory = targetrdd;
	}

}
