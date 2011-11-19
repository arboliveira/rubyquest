package rubyquest.model;

public class Scene {
	public int getId() {
		return id;
	}

	public int getWeaverpost() {
		return weaverpost;
	}

	public Scene(final int id, final int weaverpost) {
		this.weaverpost = weaverpost;
		this.id = id;
	}

	int id;
	int weaverpost;
}
