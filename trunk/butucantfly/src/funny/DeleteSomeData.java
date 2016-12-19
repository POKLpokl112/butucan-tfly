package funny;

import java.sql.SQLException;

import common.db.NoSqlUpdater;
import constant._Config;

public class DeleteSomeData {

	public static void main(final String[] args) throws SQLException {

		a(

				// "delete from todo.todo_companyleader where id like 'INV_%'",

				"delete from company.company where id like 'INV_%'",

				"delete from todo.todo_company where id like 'INV_%'"

		// "delete from company.companyleader where id like 'INV_%'"

		// "delete from constant.companycode where id like 'INV_%'"

		);

		b(

				// "delete from company.company where companycode like 'INV_%'",

				"delete from company.company where companycode like 'INV_%'"

		// "delete from company.companyleader where companycode like 'INV_%'"

		// "delete from constant.companycode where companycode like 'INV_%'"

		);
	}

	public static void a(final String... sqls) throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data", "{0}");
		for (final String sql : sqls) {

			from.execute(sql);
		}

	}

	public static void b(final String... sqls) throws SQLException {

		final NoSqlUpdater from = new NoSqlUpdater(_Config.aliDbConfigs, "compset_platform", "{0}");
		for (final String sql : sqls) {

			from.execute(sql);
		}

	}

}
