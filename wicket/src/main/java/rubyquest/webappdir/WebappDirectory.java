/*
 * Created on 03/04/2010
 */
package rubyquest.webappdir;

import java.io.File;
import java.io.FileNotFoundException;

import rubyquest.io.FindDirectory;

public class WebappDirectory {

	private final File directory;

	public WebappDirectory() throws FileNotFoundException {
		final File dynamic = FindDirectory.find("dynamic");
		final File webapp = new File(dynamic, "src/main/webapp");
		this.directory = webapp;
	}

	public File getDirectory() {
		return directory;
	}
}
