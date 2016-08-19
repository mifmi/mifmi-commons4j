/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

import org.mifmi.commons4j.valuefilter.ValueFilter;

public abstract class AbstractConverter<T, U> implements Converter<T, U>, ValueFilter<T, U> {

	public <R extends U> R filter(T value) {
		return convert(value);
	}
	
	public Object filterObject(Object value) {
		return convertObject(value);
	}

	protected <R extends U> R cast(Object obj) {
		@SuppressWarnings("unchecked")
		R r = (R)obj;
		return r;
	}
}
