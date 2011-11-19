/*
 * Created on 07/04/2010
 */
package rubyquest.downloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;

import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.downloader.ConditionalRender.Target;

public class Downloader {

	public Downloader(final IfTargetExists exists, final DoIt doIt) {
		this.exists = exists;
		this.doIt = doIt;
	}

	public void download(final URL source, final File destination) {
		new ConditionalRender(new Download(source, destination), this.exists,
				this.doIt).render();
	}

	class Download implements Target {

		private final URL source;
		private final File destination;

		Download(final URL source, final File destination) {
			this.source = source;
			this.destination = destination;
		}

		@Override
		public boolean exists() {
			return destination.exists();
		}

		@Override
		public void render() {
			logger.info("Downloading: " + destination);
			download();
			logger.info("Downloaded: " + destination);
		}

		@Override
		public void onSkip() {
			logger.info("Found, skipping download: " + destination);
		}

		@Override
		public void onDryRun() {
			logger.info("Dry run, skipping download: " + destination);
		}

		private void download() {
			try {
				FileUtils.copyURLToFile(source, destination);
			} catch (final IOException e) {
				throw new UnhandledException(e);
			}
		}
	}

	private final IfTargetExists exists;
	private final DoIt doIt;
	final Logger logger = Logger.getLogger(this.getClass());

}