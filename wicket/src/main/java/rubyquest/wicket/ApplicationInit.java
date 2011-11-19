/*
 * Created on 13/09/2009
 */
package rubyquest.wicket;

import java.io.FileNotFoundException;

import org.apache.commons.lang.UnhandledException;
import org.apache.wicket.Application;
import org.apache.wicket.settings.IResourceSettings;

import rubyquest.webappdir.WebappDirectory;

public class ApplicationInit {

	public static void init(final Application application, final boolean offline) {
		final IResourceSettings resourceSettings = application
				.getResourceSettings();
		final String resourceFolder = offline ? newWebappDirectory()
				.getDirectory()
				.toString() : "src/main/webapp";
		resourceSettings.addResourceFolder(resourceFolder);
		resourceSettings.setResourceStreamLocator(new PathStripperLocator());
	}

	private static WebappDirectory newWebappDirectory() {
		try {
			return new WebappDirectory();
		} catch (final FileNotFoundException e) {
			throw new UnhandledException(e);
		}
	}

}
