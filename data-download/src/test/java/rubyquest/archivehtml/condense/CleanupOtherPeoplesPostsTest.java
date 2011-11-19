/*
 * Created on 07/04/2010
 */
package rubyquest.archivehtml.condense;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CleanupOtherPeoplesPostsTest {

	static final String SOMEONE_ELSES_POST = "Turn on the damn lights.";

	static class FindTextOfPostMock implements FindTextOfPost {

		@Override
		public String findTextOfPost(final String number) {
			return SOMEONE_ELSES_POST;
		}

	}

	@Test
	public void testCleanup() {
		final String someoneelse = "<font class=\"unkfunc\">"
				+ "<a href=\"#3195072\" class=\"quotelink\" onclick=\"replyhl('3195072');\">&gt;&gt;3195072</a></font><br>";
		final String weaver = "<font class=\"unkfunc\">&gt;Turn on the damn lights</font><br>Unfortunately it's too dark in here it's hard to tell where the lights are.";
		final String post = someoneelse + weaver;

		final CleanupOtherPeoplesPosts cleanup = new CleanupOtherPeoplesPosts(
				new FindTextOfPostMock());
		final String actual = cleanup.cleanup(post);

		assertThat(actual, equalTo("<div class=\"someone-elses-post\"><p>"
				+ SOMEONE_ELSES_POST + "</p></div>" + weaver));
	}
}
