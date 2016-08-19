/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatValidator extends AbstractValidator<Object> {
	
	public static final String VR_NOT_VALID = createResultValue(DateFormatValidator.class, "NOT_VALID");

	private String pattern;
	private boolean lenient;
	
	private DateFormat dateFormat = null;
	
	public DateFormatValidator(String pattern) {
		this(pattern, false);
	}
	
	public DateFormatValidator(String pattern, boolean lenient) {
		this.pattern = pattern;
		this.lenient = lenient;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isLenient() {
		return lenient;
	}

	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{this.pattern, this.lenient};
	}

	@Override
	public String doValidate(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof Date) {
			return null;
		}
		
		String strValue = value.toString();
		try {
			getDateFormat().parse(strValue);
		} catch (ParseException e) {
			return VR_NOT_VALID;
		}
		
		return null;
	}

	private DateFormat getDateFormat() {
		if (this.dateFormat == null) {
			this.dateFormat = new SimpleDateFormat(this.pattern);
			this.dateFormat.setLenient(this.lenient);
		}
		return this.dateFormat;
	}

	@Override
	public String toString() {
		return "pattern=" + this.pattern + ", lenient=" + this.lenient;
	}
}
