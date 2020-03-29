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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.junit.Test;
import org.mifmi.commons4j.util.NumberUtilz;

public class NumberUtilzTest {
	private static final double DELTA = 1e-15;

	@Test
	public void testIsZero() throws Exception {
		assertEquals(true, NumberUtilz.isZero(0));
		assertEquals(true, NumberUtilz.isZero(-0));
		assertEquals(true, NumberUtilz.isZero(0.0F));
		assertEquals(true, NumberUtilz.isZero(-0.0F));
		assertEquals(true, NumberUtilz.isZero(1 / Float.POSITIVE_INFINITY));
		assertEquals(true, NumberUtilz.isZero(1 / Float.NEGATIVE_INFINITY));
		assertEquals(true, NumberUtilz.isZero(0.0D));
		assertEquals(true, NumberUtilz.isZero(-0.0D));
		assertEquals(true, NumberUtilz.isZero(1 / Double.POSITIVE_INFINITY));
		assertEquals(true, NumberUtilz.isZero(1 / Double.NEGATIVE_INFINITY));
		assertEquals(true, NumberUtilz.isZero(BigInteger.valueOf(0)));
		assertEquals(true, NumberUtilz.isZero(BigInteger.valueOf(-0)));
		assertEquals(true, NumberUtilz.isZero(BigDecimal.valueOf(0)));
		assertEquals(true, NumberUtilz.isZero(BigDecimal.valueOf(-0)));
		assertEquals(true, NumberUtilz.isZero(BigDecimal.valueOf(0.0D)));
		assertEquals(true, NumberUtilz.isZero(BigDecimal.valueOf(-0.0D)));
		assertEquals(true, NumberUtilz.isZero(new BigDecimal("+0.0")));
		assertEquals(true, NumberUtilz.isZero(new BigDecimal("-0.0")));

		assertEquals(false, NumberUtilz.isZero(1));
		assertEquals(false, NumberUtilz.isZero(-1));
		assertEquals(false, NumberUtilz.isZero(0.1F));
		assertEquals(false, NumberUtilz.isZero(-0.1F));
		assertEquals(false, NumberUtilz.isZero(0.1D));
		assertEquals(false, NumberUtilz.isZero(-0.1D));
		assertEquals(false, NumberUtilz.isZero(BigInteger.valueOf(1)));
		assertEquals(false, NumberUtilz.isZero(BigInteger.valueOf(-1)));
		assertEquals(false, NumberUtilz.isZero(BigDecimal.valueOf(1)));
		assertEquals(false, NumberUtilz.isZero(BigDecimal.valueOf(-1)));
		assertEquals(false, NumberUtilz.isZero(BigDecimal.valueOf(0.1D)));
		assertEquals(false, NumberUtilz.isZero(BigDecimal.valueOf(-0.1D)));
		assertEquals(false, NumberUtilz.isZero(new BigDecimal("+0.01")));
		assertEquals(false, NumberUtilz.isZero(new BigDecimal("-0.01")));
	}
	
	@Test
	public void testIsNaN() throws Exception {
		assertEquals(true, NumberUtilz.isNaN(Float.NaN));
		assertEquals(true, NumberUtilz.isNaN(Double.NaN));

		assertEquals(false, NumberUtilz.isNaN(Float.NEGATIVE_INFINITY));
		assertEquals(false, NumberUtilz.isNaN(Float.POSITIVE_INFINITY));
		assertEquals(false, NumberUtilz.isNaN(Double.NEGATIVE_INFINITY));
		assertEquals(false, NumberUtilz.isNaN(Double.POSITIVE_INFINITY));
		
		assertEquals(false, NumberUtilz.isNaN(0));
		assertEquals(false, NumberUtilz.isNaN(0.0F));
		assertEquals(false, NumberUtilz.isNaN(0.0D));
		assertEquals(false, NumberUtilz.isNaN(BigInteger.valueOf(0)));
		assertEquals(false, NumberUtilz.isNaN(BigDecimal.valueOf(0.0D)));
		
		assertEquals(false, NumberUtilz.isNaN(1));
		assertEquals(false, NumberUtilz.isNaN(0.1F));
		assertEquals(false, NumberUtilz.isNaN(0.1D));
		assertEquals(false, NumberUtilz.isNaN(BigInteger.valueOf(1)));
		assertEquals(false, NumberUtilz.isNaN(BigDecimal.valueOf(0.1D)));
	}
	
