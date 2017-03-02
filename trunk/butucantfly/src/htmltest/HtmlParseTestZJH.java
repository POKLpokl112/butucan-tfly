package htmltest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class HtmlParseTestZJH extends HtmlParseFromLocalFile {

	// TJT
	@Override
	public Object doSomething(final Html html) {

		final int rows = Integer.parseInt(html.xpath("//h3/span/text()").get());
		System.out.println(rows);

		final JSONArray array = new JSONArray();

		for (final Selectable li : html.xpath("//ul[@class='enterprise-info-list']/li").nodes()) {
			final JSONObject data = new JSONObject();

			final String a = li.xpath("//li/a/@href").get();
			final String docId = Util.parseRegex(a, "docId=([^&]+)");
			final String classFlag = Util.parseRegex(a, "classFlag=([^&]+)");
			final String pubType = Util.parseRegex(a, "pubType=([^&]+)");// pubType
			final String URL = "http://zj.gsxt.gov.cn/entinfo/baseinfo?encryPriPID=" + docId + "&classFlag="
					+ classFlag;

			data.put("pubType", pubType);
			data.put("URL", URL);

			final String companyName = Util.normalizeRawText(li.xpath("//li/a/span[1]").get());
			final String status = li.xpath("//li/a/i/text()").get();

			data.put("companyName", companyName);
			data.put("status", status);

			for (final Selectable span : li.xpath("//li/div/span").nodes()) {
				final String[] clearData = Util.normalizeRawText(span.toString()).split("：|:");
				data.put(clearData[0], clearData[1].trim());
			}
			array.add(data);
		}
		return array;
	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestZJH().readFile();
		System.out.println(obj);

	}

}
