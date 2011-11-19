package rubyquest.wicket;

import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.offline.Images;
import rubyquest.thisisnotatrueending.ThisIsNotATrueEnding;
import rubyquest.thisisnotatrueending.URL;

class ArchiveThumbnailImgSrcBuilder {

	private final Placement offline;

	public ArchiveThumbnailImgSrcBuilder(final Placement offline) {
		this.offline = offline;
	}

	String build(final ArchiveNumber archiveNumber, final Picture picture) {
		if (offline == Placement.Offline) {
			return buildOfflineImgSrc(archiveNumber, picture);
		}
		return buildOnlineThumbnailURL(archiveNumber, picture).toExternalForm();
	}

	private static String buildOfflineImgSrc(final ArchiveNumber archiveNumber,
			final Picture picture) {
		return Images.IMAGES + "/" + archiveNumber.getArchiveNumber() + "/"
				+ picture.getOriginalThumbnailed();
	}

	private static URL buildOnlineThumbnailURL(
			final ArchiveNumber archiveNumber, final Picture picture) {
		final URL urlPrefix = ThisIsNotATrueEnding.getArchiveURL(archiveNumber
				.getArchiveNumber());
		final URL imgsrc = new URL(urlPrefix, picture.getThumbnail());
		return imgsrc;
	}

}