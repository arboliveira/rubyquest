/*
 * Created on 29/09/2009
 */
package rubyquest.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class Style {

	static void suppressRelativePathPrefixHandler(final Page page) {
		suppressRelativePathPrefixHandler(page, null);
	}

	static void suppressRelativePathPrefixHandlerNav(final Page page) {
		suppressRelativePathPrefixHandler(page, "nav");
	}

	private static void suppressRelativePathPrefixHandler(final Page page,
			final String suffix) {
		page.add(new WebMarkupContainer("suppress-RelativePathPrefixHandler"
				+ (suffix == null ? "" : "-" + suffix)));
	}

}
