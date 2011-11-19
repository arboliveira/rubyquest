/*
 * Created on 29/09/2009
 */
package rubyquest.offline.render;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.Page;

import rubyquest.data.RubyQuestData;
import rubyquest.data.RubyQuestDataBuilder;
import rubyquest.downloader.ConditionalRender;
import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.downloader.DownloadFileFromThisIsNotATrueEndingArchive;
import rubyquest.downloader.DownloadFromArchive;
import rubyquest.downloader.Downloader;
import rubyquest.offline.common.OutputDirectory;
import rubyquest.offline.render.RenderToFile.PageFactory;
import rubyquest.webappdir.WebappDirectory;
import rubyquest.wicket.About;
import rubyquest.wicket.Begin;
import rubyquest.wicket.LeftPadWithZeroes;
import rubyquest.wicket.Menu;
import rubyquest.wicket.PageSpec;
import rubyquest.wicket.Placement;
import rubyquest.wicket.SceneSelection;
import rubyquest.wicket.Storyboard;
import rubyquest.wicket.TheMetalGlen;
import rubyquest.wicket.Title;

public class RenderOfflineWebsite {

	public RenderOfflineWebsite(final IfTargetExists ifTargetExists,
			final DoIt doIt) throws IOException {
		this.ifTargetExists = ifTargetExists;
		this.doIt = doIt;
		this.downloader = new Downloader(ifTargetExists, doIt);

		final WebappDirectory weap = new WebappDirectory();
		this.webappdir = weap;

		final String leafname = "rubyquest";
		final OfflineOutputDirectory off = new OfflineOutputDirectory(
				new OutputDirectory(), leafname);
		this.offline = off;

		this.dirToCopyImages = new OfflineImagesDirectory(off);
		this.offlinedir = off.getDirectory();
		this.data = RubyQuestDataBuilder.readDataFromRubyQuestXml();
		this.renderer = new OfflinePageRenderer();
		this.pad = new LeftPadWithZeroes(data.sceneCount());
	}

	public File run() throws IOException {
		about();
		begin();
		bonus();
		menu();
		sceneSelection();
		storyboard();
		theMetalGlen();
		title();
		css();
		javascript();
		pictures();
		motionPicture();
		credits();
		return index;
	}

	private void begin() {
		class BeginPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return new Begin(Placement.Offline);
			}
		}
		render(Begin.newPageSpec(), new BeginPageFactory());
	}

	private void storyboard() {
		class StoryboardPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return new Storyboard(Placement.Offline, data);
			}
		}
		render(Storyboard.newPageSpec(), new StoryboardPageFactory());
	}

	private void theMetalGlen() {
		class TheMetalGlenPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return new TheMetalGlen(Placement.Offline, pad);
			}
		}
		render(TheMetalGlen.newPageSpec(), new TheMetalGlenPageFactory());
	}

	private void pictures() {
		final DownloadFromArchive download = new DownloadFileFromThisIsNotATrueEndingArchive(
				dirToCopyImages.getDirectory(), downloader);
		new Pictures(data, download).run();
	}

	private void sceneSelection() {
		class SceneSelectionPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return new SceneSelection(Placement.Offline, data);
			}
		}
		render(SceneSelection.newPageSpec(), new SceneSelectionPageFactory());
	}

	private void css() throws IOException {
		copyFromWebappToOffline("style.css");
	}

	private void copyFromWebappToOffline(final String css) throws IOException {
		FileUtils.copyFile(new File(webappdir.getDirectory(), css), new File(
				offlinedir, css), true);
	}

	private void javascript() throws IOException {
		copyFromWebappToOffline("navigation-keys.js");
	}

	private void title() {
		class TitlePageFactory implements PageFactory {

			@Override
			public Page produce() {
				return new Title(Placement.Offline);
			}
		}
		this.index = render(Title.newPageSpec(), new TitlePageFactory());
	}

	private void motionPicture() {
		new MotionPicture(data, pad, offline, renderer, ifTargetExists, doIt)
				.run();
	}

	private void credits() {
		new Credits(offline, renderer, pad, data, ifTargetExists, doIt).run();
	}

	private void about() {
		class AboutPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return new About(Placement.Offline, data);
			}
		}
		render(About.newPageSpec(), new AboutPageFactory());
	}

	private File render(final PageSpec spec, final PageFactory page) {
		final RenderToFile resource = new RenderToFile(spec, page, offline,
				renderer);
		new ConditionalRender(resource, ifTargetExists, doIt).render();
		return resource.getFileToWrite();
	}

	private void bonus() throws IOException {
		new Bonus(dirToCopyImages, offline, renderer, downloader,
				ifTargetExists, doIt).run();
	}

	private void menu() {
		class MenuPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return new Menu(Placement.Offline, pad);
			}
		}
		render(Menu.newPageSpec(), new MenuPageFactory());
	}

	final RubyQuestData data;
	private final OfflinePageRenderer renderer;
	final LeftPadWithZeroes pad;
	private final File offlinedir;
	private final WebappDirectory webappdir;
	private final OfflineOutputDirectory offline;
	private final OfflineImagesDirectory dirToCopyImages;
	private File index;

	private final IfTargetExists ifTargetExists;
	private final DoIt doIt;
	private final Downloader downloader;

}
