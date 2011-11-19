/*
 * Created on 07/04/2010
 */
package rubyquest.offline.render;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Page;

import rubyquest.downloader.ConditionalRender;
import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.downloader.Downloader;
import rubyquest.offline.render.RenderToFile.PageFactory;
import rubyquest.wicket.PageSpec;
import rubyquest.wicket.Placement;

class Bonus {

	private final File img;
	private final OfflinePageRenderer renderer;
	private final OfflineOutputDirectory offline;
	private final Downloader downloader;
	private final IfTargetExists ifTargetExists;
	private final DoIt doIt;

	Bonus(final OfflineImagesDirectory dirToCopyImages,
			final OfflineOutputDirectory offlinedir,
			final OfflinePageRenderer renderer, final Downloader downloader,
			final IfTargetExists ifTargetExists, final DoIt doIt) {
		this.offline = offlinedir;
		this.renderer = renderer;
		this.ifTargetExists = ifTargetExists;
		this.doIt = doIt;
		this.img = dirToCopyImages.getDirectory();
		this.downloader = downloader;
	}

	void run() throws IOException {
		downloadBonusItems();
		renderOfflineBonusPage();
	}

	private void downloadBonusItems() throws IOException {
		downloadBonusItem(rubyquest.wicket.Bonus.FutureFamilyPhoto);
		downloadBonusItem(rubyquest.wicket.Bonus.Angry_Ruby);
	}

	private void downloadBonusItem(final String from) throws IOException {
		final String name = StringUtils.substringAfterLast(from, "/");
		final URL source = new URL(from);
		final File destination = new File(img, name);
		downloader.download(source, destination);
	}

	private void renderOfflineBonusPage() {
		class BonusPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return new rubyquest.wicket.Bonus(Placement.Offline);
			}
		}
		render(rubyquest.wicket.Bonus.newPageSpec(), new BonusPageFactory());
	}

	private void render(final PageSpec spec, final PageFactory page) {
		final RenderToFile resource = new RenderToFile(spec, page, offline,
				renderer);
		new ConditionalRender(resource, ifTargetExists, doIt).render();
	}
}