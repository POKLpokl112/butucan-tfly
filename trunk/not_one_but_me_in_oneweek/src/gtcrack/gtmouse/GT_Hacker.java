package gtcrack.gtmouse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class GT_Hacker {

	private static int[][] ii2 = { { 1, 0 }, { 2, 0 }, { 1, -1 }, { 1, 1 }, { 0, 1 }, { 0, -1 }, { 3, 0 }, { 2, -1 },
			{ 2, 1 } };

	private static String c = "stuvwxyz~";
	private static String b = "()*,-./0123456789:?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqr";

	private static String getD(final int i) {
		final int c = b.length();
		String d = "";
		int e = Math.abs(i);
		int f = e / c;

		if (f >= c) {
			f = c - 1;
		}

		if (f != 0) {
			d = b.substring(f, f + 1);
		}

		e %= c;
		String g = "";
		if (i < 0) {
			g += "!";
		}

		if (!"".equals(d)) {
			g += "$";
		}
		return g + d + b.charAt(e);

	}

	private static String getB(final int[] moveArray) {

		for (int d = 0, e = ii2.length; d < e; d++) {
			if (moveArray[0] == ii2[d][0] && moveArray[1] == ii2[d][1]) {
				return c.substring(d, d + 1);
			}
		}

		return "0";
	}

	public static String getA1(final List<int[]> ii) {
		final StringBuffer g = new StringBuffer();
		final StringBuffer h = new StringBuffer();
		final StringBuffer i = new StringBuffer();

		for (final int[] element : ii) {
			final String b = getB(element);
			if ("0".equals(b) == false) {
				h.append(b);
			} else {
				g.append(getD(element[0]));
				h.append(getD(element[1]));
			}
			i.append(getD(element[2]));
		}

		return encode(g + "!!" + h + "!!" + i);
	};

	public static String getA(final int[][] ii) {
		final StringBuffer g = new StringBuffer();
		final StringBuffer h = new StringBuffer();
		final StringBuffer i = new StringBuffer();

		for (final int[] element : ii) {
			final String b = getB(element);
			if ("0".equals(b) == false) {
				h.append(b);
			} else {
				g.append(getD(element[0]));
				h.append(getD(element[1]));
			}
			i.append(getD(element[2]));
		}
		return encode(g + "!!" + h + "!!" + i);
	};

	private static String encode(final String a) {
		String s = "";
		try {
			s = URLEncoder.encode(a, "UTF-8").replace("%21", "!").replace("%28", "(").replace("%29", ")").replace("%7E",
					"~");
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
		return s;
	}

	public static void main(final String[] args) {
		System.out.println(encode("C(!!Axssystsyutssssssss(!!(:002111111191rr199$,6"));

	}

}
