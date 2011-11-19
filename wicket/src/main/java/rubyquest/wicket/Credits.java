package rubyquest.wicket;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.Model;

public class Credits extends WebPage {

	public static PageSpec newSpec(final int i) {
		return new NamedPageSpec(htmlfile(i), Credits.class);
	}

	static final class Credit {

		private final String credit;

		public String getCredit() {
			return credit;
		}

		public String getName() {
			return name;
		}

		private final String name;

		Credit(final String credit, final String name) {
			this.credit = credit;
			this.name = name;
		}
	}

	public static int count() {
		return credits.length;
	}

	public Credits(final PageParameters parameters) {
		this(Placement.Online, buildRenderingNeeds(parameters));
	}

	public Credits(final Placement offline, final int credit) {
		if (credit > credits.length) {
			throw new IllegalArgumentException();
		}
		this.credit = credit;
		WebPageAll.onCreate(this);
		WebPageNavigable.onCreate(this);
		populateMarkup(offline);
	}

	private void populateMarkup(final Placement offline) {
		final Credit c = credits[credit - 1];
		final Label creditLabel = new Label("credit", c.getCredit());
		final Label nameLabel = new Label("name", c.getName());
		if (credit == credits.length) {
			creditLabel.add(newFontSizeDouble());
			nameLabel.add(newFontSizeDouble());
		}
		add(creditLabel);
		add(nameLabel);
		add(newLinkToNextPage("next", offline));
		add(newLinkToPreviousPage("previous", offline));
	}

	private static AttributeModifier newFontSizeDouble() {
		return new AttributeModifier("style", true, new Model<String>(
				"font-size: 2.0em;"));
	}

	private Component newLinkToNextPage(final String id, final Placement offline) {
		if (credit == credits.length) {
			return End.newLinkToTheEnd(id, offline);
		}
		final int i = credit + 1;
		return newLinkToCredits(offline, id, i);
	}

	private Component newLinkToPreviousPage(final String id,
			final Placement offline) {
		if (credit == 1) {
			return End.newLinkToShocker(offline, id);
		}
		final int i = credit - 1;
		return newLinkToCredits(offline, id, i);
	}

	static MarkupContainer newLinkToCredits(final Placement offline,
			final String id, final int i) {
		if (offline == Placement.Offline) {
			return new ExternalLink(id, htmlfile(i) + ".html");
		}
		return new BookmarkablePageLink<Credits>(id, Credits.class,
				new PageParameters("credit=" + i));
	}

	private static int buildRenderingNeeds(final PageParameters parameters) {
		final int end = parameters.getAsInteger("credit").intValue();
		return end;
	}

	private static String htmlfile(final int i) {
		return "credits-" + i;
	}

	private static final Credit[] credits = {
			new Credit("Story by", "Weaver"),
			new Credit("Art by", "Weaver"),
			new Credit("Sequenced by", "Arbo"),
			new Credit("Special thanks", "The /tg/ community" + "\n"
					+ "thisisnotatrueending"),
			new Credit("Written and directed by", "Weaver") };

	private final int credit;

}
