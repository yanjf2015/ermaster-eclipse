package org.insightech.er.util;

import java.awt.image.BufferedImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class ImageUtils {

	public static int getFormatType(String saveFilePath) {
		int format = -1;

		if (saveFilePath == null) {
			return format;
		}

		int index = saveFilePath.lastIndexOf(".");
		String ext = null;
		if (index != -1 && index != saveFilePath.length() - 1) {
			ext = saveFilePath.substring(index + 1, saveFilePath.length());
		} else {
			ext = "";
		}

		if (ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpg")) {
			format = SWT.IMAGE_JPEG;

		} else if (ext.equalsIgnoreCase("bmp")) {
			format = SWT.IMAGE_BMP;

		} else if (ext.equalsIgnoreCase("png")) {
			format = SWT.IMAGE_PNG;

		}

		return format;
	}

	public static void drawAtBufferedImage(BufferedImage bimg, Image image,
			int x, int y) throws InterruptedException {

		ImageData data = image.getImageData();

		for (int i = 0; i < image.getBounds().width; i++) {

			for (int j = 0; j < image.getBounds().height; j++) {
				int tmp = 4 * (j * image.getBounds().width + i);

				if (data.data.length > tmp + 2) {
					int r = 0xff & data.data[tmp + 2];
					int g = 0xff & data.data[tmp + 1];
					int b = 0xff & data.data[tmp];

					bimg.setRGB(i + x, j + y, 0xFF << 24 | r << 16 | g << 8
							| b << 0);
				}
			}
		}
	}
}
