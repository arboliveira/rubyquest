package rubyquest.downloader;

public class ConditionalRender {

	public static enum DoIt {
		ForReal, DryRun
	}

	public interface Target {

		boolean exists();

		void render();

		void onSkip();

		void onDryRun();
	}

	public enum IfTargetExists {
		Skip, Force
	}

	public ConditionalRender(final Target target, final IfTargetExists exists,
			final DoIt doIt) {
		this.target = target;
		this.exists = exists;
		this.doIt = doIt;
	}

	public void render() {
		if (exists == IfTargetExists.Skip && target.exists()) {
			target.onSkip();
			return;
		}
		if (doIt == DoIt.DryRun) {
			target.onDryRun();
			return;
		}
		target.render();
	}

	private final Target target;
	private final IfTargetExists exists;
	private final DoIt doIt;
}
