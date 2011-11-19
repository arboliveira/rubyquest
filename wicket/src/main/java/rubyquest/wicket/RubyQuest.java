/*
 * Created on 13/09/2009
 */
package rubyquest.wicket;

import java.io.IOException;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import rubyquest.data.RubyQuestData;
import rubyquest.data.RubyQuestDataBuilder;

/**
 * The Wicket WebApplication for the RubyQuest website.
 */
public class RubyQuest extends WebApplication implements IRubyQuestApplication {

	private final RubyQuestData data;
	private final LeftPadWithZeroes leftPadWithZeroes;
	private final boolean offline;

	public static RubyQuest get() {
		return (RubyQuest) Application.get();
	}

	public RubyQuest() throws IOException {
		this(false);
	}

	public RubyQuest(final boolean offline) throws IOException {
		this.offline = offline;
		this.data = RubyQuestDataBuilder.readDataFromRubyQuestXml();
		this.leftPadWithZeroes = new LeftPadWithZeroes(data.sceneCount());
	}

	@Override
	public Class< ? extends Page> getHomePage() {
		return Title.class;
	}

	@Override
	protected void init() {
		super.init();
		ApplicationInit.init(this, offline);
	}

	@Override
	public RubyQuestData getRubyQuestData() {
		return data;
	}

	@Override
	public LeftPadWithZeroes getLeftPadWithZeroes() {
		return leftPadWithZeroes;
	}

	@Override
	public String getConfigurationType() {
		return Application.DEPLOYMENT;
	}
}
