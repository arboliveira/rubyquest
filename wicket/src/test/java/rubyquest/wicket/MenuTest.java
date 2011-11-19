/*
 * Created on 25/04/2010
 */
package rubyquest.wicket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MenuTest {

	@Test
	public void simplest() {
		RubyQuestWicketTester.startPage(Menu.class);
	}

	@Test
	public void offlineName() {
		assertThat(Menu.newPageSpec().getOfflineName(), equalTo("menu"));
	}
}
