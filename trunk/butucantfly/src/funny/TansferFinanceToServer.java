package funny;

import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import com.alibaba.fastjson.JSONObject;

import common.Util;
import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;

public class TansferFinanceToServer {

	public static void main(final String[] args) throws SQLException {
		a();
		b();
		c();
	}

	public static void e(final String table) throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater("compset_data", "todo.todo_" + table);
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "financial." + table);

		from.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				// remove un-used value
				entry.value.remove("_excludedValues");
				entry.value.remove("_nullValues");

				to.execute(to.new SqlExecutor() {

					@Override
					protected void doExecute() throws SQLException {
						ps = prepareStatement("insert into " + to.getTable()
								+ " (companycode,year,period, data) values (?,?,?,?::jsonb)");
						final String[] keys = entry.key.split("-");
						// ps.setObject(1, entry.key);
						ps.setObject(1, keys[0]);
						ps.setObject(2, keys[1]);
						ps.setObject(3, keys[2]);
						ps.setObject(4, entry.value.toJSONString());
						try {

							final int count = ps.executeUpdate();
							if (count == 0) {
								throw new SQLException("insert error!!!");
							}
						} catch (final PSQLException e) {
							Util.getLogger(TansferToServer.class).warn(e);
						}

					}
				});
				from.delete(entry.key);
			}
		});
	}

	public static void a() throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater("compset_data", "todo.todo_incomestatement");
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "financial.incomestatement");

		from.executeForEach("", new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				// remove un-used value
				entry.value.remove("_excludedValues");
				entry.value.remove("_nullValues");

				to.execute(to.new SqlExecutor() {

					@Override
					protected void doExecute() throws SQLException {
						ps = prepareStatement("insert into " + to.getTable()
								+ " (companycode,year,period, data) values (?,?,?,?::jsonb)");
						final String[] keys = entry.key.split("-");
						// ps.setObject(1, entry.key);
						ps.setObject(1, keys[0]);
						ps.setObject(2, keys[1]);
						ps.setObject(3, keys[2]);
						ps.setObject(4, entry.value.toJSONString());
						try {

							final int count = ps.executeUpdate();
							if (count == 0) {
								throw new SQLException("insert error!!!");
							}
						} catch (final PSQLException e) {
							Util.getLogger(TansferToServer.class).warn(e);
						}

					}
				});
				from.delete(entry.key);
			}
		});
	}

	public static void b() throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater("compset_data", "todo.todo_balancesheet");
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "financial.balancesheet");

		from.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				// remove un-used value
				entry.value.remove("_excludedValues");
				entry.value.remove("_nullValues");

				to.execute(to.new SqlExecutor() {

					@Override
					protected void doExecute() throws SQLException {
						ps = prepareStatement("insert into " + to.getTable()
								+ " (companycode,year,period, data) values (?,?,?,?::jsonb)");
						final String[] keys = entry.key.split("-");
						// ps.setObject(1, entry.key);
						ps.setObject(1, keys[0]);
						ps.setObject(2, keys[1]);
						ps.setObject(3, keys[2]);
						ps.setObject(4, entry.value.toJSONString());
						try {

							final int count = ps.executeUpdate();
							if (count == 0) {
								throw new SQLException("insert error!!!");
							}
						} catch (final PSQLException e) {
							Util.getLogger(TansferToServer.class).warn(e);
						}

					}
				});
				from.delete(entry.key);
			}
		});
	}

	public static void c() throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater("compset_data", "todo.todo_cashflowstatement");
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "financial.cashflowstatement");

		from.executeForEach("id < 'LC_CN_000090-2007-Q2'", new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				// remove un-used value
				entry.value.remove("_excludedValues");
				entry.value.remove("_nullValues");

				to.execute(to.new SqlExecutor() {

					@Override
					protected void doExecute() throws SQLException {
						ps = prepareStatement("insert into " + to.getTable()
								+ " (companycode,year,period, data) values (?,?,?,?::jsonb)");
						final String[] keys = entry.key.split("-");
						// ps.setObject(1, entry.key);
						ps.setObject(1, keys[0]);
						ps.setObject(2, keys[1]);
						ps.setObject(3, keys[2]);
						ps.setObject(4, entry.value.toJSONString());
						try {

							final int count = ps.executeUpdate();
							if (count == 0) {
								throw new SQLException("insert error!!!");
							}
						} catch (final PSQLException e) {
							Util.getLogger(TansferToServer.class).warn(e);
						}

					}
				});
				from.delete(entry.key);
			}
		});
	}

}
