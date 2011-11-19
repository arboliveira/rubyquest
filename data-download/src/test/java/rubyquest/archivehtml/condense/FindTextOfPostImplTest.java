/*
 * Created on 07/04/2010
 */
package rubyquest.archivehtml.condense;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static rubyquest.archivehtml.condense.GetResourceAsString.getResourceAsString;

import java.io.IOException;

import org.junit.Test;

public class FindTextOfPostImplTest {

	@Test
	public void testFindTextOfPost() throws IOException {
		final Class< ? > c = this.getClass();
		final String html = getResourceAsString(c, "html");
		final String actual = new FindTextOfPostImpl(html)
				.findTextOfPost("3430197");
		assertThat(actual, equalTo("WHAT infection?"));
	}

}
