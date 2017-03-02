package htmltest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class HtmlParseTestNXY extends HtmlParseFromLocalFile {

	// TJT
	@Override
	public Object doSomething(final Html html) {

		final int rows = Integer.parseInt(html.xpath("//li[@class='list_txt01']/span/text()").get());
		System.out.println(rows);

		final JSONArray array = new JSONArray();

		for (final Selectable li : html.xpath("//div[@id='qylist']/li").nodes()) {

			final JSONObject data = new JSONObject();

			final String URL = "http://nx.gsxt.gov.cn/" + li.xpath("//li/div/h3/a/@href").get();
			data.put("URL", URL);
			final String companyName = Util.normalizeRawText(li.xpath("//li/div/h3/a").toString());
			final String statu = li.xpath("//li/div/div/text()").get();
			data.put("companyName", companyName);
			data.put("statu", statu);

			for (final Selectable p : li.xpath("//div[2]/p").nodes()) {
				final String[] clearData = Util.normalizeRawText(p.toString()).split("：|:");
				data.put(clearData[0], clearData[1].trim());
			}
			array.add(data);
		}
		return array;
	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestNXY().readFile();
		System.out.println(obj);

	}

}
