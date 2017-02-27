package htmltest;

import java.io.File;

import common.Util;
import us.codecraft.webmagic.selector.Html;

public abstract class HtmlParseFromLocalFile {

	// private static volatile HtmlPaserFromLocalFile INSTANCE = null;
	//
	// private HtmlPaserFromLocalFile() {
	// }
	//
	// public static HtmlPaserFromLocalFile getInstance() {
	// if (INSTANCE == null) {
	// synchronized (HtmlPaserFromLocalFile.class) {
	// if (INSTANCE == null) {
	// INSTANCE = new HtmlPaserFromLocalFile();
	// }
	// }
	// }
	// return INSTANCE;
	// }

	public abstract Object doSomething(final Html html);

	public Object readFile(final String fileName) {

		final String content = Util.loadFromFile(new File(fileName));

		final Html html = new Html(content);

		return doSomething(html);
	}

	public static void main(final String[] args) {

	}

}
