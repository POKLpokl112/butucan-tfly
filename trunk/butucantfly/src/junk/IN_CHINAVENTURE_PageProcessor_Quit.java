// package junk;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import com.alibaba.fastjson.JSONObject;
//
// import business.schedule.StarterManager;
// import common.Util;
// import us.codecraft.webmagic.Page;
// import us.codecraft.webmagic.Robot;
// import us.codecraft.webmagic.SpiderRunner;
// import us.codecraft.webmagic.executor.AbstractPageProcessor;
// import us.codecraft.webmagic.listener.AbstractExecutorListener;
// import us.codecraft.webmagic.starter.BlockStarter;
//
// public class IN_CHINAVENTURE_PageProcessor_Quit extends AbstractPageProcessor
// {
// static String mark;
//
// @Override
// public void setup() {
// super.setup();
//
// executorListeners.add(new AbstractExecutorListener() {
//
// @Override
// public void onStart(final Robot robot) {
// final JSONObject object = StarterManager.getInstance()
// .getConfig(IN_CHINAVENTURE_PageProcessor_Quit.class, "mark");
// if (object != null && object.containsKey("Quit标记mark")) {
// mark = object.get("Quit标记mark").toString();
// } else {
// mark = "nothing";
// }
// }
//
// @Override
// public void onFinished(final Robot robot) {
// final JSONObject objectOld = StarterManager.getInstance()
// .getConfig(IN_CHINAVENTURE_PageProcessor_Quit.class, "remark");
// final JSONObject object = new JSONObject();
// object.put("Quit标记mark", objectOld.get("Quit标记remark"));
// StarterManager.getInstance().putConfig(IN_CHINAVENTURE_PageProcessor_Quit.class,
// "mark", object);
// }
// });
//
// starters.add(new BlockStarter() {
// @Override
// protected void doRun() throws Exception {
// this.addTargetUrl("http://www.chinaventure.com.cn/event/searchExitsList/-1/-1/-1/-1/-1/0-16.shtml");
// }
// });
// }
//
// @Override
// public void process(final String url, final Page page) throws Exception {
// super.process(url, page);
//
// final int pageNum = Util.parseRegexInt(url, "/([0-9]+)-16");
// if (pageNum == 0) {
// final String remark =
// page.getJson().getJSONObject().getJSONArray("data").getJSONObject(0).getString("id");
// final JSONObject object = new JSONObject();
// object.put("Quit标记remark", remark);
// StarterManager.getInstance().putConfig(IN_CHINAVENTURE_PageProcessor_Quit.class,
// "remark", object);
// }
//
// final JSONObject data = page.getJson().getJSONObject();
// final List<String> idList = new ArrayList<>();
// for (final Object obj : data.getJSONArray("data")) {
// final JSONObject jo = (JSONObject) obj;
// final String id = jo.getString("id");
// idList.add(id);
// }
//
// if (idList.size() > 0 && idList.contains(mark) == false) {
// // pageNum++;
// final int num = pageNum + 15;
// page.addTargetUrl(
// "http://www.chinaventure.com.cn/event/searchExitsList/-1/-1/-1/-1/-1/" + num
// + "-16.shtml");
// }
// }
//
// public static void main(final String[] args) {
// SpiderRunner.run(new IN_CHINAVENTURE_PageProcessor_Quit().forTest());
// }
//
// }
