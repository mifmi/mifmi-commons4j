/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

public class NotMatcher<T> extends AbstractMatcher<T> {
	
	private IMatcher<T> matcher;

	public NotMatcher(IMatcher<T> matcher) {
		this.matcher = matcher;
	}

	@Override
	public Class<? super T> getType() {
		return this.matcher.getType();
	}

	@Override
	public boolean matches(T value) {
		return !this.matcher.matches(value);
	}

	public IMatcher<T> getMatcher() {
		return this.matcher;
	}

	@Override
	public String toString() {
		return "Not " + this.matcher.toString();
	}
}
