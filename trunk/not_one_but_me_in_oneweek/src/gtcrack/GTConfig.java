package gtcrack;

public class GTConfig {

	private static String GT_HOST_URL = "http://api.geetest.com/get.php";

	public static String MIDDLE_URL = "?product=popup&offline=false&protocol=&type=slide&path=/js/geetest.5.10.10.js&";

	public static String getGetURL(final String challengeString) {
		return GT_HOST_URL + MIDDLE_URL + "callback=geetest_" + System.currentTimeMillis() + "&" + challengeString;
	}

	public static String GetRegisterURL(final String registerURL) {
		return registerURL + System.currentTimeMillis();
	}

	public static void main(final String[] args) {
	}

}
