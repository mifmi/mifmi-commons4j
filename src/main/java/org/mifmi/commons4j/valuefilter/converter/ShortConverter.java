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

public class ShortConverter extends AbstractConverter<Object, Short> {

	public ShortConverter() {
	}

	public <R extends Short> R convert(Object value) {
		return cast(convertRaw(value));
	}
	
	public Object convertObject(Object value) {
		return convertRaw(value);
	}
	
	private Short convertRaw(Object value) {
		if (value == null) {
			return null;
		}
		
		return NumberUtilz.toShortObject(value);
	}
}
