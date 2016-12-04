/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

public class BooleanMatcher extends AbstractMatcher<Boolean> {
	
	private Boolean value;

	public BooleanMatcher(Boolean value) {
		this.value = value;
	}

	@Override
	public Class<? super Boolean> getType() {
		return Boolean.class;
	}

	@Override
	public boolean matches(Boolean value) {
		if (this.value == null) {
			return (value == null);
		}
		
		if (value == null) {
			return false;
		}
		
		return this.value.equals(value);
	}

	@Override
	public String toString() {
		return (this.value == null) ? "null" : this.value.toString();
	}
}