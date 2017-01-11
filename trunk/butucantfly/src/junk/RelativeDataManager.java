package junk;

import java.sql.SQLException;

import org.junit.Assert;

import common.Util;
import common.db.NoSqlUpdater;

public class RelativeDataManager {

	private static RelativeDataManager instance = new RelativeDataManager();

	public static RelativeDataManager getInstance() {
		return instance;
	}

	private RelativeDataManager() {
	}

	NoSqlUpdater update = new NoSqlUpdater("pevc", "relativelycompanyname.relativelycompanyname", false);

	public void putData(final String fullName, final String abbrName) throws SQLException {
		putData(fullName, abbrName, false);
	}

	public void putData(final String fullName, final String abbrName, final boolean isCheck) throws SQLException {
		// check
		Assert.assertTrue(Util.notEmpty(fullName) && Util.notEmpty(abbrName));

		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("select id,data,status from " + update.getTable() + " where id =? or data=?");
				ps.setString(1, fullName);
				ps.setString(2, abbrName);
				rs = ps.executeQuery();
				String id = "";
				String data = "";
				String status = "";
				if (rs.next()) {

					id = rs.getString(1);
					data = rs.getString(2);
					status = rs.getString(3);

					if ("OK".equals(status)) {
						if (data.equals(abbrName)) {
							return;
						}

						Util.getLogger(this)
								.info("already done data-" + id + ":" + data + ";" + "new is : " + abbrName);
					} else {
						boolean flag = false;

						for (final String name : data.split("\\|")) {
							if (name.equals(abbrName)) {
								flag = true;
							}
						}

						if (flag) {
							return;
						}

						data = data + "|" + abbrName;

						update(fullName, data);
					}
				} else {
					insert(fullName, abbrName, isCheck);
				}

				if (rs.next()) {
					Util.getLogger(this).warn("more than one row!-----" + fullName + "=" + abbrName);
					return;
				}

				if (Util.isEmpty(id)) {
					insert(fullName, abbrName, isCheck);
				} else {
					String s = fullName + "=" + abbrName;
					if (Util.notEmpty(data)) {
						if (data.contains(s)) {
							return;
						} else {
							s = data + "|" + s;
						}
					}
					update(fullName, s);

				}

			}
		});

	}

	public void putCheckedData(final String fullName, final String abbrName) throws SQLException {
		putData(fullName, abbrName, true);
	}

	protected void update(final String fullName, final String abbrName) throws SQLException {
		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("update " + update.getTable() + " set data=? where id =?");
				ps.setString(1, abbrName);
				ps.setString(2, fullName);
				final int i = ps.executeUpdate();
				if (i != 1) {
					throw new IllegalArgumentException();
				}
			}
		});

	}

	protected void insert(final String fullName, final String abbrName, final boolean isCheck) throws SQLException {
		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("insert into " + update.getTable() + " (id,data,status)  values (?,?,?)");
				ps.setString(1, fullName);
				ps.setString(2, abbrName);
				ps.setString(3, isCheck ? "OK" : "");
				final int i = ps.executeUpdate();
				if (i != 1) {
					throw new IllegalArgumentException();
				}
			}
		});
	}

	public void delete(final String fullName) throws SQLException {
		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("delete from " + update.getTable() + " where id = ?");
				ps.setString(1, fullName);
				final int i = ps.executeUpdate();
				if (i != 1) {
					throw new IllegalArgumentException();
				}
			}
		});
	}

	public static void main(final String[] args) throws SQLException {
		RelativeDataManager.getInstance().putData("苏州太湖旅业发展有限公司", "太湖旅业");
	}

}
