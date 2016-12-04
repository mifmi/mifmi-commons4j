package org.mifmi.commons4j.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.swing.ImageIcon;

public class AdjustableImageIcon extends ImageIcon {
	private static final long serialVersionUID = 1L;

	public enum Mode {
		Fit,
		Fill,
		None
	}
	
	private Mode mode = Mode.Fit;

	public AdjustableImageIcon() {
	}

	public AdjustableImageIcon(String filename) {
		super(filename);
	}

	public AdjustableImageIcon(URL location) {
		super(location);
	}

	public AdjustableImageIcon(Image image) {
		super(image);
	}

	public AdjustableImageIcon(byte[] imageData) {
		super(imageData);
	}

	public AdjustableImageIcon(String filename, String description) {
		super(filename, description);
	}

	public AdjustableImageIcon(URL location, String description) {
		super(location, description);
	}

	public AdjustableImageIcon(Image image, String description) {
		super(image, description);
	}

	public AdjustableImageIcon(byte[] imageData, String description) {
		super(imageData, description);
	}

	public Mode getMode() {
		return mode;
	}

	public AdjustableImageIcon setMode(Mode mode) {
		this.mode = mode;
		return this;
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		if (mode == Mode.None) {
			super.paintIcon(c, g, x, y);
			return;
		}
		
		Image image = getImage();
		if (image == null) {
			return;
		}
		
		Insets insets = ((Container)c).getInsets();
		int dx = insets.left;
		int dy = insets.top;
		
		int dw = c.getWidth() - dx - insets.right;
		int dh = c.getHeight() - dy - insets.bottom;
		
		int iw = image.getWidth(c);
		int ih = image.getHeight(c);
		
		if (mode == Mode.Fit) {
			if (iw * dh < ih * dw) {
				iw = (dh * iw) / ih;
				dx += (dw - iw) / 2;
				dw = iw;
			} else {
				ih = (dw * ih) / iw;
				dy += (dh - ih) / 2;
				dh = ih;
			}
		}
		
		ImageObserver io = getImageObserver();
		g.drawImage(image, dx, dy, dw, dh, (io == null) ? c : io);
	}
}
