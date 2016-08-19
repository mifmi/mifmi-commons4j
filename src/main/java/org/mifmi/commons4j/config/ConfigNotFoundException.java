/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

public class ConfigNotFoundException extends ConfigException {
	private static final long serialVersionUID = 6589723344508248001L;

	public ConfigNotFoundException() {
		super();
	}

	public ConfigNotFoundException(String message) {
		super(message);
	}

	public ConfigNotFoundException(Throwable cause) {
		super(cause);
	}

	public ConfigNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
