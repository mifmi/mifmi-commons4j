/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StrToDateConverter extends AbstractConverter<Object, Date> {

	private String pattern;
	private TimeZone timeZone;
	private boolean lenient;
	
	private DateFormat dateFormat = null;
	
	public StrToDateConverter(String pattern) {
		this(pattern, null);
	}
	
	public StrToDateConverter(String pattern, TimeZone timeZone) {
		this(pattern, timeZone, false);
	}
	
	public StrToDateConverter(String pattern, boolean lenient) {
		this(pattern, null, lenient);
	}
	
	public StrToDateConverter(String pattern, TimeZone timeZone, boolean lenient) {
		this.pattern = pattern;
		this.timeZone = timeZone;
		this.lenient = lenient;
	}

	public <R extends Date> R convert(Object value) {
		return cast(convertRaw(value));
	}
	
	public Object convertObject(Object value) {
		return convertRaw(value);
	}
	
	private Date convertRaw(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof Date) {
			return (Date)value;
		}
		
		String strValue = value.toString();
		if (strValue.length() == 0) {
			return null;
		}
		try {
			return getDateFormat().parse(strValue);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}


	private DateFormat getDateFormat() {
		if (this.dateFormat == null) {
			this.dateFormat = new SimpleDateFormat(this.pattern);
			if (this.timeZone != null) {
				this.dateFormat.setTimeZone(this.timeZone);
			}
			this.dateFormat.setLenient(this.lenient);
		}
		return this.dateFormat;
	}

	@Override
	public String toString() {
		return "pattern=" + this.pattern
				+ ", timeZone=" + this.timeZone
				+ ", lenient=" + this.lenient
				+ ", dateFormat=" + this.dateFormat;
	}
}
