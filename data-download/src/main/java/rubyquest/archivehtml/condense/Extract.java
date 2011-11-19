/*
 * Created on 12/09/2009
 */
package rubyquest.archivehtml.condense;

import static rubyquest.archivehtml.condense.SubstringBetween.substringBetween;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.downloader.Downloader;
import rubyquest.generateresources.TargetRubyquestDataDownloadDirectory;
import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;
import rubyquest.thisisnotatrueending.ThisIsNotATrueEnding;
import rubyquest.thisisnotatrueending.URL;

class Extract {

	private static final String weaver = "Weaver</span> <span class=\"postertrip\">!!";

	private static final Collection<String> tripcodes = Arrays.asList(
			"vY8rj3XdjnI", "FWvLi1VQOEj", "t1PjjQn4qVX");

	int position = -1;

	private final WeaverPostVisitor visitor;

	private final ArchiveNumber archiveNumber;

	private final String html;

	private final ExtractNextText extractNextText;

	Extract(final WeaverPostVisitor visitor, final ArchiveNumber archiveNumber,
			final TargetRubyquestDataDownloadDirectory target,
			final IfTargetExists exists, final DoIt doIt) throws IOException {
		this.visitor = visitor;
		this.archiveNumber = archiveNumber;
		final String htmlFromSuptg = downloadHtmlFromSuptg(archiveNumber,
				target, exists, doIt);
		this.html = htmlFromSuptg;
		final CleanupText cleanup = new CleanupText(new FindTextOfPostImpl(
				htmlFromSuptg));
		this.extractNextText = new ExtractNextText(htmlFromSuptg, cleanup);
	}

	private static String downloadHtmlFromSuptg(
			final ArchiveNumber archiveNumber,
			final TargetRubyquestDataDownloadDirectory target,
			final IfTargetExists exists, final DoIt doIt) throws IOException {
		final URL sourceHtml = ThisIsNotATrueEnding.getArchiveURL(archiveNumber
				.getArchiveNumber());
		final File destination = new File(target.getDirectory(), archiveNumber
				+ ".html");
		new Downloader(exists, doIt).download(sourceHtml.toJavaURL(),
				destination);
		final String string = FileUtils.readFileToString(destination, "UTF-8");
		return string;
	}

	void interpret() {
		extractFirstEntry();
		while (true) {
			advanceToNextWeaver();
			if (position == -1) break;
			extractNextEntry();
		}
	}

	private void extractFirstEntry() {
		final Picture picture = extractNextImage(-1);
		advanceToNextWeaver();
		final Text text = extractNextText();
		visitEntry(picture, text);
	}

	private void visitEntry(final Picture picture, final Text text) {
		visitor.visit(text.text, text.post, picture, archiveNumber);
	}

	private void advanceToNextWeaver() {
		final int i = html.indexOf(weaver, position + 1);
		if (i != -1) verifyTripcode(i);
		position = i;
	}

	private void verifyTripcode(final int i) {
		final int tripcodebegin = i + weaver.length();
		final int tripcodelength = 11;
		final int tripcodeend = tripcodebegin + tripcodelength;
		final String tripcode = html.substring(tripcodebegin, tripcodeend);
		if (!tripcodes.contains(tripcode))
			throw new RuntimeException("This is not Weaver! " + tripcode);
	}

	private void extractNextEntry() {
		final Text text = extractNextText();
		final int mustBeBeforeText = text.begin;
		final Picture picture = extractNextImage(mustBeBeforeText);
		visitEntry(picture, text);
	}

	private Picture extractNextImage(final int limitPosition) {
		final int filemarker = findNextFilemarker();
		if (filemarker == -1) return null;
		if (limitPosition != -1 && filemarker >= limitPosition) return null;
		final String archived = extractImageArchivedFilename(filemarker);
		final String original = extractImageOriginalFilename(filemarker);
		final String thumbnail = extractImageThumbnailFilename(filemarker);
		return new Picture(original, archived, thumbnail);
	}

	private String extractImageThumbnailFilename(final int filemarker) {
		final String src = substringBetween(html, "src=", " ", filemarker);
		final String noquotes = StringUtils.remove(src, '"');
		if (StringUtils.isEmpty(noquotes)) throw new IllegalStateException();
		return noquotes;
	}

	private String extractImageOriginalFilename(final int filemarker) {
		return substringBetween(html, "<span title=\"", "\"", filemarker);
	}

	private String extractImageArchivedFilename(final int filemarker) {
		return substringBetween(html, "<a href=\"", "\"", filemarker);
	}

	private Text extractNextText() {
		return this.extractNextText.extractNextText(this.position);
	}

	private int findNextFilemarker() {
		return html.indexOf("File :", position);
	}

}
