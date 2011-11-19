/*
 * Created on 01/04/2010
 */
package rubyquest.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;

class LinkFactory {

	private final Placement offline;
	private final String id;
	private final PageSpec spec;

	LinkFactory(final PageSpec spec, final Placement offline) {
		this(spec, offline, SimplePageSpec.getSimpleId(spec.getOnlineClass()));
	}

	LinkFactory(final PageSpec spec, final Placement offline, final String id) {
		this.spec = spec;
		this.offline = offline;
		this.id = id;
	}

	AbstractLink produce() {
		if (offline == Placement.Offline) {
			return new ExternalLink(id, spec.getOfflineName() + ".html");
		}
		return new BookmarkablePageLink<Page>(id, spec.getOnlineClass());
	}
}
