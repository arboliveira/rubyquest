package rubyquest.thisisnotatrueending;

import java.net.MalformedURLException;

public class URL {

	private final String path;

	public URL(final String path) {
		this.path = path;
	}

	public URL(final URL parent, final String path) {
		this.path = parent.path + "/" + path;
	}

	public String toExternalForm() {
		return "http://suptg.thisisnotatrueending.com/" + path;
	}

	public java.net.URL toJavaURL() throws MalformedURLException {
		return new java.net.URL(this.toExternalForm());
	}
}
