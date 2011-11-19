/*
 * Created on 2011-10-12
 */
package rubyquest.downloader;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.commons.lang.UnhandledException;

import rubyquest.thisisnotatrueending.ThisIsNotATrueEnding;
import rubyquest.thisisnotatrueending.URL;

public class DownloadFileFromThisIsNotATrueEndingArchive implements
		DownloadFromArchive {

	public DownloadFileFromThisIsNotATrueEndingArchive(
			final File targetDirectory, final Downloader downloader) {
		this.targetDirectory = targetDirectory;
		this.downloader = downloader;
	}

	@Override
	public void download(final String archiveNumber,
			final String sourceRelativePath,
			final String destinationRelativePath) {
		final URL pathPrefix = ThisIsNotATrueEnding
				.getArchiveURL(archiveNumber);
		final File subdirArchive = new File(targetDirectory, archiveNumber);

		final URL fullURL = new URL(pathPrefix, sourceRelativePath);
		final File destination = new File(subdirArchive,
				destinationRelativePath);
		downloader.download(toJavaURL(fullURL), destination);
	}

	private static java.net.URL toJavaURL(final URL fullURL) {
		try {
			return fullURL.toJavaURL();
		} catch (final MalformedURLException e) {
			throw new UnhandledException(e);
		}
	}

	private final File targetDirectory;
	private final Downloader downloader;
}