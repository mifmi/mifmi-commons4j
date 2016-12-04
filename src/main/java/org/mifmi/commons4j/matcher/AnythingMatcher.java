/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

public class AnythingMatcher<T> extends AbstractMatcher<T> {
	
	public AnythingMatcher() {
	}

	@Override
	public Class<? super T> getType() {
		return Object.class;
	}

	@Override
	public boolean matches(T value) {
		return true;
	}

	@Override
	public String toString() {
		return "*";
	}
}
