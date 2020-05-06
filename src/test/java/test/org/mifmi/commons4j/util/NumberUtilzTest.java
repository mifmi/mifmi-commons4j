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
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.junit.Test;
import org.mifmi.commons4j.util.NumberUtilz;
import org.mifmi.commons4j.util.exception.NumberParseException;

public class NumberUtilzTest {
	private static final double DELTA = 1e-15;
	
	@Test
	public void testDigitLength() throws Exception {
		assertEquals(0, NumberUtilz.digitLength(null));
		
		assertEquals(1, NumberUtilz.digitLength(new BigDecimal("0")));
		assertEquals(1, NumberUtilz.digitLength(new BigDecimal("1")));
		assertEquals(1, NumberUtilz.digitLength(new BigDecimal("9")));
		assertEquals(2, NumberUtilz.digitLength(new BigDecimal("10")));
		assertEquals(2, NumberUtilz.digitLength(new BigDecimal("99")));
		assertEquals(3, NumberUtilz.digitLength(new BigDecimal("100")));
		
		assertEquals(2, NumberUtilz.digitLength(new BigDecimal("0.0")));
		assertEquals(2, NumberUtilz.digitLength(new BigDecimal("0.1")));
		assertEquals(2, NumberUtilz.digitLength(new BigDecimal("0.9")));
		assertEquals(3, NumberUtilz.digitLength(new BigDecimal("0.10")));
		assertEquals(3, NumberUtilz.digitLength(new BigDecimal("0.99")));
		assertEquals(4, NumberUtilz.digitLength(new BigDecimal("0.100")));
		assertEquals(5, NumberUtilz.digitLength(new BigDecimal("0.0100")));
		
		assertEquals(2, NumberUtilz.digitLength(new BigDecimal("1.0")));
		assertEquals(2, NumberUtilz.digitLength(new BigDecimal("1.1")));
		assertEquals(2, NumberUtilz.digitLength(new BigDecimal("9.9")));
		assertEquals(4, NumberUtilz.digitLength(new BigDecimal("10.10")));
		assertEquals(4, NumberUtilz.digitLength(new BigDecimal("99.99")));
		assertEquals(6, NumberUtilz.digitLength(new BigDecimal("100.100")));
		assertEquals(7, NumberUtilz.digitLength(new BigDecimal("100.0100")));
	}
	
