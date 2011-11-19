/*
 * Created on 07/04/2010
 */
package rubyquest.archivehtml.condense;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

class GetResourceAsString {

	static String getResourceAsString(final Class< ? > c,
			final String suffix) throws IOException {
		final String name = c.getSimpleName() + "." + suffix;
		return IOUtils.toString(c.getResourceAsStream(name), "UTF-8");
	}

}