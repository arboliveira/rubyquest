package rubyquest.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;

import rubyquest.data.RubyQuestData;
import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.model.WeaverPost;

public class End extends WebPage {

	public static int count() {
		return 2;
	}

	public static PageSpec newSpec(final int i) {
		return new NamedPageSpec(End.htmlfile(i), End.class);
	}

	public End(final PageParameters parameters) {
		this(Placement.Online, parameters.getAsInteger("end").intValue(),
				RubyQuest.get().getRubyQuestData());
	}

	public End(final Placement offline, final int end, final RubyQuestData data) {
		this.end = end;
		this.data = data;
		WebPageAll.onCreate(this);
		WebPageNavigable.onCreate(this);
		populateMarkup(offline);
	}

	private void populateMarkup(final Placement offline) {
		final MarkupContainer linkNext = newLinkToNext(offline);
		add(linkNext);

		final int postid = 888 + (end - 1);
		final WeaverPost post = data.findWeaverPost(postid);
		final ArchiveNumber archiveNumber = post.getArchive();
		final Picture picture = post.getImage();
		final String imgsrc = new ArchivePictureImgSrcBuilder(offline).build(
				archiveNumber, picture);
		linkNext.add(new ExternalImage("image", imgsrc));

		final WebMarkupContainer theend = new WebMarkupContainer("theend");
		add(theend);
		if (end == 1) {
			theend.setVisible(false);
		}

		add(newLinkToPrevious(offline));
	}

	private MarkupContainer newLinkToNext(final Placement offline) {
		final String nextId = "next";
		if (end == 1) {
			return Credits.newLinkToCredits(offline, nextId, 1);
		}
		return new LinkFactory(new SimplePageSpec(About.class), offline, nextId)
				.produce();
	}

	private MarkupContainer newLinkToPrevious(final Placement offline) {
		final String previousId = "previous";
		if (end == 1) {
			return Endtitle.newLink(previousId, offline);
		}
		return Credits.newLinkToCredits(offline, previousId, Credits.count());
	}

	static Component newLinkToEndscene(final Placement offline,
			final String id, final int endid) {
		if (offline == Placement.Offline) {
			return new ExternalLink(id, htmlfile(endid) + ".html");
		}
		return new BookmarkablePageLink<End>(id, End.class, new PageParameters(
				"end=" + endid));
	}

	static Component newLinkToShocker(final Placement offline, final String id) {
		return End.newLinkToEndscene(offline, id, 1);
	}

	static Component newLinkToTheEnd(final String id, final Placement offline) {
		return newLinkToEndscene(offline, id, 2);
	}

	private static String htmlfile(final int endid) {
		return "end-" + endid;
	}

	private final int end;
	private final RubyQuestData data;

}
