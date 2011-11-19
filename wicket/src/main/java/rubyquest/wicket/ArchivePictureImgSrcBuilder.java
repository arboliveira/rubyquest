package rubyquest.wicket;

import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.offline.Images;
import rubyquest.thisisnotatrueending.ThisIsNotATrueEnding;
import rubyquest.thisisnotatrueending.URL;

class ArchivePictureImgSrcBuilder {

	private final Placement offline;

	ArchivePictureImgSrcBuilder(final Placement offline) {
		this.offline = offline;
	}

	String build(final ArchiveNumber archiveNumber, final Picture picture) {
		if (offline == Placement.Offline) {
			return buildOfflineImgSrc(archiveNumber, picture);
		}
		return buildOnlinePictureURL(archiveNumber, picture).toExternalForm();
	}

	private static String buildOfflineImgSrc(final ArchiveNumber archiveNumber,
			final Picture picture) {
		return Images.IMAGES + "/" + archiveNumber.getArchiveNumber() + "/"
				+ picture.getOriginal();
	}

	private static URL buildOnlinePictureURL(final ArchiveNumber archiveNumber,
			final Picture picture) {
		final URL archive = ThisIsNotATrueEnding.getArchiveURL(archiveNumber
				.getArchiveNumber());
		final URL withPicture = new URL(archive, picture.getArchived());
		return withPicture;
	}

}