/*
 * Created on 13/09/2009
 */
package rubyquest.wicket;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;

public class TagPut extends WebComponent {

	private final String tagname, key, value;

	public TagPut(final String id, final String tagname, final String key,
			final String value) {
		super(id);
		this.tagname = tagname;
		this.key = key;
		this.value = value;
	}

	@Override
	protected void onComponentTag(final ComponentTag tag) {
		checkComponentTag(tag, tagname);
		super.onComponentTag(tag);
		tag.put(key, value);
	}
}