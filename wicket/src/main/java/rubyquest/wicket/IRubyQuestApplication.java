package rubyquest.wicket;

import rubyquest.data.RubyQuestData;

public interface IRubyQuestApplication {

	public RubyQuestData getRubyQuestData();

	public IntToString getLeftPadWithZeroes();
}
