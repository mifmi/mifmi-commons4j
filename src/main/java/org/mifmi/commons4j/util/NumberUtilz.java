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
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.mifmi.commons4j.util.exception.NumberParseException;

/**
 * Helper methods for working with Number instances.
 * 
 * @author mozq
 */
public final class NumberUtilz {
	
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
	
	public static String toJPNum(long num) {
		return toJPNum(num, false, false, false);
	}
	
	public static String toJPNum(long num, boolean showOne, boolean useDaiji, boolean useObsoleteDaiji) {
		return toJPNum(String.valueOf(num), showOne, useDaiji, useObsoleteDaiji);
	}
	
	public static String toJPNum(BigInteger num) {
		return toJPNum(num, false, false, false);
	}
	
	public static String toJPNum(BigInteger num, boolean showOne, boolean useDaiji, boolean useObsoleteDaiji) {
		if (num == null) {
			return null;
		}
		
		return toJPNum(num.toString(), showOne, useDaiji, useObsoleteDaiji);
	}
	
	public static String toJPNum(String num) {
		return toJPNum(num, false, false, false);
	}
	
	public static String toJPNum(String num, boolean showOne, boolean useDaiji, boolean useObsoleteDaiji) {
		useObsoleteDaiji = useDaiji && useObsoleteDaiji;
		
		if (num == null) {
			return null;
		}
		
		if (num.equals("0")) {
			return (useObsoleteDaiji) ? "零" : "〇";
		}
		
		StringBuilder jpNumSb = new StringBuilder();
		
		int len = num.length();
		boolean hasD1Num = false;
		for (int i = 0; i < len; i++) {
			char c = num.charAt(i);
			int d2 = len - i - 1;
			int d1 = d2 % 4;
			
			if (c != '0') {
				switch (c) {
				case '1':
					if (showOne || d1 == 0 || d2 == 0) {
						jpNumSb.append((useDaiji) ? ((useObsoleteDaiji) ? '壹' : '壱') : '一');
					}
					break;
				case '2': jpNumSb.append((useDaiji) ? ((useObsoleteDaiji) ? '貳' : '弐') : '二'); break;
				case '3': jpNumSb.append((useDaiji) ? ((useObsoleteDaiji) ? '參' : '参') : '三'); break;
				case '4': jpNumSb.append((useObsoleteDaiji) ? '肆' : '四'); break;
				case '5': jpNumSb.append((useObsoleteDaiji) ? '伍' : '五'); break;
				case '6': jpNumSb.append((useObsoleteDaiji) ? '陸' : '六'); break;
				case '7': jpNumSb.append((useObsoleteDaiji) ? '柒' : '七'); break;
				case '8': jpNumSb.append((useObsoleteDaiji) ? '捌' : '八'); break;
				case '9': jpNumSb.append((useObsoleteDaiji) ? '玖' : '九'); break;
				default: return null;
				}
				
				switch (d1) {
				case 0: break;
				case 1: jpNumSb.append((useDaiji) ? '拾' : '十'); break;
				case 2: jpNumSb.append('百'); break;
				case 3: jpNumSb.append((useObsoleteDaiji) ? '佰' : '千'); break;
				default: return null;
				}
				
				hasD1Num = true;
			}
			if (d1 == 0 && hasD1Num) {
				switch (d2) {
				case 0: break;
				case 4: jpNumSb.append((useObsoleteDaiji) ? '萬' : '万'); break;
				case 8: jpNumSb.append('億'); break;
				case 12: jpNumSb.append('兆'); break;
				case 16: jpNumSb.append('京'); break;
				case 20: jpNumSb.append('垓'); break;
				case 24: jpNumSb.append("秭"); break;
				case 28: jpNumSb.append('穣'); break;
				case 32: jpNumSb.append('溝'); break;
				case 36: jpNumSb.append('澗'); break;
				case 40: jpNumSb.append('正'); break;
				case 44: jpNumSb.append('載'); break;
				case 48: jpNumSb.append('極'); break;
				case 52: jpNumSb.append("恒河沙"); break;
				case 56: jpNumSb.append("阿僧祇"); break;
				case 60: jpNumSb.append("那由他"); break;
				case 64: jpNumSb.append("不可思議"); break;
				case 68: jpNumSb.append("無量大数"); break;
				default: return null;
				}
			}
			
			if (d1 == 0) {
				hasD1Num = false;
			}
		}
		return jpNumSb.toString();
	}
	
