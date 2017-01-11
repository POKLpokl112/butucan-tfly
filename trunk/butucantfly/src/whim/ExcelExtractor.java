package whim;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExtractor {

	public static Map<String, String> getData(final File file) throws IOException {
		final Map<String, String> map = new HashMap<>();
		final FileInputStream in = new FileInputStream(file);
		final XSSFWorkbook wb = new XSSFWorkbook(in);

		for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
			final XSSFSheet st = wb.getSheetAt(numSheet);
			if (st == null) {
				continue;
			}
			for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
				final XSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				final XSSFCell cell1 = row.getCell(1);
				if (cell1 == null) {
					continue;
				}
				final XSSFCell cell2 = row.getCell(2);
				try {
					final String companyName = normalizeCompanyName(cell1.getStringCellValue());
					final String name = normalizeCompanyName(cell2.getStringCellValue());
					map.put(companyName, name);
				} catch (final Exception e) {
					System.out.println(map);
					throw e;
				}

			}
		}
		return map;
	}

	public static String normalizeCompanyNam(final String companyName) {
		if (companyName == null || "".equals(companyName)) {
			throw new IllegalArgumentException(companyName);
		}
		String result = companyName;

		// 去掉重复空白
		result = result.replaceAll("\\s+", " ");

		// 统一转为英文符号
		result = result.replace("（", "(").replace("）", ")").replace("。", ".");

		// trim
		result = result.trim();

		return result;
	}

	public static String normalizeCompanyName(final String companyName) {
		return normalizeCompanyNam(normalizeHtmlSymbol(companyName)).replaceAll("\\(原名?.*\\)$", "");
	}

	public static String normalizeHtmlSymbol(final String text) {
		return text.replace("&amp;", "&").replace("&nbsp;", " ").replaceAll("\\s+", " ").trim();
	}

	public static void main(final String[] args) throws FileNotFoundException, IOException {
		System.out.println(getData(new File("d:\\公司筛选.xlsx")));
	}

}
