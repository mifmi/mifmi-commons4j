/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public final class StreamUtilz {

	private StreamUtilz() {
	}
	
	public static byte[] asByteArray(InputStream in, int length) throws IOException {
		byte[] bytes = new byte[length];
		
		int idx = 0;
		int totalLength = 0;
		int readLength = 0;
		while ((readLength = in.read(bytes, idx, length - idx)) != -1) {
			idx += readLength;
			totalLength += readLength;
		}
		if (totalLength < length) {
			bytes = Arrays.copyOf(bytes, totalLength);
		}
		
		return bytes;
	}
}
