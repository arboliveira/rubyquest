/*
 * Created on 05/04/2010
 */
package rubyquest.wicket;

import org.apache.wicket.Page;

class SimplePageSpec extends NamedPageSpec {

	SimplePageSpec(final Class< ? extends Page> onlineClass) {
		super(getSimpleId(onlineClass), onlineClass);
	}

	public static String getSimpleId(final Class< ? > onlineClass) {
		return onlineClass.getSimpleName().toLowerCase();
	}

}
