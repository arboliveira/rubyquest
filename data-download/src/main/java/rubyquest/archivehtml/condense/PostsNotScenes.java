package rubyquest.archivehtml.condense;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class PostsNotScenes {

	@SuppressWarnings("boxing")
	private static HashSet<Integer> newExcludes() {
		return new HashSet<Integer>(Arrays.asList(
				//

				3195067, 3195671, 3197738, 3197800, 3197807, 3197823, 3222571,
				3223169, 3223692, 3224310, 3224555, 3252233, 3252817, 3253312,
				3253688, 3282881, 3283427, 3284052, 3284681, 3284712, 3327482,
				3328232, 3328816, 3329499, 3329857, 3381812, 3430126, 3431273,
				3431442, 3494382, 3495492, 3601335, 3602503, 3602546, 3602707

		//
				));
	}

	private static final Collection<Integer> excludes = newExcludes();

	public static boolean isScene(final String post) {
		return !excludes.contains(Integer.valueOf(post));
	}
}
