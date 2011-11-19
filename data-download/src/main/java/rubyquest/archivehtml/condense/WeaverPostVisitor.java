/*
 * Created on 13/09/2009
 */
package rubyquest.archivehtml.condense;

import rubyquest.model.ArchiveNumber;
import rubyquest.model.Picture;

interface WeaverPostVisitor {

	void visit(String text, String post, Picture picture,
			ArchiveNumber archiveNumber);
}