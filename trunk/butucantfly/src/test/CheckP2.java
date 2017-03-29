package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import common.db.NoSqlUpdater;

public class CheckP2 {

	public static void main(final String[] args) throws Exception {
		get("湖南", "hnc");
	}

	public static void get(final String name1, final String name2) throws Exception {
		final NoSqlUpdater update = new NoSqlUpdater("page_company_index",
				"com_" + name2 + "_index_r201703_pageprocessor");

		final FileInputStream inputStream = new FileInputStream(
				new File("C://Users//Administrator//Desktop//" + name1 + ".csv")); // CSV文件路径
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
		} catch (final Exception e) {
			e.printStackTrace();
		}
		String line = "";
		String everyLine = "";
		try {
			final List<String> allString = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				everyLine = line;
				allString.add(everyLine);
			}

			get2(update, allString);
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	public static void get2(final NoSqlUpdater _updater, final List<String> names) throws SQLException {
		for (final String name : names) {
			final String url = "http://localhost/#" + name;
			final JSONObject data = new JSONObject();
			data.put("status", "");
			data.put("updated", 0);
			data.put("priority", 5);
			_updater.insertUpdate(url, data);
		}
	}

}
