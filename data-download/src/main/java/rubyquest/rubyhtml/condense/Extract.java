/*
 * Created on 12/09/2009
 */
package rubyquest.rubyhtml.condense;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import rubyquest.jdom.DocumentFactory;

class Extract {

	private final PartVisitor visitor;
	private final List<Element> tableRowsWithData;
	private int partsFound;

	Extract(final InputStream rubyhtmlAsStream, final PartVisitor visitor)
			throws JDOMException, IOException {
		this.tableRowsWithData = findTableRowsWithData(rubyhtmlAsStream);
		this.visitor = visitor;
	}

	void interpret() {
		for (final Element tro : tableRowsWithData) {
			interpretRow(tro);
		}
	}

	private static List<Element> findTableRowsWithData(
			final InputStream rubyhtmlAsStream) throws JDOMException,
			IOException {
		final Document d = DocumentFactory.buildDocument(rubyhtmlAsStream);
		final Element rootElement = d.getRootElement();
		final Element table = findArchiveTable(rootElement);
		@SuppressWarnings("rawtypes")
		final List trsall = table.getChildren();
		@SuppressWarnings("unchecked")
		final List<Element> trs = trsall.subList(1, trsall.size());
		return trs;
	}

	private static Element findArchiveTable(final Element rootElement) {
		final Element body = rootElement.getChild("body");
		final Element div2nd = (Element) body.getChildren("div").get(1);
		final Element div = div2nd.getChild("div");
		final Element form = div.getChild("form");
		final Element center = form.getChild("center");
		final Element table = center.getChild("table");
		return table;
	}

	private void interpretRow(final Element tr) {
		@SuppressWarnings("rawtypes")
		final List tds = tr.getChildren();
		if (tds.size() == 1) {
			return;
		}
		final Element tdtitle = (Element) tds.get(1);
		final String title = tdtitle.getText();
		if (!title.toLowerCase().contains(" part ")) {
			return;
		}
		partsFound++;
		if (!title.contains(String.valueOf(partsFound))) {
			throw new RuntimeException("missed a part?!");
		}
		interpretPart(tds, title);
	}

	private void interpretPart(@SuppressWarnings("rawtypes") final List tds,
			final String title) {
		final Element tda = (Element) tds.get(0);
		final Element a = tda.getChild("a");
		final Attribute href = a.getAttribute("href");
		final Element tddescription = (Element) tds.get(2);
		final String description = tddescription.getText();
		visitor.visit(href.getValue(), title, description);
	}

}