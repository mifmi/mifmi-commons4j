/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.text.format;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mifmi.commons4j.text.format.NamedFormatter;

public class NamedFormatterTest {

	public NamedFormatterTest() {
	}
	
	@Test
	public void testSampleCode() throws Exception {
		// Define value with name
		Map<String, Object> vars = new HashMap<>();
		vars.put("varNull", null);
		vars.put("varStr", "val");
		vars.put("varInt", 123456);
		vars.put("varDec", new BigDecimal("123456.789"));
		vars.put("varDate", OffsetDateTime.parse("2000-01-23T04:05:06.123456789+09:00"));
		vars.put("varBool", true);
		
		
		//// Refer value as '${varName}'
		
		// Refer value
		assertEquals("val", new NamedFormatter("${varStr}").format(vars));
		assertEquals("FOOvalBAR123456BAZ", new NamedFormatter("FOO${varStr}BAR${varInt}BAZ").format(vars));
		
		
		//// Format value as '${varName%format}'
		
		// Format Integer value
		assertEquals("123,456", new NamedFormatter("${varInt%#,##0}").format(vars));
		assertEquals("123,456.000000", new NamedFormatter("${varInt%#,##0.000000}").format(vars));
		
		// Format Decimal value
		assertEquals("123,457", new NamedFormatter("${varDec%#,##0}").format(vars));
		assertEquals("123,456.789000", new NamedFormatter("${varDec%#,##0.000000}").format(vars));
		
		// Format Date value
		assertEquals("20000123 040506123", new NamedFormatter("${varDate%uuuuMMdd HHmmssSSS}").format(vars));
		
		
		//// Choice value as '${varName/val1:ret1/val2:ret2/default:retDefault}'
		//// Operator:
		////   != is Not
		////   < is Less than
		////   <= is Less than equals
		////   > is Greater than
		////   >= is Greater than equals
		
		// Choice String value
		assertEquals("Z", new NamedFormatter("${varStr/xxx:X/yyy:Y/default:Z}").format(vars));
		assertEquals("Y", new NamedFormatter("${varStr/xxx:X/val:Y/default:Z}").format(vars));
		assertEquals("Z", new NamedFormatter("${varStr/xxx:X/!=val:Y/default:Z}").format(vars));
		
		// Choice Integer value
		assertEquals("Y", new NamedFormatter("${varInt/<123456:X/123456:Y/>123456:Z}").format(vars));
		
		// Choice Decimal value
		assertEquals("Y", new NamedFormatter("${varDec/<123456.789:X/123456.789:Y/>123456.789:Z}").format(vars));
		
		// Choice Date value
		assertEquals("Y", new NamedFormatter("${varDate/<#2000-01-23#:X/#2000-01-23#:Y/>#2000-01-23#:Z}").format(vars));
		assertEquals("Y", new NamedFormatter("${varDate/<#2000-01-23T04:05:06.123456789+09:00#:X/#2000-01-23T04:05:06.123456789+09:00#:Y/>#2000-01-23T04:05:06.123456789+09:00#:Z}").format(vars));
		
		// Choice Boolean value
		assertEquals("Y", new NamedFormatter("${varBool/false:X/true:Y}").format(vars));
		assertEquals("X", new NamedFormatter("${varBool/!=false:X/!=true:Y}").format(vars));
		
		// Choice Null value
		assertEquals("Y", new NamedFormatter("${varNull/'null':X/null:Y/default:Z}").format(vars));
	}

