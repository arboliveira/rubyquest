/*
 * Created on 07/04/2010
 */
package rubyquest.archivehtml.condense;

import org.apache.commons.lang.StringUtils;

class CleanupText {

	private final CleanupOtherPeoplesPosts otherPeoplesPosts;

	CleanupText() {
		this(null);
	}

	CleanupText(final FindTextOfPost findTextOfPost) {
		this.otherPeoplesPosts = new CleanupOtherPeoplesPosts(findTextOfPost);
	}

	String cleanupText(final String original) {
		final String noposts = otherPeoplesPosts.cleanup(original);
		final String commas = StringUtils.replace(noposts, "&#44;", ",");
		final String specialized = SpecialTextConvert.convert(commas);
		return specialized;
	}

}