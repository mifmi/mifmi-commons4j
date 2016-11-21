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
 * Helper methods for working with byte array instances.
 * 
 * @author mozq
 */
public final class BinaryUtilz {

	private BinaryUtilz() {
		// NOP
	}
	
	public static String toHexStr(byte[] binary) {
		if (binary == null) {
			return null;
		}
		if (binary.length == 0) {
			return "";
		}
		
		int len = binary.length;
		
		StringBuilder sb = new StringBuilder(len * 2);
		for (byte b : binary) {
			int high = ((b >>> 4) & 0xF);
			int low = (b & 0xF);
			if (high < 10) {
				sb.append((char)('0' + high));
			} else {
				sb.append((char)('A' + (high - 10)));
			}
			if (low < 10) {
				sb.append((char)('0' + low));
			} else {
				sb.append((char)('A' + (low - 10)));
			}
		}
		return sb.toString();
	}

	public static byte[] parseHexStr(String hexStr) {
		int len = hexStr.length();
		byte[] binValue = new byte[len / 2];
		int binIdx = 0;
		for (int i = 0; i < len; ) {
			int b = 0;
			if (i != 0 || len % 2 == 0) {
				b = hexCharToInt(hexStr.charAt(i++)) << 4;
			}
			b = b | hexCharToInt(hexStr.charAt(i++));
			binValue[binIdx++] = (byte)b;
		}
		
		return binValue;
	}

	public static int hexCharToInt(char ch) {
		if ('0' <= ch && ch <= '9') {
			return (ch - '0');
		} else if ('A' <= ch && ch <= 'F') {
			return (ch - 'A' + 10);
		} else if ('a' <= ch && ch <= 'f') {
			return (ch - 'a' + 10);
		} else {
			throw new IllegalArgumentException("'" + ch + "'");
		}
	}
}
