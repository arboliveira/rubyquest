/*
 * Created on 25/04/2010
 */
package rubyquest.wicket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BeginTest {

	@Test
	public void simplest() {
		RubyQuestWicketTester.startPage(Begin.class);
	}

	@Test
	public void offlineName() {
		assertThat(Begin.newPageSpec().getOfflineName(), equalTo("begin"));
	}
}
