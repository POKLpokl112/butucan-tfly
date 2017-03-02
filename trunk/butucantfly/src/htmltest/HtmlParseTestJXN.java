package htmltest;

import java.util.Base64;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;

public class HtmlParseTestJXN extends HtmlParseFromLocalFile {

	@Override
	public Object doSomething(final String pageContent) {

		final String content = Util.parseRegex(pageContent, "var obj =([\\s\\S]+)};") + "}";

		final JSONObject object = JSONObject.parseObject(content);

		final int total = object.getIntValue("total");
		System.out.println(total);

		final JSONArray array = new JSONArray();
		for (final Object obj : object.getJSONArray("data")) {
			final JSONObject data = new JSONObject();

			final JSONObject json = (JSONObject) obj;

			final String PRIPID = json.getString("PRIPID");
			final String URL = "http://jx.gsxt.gov.cn/baseinfo/queryenterpriseinfoByRegnore.do?pripid="
					+ Base64.getEncoder().encodeToString(PRIPID.getBytes());
			data.put("URL", URL);

			final String companyName = json.getString("ENTNAME");
			final String statu = json.getString("REGSTATE_CN");
			data.put("companyName", companyName);
			data.put("statu", statu);

			final String namelike = json.getString("namelike").replace("：", "");// 代表人
			final String NAME = json.getString("NAME");// 代表人

			data.put(namelike, NAME);

			final String ESTDATE = json.getString("ESTDATE");// 成立日期
			final String UNISCID = json.getString("UNISCID");// 统一社会信用代码/注册号

			data.put("ESTDATE", ESTDATE);
			data.put("UNISCID", UNISCID);

			array.add(data);
		}

		return array;
	}

	public static void main(final String[] args) {
		final Object obj = new HtmlParseTestJXN().readFile2();
		System.out.println(obj);

	}

}
