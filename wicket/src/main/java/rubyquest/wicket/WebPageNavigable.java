/*
 * Created on 25/04/2010
 */
package rubyquest.wicket;

import org.apache.wicket.markup.html.WebPage;

final class WebPageNavigable {

	private WebPageNavigable() {
		// never instantiate
	}

	static void onCreate(final WebPage page) {
		Style.suppressRelativePathPrefixHandlerNav(page);
	}
}
