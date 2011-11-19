/*
 * Created on 06/04/2010
 */
package rubyquest.archivehtml.condense;

class Text {

	Text(final String text, final int begin, final String post) {
		this.text = text;
		this.begin = begin;
		this.post = post;
	}

	final String text;
	final int begin;
	final String post;
}