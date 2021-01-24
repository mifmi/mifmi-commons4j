/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mifmi.commons4j.util.exception.NumberParseException;

/**
 * Helper methods for working with Number instances.
 * 
 * @author mozq
 */
public final class NumberUtilz {
	
	private static final Pattern EN_NUM_DECIMAL_POINT_PATTERN = Pattern.compile("(.+)\\s+point\\s+(.+)");
	
	private static final Pattern EN_NUM_DECIMAL_AND_PATTERN = Pattern.compile("(.+)\\s+and\\s+([0-9]+)\\s*/\\s*([0-9]+)");
	
	private NumberUtilz() {
		// NOP
	}

	public static int compare(Number num1, Number num2) {
		return compare(num1, num2, false);
	}

	public static int compare(Number num1, Number num2, boolean nullable) {
		BigDecimal dec1 = toBigDecimal(num1);
		BigDecimal dec2 = toBigDecimal(num2);
		
		if (nullable) {
			return ObjectUtilz.compare(dec1, dec2);
		} else {
			return dec1.compareTo(dec2);
		}
	}
	
	public static BigInteger getIntegerPart(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return null;
		}
		
		return bigDecimal.toBigInteger();
	}
	
	public static BigDecimal getDecimalPart(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return null;
		}
		
		return bigDecimal.subtract(new BigDecimal(bigDecimal.toBigInteger()));
	}
	
	public static int digitLength(long n) {
		if (n == 0) {
			return 1;
		}
		
		return (int) (Math.log10(Math.abs(n)) + 1);
	}
	
	public static int digitLength(BigDecimal n) {
		if (n == null) {
			return 0;
		}
		
		int len = digitLengthIntegerPart(n) + digitLengthDecimalPart(n);
		
		return len;
	}
	
	public static int digitLengthIntegerPart(BigDecimal n) {
		if (n == null) {
			return 0;
		}
		
		int len = n.precision() - n.scale();
		if (len < 1) {
			len = 1;
		}

		return len;
	}
	
	public static int digitLengthDecimalPart(BigDecimal n) {
		if (n == null) {
			return 0;
		}

		int scale = n.scale();
		
		if (scale < 0) {
			scale = 0;
		}
		
		return scale;
	}
	
	public static <T extends Number> T parseNumber(String strValue, String pattern, Class<T> numberClass) {
		if (strValue == null) {
			return null;
		}
		
		if (strValue.isEmpty()) {
			return null;
		}

		DecimalFormat df = new DecimalFormat(pattern);
		df.setParseBigDecimal(numberClass == BigInteger.class || numberClass == BigDecimal.class);
		df.setParseIntegerOnly(false);
		
		Number number;
		try {
			number = df.parse(strValue);
		} catch (ParseException e) {
			throw new NumberParseException(e);
		}
		
		return castNumber(number, numberClass);
	}
	
	public static boolean isZero(Number n) {
		if (n == null) {
			return false;
		}
		
		if (n instanceof Float) {
			return (n.floatValue() == 0.0f);
		} else if (n instanceof Double) {
			return (n.doubleValue() == 0.0d);
		} else if (n instanceof BigInteger) {
			return ((BigInteger)n).compareTo(BigInteger.ZERO) == 0;
		} else if (n instanceof BigDecimal) {
			return ((BigDecimal)n).compareTo(BigDecimal.ZERO) == 0;
		}
		
		return n.longValue() == 0L;
	}
	
	public static boolean isNaN(Number n) {
		if (n == null) {
			return true;
		}
		
		if (n instanceof Float) {
			return ((Float)n).isNaN();
		} else if (n instanceof Double) {
			return ((Double)n).isNaN();
		}
		
		return false;
	}
	
	public static <T extends Number> T castNumber(Number n, Class<T> numberClass) {
		if (n == null) {
			return null;
		}
		
		Number ret;
		if (numberClass == Byte.class) {
			ret = toByteObject(n);
		} else if (numberClass == Short.class) {
			ret = toShortObject(n);
		} else if (numberClass == Integer.class) {
			ret = toIntegerObject(n);
		} else if (numberClass == Long.class) {
			ret = toLongObject(n);
		} else if (numberClass == Float.class) {
			ret = toFloatObject(n);
		} else if (numberClass == Double.class) {
			ret = toDoubleObject(n);
		} else if (numberClass == BigInteger.class) {
			ret = toBigInteger(n);
		} else if (numberClass == BigDecimal.class) {
			ret = toBigDecimal(n);
		} else if (numberClass == AtomicInteger.class) {
			ret = toAtomicInteger(n);
		} else if (numberClass == AtomicLong.class) {
			ret = toAtomicLong(n);
		} else {
			ret = toBigDecimal(n);
		}
		
		@SuppressWarnings("unchecked")
		T t = (T)ret;
		return t;
	}

	public static byte toByte(Object o) {
		Byte b = toByteObject(o);
		if (b == null) {
			return 0;
		}
		return b.byteValue();
	}

	public static Byte toByteObject(Object o) {
		if (o == null) {
			return null;
		}
		
		Byte num;
		if (o instanceof Byte) {
			num = (Byte)o;
		} else if (o instanceof Number) {
			num = Byte.valueOf(((Number)o).byteValue());
		} else if (o instanceof Character) {
			num = Byte.valueOf((byte)((Character)o).charValue());
		} else if (o instanceof String) {
			String s = (String)o;
			if (s.isEmpty()) {
				num = null;
			} else {
				num = Byte.valueOf(s);
			}
		} else if (o instanceof Boolean) {
			num = (((Boolean)o).booleanValue()) ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
		} else if (o instanceof Enum) {
			num = Byte.valueOf((byte)((Enum<?>)o).ordinal());
		} else {
			num = Byte.valueOf(o.toString());
		}
		
		return num;
	}

	public static short toShort(Object o) {
		Short s = toShortObject(o);
		if (s == null) {
			return 0;
		}
		return s.shortValue();
	}

	public static Short toShortObject(Object o) {
		if (o == null) {
			return null;
		}
		
		Short num;
		if (o instanceof Short) {
			num = (Short)o;
		} else if (o instanceof Number) {
			num = Short.valueOf(((Number)o).shortValue());
		} else if (o instanceof Character) {
			num = Short.valueOf((short)((Character)o).charValue());
		} else if (o instanceof String) {
			String s = (String)o;
			if (s.isEmpty()) {
				num = null;
			} else {
				num = Short.valueOf(s);
			}
		} else if (o instanceof Boolean) {
			num = (((Boolean)o).booleanValue()) ? Short.valueOf((short)1) : Short.valueOf((short)0);
		} else if (o instanceof Enum) {
			num = Short.valueOf((short)((Enum<?>)o).ordinal());
		} else {
			num = Short.valueOf(o.toString());
		}
		
		return num;
	}

	public static int toInt(Object o) {
		Integer i = toIntegerObject(o);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	public static Integer toIntegerObject(Object o) {
		if (o == null) {
			return null;
		}
		
		Integer num;
		if (o instanceof Integer) {
			num = (Integer)o;
		} else if (o instanceof Number) {
			num = Integer.valueOf(((Number)o).intValue());
		} else if (o instanceof Character) {
			num = Integer.valueOf((int)((Character)o).charValue());
		} else if (o instanceof String) {
			String s = (String)o;
			if (s.isEmpty()) {
				num = null;
			} else {
				num = Integer.valueOf(s);
			}
		} else if (o instanceof Boolean) {
			num = (((Boolean)o).booleanValue()) ? Integer.valueOf(1) : Integer.valueOf(0);
		} else if (o instanceof Enum) {
			num = Integer.valueOf(((Enum<?>)o).ordinal());
		} else {
			num = Integer.valueOf(o.toString());
		}
		
		return num;
	}

	public static long toLong(Object o) {
		Long l = toLongObject(o);
		if (l == null) {
			return 0L;
		}
		return l.longValue();
	}

	public static Long toLongObject(Object o) {
		if (o == null) {
			return null;
		}
		
		Long num;
		if (o instanceof Long) {
			num = (Long)o;
		} else if (o instanceof Number) {
			num = Long.valueOf(((Number)o).longValue());
		} else if (o instanceof Character) {
			num = Long.valueOf((long)((Character)o).charValue());
		} else if (o instanceof String) {
			String s = (String)o;
			if (s.isEmpty()) {
				num = null;
			} else {
				num = Long.valueOf(s);
			}
		} else if (o instanceof Boolean) {
			num = ((Boolean)o).booleanValue() ? Long.valueOf(1L) : Long.valueOf(0L);
		} else if (o instanceof Enum) {
			num = Long.valueOf((long)((Enum<?>)o).ordinal());
		} else {
			num = Long.valueOf(o.toString());
		}
		
		return num;
	}

	public static float toFloat(Object o) {
		Float f = toFloatObject(o);
		if (f == null) {
			return 0.0F;
		}
		return f.floatValue();
	}

	public static Float toFloatObject(Object o) {
		if (o == null) {
			return null;
		}

		Float num;
		if (o instanceof Float) {
			num = (Float)o;
		} else if (o instanceof Number) {
			num = Float.valueOf(((Number)o).floatValue());
		} else if (o instanceof Character) {
			num = Float.valueOf((float)((Character)o).charValue());
		} else if (o instanceof String) {
			String s = (String)o;
			if (s.isEmpty()) {
				num = null;
			} else {
				num = Float.valueOf(s);
			}
		} else if (o instanceof Boolean) {
			num = (((Boolean)o).booleanValue()) ? Float.valueOf(1.0F) : Float.valueOf(0.0F);
		} else if (o instanceof Enum) {
			num = Float.valueOf((float)((Enum<?>)o).ordinal());
		} else {
			num = Float.valueOf(o.toString());
		}
		
		return num;
	}

	public static double toDouble(Object o) {
		Double d = toDoubleObject(o);
		if (d == null) {
			return 0.0D;
		}
		return d.doubleValue();
	}
	
	public static Double toDoubleObject(Object o) {
		if (o == null) {
			return null;
		}
		
		Double num;
		if (o instanceof Double) {
			num = (Double)o;
		} else if (o instanceof Number) {
			num = Double.valueOf(((Number)o).doubleValue());
		} else if (o instanceof Character) {
			num = Double.valueOf((double)((Character)o).charValue());
		} else if (o instanceof String) {
			String s = (String)o;
			if (s.isEmpty()) {
				num = null;
			} else {
				num = Double.valueOf(s);
			}
		} else if (o instanceof Boolean) {
			num = (((Boolean)o).booleanValue()) ? Double.valueOf(1.0D) : Double.valueOf(0.0D);
		} else if (o instanceof Enum) {
			num = Double.valueOf((double)((Enum<?>)o).ordinal());
		} else {
			num = Double.valueOf(o.toString());
		}
		
		return num;
	}
	
	public static BigInteger toBigInteger(Object o) {
		if (o == null) {
			return null;
		}
		
		BigInteger bigInteger;
		if (o instanceof Byte) {
			bigInteger = BigInteger.valueOf((Byte)o);
		} else if (o instanceof Short) {
			bigInteger = BigInteger.valueOf((Short)o);
		} else if (o instanceof Integer) {
			bigInteger = BigInteger.valueOf((Integer)o);
		} else if (o instanceof Long) {
			bigInteger = BigInteger.valueOf((Long)o);
		} else if (o instanceof Float) {
			bigInteger = BigDecimal.valueOf((Float)o).toBigInteger();
		} else if (o instanceof Double) {
			bigInteger = BigDecimal.valueOf((Double)o).toBigInteger();
		} else if (o instanceof BigInteger) {
			bigInteger = (BigInteger)o;
		} else if (o instanceof BigDecimal) {
			bigInteger = ((BigDecimal)o).toBigInteger();
		} else if (o instanceof AtomicInteger) {
			bigInteger = BigInteger.valueOf(((AtomicInteger)o).longValue());
		} else if (o instanceof AtomicLong) {
			bigInteger = BigInteger.valueOf(((AtomicLong)o).longValue());
		} else if (o instanceof Character) {
			bigInteger = BigInteger.valueOf((long)((Character)o).charValue());
		} else {
			bigInteger = new BigInteger(o.toString());
		}
		
		return bigInteger;
	}
	
	public static BigDecimal toBigDecimal(Object o) {
		if (o == null) {
			return null;
		}
		
		BigDecimal bigDecimal;
		if (o instanceof Byte) {
			bigDecimal = BigDecimal.valueOf((Byte)o);
		} else if (o instanceof Short) {
			bigDecimal = BigDecimal.valueOf((Short)o);
		} else if (o instanceof Integer) {
			bigDecimal = BigDecimal.valueOf((Integer)o);
		} else if (o instanceof Long) {
			bigDecimal = BigDecimal.valueOf((Long)o);
		} else if (o instanceof Float) {
			bigDecimal = BigDecimal.valueOf((Float)o);
		} else if (o instanceof Double) {
			bigDecimal = BigDecimal.valueOf((Double)o);
		} else if (o instanceof BigInteger) {
			bigDecimal = new BigDecimal((BigInteger)o);
		} else if (o instanceof BigDecimal) {
			bigDecimal = (BigDecimal)o;
		} else if (o instanceof AtomicInteger) {
			bigDecimal = BigDecimal.valueOf(((AtomicInteger)o).longValue());
		} else if (o instanceof AtomicLong) {
			bigDecimal = BigDecimal.valueOf(((AtomicLong)o).longValue());
		} else if (o instanceof Character) {
			bigDecimal = BigDecimal.valueOf((long)((Character)o).charValue());
		} else {
			bigDecimal = new BigDecimal(o.toString());
		}
		
		return bigDecimal;
	}
	
	public static AtomicInteger toAtomicInteger(Object o) {
		if (o == null) {
			return null;
		}
		
		return new AtomicInteger(toInt(o));
	}
	
	public static AtomicLong toAtomicLong(Object o) {
		if (o == null) {
			return null;
		}
		
		return new AtomicLong(toLong(o));
	}
	
	public static String toEnNumShortScale(long num, boolean fractionDec) {
		return toEnNumShortScale(BigDecimal.valueOf(num), fractionDec);
	}
	
	public static String toEnNumShortScale(double num, boolean fractionDec) {
		return toEnNumShortScale(BigDecimal.valueOf(num), fractionDec);
	}
	
	public static String toEnNumShortScale(BigInteger num, boolean fractionDec) {
		if (num == null) {
			return null;
		}
		
		return toEnNumShortScale(new BigDecimal(num), fractionDec);
	}
	
	public static String toEnNumShortScale(BigDecimal num, boolean fractionDec) {
		if (num == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		
		boolean negative = (num.signum() < 0);
		if (negative) {
			sb.append("Negative ");
		}
		
		BigInteger numInt = num.toBigInteger();
		if (NumberUtilz.isZero(numInt)) {
			sb.append("Zero ");
		} else {
			String strInt = ((negative) ? numInt.negate() : numInt).toString();
			
			int len = strInt.length();
			boolean hasD1Num = false;
			for (int i = 0; i < len; i++) {
				char c = strInt.charAt(i);
				int d2 = len - i - 1;
				int d1 = d2 % 3;
				
				if (c != '0') {
					if (d1 == 1) {
						char cn = strInt.charAt(i + 1);
						switch (c) {
						case '1':
							switch (cn) {
							case '0': sb.append("Ten "); break;
							case '1': sb.append("Eleven "); break;
							case '2': sb.append("Twelve "); break;
							case '3': sb.append("Thirteen "); break;
							case '4': sb.append("Fourteen "); break;
							case '5': sb.append("Fifteen "); break;
							case '6': sb.append("Sixteen "); break;
							case '7': sb.append("Seventeen "); break;
							case '8': sb.append("Eighteen "); break;
							case '9': sb.append("Nineteen "); break;
							default: throw new NumberParseException("Unsupported character: " + c);
							}
							
							i++;
							
							d2--;
							d1 = d2 % 3;
							break;
						case '2': sb.append("Twenty").append((cn == '0') ? ' ' : '-'); break;
						case '3': sb.append("Thirty").append((cn == '0') ? ' ' : '-'); break;
						case '4': sb.append("Forty").append((cn == '0') ? ' ' : '-'); break;
						case '5': sb.append("Fifty").append((cn == '0') ? ' ' : '-'); break;
						case '6': sb.append("Sixty").append((cn == '0') ? ' ' : '-'); break;
						case '7': sb.append("Seventy").append((cn == '0') ? ' ' : '-'); break;
						case '8': sb.append("Eighty").append((cn == '0') ? ' ' : '-'); break;
						case '9': sb.append("Ninety").append((cn == '0') ? ' ' : '-'); break;
						default: throw new NumberParseException("Unsupported character: " + c);
						}
					} else { // d1 = 0 or 2
						switch (c) {
						case '1': sb.append("One "); break;
						case '2': sb.append("Two "); break;
						case '3': sb.append("Three "); break;
						case '4': sb.append("Four "); break;
						case '5': sb.append("Five "); break;
						case '6': sb.append("Six "); break;
						case '7': sb.append("Seven "); break;
						case '8': sb.append("Eight "); break;
						case '9': sb.append("Nine "); break;
						default: throw new NumberParseException("Unsupported character: " + c);
						}
						
						if (d1 == 2) {
							sb.append("Hundred ");
						}
					}
					
					hasD1Num = true;
				}
				if (d1 == 0 && hasD1Num) {
					switch (d2) {
					case 0: break;
					case 3: sb.append("Thousand "); break;
					case 6: sb.append("Million "); break;
					case 9: sb.append("Billion "); break;
					case 12: sb.append("Trillion "); break;
					case 15: sb.append("Quadrillion "); break;
					case 18: sb.append("Quintillion "); break;
					case 21: sb.append("Sextillion "); break;
					case 24: sb.append("Septillion "); break;
					default:  throw new NumberParseException("Number is too big. Scale: " + d2);
					}
				}
				
				if (d1 == 0) {
					hasD1Num = false;
				}
			}
		}
		
		int scale = num.scale();
		if (0 < scale) {
			BigInteger numDec = num.subtract(new BigDecimal(numInt)).unscaledValue();
			String strDec = ((negative) ? numDec.negate() : numDec).toString();
			
			if (fractionDec) {
				sb.append("and ").append(strDec).append("/1");
				StringUtilz.repeat(sb, "0", scale);
			} else {
				sb.append("point ");
				StringUtilz.repeat(sb, "Zero ", scale - strDec.length());
				for (int i = 0; i < strDec.length(); i++) {
					char c = strDec.charAt(i);
					
					switch (c) {
					case '0': sb.append("Zero "); break;
					case '1': sb.append("One "); break;
					case '2': sb.append("Two "); break;
					case '3': sb.append("Three "); break;
					case '4': sb.append("Four "); break;
					case '5': sb.append("Five "); break;
					case '6': sb.append("Six "); break;
					case '7': sb.append("Seven "); break;
					case '8': sb.append("Eight "); break;
					case '9': sb.append("Nine "); break;
					default: throw new NumberParseException("Unsupported character: " + c);
					}
				}
			}
		}
		
		return sb.toString().trim();
	}
	
	public static BigDecimal parseEnNumShortScale(String enNum) {
		return parseEnNumShortScale(enNum, RoundingMode.HALF_UP, -1);
	}
	
	public static BigDecimal parseEnNumShortScale(String enNum, RoundingMode decimalRoundingMode, int fixedDecimalScale) {
		if (enNum == null) {
			return null;
		}
		
		enNum = enNum.toLowerCase().trim();
		
		boolean negative = false;
		String enNumUnsigned = enNum;
		if (enNumUnsigned.startsWith("negative ")) {
			negative = true;
			enNumUnsigned = enNumUnsigned.substring("negative ".length());
		} else if (enNumUnsigned.startsWith("minus ")) {
			negative = true;
			enNumUnsigned = enNumUnsigned.substring("minus ".length());
		} else if (enNumUnsigned.startsWith("-")) {
			negative = true;
			enNumUnsigned = enNumUnsigned.substring("-".length());
		}
		
		enNumUnsigned = enNumUnsigned.trim();
		if (enNumUnsigned.isEmpty()) {
			throw new NumberParseException("Invalid value: " + enNum);
		}
		
		BigDecimal num = null;
		
		Matcher matcherDecPoint = EN_NUM_DECIMAL_POINT_PATTERN.matcher(enNumUnsigned);
		if (matcherDecPoint.matches()) {
			// Decimal : xxx point xxx
			String iNumStr = matcherDecPoint.group(1);
			String dNumStr = matcherDecPoint.group(2);
			
			BigDecimal iNum = parseEnNumShortScaleUnsignedIntPart(iNumStr);
			BigDecimal dNum = parseEnNumShortScaleUnsignedDecPart(dNumStr, fixedDecimalScale, decimalRoundingMode);

			num = iNum.add(dNum);
		} else {
			// Decimal : xxx and xx/xxx
			Matcher matcherDecAnd = EN_NUM_DECIMAL_AND_PATTERN.matcher(enNumUnsigned);
			if (matcherDecAnd.matches()) {
				String iNumStr = matcherDecAnd.group(1);
				String dnNumStr = matcherDecAnd.group(2);
				String ddNumStr = matcherDecAnd.group(3);
				
				BigDecimal iNum = parseEnNumShortScaleUnsignedIntPart(iNumStr);
				int dNumScale;
				if (0 <= fixedDecimalScale) {
					dNumScale = fixedDecimalScale;
				} else {
					dNumScale = ddNumStr.length() - 1;
				}
				BigDecimal dNum = new BigDecimal(dnNumStr).divide(new BigDecimal(ddNumStr), dNumScale, decimalRoundingMode);

				num = iNum.add(dNum);
			} else {
				// Integer
				num = parseEnNumShortScaleUnsignedIntPart(enNumUnsigned);
			}
		}
		
		if (num != null) {
			if (negative) {
				num = num.negate();
			}
		}
		
		return num;
	}
	
	private static BigDecimal parseEnNumShortScaleUnsignedIntPart(String enNum) {
		return parseEnNumShortScaleUnsignedPart(enNum, false, -1, null);
	}
	
	private static BigDecimal parseEnNumShortScaleUnsignedDecPart(String enNum, int fixedDecimalScale, RoundingMode decimalRoundingMode) {
		return parseEnNumShortScaleUnsignedPart(enNum, true, fixedDecimalScale, decimalRoundingMode);
	}
	
	private static BigDecimal parseEnNumShortScaleUnsignedPart(String enNum, boolean decimalPart, int fixedDecimalScale, RoundingMode decimalRoundingMode) {
		if (enNum == null) {
			return null;
		}
		
		enNum = enNum.toLowerCase().trim();
		
		enNum = enNum.replace(" and ", " ");
		enNum = enNum.replace("-", " ");
		enNum = enNum.replace(",", "");
		
		BigDecimal num = BigDecimal.ZERO;
		BigDecimal n = BigDecimal.ZERO;
		int d = -1;
		boolean xty = false;
		boolean prevXty = false;
		int zeroPrefixCount = 0;
		
		String[] tokens = enNum.split("\\s+");
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			
			boolean isLast = (i + 1 == tokens.length);
			
			prevXty = xty;
			xty = false;
			
			boolean parsed = false;
			char c0 = token.charAt(0);
			if ('0' <= c0 && c0 <= '9') {
				try {
					n = add(n, new BigDecimal(token), true);
					parsed = true;
				} catch (Exception e) {
					// NOP
				}
			}
			
			if (!parsed) {
				switch (token) {
				case "zero":
					n = add(n, 0, !prevXty);
					if (BigDecimal.ZERO.equals(n)) {
						zeroPrefixCount++;
					}
					break;
				case "one": n = add(n, 1L, !prevXty); break;
				case "two": n = add(n, 2L, !prevXty); break;
				case "three": n = add(n, 3L, !prevXty); break;
				case "four": n = add(n, 4L, !prevXty); break;
				case "five": n = add(n, 5L, !prevXty); break;
				case "six": n = add(n, 6L, !prevXty); break;
				case "seven": n = add(n, 7L, !prevXty); break;
				case "eight": n = add(n, 8L, !prevXty); break;
				case "nine": n = add(n, 9L, !prevXty); break;
				case "ten": n = add(n, 10L, !prevXty); break;
				case "eleven": n = add(n, 11L, !prevXty); break;
				case "twelve": n = add(n, 12L, !prevXty); break;
				case "thirteen": n = add(n, 13L, !prevXty); break;
				case "fourteen": n = add(n, 14L, !prevXty); break;
				case "fifteen": n = add(n, 15L, !prevXty); break;
				case "sixteen": n = add(n, 16L, !prevXty); break;
				case "seventeen": n = add(n, 17L, !prevXty); break;
				case "eighteen": n = add(n, 18L, !prevXty); break;
				case "nineteen": n = add(n, 19L, !prevXty); break;
				case "twenty": n = add(n, 20L, !prevXty); xty = true; break;
				case "thirty": n = add(n, 30L, !prevXty); xty = true; break;
				case "forty": n = add(n, 40L, !prevXty); xty = true; break;
				case "fifty": n = add(n, 50L, !prevXty); xty = true; break;
				case "sixty": n = add(n, 60L, !prevXty); xty = true; break;
				case "seventy": n = add(n, 70L, !prevXty); xty = true; break;
				case "eighty": n = add(n, 80L, !prevXty); xty = true; break;
				case "ninety": n = add(n, 90L, !prevXty); xty = true; break;
				case "hundred": n = n.scaleByPowerOfTen(2); xty = true; break;
				case "thousand": d = 3; break;
				case "million": d = 6; break;
				case "billion": d = 9; break;
				case "trillion": d = 12; break;
				case "quadrillion": d = 15; break;
				case "quintillion": d = 18; break;
				case "sextillion": d = 21; break;
				case "septillion": d = 24; break;
				default: throw new NumberParseException("Unsupported word: " + token);
				}
			}
			
			if (d != -1) {
				num = num.add(n.scaleByPowerOfTen(d));
				
				n = BigDecimal.ZERO;
				d = -1;
			} else if (isLast) {
				num = num.add(n);
			}
		}
		
		if (decimalPart) {
			// convert to decimal part
			int len;
			if (0 < zeroPrefixCount && BigDecimal.ZERO.equals(num)) {
				// avoid double count
				len = zeroPrefixCount;
			} else {
				len = zeroPrefixCount + digitLength(num);
			}
			

			if (0 <= fixedDecimalScale) {
				// fixed scale
				if (len < fixedDecimalScale) {
					// pad scale
					num = num.scaleByPowerOfTen(-fixedDecimalScale);
				} else if (fixedDecimalScale < len) {
					// round
					if (decimalRoundingMode == null) {
						throw new NumberParseException("Decimal overflow. (Expected: <= " + fixedDecimalScale + ", Actual:" + len + ")");
					}
					num = num.scaleByPowerOfTen(-len).setScale(fixedDecimalScale, decimalRoundingMode);
				} else {
					// decimalFixedScale = actual scale
					num = num.scaleByPowerOfTen(-len);
				}
			} else {
				num = num.scaleByPowerOfTen(-len);
			}
		}
		
		return num;
	}
	
	private static BigDecimal add(BigDecimal baseNum, long num, boolean asDigit) {
		return add(baseNum, BigDecimal.valueOf(num), asDigit);
	}
	
	private static BigDecimal add(BigDecimal baseNum, BigDecimal num, boolean asDigit) {
		if (baseNum == null) {
			return num;
		}
		if (num == null) {
			return baseNum;
		}
		
		if (asDigit) {
			return addDigit(baseNum, num);
		} else {
			return baseNum.add(num);
		}
	}
	
	
	public static String toJPNum(long num) {
		return toJPNum(num, false, false, false);
	}
	
	public static String toJPNum(long num, boolean showOne, boolean useDaiji, boolean useObsoleteDaiji) {
		return toJPNum(BigDecimal.valueOf(num), showOne, useDaiji, useObsoleteDaiji);
	}
	
	public static String toJPNum(double num) {
		return toJPNum(num, false, false, false);
	}
	
	public static String toJPNum(double num, boolean showOne, boolean useDaiji, boolean useObsoleteDaiji) {
		return toJPNum(BigDecimal.valueOf(num), showOne, useDaiji, useObsoleteDaiji);
	}
	
	public static String toJPNum(BigInteger num) {
		return toJPNum(num, false, false, false);
	}
	
	public static String toJPNum(BigInteger num, boolean showOne, boolean useDaiji, boolean useObsoleteDaiji) {
		if (num == null) {
			return null;
		}
		
		return toJPNum(new BigDecimal(num), showOne, useDaiji, useObsoleteDaiji);
	}
	
	public static String toJPNum(BigDecimal num) {
		return toJPNum(num, false, false, false);
	}
	
	public static String toJPNum(BigDecimal num, boolean showOne, boolean useDaiji, boolean useObsoleteDaiji) {
		useObsoleteDaiji = useDaiji && useObsoleteDaiji;
		
		if (num == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		
		boolean negative = (num.signum() < 0);
		if (negative) {
			sb.append("−");
		}
		
		BigInteger numInt = num.toBigInteger();
		if (NumberUtilz.isZero(numInt)) {
			sb.append((useDaiji) ? "零" : "〇");
		} else {
			String strInt = ((negative) ? numInt.negate() : numInt).toString();
			
			int len = strInt.length();
				
			boolean hasD1Num = false;
			for (int i = 0; i < len; i++) {
				char c = strInt.charAt(i);
				int d2 = len - i - 1;
				int d1 = d2 % 4;
				
				if (c != '0') {
					switch (c) {
					case '1':
						if (showOne || d1 == 0 || d2 == 0) {
							sb.append((useDaiji) ? ((useObsoleteDaiji) ? '壹' : '壱') : '一');
						}
						break;
					case '2': sb.append((useDaiji) ? ((useObsoleteDaiji) ? '貳' : '弐') : '二'); break;
					case '3': sb.append((useDaiji) ? ((useObsoleteDaiji) ? '參' : '参') : '三'); break;
					case '4': sb.append((useDaiji) ? '肆' : '四'); break;
					case '5': sb.append((useDaiji) ? '伍' : '五'); break;
					case '6': sb.append((useDaiji) ? '陸' : '六'); break;
					case '7': sb.append((useDaiji) ? ((useObsoleteDaiji) ? '柒' : '漆') : '七'); break;
					case '8': sb.append((useDaiji) ? '捌' : '八'); break;
					case '9': sb.append((useDaiji) ? '玖' : '九'); break;
					default: assert false;
					}
					
					switch (d1) {
					case 0: break;
					case 1: sb.append((useDaiji) ? '拾' : '十'); break;
					case 2: sb.append((useDaiji) ? '陌' : '百'); break;
					case 3: sb.append((useDaiji) ? '阡' : '千'); break;
					default: assert false;
					}
					
					hasD1Num = true;
				}
				if (d1 == 0 && hasD1Num) {
					switch (d2) {
					case 0: break;
					case 4: sb.append((useDaiji) ? '萬' : '万'); break;
					case 8: sb.append('億'); break;
					case 12: sb.append('兆'); break;
					case 16: sb.append('京'); break;
					case 20: sb.append('垓'); break;
					case 24: sb.append("秭"); break;
					case 28: sb.append('穣'); break;
					case 32: sb.append('溝'); break;
					case 36: sb.append('澗'); break;
					case 40: sb.append('正'); break;
					case 44: sb.append('載'); break;
					case 48: sb.append('極'); break;
					case 52: sb.append("恒河沙"); break;
					case 56: sb.append("阿僧祇"); break;
					case 60: sb.append("那由他"); break;
					case 64: sb.append("不可思議"); break;
					case 68: sb.append("無量大数"); break;
					default: throw new NumberParseException("Too big number");
					}
				}
				
				if (d1 == 0) {
					hasD1Num = false;
				}
			}
		}
		
		int scale = num.scale();
		if (0 < scale) {
			BigInteger numDec = num.subtract(new BigDecimal(numInt)).unscaledValue();
			String strDec = ((negative) ? numDec.negate() : numDec).toString();
			
			sb.append("・");
			StringUtilz.repeat(sb, ((useDaiji) ? "零" : "〇"), scale - strDec.length());
			for (int i = 0; i < strDec.length(); i++) {
				char c = strDec.charAt(i);
				
				switch (c) {
				case '0': sb.append((useDaiji) ? "零" : "〇"); break;
				case '1': sb.append((useDaiji) ? ((useObsoleteDaiji) ? '壹' : '壱') : '一'); break;
				case '2': sb.append((useDaiji) ? ((useObsoleteDaiji) ? '貳' : '弐') : '二'); break;
				case '3': sb.append((useDaiji) ? ((useObsoleteDaiji) ? '參' : '参') : '三'); break;
				case '4': sb.append((useDaiji) ? '肆' : '四'); break;
				case '5': sb.append((useDaiji) ? '伍' : '五'); break;
				case '6': sb.append((useDaiji) ? '陸' : '六'); break;
				case '7': sb.append((useDaiji) ? ((useObsoleteDaiji) ? '柒' : '漆') : '七'); break;
				case '8': sb.append((useDaiji) ? '捌' : '八'); break;
				case '9': sb.append((useDaiji) ? '玖' : '九'); break;
				default: throw new NumberParseException("Unsupported character: " + c);
				}
			}
		}
		
		return sb.toString();
	}
	
	public static BigDecimal parseJPNum(String jpNum) {
		if (jpNum == null) {
			return null;
		}
		
		jpNum = jpNum.toLowerCase().trim();
		
		boolean negative = false;
		String jpNumUnsigned = jpNum;
		if (jpNumUnsigned.startsWith("−")) {
			negative = true;
			jpNumUnsigned = jpNumUnsigned.substring("−".length());
		} else if (jpNumUnsigned.startsWith("-")) {
			negative = true;
			jpNumUnsigned = jpNumUnsigned.substring("-".length());
		}
		
		jpNumUnsigned = jpNumUnsigned.trim();
		if (jpNumUnsigned.isEmpty()) {
			throw new NumberParseException("Invalid value: " + jpNum);
		}
		
		BigDecimal num = parseJPNumUnsignedPart(jpNumUnsigned);
		
		if (num != null) {
			if (negative) {
				num = num.negate();
			}
		}
		
		return num;
	}
	
	private static BigDecimal parseJPNumUnsignedPart(String jpNum) {
		if (jpNum == null) {
			return null;
		}
		
		jpNum = StringUtilz.replaceAll(
				jpNum,
				new String[] { "秭", "恒河沙", "阿僧祇", "那由他", "那由多", "不可思議", "無量大数" },
				new String[] { "杼", "恒", "阿", "那", "那", "不", "無" }
				);
		
		BigDecimal num = BigDecimal.ZERO;
		BigDecimal n = BigDecimal.ZERO;
		BigDecimal n1 = null;
		int d1 = -1;
		int d2 = -1;
		int prevD1 = -1;
		int prevD2 = -1;
		boolean decimalPart = false;
		for (int i = 0; i < jpNum.length(); i++) {
			char c = jpNum.charAt(i);
			switch (c) {
			case '0':
			case '０':
			case '〇':
			case '零':
				n1 = addDigit(n1, 0L, decimalPart);
				break;
			case '1':
			case '１':
			case '一':
			case '壱':
			case '壹':
			case '弌':
				n1 = addDigit(n1, 1L, decimalPart);
				break;
			case '2':
			case '２':
			case '二':
			case '弐':
			case '貳':
			case '弍':
			case '贰': // Chinese
				n1 = addDigit(n1, 2L, decimalPart);
				break;
			case '3':
			case '３':
			case '三':
			case '参':
			case '參':
			case '弎':
			case '叁': // Chinese
			case '叄': // Chinese
				n1 = addDigit(n1, 3L, decimalPart);
				break;
			case '4':
			case '４':
			case '四':
			case '肆':
			case '亖':
				n1 = addDigit(n1, 4L, decimalPart);
				break;
			case '5':
			case '５':
			case '五':
			case '伍':
				n1 = addDigit(n1, 5L, decimalPart);
				break;
			case '6':
			case '６':
			case '六':
			case '陸':
			case '陆': // Chinese
				n1 = addDigit(n1, 6L, decimalPart);
				break;
			case '7':
			case '７':
			case '七':
			case '漆':
			case '柒':
			case '質':
				n1 = addDigit(n1, 7L, decimalPart);
				break;
			case '8':
			case '８':
			case '八':
			case '捌':
				n1 = addDigit(n1, 8L, decimalPart);
				break;
			case '9':
			case '９':
			case '九':
			case '玖':
				n1 = addDigit(n1, 9L, decimalPart);
				break;
			case '十':
			case '拾':
			case '什':
				d1 = 1;
				break;
			case '廿':
			case '念': // Chinese
				if (n1 != null) {
					throw new NumberParseException("Invalid format around '" + c + "'");
				}
				n1 = BigDecimal.valueOf(2L);
				d1 = 1;
				break;
			case '卅':
			case '丗':
				if (n1 != null) {
					throw new NumberParseException("Invalid format around '" + c + "'");
				}
				n1 = BigDecimal.valueOf(3L);
				d1 = 1;
				break;
			case '百':
			case '佰':
			case '陌':
				d1 = 2;
				break;
			case '千':
			case '仟':
			case '阡':
				d1 = 3;
				break;
			case '万':
			case '萬':
				d2 = 4;
				break;
			case '億':
				d2 = 8;
				break;
			case '兆':
				d2 = 12;
				break;
			case '京':
				d2 = 16;
				break;
			case '垓':
				d2 = 20;
				break;
			case '杼': // 秭
				d2 = 24;
				break;
			case '穣':
				d2 = 28;
				break;
			case '溝':
				d2 = 32;
				break;
			case '澗':
				d2 = 36;
				break;
			case '正':
				d2 = 40;
				break;
			case '載':
				d2 = 44;
				break;
			case '極':
				d2 = 48;
				break;
			case '恒':
				d2 = 52;
				break;
			case '阿':
				d2 = 56;
				break;
			case '那':
				d2 = 60;
				break;
			case '不':
				d2 = 64;
				break;
			case '無':
				d2 = 68;
				break;
			case '･':
			case '.':
			case '・':
			case '．':
				decimalPart = true;
				break;
			default:
				throw new NumberParseException("Unsupported character: " + c);
			}
			
			boolean isLast = (i + 1 == jpNum.length());
			
			
			if (isLast) {
				if (d2 == -1) {
					d2 = 0;
					
					if (d1 == -1) {
						d1 = 0;
					}
				}
			}

			if (prevD1 != -1 && prevD1 < d1) {
				throw new NumberParseException("Invalid format: " + jpNum);
			}
			prevD1 = d1;

			if (prevD2 != -1 && prevD2 < d2) {
				throw new NumberParseException("Invalid format: " + jpNum);
			}
			prevD2 = d2;
			
			
			if (d1 != -1) {
				if (n1 == null) {
					n1 = BigDecimal.ONE;
				}
				
				if (d1 == 0) {
					n = n.add(n1);
				} else {
					n = n.add(n1.scaleByPowerOfTen(d1));
				}
				
				n1 = null;
				d1 = -1;
				decimalPart = false;
			}
			
			if (d2 != -1) {
				if (d1 == -1 && n1 != null) {
					n = n.add(n1);
				}
				
				if (d2 == 0) {
					num = num.add(n);
				} else {
					num = num.add(n.scaleByPowerOfTen(d2));
				}
				
				n = BigDecimal.ZERO;
				n1 = null;
				d1 = -1;
				d2 = -1;
				prevD1 = -1;
				decimalPart = false;
			}
		}
		
		return num;
	}
	
	public static byte[] toByteArray(Number number) {
		if (number == null) {
			return null;
		}
		
		if (number instanceof Byte) {
			byte n = number.byteValue();
			return new byte[]{
					n
					};
		} else if (number instanceof Short) {
			short n = number.shortValue();
			return new byte[]{
					(byte)((n >> 0) & 0xff),
					(byte)((n >> 8) & 0xff),
					};
		} else if (number instanceof Integer || number instanceof AtomicInteger) {
			int n = number.intValue();
			return new byte[]{
					(byte)((n >> 0) & 0xff),
					(byte)((n >> 8) & 0xff),
					(byte)((n >> 16) & 0xff),
					(byte)((n >> 24) & 0xff),
					};
		} else if (number instanceof Long || number instanceof AtomicLong) {
			long n = number.longValue();
			return new byte[]{
					(byte)((n >> 0) & 0xff),
					(byte)((n >> 8) & 0xff),
					(byte)((n >> 16) & 0xff),
					(byte)((n >> 24) & 0xff),
					(byte)((n >> 32) & 0xff),
					(byte)((n >> 40) & 0xff),
					(byte)((n >> 48) & 0xff),
					(byte)((n >> 56) & 0xff),
					};
		} else if (number instanceof Float) {
			int n = Float.floatToRawIntBits(number.floatValue());
			return new byte[]{
					(byte)((n >> 0) & 0xff),
					(byte)((n >> 8) & 0xff),
					(byte)((n >> 16) & 0xff),
					(byte)((n >> 24) & 0xff),
					};
		} else if (number instanceof Double) {
			long n = Double.doubleToRawLongBits(number.doubleValue());
			return new byte[]{
					(byte)((n >> 0) & 0xff),
					(byte)((n >> 8) & 0xff),
					(byte)((n >> 16) & 0xff),
					(byte)((n >> 24) & 0xff),
					(byte)((n >> 32) & 0xff),
					(byte)((n >> 40) & 0xff),
					(byte)((n >> 48) & 0xff),
					(byte)((n >> 56) & 0xff),
					};
		} else if (number instanceof BigInteger) {
			BigInteger n = ((BigInteger)number);
			return n.toByteArray();
		} else if (number instanceof BigDecimal) {
			BigDecimal n = ((BigDecimal)number);
			String s = n.toString();
			return s.getBytes(Charset.forName("US-ASCII"));
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public static BigDecimal addDigit(BigDecimal baseNum, long num) {
		return addDigit(baseNum, num, false);
	}
	
	public static BigDecimal addDigit(BigDecimal baseNum, long num, boolean decimalPart) {
		return addDigit(baseNum, BigDecimal.valueOf(num), decimalPart);
	}
	
	public static BigDecimal addDigit(BigDecimal baseNum, BigDecimal num) {
		return addDigit(baseNum, num, false);
	}
	
	public static BigDecimal addDigit(BigDecimal baseNum, BigDecimal num, boolean decimalPart) {
		if (baseNum == null) {
			return num;
		}
		if (num == null) {
			return baseNum;
		}
		
		int fLen = digitLengthDecimalPart(baseNum);
		if (decimalPart || 0 < fLen) {
			return baseNum.add(num.scaleByPowerOfTen(-fLen - digitLengthIntegerPart(num)));
		} else {
			return baseNum.scaleByPowerOfTen(digitLength(num)).add(num);
		}
	}
	
	public static String toString(Number value, String pattern) {
		if (value == null) {
			return null;
		}

		DecimalFormat df = new DecimalFormat(pattern);
		
		return df.format(value);
	}
}
