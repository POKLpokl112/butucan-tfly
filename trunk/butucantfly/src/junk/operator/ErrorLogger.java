package junk.operator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.Util;

/**
 * 结果输入
 * 
 * @author Administrator
 *
 */
public class ErrorLogger {

	int size = 0;

	public ErrorLogger() {
		final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddhhmmss");
		final Date date = new Date();
		this.path = "src" + File.separator + "file" + File.separator + "error" + File.separator + "error"
				+ dateFormater.format(date) + ".txt";
		if (Util.isEmpty(path)) {
			try {
				throw new Exception("缺少必填选项！");
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private final StringBuffer errorLogs = new StringBuffer();

	private String path = null;

	public ErrorLogger(final String path, final String fileName, final String... title) {
		final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddhhmmss");
		final Date date = new Date();
		this.path = path + fileName + dateFormater.format(date) + ".txt";
		if (Util.isEmpty(path)) {
			try {
				throw new Exception("缺少必填选项！");
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void addErrorLog(final String... keyWords) {
		for (final String keyWord : keyWords) {
			errorLogs.append(keyWord + "\t");
		}
		errorLogs.append("\r\n");
		size++;
	}

	/**
	 * 错误数量
	 * 
	 * @return
	 */
	public int errorSize() {
		return size;
	}

	/**
	 * 错误信息写入日志
	 * 
	 * @return key-value
	 */
	public synchronized void getErrorFile() {
		final File file = new File(this.path).getAbsoluteFile();
		if (file.exists()) {
			file.mkdirs();
		}
		Util.saveAsFile(new File(this.path), errorLogs.toString());
	}

}
