/*
 * Created on 13/09/2009
 */
package rubyquest.wicket;

public class ExternalStyleSheet extends TagPut {

	public ExternalStyleSheet(final String id, final String value) {
		super(id, "link", "href", value);
	}

}
