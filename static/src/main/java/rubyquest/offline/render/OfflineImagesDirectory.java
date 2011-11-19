package rubyquest.offline.render;

import java.io.File;

import rubyquest.offline.Images;


public class OfflineImagesDirectory {

	private final File directory;

	public File getDirectory() {
		return directory;
	}

	public OfflineImagesDirectory(final OfflineOutputDirectory parent) {
		this.directory = new File(parent.getDirectory(), Images.IMAGES);
	}

}
