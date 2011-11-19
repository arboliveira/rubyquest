/*
 * Created on 20/09/2009
 */
package rubyquest.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import rubyquest.data.RubyQuestData;
import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.model.SceneVisitor;
import rubyquest.model.WeaverPost;

public class SceneSelection extends WebPage {

	public static PageSpec newPageSpec() {
		return new SimplePageSpec(SceneSelection.class);
	}

	public static Component newLinkToSceneSelection(final String id,
			final Placement offline) {
		if (offline == Placement.Offline) {
			return new ExternalLink(id, SceneSelection
					.newPageSpec()
					.getOfflineName() + ".html");
		}
		return new BookmarkablePageLink<SceneSelection>(id,
				SceneSelection.class);
	}

	class Visitor implements SceneVisitor {

		@Override
		public void visit(final rubyquest.model.Scene scene) {
			addScene(scene);
		}

	}

	private final RubyQuestData rubyquestdata;
	private final Placement offline;
	private final LeftPadWithZeroes pad;
	private final RepeatingView row;
	private RepeatingView cell;

	public SceneSelection(final Placement offline, final RubyQuestData data) {
		this.offline = offline;
		this.rubyquestdata = data;
		this.pad = new LeftPadWithZeroes(data.sceneCount());
		WebPageAll.onCreate(this);
		this.row = new RepeatingView("row");
		add(row);
		data.accept(new Visitor());
	}

	public SceneSelection() {
		this(Placement.Online, buildRenderingNeeds((RubyQuest) Application
				.get()));
	}

	private static RubyQuestData buildRenderingNeeds(final RubyQuest application) {
		return application.getRubyQuestData();
	}

	void addScene(final rubyquest.model.Scene scene) {
		final int sceneid = scene.getId();
		if (sceneid % CELLS_PER_ROW == 1) {
			startNewRow();
		}
		addCell(sceneid);
	}

	private void startNewRow() {
		final WebMarkupContainer rowchild;
		rowchild = new WebMarkupContainer(this.row.newChildId());
		this.row.add(rowchild);
		this.cell = new RepeatingView("cell");
		rowchild.add(this.cell);
	}

	private void addCell(final int sceneid) {
		final WeaverPost post = rubyquestdata.findWeaverPostOfScene(sceneid);

		final WebMarkupContainer cellchild;
		cellchild = new WebMarkupContainer(this.cell.newChildId());
		this.cell.add(cellchild);
		cellchild.add(new AttributeModifier("width", true, new Model<String>(
				CELL_WIDTH)));
		final MarkupContainer link = Scene.newLink(offline, "link", sceneid,
				pad);
		cellchild.add(link);

		final Picture picture = post.getImage();
		final ArchiveNumber archiveNumber = post.getArchive();
		final String imgsrc = new ArchiveThumbnailImgSrcBuilder(offline).build(
				archiveNumber, picture);
		link.add(new ExternalImage("thumb", imgsrc));
	}

	private static final int CELLS_PER_ROW = 5;
	private static final String CELL_WIDTH = String
			.valueOf(100 / CELLS_PER_ROW) + "%";
}
