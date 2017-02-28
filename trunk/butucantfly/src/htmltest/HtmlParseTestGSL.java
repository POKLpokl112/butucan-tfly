package htmltest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class HtmlParseTestGSL extends HtmlParseFromLocalFile {

	@Override
	public Object doSomething(final Html html) {
		final JSONArray array = new JSONArray();

		final int rows = Integer.parseInt(html.xpath("//*[@id='u5_img']/span[2]/text()").get());
		System.out.println(rows);

		for (final Selectable div : html.xpath("//*[@id='searchList']/div").nodes()) {
			final JSONObject data = new JSONObject();

			final Selectable sonDiv = div.xpath("//div/div[1]/div[1]");

			final String onclick = sonDiv.xpath("//div/@onclick").get();
			final String pripid = Util.parseRegex(onclick, "this,'([^']+)");
			final String entcate = Util.parseRegex(onclick, "','([^']+)");
			final String URL = "http://gs.gsxt.gov.cn/gsxygs/pubSearch/basicView?pripid=" + pripid + "&entcate="
					+ entcate;
			data.put("URL", URL);

			final String companyName = Util.normalizeRawText(sonDiv.toString());
			final String status = div.xpath("//div/div[1]/span[1]/text()").get();
			data.put("companyName", companyName);
			data.put("status", status);

			for (final Selectable nextDiv : div.xpath("//div/div[2]/div").nodes()) {
				final String[] clearData = Util.normalizeRawText(nextDiv.toString()).split("：|:");
				data.put(clearData[0], clearData[1]);
			}
			if (data.containsKey("注册号")) {
				data.put("统一社会信用代码/注册号", data.get("注册号"));
			} else if (data.containsKey("统一社会信用代码")) {
				data.put("统一社会信用代码/注册号", data.get("统一社会信用代码"));
			} else {
				throw new IllegalArgumentException("无 -- 统一社会信用代码/注册号");
			}

			array.add(data);
		}

		return array;

	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestGSL().readFile();
		System.out.println(obj);

	}
}
