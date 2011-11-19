/*
 * Created on 09/04/2010
 */
package rubyquest.wicket;

import rubyquest.data.RubyQuestData;
import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.model.WeaverPost;

class ImagenameRubyThumbnail {

	static String get(final Placement offline, final RubyQuestData data) {
		final int postid = 890;
		final WeaverPost post = data.findWeaverPost(postid);
		final ArchiveNumber archiveNumber = post.getArchive();
		final Picture picture = post.getImage();
		return new ArchiveThumbnailImgSrcBuilder(offline).build(archiveNumber,
				picture);
	}

}