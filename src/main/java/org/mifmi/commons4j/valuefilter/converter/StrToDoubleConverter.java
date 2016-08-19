/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

public class StrToDoubleConverter extends StrToNumberConverter<Double> {

	public StrToDoubleConverter(String pattern) {
		super(pattern, Double.class);
	}
}
