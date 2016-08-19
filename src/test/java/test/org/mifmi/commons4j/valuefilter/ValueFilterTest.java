/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.valuefilter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.mifmi.commons4j.valuefilter.EachElementFilter;
import org.mifmi.commons4j.valuefilter.InvalidValueException;
import org.mifmi.commons4j.valuefilter.ValueFilter;
import org.mifmi.commons4j.valuefilter.ValueFilters;
import org.mifmi.commons4j.valuefilter.converter.StrNullCharConverter;
import org.mifmi.commons4j.valuefilter.converter.StrReplaceCharConverter;
import org.mifmi.commons4j.valuefilter.converter.StrToIntegerConverter;
import org.mifmi.commons4j.valuefilter.converter.StrToLongConverter;
import org.mifmi.commons4j.valuefilter.converter.StrUpperCaseConverter;
import org.mifmi.commons4j.valuefilter.validator.NumberRangeValidator;
import org.mifmi.commons4j.valuefilter.validator.RequiredValidator;
import org.mifmi.commons4j.valuefilter.validator.StrLengthValidator;

public class ValueFilterTest {

	@Test
	public void testSampleCode() throws Exception {
		{
			Object value = "Foo Bar\0Baz";
			String ret = new ValueFilters()
					.add(new RequiredValidator())
					.add(new StrLengthValidator(1, 20))
					.add(new StrNullCharConverter(" "))
					.add(new StrUpperCaseConverter())
					.add(new StrReplaceCharConverter('B', "V"))
					.filter(value);
			assertEquals("FOO VAR VAZ", ret);
		}
		
		{
			Object value = "1,234";
			Long ret = new ValueFilters()
					.add(new RequiredValidator())
					.add(new StrToLongConverter("#,##0"))
					.add(new NumberRangeValidator(1, 1234))
					.filter(value);
			assertEquals(Long.valueOf(1234L), ret);
		}
		
		{
			Object value = new Object[]{"123", "456", "789"};
			Object[] ret = new ValueFilters()
					.add(new RequiredValidator())
					.add(new EachElementFilter(new RequiredValidator()))
					.add(new EachElementFilter(new StrLengthValidator(1, 3)))
					.add(new EachElementFilter(new StrToIntegerConverter("#,##0")))
					.filter(value);
			assertArrayEquals(new Object[]{123, 456, 789}, ret);
		}
		
		{
			ValueFilter<?, ?> filterForAssert = null;
			try {
				Object value = "1,234";
				Long ret = new ValueFilters()
						.add(new RequiredValidator())
						.add(new StrToLongConverter("#,##0"))
						.add(filterForAssert = new NumberRangeValidator(1, 1233))
						.filter(value);
				fail();
			} catch (InvalidValueException e) {
				assertEquals(Long.valueOf(1234L), e.getValue());
				assertEquals(null, e.getValueKey());
				assertEquals(filterForAssert, e.getValueFilter());
				assertEquals(NumberRangeValidator.VR_TOO_BIG, e.getValidationResult());
			}
		}
		
		{
			ValueFilter<?, ?> filterForAssert = null;
			try {
				Object value = new Object[]{"123", "4567", "789"};
				Object[] ret = new ValueFilters()
						.add(new RequiredValidator())
						.add(new EachElementFilter(new RequiredValidator()))
						.add(new EachElementFilter(filterForAssert = new StrLengthValidator(1, 3)))
						.add(new EachElementFilter(new StrToIntegerConverter("#,##0")))
						.filter(value);
				fail();
			} catch (InvalidValueException e) {
				assertEquals("4567", e.getValue());
				assertEquals(Integer.valueOf(1), e.getValueKey());
				assertEquals(filterForAssert, e.getValueFilter());
				assertEquals(StrLengthValidator.VR_TOO_LONG, e.getValidationResult());
			}
		}
	}
}
