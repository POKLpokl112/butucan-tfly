package junk;

import java.sql.SQLException;

import org.junit.Assert;

import com.alibaba.fastjson.JSONObject;

import common.Util;
import common.db.NoSqlUpdater;
import common.db.SqlUpdater;

public abstract class OldDataExtractor {

	final private NoSqlUpdater urlUpdate = new NoSqlUpdater("company.companyandurl", true);
	final private SqlUpdater comUpdate = new SqlUpdater("company.companynameandid", true);

	final SqlUpdater errorUpdate = new SqlUpdater("company.error", true);

	public void putCompanyNameAndURL(final String companyName, final String uRL) throws SQLException {

		Assert.assertTrue(Util.notEmpty(companyName) && Util.notEmpty(uRL));

		urlUpdate.execute(urlUpdate.new SqlExecutor() {

			@Override
			protected void doExecute() throws SQLException {
				ps = prepareStatement("select data from " + urlUpdate.getTable() + " where id = ?");
				ps.setString(1, companyName);
				rs = ps.executeQuery();
				if (rs.next()) {
					final JSONObject data = JSONObject.parseObject(rs.getString(1));
					if (data.containsKey("url") && data.getString("url").equals(uRL) == false) {
						errorUpdate.insertUpdate(companyName, data.toJSONString() + "|" + uRL);
						// throw new
						// IllegalArgumentException(data.toJSONString() + "\r\n"
						// + "同一个公司名不同的url:" + uRL);
					}
				} else {
					insertCompanyAndUrl(urlUpdate, companyName, uRL);
				}

			}
		});
	}

	public void putCompanyNameAndID(final String companyName, final String id) throws SQLException {
		Assert.assertNotNull(companyName);
		Assert.assertNotNull(id);
		comUpdate.insertUpdate(companyName, id);
	}

	private void insertCompanyAndUrl(final NoSqlUpdater update, final String companyName, final String uRL)
			throws SQLException {
		final JSONObject data = new JSONObject();
		data.put("url", uRL);
		update.insert(companyName, data);
	}
}
