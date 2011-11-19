/*
 * Created on 2011-10-12
 */
package rubyquest.offline.render;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import rubyquest.downloader.DownloadFromArchive;
import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.model.WeaverPost;
import rubyquest.model.WeaverPostVisitor;
import rubyquest.model.WeaverPosts;

public class PicturesTest {

	final Mockery context = new JUnit4Mockery();
	final WeaverPosts posts = context.mock(WeaverPosts.class);
	final DownloadFromArchive download = context
			.mock(DownloadFromArchive.class);

	@Test
	public void postWithPicture_shouldDownload() {
		final Picture picture = new Picture("original", "archived", "thumbnail");
		final WeaverPost post = WeaverPost.newNoScene(1,
				new ArchiveNumber(666), "post", "text", picture);

		context.checking(new Expectations() {

			{
				oneOf(posts).accept(with(any(WeaverPostVisitor.class)));
				will(offer(post));
				oneOf(download).download("666", "archived", "original");
				oneOf(download).download("666", "thumbnail", "soriginal");
			}
		});

		assertPicturesRun();
	}

	private void assertPicturesRun() {
		new Pictures(posts, download).run();
		context.assertIsSatisfied();
	}

	@Test
	public void postWithoutPicture_shouldNotDownload() {
		final WeaverPost post = WeaverPost.newNoSceneNoPicture(1,
				new ArchiveNumber(666), "post", "text");

		context.checking(new Expectations() {

			{
				oneOf(posts).accept(with(any(WeaverPostVisitor.class)));
				will(offer(post));
				never(download);
			}
		});

		assertPicturesRun();
	}

	static <T> Action offer(final WeaverPost post) {
		return new OfferAction<T>(post);
	}

	static class OfferAction<T> implements Action {

		private final WeaverPost post;

		public OfferAction(final WeaverPost post) {
			this.post = post;
		}

		@Override
		public void describeTo(final Description description) {
			description
					.appendText("offers ")
					.appendValue(post)
					.appendText(" to the visitor");
		}

		@Override
		public Object invoke(final Invocation invocation) throws Throwable {
			((WeaverPostVisitor) invocation.getParameter(0)).visit(post);
			return null;
		}
	}

}
