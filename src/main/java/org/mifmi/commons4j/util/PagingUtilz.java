/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.util;

/**
 * Helper methods for paging.
 * 
 * @author mozq
 */
public final class PagingUtilz {

	private PagingUtilz() {
		// NOP
	}

	public static int normalizePage(int page) {
		if (page < 1) {
			return 1;
		}
		return page;
	}

	public static long normalizePage(long page) {
		if (page < 1) {
			return 1;
		}
		return page;
	}
	
	public static int toPage(int offset, int limit) {
		return (offset / limit) + 1;
	}
	
	public static long toPage(long offset, long limit) {
		return (offset / limit) + 1;
	}
	
	public static int toOffset(int page, int limit) {
		return limit * (page - 1);
	}
	
	public static long toOffset(long page, long limit) {
		return limit * (page - 1);
	}
}
