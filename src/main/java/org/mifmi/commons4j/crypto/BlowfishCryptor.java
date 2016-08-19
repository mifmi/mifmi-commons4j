/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.crypto;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class BlowfishCryptor extends SecretKeyCryptor {
	
	private static final String ALGORITHM = "Blowfish";

	
	public BlowfishCryptor(byte[] key) {
		this(key, null, null);
	}
	
	public BlowfishCryptor(byte[] key, String mode, String padding) {
		this(key, mode, padding, null);
	}
	
	public BlowfishCryptor(byte[] key, String mode, String padding, byte[] iv) {
		super(toTransformation(ALGORITHM, mode, padding), new SecretKeySpec(key, ALGORITHM), newParameterSpec(iv));
	}
	
	private static AlgorithmParameterSpec newParameterSpec(byte[] iv) {
		if (iv == null) {
			return null;
		}
		return new IvParameterSpec(iv);
	}
}
