/*
 * Created on 08/04/2010
 */
package rubyquest.archivehtml.condense;

import org.apache.commons.lang.StringUtils;
import org.jdom.CDATA;
import org.jdom.Element;

import rubyquest.model.Picture;
import rubyquest.model.PictureMaybe;
import rubyquest.model.WeaverPost;
import rubyquest.model.WeaverPost.SceneIdVisitor;

class WeaverElementBuilder {

	private final WeaverPost w;
	private final Element weaver;

	WeaverElementBuilder(final WeaverPost w) {
		this.w = w;
		this.weaver = new Element("weaver");
	}

	Element build() {
		add("id", String.valueOf(w.id));
		add("archive", w.getArchive().getArchiveNumber());
		add("post", w.post);

		if (!StringUtils.isEmpty(w.text)) addCDATA("text", w.text);

		w.accept(new AddPicture());
		w.accept(new AddScene());
		return weaver;
	}

	class AddScene implements SceneIdVisitor {

		@Override
		public void visit(final int scene) {
			addScene(scene);
		}

	}

	void addScene(final int scene) {
		add("scene", String.valueOf(scene));
	}

	class AddPicture extends PictureMaybe<Object> {

		@Override
		public Object visit(final Picture picture) {
			addPicture(picture);
			return null;
		}

	}

	void addPicture(final Picture picture) {
		final Element epicture = new Element("picture");
		add(epicture, "archived", picture.getArchived());
		add(epicture, "original", picture.getOriginal());
		add(epicture, "thumbnail", picture.getThumbnail());
		weaver.addContent(epicture);
	}

	private void add(final String name, final String text) {
		add(weaver, name, text);
	}

	private static void add(final Element parent, final String name,
			final String text) {
		parent.addContent(new Element(name).setText(text));
	}

	private void addCDATA(final String name, final String string) {
		weaver.addContent(new Element(name).addContent(new CDATA(string)));
	}

}