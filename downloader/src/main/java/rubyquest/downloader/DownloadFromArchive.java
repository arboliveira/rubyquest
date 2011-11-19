package rubyquest.downloader;

public interface DownloadFromArchive {

	void download(final String archiveNumber, final String sourceRelativePath,
			final String destinationRelativePath);

}
