/*
 * Created on 08/04/2010
 */
package rubyquest.data;

import java.util.List;

import org.jdom.CDATA;
import org.jdom.Element;

import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.model.WeaverPost;

class WeaverPostBuilder {

	private final Element element;

	WeaverPostBuilder(final Element element) {
		this.element = element;
	}

	WeaverPost build() {
		final int id = id();
		final ArchiveNumber archive = archive();
		final String post = post();
		final String text = text();
		final Picture picture = picture();
		final Integer scene = scene();
		if (scene != null)
			return WeaverPost.newScene(id, archive, post, text, picture, scene);
		if (picture != null)
			return WeaverPost.newNoScene(id, archive, post, text, picture);
		return WeaverPost.newNoSceneNoPicture(id, archive, post, text);
	}

	private Integer scene() {
		final Element childScene = element.getChild("scene");
		if (childScene == null) return null;
		return Integer.valueOf(childScene.getText());
	}

	private Picture picture() {
		final Element childPicture = element.getChild("picture");
		if (childPicture == null) return null;
		final String original = childPicture.getChild("original").getText();
		final String archived = childPicture.getChild("archived").getText();
		final String thumbnail = childPicture.getChild("thumbnail").getText();
		return new Picture(original, archived, thumbnail);
	}

	private String text() {
		final Element childText = element.getChild("text");
		if (childText == null) return null;
		@SuppressWarnings("unchecked")
		final List<CDATA> content = childText.getContent();
		final CDATA cdata = content.get(0);
		return cdata.getText();
	}

	private String post() {
		return element.getChild("post").getText();
	}

	private ArchiveNumber archive() {
		return new ArchiveNumber(Integer.valueOf(element
				.getChild("archive")
				.getText()));
	}

	private int id() {
		return Integer.valueOf(element.getChild("id").getText()).intValue();
	}
}