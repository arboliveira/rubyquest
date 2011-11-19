/*
 * Created on 20/09/2009
 */
package rubyquest.wicket;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.RepeatingView;

import rubyquest.data.RubyQuestData;
import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.model.PictureVisitor;
import rubyquest.model.WeaverPost;
import rubyquest.model.WeaverPostVisitor;
import rubyquest.thisisnotatrueending.ThisIsNotATrueEnding;
import rubyquest.thisisnotatrueending.URL;

public class Storyboard extends WebPage {

	public static PageSpec newPageSpec() {
		return new SimplePageSpec(Storyboard.class);
	}

	public Storyboard() {
		this(Placement.Online, RubyQuest.get().getRubyQuestData());
	}

	public Storyboard(final Placement offline, final RubyQuestData data) {
		WebPageAll.onCreate(this);
		final RepeatingView repeating = new RepeatingView("weaverpost");
		add(repeating);
		final EachPost eachPost = new EachPost(repeating, offline,
				ImagenameRubyThumbnail.get(offline, data));
		data.accept(eachPost);
	}

	class EachPost implements WeaverPostVisitor {

		EachPost(final RepeatingView repeating, final Placement offline,
				final String imagenameRubyThumbnail) {
			this.repeating = repeating;
			this.imagenameRubyThumbnail = imagenameRubyThumbnail;
			this.archivePictureImgSrcBuilder = new ArchivePictureImgSrcBuilder(
					offline);
		}

		@Override
		public void visit(final WeaverPost post) {
			final WebMarkupContainer item = new WebMarkupContainer(
					repeating.newChildId());
			repeating.add(item);
			item.add(new Label("text", post.text).setEscapeModelStrings(false));
			final ExternalLink link = new ExternalLink("link",
					linkToArchivedPost(post));
			item.add(link);

			final String imgsrc = determineImgSrc(post);
			link.add(new ExternalImage("image", imgsrc));
		}

		private String linkToArchivedPost(final WeaverPost post) {
			final ArchiveNumber archiveNumber = post.getArchive();
			final String anchor = "#" + post.post;
			final URL baseArchiveNumber = ThisIsNotATrueEnding
					.getArchiveURL(archiveNumber.getArchiveNumber());
			return new URL(baseArchiveNumber, anchor).toExternalForm();
		}

		private String determineImgSrc(final WeaverPost post) {
			class DetermineImgSrc implements PictureVisitor<String> {

				@Override
				public String visit(final Picture picture) {
					return buildImgSrc(post, picture);
				}

				@Override
				public String noPicture() {
					return imagenameRubyThumbnail;
				}

			}
			return post.accept(new DetermineImgSrc());
		}

		String buildImgSrc(final WeaverPost post, final Picture picture) {
			final ArchiveNumber archiveNumber = post.getArchive();
			return archivePictureImgSrcBuilder.build(archiveNumber, picture);
		}

		private final RepeatingView repeating;

		final String imagenameRubyThumbnail;

		private final ArchivePictureImgSrcBuilder archivePictureImgSrcBuilder;
	}
}
