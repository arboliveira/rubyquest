/*
 * Created on 12/09/2009
 */
package rubyquest.rubyhtml.condense;

import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;

import rubyquest.thisisnotatrueending.ThisIsNotATrueEnding;

class WritePart implements PartVisitor {

	private final PrintStream txt;
	private final PrintStream html;
	private final PrintStream xml;

	public WritePart(final PrintStream txt, final PrintStream html,
			final PrintStream xml) {
		this.txt = txt;
		this.html = html;
		this.xml = xml;
	}

	@Override
	public void visit(final String href, final String title,
			final String description) {
		final String address = ThisIsNotATrueEnding
				.getURL(href)
				.toExternalForm();
		appendTxt(address);
		appendHtml(address, title, description);
		final String archiveNumber = StringUtils.substringAfterLast(href, "/");
		appendXml(archiveNumber);
	}

	private void appendTxt(final String url) {
		txt.println(url);
	}

	private void appendHtml(final String url, final String title,
			final String description) {
		html.println("<p><a href=\"" + url + "\">" + title + "</a> "
				+ description + "</p>");
	}

	private void appendXml(final String archiveNumber) {
		xml.println("<archive>" + archiveNumber + "</archive>");
	}
}