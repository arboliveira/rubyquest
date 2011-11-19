/*
 * Created on 10/09/2009
 */
package rubyquest.rubyhtml.condense;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.jdom.JDOMException;

import rubyquest.generateresources.RubyPartsXml;
import rubyquest.generateresources.TargetRubyquestDataDownloadDirectory;
import rubyquest.rubyhtml.common.RubyHtml;

public class Condense {

	public static void main(final String[] args) throws IOException,
			JDOMException {
		final TargetRubyquestDataDownloadDirectory inputdir;
		inputdir = new TargetRubyquestDataDownloadDirectory();
		final RubyHtml rubyHtml = new RubyHtml(inputdir);
		new Condense(rubyHtml).run();
	}

	private final File rubyHtmlFile;
	private final File sameDirectory;
	private final RubyPartsXml rubypartsxmltowrite;

	public Condense(final RubyHtml rubyHtml) {
		final File rubyhtmlFile = rubyHtml.getFile();
		final TargetRubyquestDataDownloadDirectory parent = rubyHtml
				.getParent();
		this.rubyHtmlFile = rubyhtmlFile;
		this.sameDirectory = parent.getDirectory();
		this.rubypartsxmltowrite = new RubyPartsXml(parent);
	}

	public RubyPartsXml run() throws IOException, JDOMException {
		final PrintStream txt = newTxt();
		try {
			final PrintStream html = newHtml();
			try {
				final PrintStream xml = newXml();
				try {
					condense(txt, html, xml);
				} finally {
					xml.close();
				}
			} finally {
				html.close();
			}
		} finally {
			txt.close();
		}
		return this.rubypartsxmltowrite;
	}

	private PrintStream newXml() throws FileNotFoundException {
		final PrintStream xml = newPrintStream(rubypartsxmltowrite
				.getPartsXml());
		return xml;
	}

	private PrintStream newTxt() throws FileNotFoundException {
		final File urllist = new File(sameDirectory, "ruby-urls.txt");
		return newPrintStream(urllist);
	}

	private PrintStream newHtml() throws FileNotFoundException {
		final File condensed = new File(sameDirectory, "condensed-"
				+ rubyHtmlFile.getName());
		return newPrintStream(condensed);
	}

	private static PrintStream newPrintStream(final File file)
			throws FileNotFoundException {
		return new PrintStream(new FileOutputStream(file));
	}

	private void condense(final PrintStream txt, final PrintStream html,
			final PrintStream xml) throws JDOMException, IOException,
			FileNotFoundException {
		xml.println("<parts>");
		condenseParts(txt, html, xml);
		xml.println("</parts>");
	}

	private void condenseParts(final PrintStream txt, final PrintStream html,
			final PrintStream xml) throws FileNotFoundException, JDOMException,
			IOException {
		final WritePart visitor = new WritePart(txt, html, xml);
		final InputStream rubyhtmlAsStream = new FileInputStream(rubyHtmlFile);
		try {
			new Extract(rubyhtmlAsStream, visitor).interpret();
		} finally {
			rubyhtmlAsStream.close();
		}
	}

}
