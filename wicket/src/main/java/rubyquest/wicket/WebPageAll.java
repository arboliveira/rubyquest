/*
 * Created on 25/04/2010
 */
package rubyquest.wicket;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

final class WebPageAll {

	private WebPageAll() {
		// never instantiate
	}

	static void onCreate(final WebPage page) {
		Style.suppressRelativePathPrefixHandler(page);
		addGoogleAnalyticsScript(page);
	}

	private static void addGoogleAnalyticsScript(final WebPage page) {
		final InputStream js = page.getClass().getResourceAsStream(
				"ga-execute.js");
		final String jsstr = toString(js);
		final Label label = new Label("google-analytics", jsstr);
		label.setRenderBodyOnly(true);
		label.setEscapeModelStrings(false);
		page.add(label);
	}

	private static String toString(final InputStream js) {
		try {
			return IOUtils.toString(js);
		} catch (final IOException e) {
			throw new UnhandledException(e);
		}
	}
}
