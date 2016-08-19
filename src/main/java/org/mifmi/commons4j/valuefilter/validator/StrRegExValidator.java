/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

import java.util.regex.Pattern;

public class StrRegExValidator extends AbstractValidator<Object> {
	
	public static final String VR_NOT_VALID = createResultValue(StrRegExValidator.class, "NOT_VALID");

	private String regex;
	private int flags;
	private Pattern pattern;

	public StrRegExValidator(String regex) {
		this(regex, 0);
	}
	
	public StrRegExValidator(String regex, int flags) {
		this.regex = regex;
		this.flags = flags;
		this.pattern = Pattern.compile(regex, flags);
	}

	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{ this.regex, this.flags };
	}

	@Override
	public String doValidate(Object value) {
		if (value == null) {
			return null;
		}

		CharSequence charSeq;
		if (value instanceof CharSequence) {
			charSeq = (CharSequence)value;
		} else {
			charSeq = value.toString();
		}
		
		if (!this.pattern.matcher(charSeq).matches()) {
			return VR_NOT_VALID;
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "regex=" + this.regex + ", flags=" + this.flags;
	}
}
