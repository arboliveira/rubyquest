/*
 * Created on 25/04/2010
 */
package rubyquest.wicket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BonusTest {

	@Test
	public void simplest() {
		RubyQuestWicketTester.startPage(Bonus.class);
	}

	@Test
	public void offlineName() {
		assertThat(Bonus.newPageSpec().getOfflineName(), equalTo("bonus"));
	}
}
