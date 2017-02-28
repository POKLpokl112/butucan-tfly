package htmltest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

//GXN
public class HtmlParseTestBJB extends HtmlParseFromLocalFile {

	@Override
	public Object doSomething(final Html html) {
		final JSONArray array = new JSONArray();

		final int rows = Integer.parseInt(html.xpath("//p[@class='total']/span/text()").get());
		System.out.println(rows);

		final String s = "http://gx.gsxt.gov.cn/gjjbj/gjjQueryCreditAction!openEntInfo.dhtml?";
		for (final Selectable li : html.xpath("//div[@class='search-result']/ul/li").nodes()) {
			final JSONObject data = new JSONObject();

			final String onclick = li.xpath("//li/h3/@onclick").get();
			final String entId = Util.parseRegex(onclick, "entId=([^&]+)");
			final String credit_ticket = Util.parseRegex(onclick, "credit_ticket=([^&|^\"]+)");
			final String URL = s + "entId=" + entId + "&credit_ticket=" + credit_ticket;

			data.put("URL", URL);

			final String companyName = Util.normalizeRawText(li.xpath("//li/h3/span[1]").toString());
			final String status = li.xpath("//li/h3/span[2]/text()").get();

			data.put("companyName", companyName);
			data.put("status", status);

			for (final Selectable td : li.xpath("//td").nodes()) {
				final String[] clearData = Util.normalizeRawText(td.toString()).split("：|:");
				data.put(clearData[0], clearData[1].trim());
			}
			array.add(data);
		}
		return array;

	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestBJB().readFile();
		System.out.println(obj);

	}
}
