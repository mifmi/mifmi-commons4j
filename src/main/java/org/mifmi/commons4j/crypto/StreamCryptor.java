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

public interface StreamCryptor {
	
	InputStream encrypt(InputStream is);
	
	InputStream decrypt(InputStream encryptedIs);
	
	OutputStream encrypt(OutputStream os);
	
	OutputStream decrypt(OutputStream encryptedOs);
}
