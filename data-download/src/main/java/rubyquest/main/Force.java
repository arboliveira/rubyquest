/*
 * Created on 06/04/2010
 */
package rubyquest.main;

import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.generateresources.GenerateResources;

/**
 * @see Main
 */
public class Force {

	public static void main(final String[] args) {
		new GenerateResources(IfTargetExists.Force, DoIt.ForReal).run();
	}
}
