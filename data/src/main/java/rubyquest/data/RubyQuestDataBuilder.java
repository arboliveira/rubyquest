/*
 * Created on 08/04/2010
 */
package rubyquest.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.UnhandledException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import rubyquest.io.InputStreamFactory;
import rubyquest.io.InputStreamManager;
import rubyquest.io.InputStreamUsage;
import rubyquest.jdom.DocumentFactory;
import rubyquest.model.Scene;
import rubyquest.model.WeaverPost;
import rubyquest.model.WeaverPost.SceneIdVisitor;

public class RubyQuestDataBuilder implements InputStreamUsage<RubyQuestData> {

	@Override
	public RubyQuestData use(final InputStream in) throws IOException {
		final Document d = buildDocument(in);
		final Element rootElement = d.getRootElement();

		@SuppressWarnings("unchecked")
		final List<Element> eweavers = rootElement
				.getChild("weaverposts")
				.getChildren();

		final int size = eweavers.size();
		final ArrayList<WeaverPost> weavers = new ArrayList<WeaverPost>(size);
		final ArrayList<Scene> scenes = new ArrayList<Scene>(size);

		for (final Element eweaver : eweavers)
			eachElement(eweaver, weavers, scenes);
		return new RubyQuestData(weavers, scenes);
	}

	private static void eachElement(final Element eweaver,
			final ArrayList<WeaverPost> weavers, final ArrayList<Scene> scenes) {
		final WeaverPost w = new WeaverPostBuilder(eweaver).build();
		weavers.add(w);

		class AddScene implements SceneIdVisitor {

			@Override
			public void visit(final int scene) {
				final Scene s = new Scene(scene, w.id);
				scenes.add(s);
			}

		}
		w.accept(new AddScene());
	}

	private static Document buildDocument(final InputStream resourceAsStream)
			throws IOException {
		try {
			return DocumentFactory.buildDocument(resourceAsStream);
		} catch (final JDOMException e) {
			throw new UnhandledException(e);
		}
	}

	public static RubyQuestData readDataFromRubyQuestXml() throws IOException {
		final InputStreamFactory xml = RubyQuestXml.getResourceAsStream();
		final InputStreamUsage<RubyQuestData> builder = new RubyQuestDataBuilder();
		final RubyQuestData data = new InputStreamManager(xml)
				.openUsing(builder);
		return data;
	}

}