/*
 * Created on 11/09/2009
 */
package rubyquest.archivehtml.condense;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.UnhandledException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.generateresources.RubyPartsXml;
import rubyquest.generateresources.RubyQuestXmlToWrite;
import rubyquest.generateresources.TargetRubyquestDataDownloadDirectory;
import rubyquest.io.FileInputStreamFactory;
import rubyquest.io.FileOutputStreamFactory;
import rubyquest.io.InputStreamFactory;
import rubyquest.io.InputStreamManager;
import rubyquest.io.InputStreamUsage;
import rubyquest.io.OutputStreamFactory;
import rubyquest.io.OutputStreamManager;
import rubyquest.io.OutputStreamUsage;
import rubyquest.jdom.DocumentFactory;
import rubyquest.model.ArchiveNumber;

public class Condense {

	public static void main(final String[] args) throws IOException {
		final TargetRubyquestDataDownloadDirectory downloadDir = new TargetRubyquestDataDownloadDirectory();
		final FileInputStreamFactory in = new FileInputStreamFactory(
				new RubyPartsXml(downloadDir).getPartsXml());
		final FileOutputStreamFactory out = new FileOutputStreamFactory(
				new RubyQuestXmlToWrite().getFile());
		new Condense(in, out, downloadDir, IfTargetExists.Skip, DoIt.ForReal)
				.run();
	}

	public Condense(final InputStreamFactory rubyPartsXmlInputStream,
			final OutputStreamFactory xmltowriteOutputStream,
			final TargetRubyquestDataDownloadDirectory downloaddir,
			final IfTargetExists exists, final DoIt doIt) {
		this.rubyPartsXmlInputStream = rubyPartsXmlInputStream;
		this.xmltowriteOutputStream = xmltowriteOutputStream;
		this.downloaddir = downloaddir;
		this.exists = exists;
		this.doIt = doIt;
		this.xml = newXml();
	}

	public void run() throws IOException {
		new InputStreamManager(this.rubyPartsXmlInputStream)
				.openUsing(new CondensePartsXml());
		new OutputStreamManager(this.xmltowriteOutputStream)
				.openUsing(new CloseXml());
	}

	class CondensePartsXml implements InputStreamUsage<Object> {

		@Override
		public Object use(final InputStream in) throws IOException {
			condense(in);
			return null;
		}

	}

	void condense(final InputStream partsXmlAsStream) throws IOException {
		final Element rootElement = xml.getRootElement();
		final Element weaverposts = addElementWeaverPosts(rootElement);
		final WeaverPostVisitor visitor = new WriteWeaverPost(weaverposts);
		final Element partsRootElement = buildDocumentPartsXml(partsXmlAsStream)
				.getRootElement();
		visitArchiveElements(visitor, partsRootElement);
	}

	private static Document buildDocumentPartsXml(
			final InputStream partsXmlAsStream) throws IOException {
		try {
			return DocumentFactory.buildDocument(partsXmlAsStream);
		} catch (final JDOMException e) {
			throw new UnhandledException(e);
		}
	}

	private static Element addElementWeaverPosts(final Element rootElement) {
		final Element weaverposts = new Element("weaverposts");
		rootElement.addContent(weaverposts);
		return weaverposts;
	}

	private void visitArchiveElements(final WeaverPostVisitor visitor,
			final Element partsRootElement) throws IOException {
		@SuppressWarnings("unchecked")
		final List<Element> children = partsRootElement.getChildren("archive");
		for (final Element e : children) {
			final ArchiveNumber archiveNumber = new ArchiveNumber(
					Integer.valueOf(e.getText()));
			new Extract(visitor, archiveNumber, downloaddir, exists, doIt)
					.interpret();
		}
	}

	private static Document newXml() {
		final Document document = new Document(new Element("ruby"));
		return document;
	}

	class CloseXml implements OutputStreamUsage {

		@Override
		public void use(final OutputStream out) throws IOException {
			closeXml(out);
		}

	}

	void closeXml(final OutputStream outputStream) throws IOException {
		final XMLOutputter xmlOutputter = new XMLOutputter(
				Format.getPrettyFormat());
		xmlOutputter.output(xml, outputStream);
	}

	private final InputStreamFactory rubyPartsXmlInputStream;
	private final OutputStreamFactory xmltowriteOutputStream;
	private final TargetRubyquestDataDownloadDirectory downloaddir;
	private final Document xml;
	private final IfTargetExists exists;
	private final DoIt doIt;

}
