/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.crypto;

public interface BinaryCryptor {
	
	byte[] encrypt(byte[] value);
	
	byte[] decrypt(byte[] encryptedValue);
	
	void encrypt(byte[] value, int offset, int len, byte[] output, int outputOffset);
	
	void decrypt(byte[] encryptedValue, int offset, int len, byte[] output, int outputOffset);
}
