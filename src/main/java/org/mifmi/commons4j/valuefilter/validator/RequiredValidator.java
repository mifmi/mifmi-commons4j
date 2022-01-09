/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import jakarta.servlet.http.Part;

import org.mifmi.commons4j.util.ReflectionUtilz;

public class RequiredValidator extends AbstractValidator<Object> {
	
	public static final String VR_EMPTY = createResultValue(RequiredValidator.class, "EMPTY");;


	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{};
	}

	@Override
	public String doValidate(Object value) {
		
		if (isEmptyValue(value)) {
			return VR_EMPTY;
		}
		
		return null;
	}

	protected static boolean isEmptyValue(Object value) {

		if (value == null) {
			return true;
		} else if (value instanceof String) {
			String strValue = (String)value;
			if (strValue.isEmpty()) {
				return true;
			}
		} else if (value.getClass().isArray()) {
			int len = Array.getLength(value);
			if (len == 0) {
				return true;
			}
		} else if (value instanceof Collection) {
			Collection<?> collectionValue = (Collection<?>)value;
			if (collectionValue.isEmpty()) {
				return true;
			}
		} else if (value instanceof Map) {
			Map<?, ?> mapValue = (Map<?, ?>)value;
			if (mapValue.isEmpty()) {
				return true;
			}
		} else if (ReflectionUtilz.isExistsClass("jakarta.servlet.http.Part")
				&& value instanceof Part) {
			Part partValue = (Part)value;
			if (partValue.getSize() == 0) {
				String contentDiposition = partValue.getHeader("Content-Disposition");
				if (contentDiposition == null
						|| !contentDiposition.contains("filename")
						|| contentDiposition.contains("filename=\"\"")) {
					// when Content-Disposition or filename not set
					return true;
				}
			}
		}
		return false;
	}
}
