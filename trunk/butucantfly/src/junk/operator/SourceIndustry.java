package junk.operator;

import java.io.File;
import java.util.List;

import org.apache.http.HttpHost;
import org.junit.Assert;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class SourceIndustry {

	public static void getData() {
		final String text = Util.loadUrlContent2("http://zdb.pedaily.cn/industry.shtml", "utf-8", (HttpHost) null);
		final Html html = new Html(text);
		final List<Selectable> divs = html.xpath("//div").nodes();
		final StringBuffer sb = new StringBuffer();
		sb.append("pedaily\r\n");
		for (final Selectable div : divs) {
			final String title1 = div.xpath("/div/b/a/text()").get();
			sb.append("\t" + title1 + "\r\n");
			Assert.assertTrue(title1.contains("["));
			final List<Selectable> ps = div.xpath("//div/p").nodes();
			for (final Selectable p : ps) {
				// if (p.xpath("/p/b/a/text()").exists()) {
				// final String title2 = p.xpath("/p/b/a/text()").get();
				// sb.append("\t\t" + title2 + "\r\n");
				// }
				final List<String> list = p.xpath("//p/a/text()").all();
				// final String title3 =
				// ProcessUtil.arrayToStandardStringData(list);
				// sb.append("\t\t\t" + title3 + "\r\n");
			}
			sb.append("\r\n");
		}

		sb.append("chinaventure\r\n");
		final String text2 = Util.loadUrlContent2("http://www.chinaventure.com.cn/event/list.shtml", "utf-8",
				(HttpHost) null);
		final Html html2 = new Html(text2);
		// sb.append("\t" +
		// ProcessUtil.arrayToStandardStringData(html2.xpath("//*[@id='industry']/div[2]/a/text()").all())
		// + "\r\n");
		sb.append("\r\n");

		sb.append("cyzone\r\n");
		final String text3 = Util.loadUrlContent2("http://www.cyzone.cn/vpeople/list-0-1/", "utf-8", (HttpHost) null);
		final Html html3 = new Html(text3);
		final List<String> l = html3.xpath("//div[@class='choose-box']/div[@class='pull-right']/a/text()").all();
		l.remove(0);
		// sb.append("\t" + ProcessUtil.arrayToStandardStringData(l) + "\r\n");
		sb.append("\r\n");
		Util.saveAsFile(new File("d:\\inds.txt"), sb.toString());

	}

	public static void main(final String[] args) {
		final String s = Util.parseRegex(
				"zb_div+='<li>'+'<div class=\"i_01\">成立日期：</div>'+'<div class=\"i_02\">'+'2004年'+'</div></li>';",
				"成立日期：.+\\+'(.+)'\\+'</div></li>");
		System.out.println(s);
	}

}
