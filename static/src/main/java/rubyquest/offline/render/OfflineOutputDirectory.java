/*
 * Created on 22/09/2009
 */
package rubyquest.offline.render;

import java.io.File;

import rubyquest.offline.common.OutputDirectory;

public class OfflineOutputDirectory {

	private final File directory;

	public OfflineOutputDirectory(final OutputDirectory parent,
			final String leafname) {
		final File offline = new File(parent.getDirectory(), "offline");
		this.directory = new File(offline, leafname);
		this.directory.mkdirs();
	}

	public File getDirectory() {
		return directory;
	}

	public File htmlFile(final String html) {
		return new File(directory, html + ".html");
	}

}
