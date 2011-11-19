/*
 * Created on 13/09/2009
 */
package rubyquest.wicket;

public class ExternalImage extends TagPut {

	public ExternalImage(final String id, final String src) {
		super(id, "img", "src", src);
	}
}