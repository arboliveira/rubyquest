/*
 * Created on 20/09/2009
 */
package rubyquest.wicket;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;

import rubyquest.data.RubyQuestData;

public class Endtitle extends WebPage {

	public static PageSpec newPageSpec() {
		return new SimplePageSpec(Endtitle.class);
	}

	public Endtitle() {
		this(RubyQuest.get().getLeftPadWithZeroes(), RubyQuest
				.get()
				.getRubyQuestData(), Placement.Online);
	}

	public Endtitle(final IntToString pad, final RubyQuestData data,
			final Placement offline) {
		WebPageAll.onCreate(this);
		WebPageNavigable.onCreate(this);
		add(Scene.newLink(offline, "previous", data.sceneCount(), pad));
		add(End.newLinkToShocker(offline, "next"));
	}

	public static MarkupContainer newLink(final String id,
			final Placement offline) {
		return new LinkFactory(Endtitle.newPageSpec(), offline, id).produce();
	}

}
