/*
 * Created on 22/09/2009
 */
package rubyquest.archivehtml.condense;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static rubyquest.archivehtml.condense.GetResourceAsString.getResourceAsString;

import java.io.IOException;

import org.junit.Test;

public class SpecialTextConvertTest {

	@Test
	public void testConvert() throws IOException {
		final Class< ? > c = this.getClass();
		final String original = getResourceAsString(c, "original.txt");
		final String actual = SpecialTextConvert.convert(original);
		final String expected = getResourceAsString(c, "result.txt");
		assertThat(actual, equalTo(expected));
	}

}
