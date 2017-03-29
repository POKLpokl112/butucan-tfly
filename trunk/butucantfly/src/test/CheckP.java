package test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.Util;

public class CheckP {

	public static void main(final String[] args) {
		final String text = Util.loadFromFile(new File("C://Users//Administrator//Desktop//companys.txt"));
		final JSONArray array = JSONArray.parseArray(text);
		System.out.println(array.size());
		// final Set<String> s = new HashSet<>();
		// int i = 0;
		final java.util.Map<String, String> map = new HashMap<>();
		for (final Object object : array) {
			final JSONObject obj = (JSONObject) object;
			final String asd = obj.values().toArray(new String[1])[0];
			// if (asd.equals("总局")) {
			// i++;
			// }
			final String name = obj.keySet().toArray(new String[1])[0];
			asd(map, asd, name);
			// s.add(asd);
		}
		// System.out.println(i);
		// System.out.println(s.size());
		// System.out.println(s);
		System.out.println(map);
		for (final Entry<String, String> entry : map.entrySet()) {
			createFile(entry.getKey(), entry.getValue());
		}

	}

	private static void asd(final Map<String, String> map, final String asd, final String name) {
		if (map.containsKey(asd)) {
			map.put(asd, map.get(asd) + name + "\r\n");
		} else {
			map.put(asd, name + "\r\n");
		}
	}

	public static void createFile(final String name1, final String content) {
		Util.saveAsFile(new File("C://Users//Administrator//Desktop//" + name1 + ".csv"), content, "gbk");
	}

}
