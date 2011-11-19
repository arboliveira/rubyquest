/*
 * Created on 10/09/2009
 */
package rubyquest.rubyhtml.common;

import java.io.File;

import rubyquest.generateresources.TargetRubyquestDataDownloadDirectory;

public class RubyHtml {

	private static final String LOCAL_FILENAME_RUBY_HTML = "ruby.html";

	private final TargetRubyquestDataDownloadDirectory parent;
	private final File rubyhtmlFile;

	public File getFile() {
		return rubyhtmlFile;
	}

	public RubyHtml(final TargetRubyquestDataDownloadDirectory parent) {
		this.parent = parent;
		final File dir = parent.getDirectory();
		this.rubyhtmlFile = new File(dir, LOCAL_FILENAME_RUBY_HTML);
	}

	public TargetRubyquestDataDownloadDirectory getParent() {
		return parent;
	}

}
