/*
 * Created on 13/09/2009
 */
package rubyquest.data;

import java.util.List;

import rubyquest.model.Scene;
import rubyquest.model.SceneVisitor;
import rubyquest.model.WeaverPost;
import rubyquest.model.WeaverPostVisitor;
import rubyquest.model.WeaverPosts;

public class RubyQuestData implements WeaverPosts {

	private final List<WeaverPost> weavers;
	private final List<Scene> scenes;

	RubyQuestData(final List<WeaverPost> weavers, final List<Scene> scenes) {
		this.weavers = weavers;
		this.scenes = scenes;
	}

	@Override
	public void accept(final WeaverPostVisitor visitor) {
		for (final WeaverPost post : weavers)
			visitor.visit(post);
	}

	public void accept(final SceneVisitor visitor) {
		for (final Scene scene : scenes)
			visitor.visit(scene);
	}

	public int weaverpostCount() {
		return weavers.size();
	}

	public WeaverPost findWeaverPostOfScene(final int sceneid) {
		final Scene scene = findScene(sceneid);
		final WeaverPost weaverPost = findWeaverPost(scene.getWeaverpost());
		return weaverPost;
	}

	public WeaverPost findWeaverPost(final int weaverpostid) {
		return weavers.get(weaverpostid - 1);
	}

	private Scene findScene(final int sceneid) {
		return scenes.get(sceneid - 1);
	}

	public int sceneCount() {
		return scenes.size();
	}
}
