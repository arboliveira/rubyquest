package rubyquest.model;

public final class Picture {

	private final String original;
	private final String archived;
	private final String thumbnail;

	public Picture(final String original, final String archived,
			final String thumbnail) {
		this.original = original;
		this.archived = archived;
		this.thumbnail = thumbnail;
	}

	public String getOriginal() {
		return original;
	}

	public String getOriginalThumbnailed() {
		return "s" + original;
	}

	public String getArchived() {
		return archived;
	}

	public String getThumbnail() {
		return thumbnail;
	}

}