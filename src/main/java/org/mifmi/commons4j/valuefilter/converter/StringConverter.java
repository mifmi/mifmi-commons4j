/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;


public class StringConverter extends AbstractConverter<Object, String> {
	
	public StringConverter() {
	}

	public <R extends String> R convert(Object value) {
		return cast(convertRaw(value));
	}
	
	public Object convertObject(Object value) {
		return convertRaw(value);
	}
	
	private String convertRaw(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return (String)value;
		} else {
			return value.toString();
		}
	}
}