	@Test
	public void testDigitLengthIntegerPart() throws Exception {
		assertEquals(0, NumberUtilz.digitLengthIntegerPart(null));
		
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("0")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("1")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("9")));
		assertEquals(2, NumberUtilz.digitLengthIntegerPart(new BigDecimal("10")));
		assertEquals(2, NumberUtilz.digitLengthIntegerPart(new BigDecimal("99")));
		assertEquals(3, NumberUtilz.digitLengthIntegerPart(new BigDecimal("100")));
		
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("0.0")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("0.1")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("0.9")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("0.10")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("0.99")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("0.100")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("0.0100")));
		
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("1.0")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("1.1")));
		assertEquals(1, NumberUtilz.digitLengthIntegerPart(new BigDecimal("9.9")));
		assertEquals(2, NumberUtilz.digitLengthIntegerPart(new BigDecimal("10.10")));
		assertEquals(3, NumberUtilz.digitLengthIntegerPart(new BigDecimal("100.10")));
		assertEquals(2, NumberUtilz.digitLengthIntegerPart(new BigDecimal("99.99")));
		assertEquals(3, NumberUtilz.digitLengthIntegerPart(new BigDecimal("990.99")));
		assertEquals(3, NumberUtilz.digitLengthIntegerPart(new BigDecimal("100.100")));
		assertEquals(3, NumberUtilz.digitLengthIntegerPart(new BigDecimal("100.0100")));
	}
	
	@Test
	public void testDigitLengthDecimalPart() throws Exception {
		assertEquals(0, NumberUtilz.digitLengthDecimalPart(null));
		
		assertEquals(0, NumberUtilz.digitLengthDecimalPart(new BigDecimal("0")));
		assertEquals(0, NumberUtilz.digitLengthDecimalPart(new BigDecimal("1")));
		assertEquals(0, NumberUtilz.digitLengthDecimalPart(new BigDecimal("9")));
		assertEquals(0, NumberUtilz.digitLengthDecimalPart(new BigDecimal("10")));
		assertEquals(0, NumberUtilz.digitLengthDecimalPart(new BigDecimal("99")));
		assertEquals(0, NumberUtilz.digitLengthDecimalPart(new BigDecimal("100")));
		
		assertEquals(1, NumberUtilz.digitLengthDecimalPart(new BigDecimal("0.0")));
		assertEquals(1, NumberUtilz.digitLengthDecimalPart(new BigDecimal("0.1")));
		assertEquals(1, NumberUtilz.digitLengthDecimalPart(new BigDecimal("0.9")));
		assertEquals(2, NumberUtilz.digitLengthDecimalPart(new BigDecimal("0.10")));
		assertEquals(2, NumberUtilz.digitLengthDecimalPart(new BigDecimal("0.99")));
		assertEquals(3, NumberUtilz.digitLengthDecimalPart(new BigDecimal("0.100")));
		assertEquals(4, NumberUtilz.digitLengthDecimalPart(new BigDecimal("0.0100")));
		
		assertEquals(1, NumberUtilz.digitLengthDecimalPart(new BigDecimal("1.0")));
		assertEquals(1, NumberUtilz.digitLengthDecimalPart(new BigDecimal("1.1")));
		assertEquals(1, NumberUtilz.digitLengthDecimalPart(new BigDecimal("9.9")));
		assertEquals(2, NumberUtilz.digitLengthDecimalPart(new BigDecimal("10.10")));
		assertEquals(3, NumberUtilz.digitLengthDecimalPart(new BigDecimal("10.100")));
		assertEquals(2, NumberUtilz.digitLengthDecimalPart(new BigDecimal("99.99")));
		assertEquals(3, NumberUtilz.digitLengthDecimalPart(new BigDecimal("99.990")));
		assertEquals(3, NumberUtilz.digitLengthDecimalPart(new BigDecimal("100.100")));
		assertEquals(4, NumberUtilz.digitLengthDecimalPart(new BigDecimal("100.0100")));
	}
	
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
		

		assertEquals(new BigDecimal("25"), NumberUtilz.parseEnNumShortScale("25"));
		assertEquals(new BigDecimal("2.5"), NumberUtilz.parseEnNumShortScale("2.5"));
		
		assertEquals(new BigDecimal("2500"), NumberUtilz.parseEnNumShortScale("25 Hundred"));
		assertEquals(new BigDecimal("250"), NumberUtilz.parseEnNumShortScale("2.5 Hundred"));
		
		assertEquals(new BigDecimal("25000"), NumberUtilz.parseEnNumShortScale("25 Thousand"));
		assertEquals(new BigDecimal("2500"), NumberUtilz.parseEnNumShortScale("2.5 Thousand"));
		
		assertEquals(new BigDecimal("25000000"), NumberUtilz.parseEnNumShortScale("25 Million"));
		assertEquals(new BigDecimal("2500000"), NumberUtilz.parseEnNumShortScale("2.5 Million"));
		
		try {
			NumberUtilz.parseEnNumShortScale("");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
		
		try {
			NumberUtilz.parseEnNumShortScale("-");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
		
		try {
			NumberUtilz.parseEnNumShortScale("xxx");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
		
		try {
			NumberUtilz.parseEnNumShortScale("Hundred");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
		
		try {
			NumberUtilz.parseEnNumShortScale("Two Hundreds");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
	}
	
	@Test
	public void testToJPNum() throws Exception {
		assertEquals(null, NumberUtilz.toJPNum((BigDecimal)null));
		
		assertEquals("〇", NumberUtilz.toJPNum(new BigDecimal("0")));
		assertEquals("一", NumberUtilz.toJPNum(new BigDecimal("1")));
		assertEquals("二", NumberUtilz.toJPNum(new BigDecimal("2")));
		
		assertEquals("十", NumberUtilz.toJPNum(new BigDecimal("10")));
		assertEquals("十一", NumberUtilz.toJPNum(new BigDecimal("11")));
		assertEquals("十二", NumberUtilz.toJPNum(new BigDecimal("12")));
		
		assertEquals("二十", NumberUtilz.toJPNum(new BigDecimal("20")));
		assertEquals("二十一", NumberUtilz.toJPNum(new BigDecimal("21")));

		assertEquals("九十九", NumberUtilz.toJPNum(new BigDecimal("99")));

		assertEquals("百", NumberUtilz.toJPNum(new BigDecimal("100")));
		assertEquals("百一", NumberUtilz.toJPNum(new BigDecimal("101")));
		
		assertEquals("百十", NumberUtilz.toJPNum(new BigDecimal("110")));
		assertEquals("百十一", NumberUtilz.toJPNum(new BigDecimal("111")));
		assertEquals("百二十", NumberUtilz.toJPNum(new BigDecimal("120")));
		assertEquals("百二十一", NumberUtilz.toJPNum(new BigDecimal("121")));
		
		assertEquals("二千", NumberUtilz.toJPNum(new BigDecimal("2000")));
		
		assertEquals("二万三千四百五十六", NumberUtilz.toJPNum(new BigDecimal("23456")));
		
		assertEquals("九千八百七十六無量大数五千四百三十二不可思議千九十八那由他七千六百五十四阿僧祇三千二百十恒河沙九千八百七十六極五千四百三十三載二千百九正八千七百六十五澗四千三百二十一溝九百八十七穣六千五百四十三秭二千百九垓八千七百六十五京四千三百二十一兆九百八十七億六千五百四十三万二千百九", NumberUtilz.toJPNum(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109")));
		assertEquals("玖阡捌陌漆拾陸無量大数伍阡肆陌参拾弐不可思議阡玖拾捌那由他漆阡陸陌伍拾肆阿僧祇参阡弐陌拾恒河沙玖阡捌陌漆拾陸極伍阡肆陌参拾参載弐阡陌玖正捌阡漆陌陸拾伍澗肆阡参陌弐拾壱溝玖陌捌拾漆穣陸阡伍陌肆拾参秭弐阡陌玖垓捌阡漆陌陸拾伍京肆阡参陌弐拾壱兆玖陌捌拾漆億陸阡伍陌肆拾参萬弐阡陌玖", NumberUtilz.toJPNum(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), false, true, false));
		assertEquals("玖阡捌陌柒拾陸無量大数伍阡肆陌參拾貳不可思議阡玖拾捌那由他柒阡陸陌伍拾肆阿僧祇參阡貳陌拾恒河沙玖阡捌陌柒拾陸極伍阡肆陌參拾參載貳阡陌玖正捌阡柒陌陸拾伍澗肆阡參陌貳拾壹溝玖陌捌拾柒穣陸阡伍陌肆拾參秭貳阡陌玖垓捌阡柒陌陸拾伍京肆阡參陌貳拾壹兆玖陌捌拾柒億陸阡伍陌肆拾參萬貳阡陌玖", NumberUtilz.toJPNum(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), false, true, true));
		assertEquals("玖阡捌陌漆拾陸無量大数伍阡肆陌参拾弐不可思議壱阡玖拾捌那由他漆阡陸陌伍拾肆阿僧祇参阡弐陌壱拾恒河沙玖阡捌陌漆拾陸極伍阡肆陌参拾参載弐阡壱陌玖正捌阡漆陌陸拾伍澗肆阡参陌弐拾壱溝玖陌捌拾漆穣陸阡伍陌肆拾参秭弐阡壱陌玖垓捌阡漆陌陸拾伍京肆阡参陌弐拾壱兆玖陌捌拾漆億陸阡伍陌肆拾参萬弐阡壱陌玖", NumberUtilz.toJPNum(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), true, true, false));
		assertEquals("玖阡捌陌柒拾陸無量大数伍阡肆陌參拾貳不可思議壹阡玖拾捌那由他柒阡陸陌伍拾肆阿僧祇參阡貳陌壹拾恒河沙玖阡捌陌柒拾陸極伍阡肆陌參拾參載貳阡壹陌玖正捌阡柒陌陸拾伍澗肆阡參陌貳拾壹溝玖陌捌拾柒穣陸阡伍陌肆拾參秭貳阡壹陌玖垓捌阡柒陌陸拾伍京肆阡參陌貳拾壹兆玖陌捌拾柒億陸阡伍陌肆拾參萬貳阡壹陌玖", NumberUtilz.toJPNum(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), true, true, true));
		
		assertEquals("〇・〇", NumberUtilz.toJPNum(new BigDecimal("0.0")));
		
		assertEquals("〇・〇〇", NumberUtilz.toJPNum(new BigDecimal("0.00")));
		
		assertEquals("〇・〇一二", NumberUtilz.toJPNum(new BigDecimal("0.012")));
		
		
		assertEquals("−九千八百七十六無量大数五千四百三十二不可思議千九十八那由他七千六百五十四阿僧祇三千二百十恒河沙九千八百七十六極五千四百三十三載二千百九正八千七百六十五澗四千三百二十一溝九百八十七穣六千五百四十三秭二千百九垓八千七百六十五京四千三百二十一兆九百八十七億六千五百四十三万二千百九", NumberUtilz.toJPNum(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109")));
		assertEquals("−玖阡捌陌漆拾陸無量大数伍阡肆陌参拾弐不可思議阡玖拾捌那由他漆阡陸陌伍拾肆阿僧祇参阡弐陌拾恒河沙玖阡捌陌漆拾陸極伍阡肆陌参拾参載弐阡陌玖正捌阡漆陌陸拾伍澗肆阡参陌弐拾壱溝玖陌捌拾漆穣陸阡伍陌肆拾参秭弐阡陌玖垓捌阡漆陌陸拾伍京肆阡参陌弐拾壱兆玖陌捌拾漆億陸阡伍陌肆拾参萬弐阡陌玖", NumberUtilz.toJPNum(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), false, true, false));
		assertEquals("−玖阡捌陌柒拾陸無量大数伍阡肆陌參拾貳不可思議阡玖拾捌那由他柒阡陸陌伍拾肆阿僧祇參阡貳陌拾恒河沙玖阡捌陌柒拾陸極伍阡肆陌參拾參載貳阡陌玖正捌阡柒陌陸拾伍澗肆阡參陌貳拾壹溝玖陌捌拾柒穣陸阡伍陌肆拾參秭貳阡陌玖垓捌阡柒陌陸拾伍京肆阡參陌貳拾壹兆玖陌捌拾柒億陸阡伍陌肆拾參萬貳阡陌玖", NumberUtilz.toJPNum(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), false, true, true));
		assertEquals("−玖阡捌陌漆拾陸無量大数伍阡肆陌参拾弐不可思議壱阡玖拾捌那由他漆阡陸陌伍拾肆阿僧祇参阡弐陌壱拾恒河沙玖阡捌陌漆拾陸極伍阡肆陌参拾参載弐阡壱陌玖正捌阡漆陌陸拾伍澗肆阡参陌弐拾壱溝玖陌捌拾漆穣陸阡伍陌肆拾参秭弐阡壱陌玖垓捌阡漆陌陸拾伍京肆阡参陌弐拾壱兆玖陌捌拾漆億陸阡伍陌肆拾参萬弐阡壱陌玖", NumberUtilz.toJPNum(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), true, true, false));
		assertEquals("−玖阡捌陌柒拾陸無量大数伍阡肆陌參拾貳不可思議壹阡玖拾捌那由他柒阡陸陌伍拾肆阿僧祇參阡貳陌壹拾恒河沙玖阡捌陌柒拾陸極伍阡肆陌參拾參載貳阡壹陌玖正捌阡柒陌陸拾伍澗肆阡參陌貳拾壹溝玖陌捌拾柒穣陸阡伍陌肆拾參秭貳阡壹陌玖垓捌阡柒陌陸拾伍京肆阡參陌貳拾壹兆玖陌捌拾柒億陸阡伍陌肆拾參萬貳阡壹陌玖", NumberUtilz.toJPNum(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), true, true, true));
		
		
		assertEquals("〇・〇", NumberUtilz.toJPNum(new BigDecimal("-0.0"))); // BigDecimal has not signed zero
		
		assertEquals("〇・〇〇", NumberUtilz.toJPNum(new BigDecimal("-0.00"))); // BigDecimal has not signed zero
		
		assertEquals("−〇・〇一二", NumberUtilz.toJPNum(new BigDecimal("-0.012")));
	}
	
	@Test
	public void testParseJPNum() throws Exception {
		assertEquals(null, NumberUtilz.parseJPNum(null));
		
		assertEquals(new BigDecimal("0"), NumberUtilz.parseJPNum("〇"));
		assertEquals(new BigDecimal("1"), NumberUtilz.parseJPNum("一"));
		
		assertEquals(new BigDecimal("10"), NumberUtilz.parseJPNum("十"));
		
		assertEquals(new BigDecimal("11"), NumberUtilz.parseJPNum("十一"));
		
		assertEquals(new BigDecimal("20"), NumberUtilz.parseJPNum("二十"));
		assertEquals(new BigDecimal("21"), NumberUtilz.parseJPNum("二十一"));
		
		assertEquals(new BigDecimal("99"), NumberUtilz.parseJPNum("九十九"));
		
		assertEquals(new BigDecimal("100"), NumberUtilz.parseJPNum("百"));
		assertEquals(new BigDecimal("101"), NumberUtilz.parseJPNum("百一"));
		
		assertEquals(new BigDecimal("110"), NumberUtilz.parseJPNum("百十"));
		assertEquals(new BigDecimal("111"), NumberUtilz.parseJPNum("百十一"));
		assertEquals(new BigDecimal("120"), NumberUtilz.parseJPNum("百二十"));
		assertEquals(new BigDecimal("121"), NumberUtilz.parseJPNum("百二十一"));
		
		assertEquals(new BigDecimal("2000"), NumberUtilz.parseJPNum("二千"));
		
		assertEquals(new BigDecimal("23456"), NumberUtilz.parseJPNum("二万三千四百五十六"));
		
		assertEquals(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("九千八百七十六無量大数五千四百三十二不可思議千九十八那由他七千六百五十四阿僧祇三千二百十恒河沙九千八百七十六極五千四百三十三載二千百九正八千七百六十五澗四千三百二十一溝九百八十七穣六千五百四十三秭二千百九垓八千七百六十五京四千三百二十一兆九百八十七億六千五百四十三万二千百九"));
		assertEquals(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("玖阡捌陌漆拾陸無量大数伍阡肆陌参拾弐不可思議阡玖拾捌那由他漆阡陸陌伍拾肆阿僧祇参阡弐陌拾恒河沙玖阡捌陌漆拾陸極伍阡肆陌参拾参載弐阡陌玖正捌阡漆陌陸拾伍澗肆阡参陌弐拾壱溝玖陌捌拾漆穣陸阡伍陌肆拾参秭弐阡陌玖垓捌阡漆陌陸拾伍京肆阡参陌弐拾壱兆玖陌捌拾漆億陸阡伍陌肆拾参萬弐阡陌玖"));
		assertEquals(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("玖阡捌陌柒拾陸無量大数伍阡肆陌參拾貳不可思議阡玖拾捌那由他柒阡陸陌伍拾肆阿僧祇參阡貳陌拾恒河沙玖阡捌陌柒拾陸極伍阡肆陌參拾參載貳阡陌玖正捌阡柒陌陸拾伍澗肆阡參陌貳拾壹溝玖陌捌拾柒穣陸阡伍陌肆拾參秭貳阡陌玖垓捌阡柒陌陸拾伍京肆阡參陌貳拾壹兆玖陌捌拾柒億陸阡伍陌肆拾參萬貳阡陌玖"));
		assertEquals(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("玖阡捌陌漆拾陸無量大数伍阡肆陌参拾弐不可思議壱阡玖拾捌那由他漆阡陸陌伍拾肆阿僧祇参阡弐陌壱拾恒河沙玖阡捌陌漆拾陸極伍阡肆陌参拾参載弐阡壱陌玖正捌阡漆陌陸拾伍澗肆阡参陌弐拾壱溝玖陌捌拾漆穣陸阡伍陌肆拾参秭弐阡壱陌玖垓捌阡漆陌陸拾伍京肆阡参陌弐拾壱兆玖陌捌拾漆億陸阡伍陌肆拾参萬弐阡壱陌玖"));
		assertEquals(new BigDecimal("987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("玖阡捌陌柒拾陸無量大数伍阡肆陌參拾貳不可思議壹阡玖拾捌那由他柒阡陸陌伍拾肆阿僧祇參阡貳陌壹拾恒河沙玖阡捌陌柒拾陸極伍阡肆陌參拾參載貳阡壹陌玖正捌阡柒陌陸拾伍澗肆阡參陌貳拾壹溝玖陌捌拾柒穣陸阡伍陌肆拾參秭貳阡壹陌玖垓捌阡柒陌陸拾伍京肆阡參陌貳拾壹兆玖陌捌拾柒億陸阡伍陌肆拾參萬貳阡壹陌玖"));
		
		
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseJPNum("〇・〇"));
		assertEquals(new BigDecimal("1.0"), NumberUtilz.parseJPNum("一・〇"));
		
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseJPNum("〇・〇〇"));
		assertEquals(new BigDecimal("1.00"), NumberUtilz.parseJPNum("一・〇〇"));
		
		assertEquals(new BigDecimal("0.012"), NumberUtilz.parseJPNum("〇・〇一二"));
		assertEquals(new BigDecimal("1.012"), NumberUtilz.parseJPNum("一・〇一二"));
		
		
		assertEquals(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("−九千八百七十六無量大数五千四百三十二不可思議千九十八那由他七千六百五十四阿僧祇三千二百十恒河沙九千八百七十六極五千四百三十三載二千百九正八千七百六十五澗四千三百二十一溝九百八十七穣六千五百四十三秭二千百九垓八千七百六十五京四千三百二十一兆九百八十七億六千五百四十三万二千百九"));
		assertEquals(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("−玖阡捌陌漆拾陸無量大数伍阡肆陌参拾弐不可思議阡玖拾捌那由他漆阡陸陌伍拾肆阿僧祇参阡弐陌拾恒河沙玖阡捌陌漆拾陸極伍阡肆陌参拾参載弐阡陌玖正捌阡漆陌陸拾伍澗肆阡参陌弐拾壱溝玖陌捌拾漆穣陸阡伍陌肆拾参秭弐阡陌玖垓捌阡漆陌陸拾伍京肆阡参陌弐拾壱兆玖陌捌拾漆億陸阡伍陌肆拾参萬弐阡陌玖"));
		assertEquals(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("−玖阡捌陌柒拾陸無量大数伍阡肆陌參拾貳不可思議阡玖拾捌那由他柒阡陸陌伍拾肆阿僧祇參阡貳陌拾恒河沙玖阡捌陌柒拾陸極伍阡肆陌參拾參載貳阡陌玖正捌阡柒陌陸拾伍澗肆阡參陌貳拾壹溝玖陌捌拾柒穣陸阡伍陌肆拾參秭貳阡陌玖垓捌阡柒陌陸拾伍京肆阡參陌貳拾壹兆玖陌捌拾柒億陸阡伍陌肆拾參萬貳阡陌玖"));
		assertEquals(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("−玖阡捌陌漆拾陸無量大数伍阡肆陌参拾弐不可思議壱阡玖拾捌那由他漆阡陸陌伍拾肆阿僧祇参阡弐陌壱拾恒河沙玖阡捌陌漆拾陸極伍阡肆陌参拾参載弐阡壱陌玖正捌阡漆陌陸拾伍澗肆阡参陌弐拾壱溝玖陌捌拾漆穣陸阡伍陌肆拾参秭弐阡壱陌玖垓捌阡漆陌陸拾伍京肆阡参陌弐拾壱兆玖陌捌拾漆億陸阡伍陌肆拾参萬弐阡壱陌玖"));
		assertEquals(new BigDecimal("-987654321098765432109876543321098765432109876543210987654321098765432109"), NumberUtilz.parseJPNum("−玖阡捌陌柒拾陸無量大数伍阡肆陌參拾貳不可思議壹阡玖拾捌那由他柒阡陸陌伍拾肆阿僧祇參阡貳陌壹拾恒河沙玖阡捌陌柒拾陸極伍阡肆陌參拾參載貳阡壹陌玖正捌阡柒陌陸拾伍澗肆阡參陌貳拾壹溝玖陌捌拾柒穣陸阡伍陌肆拾參秭貳阡壹陌玖垓捌阡柒陌陸拾伍京肆阡參陌貳拾壹兆玖陌捌拾柒億陸阡伍陌肆拾參萬貳阡壹陌玖"));
		
		
		assertEquals(new BigDecimal("0.0"), NumberUtilz.parseJPNum("−〇・〇"));
		assertEquals(new BigDecimal("-1.0"), NumberUtilz.parseJPNum("−一・〇"));
		
		assertEquals(new BigDecimal("0.00"), NumberUtilz.parseJPNum("−〇・〇〇"));
		assertEquals(new BigDecimal("-1.00"), NumberUtilz.parseJPNum("−一・〇〇"));
		
		assertEquals(new BigDecimal("-0.012"), NumberUtilz.parseJPNum("−〇・〇一二"));
		assertEquals(new BigDecimal("-1.012"), NumberUtilz.parseJPNum("−一・〇一二"));
		

		assertEquals(new BigDecimal("25"), NumberUtilz.parseJPNum("25"));
		assertEquals(new BigDecimal("2.5"), NumberUtilz.parseJPNum("2.5"));
		
		assertEquals(new BigDecimal("250"), NumberUtilz.parseJPNum("25十"));
		assertEquals(new BigDecimal("25"), NumberUtilz.parseJPNum("2.5十"));
		
		assertEquals(new BigDecimal("2500"), NumberUtilz.parseJPNum("25百"));
		assertEquals(new BigDecimal("250"), NumberUtilz.parseJPNum("2.5百"));
		
		assertEquals(new BigDecimal("25000"), NumberUtilz.parseJPNum("25千"));
		assertEquals(new BigDecimal("2500"), NumberUtilz.parseJPNum("2.5千"));
		
		assertEquals(new BigDecimal("250000"), NumberUtilz.parseJPNum("25万"));
		assertEquals(new BigDecimal("25000"), NumberUtilz.parseJPNum("2.5万"));
		
		assertEquals(new BigDecimal("2500000"), NumberUtilz.parseJPNum("25十万"));
		assertEquals(new BigDecimal("250000"), NumberUtilz.parseJPNum("2.5十万"));
		
		assertEquals(new BigDecimal("25000000"), NumberUtilz.parseJPNum("25百万"));
		assertEquals(new BigDecimal("2500000"), NumberUtilz.parseJPNum("2.5百万"));
		
		assertEquals(new BigDecimal("250000000"), NumberUtilz.parseJPNum("25千万"));
		assertEquals(new BigDecimal("25000000"), NumberUtilz.parseJPNum("2.5千万"));
		
		assertEquals(new BigDecimal("2500000000"), NumberUtilz.parseJPNum("25億"));
		assertEquals(new BigDecimal("250000000"), NumberUtilz.parseJPNum("2.5億"));
		
		try {
			NumberUtilz.parseJPNum("");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
		
		try {
			NumberUtilz.parseJPNum("−");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
		
		try {
			NumberUtilz.parseJPNum("あ");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
		
		try {
			NumberUtilz.parseJPNum("二あ");
			assertTrue(true);
		} catch (NumberParseException e) {
			// NOP
		}
	}
	
	@Test
	public void testAddDigit() throws Exception {
		assertEquals(null, NumberUtilz.addDigit(null, null));
		assertEquals(new BigDecimal("0"), NumberUtilz.addDigit(null, new BigDecimal("0")));
		assertEquals(new BigDecimal("0"), NumberUtilz.addDigit(new BigDecimal("0"), null));
		
		assertEquals(new BigDecimal("0"), NumberUtilz.addDigit(new BigDecimal("0"), new BigDecimal("0")));
		assertEquals(new BigDecimal("1"), NumberUtilz.addDigit(new BigDecimal("0"), new BigDecimal("1")));
		assertEquals(new BigDecimal("12"), NumberUtilz.addDigit(new BigDecimal("0"), new BigDecimal("12")));
		
		assertEquals(new BigDecimal("0.0"), NumberUtilz.addDigit(new BigDecimal("0"), new BigDecimal("0"), true));
		assertEquals(new BigDecimal("0.1"), NumberUtilz.addDigit(new BigDecimal("0"), new BigDecimal("1"), true));
		assertEquals(new BigDecimal("0.12"), NumberUtilz.addDigit(new BigDecimal("0"), new BigDecimal("12"), true));
		
		assertEquals(new BigDecimal("10"), NumberUtilz.addDigit(new BigDecimal("1"), new BigDecimal("0")));
		assertEquals(new BigDecimal("11"), NumberUtilz.addDigit(new BigDecimal("1"), new BigDecimal("1")));
		assertEquals(new BigDecimal("112"), NumberUtilz.addDigit(new BigDecimal("1"), new BigDecimal("12")));
		
		assertEquals(new BigDecimal("1.0"), NumberUtilz.addDigit(new BigDecimal("1"), new BigDecimal("0"), true));
		assertEquals(new BigDecimal("1.1"), NumberUtilz.addDigit(new BigDecimal("1"), new BigDecimal("1"), true));
		assertEquals(new BigDecimal("1.12"), NumberUtilz.addDigit(new BigDecimal("1"), new BigDecimal("12"), true));
		
		assertEquals(new BigDecimal("120"), NumberUtilz.addDigit(new BigDecimal("12"), new BigDecimal("0")));
		assertEquals(new BigDecimal("121"), NumberUtilz.addDigit(new BigDecimal("12"), new BigDecimal("1")));
		assertEquals(new BigDecimal("1212"), NumberUtilz.addDigit(new BigDecimal("12"), new BigDecimal("12")));

		assertEquals(new BigDecimal("12.0"), NumberUtilz.addDigit(new BigDecimal("12"), new BigDecimal("0"), true));
		assertEquals(new BigDecimal("12.1"), NumberUtilz.addDigit(new BigDecimal("12"), new BigDecimal("1"), true));
		assertEquals(new BigDecimal("12.12"), NumberUtilz.addDigit(new BigDecimal("12"), new BigDecimal("12"), true));
		
		assertEquals(new BigDecimal("0.00"), NumberUtilz.addDigit(new BigDecimal("0.0"), new BigDecimal("0")));
		assertEquals(new BigDecimal("0.01"), NumberUtilz.addDigit(new BigDecimal("0.0"), new BigDecimal("1")));
		assertEquals(new BigDecimal("0.012"), NumberUtilz.addDigit(new BigDecimal("0.0"), new BigDecimal("12")));

		assertEquals(new BigDecimal("0.00"), NumberUtilz.addDigit(new BigDecimal("0.0"), new BigDecimal("0"), true));
		assertEquals(new BigDecimal("0.01"), NumberUtilz.addDigit(new BigDecimal("0.0"), new BigDecimal("1"), true));
		assertEquals(new BigDecimal("0.012"), NumberUtilz.addDigit(new BigDecimal("0.0"), new BigDecimal("12"), true));

		assertEquals(new BigDecimal("0.10"), NumberUtilz.addDigit(new BigDecimal("0.1"), new BigDecimal("0")));
		assertEquals(new BigDecimal("0.11"), NumberUtilz.addDigit(new BigDecimal("0.1"), new BigDecimal("1")));
		assertEquals(new BigDecimal("0.112"), NumberUtilz.addDigit(new BigDecimal("0.1"), new BigDecimal("12")));

		assertEquals(new BigDecimal("0.120"), NumberUtilz.addDigit(new BigDecimal("0.12"), new BigDecimal("0")));
		assertEquals(new BigDecimal("0.121"), NumberUtilz.addDigit(new BigDecimal("0.12"), new BigDecimal("1")));
		assertEquals(new BigDecimal("0.1212"), NumberUtilz.addDigit(new BigDecimal("0.12"), new BigDecimal("12")));

		assertEquals(new BigDecimal("0.12012"), NumberUtilz.addDigit(new BigDecimal("0.12"), new BigDecimal("0.12")));
		assertEquals(new BigDecimal("0.12123"), NumberUtilz.addDigit(new BigDecimal("0.12"), new BigDecimal("1.23")));
		assertEquals(new BigDecimal("0.121234"), NumberUtilz.addDigit(new BigDecimal("0.12"), new BigDecimal("12.34")));
		
		assertEquals(new BigDecimal("12.00"), NumberUtilz.addDigit(new BigDecimal("12.0"), new BigDecimal("0")));
		assertEquals(new BigDecimal("12.01"), NumberUtilz.addDigit(new BigDecimal("12.0"), new BigDecimal("1")));
		assertEquals(new BigDecimal("12.012"), NumberUtilz.addDigit(new BigDecimal("12.0"), new BigDecimal("12")));

		assertEquals(new BigDecimal("12.10"), NumberUtilz.addDigit(new BigDecimal("12.1"), new BigDecimal("0")));
		assertEquals(new BigDecimal("12.11"), NumberUtilz.addDigit(new BigDecimal("12.1"), new BigDecimal("1")));
		assertEquals(new BigDecimal("12.112"), NumberUtilz.addDigit(new BigDecimal("12.1"), new BigDecimal("12")));

		assertEquals(new BigDecimal("12.120"), NumberUtilz.addDigit(new BigDecimal("12.12"), new BigDecimal("0")));
		assertEquals(new BigDecimal("12.121"), NumberUtilz.addDigit(new BigDecimal("12.12"), new BigDecimal("1")));
		assertEquals(new BigDecimal("12.1212"), NumberUtilz.addDigit(new BigDecimal("12.12"), new BigDecimal("12")));
	}
}
