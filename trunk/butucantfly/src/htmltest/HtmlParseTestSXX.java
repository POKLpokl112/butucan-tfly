package htmltest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class HtmlParseTestSXX extends HtmlParseFromLocalFile {

	// TJT
	@Override
	public Object doSomething(final Html html) {

		final int rows = Integer.parseInt(html.xpath("//span[@class='fred1']/text()").get());
		System.out.println(rows);

		final JSONArray array = new JSONArray();

		for (final Selectable a : html.xpath("//*[@id='myDiv']/a").nodes()) {
			final JSONObject data = new JSONObject();

			final String onclick = a.xpath("//a/@onclick").get();
			final String URL = "http://sn.gsxt.gov.cn/ztxy.do?method=qyinfo_jcxx&random=1779155792443&pripid="
					+ Util.parseRegex(onclick, "\\('([^']+)");
			data.put("URL", URL);

			final String companyName = a.xpath("//*[@id='mySpan']/@title").get();
			final String statu = a.xpath("//a/p[1]/span[2]/text()").get().trim();
			data.put("companyName", companyName);
			data.put("statu", statu);

			for (final Selectable span : a.xpath("//a/p[2]/span").nodes()) {
				final String[] clearData = Util.normalizeRawText(span.toString()).split("：|:");
				data.put(clearData[0], clearData[1].trim());
			}
			array.add(data);
		}
		return array;
	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestSXX().readFile();
		System.out.println(obj);

	}

}
