/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.crypto;

public class CryptionException extends RuntimeException {
	private static final long serialVersionUID = -1963674936300327547L;

	public CryptionException() {
		super();
	}

	public CryptionException(String message) {
		super(message);
	}

	public CryptionException(Throwable cause) {
		super(cause);
	}

	public CryptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public CryptionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