	public static BigInteger parseJPNum(String jpNum) {
		if (jpNum == null || jpNum.isEmpty()) {
			return null;
		}
		
		jpNum = jpNum.replace("秭", "杼");
		jpNum = jpNum.replace("恒河沙", "恒");
		jpNum = jpNum.replace("阿僧祇", "阿");
		jpNum = jpNum.replace("那由他", "那");
		jpNum = jpNum.replace("那由多", "那");
		jpNum = jpNum.replace("不可思議", "不");
		jpNum = jpNum.replace("無量大数", "無");
		
		BigInteger num = BigInteger.ZERO;
		BigInteger n = BigInteger.ZERO;
		BigInteger n1 = null;
		int d1 = -1;
		int d2 = -1;
		int prevD1 = -1;
		int prevD2 = -1;
		for (int i = 0; i < jpNum.length(); i++) {
			char c = jpNum.charAt(i);
			switch (c) {
			case '0':
			case '０':
			case '〇':
			case '零':
				if (n1 == null) {
					n1 = BigInteger.ZERO;
				} else {
					n1 = n1.multiply(BigInteger.TEN);
				}
				break;
			case '1':
			case '１':
			case '一':
			case '壱':
			case '壹':
			case '弌':
				if (n1 == null) {
					n1 = BigInteger.ONE;
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.ONE);
				}
				break;
			case '2':
			case '２':
			case '二':
			case '弐':
			case '貳':
			case '弍':
			case '贰': // Chinese
				if (n1 == null) {
					n1 = BigInteger.valueOf(2);
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.valueOf(2));
				}
				break;
			case '3':
			case '３':
			case '三':
			case '参':
			case '參':
			case '弎':
			case '叁': // Chinese
			case '叄': // Chinese
				if (n1 == null) {
					n1 = BigInteger.valueOf(3);
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.valueOf(3));
				}
				break;
			case '4':
			case '４':
			case '四':
			case '肆':
			case '亖':
				if (n1 == null) {
					n1 = BigInteger.valueOf(4);
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.valueOf(4));
				}
				break;
			case '5':
			case '５':
			case '五':
			case '伍':
				if (n1 == null) {
					n1 = BigInteger.valueOf(5);
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.valueOf(5));
				}
				break;
			case '6':
			case '６':
			case '六':
			case '陸':
			case '陆': // Chinese
				if (n1 == null) {
					n1 = BigInteger.valueOf(6);
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.valueOf(6));
				}
				break;
			case '7':
			case '７':
			case '七':
			case '漆':
			case '柒':
			case '質':
				if (n1 == null) {
					n1 = BigInteger.valueOf(7);
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.valueOf(7));
				}
				break;
			case '8':
			case '８':
			case '八':
			case '捌':
				if (n1 == null) {
					n1 = BigInteger.valueOf(8);
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.valueOf(8));
				}
				break;
			case '9':
			case '９':
			case '九':
			case '玖':
				if (n1 == null) {
					n1 = BigInteger.valueOf(9);
				} else {
					n1 = n1.multiply(BigInteger.TEN).add(BigInteger.valueOf(9));
				}
				break;
			case '十':
			case '拾':
			case '什':
				d1 = 1;
				break;
			case '廿':
			case '念': // Chinese
				if (n1 != null) {
					return null;
				}
				n1 = BigInteger.valueOf(2);
				d1 = 1;
				break;
			case '卅':
			case '丗':
				if (n1 != null) {
					return null;
				}
				n1 = BigInteger.valueOf(3);
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
			default:
				return null;
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
				return null;
			}
			prevD1 = d1;

			if (prevD2 != -1 && prevD2 < d2) {
				return null;
			}
			prevD2 = d2;
			
			
			if (d1 != -1) {
				if (n1 == null) {
					n1 = BigInteger.ONE;
				}
				
				if (d1 == 0) {
					n = n.add(n1);
				} else {
					n = n.add(n1.multiply(BigInteger.TEN.pow(d1)));
				}
				
				n1 = null;
				d1 = -1;
			}
			
			if (d2 != -1) {
				if (d1 == -1 && n1 != null) {
					n = n.add(n1);
				}
				
				if (d2 == 0) {
					num = num.add(n);
				} else {
					num = num.add(n.multiply(BigInteger.TEN.pow(d2)));
				}
				
				n = BigInteger.ZERO;
				n1 = null;
				d1 = -1;
				d2 = -1;
				prevD1 = -1;
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
	
	public static String toString(Number value, String pattern) {
		if (value == null) {
			return null;
		}

		DecimalFormat df = new DecimalFormat(pattern);
		
		return df.format(value);
	}
}
