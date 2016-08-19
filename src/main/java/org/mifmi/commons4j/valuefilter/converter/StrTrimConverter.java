/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

import java.util.Arrays;

public class StrTrimConverter extends AbstractConverter<CharSequence, CharSequence> {
	
	private char[] extBlankChars;
	
	public StrTrimConverter(char... extBlankChars) {
		this.extBlankChars = extBlankChars;
	}
	
	public char[] getExtBlankChars() {
		return this.extBlankChars;
	}

	public <R extends CharSequence> R convert(CharSequence value) {
		return cast(convertRaw(value));
	}
	
	public Object convertObject(Object value) {
		
		CharSequence charSeq;
		if (value instanceof CharSequence) {
			charSeq = (CharSequence)value;
		} else {
			charSeq = value.toString();
		}
		
		return convertRaw(charSeq);
	}

	private CharSequence convertRaw(CharSequence value) {
		if (value == null) {
			return null;
		}
		
		CharSequence charSeq = value;
		
		if (charSeq.length() == 0) {
			return charSeq;
		}
		int idx = indexOfNotBlankChar(charSeq, this.extBlankChars);
		if (idx == -1) {
			return charSeq.subSequence(0, 0);
		}
		int lastIdx = lastIndexOfNotBlankChar(charSeq, this.extBlankChars);
		return charSeq.subSequence(idx, lastIdx + 1);
	}

	private static boolean isBlankChar(char c, char... extBlankChars) {
		if (c <= '\u0020') {
			return true;
		}
		if (extBlankChars != null) {
			for (char blankChar : extBlankChars) {
				if (c == blankChar) {
					return true;
				}
			}
		}
		return false;
	}

	private static int indexOfNotBlankChar(CharSequence charSeq, char... extBlankChars) {
		int len = charSeq.length();
		for (int i = 0; i < len; i++) {
			char c = charSeq.charAt(i);
			if (!isBlankChar(c, extBlankChars)) {
				return i;
			}
		}
		return -1;
	}

	private static int lastIndexOfNotBlankChar(CharSequence charSeq, char... extBlankChars) {
		int len = charSeq.length();
		for (int i = len - 1; 0 <= i; i--) {
			char c = charSeq.charAt(i);
			if (!isBlankChar(c, extBlankChars)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return "extBlankChars=" + Arrays.toString(this.extBlankChars);
	}
}
