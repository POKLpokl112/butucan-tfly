package htmltest;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class HtmlParseTestNEN extends HtmlParseFromLocalFile {

	// TJT
	@Override
	public Object doSomething(final Html html) {

		final int rows = Integer.parseInt(html.xpath("//div[@class='search_result']/span/text()").toString());
		System.out.println(rows);

		final JSONArray array = new JSONArray();
		for (final Selectable a : html.xpath("//div[@class='container']/div[3]/a").nodes()) {

			final JSONObject data = new JSONObject();
			final String URL = "http://www.gsxt.gov.cn" + a.xpath("//a/@href").get();
			data.put("URL", URL);

			final String companyName = Util.normalizeRawText(a.xpath("//a/h1").toString());
			final String statu = a.xpath("//a/div[1]/span/text()").get();
			data.put("companyName", companyName);
			data.put("statu", statu);

			final Map<String, String> map = new HashMap<>();
			for (final Selectable div : a.xpath("//a/div[2]/div").nodes()) {
				final String[] clearData = Util.normalizeRawText(div.toString()).split("：|:");
				map.put(clearData[0], clearData[1].trim());
			}

			if (map.containsKey("注册号")) {
				map.put("统一社会信用代码/注册号", map.get("注册号"));
			} else if (map.containsKey("统一社会信用代码")) {
				map.put("统一社会信用代码/注册号", map.get("统一社会信用代码"));
			} else {
				throw new IllegalArgumentException("无 -- 统一社会信用代码/注册号");
			}
			data.putAll(map);
			array.add(data);
		}
		return array;
	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestNEN().readFile();
		System.out.println(obj);

	}

}
