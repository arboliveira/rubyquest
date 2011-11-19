/*
 * Created on 12/09/2009
 */
package rubyquest.archivehtml.condense;

import org.jdom.Element;

import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.model.WeaverPost;

public class WriteWeaverPost implements WeaverPostVisitor {

	private final Element weaverposts;

	int id, scene;

	WriteWeaverPost(final Element weaverposts) {
		this.weaverposts = weaverposts;
	}

	@Override
	public void visit(final String text, final String post,
			final Picture picture, final ArchiveNumber archiveNumber) {
		final WeaverPost w = newWeaverPost(text, post, picture, archiveNumber);
		final Element weaver = new WeaverElementBuilder(w).build();
		weaverposts.addContent(weaver);
	}

	private WeaverPost newWeaverPost(final String text, final String post,
			final Picture picture, final ArchiveNumber archiveNumber) {
		id++;
		if (PostsNotScenes.isScene(post)) {
			scene++;
			return WeaverPost.newScene(id, archiveNumber, post, text, picture,
					scene);
		}
		if (picture != null)
			return WeaverPost
					.newNoScene(id, archiveNumber, post, text, picture);
		return WeaverPost.newNoSceneNoPicture(id, archiveNumber, post, text);
	}
}
