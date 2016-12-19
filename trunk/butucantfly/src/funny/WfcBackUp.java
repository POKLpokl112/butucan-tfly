package funny;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import common.config.GlobalConfig;
import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;

public class WfcBackUp {

	public static final Map<String, String> wfcDbConfigs = GlobalConfig.db.dbConfigs();

	// 主要用于方便本地测试用！
	static {
		wfcDbConfigs.put("url", "jdbc:postgresql://121.199.48.249:5432/{0}");
		wfcDbConfigs.put("user", "wfcdatauser");
		wfcDbConfigs.put("password", "qidiao");
	}

	public static void main(final String[] args) throws SQLException {
		backUpTime("_wfc_company_itjuzi");
		backUpTime("_wfc_company_jobtong");
		backUpTime("_wfc_company_lagou");
		backUpTime("_wfc_company_neitui");
		backUpTime("_wfc_investfirm_amac");
		backUpTime("_wfc_investfirm_itjuzi");
		backUpTime("_wfc_investor_36kr");
		backUpTime("_wfc_investor_fellowplus");
		backUpTime("_wfc_investor_itjuzi");
		backUpTime("_wfc_investor_lieyunwang");
		// backUpTime("_wfc_investor_newchama");

	}

	public static void backUpTime(final String table) throws SQLException {
		final NoSqlUpdater serverUpdater = new NoSqlUpdater(wfcDbConfigs, "customer", table);
		final NoSqlUpdater localUpdater = new NoSqlUpdater("customer", table);
		localUpdater.setMaxThread4Execute(1);
		serverUpdater.setMaxThread4Execute(1);

		localUpdater.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final String key = entry.key;
				final String modified = localUpdater.<String> execute(localUpdater.new QuerySqlExecutor<String>() {

					@Override
					protected String doExecute() throws SQLException {
						ps = prepareStatement("select modified from " + localUpdater.getTable() + " where id = ?");
						ps.setString(1, key);
						rs = ps.executeQuery();
						while (rs.next()) {
							return rs.getString(1);
							// Util.formatDate(new
							// Date(rs.getDate(1).getTime()), "yyyy-MM-dd
							// HH:mm:ss");
						}
						return null;
					}
				});

				// boolean b =serverUpdater.<Boolean> execute(localUpdater.new
				// QuerySqlExecutor<Boolean>() {
				//
				// @Override
				// protected Boolean doExecute() throws SQLException {
				// ps = prepareStatement("select id from " +
				// localUpdater.getTable() + "where id = ?");
				// ps.setString(1, key);
				// rs = ps.executeQuery();
				// return rs.next();
				//
				// }
				// });
				if (modified != null) {
					serverUpdater.<String> execute(serverUpdater.new QuerySqlExecutor<String>() {

						@Override
						protected String doExecute() throws SQLException {
							ps = prepareStatement("update " + serverUpdater.getTable() + " set modified = '" + modified
									+ "' where id = ?");
							ps.setString(1, key);
							ps.execute();
							return null;
						}
					});
				}
			}
		});

	}

}
