/*
 * Created on 25/04/2010
 */
package rubyquest.wicket;

import java.io.IOException;

import org.apache.commons.lang.UnhandledException;
import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;

class RubyQuestWicketTester {

	static void startPage(final Class< ? extends Page> pageClass) {
		tester.startPage(pageClass);
	}

	private static final WicketTester tester = newWicketTester();

	private static WicketTester newWicketTester() {
		try {
			return new WicketTester(new RubyQuest(true));
		} catch (final IOException e) {
			throw new UnhandledException(e);
		}
	}

}
