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

public class StrToBigDecimalConverter extends StrToNumberConverter<BigDecimal> {

	public StrToBigDecimalConverter(String pattern) {
		super(pattern, BigDecimal.class);
	}
}
