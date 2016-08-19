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

public class StrLineBreakConverter extends AbstractConverter<CharSequence, CharSequence> {

	public static final String CR = "\r";
	public static final String LF = "\n";
	public static final String CRLF = "\r\n";
	
	private static final char[] LB_CHARS = {'\r', '\n'};
	
	private String replacingStr;
	
	public StrLineBreakConverter() {
		this(CRLF);
	}
	public StrLineBreakConverter(String replacingStr) {
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
		
		int idx = StringUtilz.indexOf(charSeq, LB_CHARS);
		if (idx == -1) {
			return cast(charSeq);
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
					int lbLen = 1;
					switch (c) {
					case '\r':
						int ni = i + 1;
						if (ni < len) {
							char nc = charSeq.charAt(ni);
							if (nc == '\n') {
								lbLen = 2;
							}
						}
						// FALLTHRU
					case '\n':
						if (repLen == 0) {
							sb.deleteCharAt(i);
							i += lbLen - 1;
						} else {
							sb.replace(i, i + lbLen - 1, this.replacingStr);
							i += repLen - lbLen;
						}
						break;
					default:
						// NOP
						break;
					}
				}
				return sb;
			} else if (charSeq instanceof StringBuffer) {
				StringBuffer sb = (StringBuffer)charSeq;
				int len = charSeq.length();
				int repLen = this.replacingStr.length();
				for (int i = idx; i < len; i++) {
					char c = charSeq.charAt(i);
					int lbLen = 1;
					switch (c) {
					case '\r':
						int ni = i + 1;
						if (ni < len) {
							char nc = charSeq.charAt(ni);
							if (nc == '\n') {
								lbLen = 2;
							}
						}
						// FALLTHRU
					case '\n':
						if (repLen == 0) {
							sb.deleteCharAt(i);
							i += lbLen - 1;
						} else {
							sb.replace(i, i + lbLen - 1, this.replacingStr);
							i += repLen - lbLen;
						}
						break;
					default:
						// NOP
						break;
					}
				}
				return sb;
			} else {
				int len = charSeq.length();
				StringBuilder sb = new StringBuilder(len);
				for (int i = 0; i < len; i++) {
					char c = charSeq.charAt(i);
					if (c == '\r') {
						int ni = i + 1;
						if (ni < len) {
							char nc = charSeq.charAt(ni);
							if (nc == '\n') {
								i = ni;
							}
						}
						sb.append(this.replacingStr);
					} else if (c == '\n') {
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
		return "replacingStr=" + this.replacingStr;
	}
}
