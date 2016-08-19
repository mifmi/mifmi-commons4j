/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

import java.math.BigDecimal;

import org.mifmi.commons4j.util.NumberUtilz;

public class BigDecimalConverter extends AbstractConverter<Object, BigDecimal> {

	public BigDecimalConverter() {
	}

	public <R extends BigDecimal> R convert(Object value) {
		return cast(convertRaw(value));
	}
	
	public Object convertObject(Object value) {
		return convertRaw(value);
	}
	
	private BigDecimal convertRaw(Object value) {
		if (value == null) {
			return null;
		}
		return NumberUtilz.toBigDecimal(value);
	}
}
