/*
 * Created on 13/09/2009
 */
package rubyquest.data;

import java.io.File;

import rubyquest.io.InputStreamFactory;
import rubyquest.io.ResourceAsStreamFactory;

public class RubyQuestXml {

	private static final String ROOT_OF_CLASSPATH = "/";
	private static final String FILENAME = "rubyquest.xml";

	public static InputStreamFactory getResourceAsStream() {
		return new ResourceAsStreamFactory(ROOT_OF_CLASSPATH + FILENAME);
	}

	public static File newFile(final File parent) {
		return new File(parent, FILENAME);
	}
}
