/*
 * Created on 08/04/2010
 */
package rubyquest.offline.render;

import rubyquest.downloader.DownloadFromArchive;
import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.model.PictureMaybe;
import rubyquest.model.WeaverPost;
import rubyquest.model.WeaverPostVisitor;
import rubyquest.model.WeaverPosts;

class Pictures {

	Pictures(final WeaverPosts data, final DownloadFromArchive download) {
		this.data = data;
		this.download = download;
	}

	void run() {
		class DownloadOnePicture implements WeaverPostVisitor {

			@Override
			public void visit(final WeaverPost post) {
				downloadFromOnePost(post);
			}
		}
		data.accept(new DownloadOnePicture());
	}

	void downloadFromOnePost(final WeaverPost post) {
		class DownloadPictureAndThumbnail extends PictureMaybe<Object> {

			@Override
			public Object visit(final Picture picture) {
				downloadPictureAndThumbnail(picture, post.getArchive());
				return null;
			}

		}
		post.accept(new DownloadPictureAndThumbnail());
	}

	void downloadPictureAndThumbnail(final Picture picture,
			final ArchiveNumber archiveNumber) {
		final String archiveNumberString = archiveNumber.getArchiveNumber();
		download.download(archiveNumberString, picture.getArchived(),
				picture.getOriginal());
		download.download(archiveNumberString, picture.getThumbnail(),
				picture.getOriginalThumbnailed());
	}

	private final WeaverPosts data;
	private final DownloadFromArchive download;

}
