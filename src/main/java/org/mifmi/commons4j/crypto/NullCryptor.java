/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.crypto;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class NullCryptor implements Cryptor {

	public NullCryptor() {
	}

	public byte[] encrypt(String value, Charset charset) {
		return value.getBytes(charset);
	}

	public String decrypt(byte[] encryptedValue, Charset charset) {
		return new String(encryptedValue, charset);
	}

	public byte[] encrypt(byte[] value) {
		return value;
	}

	public byte[] decrypt(byte[] encryptedValue) {
		return encryptedValue;
	}

	public void encrypt(byte[] value, int offset, int len, byte[] output, int outputOffset) {
		System.arraycopy(value, offset, output, outputOffset, len);
	}

	public void decrypt(byte[] encryptedValue, int offset, int len, byte[] output, int outputOffset) {
		System.arraycopy(encryptedValue, offset, output, outputOffset, len);
	}

	public InputStream encrypt(InputStream is) {
		return is;
	}

	public InputStream decrypt(InputStream encryptedIs) {
		return encryptedIs;
	}

	public OutputStream encrypt(OutputStream os) {
		return os;
	}

	public OutputStream decrypt(OutputStream encryptedOs) {
		return encryptedOs;
	}

}
