/*
 * Created on 07/04/2010
 */
package rubyquest.archivehtml.condense;

import static rubyquest.archivehtml.condense.SubstringBetween.substringBetween;

class ExtractNextText {

	private final String html;
	private final CleanupText cleanup;

	ExtractNextText(final String html, final CleanupText cleanup) {
		this.html = html;
		this.cleanup = cleanup;
	}

	Text extractNextText(final int fromIndex) {
		final String bqopen = "<blockquote>";
		final String bqclose = "</blockquote>";
		final String bq = substringBetween(html, bqopen, bqclose, fromIndex);
		final String ttext = cleanup.cleanupText(bq);
		final int tbegin = html.indexOf(bqopen, fromIndex)
				+ bqopen.length();
		final String tpost = substringBetween(html, "<a href=\"#",
				"\" class=\"quotejs\">No.", fromIndex);
		final Text text = new Text(ttext, tbegin, tpost);
		return text;
	}

}