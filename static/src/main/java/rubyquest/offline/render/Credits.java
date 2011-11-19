/*
 * Created on 02/10/2009
 */
package rubyquest.offline.render;

import org.apache.wicket.Page;

import rubyquest.data.RubyQuestData;
import rubyquest.downloader.ConditionalRender;
import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.offline.render.RenderToFile.PageFactory;
import rubyquest.wicket.End;
import rubyquest.wicket.Endtitle;
import rubyquest.wicket.IntToString;
import rubyquest.wicket.PageSpec;
import rubyquest.wicket.Placement;

public class Credits {

	public Credits(final OfflineOutputDirectory offline,
			final OfflinePageRenderer renderer, final IntToString pad,
			final RubyQuestData data, final IfTargetExists ifTargetExists,
			final DoIt doIt) {
		this.offline = offline;
		this.renderer = renderer;
		this.pad = pad;
		this.data = data;
		this.ifTargetExists = ifTargetExists;
		this.doIt = doIt;
	}

	public void run() {
		endtitle();
		ends();
		credits();
	}

	private void endtitle() {
		class EndtitlePageFactory implements PageFactory {

			@Override
			public Page produce() {
				return newOfflineEndtitle();
			}
		}
		render(Endtitle.newPageSpec(), new EndtitlePageFactory());
	}

	Page newOfflineEndtitle() {
		return new Endtitle(pad, data, Placement.Offline);
	}

	private void ends() {
		for (int i = 1; i <= End.count(); i++)
			renderEnd(i);
	}

	private void renderEnd(final int i) {
		class EndPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return newOfflineEnd(i);
			}
		}
		render(End.newSpec(i), new EndPageFactory());
	}

	Page newOfflineEnd(final int i) {
		return new End(Placement.Offline, i, data);
	}

	private void credits() {
		for (int i = 1; i <= rubyquest.wicket.Credits.count(); i++)
			renderCredits(i);
	}

	private void renderCredits(final int i) {
		class CreditsPageFactory implements PageFactory {

			@Override
			public Page produce() {
				return newOfflineCredits(i);
			}
		}
		render(rubyquest.wicket.Credits.newSpec(i), new CreditsPageFactory());
	}

	static Page newOfflineCredits(final int i) {
		return new rubyquest.wicket.Credits(Placement.Offline, i);
	}

	private void render(final PageSpec spec, final PageFactory page) {
		final RenderToFile resource = new RenderToFile(spec, page, offline,
				renderer);
		new ConditionalRender(resource, ifTargetExists, doIt).render();
	}

	private final OfflineOutputDirectory offline;
	private final OfflinePageRenderer renderer;
	private final RubyQuestData data;
	private final IntToString pad;
	private final IfTargetExists ifTargetExists;
	private final DoIt doIt;

}
