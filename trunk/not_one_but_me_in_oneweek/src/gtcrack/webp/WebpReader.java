package gtcrack.webp;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;

import net.sf.javavp8decoder.imageio.WebPImageReaderSpi;

public class WebpReader {

	private static final WebPImageReaderSpi wp = new WebPImageReaderSpi();

	public static BufferedImage readByte(final byte[] bytes) throws Exception {

		final InputStream input = new BufferedInputStream(new ByteArrayInputStream(bytes));

		try {
			return ImageIO.read(input);
		} catch (final IIOException e) {
			return readInputStream(input);
		}

	}

	public static BufferedImage readFile(final File file) throws Exception {

		try {
			return ImageIO.read(file);
		} catch (final IIOException e) {
			return readInputStream(new FileImageInputStream(file));
		}

	}

	public static BufferedImage readInputStream(final Object InputStream) throws Exception {

		final ImageReader imageReader = wp.createReaderInstance();
		imageReader.setInput(InputStream);
		final BufferedImage bufferedImage = imageReader.read(0);
		return bufferedImage;

	}

	public static void main(final String[] args) throws Exception {
	}

}
