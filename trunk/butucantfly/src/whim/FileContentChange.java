package whim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileContentChange {

	public static void changeFileContent(final File file) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			final StringBuffer sb = new StringBuffer();
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString.trim().replace("\t", ",") + "\r\n");

			}
			reader.close();

			final File csv = new File("d:\\" + file.getName() + ".csv");
			final FileOutputStream out = new FileOutputStream(csv);

			out.write(sb.toString().getBytes("gbk"));
			out.close();

		} catch (

		final IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (final IOException e1) {
				}
			}
		}
	}

	public static void main(final String[] args) throws IOException {
		changeFileContent(new File("d:\\pedaily_pcd20170106034608.txt"));

	}

}
