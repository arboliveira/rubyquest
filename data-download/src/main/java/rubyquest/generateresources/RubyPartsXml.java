/*
 * Created on 02/04/2010
 */
package rubyquest.generateresources;

import java.io.File;

public class RubyPartsXml {

	private final File partsxml;
	private final TargetRubyquestDataDownloadDirectory parent;

	public RubyPartsXml(final TargetRubyquestDataDownloadDirectory parent) {
		this.parent = parent;
		final File dir = parent.getDirectory();
		this.partsxml = new File(dir, "ruby-parts.xml");
	}

	public File getPartsXml() {
		return this.partsxml;
	}

	public TargetRubyquestDataDownloadDirectory getParent() {
		return parent;
	}

}
