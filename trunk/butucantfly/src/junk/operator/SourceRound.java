package junk.operator;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import common.Util;
import common.db.NoSqlUpdater;
import constant._Config;

public class SourceRound {

	public static void getData() throws SQLException, UnsupportedEncodingException {
		final NoSqlUpdater update = new NoSqlUpdater(_Config.aliDbConfigs, "pevc", "{0}");
		final StringBuffer sb = new StringBuffer();
		sb.append("chinaventure");
		sb.append("\t");
		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("select DISTINCT(data->>'TargetRound') from chinaventure.transaction");
				rs = ps.executeQuery();
				while (rs.next()) {
					sb.append(rs.getString(1) + ",");
				}
			}
		});
		sb.append("\r\n");

		sb.append("cyzone");
		sb.append("\t");
		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("select DISTINCT(data->>'TargetRound') from cyzone.transaction;");
				rs = ps.executeQuery();
				while (rs.next()) {
					sb.append(rs.getString(1) + ",");
				}
			}
		});
		sb.append("\r\n");

		sb.append("pedaily");
		sb.append("\t");
		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("select DISTINCT(data->>'TargetRound') from pedaily.transaction;");
				rs = ps.executeQuery();
				while (rs.next()) {
					sb.append(rs.getString(1) + ",");
				}
			}
		});
		sb.append("\r\n");

		Util.saveAsFile(new File("d:\\differenceround.text"), sb.toString());
	}

	public static void main(final String[] args) throws SQLException, UnsupportedEncodingException {
		getData();
	}

}
