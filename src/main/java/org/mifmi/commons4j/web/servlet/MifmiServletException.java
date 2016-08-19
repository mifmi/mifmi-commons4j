/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.servlet;

public class MifmiServletException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MifmiServletException() {
	}

	public MifmiServletException(String message) {
		super(message);
	}

	public MifmiServletException(Throwable cause) {
		super(cause);
	}

	public MifmiServletException(String message, Throwable cause) {
		super(message, cause);
	}

}
