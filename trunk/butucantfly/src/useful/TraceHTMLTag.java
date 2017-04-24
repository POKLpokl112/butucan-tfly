package useful;

import common.Util;
import us.codecraft.webmagic.selector.Html;

public class TraceHTMLTag {

	public static void main(final String[] args) {
		final String s = "<span class=\"titleLabel\" style=\"line-height: 16px\"> 企业年报信息 </span></div> </td> </tr> </table> </div> <div> <table class=\"tableInfo\" cellspacing=\"0\"> <tr class=\"trTitleText\"> <td>序号</td> <td>报送年度</td> <td>公示日期</td> <td>详情</td> </tr> <tr class=\"tablebodytext\"> <td class=\"nothing\" colspan=\"4\">暂无数据</td> </tr> </table>asdasd2312  asd";
		final String a = "<table" + Util.parseRegex(s, "企业年报信息[\\s\\S]*?<table([\\s\\S]*?)/table>") + "/table>";
		final Html html = new Html(a);
		System.out.println(html);

		System.out.println("116".matches("^(\\-|\\+?)\\d+\\.?\\d*"));
	}

}
