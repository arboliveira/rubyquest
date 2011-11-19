package rubyquest.model;

import org.apache.commons.lang.Validate;

public class WeaverPost {

	public final int id;

	public final String post;

	public final String text;

	public static WeaverPost newScene(final int id,
			final ArchiveNumber archiveNumber, final String post,
			final String text, final Picture picture, final int scene) {
		Validate.notNull(picture);
		return new WeaverPost(id, archiveNumber, post, text, picture, scene);
	}

	public static WeaverPost newNoScene(final int id,
			final ArchiveNumber archiveNumber, final String post,
			final String text, final Picture picture) {
		Validate.notNull(picture);
		return new WeaverPost(id, archiveNumber, post, text, picture, null);
	}

	public static WeaverPost newNoSceneNoPicture(final int id,
			final ArchiveNumber archiveNumber, final String post,
			final String text) {
		return new WeaverPost(id, archiveNumber, post, text, null, null);
	}

	public ArchiveNumber getArchive() {
		return archiveNumber;
	}

	public <T> T accept(final PictureVisitor<T> visitor) {
		if (this.picture != null) return visitor.visit(picture);
		return visitor.noPicture();
	}

	public Picture getImage() {
		return this.accept(PictureMust.SINGLETON);
	}

	public void accept(final SceneIdVisitor visitor) {
		if (this.scene == null) return;
		visitor.visit(this.scene);
	}

	public interface SceneIdVisitor {

		void visit(int scene);
	}

	private WeaverPost(final int id, final ArchiveNumber archiveNumber,
			final String post, final String text, final Picture picture,
			final Integer scene) {
		Validate.notNull(archiveNumber);
		Validate.notNull(post);
		this.id = id;
		this.post = post;
		this.text = text;
		this.archiveNumber = archiveNumber;
		this.picture = picture;
		this.scene = scene;
	}

	private final ArchiveNumber archiveNumber;
	private final Picture picture;
	private final Integer scene;
}
