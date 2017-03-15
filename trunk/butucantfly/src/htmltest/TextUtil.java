package htmltest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.codecraft.webmagic.selector.Html;

public class TextUtil {

	public static List<String> parseText(final String text, final String regex) {
		final List<String> list = new ArrayList<>();
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(text);
		while (m.find()) {
			list.add(m.group());
		}
		return list;

	}

	public static Html getHtmlStack(final String rawText, final String regex) {
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(rawText);
		while (m.find()) {
			final String text = m.group();
			return new Html(text);
		}
		return new Html("");
	}

	public static void main(final String[] args) {
		System.out.println("2012".compareTo("2013"));
	}

}
