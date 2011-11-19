/*
 * Created on 11/09/2009
 */
package rubyquest.jdom;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class DocumentFactory {

	public static Document buildDocument(final InputStream in)
			throws JDOMException, IOException {
		final InputStreamReader characterStream = new InputStreamReader(in);
		return buildDocument(characterStream);
	}

	public static Document buildDocument(final Reader characterStream)
			throws JDOMException, IOException {
		final SAXBuilder builder = new SAXBuilder();
		builder.setFeature(
				"http://apache.org/xml/features/nonvalidating/load-external-dtd",
				false);
		final Document d = builder.build(characterStream);
		return d;
	}

}
