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

public class WfcGodLikeBackUp_fellow {

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
		list.add("in_fellowplus_r20161012_investor");
		list.add("in_fellowplus_r20161013_investor");
		list.add("in_fellowplus_r20161015_investor");
		list.add("in_fellowplus_r20161016_investor");
		list.add("in_fellowplus_r20161017_investor");
		list.add("in_fellowplus_r20161018_investor");
		list.add("in_fellowplus_r20161019_investor");
		list.add("in_fellowplus_r20161020_investor");
		list.add("in_fellowplus_r20161021_investor");
		list.add("in_fellowplus_r20161025_investor");
		list.add("in_fellowplus_r20161028_investor");
		list.add("in_fellowplus_r20161031_investor");
	}

	public static void godLikeBackUp(final NoSqlUpdater wfcUpdater) throws SQLException {
		wfcUpdater.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final List<String> target = new ArrayList<>(list);
				final JSONObject data = new JSONObject(entry.value);
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
				ps = prepareStatement("select data,metadata from " + aliUpdater.getTable() + " where id = ?");
				ps.setString(1, "IN_FELLOWPLUS[investor]-" + id);
				rs = ps.executeQuery();
				if (rs.next()) {
					final JSONObject oldData = JSONObject.parseObject(rs.getString(1));
					final JSONObject cData = new JSONObject();
					cData.put("投资机构名称", oldData.getString("投资机构名称"));
					cData.put("投资人姓名", oldData.getString("投资人姓名"));
					cData.put("职务", oldData.getString("职务"));
					cData.put("关注领域", oldData.getString("关注领域"));
					cData.put("投资偏好/轮次", oldData.getString("偏好投资阶段"));
					cData.put("常驻城市", oldData.getString("常驻城市"));
					cData.put("单个项目投资额度", oldData.getString("单个项目投资额度"));
					cData.put("投资经历", oldData.getString("投资经历"));

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
		final NoSqlUpdater wfcUpdater = new NoSqlUpdater(wfcDbConfigs, "customer", "_wfc_investor_fellowplus");
		wfcUpdater.setMaxThread4Execute(1);

		godLikeBackUp(wfcUpdater);

	}

}
