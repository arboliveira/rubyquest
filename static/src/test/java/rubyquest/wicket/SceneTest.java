/*
 * Created on 12/09/2009
 */
package rubyquest.wicket;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.log4j.Logger;
import org.junit.Test;

import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.offline.render.OfflinePageRenderer;
import rubyquest.wicket.Scene.SceneRenderParams;

public class SceneTest {

	private static final Logger insight = new Insight().getLogger();

	@Test
	public void render() throws IOException {
		final OfflinePageRenderer wicketRequiresApplicationBeforePage = new OfflinePageRenderer();
		final int sceneCount = 100;
		final Scene page = new Scene(new SceneRenderParams("test",
				new ArchiveNumber(666), new Picture("test", null, null), 16,
				sceneCount, new LeftPadWithZeroes(sceneCount)),
				Placement.Offline);
		final String rendered = wicketRequiresApplicationBeforePage
				.render(page);
		insight.debug(rendered);
		final Object[] actuals = IOUtils.readLines(
				new CharSequenceReader(rendered)).toArray();
		final Object[] expecteds = IOUtils.readLines(expected()).toArray();
		assertArrayEquals(expecteds, actuals);
	}

	private InputStream expected() {
		final Class< ? extends SceneTest> class1 = getClass();
		final String html = class1.getSimpleName() + ".html";
		return class1.getResourceAsStream(html);
	}

}
