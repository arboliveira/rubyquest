/*
 * Created on 02/10/2009
 */
package rubyquest.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;

public class TheMetalGlen extends WebPage {

	public static PageSpec newPageSpec() {
		return new SimplePageSpec(TheMetalGlen.class);
	}

	public TheMetalGlen() {
		this(Placement.Online, buildRenderingNeedsFromWebApplication());
	}

	public TheMetalGlen(final Placement offline, final IntToString pad) {
		WebPageAll.onCreate(this);
		WebPageNavigable.onCreate(this);
		add(new LinkFactory(Begin.newPageSpec(), offline).produce());
		add(Scene.newLink(offline, "next", 1, pad));
	}

	private static IntToString buildRenderingNeedsFromWebApplication() {
		final RubyQuest application = (RubyQuest) Application.get();
		final IntToString pad = application.getLeftPadWithZeroes();
		return pad;
	}

}
