/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

public class StrLowerCaseConverter extends AbstractConverter<CharSequence, CharSequence> {
	
	public StrLowerCaseConverter() {
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
		
		if (charSeq instanceof StringBuilder) {
			StringBuilder sb = (StringBuilder)charSeq;
			int len = charSeq.length();
			for (int i = 0; i < len; i++) {
				char c = charSeq.charAt(i);
				if (Character.isHighSurrogate(c)) {
					int cp = Character.codePointAt(sb, i);
					cp = Character.toLowerCase(cp);
					for (char ch : Character.toChars(cp)) {
						sb.setCharAt(i, ch);
						i++;
					}
					i--;
				} else {
					c = Character.toLowerCase(c);
					sb.setCharAt(i, c);
				}
			}
			return sb;
		} else if (charSeq instanceof StringBuffer) {
			StringBuffer sb = (StringBuffer)charSeq;
			int len = charSeq.length();
			for (int i = 0; i < len; i++) {
				char c = charSeq.charAt(i);
				if (Character.isHighSurrogate(c)) {
					int cp = Character.codePointAt(sb, i);
					cp = Character.toLowerCase(cp);
					for (char ch : Character.toChars(cp)) {
						sb.setCharAt(i, ch);
						i++;
					}
					i--;
				} else {
					c = Character.toLowerCase(c);
					sb.setCharAt(i, c);
				}
			}
			return sb;
		} else {
			int len = charSeq.length();
			StringBuilder sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				char c = charSeq.charAt(i);
				if (Character.isHighSurrogate(c)) {
					int cp = Character.codePointAt(sb, i);
					cp = Character.toLowerCase(cp);
					for (char ch : Character.toChars(cp)) {
						sb.append(ch);
						i++;
					}
					i--;
				} else {
					c = Character.toLowerCase(c);
					sb.append(c);
				}
			}
			return sb.toString();
		}
	}
}
