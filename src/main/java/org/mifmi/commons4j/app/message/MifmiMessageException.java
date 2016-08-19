/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.app.message;

public class MifmiMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Message message = null;
	

	public MifmiMessageException(Message message) {
		super(message.getMessage());
		this.message = message;
	}

	public MifmiMessageException(Message message, Throwable cause) {
		super(message.getMessage(), cause);
		this.message = message;
	}

	public Message getMessageObject() {
		return message;
	}
}
