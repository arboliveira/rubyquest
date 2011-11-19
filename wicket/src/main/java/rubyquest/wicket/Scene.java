package rubyquest.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;

import rubyquest.data.RubyQuestData;
import rubyquest.model.ArchiveNumber;
import rubyquest.model.Flow;
import rubyquest.model.Picture;
import rubyquest.model.WeaverPost;

public class Scene extends WebPage {

	public final static String prefix = "scene";

	public static PageSpec newPageSpec(final int sceneid,
			final LeftPadWithZeroes leftPadWithZeroes) {
		return new NamedPageSpec(Scene.pageFilenameWithPrefix(sceneid,
				leftPadWithZeroes), Scene.class);
	}

	public static AbstractLink newLink(final Placement offline,
			final String id, final int scene, final IntToString intToString) {
		if (offline == Placement.Offline) {
			final String offlineName = pageFilenameWithPrefix(scene,
					intToString);
			return new ExternalLink(id, offlineName + ".html");
		}
		final Class<Scene> onlineClass = Scene.class;
		return new BookmarkablePageLink<Scene>(id, onlineClass,
				new PageParameters("scene=" + intToString.toString(scene)));
	}

	private static String pageFilenameWithPrefix(final int scene,
			final IntToString padded) {
		return prefix + "-" + padded.toString(scene);
	}

	public Scene(final SceneRenderParams params, final Placement offline) {
		this.params = params;
		this.offline = offline;
		WebPageAll.onCreate(this);
		WebPageNavigable.onCreate(this);
		populateMarkup();
	}

	public Scene(final PageParameters parameters) {
		this(buildSceneRenderParams(parameters), Placement.Online);
	}

	public static class SceneRenderParams {

		final String text;
		private final Picture picture;
		final int sceneid;
		final int sceneCount;
		final IntToString pad;
		private final ArchiveNumber archiveNumber;

		public SceneRenderParams(final String text,
				final ArchiveNumber archiveNumber, final Picture picture,
				final int sceneid, final int sceneCount, final IntToString pad) {
			super();
			this.text = text;
			this.archiveNumber = archiveNumber;
			this.picture = picture;
			this.sceneid = sceneid;
			this.sceneCount = sceneCount;
			this.pad = pad;
		}

		public Picture getPicture() {
			return picture;
		}

		public ArchiveNumber getArchiveNumber() {
			return archiveNumber;
		}

	}

	private void populateMarkup() {
		final Flow flow = new Flow(params.sceneCount);
		final Integer nextScene = flow.nextScene(params.sceneid);
		final Integer previousScene = flow.previousScene(params.sceneid);

		addText();
		addImageWithinLink(nextScene);
		addNavFirst();
		addNavPrevious(previousScene);
		addNavNext(nextScene);
		addNavLast();
	}

	private void addNavLast() {
		add(Scene.newLink(offline, "nav-last", params.sceneCount, params.pad));
	}

	private void addNavNext(final Integer nextScene) {
		add(newLinkToNextScene("nav-next", nextScene, params.pad));
	}

	private void addNavPrevious(final Integer previousScene) {
		add(newLinkToPreviousScene("nav-previous", previousScene, params.pad));
	}

	private void addNavFirst() {
		add(Scene.newLink(offline, "nav-first", 1, params.pad));
	}

	private void addText() {
		add(new Label("text", params.text).setEscapeModelStrings(false));
	}

	private void addImageWithinLink(final Integer nextScene) {
		final MarkupContainer next = newLinkToNextScene("next", nextScene,
				params.pad);
		final String imgsrc = imgsrc();
		next.add(new ExternalImage("image", imgsrc));
		add(next);
	}

	private String imgsrc() {
		final ArchiveNumber archiveNumber = params.getArchiveNumber();
		final Picture picture = params.getPicture();
		final String imgsrc = new ArchivePictureImgSrcBuilder(offline).build(
				archiveNumber, picture);
		return imgsrc;
	}

	private AbstractLink newLinkToPreviousScene(final String id,
			final Integer previousScene, final IntToString pad) {
		if (previousScene != null) {
			return Scene.newLink(offline, id, previousScene.intValue(), pad);
		}
		return new LinkFactory(TheMetalGlen.newPageSpec(), offline, id)
				.produce();
	}

	private MarkupContainer newLinkToNextScene(final String id,
			final Integer nextScene, final IntToString pad) {
		if (nextScene != null) {
			return Scene.newLink(offline, id, nextScene.intValue(), pad);
		}
		return Endtitle.newLink(id, offline);
	}

	private static SceneRenderParams buildSceneRenderParams(
			final PageParameters parameters) {
		final int sceneid = parameters.getAsInteger("scene").intValue();
		final RubyQuest application = (RubyQuest) Application.get();
		final RubyQuestData data = application.getRubyQuestData();
		final WeaverPost post = data.findWeaverPostOfScene(sceneid);
		final SceneRenderParams p = new SceneRenderParams(post.text,
				post.getArchive(), post.getImage(), sceneid, data.sceneCount(),
				application.getLeftPadWithZeroes());
		return p;
	}

	private final SceneRenderParams params;
	private final Placement offline;

}
