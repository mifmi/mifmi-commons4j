/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.util.exception;

/**
 * Signals that an error has been reached unexpectedly while parsing.
 * 
 * @author mozq
 */
public class NumberParseException extends RuntimeException {
	private static final long serialVersionUID = -5869188110424556164L;

	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public NumberParseException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public NumberParseException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause.
	 * 
	 * @param cause the cause (A null value is permitted.)
	 */
	public NumberParseException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause (A null value is permitted.)
	 */
	public NumberParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
