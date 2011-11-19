/*
 * Created on 07/04/2010
 */
package rubyquest.archivehtml.condense;

import org.apache.commons.lang.StringUtils;

class SubstringBetween {

	static String substringBetween(final String str, final String open,
			final String close, final int fromIndex) {
		return StringUtils.substringBetween(str.substring(fromIndex), open,
				close);
	}

}