/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.servlet;

public class MifmiServletParameterNotFoundException extends MifmiServletException {

	private static final long serialVersionUID = 1L;
	
	public MifmiServletParameterNotFoundException() {
	}

	public MifmiServletParameterNotFoundException(String message) {
		super(message);
	}

	public MifmiServletParameterNotFoundException(Throwable cause) {
		super(cause);
	}

	public MifmiServletParameterNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
