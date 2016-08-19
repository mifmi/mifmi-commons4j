/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter;

public class ValueFilterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValueFilterException() {
	}

	public ValueFilterException(String message) {
		super(message);
	}

	public ValueFilterException(Throwable cause) {
		super(cause);
	}

	public ValueFilterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValueFilterException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
