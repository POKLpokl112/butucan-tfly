package test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test {

	public static void main(final String[] args) throws UnsupportedEncodingException {
		System.out.println("asd" == "asd");
		System.out.println(URLEncoder.encode("建设", "gbk"));
		System.out.println(URLDecoder.decode(
				"%C7%EB%CA%E4%C8%EB%D3%AA%D2%B5%D6%B4%D5%D5%D7%A2%B2%E1%BA%C5%BB%F2%CD%B3%D2%BB%C9%E7%BB%E1%D0%C5%D3%C3%B4%FA%C2%EB",
				"gbk"));
	}

}
