/*
 * Created on 01/04/2010
 */
package rubyquest.wicket;

import org.apache.wicket.markup.html.WebPage;

public class Begin extends WebPage {

	public static PageSpec newPageSpec() {
		return new SimplePageSpec(Begin.class);
	}

	public Begin(final Placement offline) {
		WebPageAll.onCreate(this);
		WebPageNavigable.onCreate(this);
		add(new LinkFactory(Title.newPageSpec(), offline).produce());
		add(new LinkFactory(TheMetalGlen.newPageSpec(), offline).produce());
	}

	public Begin() {
		this(Placement.Online);
	}

}
