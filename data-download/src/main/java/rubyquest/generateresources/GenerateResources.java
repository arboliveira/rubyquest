/*
 * Created on 06/04/2010
 */
package rubyquest.generateresources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import rubyquest.archivehtml.condense.Condense;
import rubyquest.downloader.ConditionalRender;
import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.downloader.ConditionalRender.Target;
import rubyquest.io.FileInputStreamFactory;
import rubyquest.io.FileOutputStreamFactory;
import rubyquest.rubyhtml.common.RubyHtml;
import rubyquest.rubyhtml.download.Download;

public class GenerateResources {

	public GenerateResources(final IfTargetExists ifTargetExists,
			final DoIt doIt) {
		this.ifTargetExists = ifTargetExists;
		this.doIt = doIt;
		this.targetdir = newTargetDirectory();
	}

	public void run() {
		new ConditionalRender(new Generate(), ifTargetExists, doIt).render();
	}

	class Generate implements Target {

		Generate() {
			this.rubyQuestXmlFile = determineXmlFile();
		}

		@Override
		public boolean exists() {
			return rubyQuestXmlFile.exists();
		}

		@Override
		public void onSkip() {
			logger.info("Found, skipping generation: " + rubyQuestXmlFile);
		}

		@Override
		public void onDryRun() {
			logger.info("Dry run, skipping generation: " + rubyQuestXmlFile);
		}

		@Override
		public void render() {
			logger.info("Generating: " + rubyQuestXmlFile);
			generate();
			logger.info("Generated: " + rubyQuestXmlFile);
		}

		private void generate() {
			try {
				tryGenerate(rubyQuestXmlFile);
			} catch (final IOException e) {
				throw new UnhandledException(e);
			} catch (final JDOMException e) {
				throw new UnhandledException(e);
			}
		}

		private final File rubyQuestXmlFile;
	}

	static File determineXmlFile() {
		final RubyQuestXmlToWrite rubyQuestXmlToWrite = newRubyQuestXmlToWrite();
		return rubyQuestXmlToWrite.getFile();
	}

	void tryGenerate(final File rubyQuestXmlFile) throws IOException,
			JDOMException {
		final RubyPartsXml rubyPartsXml = firstGenerateRubyPartsXml();
		thenCondenseRubyQuestXml(rubyQuestXmlFile, rubyPartsXml);
	}

	private static TargetRubyquestDataDownloadDirectory newTargetDirectory() {
		try {
			return new TargetRubyquestDataDownloadDirectory();
		} catch (final FileNotFoundException e) {
			throw new UnhandledException(e);
		}
	}

	private static RubyQuestXmlToWrite newRubyQuestXmlToWrite() {
		try {
			return new RubyQuestXmlToWrite();
		} catch (final FileNotFoundException e) {
			throw new UnhandledException(e);
		}
	}

	private RubyPartsXml firstGenerateRubyPartsXml()
			throws MalformedURLException, IOException, JDOMException {
		final RubyHtml rubyHtml = firstDownloadRubyHtml();
		return thenCondenseRubyPartsXml(rubyHtml);
	}

	private RubyHtml firstDownloadRubyHtml() throws MalformedURLException,
			IOException {
		final RubyHtml rubyHtml = newDownload().run();
		return rubyHtml;
	}

	private Download newDownload() {
		return new rubyquest.rubyhtml.download.Download(targetdir,
				ifTargetExists, doIt);
	}

	private static RubyPartsXml thenCondenseRubyPartsXml(final RubyHtml rubyHtml)
			throws IOException, JDOMException {
		final RubyPartsXml rubyPartsXml = new rubyquest.rubyhtml.condense.Condense(
				rubyHtml).run();
		return rubyPartsXml;
	}

	private void thenCondenseRubyQuestXml(final File rubyQuestXmlFile,
			final RubyPartsXml rubyPartsXml) throws IOException {
		final FileInputStreamFactory in = new FileInputStreamFactory(
				rubyPartsXml.getPartsXml());
		final FileOutputStreamFactory out = new FileOutputStreamFactory(
				rubyQuestXmlFile);
		newCondense(in, out).run();
	}

	private Condense newCondense(final FileInputStreamFactory in,
			final FileOutputStreamFactory out) {
		return new rubyquest.archivehtml.condense.Condense(in, out, targetdir,
				ifTargetExists, doIt);
	}

	private final IfTargetExists ifTargetExists;
	private final DoIt doIt;
	private final TargetRubyquestDataDownloadDirectory targetdir;
	final Logger logger = Logger.getLogger(this.getClass());

}
