/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Helper methods for working with date and time.
 * 
 * @author mozq
 */
public final class DateUtilz {
	
	public enum ChronoCompType {
		LeftFields,
		RightFields,
	};
	
	private static Map<Locale, TimeZone> localeTimeZoneMap;
	static {
		// cf. http://david.uebelacker.ch/2010/05/13/java-retrive-locale-and-timezone-from-request/
		localeTimeZoneMap = new HashMap<Locale, TimeZone>();
		localeTimeZoneMap.put(new Locale("ja", "JP"), TimeZone.getTimeZone("Asia/Tokyo"));
		localeTimeZoneMap.put(new Locale("es", "PE"), TimeZone.getTimeZone("America/Lima"));
		localeTimeZoneMap.put(new Locale("en"), TimeZone.getTimeZone("America/Los_Angeles"));
		localeTimeZoneMap.put(new Locale("ja", "JP"), TimeZone.getTimeZone("Asia/Tokyo"));
		localeTimeZoneMap.put(new Locale("es", "PA"), TimeZone.getTimeZone("America/Panama"));
		localeTimeZoneMap.put(new Locale("sr", "BA"), TimeZone.getTimeZone("Europe/Sarajevo"));
		localeTimeZoneMap.put(new Locale("mk"), TimeZone.getTimeZone("Europe/Skopje"));
		localeTimeZoneMap.put(new Locale("es", "GT"), TimeZone.getTimeZone("America/Guatemala"));
		localeTimeZoneMap.put(new Locale("ar", "AE"), TimeZone.getTimeZone("Asia/Dubai"));
		localeTimeZoneMap.put(new Locale("no", "NO"), TimeZone.getTimeZone("Europe/Oslo"));
		localeTimeZoneMap.put(new Locale("sq", "AL"), TimeZone.getTimeZone("Europe/Tirane"));
		localeTimeZoneMap.put(new Locale("bg"), TimeZone.getTimeZone("Europe/Sofia"));
		localeTimeZoneMap.put(new Locale("ar", "IQ"), TimeZone.getTimeZone("Asia/Baghdad"));
		localeTimeZoneMap.put(new Locale("ar", "YE"), TimeZone.getTimeZone("Asia/Qatar"));
		localeTimeZoneMap.put(new Locale("hu"), TimeZone.getTimeZone("Europe/Budapest"));
		localeTimeZoneMap.put(new Locale("pt", "PT"), TimeZone.getTimeZone("Europe/Lisbon"));
		localeTimeZoneMap.put(new Locale("el", "CY"), TimeZone.getTimeZone("Asia/Nicosia"));
		localeTimeZoneMap.put(new Locale("ar", "QA"), TimeZone.getTimeZone("Asia/Qatar"));
		localeTimeZoneMap.put(new Locale("mk", "MK"), TimeZone.getTimeZone("Europe/Skopje"));
		localeTimeZoneMap.put(new Locale("sv"), TimeZone.getTimeZone("Europe/Stockholm"));
		localeTimeZoneMap.put(new Locale("de", "CH"), TimeZone.getTimeZone("Europe/Zurich"));
		localeTimeZoneMap.put(new Locale("en", "US"), TimeZone.getTimeZone("America/Los_Angeles"));
		localeTimeZoneMap.put(new Locale("fi", "FI"), TimeZone.getTimeZone("Europe/Helsinki"));
		localeTimeZoneMap.put(new Locale("is"), TimeZone.getTimeZone("Atlantic/Reykjavik"));
		localeTimeZoneMap.put(new Locale("cs"), TimeZone.getTimeZone("Europe/Prague"));
		localeTimeZoneMap.put(new Locale("en", "MT"), TimeZone.getTimeZone("Europe/Malta"));
		localeTimeZoneMap.put(new Locale("sl", "SI"), TimeZone.getTimeZone("Europe/Ljubljana"));
		localeTimeZoneMap.put(new Locale("sk", "SK"), TimeZone.getTimeZone("Europe/Bratislava"));
		localeTimeZoneMap.put(new Locale("it"), TimeZone.getTimeZone("Europe/Rome"));
		localeTimeZoneMap.put(new Locale("tr", "TR"), TimeZone.getTimeZone("Europe/Istanbul"));
		localeTimeZoneMap.put(new Locale("zh"), TimeZone.getTimeZone("Asia/Taipei"));
		localeTimeZoneMap.put(new Locale("th"), TimeZone.getTimeZone("Asia/Bangkok"));
		localeTimeZoneMap.put(new Locale("ar", "SA"), TimeZone.getTimeZone("Asia/Riyadh"));
		localeTimeZoneMap.put(new Locale("no"), TimeZone.getTimeZone("Europe/Oslo"));
		localeTimeZoneMap.put(new Locale("en", "GB"), TimeZone.getTimeZone("Europe/London"));
		localeTimeZoneMap.put(new Locale("sr", "CS"), TimeZone.getTimeZone("Europe/Belgrade"));
		localeTimeZoneMap.put(new Locale("lt"), TimeZone.getTimeZone("Europe/Vilnius"));
		localeTimeZoneMap.put(new Locale("ro"), TimeZone.getTimeZone("Europe/Bucharest"));
		localeTimeZoneMap.put(new Locale("en", "NZ"), TimeZone.getTimeZone("Pacific/Auckland"));
		localeTimeZoneMap.put(new Locale("no", "NO"), TimeZone.getTimeZone("Europe/Oslo"));
		localeTimeZoneMap.put(new Locale("lt", "LT"), TimeZone.getTimeZone("Europe/Vilnius"));
		localeTimeZoneMap.put(new Locale("es", "NI"), TimeZone.getTimeZone("America/Managua"));
		localeTimeZoneMap.put(new Locale("nl"), TimeZone.getTimeZone("Europe/Amsterdam"));
		localeTimeZoneMap.put(new Locale("ga", "IE"), TimeZone.getTimeZone("Europe/Dublin"));
		localeTimeZoneMap.put(new Locale("fr", "BE"), TimeZone.getTimeZone("Europe/Brussels"));
		localeTimeZoneMap.put(new Locale("es", "ES"), TimeZone.getTimeZone("Europe/Madrid"));
		localeTimeZoneMap.put(new Locale("ar", "LB"), TimeZone.getTimeZone("Asia/Beirut"));
		localeTimeZoneMap.put(new Locale("ko"), TimeZone.getTimeZone("Asia/Seoul"));
		localeTimeZoneMap.put(new Locale("fr", "CA"), TimeZone.getTimeZone("America/Montreal"));
		localeTimeZoneMap.put(new Locale("et", "EE"), TimeZone.getTimeZone("Europe/Tallinn"));
		localeTimeZoneMap.put(new Locale("ar", "KW"), TimeZone.getTimeZone("Asia/Kuwait"));
		localeTimeZoneMap.put(new Locale("sr", "RS"), TimeZone.getTimeZone("Europe/Belgrade"));
		localeTimeZoneMap.put(new Locale("es", "US"), TimeZone.getTimeZone("Europe/London"));
		localeTimeZoneMap.put(new Locale("es", "MX"), TimeZone.getTimeZone("America/Mexico_City"));
		localeTimeZoneMap.put(new Locale("ar", "SD"), TimeZone.getTimeZone("Africa/Khartoum"));
		localeTimeZoneMap.put(new Locale("in", "ID"), TimeZone.getTimeZone("Asia/Jakarta"));
		localeTimeZoneMap.put(new Locale("ru"), TimeZone.getTimeZone("Europe/Moscow"));
		localeTimeZoneMap.put(new Locale("lv"), TimeZone.getTimeZone("Europe/Riga"));
		localeTimeZoneMap.put(new Locale("es", "UY"), TimeZone.getTimeZone("America/Montevideo"));
		localeTimeZoneMap.put(new Locale("lv", "LV"), TimeZone.getTimeZone("Europe/Riga"));
		localeTimeZoneMap.put(new Locale("iw"), TimeZone.getTimeZone("Asia/Jerusalem"));
		localeTimeZoneMap.put(new Locale("pt", "BR"), TimeZone.getTimeZone("America/Sao_Paulo"));
		localeTimeZoneMap.put(new Locale("ar", "SY"), TimeZone.getTimeZone("Asia/Damascus"));
		localeTimeZoneMap.put(new Locale("hr"), TimeZone.getTimeZone("Europe/Zagreb"));
		localeTimeZoneMap.put(new Locale("et"), TimeZone.getTimeZone("Europe/Tallinn"));
		localeTimeZoneMap.put(new Locale("es", "DO"), TimeZone.getTimeZone("America/Santo_Domingo"));
		localeTimeZoneMap.put(new Locale("fr", "CH"), TimeZone.getTimeZone("Europe/Zurich"));
		localeTimeZoneMap.put(new Locale("hi", "IN"), TimeZone.getTimeZone("Asia/Calcutta"));
		localeTimeZoneMap.put(new Locale("es", "VE"), TimeZone.getTimeZone("America/Caracas"));
		localeTimeZoneMap.put(new Locale("ar", "BH"), TimeZone.getTimeZone("Asia/Bahrain"));
		localeTimeZoneMap.put(new Locale("en", "PH"), TimeZone.getTimeZone("Asia/Manila"));
		localeTimeZoneMap.put(new Locale("ar", "TN"), TimeZone.getTimeZone("Africa/Tunis"));
		localeTimeZoneMap.put(new Locale("fi"), TimeZone.getTimeZone("Europe/Helsinki"));
		localeTimeZoneMap.put(new Locale("de", "AT"), TimeZone.getTimeZone("Europe/Vienna"));
		localeTimeZoneMap.put(new Locale("es"), TimeZone.getTimeZone("America/Lima"));
		localeTimeZoneMap.put(new Locale("nl", "NL"), TimeZone.getTimeZone("Europe/Amsterdam"));
		localeTimeZoneMap.put(new Locale("es", "EC"), TimeZone.getTimeZone("America/Guayaquil"));
		localeTimeZoneMap.put(new Locale("zh", "TW"), TimeZone.getTimeZone("Asia/Taipei"));
		localeTimeZoneMap.put(new Locale("ar", "JO"), TimeZone.getTimeZone("Asia/Amman"));
		localeTimeZoneMap.put(new Locale("be"), TimeZone.getTimeZone("Europe/Minsk"));
		localeTimeZoneMap.put(new Locale("is", "IS"), TimeZone.getTimeZone("Atlantic/Reykjavik"));
		localeTimeZoneMap.put(new Locale("es", "CO"), TimeZone.getTimeZone("America/Bogota"));
		localeTimeZoneMap.put(new Locale("es", "CR"), TimeZone.getTimeZone("America/Costa_Rica"));
		localeTimeZoneMap.put(new Locale("es", "CL"), TimeZone.getTimeZone("America/Santiago"));
		localeTimeZoneMap.put(new Locale("ar", "EG"), TimeZone.getTimeZone("Africa/Cairo"));
		localeTimeZoneMap.put(new Locale("en", "ZA"), TimeZone.getTimeZone("Africa/Johannesburg"));
		localeTimeZoneMap.put(new Locale("th", "TH"), TimeZone.getTimeZone("Asia/Bangkok"));
		localeTimeZoneMap.put(new Locale("el", "GR"), TimeZone.getTimeZone("Europe/Athens"));
		localeTimeZoneMap.put(new Locale("it", "IT"), TimeZone.getTimeZone("Europe/Rome"));
		localeTimeZoneMap.put(new Locale("ca"), TimeZone.getTimeZone("Europe/Madrid"));
		localeTimeZoneMap.put(new Locale("hu", "HU"), TimeZone.getTimeZone("Europe/Budapest"));
		localeTimeZoneMap.put(new Locale("fr"), TimeZone.getTimeZone("Europe/Brussels"));
		localeTimeZoneMap.put(new Locale("en", "IE"), TimeZone.getTimeZone("Europe/Dublin"));
		localeTimeZoneMap.put(new Locale("uk", "UA"), TimeZone.getTimeZone("Europe/Kiev"));
		localeTimeZoneMap.put(new Locale("pl", "PL"), TimeZone.getTimeZone("Europe/Warsaw"));
		localeTimeZoneMap.put(new Locale("fr", "LU"), TimeZone.getTimeZone("Europe/Luxembourg"));
		localeTimeZoneMap.put(new Locale("nl", "BE"), TimeZone.getTimeZone("Europe/Brussels"));
		localeTimeZoneMap.put(new Locale("en", "IN"), TimeZone.getTimeZone("Asia/Calcutta"));
		localeTimeZoneMap.put(new Locale("ca", "ES"), TimeZone.getTimeZone("Europe/Madrid"));
		localeTimeZoneMap.put(new Locale("ar", "MA"), TimeZone.getTimeZone("Africa/Casablanca"));
		localeTimeZoneMap.put(new Locale("es", "BO"), TimeZone.getTimeZone("America/La_Paz"));
		localeTimeZoneMap.put(new Locale("en", "AU"), TimeZone.getTimeZone("Australia/Canberra"));
		localeTimeZoneMap.put(new Locale("sr"), TimeZone.getTimeZone("Europe/Sarajevo"));
		localeTimeZoneMap.put(new Locale("zh", "SG"), TimeZone.getTimeZone("Asia/Singapore"));
		localeTimeZoneMap.put(new Locale("pt"), TimeZone.getTimeZone("Europe/Lisbon"));
		localeTimeZoneMap.put(new Locale("uk"), TimeZone.getTimeZone("Europe/Kiev"));
		localeTimeZoneMap.put(new Locale("es", "SV"), TimeZone.getTimeZone("America/El_Salvador"));
		localeTimeZoneMap.put(new Locale("ru", "RU"), TimeZone.getTimeZone("Europe/Moscow"));
		localeTimeZoneMap.put(new Locale("ko", "KR"), TimeZone.getTimeZone("Asia/Seoul"));
		localeTimeZoneMap.put(new Locale("vi"), TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		localeTimeZoneMap.put(new Locale("ar", "DZ"), TimeZone.getTimeZone("Africa/Algiers"));
		localeTimeZoneMap.put(new Locale("vi", "VN"), TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		localeTimeZoneMap.put(new Locale("sr", "ME"), TimeZone.getTimeZone("Europe/Podgorica"));
		localeTimeZoneMap.put(new Locale("sq"), TimeZone.getTimeZone("Europe/Tirane"));
		localeTimeZoneMap.put(new Locale("ar", "LY"), TimeZone.getTimeZone("Africa/Tripoli"));
		localeTimeZoneMap.put(new Locale("ar"), TimeZone.getTimeZone("Asia/Dubai"));
		localeTimeZoneMap.put(new Locale("zh", "CN"), TimeZone.getTimeZone("Asia/Shanghai"));
		localeTimeZoneMap.put(new Locale("be", "BY"), TimeZone.getTimeZone("Europe/Minsk"));
		localeTimeZoneMap.put(new Locale("zh", "HK"), TimeZone.getTimeZone("Asia/Hong_Kong"));
		localeTimeZoneMap.put(new Locale("ja"), TimeZone.getTimeZone("Asia/Tokyo"));
		localeTimeZoneMap.put(new Locale("iw", "IL"), TimeZone.getTimeZone("Asia/Jerusalem"));
		localeTimeZoneMap.put(new Locale("bg", "BG"), TimeZone.getTimeZone("Europe/Sofia"));
		localeTimeZoneMap.put(new Locale("in"), TimeZone.getTimeZone("Asia/Jakarta"));
		localeTimeZoneMap.put(new Locale("mt", "MT"), TimeZone.getTimeZone("Europe/Malta"));
		localeTimeZoneMap.put(new Locale("es", "PY"), TimeZone.getTimeZone("America/Asuncion"));
		localeTimeZoneMap.put(new Locale("sl"), TimeZone.getTimeZone("Europe/Ljubljana"));
		localeTimeZoneMap.put(new Locale("fr", "FR"), TimeZone.getTimeZone("Europe/Paris"));
		localeTimeZoneMap.put(new Locale("cs", "CZ"), TimeZone.getTimeZone("Europe/Prague"));
		localeTimeZoneMap.put(new Locale("it", "CH"), TimeZone.getTimeZone("Europe/Zurich"));
		localeTimeZoneMap.put(new Locale("ro", "RO"), TimeZone.getTimeZone("Europe/Bucharest"));
		localeTimeZoneMap.put(new Locale("es", "PR"), TimeZone.getTimeZone("America/Argentina/San_Juan"));
		localeTimeZoneMap.put(new Locale("en", "CA"), TimeZone.getTimeZone("America/Montreal"));
		localeTimeZoneMap.put(new Locale("de", "DE"), TimeZone.getTimeZone("Europe/Berlin"));
		localeTimeZoneMap.put(new Locale("ga"), TimeZone.getTimeZone("Europe/Dublin"));
		localeTimeZoneMap.put(new Locale("de", "LU"), TimeZone.getTimeZone("Europe/Luxembourg"));
		localeTimeZoneMap.put(new Locale("de"), TimeZone.getTimeZone("Europe/Zurich"));
		localeTimeZoneMap.put(new Locale("es", "AR"), TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
		localeTimeZoneMap.put(new Locale("sk"), TimeZone.getTimeZone("Europe/Bratislava"));
		localeTimeZoneMap.put(new Locale("ms", "MY"), TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
		localeTimeZoneMap.put(new Locale("hr", "HR"), TimeZone.getTimeZone("Europe/Zagreb"));
		localeTimeZoneMap.put(new Locale("en", "SG"), TimeZone.getTimeZone("Asia/Singapore"));
		localeTimeZoneMap.put(new Locale("da"), TimeZone.getTimeZone("Europe/Copenhagen"));
		localeTimeZoneMap.put(new Locale("mt"), TimeZone.getTimeZone("Europe/Malta"));
		localeTimeZoneMap.put(new Locale("pl"), TimeZone.getTimeZone("Europe/Warsaw"));
		localeTimeZoneMap.put(new Locale("ar", "OM"), TimeZone.getTimeZone("Asia/Muscat"));
		localeTimeZoneMap.put(new Locale("tr"), TimeZone.getTimeZone("Europe/Istanbul"));
		localeTimeZoneMap.put(new Locale("th", "TH"), TimeZone.getTimeZone("Asia/Bangkok"));
		localeTimeZoneMap.put(new Locale("el"), TimeZone.getTimeZone("Asia/Nicosia"));
		localeTimeZoneMap.put(new Locale("ms"), TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
		localeTimeZoneMap.put(new Locale("sv", "SE"), TimeZone.getTimeZone("Europe/Stockholm"));
		localeTimeZoneMap.put(new Locale("da", "DK"), TimeZone.getTimeZone("Europe/Copenhagen"));
		localeTimeZoneMap.put(new Locale("es", "HN"), TimeZone.getTimeZone("America/Tegucigalpa"));
	}
	
	private DateUtilz() {
		// NOP
	}
	
	public static long toMillis(Date date) {
		if (date == null) {
			throw new NullPointerException();
		}
		return date.getTime();
	}
	
	public static long toMillis(Calendar calendar) {
		if (calendar == null) {
			throw new NullPointerException();
		}
		return calendar.getTimeInMillis();
	}
	
	public static long toMillis(XMLGregorianCalendar xmlGregorianCalendar) {
		if (xmlGregorianCalendar == null) {
			throw new NullPointerException();
		}
		return xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis();
	}
	
	public static long toMillis(java.sql.Date date) {
		if (date == null) {
			throw new NullPointerException();
		}
		return date.getTime();
	}
	
	public static long toMillis(java.sql.Time time) {
		if (time == null) {
			throw new NullPointerException();
		}
		return time.getTime();
	}
	
	public static long toMillis(java.sql.Timestamp timestamp) {
		if (timestamp == null) {
			throw new NullPointerException();
		}
		return timestamp.getTime();
	}
	
	public static Date toDate(long date) {
		return new Date(date);
	}
	
	public static Date toDate(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		return new Date(date.getTime());
	}
	
	public static Date toDate(java.sql.Time time) {
		if (time == null) {
			return null;
		}
		return new Date(time.getTime());
	}
	
	public static Date toDate(java.sql.Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return new Date(timestamp.getTime());
	}
	
	public static Date toDate(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.getTime();
	}
	
	public static Date toDate(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	public static Calendar toCalendar(Date date) {
		return toCalendar(date, null, null);
	}
	
	public static Calendar toCalendar(Date date, TimeZone timeZone, Locale locale) {
		if (date == null) {
			return null;
		}
		return toCalendar(date.getTime(), timeZone, locale);
	}
	
	public static Calendar toCalendar(long date) {
		return toCalendar(date, null, null);
	}
	
	public static Calendar toCalendar(long date, TimeZone timeZone, Locale locale) {
		Calendar cal = getCalendar(timeZone, locale);
		cal.setTimeInMillis(date);
		return cal;
	}

	public static GregorianCalendar toGregorianCalendar(Date date) {
		return toGregorianCalendar(date, null, null);
	}
	
	public static GregorianCalendar toGregorianCalendar(Date date, TimeZone timeZone, Locale locale) {
		if (date == null) {
			return null;
		}
		return toGregorianCalendar(date.getTime(), timeZone, locale);
	}
	
	public static GregorianCalendar toGregorianCalendar(long date) {
		return toGregorianCalendar(date, null, null);
	}
	
	public static GregorianCalendar toGregorianCalendar(long date, TimeZone timeZone, Locale locale) {
		GregorianCalendar gccal = getGregorianCalendar(timeZone, locale);
		gccal.setTimeInMillis(date);
		return gccal;
	}

	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		return toXMLGregorianCalendar(date, null, null);
	}
	
	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date, TimeZone timeZone, Locale locale) throws DatatypeConfigurationException {
		if (date == null) {
			return null;
		}
		return toXMLGregorianCalendar(date.getTime(), timeZone, locale);
	}
	
	public static XMLGregorianCalendar toXMLGregorianCalendar(long date) throws DatatypeConfigurationException {
		return toXMLGregorianCalendar(date, null, null);
	}
	
	public static XMLGregorianCalendar toXMLGregorianCalendar(long date, TimeZone timeZone, Locale locale) throws DatatypeConfigurationException {
		return DatatypeFactory
				.newInstance()
				.newXMLGregorianCalendar(toGregorianCalendar(date, timeZone, locale));
	}

	public static java.sql.Date toSqlDate(Date date) {
		if (date == null) {
			return null;
		}
		return toSqlDate(date.getTime());
	}
	
	public static java.sql.Date toSqlDate(long date) {
		return new java.sql.Date(date);
	}

	public static java.sql.Time toSqlTime(Date date) {
		if (date == null) {
			return null;
		}
		return toSqlTime(date.getTime());
	}
	
	public static java.sql.Time toSqlTime(long date) {
		return new java.sql.Time(date);
	}
	
	public static java.sql.Timestamp toSqlTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		return toSqlTimestamp(date.getTime());
	}
	
	public static java.sql.Timestamp toSqlTimestamp(long date) {
		return new java.sql.Timestamp(date);
	}
	
	public static Calendar getCalendar(TimeZone timeZone, Locale locale) {
		Calendar cal;
		if (timeZone == null) {
			if (locale == null) {
				cal = Calendar.getInstance();
			} else {
				cal = Calendar.getInstance(locale);
			}
		} else {
			if (locale == null) {
				cal = Calendar.getInstance(timeZone);
			} else {
				cal = Calendar.getInstance(timeZone, locale);
			}
		}
		return cal;
	}
	
	public static GregorianCalendar getGregorianCalendar(TimeZone timeZone, Locale locale) {
		GregorianCalendar gcal;
		if (timeZone == null) {
			if (locale == null) {
				gcal = new GregorianCalendar();
			} else {
				gcal = new GregorianCalendar(locale);
			}
		} else {
			if (locale == null) {
				gcal = new GregorianCalendar(timeZone);
			} else {
				gcal = new GregorianCalendar(timeZone, locale);
			}
		}
		return gcal;
	}
	
	public static XMLGregorianCalendar getXMLGregorianCalendar(TimeZone timeZone, Locale locale) throws DatatypeConfigurationException {
		return DatatypeFactory
				.newInstance()
				.newXMLGregorianCalendar(getGregorianCalendar(timeZone, locale));
	}
	
	public static long diffYear(long date, int year, TimeZone timeZone, Locale locale) {
		return diff(date, year, 0, 0, 0, 0, 0, 0, timeZone, locale);
	}
	
	public static long diffMonth(long date, int month, TimeZone timeZone, Locale locale) {
		return diff(date, 0, month, 0, 0, 0, 0, 0, timeZone, locale);
	}
	
	public static long diffDay(long date, int day, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, day, 0, 0, 0, 0, timeZone, locale);
	}
	
	public static long diffHour(long date, int hour, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, hour, 0, 0, 0, timeZone, locale);
	}
	
	public static long diffMinute(long date, int minute, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, 0, minute, 0, 0, timeZone, locale);
	}
	
