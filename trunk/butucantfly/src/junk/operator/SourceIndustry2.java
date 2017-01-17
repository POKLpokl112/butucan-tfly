package junk.operator;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;

import common.Util;
import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;

public class SourceIndustry2 {

	public static void getData() throws SQLException, UnsupportedEncodingException {
		final NoSqlUpdater update = new NoSqlUpdater("in_pedaily_r201612_enterprise");
		final StringBuffer sb = new StringBuffer();
		update.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final JSONObject data = entry.value;
				String fullName = data.getString("中文全称");
				if (fullName == null) {
					fullName = "";
				}
				sb.append(fullName + ",");
				String inds = data.getString("所属行业");
				inds = inds != null ? inds.replace(",", "，") : "";
				sb.append(inds + ",");
				String des = data.getString("简介");
				des = des != null ? des.replace(",", "，") : "";
				sb.append(des + "\r\n");

			}
		});
		Util.saveAsFile(new File("d:\\pedailyinds.csv"), sb.toString().getBytes("gbk"));

		final NoSqlUpdater update2 = new NoSqlUpdater("in_cyzone_r201701_company");
		final StringBuffer sb2 = new StringBuffer();
		update2.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final JSONObject data = entry.value;
				String fullName = data.getString("中文全称");
				if (fullName == null) {
					fullName = "";
				}
				sb2.append(fullName + ",");
				String inds = data.getString("业务范围");
				inds = inds != null ? inds.replace(",", "，") : "";
				sb2.append(inds + ",");
				String des = data.getString("简介");
				des = des != null ? des.replace(",", "，") : "";
				sb2.append(des + "\r\n");

			}
		});
		Util.saveAsFile(new File("d:\\cyzoneinds.csv"), sb2.toString().getBytes("gbk"));

		final NoSqlUpdater update3 = new NoSqlUpdater("in_chinaventure_r201701_company");
		final StringBuffer sb3 = new StringBuffer();
		update3.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final JSONObject data = entry.value;
				String fullName = data.getString("中文全称");
				if (fullName == null) {
					fullName = "";
				}
				sb3.append(fullName + ",");
				String inds = data.getString("所属行业");
				inds = inds != null ? inds.replace(",", "，") : "";
				sb3.append(inds + ",");
				String des = data.getString("简介");
				des = des != null ? des.replace(",", "，") : "";
				sb3.append(des + "\r\n");

			}
		});
		Util.saveAsFile(new File("d:\\chinaventureinds.csv"), sb3.toString().getBytes("gbk"));
	}

	public static void main(final String[] args) throws SQLException, UnsupportedEncodingException {
		getData();
	}

}
