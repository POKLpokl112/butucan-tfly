package funny;

import java.util.Map;

import common.config.GlobalConfig;

public class _Config {

	public static final Map<String, String> aliDbConfigs = GlobalConfig.db.dbConfigs();

	// 主要用于方便本地测试用！
	static {
		aliDbConfigs.put("url", "jdbc:postgresql://rdsx4rdj2288028n03k0shzd.pg.rds.aliyuncs.com:3469/{0}");
		aliDbConfigs.put("user", "shzd999");
		aliDbConfigs.put("password", "faldjkeino@#$132kdfa");
	}

}
