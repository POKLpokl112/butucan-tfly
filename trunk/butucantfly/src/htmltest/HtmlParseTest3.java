package htmltest;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class HtmlParseTest3 extends HtmlParseFromLocalFile {

	@Override
	public Object doSomething(final Html html) {
		final JSONArray array = new JSONArray();

		final int rows = Integer.parseInt(html.xpath("//div[@class='contentA1']/p/span/text()").get());
		System.out.println(rows);

		final List<Selectable> divs = html.xpath("//div[@class='contentA1']/div").nodes();
		for (int i = 0; i < divs.size() - 1; i++) {

			final JSONObject data = new JSONObject();

			final Selectable div = divs.get(i);

			final String companyName = div.xpath("//tr[1]/td/span/text()").get();
			final String status = div.xpath("//tr[1]/td/i/text()").get().trim();

			data.put("companyName", companyName);
			data.put("status", status);

			for (final Selectable th : div.xpath("//th").nodes()) {
				final String[] clearData = Util.normalizeRawText(th.toString()).split("：|:");
				data.put(clearData[0], clearData[1]);
			}
			array.add(data);
		}

		return array;

	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTest3().readFile();
		System.out.println(obj);

	}
}
