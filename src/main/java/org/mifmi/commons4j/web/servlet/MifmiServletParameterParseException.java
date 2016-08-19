/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.servlet;

public class MifmiServletParameterParseException extends MifmiServletException {

	private static final long serialVersionUID = 1L;
	
	public MifmiServletParameterParseException() {
	}

	public MifmiServletParameterParseException(String message) {
		super(message);
	}

	public MifmiServletParameterParseException(Throwable cause) {
		super(cause);
	}

	public MifmiServletParameterParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
