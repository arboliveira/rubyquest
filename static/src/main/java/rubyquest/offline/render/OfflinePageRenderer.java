/*
 * Created on 13/09/2009
 */
package rubyquest.offline.render;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.util.tester.BaseWicketTester;

import rubyquest.wicket.ApplicationInit;

public class OfflinePageRenderer {

	public OfflinePageRenderer() {
		tester = new BaseWicketTester(new OfflinePageRendererApplication());
	}

	public String render(final Page page) {
		tester.startPage(page);
		final String document = tester.getServletResponse().getDocument();
		return document;
	}

	static class OfflinePageRendererApplication extends
			BaseWicketTester.DummyWebApplication {

		@Override
		public String getConfigurationType() {
			return Application.DEPLOYMENT;
		}

		@Override
		protected void init() {
			super.init();
			ApplicationInit.init(this, true);
		}
	}

	private final BaseWicketTester tester;

}
