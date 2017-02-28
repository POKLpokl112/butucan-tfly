package htmltest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class HtmlParseTestGDG extends HtmlParseFromLocalFile {

	@Override
	public Object doSomething(final Html html) {
		final JSONArray array = new JSONArray();

		final int rows = Integer.parseInt(html.xpath("//div[@class='mianBodyStyle']/div[1]/span/text()").get());
		System.out.println(rows);

		for (final Selectable div : html.xpath("//div[@class='mianBodyStyle']/div[@class='clickStyle']").nodes()) {
			final JSONObject data = new JSONObject();

			final String URL = div.xpath("//div/div[1]/a/@href").get();
			data.put("URL", URL);

			final String companyName = Util.normalizeRawText(div.xpath("//div/div[1]/a").toString());
			final String statu = div.xpath("//div/span/span[2]/text()").get().trim();
			data.put("companyName", companyName);
			data.put("statu", statu);

			for (final Selectable td : div.xpath("//td").nodes()) {
				final String[] clearData = Util.normalizeRawText(td.toString()).split("：|:");
				data.put(clearData[0], clearData[1].trim());
			}

			array.add(data);
		}

		return array;

	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestGDG().readFile();
		System.out.println(obj);

	}
}
