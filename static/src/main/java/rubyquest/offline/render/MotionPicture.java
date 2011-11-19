/*
 * Created on 13/09/2009
 */
package rubyquest.offline.render;

import org.apache.wicket.Page;

import rubyquest.data.RubyQuestData;
import rubyquest.downloader.ConditionalRender;
import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.model.Scene;
import rubyquest.model.SceneVisitor;
import rubyquest.model.WeaverPost;
import rubyquest.offline.render.RenderToFile.PageFactory;
import rubyquest.wicket.LeftPadWithZeroes;
import rubyquest.wicket.PageSpec;
import rubyquest.wicket.Placement;
import rubyquest.wicket.Scene.SceneRenderParams;

class MotionPicture {

	private final OfflinePageRenderer renderer;
	private final RubyQuestData data;
	private final int sceneCount;
	private final LeftPadWithZeroes leftPadWithZeroes;
	private final OfflineOutputDirectory offlineOutputDirectory;
	private final IfTargetExists ifTargetExists;
	private final DoIt doIt;

	MotionPicture(final RubyQuestData data,
			final LeftPadWithZeroes leftPadWithZeroes,
			final OfflineOutputDirectory output,
			final OfflinePageRenderer renderer2,
			final IfTargetExists ifTargetExists, final DoIt doIt) {
		this.offlineOutputDirectory = output;
		this.renderer = renderer2;
		this.data = data;
		this.ifTargetExists = ifTargetExists;
		this.doIt = doIt;
		this.sceneCount = data.sceneCount();
		this.leftPadWithZeroes = leftPadWithZeroes;
	}

	void run() {
		data.accept(new RenderOneScene());
	}

	class RenderOneScene implements SceneVisitor {

		@Override
		public void visit(final Scene scene) {
			renderOneScene(scene);
		}
	}

	void renderOneScene(final Scene scene) {
		final int sceneid = scene.getId();
		final WeaverPost post = data.findWeaverPostOfScene(sceneid);

		class ScenePageFactory implements PageFactory {

			@Override
			public Page produce() {
				return newOfflinePageScene(sceneid, post);
			}
		}
		render(rubyquest.wicket.Scene.newPageSpec(sceneid, leftPadWithZeroes),
				new ScenePageFactory());
	}

	rubyquest.wicket.Scene newOfflinePageScene(final int sceneid,
			final WeaverPost post) {
		return new rubyquest.wicket.Scene(new SceneRenderParams(post.text,
				post.getArchive(), post.getImage(), sceneid, sceneCount,
				leftPadWithZeroes), Placement.Offline);
	}

	private void render(final PageSpec spec, final PageFactory page) {
		final RenderToFile resource = new RenderToFile(spec, page,
				offlineOutputDirectory, renderer);
		new ConditionalRender(resource, ifTargetExists, doIt).render();
	}
}
