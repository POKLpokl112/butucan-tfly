package funny;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;

import common.db.KVSqlUpdater;
import common.db.KVSqlUpdater.Entry;
import common.db.KVSqlUpdater.EntryRunnable;
import common.db.NoSqlUpdater;
import common.db.SqlUpdater;

public class MakeDataUpdate {

	public void identicalData1() throws SQLException {
		final NoSqlUpdater update = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data", "todo.todo_incomestatement");
		update.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final JSONObject data = entry.value;
				if (data.getString("CompanyCode").startsWith("LC_CN") && data.containsKey("FinancialType")
						&& (data.getString("FinancialType").equals("非金融公司") == false)) {
					data.put("TotalOperatingRevenue", data.get("OperatingRevenue"));
					data.remove("OperatingRevenue");
					data.put("TotalOperatingCost", data.get("OperatingPayout"));
					data.remove("OperatingPayout");
					data.put("SpecialItemsTOC_AdjustmentItemsTOC", data.get("SpecialItemsOP_AdjustmentItemsOP"));
					data.remove("SpecialItemsOP_AdjustmentItemsOP");
					// data.put("OtherItemsEffectingOP_AdjustedItemsEffectingOP",
					// data.get("OtherItemsEffectingOP_AdjustedItemsEffectingOP"));
					// data.remove("OtherItemsEffectingOP_AdjustedItemsEffectingOP");
					data.put("NPParentCompanyOwners", data.get("NPFromParentCompanyOwners"));
					data.remove("NPFromParentCompanyOwners");
					data.put("OperatingTaxSurcharges", data.get("OperatingTaxAndSurcharges"));
					data.remove("OperatingTaxAndSurcharges");
					update.update(entry.key, data);
				}
			}
		});

		final NoSqlUpdater update1 = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data",
				"financial.incomestatement");
		update1.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final JSONObject data = entry.value;
				if (data.getString("CompanyCode").startsWith("LC_CN") && data.containsKey("FinancialType")
						&& (data.getString("FinancialType").equals("非金融公司") == false)) {
					data.put("TotalOperatingRevenue", data.get("OperatingRevenue"));
					data.remove("OperatingRevenue");
					data.put("TotalOperatingCost", data.get("OperatingPayout"));
					data.remove("OperatingPayout");
					data.put("SpecialItemsTOC_AdjustmentItemsTOC", data.get("SpecialItemsOP_AdjustmentItemsOP"));
					data.remove("SpecialItemsOP_AdjustmentItemsOP");
					// data.put("OtherItemsEffectingOP_AdjustedItemsEffectingOP",
					// data.get("OtherItemsEffectingOP_AdjustedItemsEffectingOP"));
					// data.remove("OtherItemsEffectingOP_AdjustedItemsEffectingOP");
					data.put("NPParentCompanyOwners", data.get("NPFromParentCompanyOwners"));
					data.remove("NPFromParentCompanyOwners");
					data.put("OperatingTaxSurcharges", data.get("OperatingTaxAndSurcharges"));
					data.remove("OperatingTaxAndSurcharges");
					update1.update(entry.key, data);
				}
			}
		});
	}

	public void identicalData2() throws SQLException {
		final NoSqlUpdater update = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data", "todo.todo_balancesheet");
		update.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final JSONObject data = entry.value;
				if (data.getString("CompanyCode").startsWith("LC_CN") && data.containsKey("FinancialType")
						&& (data.getString("FinancialType").equals("非金融公司") == false)) {
					data.put("AccountReceivable", data.get("AccountReceivables"));
					data.remove("AccountReceivables");
					data.put("InterestReceivable ", data.get("InterestReceivables"));
					data.remove("InterestReceivables");
					data.put("ForeignCurrencyReportConvDiff", data.get("ForeignReportConvDiff"));
					data.remove("ForeignReportConvDiff");
					data.put("OtherItemsEffectingSE",
							data.get("SEExceptionalItems_SEAdjustmentItems_OtherItemsEffectingSE"));
					data.remove("SEExceptionalItems_SEAdjustmentItems_OtherItemsEffectingSE");
					update.update(entry.key, data);
				}
			}
		});

		final NoSqlUpdater update1 = new NoSqlUpdater(_Config.aliDbConfigs, "compset_data", "financial.balancesheet");
		update1.executeForEach(new EntryRunnable<KVSqlUpdater.Entry<String, JSONObject>>() {

			@Override
			public void run(final Entry<String, JSONObject> entry) throws Exception {
				final JSONObject data = entry.value;
				if (data.getString("CompanyCode").startsWith("LC_CN") && data.containsKey("FinancialType")
						&& (data.getString("FinancialType").equals("非金融公司") == false)) {
					data.put("AccountReceivable", data.get("AccountReceivables"));
					data.remove("AccountReceivables");
					data.put("InterestReceivable ", data.get("InterestReceivables"));
					data.remove("InterestReceivables");
					data.put("ForeignCurrencyReportConvDiff", data.get("ForeignReportConvDiff"));
					data.remove("ForeignReportConvDiff");
					data.put("OtherItemsEffectingSE",
							data.get("SEExceptionalItems_SEAdjustmentItems_OtherItemsEffectingSE"));
					data.remove("SEExceptionalItems_SEAdjustmentItems_OtherItemsEffectingSE");
					update1.update(entry.key, data);
				}
			}
		});
	}

	public void identicalData3() throws SQLException {
		final SqlUpdater update = new SqlUpdater(_Config.aliDbConfigs, "compset_platform", "financial.balancesheet");
		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("select companycode,year,period,data from financial.balancesheet");
				rs = ps.executeQuery();
				while (rs.next()) {
					final String companyCode = rs.getString(1);
					final String year = rs.getString(2);
					final String period = rs.getString(3);
					final JSONObject data = JSONObject.parseObject(rs.getString(4));

					if (companyCode.startsWith("LC_CN")) {
						if (data.containsKey("FinancialType")
								&& (data.getString("FinancialType").equals("非金融公司") == false)) {
							data.put("AccountReceivable", data.get("AccountReceivables"));
							data.remove("AccountReceivables");
							data.put("InterestReceivable ", data.get("InterestReceivables"));
							data.remove("InterestReceivables");
							data.put("ForeignCurrencyReportConvDiff", data.get("ForeignReportConvDiff"));
							data.remove("ForeignReportConvDiff");
							data.put("OtherItemsEffectingSE",
									data.get("SEExceptionalItems_SEAdjustmentItems_OtherItemsEffectingSE"));
							data.remove("SEExceptionalItems_SEAdjustmentItems_OtherItemsEffectingSE");

							final String sqlHead = "update financial.balancesheet set data = '";
							final String whereCondition = "'::jsonb where companycode ='" + companyCode
									+ "' and year = '" + year + "' and period = '" + period + "'";
							update.execute(sqlHead + data.toJSONString() + whereCondition);

						}
					}
				}
			}
		});
	}

	public void identicalData4() throws SQLException {
		final SqlUpdater update = new SqlUpdater(_Config.aliDbConfigs, "compset_platform", "financial.incomestatement");
		update.execute(update.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("select companycode,year,period,data from financial.incomestatement");
				rs = ps.executeQuery();
				while (rs.next()) {
					final String companyCode = rs.getString(1);
					final String year = rs.getString(2);
					final String period = rs.getString(3);
					final JSONObject data = JSONObject.parseObject(rs.getString(4));
					if (companyCode.startsWith("LC_CN")) {
						if (data.containsKey("FinancialType")
								&& (data.getString("FinancialType").equals("非金融公司") == false)) {

							data.put("TotalOperatingRevenue", data.get("OperatingRevenue"));
							data.remove("OperatingRevenue");
							data.put("TotalOperatingCost", data.get("OperatingPayout"));
							data.remove("OperatingPayout");
							data.put("SpecialItemsTOC_AdjustmentItemsTOC",
									data.get("SpecialItemsOP_AdjustmentItemsOP"));
							data.remove("SpecialItemsOP_AdjustmentItemsOP");
							// data.put("OtherItemsEffectingOP_AdjustedItemsEffectingOP",
							// data.get("OtherItemsEffectingOP_AdjustedItemsEffectingOP"));
							// data.remove("OtherItemsEffectingOP_AdjustedItemsEffectingOP");
							data.put("NPParentCompanyOwners", data.get("NPFromParentCompanyOwners"));
							data.remove("NPFromParentCompanyOwners");
							data.put("OperatingTaxSurcharges", data.get("OperatingTaxAndSurcharges"));
							data.remove("OperatingTaxAndSurcharges");
							final String sqlHead = "update financial.incomestatement set data = '";
							final String whereCondition = "'::jsonb where companycode ='" + companyCode
									+ "' and year = '" + year + "' and period = '" + period + "'";
							update.execute(sqlHead + data.toJSONString() + whereCondition);
						}
					}
				}
			}
		});
	}

	public static void main(final String[] args) throws SQLException {
		final MakeDataUpdate m = new MakeDataUpdate();

		m.identicalData1();
		m.identicalData2();
		m.identicalData3();
		m.identicalData4();
	}

}