	public static long diffSecond(long date, int second, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, 0, 0, second, 0, timeZone, locale);
	}
	
	public static long diffMilliSecond(long date, int milliSecond, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, 0, 0, 0, milliSecond, timeZone, locale);
	}
	
	public static long diffDate(long date, int year, int month, int day, TimeZone timeZone, Locale locale) {
		return diff(date, year, month, day, 0, 0, 0, 0, timeZone, locale);
	}
	
	public static long diffTime(long date, int hour, int minute, int second, int milliSecond, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, hour, minute, second, milliSecond, timeZone, locale);
	}
	
	public static long diff(long date, int year, int month, int day, int hour, int minute, int second, int milliSecond, TimeZone timeZone, Locale locale) {
		Calendar calendar = getCalendar(timeZone, locale);
		calendar.setTimeInMillis(date);
		calendar = diff(calendar, year, month, day, hour, minute, second, milliSecond);
		return calendar.getTimeInMillis();
	}
	
	public static Date diffYear(Date date, int year, TimeZone timeZone, Locale locale) {
		return diff(date, year, 0, 0, 0, 0, 0, 0, timeZone, locale);
	}
	
	public static Date diffMonth(Date date, int month, TimeZone timeZone, Locale locale) {
		return diff(date, 0, month, 0, 0, 0, 0, 0, timeZone, locale);
	}
	
	public static Date diffDay(Date date, int day, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, day, 0, 0, 0, 0, timeZone, locale);
	}
	
	public static Date diffHour(Date date, int hour, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, hour, 0, 0, 0, timeZone, locale);
	}
	
	public static Date diffMinute(Date date, int minute, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, 0, minute, 0, 0, timeZone, locale);
	}
	
	public static Date diffSecond(Date date, int second, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, 0, 0, second, 0, timeZone, locale);
	}
	
	public static Date diffMilliSecond(Date date, int milliSecond, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, 0, 0, 0, milliSecond, timeZone, locale);
	}
	
	public static Date diffDate(Date date, int year, int month, int day, TimeZone timeZone, Locale locale) {
		return diff(date, year, month, day, 0, 0, 0, 0, timeZone, locale);
	}
	
	public static Date diffTime(Date date, int hour, int minute, int second, int milliSecond, TimeZone timeZone, Locale locale) {
		return diff(date, 0, 0, 0, hour, minute, second, milliSecond, timeZone, locale);
	}
	
	public static Date diff(Date date, int year, int month, int day, int hour, int minute, int second, int milliSecond, TimeZone timeZone, Locale locale) {
		Calendar calendar = getCalendar(timeZone, locale);
		calendar.setTime(date);
		calendar = diff(calendar, year, month, day, hour, minute, second, milliSecond);
		return calendar.getTime();
	}
	
	public static Calendar diffYear(Calendar calendar, int year) {
		return diff(calendar, year, 0, 0, 0, 0, 0, 0);
	}
	
	public static Calendar diffMonth(Calendar calendar, int month) {
		return diff(calendar, 0, month, 0, 0, 0, 0, 0);
	}
	
	public static Calendar diffDay(Calendar calendar, int day) {
		return diff(calendar, 0, 0, day, 0, 0, 0, 0);
	}
	
	public static Calendar diffHour(Calendar calendar, int hour) {
		return diff(calendar, 0, 0, 0, hour, 0, 0, 0);
	}
	
	public static Calendar diffMinute(Calendar calendar, int minute) {
		return diff(calendar, 0, 0, 0, 0, minute, 0, 0);
	}
	
	public static Calendar diffSecond(Calendar calendar, int second) {
		return diff(calendar, 0, 0, 0, 0, 0, second, 0);
	}
	
	public static Calendar diffMilliSecond(Calendar calendar, int milliSecond) {
		return diff(calendar, 0, 0, 0, 0, 0, 0, milliSecond);
	}
	
	public static Calendar diffDate(Calendar calendar, int year, int month, int day) {
		return diff(calendar, year, month, day, 0, 0, 0, 0);
	}
	
	public static Calendar diffTime(Calendar calendar, int hour, int minute, int second, int milliSecond) {
		return diff(calendar, 0, 0, 0, hour, minute, second, milliSecond);
	}
	
	public static Calendar diff(Calendar calendar, int year, int month, int day, int hour, int minute, int second, int milliSecond) {
		calendar.add(Calendar.MILLISECOND, milliSecond);
		calendar.add(Calendar.SECOND, second);
		calendar.add(Calendar.MINUTE, minute);
		calendar.add(Calendar.HOUR, hour);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.YEAR, year);
		return calendar;
	}
	
	public static TimeZone localeToTimeZone(Locale locale) {
		return localeTimeZoneMap.get(locale);
	}


	/**
	 * Parse a date/time string with multiple patterns.
	 * Locale is used Locale.US.
	 * TimeZone is used UTC.
	 * 
	 * @param str the date/time string to be parsed
	 * @param strict when true, parsing is strict
	 * @param parsePatterns date patterns
	 * @return a date parsed from the string
	 */
	public static Date parseDate(String str, boolean strict, String... parsePatterns) {
		return parseDate(str, strict, null, null, parsePatterns);
	}
	
	/**
	 * Parse a date/time string with multiple patterns.
	 * 
	 * @param str the date/time string to be parsed
	 * @param strict when true, parsing is strict
	 * @param timeZone the TimeZone of date/time. Locale.US if argument is null
	 * @param locale the Locale of date/time. UTC if argument is null
	 * @param parsePatterns date patterns
	 * @return a date parsed from the string
	 */
	public static Date parseDate(String str, boolean strict, TimeZone timeZone, Locale locale, String... parsePatterns) {
		
		if (locale == null) {
			locale = Locale.US;
		}
		
		if (timeZone == null) {
			timeZone = TimeZone.getTimeZone("UTC");
		}
		
		Calendar calendar = Calendar.getInstance(timeZone, locale);
		calendar.setLenient(!strict);
		
		return parseDate(str, calendar, locale, parsePatterns);
	}
	
	/**
	 * Parse a date/time string with multiple patterns.
	 * 
	 * @param str the date/time string to be parsed
	 * @param calendar the Calendar of date/time. Calendar.getInstance() if argument is null
	 * @param locale the Locale of date/time. UTC if argument is null
	 * @param parsePatterns date patterns
	 * @return a date parsed from the string
	 */
	public static Date parseDate(String str, Calendar calendar, Locale locale, String... parsePatterns) {
		if (str == null || str.isEmpty()) {
			return null;
		}
		if (parsePatterns == null || parsePatterns.length == 0) {
			throw new IllegalArgumentException();
		}
		
		if (calendar == null) {
			calendar = Calendar.getInstance(locale);
		}
		
		for (String parsePattern : parsePatterns) {
			DateFormat dateFormat = new SimpleDateFormat(parsePattern, locale);
			dateFormat.setCalendar(calendar);
			try {
				return dateFormat.parse(str);
			} catch (ParseException e) {
				continue;
			}
		}
		
		return null;
	}
	
	public static int compare(Date date1, Date date2, boolean nullGreater) {
		if (date1 == null) {
			if (date2 == null) {
				return 0;
			} else {
				return (nullGreater) ? 1 : -1;
			}
		} else if (date2 == null) {
			return (nullGreater) ? -1 : 1;
		}
		
		return date1.compareTo(date2);
	}

	public static int compareChrono(TemporalAccessor date1, TemporalAccessor date2) {
		return compareChrono(date1, date2, false);
	}

	public static int compareChrono(TemporalAccessor date1, TemporalAccessor date2, boolean bothSupportOnly) {
		return compareChrono(date1, date2, bothSupportOnly, false, false);
	}
	
	public static int compareChrono(TemporalAccessor date1, TemporalAccessor date2, boolean bothSupportOnly, boolean nullGreater, boolean unsupportedGreater) {
		if (date1 == null) {
			if (date2 == null) {
				return 0;
			} else {
				return (nullGreater) ? 1 : -1;
			}
		} else if (date2 == null) {
			return (nullGreater) ? -1 : 1;
		}
		
		boolean matched = false;
		int cmp;
		int cmpUnsupport = 0;
		
		for (TemporalField field : new TemporalField[]{
				ChronoField.YEAR, ChronoField.MONTH_OF_YEAR, ChronoField.DAY_OF_MONTH,
				ChronoField.DAY_OF_WEEK,
				ChronoField.HOUR_OF_DAY, ChronoField.MINUTE_OF_HOUR, ChronoField.SECOND_OF_MINUTE, ChronoField.MILLI_OF_SECOND, ChronoField.NANO_OF_SECOND}) {
			try {
				cmp = compareTemporalField(date1, date2, field, unsupportedGreater);
				if (cmp == -2 || cmp == 2) {
					if (bothSupportOnly) {
						if (cmpUnsupport == 0) {
							cmpUnsupport = cmp;
						}
					} else {
						return cmp;
					}
				} else if (cmp != 0) {
					return cmp;
				} else {
					matched = true;
				}
			} catch (UnsupportedTemporalTypeException e) {
				// NOP
			}
		}
		
		if (hasZone(date1) && hasZone(date2)) {
			try {
				cmp = compareTemporalField(date1, date2, ChronoField.OFFSET_SECONDS, unsupportedGreater);
				if (cmp == -2 || cmp == 2) {
					if (bothSupportOnly) {
						if (cmpUnsupport == 0) {
							cmpUnsupport = cmp;
						}
					} else {
						return cmp;
					}
				} else if (cmp != 0) {
					return cmp;
				} else {
					matched = true;
				}
			} catch (UnsupportedTemporalTypeException e) {
				// NOP
			}
		}
		
		if (matched) {
			return 0;
		} else {
			return cmpUnsupport;
		}
	}

	private static int compareTemporalField(TemporalAccessor date1, TemporalAccessor date2, TemporalField field, boolean unsupportedGreater) {
		if (date1.isSupported(field)) {
			if (date2.isSupported(field)) {
				int cmp = date1.get(field) - date2.get(field);
				if (cmp == 0) {
					return 0;
				} else if (cmp < 0) {
					return -1;
				} else {
					return 1;
				}
			} else {
				return (unsupportedGreater) ? -2 : 2;
			}
		} else {
			if (date2.isSupported(field)) {
				return (unsupportedGreater) ? 2 : -2;
			} else {
				throw new UnsupportedTemporalTypeException(String.valueOf(field));
			}
		}
	}
	
	public static boolean hasZone(TemporalAccessor date) {
		if (date == null) {
			return false;
		}
		
		if (date instanceof ZonedDateTime) {
			return true;
		} else if (date instanceof OffsetDateTime) {
			return true;
		} else if (date instanceof OffsetTime) {
			return true;
		}
		
		return false;
	}
}
