/*
 * Created on 02/04/2010
 */
package rubyquest.generateresources;

import java.io.File;
import java.io.FileNotFoundException;

import rubyquest.data.RubyQuestXml;
import rubyquest.io.FindDirectory;

public class RubyQuestXmlToWrite {

	private final File file;

	public RubyQuestXmlToWrite() throws FileNotFoundException {
		final File wicket = FindDirectory.find("wicket");
		final File resourcesMaven = new File(wicket, "src/main/resources");
		this.file = RubyQuestXml.newFile(resourcesMaven);
	}

	public File getFile() {
		return file;
	}

}
