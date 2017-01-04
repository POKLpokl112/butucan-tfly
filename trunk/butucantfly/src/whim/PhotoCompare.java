package whim;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PhotoCompare {

	public static int getImageDefferencePoint(final BufferedImage bfImage1, final BufferedImage bfImage2) {
		final int i = findImageDeference(getImageRGB(bfImage1), getImageRGB(bfImage2));
		if (i == 0) {
			return -1;
		}
		return i == -1 ? -1 : i + 65;
	}

	public static int[][] getImageRGB(final BufferedImage bfImage) {
		final int width = bfImage.getWidth();
		final int height = bfImage.getHeight();
		final int[][] result = new int[width - 65][height - 16];
		for (int w = 71; w < width; w++) {
			for (int h = 16; h < height; h++) {
				result[w - 71][h - 16] = bfImage.getRGB(w, h);
			}
		}
		return result;
	}

	public static int findImageDeference(final int[][] rgb1, final int[][] rgb2) {
		for (int i = 0; i < rgb1.length; i++) {
			if (compareRGB(rgb2[i], rgb1[i])) {
				return i;
			}
		}
		return -1;
	}

	private static boolean compareRGB(final int[] rgb1, final int[] rgb2) {
		int count = 0;
		for (int i = 0; i < rgb2.length; i++) {
			final Color c1 = new Color(rgb1[i]);
			final Color c2 = new Color(rgb2[i]);
			if (compareColor(c1, c2)) {
				count++;
			}
		}
		if (count > 13) {
			return true;
		}

		return false;
	}

	private static boolean compareColor(final Color c1, final Color c2) {
		final double a1 = c1.getRed() * 0.297 + c1.getGreen() * 0.593 + c1.getBlue() * 0.11;
		final double a2 = c2.getRed() * 0.297 + c2.getGreen() * 0.593 + c2.getBlue() * 0.11;
		final double t = 1 - Math.abs(a1 - a2) / (a1 + a2);
		// final int r1 = c1.getRed();
		// final int g1 = c1.getGreen();
		// final int b1 = c1.getBlue();
		// final int r2 = c2.getRed();
		// final int g2 = c2.getGreen();
		// final int b2 = c2.getBlue();
		// final double t = (255 - Math.abs(r1 - r2) * 0.297 - Math.abs(g1 - g2)
		// * 0.593 - Math.abs(b1 - b2) * 0.11) / 255;
		if (t < 0.87) {
			return true;
		}
		return false;
	}

	public static void main(final String[] args) throws IOException {
		// System.out.println(
		// getImageDefferencePoint(ImageIO.read(new File("d:\\a.webp")),
		// ImageIO.read(new File("d:\\b.webp"))));
		System.out.println(
				getImageDefferencePoint(ImageIO.read(new File("d:\\c.webp")), ImageIO.read(new File("d:\\d.webp"))));
	}

}
