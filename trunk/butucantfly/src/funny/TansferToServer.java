package funny;

import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import com.alibaba.fastjson.JSONObject;

import common.Util;
import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;

public class TansferToServer {

	public static void main(final String[] args) throws SQLException {
		b();
	}

	public static void b() throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data", "todo.todo_company");
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "company.company");

		from.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				// remove un-used value
				entry.value.remove("_excludedValues");
				entry.value.remove("_nullValues");

				to.execute(to.new SqlExecutor() {

					@Override
					protected void doExecute() throws SQLException {
						ps = prepareStatement(
								"insert into " + to.getTable() + " (companycode, data) values (?, ?::jsonb)");
						ps.setObject(1, entry.key);
						ps.setObject(2, entry.value.toJSONString());
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

	public static void d() throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data", "todo.todo_incomestatement");
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "financial.incomestatement", true);

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
						final int count = ps.executeUpdate();
						if (count == 0) {
							throw new SQLException("insert error!!!");
						}

					}
				});
				from.delete(entry.key);
			}
		});
	}

	public static void c() throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data",
				"todo.todo_companyshareholder");
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "company.companyshareholder");

		from.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				// remove un-used value
				entry.value.remove("_excludedValues");
				entry.value.remove("_nullValues");
				final int index = entry.key.indexOf("-");
				final String companycode = entry.key.substring(0, index);
				final String shareholdername = entry.key.substring(index + 1);

				to.execute(to.new SqlExecutor() {

					@Override
					protected void doExecute() throws SQLException {
						ps = prepareStatement("insert into " + to.getTable()
								+ " (companycode,shareholdername, data) values (?,?, ?::jsonb)");
						ps.setObject(1, companycode);
						ps.setObject(2, shareholdername);
						ps.setObject(3, entry.value.toJSONString());
						final int count = ps.executeUpdate();
						if (count == 0) {
							throw new SQLException("insert error!!!");
						}

					}
				});

			}
		});

	}

	public static void f() throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data", "todo.todo_companyleader");
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "company.companyleader");

		from.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				// remove un-used value
				entry.value.remove("_excludedValues");
				entry.value.remove("_nullValues");
				final int index = entry.key.indexOf("-");
				final String companycode = entry.key.substring(0, index);
				final String leadername = entry.key.substring(index + 1);

				to.execute(to.new SqlExecutor() {

					@Override
					protected void doExecute() throws SQLException {
						ps = prepareStatement("insert into " + to.getTable()
								+ " (companycode,leadername, data) values (?,?, ?::jsonb)");
						ps.setObject(1, companycode);
						ps.setObject(2, leadername);
						ps.setObject(3, entry.value.toJSONString());
						final int count = ps.executeUpdate();
						if (count == 0) {
							throw new SQLException("insert error!!!");
						}

					}
				});

			}
		});

	}

	public static void a() throws SQLException {
		final NoSqlUpdater from = new NoSqlUpdater("compset_data", "todo.todo_company");
		final NoSqlUpdater to = new NoSqlUpdater(_Config.aliDbConfigs, "compset", "company.company");
		from.executeForEach("id < 'LC_CN_000422'", new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				// remove un-used value
				entry.value.remove("_excludedValues");
				entry.value.remove("_nullValues");

				to.execute(to.new SqlExecutor() {

					@Override
					protected void doExecute() throws SQLException {
						ps = prepareStatement(
								"insert into " + to.getTable() + " (companycode, data) values (?, ?::jsonb)");
						ps.setObject(1, entry.key);
						ps.setObject(2, entry.value.toJSONString());
						final int count = ps.executeUpdate();
						if (count == 0) {
							throw new SQLException("insert error!!!");
						}

					}
				});

			}
		});
	}

}
