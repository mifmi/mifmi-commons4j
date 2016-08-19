/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

import org.mifmi.commons4j.util.StringUtilz;

public class StrReplaceCharConverter extends AbstractConverter<CharSequence, CharSequence> {
	
	private char targetChar;
	private String replacingStr;
	
	public StrReplaceCharConverter(char targetChar) {
		this(targetChar, "");
	}
	
	public StrReplaceCharConverter(char targetChar, String replacingStr) {
		this.targetChar = targetChar;
		this.replacingStr = replacingStr;
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
		
		int idx = StringUtilz.indexOf(charSeq, this.targetChar);
		if (idx == -1) {
			return value;
		}
		if (this.replacingStr == null) {
			return charSeq.subSequence(0, idx);
		} else {
			if (charSeq instanceof StringBuilder) {
				StringBuilder sb = (StringBuilder)charSeq;
				int len = charSeq.length();
				int repLen = this.replacingStr.length();
				for (int i = idx; i < len; i++) {
					char c = charSeq.charAt(i);
					if (c == this.targetChar) {
						if (repLen == 0) {
							sb.deleteCharAt(i);
						} else {
							sb.replace(i, i, this.replacingStr);
							i += repLen - 1;
						}
					}
				}
				return sb;
			} else if (charSeq instanceof StringBuffer) {
				StringBuffer sb = (StringBuffer)charSeq;
				int len = charSeq.length();
				int repLen = this.replacingStr.length();
				for (int i = idx; i < len; i++) {
					char c = charSeq.charAt(i);
					if (c == this.targetChar) {
						if (repLen == 0) {
							sb.deleteCharAt(i);
						} else {
							sb.replace(i, i, this.replacingStr);
							i += repLen - 1;
						}
					}
				}
				return sb;
			} else {
				int len = charSeq.length();
				StringBuilder sb = new StringBuilder(len);
				for (int i = 0; i < len; i++) {
					char c = charSeq.charAt(i);
					if (c == this.targetChar) {
						sb.append(this.replacingStr);
					} else {
						sb.append(c);
					}
				}
				return sb.toString();
			}
		}
	}

	@Override
	public String toString() {
		return "targetChar=" + this.targetChar + ", replacingStr=" + this.replacingStr;
	}
}
