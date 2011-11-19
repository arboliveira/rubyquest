package rubyquest.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;

public class Menu extends WebPage {

	public static PageSpec newPageSpec() {
		return new SimplePageSpec(Menu.class);
	}

	public Menu(final Placement offline, final IntToString pad) {
		WebPageAll.onCreate(this);
		add(Scene.newLink(offline, "motionpicture", 1, pad));
		add(SceneSelection.newLinkToSceneSelection("sceneselection", offline));
		add(new LinkFactory(Storyboard.newPageSpec(), offline).produce());
		add(new LinkFactory(About.newPageSpec(), offline).produce());
		add(new LinkFactory(Bonus.newPageSpec(), offline).produce());
		add(new LinkFactory(Title.newPageSpec(), offline).produce());
	}

	public Menu() {
		this(Placement.Online, buildRenderingNeedsFromWebApplication());
	}

	private static IntToString buildRenderingNeedsFromWebApplication() {
		final RubyQuest application = (RubyQuest) Application.get();
		final IntToString pad = application.getLeftPadWithZeroes();
		return pad;
	}

}
