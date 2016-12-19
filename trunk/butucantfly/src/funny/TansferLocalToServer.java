package funny;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;

import business.db.idm.DataMetadataSync;
import common.Util;
import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;
import constant._Config;

public class TansferLocalToServer {

	public static void main(final String[] args) throws SQLException {
		f("compset_data", "todo.todo_companyleader");
	}

	public static void f(final String schema, final String table) throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater(schema, table);
		final DataMetadataSync to = new DataMetadataSync(_Config.aliDbConfigs, schema, table, false);

		from.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				try {
					to.syncData(entry.key, entry.value);
				} catch (final Exception e) {
					Util.getLogger(this).warn(e);
				}
			}
		});
	}

	public static void a(final String schema, final String table) throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater(schema, table);
		final DataMetadataSync to = new DataMetadataSync(_Config.aliDbConfigs, schema, table, false);

		from.executeForEach("", new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {

				try {
					to.syncData(entry.key, entry.value);
				} catch (final Exception e) {
					Util.getLogger(this).warn(e);
				}
			}
		});
	}

}