	@Test
	public void testToLong() throws Exception {
		assertEquals((long)0, NumberUtilz.toLong(null));
		
		assertEquals((long)123, NumberUtilz.toLong(Byte.valueOf((byte)123)));
		assertEquals((long)-123, NumberUtilz.toLong(Byte.valueOf((byte)-123)));
		assertEquals((long)0, NumberUtilz.toLong(Byte.valueOf((byte)0)));
		assertEquals((long)Byte.MIN_VALUE, NumberUtilz.toLong(Byte.valueOf(Byte.MIN_VALUE)));
		assertEquals((long)Byte.MAX_VALUE, NumberUtilz.toLong(Byte.valueOf(Byte.MAX_VALUE)));
		
		assertEquals((long)123, NumberUtilz.toLong(Short.valueOf((short)123)));
		assertEquals((long)-123, NumberUtilz.toLong(Short.valueOf((short)-123)));
		assertEquals((long)0, NumberUtilz.toLong(Short.valueOf((short)0)));
		assertEquals((long)Short.MIN_VALUE, NumberUtilz.toLong(Short.valueOf(Short.MIN_VALUE)));
		assertEquals((long)Short.MAX_VALUE, NumberUtilz.toLong(Short.valueOf(Short.MAX_VALUE)));
		
		assertEquals((long)123, NumberUtilz.toLong(Integer.valueOf(123)));
		assertEquals((long)-123, NumberUtilz.toLong(Integer.valueOf(-123)));
		assertEquals((long)0, NumberUtilz.toLong(Integer.valueOf(0)));
		assertEquals((long)Integer.MIN_VALUE, NumberUtilz.toLong(Integer.valueOf(Integer.MIN_VALUE)));
		assertEquals((long)Integer.MAX_VALUE, NumberUtilz.toLong(Integer.valueOf(Integer.MAX_VALUE)));
		
		assertEquals((long)123, NumberUtilz.toLong(Long.valueOf(123L)));
		assertEquals((long)-123, NumberUtilz.toLong(Long.valueOf(-123L)));
		assertEquals((long)0, NumberUtilz.toLong(Long.valueOf(0L)));
		assertEquals((long)Long.MIN_VALUE, NumberUtilz.toLong(Long.valueOf(Long.MIN_VALUE)));
		assertEquals((long)Long.MAX_VALUE, NumberUtilz.toLong(Long.valueOf(Long.MAX_VALUE)));
		
		assertEquals((long)123, NumberUtilz.toLong(Float.valueOf(123.456F)));
		assertEquals((long)-123, NumberUtilz.toLong(Float.valueOf(-123.456F)));
		assertEquals((long)0, NumberUtilz.toLong(Float.valueOf(0.0F)));
		assertEquals((long)Float.MIN_VALUE, NumberUtilz.toLong(Float.valueOf(Float.MIN_VALUE)));
		assertEquals((long)Float.MAX_VALUE, NumberUtilz.toLong(Float.valueOf(Float.MAX_VALUE)));
		
		assertEquals((long)123, NumberUtilz.toLong(Double.valueOf(123.456D)));
		assertEquals((long)-123, NumberUtilz.toLong(Double.valueOf(-123.456D)));
		assertEquals((long)0, NumberUtilz.toLong(Double.valueOf(0.0D)));
		assertEquals((long)Double.MIN_VALUE, NumberUtilz.toLong(Double.valueOf(Double.MIN_VALUE)));
		assertEquals((long)Double.MAX_VALUE, NumberUtilz.toLong(Double.valueOf(Double.MAX_VALUE)));
		
		assertEquals((long)123, NumberUtilz.toLong(BigInteger.valueOf(123L)));
		assertEquals((long)-123, NumberUtilz.toLong(BigInteger.valueOf(-123L)));
		assertEquals((long)0, NumberUtilz.toLong(BigInteger.valueOf(0L)));
		assertEquals((long)Long.MIN_VALUE, NumberUtilz.toLong(BigInteger.valueOf(Long.MIN_VALUE)));
		assertEquals((long)Long.MAX_VALUE, NumberUtilz.toLong(BigInteger.valueOf(Long.MAX_VALUE)));
		assertEquals((long)(Long.MIN_VALUE - 1L), NumberUtilz.toLong(BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE)));
		assertEquals((long)(Long.MAX_VALUE + 1L), NumberUtilz.toLong(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE)));
		
		assertEquals((long)123, NumberUtilz.toLong(BigDecimal.valueOf(123.456D)));
		assertEquals((long)-123, NumberUtilz.toLong(BigDecimal.valueOf(-123.456D)));
		assertEquals((long)0, NumberUtilz.toLong(BigDecimal.valueOf(0.0D)));
		assertEquals((long)Double.MIN_VALUE, NumberUtilz.toLong(BigDecimal.valueOf(Double.MIN_VALUE)));
