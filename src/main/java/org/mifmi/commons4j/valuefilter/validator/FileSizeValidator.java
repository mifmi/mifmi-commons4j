/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

import java.io.File;

import javax.servlet.http.Part;

import org.mifmi.commons4j.util.ReflectionUtilz;
import org.mifmi.commons4j.valuefilter.UnsupportedValueTypeException;

public class FileSizeValidator extends AbstractValidator<Object> {
	
	public static final String VR_TOO_SHORT = createResultValue(FileSizeValidator.class, "TOO_SHORT");
	public static final String VR_TOO_LONG = createResultValue(FileSizeValidator.class, "TOO_LONG");;

	private Long min;
	private Long max;
	
	public FileSizeValidator(Long min, Long max) {
		this.min = min;
		this.max = max;
	}
	
	public Long getMin() {
		return min;
	}

	public Long getMax() {
		return max;
	}

	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{this.min, this.max};
	}

	@Override
	public String doValidate(Object value) {
		
		long len;
		if (value == null) {
			len = 0;
		} else if (value instanceof File) {
			File fileValue = (File)value;
			len = fileValue.length();
		} else if (ReflectionUtilz.isExistsClass("javax.servlet.http.Part")
				&& value instanceof Part) {
			Part partValue = (Part)value;
			len = partValue.getSize();
		} else {
			throw new UnsupportedValueTypeException(value, this);
		}
		
		if (this.min != null && len < this.min.longValue()) {
			return VR_TOO_SHORT;
		}
		
		if (this.max != null && this.max.longValue() < len) {
			return VR_TOO_LONG;
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "min=" + this.min + ", max=" + this.max;
	}
}
