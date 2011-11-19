package rubyquest.model;

public class Flow {

	private final int sceneCount;

	public Flow(final int sceneCount) {
		this.sceneCount = sceneCount;
	}

	public Integer nextScene(final int sceneid) {
		final boolean last = sceneid == sceneCount;
		if (last) {
			return null;
		}
		return Integer.valueOf(sceneid + 1);
	}

	public Integer previousScene(final int sceneid) {
		final boolean first = sceneid == 1;
		if (first) {
			return null;
		}
		return Integer.valueOf(sceneid - 1);
	}

}