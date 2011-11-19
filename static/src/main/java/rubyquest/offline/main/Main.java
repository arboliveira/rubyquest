/*
 * Created on 06/12/2009
 */
package rubyquest.offline.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.offline.render.RenderOfflineWebsite;

public final class Main {

	public static void main(final String[] args) throws MalformedURLException,
			IOException {
		final File index = renderStaticWebsite();
		launchWebBrowser(index);
	}

	private static File renderStaticWebsite() throws IOException {
		return new RenderOfflineWebsite(IfTargetExists.Skip, DoIt.ForReal)
				.run();
	}

	private static void launchWebBrowser(final File index) throws IOException {
		Desktop.getDesktop().open(index);
	}

}
