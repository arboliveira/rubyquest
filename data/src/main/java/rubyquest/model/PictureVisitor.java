package rubyquest.model;

public interface PictureVisitor<T> {

	T visit(Picture picture);

	T noPicture();
}