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

	public Object doSomething(final Html html) {
		throw new IllegalArgumentException();
	}

	public Object readFile() {

		final String content = Util.loadFromFile(new File("D://robot//test.html"));

		final Html html = new Html(content);

		return doSomething(html);
	}

	public Object readFile2() {

		final String content = Util.loadFromFile(new File("D://robot//test.html"));

		return doSomething(content);
	}

	public Object doSomething(final String pageContent) {
		throw new IllegalArgumentException();
	}

	public static void main(final String[] args) {

	}

}
