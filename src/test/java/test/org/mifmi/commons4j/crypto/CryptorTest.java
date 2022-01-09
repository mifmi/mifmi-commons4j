/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mifmi.commons4j.crypto.AESCryptor;
import org.mifmi.commons4j.crypto.BlowfishCryptor;
import org.mifmi.commons4j.crypto.Cryptor;
import org.mifmi.commons4j.crypto.DESedeCryptor;
import org.mifmi.commons4j.io.StreamUtilz;

public class CryptorTest {

	@Test
	public void testSampleCode() throws Exception {
		// AES with Binary
		{
			Cryptor cryptor = new AESCryptor(createKey(128), "ECB", "PKCS5Padding");

			byte[] val = new byte[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
			byte[] encVal = cryptor.encrypt(val);
			byte[] decVal = cryptor.decrypt(encVal);
			assertArrayEquals(val, decVal);
		}
		
		// Blowfish with Binary
		{
			Cryptor cryptor = new BlowfishCryptor(createKey(128));
			
			byte[] val = new byte[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
			byte[] encVal = cryptor.encrypt(val);
			byte[] decVal = cryptor.decrypt(encVal);
			assertArrayEquals(val, decVal);
		}

		// DESede with Binary
		{
			Cryptor cryptor = new DESedeCryptor(createKey(192), "ECB", "PKCS5Padding");
			
			byte[] val = new byte[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
			byte[] encVal = cryptor.encrypt(val);
			byte[] decVal = cryptor.decrypt(encVal);
			assertArrayEquals(val, decVal);
		}

		// DESede with InputStream
		{
			Cryptor cryptor = new DESedeCryptor(createKey(192), "ECB", "PKCS5Padding");
			
			byte[] val = new byte[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
			InputStream encVal = cryptor.encrypt(new ByteArrayInputStream(val));
			InputStream decVal = cryptor.decrypt(encVal);
			assertArrayEquals(val, StreamUtilz.asByteArray(decVal, 1024));
		}

		// DESede with OutputStream
		{
			Cryptor cryptor = new DESedeCryptor(createKey(192), "ECB", "PKCS5Padding");
			
			byte[] val = new byte[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
			ByteArrayOutputStream encVal = new ByteArrayOutputStream(1024);
			try (OutputStream encTemp = cryptor.encrypt(encVal)) {
				encTemp.write(val);
			}
			ByteArrayOutputStream decVal = new ByteArrayOutputStream(1024);
			try (OutputStream decTemp = cryptor.decrypt(decVal)) {
				decTemp.write(encVal.toByteArray());
			}
			assertArrayEquals(val, decVal.toByteArray());
		}
	}
	
	private static byte[] createKey(int bits) {
		byte[] key = new byte[bits / 8];
		
		Random random = new Random(System.currentTimeMillis());
		random.nextBytes(key);
		
		return key;
	}
}
