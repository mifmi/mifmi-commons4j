/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

public class ObjectMatcher<T> extends AbstractMatcher<T> {

	private T pattern;

	public ObjectMatcher(T pattern) {
		this.pattern = pattern;
	}

	@Override
	public Class<? super Object> getType() {
		return Object.class;
	}
	
	@Override
	public boolean matches(T value) {
		if (this.pattern == null) {
			return (value == null);
		}
		if (value == null) {
			return false;
		}

		return this.pattern.equals(value);
	}

	@Override
	public String toString() {
		return "" + this.pattern;
	}
}
