package funny;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

public class TypeForCode {

	public static String getTypeFromCode(final Object obj) {
		Assert.assertTrue(isNumber(obj));
		final String code = obj + "";
		if (code.startsWith("000")) {
			return "深圳主板";
		} else if (code.startsWith("002")) {
			return "中小板";
		} else if (code.startsWith("6")) {
			return "上海主板";
		} else if (code.startsWith("4")) {
			return "新三板";
		} else if (code.startsWith("3")) {
			return "创业板";
		} else if (code.length() == 5) {
			return "港股";
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static boolean isNumber(final Object obj) {
		if (obj == null) {
			return false;
		}
		final Pattern p = Pattern.compile("[0-9]+");
		final Matcher m = p.matcher(obj + "");
		return m.matches();
	}

	public static void main(final String[] args) {
		System.out.println(getTypeFromCode("32456"));
	}

}

// Assert.assertTrue(obj != null && isNumber(obj));
// final String code = obj + "";
// if (code.length() == 6) {
// if (code.startsWith("000")) {
// return "深圳主板";
// } else if (code.startsWith("002")) {
// return "中小板";
// } else if (code.startsWith("6")) {
// return "上海主板";
// } else if (code.startsWith("4")) {
// return "新三板";
// } else if (code.startsWith("3")) {
// return "创业板";
// } else {
// throw new IllegalArgumentException();
// }
// } else if (code.length() == 5) {
// return "港股";
// } else {
// throw new IllegalArgumentException();
// }
