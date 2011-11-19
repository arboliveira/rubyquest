/*
 * Created on 07/04/2010
 */
package rubyquest.archivehtml.condense;

class FindTextOfPostImpl implements FindTextOfPost {

	private final String html;
	private final ExtractNextText extract;

	public FindTextOfPostImpl(final String html) {
		this.html = html;
		this.extract = new ExtractNextText(html, new CleanupText());
	}

	@Override
	public String findTextOfPost(final String number) {
		final int fromIndex = html.indexOf(number + "</a></span>");
		final Text text = extract.extractNextText(fromIndex);
		return text.text;
	}

}