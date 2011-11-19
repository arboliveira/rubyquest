/*
 * Created on 20/09/2009
 */
package rubyquest.wicket;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.WebPage;

import rubyquest.offline.Images;

public class Bonus extends WebPage {

	public static final String FutureFamilyPhoto = "http://1d4chan.org/images/4/4f/FutureFamilyPhoto.png";
	public static final String Angry_Ruby = "http://1d4chan.org/images/e/e2/Angry_Ruby.jpg";

	public static PageSpec newPageSpec() {
		return new SimplePageSpec(Bonus.class);
	}

	public Bonus() {
		this(Placement.Online);
	}

	public Bonus(final Placement offline) {
		this.offline = offline;
		WebPageAll.onCreate(this);
		addBonus("img-FutureFamilyPhoto", FutureFamilyPhoto);
		addBonus("img-Angry_Ruby", Angry_Ruby);
	}

	private void addBonus(final String id, final String url) {
		final String imgsrc;
		if (offline == Placement.Offline) {
			final String name = StringUtils.substringAfterLast(url, "/");
			imgsrc = Images.IMAGES + "/" + name;
		} else {
			imgsrc = url;
		}
		add(new ExternalImage(id, imgsrc));
	}

	private final Placement offline;

}
