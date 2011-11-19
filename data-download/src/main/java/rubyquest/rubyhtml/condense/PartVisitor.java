/*
 * Created on 12/09/2009
 */
package rubyquest.rubyhtml.condense;

interface PartVisitor {

	void visit(final String url, final String title,
			final String description);
}