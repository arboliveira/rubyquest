/*
 * Created on 22/09/2009
 */
package rubyquest.archivehtml.condense;

import java.lang.Character.UnicodeBlock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialTextConvert {

	static private final Pattern PATTERN = newPattern();

	public static String convert(final String t) {
		int pos = 0;
		final StringBuilder result = new StringBuilder(t.length());
		final Matcher m = PATTERN.matcher(t);
		while (m.find()) {
			final int start = m.start();
			final String fromPosToStart = t.substring(pos, start);
			result.append(fromPosToStart);
			final int end = m.end();
			result.append("<span class=\"special-text\">");
			convert(result, t, start, end);
			result.append("</span>");
			pos = end;
		}
		final String rest = t.substring(pos);
		result.append(rest);
		final String r = result.toString();
		return r;
	}

	private static Pattern newPattern() {
		final String block = "\\p{In"
				+ UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS + "}";
		final String wantedCharacters = "',. \\?\\!";
		return Pattern.compile("(" + block + "+" + "[" + wantedCharacters
				+ block + "]+)");
	}

	private static void convert(final StringBuilder target, final String t2,
			final int start, final int end) {
		for (int i = start; i < end; i++) {
			final char ch = t2.charAt(i);
			final char cc;
			if (isSpecial(ch)) {
				cc = (char) (ch - 65248);
			} else {
				cc = ch;
			}
			target.append(cc);
		}
	}

	private static boolean isSpecial(final char ch) {
		final UnicodeBlock of = Character.UnicodeBlock.of(ch);
		return of.equals(UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
	}

}
