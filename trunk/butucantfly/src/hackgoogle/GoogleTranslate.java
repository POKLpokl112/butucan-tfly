package hackgoogle;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import common.Util;

public class GoogleTranslate {

	// http://translate.google.cn/translate_a/single?client=t&sl=zh-CN&tl=en&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&ssel=5&tsel=5&kc=1&tk=20332.393349
	public static void main(final String[] args) {
		System.out.println(Util.loadUrlContent2("http://translate.google.cn"));
	}

	public static String getData(final String url) {

		final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectionRequestTimeout(3000)
				.setSocketTimeout(3000).setConnectTimeout(3000).setCookieSpec(CookieSpecs.DEFAULT);

		final CloseableHttpClient httpClient = HttpClients.createDefault();
		final HttpGet httpGet = new HttpGet(url);
		final HttpPost httpPost = new HttpPost();

		try {
			httpPost.setURI(new URI(url));
		} catch (final URISyntaxException e1) {
			e1.printStackTrace();
		}

		httpGet.setConfig(requestConfigBuilder.build());
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			final HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

}
