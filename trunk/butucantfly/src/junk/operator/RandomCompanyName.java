// package junk.operator;
//
// import java.io.File;
// import java.sql.SQLException;
//
// import common.Util;
// import common.db.SqlUpdater;
//
// public class RandomCompanyName {
//
// public static void main(final String[] args) throws SQLException {
// final SqlUpdater update = new SqlUpdater(JYUtils.xhpSqlServerDbConfigs,
// "jydb", "dbo.secumain");
// final StringBuffer sb = new StringBuffer();
// for (int i = 2015; i < 2017; i++) {
// for (int j = 1; j < 13; j++) {
// final String fromData = i + (j < 10 ? "0" + j : j + "") + "01";
// final int k = j + 1;
// final String toDate = j == 12 ? (i + 1) + "0101" : i + (k < 10 ? "0" + k : k
// + "") + "01";
// update.execute(update.new SqlExecutor() {
//
// @Override
// protected void doExecute() throws SQLException {
// ps = prepareStatement("select chiname,secucode,listeddate from " +
// update.getTable()
// + " where secucode like ? and listeddate>'" + fromData + "' and listeddate<'"
// + toDate
// + "'");
// ps.setString(1, "8%");
// rs = ps.executeQuery();
// int twoToEnd = 0;
// while (rs.next()) {
// if (twoToEnd == 2) {
// return;
// }
// twoToEnd++;
// final String name = rs.getString(1);
// final String code = rs.getString(2);
// final String date = rs.getString(3);
// sb.append(name + "\t" + code + "\t" + date + "\t");
// sb.append("\r\n\r\n");
// }
//
// }
// });
// }
// }
//
// sb.append("----------------------------------------------------------");
// sb.append("\r\n\r\n");
// for (int i = 0; i < 2; i++) {
// for (int j = 0; j < 2; j++) {
// final String fromData = i == 0 ? "201612" + (18 + j * 6) : "2017010" + (1 + j
// * 6);
// final String toDate = i == 0 ? "201612" + (18 + (j + 1) * 6)
// : "201701" + ((1 + (j + 1) * 6) < 10 ? "0" + (1 + (j + 1) * 6) : (1 + (j + 1)
// * 6));
// update.execute(update.new SqlExecutor() {
//
// @Override
// protected void doExecute() throws SQLException {
// ps = prepareStatement("select chiname,secucode,listeddate from " +
// update.getTable()
// + " where secucode like ? and listeddate>'" + fromData + "' and listeddate<'"
// + toDate
// + "'");
// ps.setString(1, "8%");
// rs = ps.executeQuery();
// int twoToEnd = 0;
// while (rs.next()) {
// if (twoToEnd == 5) {
// return;
// }
// twoToEnd++;
// final String name = rs.getString(1);
// final String code = rs.getString(2);
// final String date = rs.getString(3);
// sb.append(name + "\t" + code + "\t" + date + "\t");
// sb.append("\r\n\r\n");
// }
//
// }
// });
// }
// }
//
// Util.saveAsFile(new File("d:\\randomcom.txt"), sb.toString());
//
// }
//
// }
