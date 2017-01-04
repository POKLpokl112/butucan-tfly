// package junk;
//
// import com.alibaba.fastjson.JSONArray;
//
// import common.Util;
// import us.codecraft.webmagic.Page;
// import us.codecraft.webmagic.SpiderRunner;
// import us.codecraft.webmagic.executor.AbstractPageProcessor;
// import us.codecraft.webmagic.starter.BlockStarter;
//
// public class IN_CHINAVENTURE_PageProcessor_Company2 extends
// AbstractPageProcessor {
//
// @Override
// public void setup() {
// super.setup();
// starters.add(new BlockStarter() {
//
// @Override
// protected void doRun() throws Exception {
// this.forceTargetUrl("http://www.chinaventure.com.cn/commSearch/0-20/enterprise/.shtml");
// }
//
// });
// }
//
// @Override
// public void process(final String url, final Page page) throws Exception {
// super.process(url, page);
//
// final JSONArray array = page.getJson().getJSONObject().getJSONArray("list");
// final int size = array.size();
// if (size == 20) {
// final int num = Util.parseRegexInt(url, "/([0-9])+-20") + 20;
// page.addTargetUrl("http://www.chinaventure.com.cn/commSearch/" + num +
// "-20/enterprise/.shtml");
// }
//
// }
//
// public static void main(final String[] args) {
// SpiderRunner.run(new IN_CHINAVENTURE_PageProcessor_Company2().forTest());
// }
//
// }
