/*
 * Created on 05/04/2010
 */
package rubyquest.wicket;

import org.apache.wicket.Page;

class NamedPageSpec implements PageSpec {

	NamedPageSpec(final String name, final Class< ? extends Page> onlineClass) {
		this.name = name;
		this.onlineClass = onlineClass;
	}

	@Override
	public String getOfflineName() {
		return name;
	}

	@Override
	public Class< ? extends Page> getOnlineClass() {
		return onlineClass;
	}

	private final String name;
	private final Class< ? extends Page> onlineClass;

}
