package rubyquest.offline.render;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;
import org.apache.wicket.Page;

import rubyquest.downloader.ConditionalRender.Target;
import rubyquest.wicket.PageSpec;

class RenderToFile implements Target {

	interface PageFactory {

		Page produce();
	}

	RenderToFile(final PageSpec spec, final PageFactory page,
			final OfflineOutputDirectory offline,
			final OfflinePageRenderer renderer) {
		this.fileToWrite = offline.htmlFile(spec.getOfflineName());
		this.page = page;
		this.renderer = renderer;
	}

	@Override
	public boolean exists() {
		return fileToWrite.exists();
	}

	@Override
	public void render() {
		logger.info("Rendering: " + fileToWrite.getPath());
		try {
			renderToFile();
		} catch (final IOException e) {
			throw new UnhandledException(e);
		}
		logger.info("Rendered: " + fileToWrite.getPath());
	}

	private void renderToFile() throws IOException {
		final String rendered = renderer.render(page.produce());
		FileUtils.writeStringToFile(fileToWrite, rendered, "UTF-8");
	}

	@Override
	public void onSkip() {
		logger.info("Found, skipping rendering: " + fileToWrite.getPath());
	}

	@Override
	public void onDryRun() {
		logger.info("Dry run, skipping rendering: " + fileToWrite.getPath());
	}

	File getFileToWrite() {
		return fileToWrite;
	}

	final Logger logger = Logger.getLogger(this.getClass());

	private final File fileToWrite;
	private final PageFactory page;
	private final OfflinePageRenderer renderer;
}