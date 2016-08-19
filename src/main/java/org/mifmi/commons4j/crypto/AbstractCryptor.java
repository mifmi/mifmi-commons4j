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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

public abstract class AbstractCryptor implements Cryptor, StringCryptor, BinaryCryptor, StreamCryptor {
	
	private Charset stringCharset;

	public AbstractCryptor() {
	}
	
	public abstract Cipher getEncryptionCipher();

	public abstract Cipher getDecryptionCipher();

	
	public byte[] encrypt(String value, Charset charset) {
		return encrypt(stringToBinary(value, charset));
	}

	public String decrypt(byte[] encryptedValue, Charset charset) {
		return binaryToString(decrypt(encryptedValue), charset);
	}
	

	public byte[] encrypt(byte[] value) {
		byte[] encryptedValue;
		try {
			encryptedValue = getEncryptionCipher().doFinal(value);
		} catch (IllegalBlockSizeException e) {
			throw new CryptionException(e);
		} catch (BadPaddingException e) {
			throw new CryptionException(e);
		}
		return encryptedValue;
	}

	public byte[] decrypt(byte[] encryptedValue) {
		byte[] value;
		try {
			value = getDecryptionCipher().doFinal(encryptedValue);
		} catch (IllegalBlockSizeException e) {
			throw new CryptionException(e);
		} catch (BadPaddingException e) {
			throw new CryptionException(e);
		}
		return value;
	}
	

	public void encrypt(byte[] value, int offset, int len, byte[] output, int outputOffset) {
		try {
			getEncryptionCipher().doFinal(value, offset, len, output, outputOffset);
		} catch (ShortBufferException e) {
			throw new CryptionException(e);
		} catch (IllegalBlockSizeException e) {
			throw new CryptionException(e);
		} catch (BadPaddingException e) {
			throw new CryptionException(e);
		}
	}

	public void decrypt(byte[] encryptedValue, int offset, int len, byte[] output, int outputOffset) {
		try {
			getDecryptionCipher().doFinal(encryptedValue, offset, len, output, outputOffset);
		} catch (ShortBufferException e) {
			throw new CryptionException(e);
		} catch (IllegalBlockSizeException e) {
			throw new CryptionException(e);
		} catch (BadPaddingException e) {
			throw new CryptionException(e);
		}
	}
	

	public InputStream encrypt(InputStream is) {
		return new CipherInputStream(is, getEncryptionCipher());
	}

	public InputStream decrypt(InputStream encryptedIs) {
		return new CipherInputStream(encryptedIs, getDecryptionCipher());
	}
	

	public OutputStream encrypt(OutputStream os) {
		return new CipherOutputStream(os, getEncryptionCipher());
	}

	public OutputStream decrypt(OutputStream encryptedOs) {
		return new CipherOutputStream(encryptedOs, getDecryptionCipher());
	}
	

	public Charset getStringCharset() {
		return stringCharset;
	}

	public void setStringCharset(Charset stringCharset) {
		this.stringCharset = stringCharset;
	}

	
	protected static String toTransformation(String algorithm, String mode, String padding) {
		if (mode == null) {
			return algorithm;
		}
		
		if (padding == null) {
			padding = "NoPadding";
		}
		
		return algorithm + "/" + mode + "/" + padding;
	}
	
	protected static String getAlgorithm(String transformation) {
		if (transformation == null) {
			return null;
		}
		
		String algorithm;
		int idx = transformation.indexOf('/');
		if (idx == -1) {
			algorithm = transformation;
		} else {
			algorithm = transformation.substring(0, idx);
		}
		
		return algorithm;
	}

	protected static byte[] stringToBinary(String value, Charset charset) {
		if (value == null) {
			return null;
		}
		
		byte[] binaryValue = value.getBytes(charset);
		
		return binaryValue;
	}

	protected static String binaryToString(byte[] binaryValue, Charset charset) {
		if (binaryValue == null) {
			return null;
		}
		
		String stringValue = new String(binaryValue, charset);
		
		return stringValue;
	}

	protected static byte[] binaryStringToBinary(String value) {
		if (value == null) {
			return null;
		}
		
		byte[] binaryValue = new byte[value.length() / 2];
		for (int i = 0; i < binaryValue.length; i++) {
			int idx = i * 2;
			binaryValue[i] = (byte)Integer.parseInt(value.substring(idx, idx + 2), 16);
		}
		return binaryValue;
	}

	protected static String binaryToBinaryString(byte[] value) {
		if (value == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder(value.length * 2);
		for (byte b : value) {
			sb.append(Integer.toString((int)((b >> 4) & 0x0F), 16));
			sb.append(Integer.toString((int)(b & 0x0F), 16));
		}
		return sb.toString();
	}
}
