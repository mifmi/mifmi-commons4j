/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.mifmi.commons4j.util.ExceptionUtilz;
import org.mifmi.commons4j.web.servlet.MifmiServletException;

public class ExceptionUtilzTest {

	@Test
	public void testIsCause() throws Exception {
		assertEquals(false, ExceptionUtilz.isCause(null, Exception.class));
		try {
			ExceptionUtilz.isCause(new IllegalArgumentException(), null);
			fail();
		} catch (NullPointerException e) {
			// OK
		}
		
		assertEquals(true, ExceptionUtilz.isCause(new IllegalArgumentException(), IllegalArgumentException.class));
		assertEquals(true, ExceptionUtilz.isCause(new IllegalArgumentException(), RuntimeException.class));
		assertEquals(true, ExceptionUtilz.isCause(new IllegalArgumentException(), Exception.class));
		assertEquals(false, ExceptionUtilz.isCause(new IllegalArgumentException(), Error.class));
		assertEquals(false, ExceptionUtilz.isCause(new IllegalArgumentException(), UnsupportedOperationException.class));
		
		assertEquals(true, ExceptionUtilz.isCause(new IllegalCallerException(new IllegalArgumentException()), IllegalArgumentException.class));
		assertEquals(true, ExceptionUtilz.isCause(new IllegalCallerException(new IllegalArgumentException()), IllegalCallerException.class));
		assertEquals(false, ExceptionUtilz.isCause(new IllegalCallerException(new IllegalArgumentException()), UnsupportedOperationException.class));
		
		assertEquals(true, ExceptionUtilz.isCause(new MifmiServletException(new IllegalCallerException(new IllegalArgumentException())), IllegalArgumentException.class));
		assertEquals(true, ExceptionUtilz.isCause(new MifmiServletException(new IllegalCallerException(new IllegalArgumentException())), IllegalCallerException.class));
		assertEquals(true, ExceptionUtilz.isCause(new MifmiServletException(new IllegalCallerException(new IllegalArgumentException())), MifmiServletException.class));
		assertEquals(false, ExceptionUtilz.isCause(new MifmiServletException(new IllegalCallerException(new IllegalArgumentException())), UnsupportedOperationException.class));
		
		assertEquals(true, ExceptionUtilz.isCause(new OutOfMemoryError(), OutOfMemoryError.class));
		assertEquals(false, ExceptionUtilz.isCause(new OutOfMemoryError(), RuntimeException.class));
		assertEquals(false, ExceptionUtilz.isCause(new OutOfMemoryError(), Exception.class));
		assertEquals(true, ExceptionUtilz.isCause(new OutOfMemoryError(), Error.class));
	}
}
