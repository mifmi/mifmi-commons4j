/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

import org.mifmi.commons4j.util.NumberUtilz;

public class StrToNumberConverter<T extends Number> extends AbstractConverter<CharSequence, T> {

	private String pattern;
	private Class<T> numberClass;
	
	public StrToNumberConverter(String pattern, Class<T> numberClass) {
		this.pattern = pattern;
		this.numberClass = numberClass;
	}

	public <R extends T> R convert(CharSequence value) {
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

	private Number convertRaw(CharSequence value) {
		if (value == null) {
			return null;
		}
		
		String strValue = value.toString();
		if (strValue.length() == 0) {
			return null;
		}
		
		return NumberUtilz.parseNumber(strValue, this.pattern, this.numberClass);
	}

	@Override
	public String toString() {
		return "pattern=" + this.pattern + ", numberClass=" + this.numberClass;
	}
}
