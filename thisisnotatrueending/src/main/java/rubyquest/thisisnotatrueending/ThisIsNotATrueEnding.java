/*
 * Created on 12/09/2009
 */
package rubyquest.thisisnotatrueending;

public class ThisIsNotATrueEnding {

	public static URL getURL(final String path) {
		return new URL(path);
	}

	public static URL getArchiveURL(final String archiveNumber) {
		return new URL(ARCHIVE, archiveNumber);
	}

	public static URL getRubyHtmlURL() {
		return getURL("ruby.html");
	}

	private static final URL ARCHIVE = produceArchiveURL();

	private static URL produceArchiveURL() {
		return getURL("archive");
	}

}
