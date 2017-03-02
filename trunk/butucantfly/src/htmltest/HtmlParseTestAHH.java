package htmltest;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class HtmlParseTestAHH extends HtmlParseFromLocalFile {

	// HBW\hlj\hnh\hnz
	@Override
	public Object doSomething(final Html html) {
		final JSONArray array = new JSONArray();

		final int rows = Integer
				.parseInt(html.xpath("//*[@id='searchtipsu1']/p/span[2]").get().replaceAll("<[^>]+>", ""));
		System.out.println(rows);

		// 所有记录数
		final List<Selectable> divs = html.xpath("//*[@id='contentitemHidden']/div").nodes();
		for (final Selectable div : divs) {

			final JSONObject data = new JSONObject();

			final String URL = "http://hb.gsxt.gov.cn/business/JCXX.jspx?id=" + div.xpath("//div/@data-label").get();
			final String id = div.xpath("//div/@data-label-id").get();
			data.put("URL", URL);
			data.put("id", id);

			final Selectable p1 = div.xpath("//div/p[1]");
			final String p1String = p1.toString();
			final String companyName = Util
					.normalizeRawText(Util.parseRegex(p1String, "([\\s\\S]+)<span class=\"qiyezhuangtai fillet\""));
			data.put("companyName", companyName);

			final String companyStatus = html.xpath("//span[@class='qiyezhuangtai fillet']/text()").get().trim();
			data.put("companyStatus", companyStatus);

			final List<Selectable> spans = div.xpath("//div/p[2]/span").nodes();
			for (final Selectable span : spans) {
				final String[] clearData = Util.normalizeRawText(span.toString()).split("：|:");
				data.put(clearData[0], clearData[1].trim());
			}

			array.add(data);

		}
		return array;

	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestAHH().readFile();
		System.out.println(obj);

	}
}
