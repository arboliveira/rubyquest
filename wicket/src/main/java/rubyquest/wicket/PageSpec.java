/*
 * Created on 05/04/2010
 */
package rubyquest.wicket;

import org.apache.wicket.Page;

public interface PageSpec {

	String getOfflineName();

	Class< ? extends Page> getOnlineClass();
}
