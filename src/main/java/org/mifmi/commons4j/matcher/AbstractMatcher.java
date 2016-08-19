/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

public abstract class AbstractMatcher<T> implements IMatcher<T> {

	public AbstractMatcher() {
	}

	@Override
	public abstract boolean matches(T value);

	public IMatcher<T> and(IMatcher<T> matcher) {
		return new AndMatcher<>(this, matcher);
	}

	public IMatcher<T> or(IMatcher<T> matcher) {
		return new OrMatcher<>(this, matcher);
	}

	public IMatcher<T> not() {
		return new NotMatcher<>(this);
	}
}
