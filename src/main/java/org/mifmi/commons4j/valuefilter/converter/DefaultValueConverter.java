/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

public class DefaultValueConverter<T> extends AbstractConverter<T, T> {

	private T defaultValue;
	
	public DefaultValueConverter(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public <R extends T> R convert(T value) {
		if (value == null) {
			return cast(this.defaultValue);
		}
		
		return cast(value);
	}
	
	public Object convertObject(Object value) {
		if (value == null) {
			return this.defaultValue;
		}
		
		return value;
	}

}
