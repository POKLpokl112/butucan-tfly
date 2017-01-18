package junk.operator;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;

import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;
import constant._Config;

public class ClearSomething {
	public static void clearPEDAILY() throws SQLException {
		final NoSqlUpdater update = new NoSqlUpdater(_Config.aliDbConfigs, "pevc", "pedaily.transaction");
		update.executeForEach("id like '%-Pre-PreA'", new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final String oldId = entry.key;
				final JSONObject data = entry.value;
				final String id = oldId.replace("-Pre-PreA", "-PreA");
				update.insert(id, data);
				update.delete(oldId);
			}
		});
	}

	public static void main(final String[] args) {

	}

}
