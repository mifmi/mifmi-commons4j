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

public class StrToArrayConverter extends AbstractConverter<CharSequence, String[]> {

	private String separatorRegex;
	private boolean trim;
	
	public StrToArrayConverter(String separatorRegex, boolean trim) {
		this.separatorRegex = separatorRegex;
		this.trim = trim;
	}

	public String[] convert(CharSequence value) {
		return cast(convertRaw(value));
	}
	
	public Object convertObject(Object value) {
		if (value == null) {
			return null;
		}
		
		CharSequence charSeq;
		if (value instanceof CharSequence) {
			charSeq = (CharSequence)value;
		} else {
			charSeq = value.toString();
		}
		
		return convertRaw(charSeq);
	}

	private String[] convertRaw(CharSequence value) {
		if (value == null) {
			return null;
		}
		
		String strValue = value.toString();
		String[] array = StringUtilz.split(strValue, this.separatorRegex, this.trim);
		
		return array;
	}
}
