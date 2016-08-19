/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.datastore;

public class DatastoreException extends RuntimeException {

	private static final long serialVersionUID = -1181368513219353490L;

	public DatastoreException() {
		super();
	}

	public DatastoreException(String message) {
		super(message);
	}

	public DatastoreException(Throwable cause) {
		super(cause);
	}

	public DatastoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatastoreException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
