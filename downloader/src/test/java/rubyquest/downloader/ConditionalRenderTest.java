package rubyquest.downloader;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.Target;

@RunWith(JMock.class)
public class ConditionalRenderTest {

	final Mockery context = new JUnit4Mockery();
	final Target target = context.mock(Target.class);

	@Test
	public void force_shouldRender() {
		context.checking(new Expectations() {

			{
				oneOf(target).render();
			}
		});
		new ConditionalRender(target, IfTargetExists.Force, DoIt.ForReal)
				.render();
	}

	@Test
	public void nonExistant_shouldRender() {
		context.checking(new Expectations() {

			{
				oneOf(target).exists();
				will(returnValue(false));
				oneOf(target).render();
			}
		});
		renderSkip();
	}

	@Test
	public void existant_shouldSkip() {
		context.checking(new Expectations() {

			{
				oneOf(target).exists();
				will(returnValue(true));
				oneOf(target).onSkip();
			}
		});
		renderSkip();
	}

	@Test
	public void dryRun_shouldDry() {
		context.checking(new Expectations() {

			{
				oneOf(target).onDryRun();
			}
		});
		new ConditionalRender(target, IfTargetExists.Force, DoIt.DryRun)
				.render();
	}

	private void renderSkip() {
		new ConditionalRender(target, IfTargetExists.Skip, DoIt.ForReal)
				.render();
	}
}
