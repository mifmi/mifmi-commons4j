/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.crypto;

import javax.crypto.spec.PBEKeySpec;

public class PBECryptor extends SecretKeyCryptor {
	
	private static final String ALGORITHM = "PBEWithMD5AndDES";

	public PBECryptor(String key) {
		this(key.toCharArray());
	}
	
	public PBECryptor(String key, byte[] salt, int iterationCount) {
		this(key.toCharArray(), salt, iterationCount);
	}
	
	public PBECryptor(char[] key) {
		super(ALGORITHM, createSecretKey(ALGORITHM, new PBEKeySpec(key)), null);
	}
	
	public PBECryptor(char[] key, byte[] salt, int iterationCount) {
		super(ALGORITHM, createSecretKey(ALGORITHM, new PBEKeySpec(key, salt, iterationCount)), null);
	}
}
