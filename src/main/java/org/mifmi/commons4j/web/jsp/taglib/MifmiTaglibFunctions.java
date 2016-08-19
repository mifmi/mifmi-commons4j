/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.jsp.taglib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.mifmi.commons4j.util.StringUtilz;

public class MifmiTaglibFunctions {
	
	public static String strcat(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return null;
		}
		
		if (s1 == null) {
			return s2;
		}
		if (s2 == null) {
			return s1;
		}
		
		return s1 + s2;
	}
	
	public static String strcat(String s1, String s2, String s3) {
		if (s1 == null && s2 == null && s3 == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		if (s1 != null) {
			sb.append(s1);
		}
		if (s2 != null) {
			sb.append(s2);
		}
		if (s3 != null) {
			sb.append(s3);
		}
		
		return sb.toString();
	}
	
	public static String strcat(String s1, String s2, String s3, String s4) {
		if (s1 == null && s2 == null && s3 == null && s4 == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		if (s1 != null) {
			sb.append(s1);
		}
		if (s2 != null) {
			sb.append(s2);
		}
		if (s3 != null) {
			sb.append(s3);
		}
		if (s4 != null) {
			sb.append(s4);
		}
		
		return sb.toString();
	}
	
	public static String strcatNotNull(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return null;
		}
		
		return s1 + s2;
	}
	
	public static String strcatNotNull(String s1, String s2, String s3) {
		if (s1 == null || s2 == null || s3 == null) {
			return null;
		}
		
		return s1 + s2 + s3;
	}
	
	public static String strcatNotNull(String s1, String s2, String s3, String s4) {
		if (s1 == null || s2 == null || s3 == null || s4 == null) {
			return null;
		}

		return s1 + s2 + s3 + s4;
	}
	
	public static String strcatNotBlank(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return null;
		}
		
		if (s1.isEmpty() || s2.isEmpty()) {
			return "";
		}
		
		return s1 + s2;
	}
	
	public static String strcatNotBlank(String s1, String s2, String s3) {
		if (s1 == null || s2 == null || s3 == null) {
			return null;
		}
		
		if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
			return "";
		}
		
		return s1 + s2 + s3;
	}
	
	public static String strcatNotBlank(String s1, String s2, String s3, String s4) {
		if (s1 == null || s2 == null || s3 == null || s4 == null) {
			return null;
		}
		
		if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty()) {
			return "";
		}

		return s1 + s2 + s3 + s4;
	}
	
	public static String replace(String str, String regex, String replacement) {
		if (str == null || str.isEmpty()) {
			return "";
		}
		
		return str.replaceAll(regex, replacement);
	}
	
	public static String halfAlNum(String str) {
		if (str == null || str.isEmpty()) {
			return "";
		}
		
		return StringUtilz.toHalfWidth(str, true, true, false, false, false, false);
	}
	
	public static String escapeHTML(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		
		StringBuilder sb = new StringBuilder(str.length() * 2);
		for (char c : str.toCharArray()) {
			switch (c) {
			case '<': sb.append("&lt;"); break;
			case '>': sb.append("&gt;"); break;
			case '&': sb.append("&amp;"); break;
			case '"': sb.append("&quot;"); break;
			case '\'': sb.append("&#039;"); break;
			default: sb.append(c); break;
			}
		}
		
		return sb.toString();
	}
	
	public static String br(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		
		StringBuilder sb = new StringBuilder(str.length() * 2);
		char[] chars = str.toCharArray();
		int maxIdx = chars.length - 1;
		for (int i = 0; i <= maxIdx; i++) {
			char c = chars[i];
			switch (c) {
			case '\r':
				sb.append("<br />");
				if (i != maxIdx) {
					char nc = chars[i + 1];
					if (nc == '\n') {
						i++;
					}
				}
				break;
			case '\n':
				sb.append("<br />");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		
		return sb.toString();
	}
	
	public static String formatDate(Date date, String pattern, TimeZone timeZone) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		if (timeZone != null) {
			format.setTimeZone(timeZone);
		}
		return format.format(date);
	}
}
