/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mifmi.commons4j.config.OrderedProperties;

public class OrderedPropertiesTest {

	@Test
	public void testSampleCode() throws Exception {
		OrderedProperties props = new OrderedProperties();
		props.setProperty("testkey3", "testvalue3");
		props.setProperty("testkey2", "testvalue2");
		props.setProperty("testkey1", "testvalue1");
		
		assertEquals("testvalue3", props.getProperty("testkey3"));
		assertEquals("testvalue2", props.getProperty("testkey2"));
		assertEquals("testvalue1", props.getProperty("testkey1"));
		
		int i = 0;
		for (String name : props.stringPropertyNames()) {
			switch (i) {
			case 0: assertEquals("testkey3", name); break;
			case 1: assertEquals("testkey2", name); break;
			case 2: assertEquals("testkey1", name); break;
			}
			i++;
		}
	}
}
