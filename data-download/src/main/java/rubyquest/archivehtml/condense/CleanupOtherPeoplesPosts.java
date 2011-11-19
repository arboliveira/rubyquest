/*
 * Created on 07/04/2010
 */
package rubyquest.archivehtml.condense;

import org.apache.commons.lang.StringUtils;

class CleanupOtherPeoplesPosts {

	private final FindTextOfPost findTextOfPost;

	public CleanupOtherPeoplesPosts(final FindTextOfPost findTextOfPost) {
		this.findTextOfPost = findTextOfPost;
	}

	private static final String open = "<font class=\"unkfunc\"><a href=\"#";
	private static final String close = "</a></font>";

	String cleanup(final String original) {
		String noposts = original;
		final StringBuilder absorbed = new StringBuilder();
		while (true) {
			final String somebodysPost = findSomebodysPost(noposts);
			if (somebodysPost == null) break;

			final String snippetToRemove = open + somebodysPost + close;
			final String br = determineBreak(noposts, snippetToRemove);
			final String snippetToRemoveWithBreak = snippetToRemove + br;
			final String aLittleCleaner = StringUtils.remove(noposts, snippetToRemoveWithBreak);
			noposts = aLittleCleaner;

			if (findTextOfPost != null)
				appendTextOfPost(absorbed, somebodysPost);
		}
		final String a = absorbed.toString();
		if (StringUtils.isEmpty(a)) return noposts;
		return "<div class=\"someone-elses-post\">" + a + "</div>" + noposts;
	}

	private static String findSomebodysPost(final String noposts) {
		final String somebodysPost = StringUtils.substringBetween(noposts,
				open, close);
		return somebodysPost;
	}

	private void appendTextOfPost(final StringBuilder absorbed,
			final String somebodysPost) {
		final String number = StringUtils.substringBefore(somebodysPost, "\"");
		final String text = findTextOfPost.findTextOfPost(number);
		final String div = "<p>" + text + "</p>";
		absorbed.append(div);
	}

	private static String determineBreak(final String noposts,
			final String remove) {
		final String br1 = "<br>";
		final String br2 = "<br />";
		final String br;
		if (after(noposts, remove, br1))
			br = br1;
		else if (after(noposts, remove, br2))
			br = br2;
		else
			br = "";
		return br;
	}

	private static boolean after(final String noposts, final String remove,
			final String br1) {
		final int i1 = noposts.indexOf(remove) + remove.length();
		final int i2 = i1 + br1.length();
		if (i2 > noposts.length()) return false;
		final String substring = noposts.substring(i1, i2);
		return br1.equals(substring);
	}
}