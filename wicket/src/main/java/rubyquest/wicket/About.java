/*
 * Created on 20/09/2009
 */
package rubyquest.wicket;

import org.apache.wicket.markup.html.WebPage;

import rubyquest.data.RubyQuestData;

public class About extends WebPage {

	public About() {
		this(Placement.Online, RubyQuest.get().getRubyQuestData());
	}

	public About(final Placement offline, final RubyQuestData data) {
		WebPageAll.onCreate(this);
		add(new LinkFactory(new SimplePageSpec(Menu.class), offline).produce());
		add(new ExternalImage("img", ImagenameRubyThumbnail.get(offline, data)));
	}

	public static PageSpec newPageSpec() {
		return new SimplePageSpec(About.class);
	}

}
