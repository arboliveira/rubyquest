/*
 * Created on 2011-10-12
 */
package rubyquest.model;

public class PictureMust implements PictureVisitor<Picture> {

	public static final PictureMust SINGLETON = new PictureMust();

	@Override
	public Picture visit(final Picture picture) {
		return picture;
	}

	@Override
	public Picture noPicture() {
		throw new IllegalStateException("Should have found a Picture");
	}

}
