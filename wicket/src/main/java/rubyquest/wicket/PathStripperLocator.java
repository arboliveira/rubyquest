package rubyquest.wicket;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;

public class PathStripperLocator extends ResourceStreamLocator {

	@Override
	public IResourceStream locate(final Class< ? > clazz, final String path) {
		final String trim = trimFolders(path);
		final String lowercase = trim.toLowerCase();
		final IResourceStream located = super.locate(clazz, lowercase);
		if (located != null) {
			return located;
		}
		return super.locate(clazz, path);
	}

	private static String trimFolders(final String path) {
		return StringUtils.substringAfterLast(path, "/");
	}
}