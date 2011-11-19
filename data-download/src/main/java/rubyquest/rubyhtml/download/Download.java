/*
 * Created on 10/09/2009
 */
package rubyquest.rubyhtml.download;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.downloader.Downloader;
import rubyquest.generateresources.TargetRubyquestDataDownloadDirectory;
import rubyquest.rubyhtml.common.RubyHtml;
import rubyquest.thisisnotatrueending.ThisIsNotATrueEnding;
import rubyquest.thisisnotatrueending.URL;

public class Download {

	public static void main(final String[] args) throws MalformedURLException,
			IOException {
		new Download(new TargetRubyquestDataDownloadDirectory(),
				IfTargetExists.Skip, DoIt.ForReal).run();
	}

	public Download(final TargetRubyquestDataDownloadDirectory targetdir,
			final IfTargetExists exists, final DoIt doIt) {
		this.targetdir = targetdir;
		this.downloader = new Downloader(exists, doIt);
	}

	public RubyHtml run() throws MalformedURLException, IOException {
		final RubyHtml rubyHtml = new RubyHtml(targetdir);
		final URL url = ThisIsNotATrueEnding.getRubyHtmlURL();
		final File file = rubyHtml.getFile();
		this.downloader.download(url.toJavaURL(), file);
		return rubyHtml;
	}

	private final TargetRubyquestDataDownloadDirectory targetdir;
	private final Downloader downloader;

}
