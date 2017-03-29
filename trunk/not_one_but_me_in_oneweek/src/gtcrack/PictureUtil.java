package gtcrack;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import gtcrack.webp.WebpReader;

public class PictureUtil {
	private static List<Integer> picOrder = new ArrayList<>();
	static {
		final String[] b = "6_11_7_10_4_12_3_1_0_5_2_9_8".split("_");
		for (int d = 0, e = 52; d < e; d++) {
			int a = 2 * Integer.parseInt(b[d % 26 / 2]) + d % 2;
			a += d % 2 == 1 ? -1 : 1;
			if ((d / 2) % 2 == 1) {
				a += d % 2 == 0 ? -1 : 1;
			}
			a += d < 26 ? 26 : 0;
			picOrder.add(a);
		}
	}

	public static List<Integer> getPicList() {
		return picOrder;
	}

	public static BufferedImage repairImage(final BufferedImage bfi, final List<Integer> order) throws IOException {

		final List<BufferedImage> list = new ArrayList<>();
		final int width = bfi.getWidth();

		for (int i = 0, k = width / 12; i < k; i++) {
			list.add(bfi.getSubimage(12 * i + 1, 0, 11, 58));
		}

		for (int i = 0, k = width / 12; i < k; i++) {
			list.add(bfi.getSubimage(12 * i + 1, 58, 11, 58));
		}

		final List<BufferedImage> orderList = new ArrayList<>();
		for (final int i : order) {
			orderList.add(list.get(i));
		}

		final BufferedImage oriBfi = getOriPicture(orderList);

		// ImageIO.write(oriBfi, "png", new File("d://x.webp"));
		return oriBfi;
	}

	private static BufferedImage getOriPicture(final List<BufferedImage> orderList) {
		final BufferedImage bfi = new BufferedImage(260, 116, BufferedImage.TYPE_4BYTE_ABGR);

		final Graphics g = bfi.getGraphics();
		for (int i = 0; i < 26; i++) {
			g.drawImage(orderList.get(i), i * 10, 0, null);
		}

		for (int i = 26; i < 52; i++) {
			g.drawImage(orderList.get(i), (i - 26) * 10, 58, null);
		}

		return bfi;
	}

	public static void main(final String[] args) throws Exception {

		ImageIO.write(repairImage(WebpReader.readFile(new File("d://full.webp")), picOrder), "png",
				new File("d://x.webp"));
		ImageIO.write(repairImage(WebpReader.readFile(new File("d://no.webp")), picOrder), "png",
				new File("d://y.webp"));
		// repairImage(WebpReader.readFile(new File("d://no.webp")), picOrder);

	}

}
