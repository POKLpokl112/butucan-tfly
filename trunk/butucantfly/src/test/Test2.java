package test;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import common.config.GlobalConfig;
import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;

public class Test2 {

	public static final Map<String, String> aliDbConfigs = GlobalConfig.db.dbConfigs();

	// 主要用于方便本地测试用！
	static {
		aliDbConfigs.put("url", "jdbc:postgresql://rdsx4rdj2288028n03k0shzd.pg.rds.aliyuncs.com:3469/{0}");
		aliDbConfigs.put("user", "shzd999");
		aliDbConfigs.put("password", "faldjkeino@#$132kdfa");
	}

	public static void some() throws SQLException {

		final NoSqlUpdater update = new NoSqlUpdater(aliDbConfigs, "compset_data", "constant.region");
		final NoSqlUpdater update1 = new NoSqlUpdater("company_extra", "location", true);

		update.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final String id = entry.key;
				final JSONObject data = entry.value;
				data.remove("NameEN");
				data.remove("HierarchyId");

				update1.insert(id, data);

			}
		});

	}

	public static void some1() throws SQLException {

		final NoSqlUpdater update1 = new NoSqlUpdater("company_extra", "location");
		update1.execute(update1.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement(
						"select id ,data->>'NameCN' from location where data->>'NameCN' in (select data->>'NameCN'  from location GROUP by data->>'NameCN' having count(data->>'NameCN')>1)  order by data->>'NameCN'");
				rs = ps.executeQuery();
				// String id1 = "";
				String id2 = "";
				int i = 0;
				while (rs.next()) {
					if (i == 0) {
						// id1 = rs.getString(1);
						i++;
					} else if (i == 1) {
						id2 = rs.getString(1);

						// if (id1.substring(0, 2).equals(id2.substring(0, 2)))
						// {
						update1.delete(id2);
						// } else {
						// update1.delete(id1);
						// update1.delete(id2);
						// }

						i = 0;
						// id1 = "";
						id2 = "";
					}

				}
			}
		});
	}

	public static void main(final String[] args) throws SQLException, ClassNotFoundException {

		// final String location =
		// LocationDetector.getInstance().getLocation("广州市灏晴财务咨询有限公司");
		//
		// final Class<? extends AbstractIndexPageProcessor> c = (Class<?
		// extends AbstractIndexPageProcessor>) Class
		// .forName(location + "_DETAIL." + location + "_DETAIL_PageProcessor");
		// System.out.println(c);
	}

}
