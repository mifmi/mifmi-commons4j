/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.graphics.image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageUtilz {
	
	private ImageUtilz() {
	}

	public static byte[] shrinkAsByteArray(InputStream is, String formatName, int maxWidth, int maxHeight, Map<Key, Object> hints) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			try {
				shrink(is, bos, formatName, maxWidth, maxHeight, hints);
				bos.flush();
				return baos.toByteArray();
			} finally {
				bos.close();
			}
		} finally {
			baos.close();
		}
	}
	public static void shrink(InputStream is, OutputStream os, String formatName, int maxWidth, int maxHeight, Map<Key, Object> hints) throws IOException {
		BufferedImage shrinkImage = shrink(is, maxWidth, maxHeight, hints);
		ImageIO.write(shrinkImage, formatName, os);
	}
	public static BufferedImage shrink(InputStream is, int maxWidth, int maxHeight, Map<Key, Object> hints) throws IOException {
		BufferedImage image;
		try {
			image = ImageIO.read(is);
		} catch (IOException e) {
			throw e;
		}
		
		double scale = calcScale(image.getWidth(), image.getHeight(), maxWidth, maxHeight);
		if (1.0 <= scale) {
			// FIXME
		}
		
		BufferedImage shrinkImage = resize(image, scale, hints);
		return shrinkImage;
	}
	
	public static BufferedImage resize(BufferedImage image, double scale, Map<Key, Object> hints) {
		// see: http://www.javainthebox.com/2010/07/java-3.html
		
		int targetWidth = (int)(image.getWidth() * scale);
		int targetHeight = (int)(image.getHeight() * scale);

		BufferedImage currentImage = image;

		while (true) {
			int currentWidth = currentImage.getWidth();
			int currentHeight = currentImage.getHeight();
			
			if (currentWidth <= targetWidth || currentHeight <= targetHeight) {
				break;
			}
			
			int delta = currentWidth - targetWidth;
			int nextWidth = currentWidth >> 1;
			int nextHeight = currentHeight >> 1;
			boolean isFinal = false;
			if (delta <= nextWidth) {
				// 最終的なイメージとサイズの差が、次に縮小するサイズよりも小さい場合
				nextWidth = targetWidth;
				nextHeight = targetHeight;
				isFinal = true;
			}
			
			BufferedImage tmpImage = new BufferedImage(nextWidth, nextHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = tmpImage.createGraphics();
			try {
				if (hints != null) {
					for (Map.Entry<Key, Object> hint : hints.entrySet()) {
						g2d.setRenderingHint(hint.getKey(), hint.getValue());
					}
				}
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2d.drawImage(currentImage, 0, 0, tmpImage.getWidth(), tmpImage.getHeight(), null);
			} finally {
				g2d.dispose();
			}
			currentImage = tmpImage;
			
			if (isFinal) {
				break;
			}
		}
	
		return currentImage;
	}
	
	public static double calcScale(int srcWidth, int srcHeight, int destWidth, int destHeight) {
		double scaleWidth = (double)destWidth / srcWidth;
		double scaleHeight = (double)destHeight / srcHeight;
		return Math.min(scaleWidth, scaleHeight);
	}
}