	@Test
	public void textFormat_PlainString() {
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return "";
			});
			assertEquals("", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return "val";
			});
			assertEquals("val", text);
		}
	}

	@Test
	public void textFormat_PlainInteger() {
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return 0;
			});
			assertEquals("0", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return 1234;
			});
			assertEquals("1234", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return -1234;
			});
			assertEquals("-1234", text);
		}
	}

	@Test
	public void textFormat_PlainDecimal() {
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return 0.0;
			});
			assertEquals("0.0", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return 1234.5678;
			});
			assertEquals("1234.5678", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return -1234.5678;
			});
			assertEquals("-1234.5678", text);
		}
	}

	@Test
	public void textFormat_PlainDate() {
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			Date date = new Date();
			String text = formatter.format(varName -> {
				return date;
			});
			assertEquals(date.toString(), text);
		}
	}

	@Test
	public void textFormat_PlainBoolean() {
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return Boolean.TRUE;
			});
			assertEquals("true", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return Boolean.FALSE;
			});
			assertEquals("false", text);
		}
	}

	@Test
	public void textFormat_PlainNull() {
		{
			NamedFormatter formatter = new NamedFormatter("${var}");
			String text = formatter.format(varName -> {
				return null;
			});
			assertEquals("", text);
		}
	}

	@Test
	public void textFormat_PlainMulti() {
		{
			NamedFormatter formatter = new NamedFormatter("test${var1}test${var2}test${var3}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var1": return "val1";
				case "var2": return "val2";
				default: return null;
				}
			});
			assertEquals("testval1testval2test", text);
		}
	}

	@Test
	public void textFormat_FormatInteger() {
		{
			NamedFormatter formatter = new NamedFormatter("${var%%06d}");
			String text = formatter.format(varName -> {
				return 1234;
			});
			assertEquals("001234", text);
		}
		
		{
			NamedFormatter formatter = new NamedFormatter("${var%000000}");
			String text = formatter.format(varName -> {
				return 1234;
			});
			assertEquals("001234", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var%#,###}");
			String text = formatter.format(varName -> {
				return 1234;
			});
			assertEquals("1,234", text);
		}
	}

	@Test
	public void textFormat_Decimal() {
		{
			NamedFormatter formatter = new NamedFormatter("${var%#,##0.00}");
			String text = formatter.format(varName -> {
				return 1234.5678;
			});
			assertEquals("1,234.57", text);
		}
	}

	@Test
	public void textFormat_FormatDate() {
		{
			NamedFormatter formatter = new NamedFormatter("${var%%tY-%tm-%td}");
			Date date = new Date();
			String text = formatter.format(varName -> {
				return date;
			});
			assertEquals(String.format("%tY-%tm-%td", date, date, date), text);
		}
		
		{
			NamedFormatter formatter = new NamedFormatter("${var%uuuu-MM-dd HH:mm:ss.SSS}");
			Date date = new Date();
			String text = formatter.format(varName -> {
				return date;
			});
			assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date), text);
		}
	}

	@Test
	public void textFormat_FormatMulti() {
		{
			NamedFormatter formatter = new NamedFormatter("test${var1}test${var2%#,###}test${var3%uuuu-MM-dd HH:mm:ss.SSS}test\\${var1}\\\\");
			Date date = new Date();
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var1": return "val1";
				case "var2": return 1234;
				case "var3": return date;
				default: return null;
				}
			});
			assertEquals("testval1test1,234test" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date)) + "test${var1}\\", text);
		}
	}

	@Test
	public void textFormat_ChoiceString() {
		{
			NamedFormatter formatter = new NamedFormatter("${var/'strVal':retStr}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return "strVal";
				default: return null;
				}
			});
			assertEquals("retStr", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/'strVal':retStr/~'(?i).+va.':retRegex/!'not':retNot/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return "strVal";
				default: return null;
				}
			});
			assertEquals("retStr", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/'strVal':retStr/~'(?i).+va.':retRegex/!'not':retNot/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return "regexVal";
				default: return null;
				}
			});
			assertEquals("retRegex", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/'strVal':retStr/~'(?i).+va.':retRegex/!'not':retNot/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return "xxxnot";
				default: return null;
				}
			});
			assertEquals("retNot", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/'strVal':retStr/~'(?i).+va.':retRegex/!'not':retNot/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return "not";
				default: return null;
				}
			});
			assertEquals("retDef", text);
		}

		{
			NamedFormatter formatter = new NamedFormatter("${var/<123456:retLT/123456:retEQ/>123456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 12344;
				default: return null;
				}
			});
			assertEquals("retLT", text);
		}
	}

	@Test
	public void textFormat_ChoiceInteger() {
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123456:retLT/123456:retEQ/>123456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 123456;
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123456:retLT/123456:retEQ/>123456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 123457;
				default: return null;
				}
			});
			assertEquals("retGT", text);
		}

		{
			NamedFormatter formatter = new NamedFormatter("${var/<123456:retLT/123456:retEQ/>123456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 123455.9d;
				default: return null;
				}
			});
			assertEquals("retLT", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123456:retLT/123456:retEQ/>123456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 123456.0d;
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123456:retLT/123456:retEQ/>123456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 123456.1d;
				default: return null;
				}
			});
			assertEquals("retGT", text);
		}
	}

	@Test
	public void textFormat_ChoiceDecimal() {
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123.456:retLT/123.456:retEQ/>123.456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 123.4559d;
				default: return null;
				}
			});
			assertEquals("retLT", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123.456:retLT/123.456:retEQ/>123.456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 123.4560d;
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123.456:retLT/123.456:retEQ/>123.456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 123.4561d;
				default: return null;
				}
			});
			assertEquals("retGT", text);
		}

		{
			NamedFormatter formatter = new NamedFormatter("${var/<123.456:retLT/123.456:retEQ/>123.456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return new BigDecimal("123.4559");
				default: return null;
				}
			});
			assertEquals("retLT", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123.456:retLT/123.456:retEQ/>123.456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return new BigDecimal("123.4560");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<123.456:retLT/123.456:retEQ/>123.456:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return new BigDecimal("123.4561");
				default: return null;
				}
			});
			assertEquals("retGT", text);
		}
	}

	@Test
	public void textFormat_ChoiceDate() {
		{
			NamedFormatter formatter = new NamedFormatter("${var/<#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retLT/#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retEQ/>#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return ZonedDateTime.parse("2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retLT/#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retEQ/>#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return OffsetDateTime.parse("2000-01-23T04:05:06.123456789+09:00");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retLT/#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retEQ/>#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return LocalDateTime.parse("2000-01-23T04:05:06.123456789");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retLT/#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retEQ/>#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return YearMonth.parse("2000-01");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retLT/#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retEQ/>#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return MonthDay.parse("--01-23");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retLT/#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retEQ/>#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return LocalDate.parse("2000-01-23");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retLT/#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retEQ/>#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return OffsetTime.parse("04:05:06.123456789+09:00");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/<#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retLT/#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retEQ/>#2000-01-23T04:05:06.123456789+09:00[Asia/Tokyo]#:retGT/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return LocalTime.parse("04:05:06.123456789");
				default: return null;
				}
			});
			assertEquals("retEQ", text);
		}
	}

	@Test
	public void textFormat_ChoiceBoolean() {
		{
			NamedFormatter formatter = new NamedFormatter("${var/true:retT/false:retF/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return Boolean.TRUE;
				default: return null;
				}
			});
			assertEquals("retT", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/true:retT/false:retF/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return Boolean.FALSE;
				default: return null;
				}
			});
			assertEquals("retF", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/true:retT/false:retF/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return null;
				default: return null;
				}
			});
			assertEquals("retDef", text);
		}

		{
			NamedFormatter formatter = new NamedFormatter("${var/!true:retT/!false:retF/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return Boolean.TRUE;
				default: return null;
				}
			});
			assertEquals("retF", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/!true:retT/!false:retF/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return Boolean.FALSE;
				default: return null;
				}
			});
			assertEquals("retT", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/!true:retT/!false:retF/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return null;
				default: return null;
				}
			});
			assertEquals("retT", text);
		}

		{
			NamedFormatter formatter = new NamedFormatter("${var/true:retT/false:retF/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 1;
				default: return null;
				}
			});
			assertEquals("retT", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/true:retT/false:retF/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 0;
				default: return null;
				}
			});
			assertEquals("retF", text);
		}
	}

	@Test
	public void textFormat_ChoiceNull() {
		{
			NamedFormatter formatter = new NamedFormatter("${var/null:retN/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return null;
				default: return null;
				}
			});
			assertEquals("retN", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/!null:retN/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return null;
				default: return null;
				}
			});
			assertEquals("retDef", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/null:retN/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return "";
				default: return null;
				}
			});
			assertEquals("retDef", text);
		}
		{
			NamedFormatter formatter = new NamedFormatter("${var/null:retN/default:retDef}");
			String text = formatter.format(varName -> {
				switch (varName) {
				case "var": return 0;
				default: return null;
				}
			});
			assertEquals("retDef", text);
		}
	}
}
