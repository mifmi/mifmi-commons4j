/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

public class ConfigParseException extends ConfigException {
	private static final long serialVersionUID = -5443366682721618770L;

	public ConfigParseException() {
		super();
	}

	public ConfigParseException(String message) {
		super(message);
	}

	public ConfigParseException(Throwable cause) {
		super(cause);
	}

	public ConfigParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigParseException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
