/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.crypto;

import java.nio.charset.Charset;

public interface StringCryptor {

	byte[] encrypt(String value, Charset charset);
	
	String decrypt(byte[] encryptedValue, Charset charset);
}
