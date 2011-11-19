package rubyquest.wicket;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;

public class ArchivePictureImgSrcBuilderTest {

	@Test
	public void online() {
		final Picture picture = new Picture(null, "987.gif", null);
		final String build = new ArchivePictureImgSrcBuilder(Placement.Online)
				.build(new ArchiveNumber(123), picture);
		assertThat(
				build,
				CoreMatchers
						.equalTo("http://suptg.thisisnotatrueending.com/archive/123/987.gif"));
	}

}
