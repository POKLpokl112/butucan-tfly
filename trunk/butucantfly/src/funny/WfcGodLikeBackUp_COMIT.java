package funny;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import common.Util;
import common.config.GlobalConfig;
import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;

public class WfcGodLikeBackUp_COMIT {

	public static final Map<String, String> aliDbConfigs = GlobalConfig.db.dbConfigs();

	// 主要用于方便本地测试用！
	static {
		aliDbConfigs.put("url", "jdbc:postgresql://rdsx4rdj2288028n03k0shzd.pg.rds.aliyuncs.com:3469/{0}");
		aliDbConfigs.put("user", "shzd999");
		aliDbConfigs.put("password", "faldjkeino@#$132kdfa");
	}

	public static final Map<String, String> wfcDbConfigs = GlobalConfig.db.dbConfigs();

	// 主要用于方便本地测试用！
	static {
		wfcDbConfigs.put("url", "jdbc:postgresql://121.199.48.249:5432/{0}");
		wfcDbConfigs.put("user", "wfcdatauser");
		wfcDbConfigs.put("password", "qidiao");
	}

	static List<String> list = new ArrayList<>();
	static {
		list.add("in_itjuzi_r20161011_company");
		list.add("in_itjuzi_r20161012_company");
		list.add("in_itjuzi_r20161013_company");
		list.add("in_itjuzi_r20161015_company");
		list.add("in_itjuzi_r20161016_company");
		list.add("in_itjuzi_r20161017_company");
		list.add("in_itjuzi_r20161018_company");
		list.add("in_itjuzi_r20161019_company");
		list.add("in_itjuzi_r20161020_company");
		list.add("in_itjuzi_r20161021_company");
		list.add("in_itjuzi_r20161031_company");
	}

	public static void godLikeBackUp(final NoSqlUpdater wfcUpdater) throws SQLException {
		wfcUpdater.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final List<String> target = new ArrayList<>(list);
				final JSONObject data = new JSONObject(entry.value);
				data.remove("城市");
				data.put("公司网址", data.getString("公司网址").trim());
				search(wfcUpdater, entry.key, data, target);

			}
		});
	}

	public static void search(final NoSqlUpdater wfcUpdater, final String id, final JSONObject data,
			final List<String> target) throws SQLException {
		final NoSqlUpdater aliUpdater = new NoSqlUpdater(aliDbConfigs, "customer", target.get(0));
		aliUpdater.setMaxThread4Execute(1);

		aliUpdater.execute(aliUpdater.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement(
						"select data,metadata from " + aliUpdater.getTable() + " where data ->> '公司全称' = ?");
				ps.setString(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					final JSONObject oldData = JSONObject.parseObject(rs.getString(1));
					final JSONObject cData = new JSONObject();
					cData.put("公司名称", oldData.get("公司全称"));
					cData.put("公司网址", oldData.get("网站"));
					cData.put("获投信息", oldData.get("获投信息"));
					cData.put("联系信息", data.get("联系信息"));
					cData.put("团队信息", oldData.get("团队信息"));
					cData.put("产品信息", oldData.get("产品信息"));

					if (cData.equals(data)) {
						final long modified = Long
								.parseLong(JSONObject.parseObject(rs.getString(2)).getString("modified"));
						final String date = Util.formatDate(new Date(modified), "yyyy-MM-dd HH:mm:ss");
						wfcUpdater.execute(wfcUpdater.new SqlExecutor() {

							@Override
							protected void doExecute() throws SQLException {
								ps = prepareStatement("update " + wfcUpdater.getTable() + " set modified = '" + date
										+ "' where id = ?");
								ps.setString(1, id);
								ps.execute();
							}
						});

					} else {
						target.remove(0);
						if (target.isEmpty() == false) {
							search(wfcUpdater, id, data, target);
						}
					}
				} else {
					target.remove(0);
					if (target.isEmpty() == false) {
						search(wfcUpdater, id, data, target);
					}
				}

			}

		});
	}

	public static void main(final String[] args) throws SQLException {
		final NoSqlUpdater wfcUpdater = new NoSqlUpdater(wfcDbConfigs, "customer", "_wfc_company_itjuzi");
		wfcUpdater.setMaxThread4Execute(1);

		godLikeBackUp(wfcUpdater);

	}

}
