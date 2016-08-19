/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mifmi.commons4j.io.file.FileUtilz;

public class FileUtilzTest {
	@Test
	public void testGetPath() throws Exception {
		assertEquals("/base/path/sub/path", FileUtilz.getPath("/base/path/", "sub/path", '/'));
		assertEquals("/base/path/sub/path", FileUtilz.getPath("/base/path/", "/sub/path", '/'));
		assertEquals("/base/path/sub/path", FileUtilz.getPath("/base/path", "sub/path", '/'));
		assertEquals("/base/path/sub/path", FileUtilz.getPath("/base/path", "/sub/path", '/'));

		assertEquals("/base/path/./sub/path", FileUtilz.getPath("/base/path/", "./sub/path", '/'));
		assertEquals("/base/path/../sub/path", FileUtilz.getPath("/base/path/", "../sub/path", '/'));
		assertEquals("/base/path/.sub/path", FileUtilz.getPath("/base/path/", ".sub/path", '/'));
		
		assertEquals("/base/path//sub/path", FileUtilz.getPath("/base/path/", "//sub/path", '/'));
		assertEquals("/base/path///sub/path", FileUtilz.getPath("/base/path/", "///sub/path", '/'));
	}
	
	@Test
	public void testGetCanonicalPath() throws Exception {
		
		assertEquals("/base/path/.sub/path", FileUtilz.getCanonicalPath("/base/path/.sub/path", '/'));
		assertEquals("/base/path/..sub/path", FileUtilz.getCanonicalPath("/base/path/..sub/path", '/'));
		assertEquals("/base/path/sub./path", FileUtilz.getCanonicalPath("/base/path/sub./path", '/'));
		assertEquals("/base/path/sub../path", FileUtilz.getCanonicalPath("/base/path/sub../path", '/'));

		assertEquals("/base/path//sub/path", FileUtilz.getCanonicalPath("/base/path//sub/path", '/'));
		assertEquals("/base/path///sub/path", FileUtilz.getCanonicalPath("/base/path///sub/path", '/'));
		
		assertEquals("/base/path/sub/path", FileUtilz.getCanonicalPath("/base/path/./sub/path", '/'));
		assertEquals("/base/path/sub/path", FileUtilz.getCanonicalPath("/base/path/././sub/path", '/'));
		assertEquals("/base/sub/path", FileUtilz.getCanonicalPath("/base/path/../sub/path", '/'));
		assertEquals("/sub/path", FileUtilz.getCanonicalPath("/base/path/../../sub/path", '/'));
		assertEquals("/base/sub/path", FileUtilz.getCanonicalPath("/base/path/./.././sub/path", '/'));
		assertEquals("/sub/path", FileUtilz.getCanonicalPath("/base/path/./../.././sub/path", '/'));
		
		assertEquals("/base/path/", FileUtilz.getCanonicalPath("/base/path/.", '/'));
		assertEquals("/base/path/", FileUtilz.getCanonicalPath("/base/path/./.", '/'));
		assertEquals("/base/", FileUtilz.getCanonicalPath("/base/path/..", '/'));
		assertEquals("/", FileUtilz.getCanonicalPath("/base/path/../..", '/'));
		assertEquals("/base/", FileUtilz.getCanonicalPath("/base/path/./../.", '/'));
		assertEquals("/", FileUtilz.getCanonicalPath("/base/path/./../../.", '/'));

		assertEquals("/base/path/", FileUtilz.getCanonicalPath("/base/path/./", '/'));
		assertEquals("/base/path/", FileUtilz.getCanonicalPath("/base/path/././", '/'));
		assertEquals("/base/", FileUtilz.getCanonicalPath("/base/path/../", '/'));
		assertEquals("/", FileUtilz.getCanonicalPath("/base/path/../../", '/'));
		assertEquals("/base/", FileUtilz.getCanonicalPath("/base/path/./.././", '/'));
		assertEquals("/", FileUtilz.getCanonicalPath("/base/path/./../.././", '/'));

		assertEquals("", FileUtilz.getCanonicalPath("/base/path/../../../", '/')); // FIXME
		assertEquals("", FileUtilz.getCanonicalPath("/base/path/../../../../", '/')); // FIXME
		
		assertEquals("base/path/sub/path", FileUtilz.getCanonicalPath("base/path/sub/path", '/'));
		assertEquals("base/path/sub/path", FileUtilz.getCanonicalPath("base/path/./sub/path", '/'));
		assertEquals("base/path/sub/path", FileUtilz.getCanonicalPath("base/path/././sub/path", '/'));
		assertEquals("base/sub/path", FileUtilz.getCanonicalPath("base/path/../sub/path", '/'));
		assertEquals("sub/path", FileUtilz.getCanonicalPath("base/path/../../sub/path", '/'));
		assertEquals("base/sub/path", FileUtilz.getCanonicalPath("base/path/./.././sub/path", '/'));
		assertEquals("sub/path", FileUtilz.getCanonicalPath("base/path/./../.././sub/path", '/'));

		assertEquals("base/path/", FileUtilz.getCanonicalPath("base/path/.", '/'));
		assertEquals("base/path/", FileUtilz.getCanonicalPath("base/path/./.", '/'));
		assertEquals("base/", FileUtilz.getCanonicalPath("base/path/..", '/'));
		assertEquals("", FileUtilz.getCanonicalPath("base/path/../..", '/'));
		assertEquals("base/", FileUtilz.getCanonicalPath("base/path/./../.", '/'));
		assertEquals("", FileUtilz.getCanonicalPath("base/path/./../../.", '/'));

		assertEquals("base/path/", FileUtilz.getCanonicalPath("base/path/./", '/'));
		assertEquals("base/path/", FileUtilz.getCanonicalPath("base/path/././", '/'));
		assertEquals("base/", FileUtilz.getCanonicalPath("base/path/../", '/'));
		assertEquals("", FileUtilz.getCanonicalPath("base/path/../../", '/'));
		assertEquals("base/", FileUtilz.getCanonicalPath("base/path/./.././", '/'));
		assertEquals("", FileUtilz.getCanonicalPath("base/path/./../.././", '/'));

		assertEquals("", FileUtilz.getCanonicalPath("base/path/../../../", '/')); // FIXME
		assertEquals("", FileUtilz.getCanonicalPath("base/path/../../../../", '/')); // FIXME
	}
}
