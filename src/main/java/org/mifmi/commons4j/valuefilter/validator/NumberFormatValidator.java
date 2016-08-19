/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberFormatValidator extends AbstractValidator<Object> {
	
	public static final String VR_NOT_VALID = createResultValue(NumberFormatValidator.class, "NOT_VALID");

	private String pattern;
	
	private NumberFormat numberFormat = null;
	
	public NumberFormatValidator(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{this.pattern};
	}

	@Override
	public String doValidate(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof Number) {
			return null;
		}
		
		String strValue = value.toString();
		try {
			getNumberFormat().parse(strValue);
		} catch (ParseException e) {
			return VR_NOT_VALID;
		}
		
		return null;
	}

	private NumberFormat getNumberFormat() {
		if (this.numberFormat == null) {
			this.numberFormat = new DecimalFormat(this.pattern);
		}
		return this.numberFormat;
	}

	@Override
	public String toString() {
		return "pattern=" + this.pattern;
	}
}
