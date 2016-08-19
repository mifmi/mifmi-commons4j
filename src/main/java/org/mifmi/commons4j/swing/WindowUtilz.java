/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public final class WindowUtilz {

	private WindowUtilz() {
		// NOP
	}

	public static void setPositionCenter(Window window) {
		Dimension display = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)((display.getWidth() - window.getWidth()) / 2);
		int y = (int)((display.getHeight() - window.getHeight()) / 2);

		window.setLocation(x, y);
	}

	public static void setPositionCenter(Window window, Window parentWindow) {
		int x = parentWindow.getX() + ((parentWindow.getWidth() - window.getWidth()) / 2);
		int y = parentWindow.getY() + ((parentWindow.getHeight() - window.getHeight()) / 2);
		
		Dimension display = Toolkit.getDefaultToolkit().getScreenSize();
		if (x < 0) {
			x = 0;
		} else {
			int x2 = (int)(display.getWidth() - window.getWidth());
			if (x2 < x) {
				x = x2;
			}
		}

		if (y < 0) {
			y = 0;
		} else {
			int y2 = (int)(display.getHeight() - window.getHeight());
			if (y2 < y) {
				y = y2;
			}
		}
		
		window.setLocation(x, y);
	}
}
