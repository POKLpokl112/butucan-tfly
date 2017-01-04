// package junk;
//
// import com.alibaba.fastjson.JSONArray;
// import com.alibaba.fastjson.JSONObject;
//
// import common.Util;
// import us.codecraft.webmagic.Page;
// import us.codecraft.webmagic.SpiderRunner;
// import us.codecraft.webmagic.executor.AbstractPageProcessor;
// import us.codecraft.webmagic.starter.BlockStarter;
//
// public class IN_CHINAVENTURE_PageProcessor_Investfirm2 extends
// AbstractPageProcessor {
//
// @Override
// public void setup() {
// super.setup();
//
// starters.add(new BlockStarter() {
//
// @Override
// protected void doRun() throws Exception {
// this.forceTargetUrl("http://www.chinaventure.com.cn/commSearch/0-20/intitution/.shtml");
// }
//
// });
// }
//
// @Override
// public void process(final String url, final Page page) throws Exception {
// super.process(url, page);
// page.getCookies();
//
// if (url.contains("commSearch")) {
// final JSONArray array = page.getJson().getJSONObject().getJSONArray("list");
// final int size = array.size();
// if (size == 20) {
// final int num = Util.parseRegexInt(url, "/([0-9]+)-20") + 20;
// page.addTargetUrl("http://www.chinaventure.com.cn/commSearch/" + num +
// "-20/intitution/.shtml");
// }
// for (int i = 0; i < size; i++) {
// final JSONObject obj = array.getJSONObject(i);
// final String id = obj.getString("id");
// page.addTargetUrl("http://www.chinaventure.com.cn/cvmodule/institution/detail/"
// + id + ".shtml");
// }
// } else if (url.contains("cvmodule")) {
// page.savePage("cvmodule");
// new ActionSubProcessor(url, page) {
//
// @Override
// public void process(final String url, final Page page) throws Exception {
// final String id = Util.parseRegex(url, "detail/(.+).shtml");
// actor.gotoPage(newPostRequest(
// "http://www.chinaventure.com.cn/cvmodule/institution/getAjaxInvestEvent.shtml",
// "",
// "institutionId=" + id), page);
// page.savePage("getAjaxInvestEvent");
// actor.gotoPage(newPostRequest(
// "http://www.chinaventure.com.cn/cvmodule/institution/getAjaxExitsEvent.shtml",
// "",
// "institutionId=" + id), page);
//
// }
// };
// }
//
// }
//
// public static void main(final String[] args) {
// SpiderRunner.run(new IN_CHINAVENTURE_PageProcessor_Investfirm2().forTest());
// }
//
// }
