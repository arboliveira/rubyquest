/*
 * Created on 2011-10-12
 */
package rubyquest.model;

public abstract class PictureMaybe<T> implements PictureVisitor<T> {

	@Override
	public T noPicture() {
		// Do nothing
		return null;
	}

}
