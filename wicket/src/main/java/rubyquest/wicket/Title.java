package rubyquest.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;

public class Title extends WebPage {

	public static PageSpec newPageSpec() {
		return new Spec();
	}

	static class Spec implements PageSpec {

		@Override
		public String getOfflineName() {
			return "index";
		}

		@Override
		public Class< ? extends Page> getOnlineClass() {
			return Title.class;
		}

	}

	public Title(final Placement offline) {
		WebPageAll.onCreate(this);
		WebPageNavigable.onCreate(this);
		add(new LinkFactory(new SimplePageSpec(Begin.class), offline).produce());
		add(new LinkFactory(new SimplePageSpec(Menu.class), offline).produce());
	}

	public Title() {
		this(Placement.Online);
	}

}
