/*
 * Created on 06/04/2010
 */
package rubyquest.io;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.lang.SystemUtils;

public class FindDirectory {

	public static File find(final String contains) throws FileNotFoundException {
		final File parent = findParent(contains);
		return new File(parent, contains);
	}

	public static File findParent(final String contains)
			throws FileNotFoundException {
		File parent = SystemUtils.getUserDir();
		while (true) {
			final File child = new File(parent, contains);
			if (child.isDirectory()) {
				return parent;
			}
			parent = parent.getParentFile();
			if (parent == null) {
				throw new FileNotFoundException(contains);
			}
		}
	}
}
