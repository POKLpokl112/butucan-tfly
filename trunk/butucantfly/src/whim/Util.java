package whim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	public static Set<String> readUsefulLog(final String regex, final String filePath) throws IOException {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(filePath));
			br = new BufferedReader(fr);
			final Set<String> row = new HashSet<>();
			String s = br.readLine();
			while (s != null) {
				final String data = find(s, "more than one row!-----([^=]+=[^=]+)");
				if (data != null) {
					row.add(data);
				}
				s = br.readLine();
			}
			return row;

		} catch (final Exception e) {
			throw e;
		} finally {
			br.close();
			fr.close();
		}

	}

	public static String find(final String source, final String regex) {
		return parseRegex(source, regex, 1);
	}

	public static String findTheSame(final String source, final String regex) {
		return parseRegex(source, regex, 0);
	}

	public static String parseRegex(final String source, final String regex, final int i) {
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(source);

		return m.find() ? m.group(i) : null;
	}

	public static void main(final String[] args) throws IOException {
		System.out.println(findTheSame(") - more than one row!-----芜湖顺荣三七互娱网络科技股份有限公司=三七互娱",
				"more than one row!-----([^=]+=[^=]+)"));
	}

}
