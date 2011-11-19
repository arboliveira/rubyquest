/*
 * Created on 20/09/2009
 */
package rubyquest.wicket;

import org.apache.commons.lang.StringUtils;

public class LeftPadWithZeroes implements IntToString {

	private final int size;

	public LeftPadWithZeroes(final int max) {
		this.size = String.valueOf(max).length();
	}

	@Override
	public String toString(final int i) {
		return StringUtils.leftPad(String.valueOf(i), size, '0');
	}

}
