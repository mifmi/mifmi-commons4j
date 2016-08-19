/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

public class SecretKeyCryptor extends AbstractCryptor {

	private String transformation;
	private SecretKey secretKey;
	private AlgorithmParameterSpec parameterSpec;
	
	private Cipher encryptionCipher = null;
	private Cipher decryptionCipher = null;

	public SecretKeyCryptor(String algorithm, String mode, String padding, SecretKey secretKey) {
		this(algorithm, mode, padding, secretKey, null);
	}
	
	public SecretKeyCryptor(String algorithm, String mode, String padding, SecretKey secretKey, AlgorithmParameterSpec parameterSpec) {
		this(toTransformation(algorithm, mode, padding), secretKey, parameterSpec);
	}

	public SecretKeyCryptor(String transformation, SecretKey secretKey) {
		this(transformation, secretKey, null);
	}
	
	public SecretKeyCryptor(String transformation, SecretKey secretKey, AlgorithmParameterSpec parameterSpec) {
		this.transformation = transformation;
		this.secretKey = secretKey;
		this.parameterSpec = parameterSpec;
	}
	
	@Override
	public Cipher getEncryptionCipher() {
		if (this.encryptionCipher == null) {
			try {
				this.encryptionCipher = Cipher.getInstance(this.transformation);
				if (this.parameterSpec == null) {
					this.encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
				} else {
					this.encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey, this.parameterSpec);	
				}
			} catch (NoSuchAlgorithmException e) {
				throw new CryptionException(e);
			} catch (NoSuchPaddingException e) {
				throw new CryptionException(e);
			} catch (InvalidKeyException e) {
				throw new CryptionException(e);
			} catch (InvalidAlgorithmParameterException e) {
				throw new CryptionException(e);
			}
		}
		return this.encryptionCipher;
	}
	
	@Override
	public Cipher getDecryptionCipher() {
		if (this.decryptionCipher == null) {
			try {
				this.decryptionCipher = Cipher.getInstance(this.transformation);
				if (this.parameterSpec == null) {
					this.decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey);
				} else {
					this.decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, this.parameterSpec);
				}
			} catch (NoSuchAlgorithmException e) {
				throw new CryptionException(e);
			} catch (NoSuchPaddingException e) {
				throw new CryptionException(e);
			} catch (InvalidKeyException e) {
				throw new CryptionException(e);
			} catch (InvalidAlgorithmParameterException e) {
				throw new CryptionException(e);
			}
		}
		return this.decryptionCipher;
	}
	
	protected static SecretKey createSecretKey(String algorithm, KeySpec keySpec) {
		try {
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
			return secretKeyFactory.generateSecret(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptionException(e);
		} catch (InvalidKeySpecException e) {
			throw new CryptionException(e);
		}
	}
}