//FIXME:		assertEquals((long)Double.MAX_VALUE, NumberUtilz.toLong(BigDecimal.valueOf(Double.MAX_VALUE)));

		assertEquals((long)123, NumberUtilz.toLong("123"));
		assertEquals((long)-123, NumberUtilz.toLong("-123"));
//		assertEquals((long)123, NumberUtilz.toLong("123.456"));
//		assertEquals((long)-123, NumberUtilz.toLong("-123.456"));

		assertEquals(1L, NumberUtilz.toLong(Boolean.TRUE));
		assertEquals(0L, NumberUtilz.toLong(Boolean.FALSE));

		assertEquals((long)'a', NumberUtilz.toLong('a'));
		assertEquals((long)'あ', NumberUtilz.toLong('あ'));
		assertEquals((long)0, NumberUtilz.toLong('\u0000'));
		assertEquals((long)Character.MIN_VALUE, NumberUtilz.toLong(Character.MIN_VALUE));
		assertEquals((long)Character.MAX_VALUE, NumberUtilz.toLong(Character.MAX_VALUE));
	}
	
	@Test
	public void testToDouble() throws Exception {
		assertEquals((double)0, NumberUtilz.toDouble(null), DELTA);
		
		assertEquals((double)123, NumberUtilz.toDouble(Byte.valueOf((byte)123)), DELTA);
		assertEquals((double)-123, NumberUtilz.toDouble(Byte.valueOf((byte)-123)), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble(Byte.valueOf((byte)0)), DELTA);
		assertEquals((double)Byte.MIN_VALUE, NumberUtilz.toDouble(Byte.valueOf(Byte.MIN_VALUE)), DELTA);
		assertEquals((double)Byte.MAX_VALUE, NumberUtilz.toDouble(Byte.valueOf(Byte.MAX_VALUE)), DELTA);
		
		assertEquals((double)123, NumberUtilz.toDouble(Short.valueOf((short)123)), DELTA);
		assertEquals((double)-123, NumberUtilz.toDouble(Short.valueOf((short)-123)), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble(Short.valueOf((short)0)), DELTA);
		assertEquals((double)Short.MIN_VALUE, NumberUtilz.toDouble(Short.valueOf(Short.MIN_VALUE)), DELTA);
		assertEquals((double)Short.MAX_VALUE, NumberUtilz.toDouble(Short.valueOf(Short.MAX_VALUE)), DELTA);
		
		assertEquals((double)123, NumberUtilz.toDouble(Integer.valueOf(123)), DELTA);
		assertEquals((double)-123, NumberUtilz.toDouble(Integer.valueOf(-123)), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble(Integer.valueOf(0)), DELTA);
		assertEquals((double)Integer.MIN_VALUE, NumberUtilz.toDouble(Integer.valueOf(Integer.MIN_VALUE)), DELTA);
		assertEquals((double)Integer.MAX_VALUE, NumberUtilz.toDouble(Integer.valueOf(Integer.MAX_VALUE)), DELTA);
		
		assertEquals((double)123, NumberUtilz.toDouble(Long.valueOf(123L)), DELTA);
		assertEquals((double)-123, NumberUtilz.toDouble(Long.valueOf(-123L)), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble(Long.valueOf(0L)), DELTA);
		assertEquals((double)Long.MIN_VALUE, NumberUtilz.toDouble(Long.valueOf(Long.MIN_VALUE)), DELTA);
		assertEquals((double)Long.MAX_VALUE, NumberUtilz.toDouble(Long.valueOf(Long.MAX_VALUE)), DELTA);
		
		assertEquals((double)123.456F, NumberUtilz.toDouble(Float.valueOf(123.456F)), DELTA);
		assertEquals((double)-123.456F, NumberUtilz.toDouble(Float.valueOf(-123.456F)), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble(Float.valueOf(0.0F)), DELTA);
		assertEquals((double)Float.MIN_VALUE, NumberUtilz.toDouble(Float.valueOf(Float.MIN_VALUE)), DELTA);
		assertEquals((double)Float.MAX_VALUE, NumberUtilz.toDouble(Float.valueOf(Float.MAX_VALUE)), DELTA);
		
		assertEquals((double)123.456D, NumberUtilz.toDouble(Double.valueOf(123.456D)), DELTA);
		assertEquals((double)-123.456D, NumberUtilz.toDouble(Double.valueOf(-123.456D)), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble(Double.valueOf(0.0D)), DELTA);
		assertEquals((double)Double.MIN_VALUE, NumberUtilz.toDouble(Double.valueOf(Double.MIN_VALUE)), DELTA);
		assertEquals((double)Double.MAX_VALUE, NumberUtilz.toDouble(Double.valueOf(Double.MAX_VALUE)), DELTA);
		
		assertEquals((double)123, NumberUtilz.toDouble(BigInteger.valueOf(123L)), DELTA);
		assertEquals((double)-123, NumberUtilz.toDouble(BigInteger.valueOf(-123L)), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble(BigInteger.valueOf(0L)), DELTA);
		assertEquals((double)Long.MIN_VALUE, NumberUtilz.toDouble(BigInteger.valueOf(Long.MIN_VALUE)), DELTA);
		assertEquals((double)Long.MAX_VALUE, NumberUtilz.toDouble(BigInteger.valueOf(Long.MAX_VALUE)), DELTA);
//FIXME:		assertEquals((double)(Long.MIN_VALUE - 1L), NumberUtilz.toDouble(BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE)), DELTA);
//FIXME:		assertEquals((double)(Long.MAX_VALUE + 1L), NumberUtilz.toDouble(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE)), DELTA);
		
		assertEquals((double)123.456, NumberUtilz.toDouble(BigDecimal.valueOf(123.456D)), DELTA);
		assertEquals((double)-123.456, NumberUtilz.toDouble(BigDecimal.valueOf(-123.456D)), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble(BigDecimal.valueOf(0.0D)), DELTA);
		assertEquals((double)Double.MIN_VALUE, NumberUtilz.toDouble(BigDecimal.valueOf(Double.MIN_VALUE)), DELTA);
		assertEquals((double)Double.MAX_VALUE, NumberUtilz.toDouble(BigDecimal.valueOf(Double.MAX_VALUE)), DELTA);

		assertEquals((double)123, NumberUtilz.toDouble("123"), DELTA);
		assertEquals((double)-123, NumberUtilz.toDouble("-123"), DELTA);
		assertEquals((double)123.456, NumberUtilz.toDouble("123.456"), DELTA);
		assertEquals((double)-123.456, NumberUtilz.toDouble("-123.456"), DELTA);

		assertEquals(1L, NumberUtilz.toDouble(Boolean.TRUE), DELTA);
		assertEquals(0L, NumberUtilz.toDouble(Boolean.FALSE), DELTA);

		assertEquals((double)'a', NumberUtilz.toDouble('a'), DELTA);
		assertEquals((double)'あ', NumberUtilz.toDouble('あ'), DELTA);
		assertEquals((double)0, NumberUtilz.toDouble('\u0000'), DELTA);
		assertEquals((double)Character.MIN_VALUE, NumberUtilz.toDouble(Character.MIN_VALUE), DELTA);
		assertEquals((double)Character.MAX_VALUE, NumberUtilz.toDouble(Character.MAX_VALUE), DELTA);
	}
	
	@Test
	public void testToEnNumShortScale() throws Exception {
		assertEquals(null, NumberUtilz.toEnNumShortScale((BigDecimal)null, false));
		assertEquals(null, NumberUtilz.toEnNumShortScale((BigDecimal)null, true));
		
		assertEquals("Zero", NumberUtilz.toEnNumShortScale(new BigDecimal("0"), false));
		assertEquals("One", NumberUtilz.toEnNumShortScale(new BigDecimal("1"), false));
		assertEquals("Two", NumberUtilz.toEnNumShortScale(new BigDecimal("2"), false));
		
		assertEquals("Ten", NumberUtilz.toEnNumShortScale(new BigDecimal("10"), false));
		assertEquals("Eleven", NumberUtilz.toEnNumShortScale(new BigDecimal("11"), false));
		assertEquals("Twelve", NumberUtilz.toEnNumShortScale(new BigDecimal("12"), false));
		
		assertEquals("Twenty", NumberUtilz.toEnNumShortScale(new BigDecimal("20"), false));
		assertEquals("Twenty-One", NumberUtilz.toEnNumShortScale(new BigDecimal("21"), false));

		assertEquals("Ninety-Nine", NumberUtilz.toEnNumShortScale(new BigDecimal("99"), false));

		assertEquals("One Hundred", NumberUtilz.toEnNumShortScale(new BigDecimal("100"), false));
		assertEquals("One Hundred One", NumberUtilz.toEnNumShortScale(new BigDecimal("101"), false));
		
		assertEquals("One Hundred Ten", NumberUtilz.toEnNumShortScale(new BigDecimal("110"), false));
		assertEquals("One Hundred Eleven", NumberUtilz.toEnNumShortScale(new BigDecimal("111"), false));
		assertEquals("One Hundred Twenty", NumberUtilz.toEnNumShortScale(new BigDecimal("120"), false));
		assertEquals("One Hundred Twenty-One", NumberUtilz.toEnNumShortScale(new BigDecimal("121"), false));
		
		assertEquals("Two Thousand", NumberUtilz.toEnNumShortScale(new BigDecimal("2000"), false));
		
		assertEquals("Twenty-Three Thousand Four Hundred Fifty-Six", NumberUtilz.toEnNumShortScale(new BigDecimal("23456"), false));

		assertEquals("Nine Hundred Eighty-Seven Septillion Six Hundred Fifty-Four Sextillion Three Hundred Twenty-One Quintillion Ninety-Eight Quadrillion Seven Hundred Sixty-Five Trillion Four Hundred Thirty-Two Billion One Hundred Nine Million Eight Hundred Seventy-Six Thousand Five Hundred Forty-Three", NumberUtilz.toEnNumShortScale(new BigDecimal("987654321098765432109876543"), false));
		
		
		assertEquals("Zero point Zero", NumberUtilz.toEnNumShortScale(new BigDecimal("0.0"), false));
		assertEquals("One and 0/10", NumberUtilz.toEnNumShortScale(new BigDecimal("1.0"), true));
		
		assertEquals("Zero point Zero Zero", NumberUtilz.toEnNumShortScale(new BigDecimal("0.00"), false));
		assertEquals("One and 0/100", NumberUtilz.toEnNumShortScale(new BigDecimal("1.00"), true));
		
		assertEquals("Zero point Zero One Two", NumberUtilz.toEnNumShortScale(new BigDecimal("0.012"), false));
		assertEquals("One and 12/1000", NumberUtilz.toEnNumShortScale(new BigDecimal("1.012"), true));
		
		
		assertEquals("Negative Nine Hundred Eighty-Seven Septillion Six Hundred Fifty-Four Sextillion Three Hundred Twenty-One Quintillion Ninety-Eight Quadrillion Seven Hundred Sixty-Five Trillion Four Hundred Thirty-Two Billion One Hundred Nine Million Eight Hundred Seventy-Six Thousand Five Hundred Forty-Three", NumberUtilz.toEnNumShortScale(new BigDecimal("-987654321098765432109876543"), false));
		
		
		assertEquals("Zero point Zero", NumberUtilz.toEnNumShortScale(new BigDecimal("-0.0"), false)); // BigDecimal has not signed zero
		assertEquals("Negative One and 0/10", NumberUtilz.toEnNumShortScale(new BigDecimal("-1.0"), true));
		
		assertEquals("Zero point Zero Zero", NumberUtilz.toEnNumShortScale(new BigDecimal("-0.00"), false)); // BigDecimal has not signed zero
		assertEquals("Negative One and 0/100", NumberUtilz.toEnNumShortScale(new BigDecimal("-1.00"), true));
		
		assertEquals("Negative Zero point Zero One Two", NumberUtilz.toEnNumShortScale(new BigDecimal("-0.012"), false));
		assertEquals("Negative One and 12/1000", NumberUtilz.toEnNumShortScale(new BigDecimal("-1.012"), true));
	}
	
	@Test
	public void testParseEnNumShortScale() throws Exception {
		assertEquals(null, NumberUtilz.parseEnNumShortScale(null));

		assertEquals(new BigDecimal("0"), NumberUtilz.parseEnNumShortScale("Zero"));
		assertEquals(new BigDecimal("1"), NumberUtilz.parseEnNumShortScale("One"));
		
		assertEquals(new BigDecimal("10"), NumberUtilz.parseEnNumShortScale("Ten"));
		
		assertEquals(new BigDecimal("11"), NumberUtilz.parseEnNumShortScale("Eleven"));
		
		assertEquals(new BigDecimal("20"), NumberUtilz.parseEnNumShortScale("Twenty"));
		assertEquals(new BigDecimal("21"), NumberUtilz.parseEnNumShortScale("Twenty-One"));
		
		assertEquals(new BigDecimal("99"), NumberUtilz.parseEnNumShortScale("Ninety-Nine"));
		
		assertEquals(new BigDecimal("100"), NumberUtilz.parseEnNumShortScale("One Hundred"));
		assertEquals(new BigDecimal("101"), NumberUtilz.parseEnNumShortScale("One Hundred One"));
		
		assertEquals(new BigDecimal("110"), NumberUtilz.parseEnNumShortScale("One Hundred Ten"));
		assertEquals(new BigDecimal("111"), NumberUtilz.parseEnNumShortScale("One Hundred Eleven"));
		assertEquals(new BigDecimal("120"), NumberUtilz.parseEnNumShortScale("One Hundred Twenty"));
		assertEquals(new BigDecimal("121"), NumberUtilz.parseEnNumShortScale("One Hundred Twenty-One"));
		
		assertEquals(new BigDecimal("2000"), NumberUtilz.parseEnNumShortScale("Two Thousand"));
		
		assertEquals(new BigDecimal("23456"), NumberUtilz.parseEnNumShortScale("Twenty-Three Thousand Four Hundred Fifty-Six"));
		
		assertEquals(new BigDecimal("987654321098765432109876543"), NumberUtilz.parseEnNumShortScale("Nine Hundred Eighty-Seven Septillion Six Hundred Fifty-Four Sextillion Three Hundred Twenty-One Quintillion Ninety-Eight Quadrillion Seven Hundred Sixty-Five Trillion Four Hundred Thirty-Two Billion One Hundred Nine Million Eight Hundred Seventy-Six Thousand Five Hundred Forty-Three"));
		
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseEnNumShortScale("Zero point Zero"));
		assertEquals(new BigDecimal("1.0"), NumberUtilz.parseEnNumShortScale("One and 0/10"));
		
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseEnNumShortScale("Zero point Zero Zero"));
		assertEquals(new BigDecimal("1.00"), NumberUtilz.parseEnNumShortScale("One and 0/100"));
		
		assertEquals(new BigDecimal("0.012"), NumberUtilz.parseEnNumShortScale("Zero point Zero One Two"));
		assertEquals(new BigDecimal("1.012"), NumberUtilz.parseEnNumShortScale("One and 12/1000"));
		
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseEnNumShortScale("Zero point Zero Zero"));
		assertEquals(new BigDecimal("1.00"), NumberUtilz.parseEnNumShortScale("One and 0/100"));
		
		assertEquals(new BigDecimal("0.012"), NumberUtilz.parseEnNumShortScale("Zero point Zero One Two"));
		assertEquals(new BigDecimal("1.012"), NumberUtilz.parseEnNumShortScale("One and 12/1000"));
		
		
		assertEquals(new BigDecimal("0"), NumberUtilz.parseEnNumShortScale("Zero point Zero", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseEnNumShortScale("Zero point Zero", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseEnNumShortScale("Zero point Zero", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("0.000"), NumberUtilz.parseEnNumShortScale("Zero point Zero", RoundingMode.HALF_UP, 3));
		assertEquals(new BigDecimal("1"), NumberUtilz.parseEnNumShortScale("One and 0/10", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("1.0"), NumberUtilz.parseEnNumShortScale("One and 0/10", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("1.00"), NumberUtilz.parseEnNumShortScale("One and 0/10", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("1.000"), NumberUtilz.parseEnNumShortScale("One and 0/10", RoundingMode.HALF_UP, 3));

		assertEquals(new BigDecimal("0"), NumberUtilz.parseEnNumShortScale("Zero point Zero Zero", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseEnNumShortScale("Zero point Zero Zero", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseEnNumShortScale("Zero point Zero Zero", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("0.000"), NumberUtilz.parseEnNumShortScale("Zero point Zero Zero", RoundingMode.HALF_UP, 3));
		assertEquals(new BigDecimal("1"), NumberUtilz.parseEnNumShortScale("One and 0/100", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("1.0"), NumberUtilz.parseEnNumShortScale("One and 0/100", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("1.00"), NumberUtilz.parseEnNumShortScale("One and 0/100", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("1.000"), NumberUtilz.parseEnNumShortScale("One and 0/100", RoundingMode.HALF_UP, 3));

		assertEquals(new BigDecimal("0"), NumberUtilz.parseEnNumShortScale("Zero point Zero One Two", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseEnNumShortScale("Zero point Zero One Two", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("0.01"), NumberUtilz.parseEnNumShortScale("Zero point Zero One Two", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("0.012"), NumberUtilz.parseEnNumShortScale("Zero point Zero One Two", RoundingMode.HALF_UP, 3));
		assertEquals(new BigDecimal("1"), NumberUtilz.parseEnNumShortScale("One and 12/1000", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("1.0"), NumberUtilz.parseEnNumShortScale("One and 12/1000", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("1.01"), NumberUtilz.parseEnNumShortScale("One and 12/1000", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("1.012"), NumberUtilz.parseEnNumShortScale("One and 12/1000", RoundingMode.HALF_UP, 3));
		
		assertEquals(new BigDecimal("1"), NumberUtilz.parseEnNumShortScale("Zero point Five Five Five", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("0.6"), NumberUtilz.parseEnNumShortScale("Zero point Five Five Five", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("0.56"), NumberUtilz.parseEnNumShortScale("Zero point Five Five Five", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("0.555"), NumberUtilz.parseEnNumShortScale("Zero point Five Five Five", RoundingMode.HALF_UP, 3));
		

		assertEquals(new BigDecimal("-987654321098765432109876543"), NumberUtilz.parseEnNumShortScale("Negative Nine Hundred Eighty-Seven Septillion Six Hundred Fifty-Four Sextillion Three Hundred Twenty-One Quintillion Ninety-Eight Quadrillion Seven Hundred Sixty-Five Trillion Four Hundred Thirty-Two Billion One Hundred Nine Million Eight Hundred Seventy-Six Thousand Five Hundred Forty-Three"));
		
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero"));
		assertEquals(new BigDecimal("-1.0"), NumberUtilz.parseEnNumShortScale("Negative One and 0/10"));
		
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero Zero"));
		assertEquals(new BigDecimal("-1.00"), NumberUtilz.parseEnNumShortScale("Negative One and 0/100"));
		
		assertEquals(new BigDecimal("-0.012"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero One Two"));
		assertEquals(new BigDecimal("-1.012"), NumberUtilz.parseEnNumShortScale("Negative One and 12/1000"));
		
		
		assertEquals(new BigDecimal("0"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("0.000"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero", RoundingMode.HALF_UP, 3));
		assertEquals(new BigDecimal("-1"), NumberUtilz.parseEnNumShortScale("Negative One and 0/10", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("-1.0"), NumberUtilz.parseEnNumShortScale("Negative One and 0/10", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("-1.00"), NumberUtilz.parseEnNumShortScale("Negative One and 0/10", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("-1.000"), NumberUtilz.parseEnNumShortScale("Negative One and 0/10", RoundingMode.HALF_UP, 3));

		assertEquals(new BigDecimal("0"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero Zero", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero Zero", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero Zero", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("0.000"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero Zero", RoundingMode.HALF_UP, 3));
		assertEquals(new BigDecimal("-1"), NumberUtilz.parseEnNumShortScale("Negative One and 0/100", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("-1.0"), NumberUtilz.parseEnNumShortScale("Negative One and 0/100", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("-1.00"), NumberUtilz.parseEnNumShortScale("Negative One and 0/100", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("-1.000"), NumberUtilz.parseEnNumShortScale("Negative One and 0/100", RoundingMode.HALF_UP, 3));

		assertEquals(new BigDecimal("0"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero One Two", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero One Two", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("-0.01"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero One Two", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("-0.012"), NumberUtilz.parseEnNumShortScale("Negative Zero point Zero One Two", RoundingMode.HALF_UP, 3));
		assertEquals(new BigDecimal("-1"), NumberUtilz.parseEnNumShortScale("Negative One and 12/1000", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("-1.0"), NumberUtilz.parseEnNumShortScale("Negative One and 12/1000", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("-1.01"), NumberUtilz.parseEnNumShortScale("Negative One and 12/1000", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("-1.012"), NumberUtilz.parseEnNumShortScale("Negative One and 12/1000", RoundingMode.HALF_UP, 3));
		
		assertEquals(new BigDecimal("-1"), NumberUtilz.parseEnNumShortScale("Negative Zero point Five Five Five", RoundingMode.HALF_UP, 0));
		assertEquals(new BigDecimal("-0.6"), NumberUtilz.parseEnNumShortScale("Negative Zero point Five Five Five", RoundingMode.HALF_UP, 1));
		assertEquals(new BigDecimal("-0.56"), NumberUtilz.parseEnNumShortScale("Negative Zero point Five Five Five", RoundingMode.HALF_UP, 2));
		assertEquals(new BigDecimal("-0.555"), NumberUtilz.parseEnNumShortScale("Negative Zero point Five Five Five", RoundingMode.HALF_UP, 3));
	}
}
