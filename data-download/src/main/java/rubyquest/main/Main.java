/*
 * Created on 02/04/2010
 */
package rubyquest.main;

import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.generateresources.GenerateResources;

/**
 * @see Force
 */
public class Main {

	public static void main(final String[] args) {
		new GenerateResources(IfTargetExists.Skip, DoIt.ForReal).run();
	}
}
