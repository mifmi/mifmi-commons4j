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

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;
import org.mifmi.commons4j.util.DateUtilz;

public class DateUtilzTest {

	@Test
	public void testGetDefaultTimeZoneByLocale() throws Exception {
		// see: https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
		assertEquals(TimeZone.getTimeZone("Asia/Dubai"), DateUtilz.localeToTimeZone(new Locale("ar", "AE")));
		assertEquals(TimeZone.getTimeZone("Asia/Bahrain"), DateUtilz.localeToTimeZone(new Locale("ar", "BH")));
		assertEquals(TimeZone.getTimeZone("Africa/Algiers"), DateUtilz.localeToTimeZone(new Locale("ar", "DZ")));
		assertEquals(TimeZone.getTimeZone("Africa/Cairo"), DateUtilz.localeToTimeZone(new Locale("ar", "EG")));
		assertEquals(TimeZone.getTimeZone("Asia/Baghdad"), DateUtilz.localeToTimeZone(new Locale("ar", "IQ")));
		assertEquals(TimeZone.getTimeZone("Asia/Amman"), DateUtilz.localeToTimeZone(new Locale("ar", "JO")));
		assertEquals(TimeZone.getTimeZone("Asia/Kuwait"), DateUtilz.localeToTimeZone(new Locale("ar", "KW")));
		assertEquals(TimeZone.getTimeZone("Asia/Beirut"), DateUtilz.localeToTimeZone(new Locale("ar", "LB")));
		assertEquals(TimeZone.getTimeZone("Africa/Tripoli"), DateUtilz.localeToTimeZone(new Locale("ar", "LY")));
		assertEquals(TimeZone.getTimeZone("Africa/Casablanca"), DateUtilz.localeToTimeZone(new Locale("ar", "MA")));
		assertEquals(TimeZone.getTimeZone("Asia/Muscat"), DateUtilz.localeToTimeZone(new Locale("ar", "OM")));
		assertEquals(TimeZone.getTimeZone("Asia/Qatar"), DateUtilz.localeToTimeZone(new Locale("ar", "QA")));
		assertEquals(TimeZone.getTimeZone("Asia/Riyadh"), DateUtilz.localeToTimeZone(new Locale("ar", "SA")));
		assertEquals(TimeZone.getTimeZone("Africa/Khartoum"), DateUtilz.localeToTimeZone(new Locale("ar", "SD")));
		assertEquals(TimeZone.getTimeZone("Asia/Damascus"), DateUtilz.localeToTimeZone(new Locale("ar", "SY")));
		assertEquals(TimeZone.getTimeZone("Africa/Tunis"), DateUtilz.localeToTimeZone(new Locale("ar", "TN")));
		assertEquals(TimeZone.getTimeZone("Asia/Aden"), DateUtilz.localeToTimeZone(new Locale("ar", "YE")));
		assertEquals(TimeZone.getTimeZone("Africa/Cairo"), DateUtilz.localeToTimeZone(new Locale("ar")));
		assertEquals(TimeZone.getTimeZone("Europe/Minsk"), DateUtilz.localeToTimeZone(new Locale("be", "BY")));
		assertEquals(TimeZone.getTimeZone("Europe/Minsk"), DateUtilz.localeToTimeZone(new Locale("be")));
		assertEquals(TimeZone.getTimeZone("Europe/Sofia"), DateUtilz.localeToTimeZone(new Locale("bg", "BG")));
		assertEquals(TimeZone.getTimeZone("Europe/Sofia"), DateUtilz.localeToTimeZone(new Locale("bg")));
		assertEquals(TimeZone.getTimeZone("Europe/Madrid"), DateUtilz.localeToTimeZone(new Locale("ca", "ES")));
		assertEquals(TimeZone.getTimeZone("Europe/Madrid"), DateUtilz.localeToTimeZone(new Locale("ca")));
		assertEquals(TimeZone.getTimeZone("Europe/Prague"), DateUtilz.localeToTimeZone(new Locale("cs", "CZ")));
		assertEquals(TimeZone.getTimeZone("Europe/Prague"), DateUtilz.localeToTimeZone(new Locale("cs")));
		assertEquals(TimeZone.getTimeZone("Europe/Copenhagen"), DateUtilz.localeToTimeZone(new Locale("da", "DK")));
		assertEquals(TimeZone.getTimeZone("Europe/Copenhagen"), DateUtilz.localeToTimeZone(new Locale("da")));
		assertEquals(TimeZone.getTimeZone("Europe/Vienna"), DateUtilz.localeToTimeZone(new Locale("de", "AT")));
		assertEquals(TimeZone.getTimeZone("Europe/Zurich"), DateUtilz.localeToTimeZone(new Locale("de", "CH")));
		assertEquals(TimeZone.getTimeZone("Europe/Berlin"), DateUtilz.localeToTimeZone(new Locale("de", "DE")));
		assertEquals(TimeZone.getTimeZone("Europe/Luxembourg"), DateUtilz.localeToTimeZone(new Locale("de", "LU")));
		assertEquals(TimeZone.getTimeZone("Europe/Berlin"), DateUtilz.localeToTimeZone(new Locale("de")));
		assertEquals(TimeZone.getTimeZone("Asia/Nicosia"), DateUtilz.localeToTimeZone(new Locale("el", "CY")));
		assertEquals(TimeZone.getTimeZone("Europe/Athens"), DateUtilz.localeToTimeZone(new Locale("el", "GR")));
		assertEquals(TimeZone.getTimeZone("Asia/Nicosia"), DateUtilz.localeToTimeZone(new Locale("el")));
		assertEquals(TimeZone.getTimeZone("Australia/Sydney"), DateUtilz.localeToTimeZone(new Locale("en", "AU")));
		assertEquals(TimeZone.getTimeZone("America/Toronto"), DateUtilz.localeToTimeZone(new Locale("en", "CA")));
		assertEquals(TimeZone.getTimeZone("Europe/London"), DateUtilz.localeToTimeZone(new Locale("en", "GB")));
		assertEquals(TimeZone.getTimeZone("Europe/Dublin"), DateUtilz.localeToTimeZone(new Locale("en", "IE")));
		assertEquals(TimeZone.getTimeZone("Asia/Kolkata"), DateUtilz.localeToTimeZone(new Locale("en", "IN")));
		assertEquals(TimeZone.getTimeZone("Europe/Malta"), DateUtilz.localeToTimeZone(new Locale("en", "MT")));
		assertEquals(TimeZone.getTimeZone("Pacific/Auckland"), DateUtilz.localeToTimeZone(new Locale("en", "NZ")));
		assertEquals(TimeZone.getTimeZone("Asia/Manila"), DateUtilz.localeToTimeZone(new Locale("en", "PH")));
		assertEquals(TimeZone.getTimeZone("Asia/Singapore"), DateUtilz.localeToTimeZone(new Locale("en", "SG")));
		assertEquals(TimeZone.getTimeZone("America/New_York"), DateUtilz.localeToTimeZone(new Locale("en", "US")));
		assertEquals(TimeZone.getTimeZone("Africa/Johannesburg"), DateUtilz.localeToTimeZone(new Locale("en", "ZA")));
		assertEquals(TimeZone.getTimeZone("America/New_York"), DateUtilz.localeToTimeZone(new Locale("en")));
		assertEquals(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"), DateUtilz.localeToTimeZone(new Locale("es", "AR")));
		assertEquals(TimeZone.getTimeZone("America/La_Paz"), DateUtilz.localeToTimeZone(new Locale("es", "BO")));
		assertEquals(TimeZone.getTimeZone("America/Santiago"), DateUtilz.localeToTimeZone(new Locale("es", "CL")));
		assertEquals(TimeZone.getTimeZone("America/Bogota"), DateUtilz.localeToTimeZone(new Locale("es", "CO")));
		assertEquals(TimeZone.getTimeZone("America/Costa_Rica"), DateUtilz.localeToTimeZone(new Locale("es", "CR")));
		assertEquals(TimeZone.getTimeZone("America/Santo_Domingo"), DateUtilz.localeToTimeZone(new Locale("es", "DO")));
		assertEquals(TimeZone.getTimeZone("America/Guayaquil"), DateUtilz.localeToTimeZone(new Locale("es", "EC")));
		assertEquals(TimeZone.getTimeZone("Europe/Madrid"), DateUtilz.localeToTimeZone(new Locale("es", "ES")));
		assertEquals(TimeZone.getTimeZone("America/Guatemala"), DateUtilz.localeToTimeZone(new Locale("es", "GT")));
		assertEquals(TimeZone.getTimeZone("America/Tegucigalpa"), DateUtilz.localeToTimeZone(new Locale("es", "HN")));
		assertEquals(TimeZone.getTimeZone("America/Mexico_City"), DateUtilz.localeToTimeZone(new Locale("es", "MX")));
		assertEquals(TimeZone.getTimeZone("America/Managua"), DateUtilz.localeToTimeZone(new Locale("es", "NI")));
		assertEquals(TimeZone.getTimeZone("America/Panama"), DateUtilz.localeToTimeZone(new Locale("es", "PA")));
		assertEquals(TimeZone.getTimeZone("America/Lima"), DateUtilz.localeToTimeZone(new Locale("es", "PE")));
		assertEquals(TimeZone.getTimeZone("America/Puerto_Rico"), DateUtilz.localeToTimeZone(new Locale("es", "PR")));
		assertEquals(TimeZone.getTimeZone("America/Asuncion"), DateUtilz.localeToTimeZone(new Locale("es", "PY")));
		assertEquals(TimeZone.getTimeZone("America/El_Salvador"), DateUtilz.localeToTimeZone(new Locale("es", "SV")));
		assertEquals(TimeZone.getTimeZone("America/New_York"), DateUtilz.localeToTimeZone(new Locale("es", "US")));
		assertEquals(TimeZone.getTimeZone("America/Montevideo"), DateUtilz.localeToTimeZone(new Locale("es", "UY")));
		assertEquals(TimeZone.getTimeZone("America/Caracas"), DateUtilz.localeToTimeZone(new Locale("es", "VE")));
		assertEquals(TimeZone.getTimeZone("Europe/Madrid"), DateUtilz.localeToTimeZone(new Locale("es")));
		assertEquals(TimeZone.getTimeZone("Europe/Tallinn"), DateUtilz.localeToTimeZone(new Locale("et", "EE")));
		assertEquals(TimeZone.getTimeZone("Europe/Tallinn"), DateUtilz.localeToTimeZone(new Locale("et")));
		assertEquals(TimeZone.getTimeZone("Europe/Helsinki"), DateUtilz.localeToTimeZone(new Locale("fi", "FI")));
		assertEquals(TimeZone.getTimeZone("Europe/Helsinki"), DateUtilz.localeToTimeZone(new Locale("fi")));
		assertEquals(TimeZone.getTimeZone("Europe/Brussels"), DateUtilz.localeToTimeZone(new Locale("fr", "BE")));
		assertEquals(TimeZone.getTimeZone("America/Toronto"), DateUtilz.localeToTimeZone(new Locale("fr", "CA")));
		assertEquals(TimeZone.getTimeZone("Europe/Zurich"), DateUtilz.localeToTimeZone(new Locale("fr", "CH")));
		assertEquals(TimeZone.getTimeZone("Europe/Paris"), DateUtilz.localeToTimeZone(new Locale("fr", "FR")));
		assertEquals(TimeZone.getTimeZone("Europe/Luxembourg"), DateUtilz.localeToTimeZone(new Locale("fr", "LU")));
		assertEquals(TimeZone.getTimeZone("Europe/Paris"), DateUtilz.localeToTimeZone(new Locale("fr")));
		assertEquals(TimeZone.getTimeZone("Europe/Dublin"), DateUtilz.localeToTimeZone(new Locale("ga", "IE")));
		assertEquals(TimeZone.getTimeZone("Europe/Dublin"), DateUtilz.localeToTimeZone(new Locale("ga")));
		assertEquals(TimeZone.getTimeZone("Asia/Kolkata"), DateUtilz.localeToTimeZone(new Locale("hi", "IN")));
		assertEquals(TimeZone.getTimeZone("Europe/Zagreb"), DateUtilz.localeToTimeZone(new Locale("hr", "HR")));
		assertEquals(TimeZone.getTimeZone("Europe/Zagreb"), DateUtilz.localeToTimeZone(new Locale("hr")));
		assertEquals(TimeZone.getTimeZone("Europe/Budapest"), DateUtilz.localeToTimeZone(new Locale("hu", "HU")));
		assertEquals(TimeZone.getTimeZone("Europe/Budapest"), DateUtilz.localeToTimeZone(new Locale("hu")));
		assertEquals(TimeZone.getTimeZone("Asia/Jakarta"), DateUtilz.localeToTimeZone(new Locale("in", "ID")));
		assertEquals(TimeZone.getTimeZone("Asia/Jakarta"), DateUtilz.localeToTimeZone(new Locale("in")));
		assertEquals(TimeZone.getTimeZone("Atlantic/Reykjavik"), DateUtilz.localeToTimeZone(new Locale("is", "IS")));
		assertEquals(TimeZone.getTimeZone("Atlantic/Reykjavik"), DateUtilz.localeToTimeZone(new Locale("is")));
		assertEquals(TimeZone.getTimeZone("Europe/Zurich"), DateUtilz.localeToTimeZone(new Locale("it", "CH")));
		assertEquals(TimeZone.getTimeZone("Europe/Rome"), DateUtilz.localeToTimeZone(new Locale("it", "IT")));
		assertEquals(TimeZone.getTimeZone("Europe/Rome"), DateUtilz.localeToTimeZone(new Locale("it")));
		assertEquals(TimeZone.getTimeZone("Asia/Jerusalem"), DateUtilz.localeToTimeZone(new Locale("iw", "IL")));
		assertEquals(TimeZone.getTimeZone("Asia/Jerusalem"), DateUtilz.localeToTimeZone(new Locale("iw")));
		assertEquals(TimeZone.getTimeZone("Asia/Tokyo"), DateUtilz.localeToTimeZone(new Locale("ja", "JP")));
		assertEquals(TimeZone.getTimeZone("Asia/Tokyo"), DateUtilz.localeToTimeZone(new Locale("ja")));
		assertEquals(TimeZone.getTimeZone("Asia/Seoul"), DateUtilz.localeToTimeZone(new Locale("ko", "KR")));
		assertEquals(TimeZone.getTimeZone("Asia/Seoul"), DateUtilz.localeToTimeZone(new Locale("ko")));
		assertEquals(TimeZone.getTimeZone("Europe/Vilnius"), DateUtilz.localeToTimeZone(new Locale("lt", "LT")));
		assertEquals(TimeZone.getTimeZone("Europe/Vilnius"), DateUtilz.localeToTimeZone(new Locale("lt")));
		assertEquals(TimeZone.getTimeZone("Europe/Riga"), DateUtilz.localeToTimeZone(new Locale("lv", "LV")));
		assertEquals(TimeZone.getTimeZone("Europe/Riga"), DateUtilz.localeToTimeZone(new Locale("lv")));
		assertEquals(TimeZone.getTimeZone("Europe/Skopje"), DateUtilz.localeToTimeZone(new Locale("mk", "MK")));
		assertEquals(TimeZone.getTimeZone("Europe/Skopje"), DateUtilz.localeToTimeZone(new Locale("mk")));
		assertEquals(TimeZone.getTimeZone("Asia/Kuala_Lumpur"), DateUtilz.localeToTimeZone(new Locale("ms", "MY")));
		assertEquals(TimeZone.getTimeZone("Asia/Kuala_Lumpur"), DateUtilz.localeToTimeZone(new Locale("ms")));
		assertEquals(TimeZone.getTimeZone("Europe/Malta"), DateUtilz.localeToTimeZone(new Locale("mt", "MT")));
		assertEquals(TimeZone.getTimeZone("Europe/Malta"), DateUtilz.localeToTimeZone(new Locale("mt")));
		assertEquals(TimeZone.getTimeZone("Europe/Brussels"), DateUtilz.localeToTimeZone(new Locale("nl", "BE")));
		assertEquals(TimeZone.getTimeZone("Europe/Amsterdam"), DateUtilz.localeToTimeZone(new Locale("nl", "NL")));
		assertEquals(TimeZone.getTimeZone("Europe/Amsterdam"), DateUtilz.localeToTimeZone(new Locale("nl")));
		assertEquals(TimeZone.getTimeZone("Europe/Oslo"), DateUtilz.localeToTimeZone(new Locale("no", "NO")));
		assertEquals(TimeZone.getTimeZone("Europe/Oslo"), DateUtilz.localeToTimeZone(new Locale("no")));
		assertEquals(TimeZone.getTimeZone("Europe/Warsaw"), DateUtilz.localeToTimeZone(new Locale("pl", "PL")));
		assertEquals(TimeZone.getTimeZone("Europe/Warsaw"), DateUtilz.localeToTimeZone(new Locale("pl")));
		assertEquals(TimeZone.getTimeZone("America/Sao_Paulo"), DateUtilz.localeToTimeZone(new Locale("pt", "BR")));
		assertEquals(TimeZone.getTimeZone("Europe/Lisbon"), DateUtilz.localeToTimeZone(new Locale("pt", "PT")));
		assertEquals(TimeZone.getTimeZone("Europe/Lisbon"), DateUtilz.localeToTimeZone(new Locale("pt")));
		assertEquals(TimeZone.getTimeZone("Europe/Bucharest"), DateUtilz.localeToTimeZone(new Locale("ro", "RO")));
		assertEquals(TimeZone.getTimeZone("Europe/Bucharest"), DateUtilz.localeToTimeZone(new Locale("ro")));
		assertEquals(TimeZone.getTimeZone("Europe/Moscow"), DateUtilz.localeToTimeZone(new Locale("ru", "RU")));
		assertEquals(TimeZone.getTimeZone("Europe/Moscow"), DateUtilz.localeToTimeZone(new Locale("ru")));
		assertEquals(TimeZone.getTimeZone("Europe/Bratislava"), DateUtilz.localeToTimeZone(new Locale("sk", "SK")));
		assertEquals(TimeZone.getTimeZone("Europe/Bratislava"), DateUtilz.localeToTimeZone(new Locale("sk")));
		assertEquals(TimeZone.getTimeZone("Europe/Ljubljana"), DateUtilz.localeToTimeZone(new Locale("sl", "SI")));
		assertEquals(TimeZone.getTimeZone("Europe/Ljubljana"), DateUtilz.localeToTimeZone(new Locale("sl")));
		assertEquals(TimeZone.getTimeZone("Europe/Tirane"), DateUtilz.localeToTimeZone(new Locale("sq", "AL")));
		assertEquals(TimeZone.getTimeZone("Europe/Tirane"), DateUtilz.localeToTimeZone(new Locale("sq")));
		assertEquals(TimeZone.getTimeZone("Europe/Sarajevo"), DateUtilz.localeToTimeZone(new Locale("sr", "BA")));
		assertEquals(TimeZone.getTimeZone("Europe/Podgorica"), DateUtilz.localeToTimeZone(new Locale("sr", "ME")));
		assertEquals(TimeZone.getTimeZone("Europe/Belgrade"), DateUtilz.localeToTimeZone(new Locale("sr", "RS")));
		assertEquals(TimeZone.getTimeZone("Europe/Sarajevo"), DateUtilz.localeToTimeZone(new Locale("sr")));
		assertEquals(TimeZone.getTimeZone("Europe/Stockholm"), DateUtilz.localeToTimeZone(new Locale("sv", "SE")));
		assertEquals(TimeZone.getTimeZone("Europe/Stockholm"), DateUtilz.localeToTimeZone(new Locale("sv")));
		assertEquals(TimeZone.getTimeZone("Asia/Bangkok"), DateUtilz.localeToTimeZone(new Locale("th", "TH")));
		assertEquals(TimeZone.getTimeZone("Asia/Bangkok"), DateUtilz.localeToTimeZone(new Locale("th")));
		assertEquals(TimeZone.getTimeZone("Europe/Istanbul"), DateUtilz.localeToTimeZone(new Locale("tr", "TR")));
		assertEquals(TimeZone.getTimeZone("Europe/Istanbul"), DateUtilz.localeToTimeZone(new Locale("tr")));
		assertEquals(TimeZone.getTimeZone("Europe/Kiev"), DateUtilz.localeToTimeZone(new Locale("uk", "UA")));
		assertEquals(TimeZone.getTimeZone("Europe/Kiev"), DateUtilz.localeToTimeZone(new Locale("uk")));
		assertEquals(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"), DateUtilz.localeToTimeZone(new Locale("vi", "VN")));
		assertEquals(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"), DateUtilz.localeToTimeZone(new Locale("vi")));
		assertEquals(TimeZone.getTimeZone("Asia/Shanghai"), DateUtilz.localeToTimeZone(new Locale("zh", "CN")));
		assertEquals(TimeZone.getTimeZone("Asia/Hong_Kong"), DateUtilz.localeToTimeZone(new Locale("zh", "HK")));
		assertEquals(TimeZone.getTimeZone("Asia/Singapore"), DateUtilz.localeToTimeZone(new Locale("zh", "SG")));
		assertEquals(TimeZone.getTimeZone("Asia/Taipei"), DateUtilz.localeToTimeZone(new Locale("zh", "TW")));
		assertEquals(TimeZone.getTimeZone("Asia/Shanghai"), DateUtilz.localeToTimeZone(new Locale("zh")));

		assertEquals(null, DateUtilz.localeToTimeZone(new Locale("xx", "XX")));
	}
	
	@Test
	public void testParseDate() throws Exception {
		TimeZone tzUTC = TimeZone.getTimeZone("UTC");
		TimeZone tzEST = TimeZone.getTimeZone("US/Eastern");
		
		Locale lcUS = Locale.US;
		
		Calendar calUTC = Calendar.getInstance(tzUTC, lcUS);
		Calendar calUTC_MDIFW4 = Calendar.getInstance(tzUTC, lcUS);
		calUTC_MDIFW4.setMinimalDaysInFirstWeek(4);
		calUTC_MDIFW4.setFirstDayOfWeek(Calendar.MONDAY);
		Calendar calEST = Calendar.getInstance(tzEST, lcUS);
		
		String[] parsePatterns = {
				"yyyy-MM-dd'T'HH:mm:ss,SSS",
				"yyyy-MM-dd'T'HH:mm:ss",
				"yyyy-MM-dd",
				"YYYY-'W'ww-u'T'HH:mm:ss,SSS",
				"YYYY-'W'ww-u'T'HH:mm:ss",
				"YYYY-'W'ww-u"
		};
		
		// Parsing order
		assertEquals(date(2016, 12, 31, 1, 23, 45, 678, tzUTC), DateUtilz.parseDate("2016-12-31T01:23:45,678", calUTC, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 1, 1, 23, 45, 678, tzUTC), DateUtilz.parseDate("2017-01-01T01:23:45,678", calUTC, lcUS, parsePatterns));
		
		assertEquals(date(2016, 12, 31, 1, 23, 45, 0, tzUTC), DateUtilz.parseDate("2016-12-31T01:23:45", calUTC, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 1, 1, 23, 45, 0, tzUTC), DateUtilz.parseDate("2017-01-01T01:23:45", calUTC, lcUS, parsePatterns));
		
		assertEquals(date(2016, 12, 31, 0, 0, 0, 0, tzUTC), DateUtilz.parseDate("2016-12-31", calUTC, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 1, 0, 0, 0, 0, tzUTC), DateUtilz.parseDate("2017-01-01", calUTC, lcUS, parsePatterns));
		
		// TimeZone
		assertEquals(date(2016, 12, 31, 1, 23, 45, 678, tzEST), DateUtilz.parseDate("2016-12-31T01:23:45,678", calEST, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 1, 1, 23, 45, 678, tzEST), DateUtilz.parseDate("2017-01-01T01:23:45,678", calEST, lcUS, parsePatterns));
		
		// Calendar
		assertEquals(date(2016, 12, 24, 1, 23, 45, 678, tzUTC), DateUtilz.parseDate("2016-W52-6T01:23:45,678", calUTC, lcUS, parsePatterns));
		
		assertEquals(date(2016, 12, 31, 1, 23, 45, 678, tzUTC), DateUtilz.parseDate("2016-W52-6T01:23:45,678", calUTC_MDIFW4, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 1, 1, 23, 45, 678, tzUTC), DateUtilz.parseDate("2016-W52-7T01:23:45,678", calUTC_MDIFW4, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 2, 1, 23, 45, 678, tzUTC), DateUtilz.parseDate("2017-W01-1T01:23:45,678", calUTC_MDIFW4, lcUS, parsePatterns));
		
		assertEquals(date(2016, 12, 31, 1, 23, 45, 0, tzUTC), DateUtilz.parseDate("2016-W52-6T01:23:45", calUTC_MDIFW4, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 1, 1, 23, 45, 0, tzUTC), DateUtilz.parseDate("2016-W52-7T01:23:45", calUTC_MDIFW4, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 2, 1, 23, 45, 0, tzUTC), DateUtilz.parseDate("2017-W01-1T01:23:45", calUTC_MDIFW4, lcUS, parsePatterns));
		
		assertEquals(date(2016, 12, 31, 0, 0, 0, 0, tzUTC), DateUtilz.parseDate("2016-W52-6", calUTC_MDIFW4, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 1, 0, 0, 0, 0, tzUTC), DateUtilz.parseDate("2016-W52-7", calUTC_MDIFW4, lcUS, parsePatterns));
		assertEquals(date(2017, 1, 2, 0, 0, 0, 0, tzUTC), DateUtilz.parseDate("2017-W01-1", calUTC_MDIFW4, lcUS, parsePatterns));
	}
	
	@Test
	public void testCompareChrono() throws Exception {
		LocalDateTime defLdt = LocalDateTime.of(2000, 2, 29, 12, 34, 56, 7891011);
		ZonedDateTime defZdtZ = ZonedDateTime.of(2000, 2, 29, 12, 34, 56, 7891011, ZoneId.of("UTC"));
		OffsetDateTime defOdtZ = OffsetDateTime.of(2000, 2, 29, 12, 34, 56, 7891011, ZoneOffset.ofHoursMinutes(0, 0));
		
		LocalDate defLd = LocalDate.of(2000, 2, 29);
		
		LocalTime defLt = LocalTime.of(12, 34, 56, 7891011);
		OffsetTime defOtZ = OffsetTime.of(12, 34, 56, 7891011, ZoneOffset.ofHoursMinutes(0, 0));
		
		Year defY = Year.of(2000);
		YearMonth defYm = YearMonth.of(2000, 2);
		MonthDay defMd = MonthDay.of(2, 29);
		Month defM = Month.FEBRUARY;
		DayOfWeek defW = DayOfWeek.TUESDAY;
		
		ZonedDateTime defZdtTokyo = ZonedDateTime.of(2000, 2, 29, 12, 34, 56, 7891011, ZoneId.of("Asia/Tokyo"));
		OffsetDateTime defOdt0900 = OffsetDateTime.of(2000, 2, 29, 12, 34, 56, 7891011, ZoneOffset.ofHoursMinutes(9, 0));
		
		OffsetTime defOt0900 = OffsetTime.of(12, 34, 56, 7891011, ZoneOffset.ofHoursMinutes(9, 0));
		

		TemporalAccessor[] defsDateTime = {
				defLdt,
				defZdtZ,
				defOdtZ,
		};
		
		TemporalAccessor[] defsDate = {
				defLd,
				defY,
				defYm,
				defMd,
				defM,
				defW,
		};
		
		TemporalAccessor[] defsTime = {
				defLt,
				defOtZ,
		};
		
		for (TemporalAccessor t1 : join(defsDateTime, defsDate)) {
			for (TemporalAccessor t2 : join(defsDateTime, defsDate)) {
				if (t1 == defY && !t2.isSupported(ChronoField.YEAR)) {
					assertEquals(t1 + " / " + t2, 2, DateUtilz.compareChrono(t1, t2, true));
				} else if (t2 == defY && !t1.isSupported(ChronoField.YEAR)) {
					assertEquals(t1 + " / " + t2, -2, DateUtilz.compareChrono(t1, t2, true));
				} else if (t1 == defW && !t2.isSupported(ChronoField.DAY_OF_WEEK)) {
					if (t2.isSupported(ChronoField.YEAR) || t2.isSupported(ChronoField.MONTH_OF_YEAR) || t2.isSupported(ChronoField.DAY_OF_MONTH)) {
						assertEquals(t1 + " / " + t2, -2, DateUtilz.compareChrono(t1, t2, true));
					} else {
						assertEquals(t1 + " / " + t2, 2, DateUtilz.compareChrono(t1, t2, true));
					}
				} else if (t2 == defW && !t1.isSupported(ChronoField.DAY_OF_WEEK)) {
					if (t1.isSupported(ChronoField.YEAR) || t1.isSupported(ChronoField.MONTH_OF_YEAR) || t1.isSupported(ChronoField.DAY_OF_MONTH)) {
						assertEquals(t1 + " / " + t2, 2, DateUtilz.compareChrono(t1, t2, true));
					} else {
						assertEquals(t1 + " / " + t2, -2, DateUtilz.compareChrono(t1, t2, true));
					}
				} else {
					assertEquals(t1 + " / " + t2, 0, DateUtilz.compareChrono(t1, t2, true));
				}
			}
		}
		
		for (TemporalAccessor t1 : join(defsDateTime, defsTime)) {
			for (TemporalAccessor t2 : join(defsDateTime, defsTime)) {
				assertEquals(t1 + " / " + t2, 0, DateUtilz.compareChrono(t1, t2, true));
			}
		}
		
		for (TemporalAccessor t1 : defsDate) {
			for (TemporalAccessor t2 : defsTime) {
				assertEquals(t1 + " / " + t2, 2, DateUtilz.compareChrono(t1, t2, true));
			}
		}
		
		for (TemporalAccessor t1 : defsTime) {
			for (TemporalAccessor t2 : defsDate) {
				assertEquals(t1 + " / " + t2, -2, DateUtilz.compareChrono(t1, t2, true));
			}
		}
		
		for (TemporalAccessor t1 : defsDate) {
			for (TemporalAccessor t2 : defsTime) {
				assertEquals(t1 + " / " + t2, 2, DateUtilz.compareChrono(t1, t2, false));
			}
		}
		
		for (TemporalAccessor t1 : defsTime) {
			for (TemporalAccessor t2 : defsDate) {
				assertEquals(t1 + " / " + t2, -2, DateUtilz.compareChrono(t1, t2, false));
			}
		}

		assertEquals(0, DateUtilz.compareChrono(defLdt, defZdtZ, false));
		assertEquals(0, DateUtilz.compareChrono(defLdt, defOdtZ, false));
		assertEquals(0, DateUtilz.compareChrono(defZdtZ, defOdtZ, false));
		assertEquals(0, DateUtilz.compareChrono(defZdtZ, defOdtZ, false));

		assertEquals(0, DateUtilz.compareChrono(defZdtTokyo, defOdt0900, true));
		assertEquals(0, DateUtilz.compareChrono(defZdtTokyo, defOdt0900, false));

		assertEquals(0, DateUtilz.compareChrono(defZdtTokyo, defOt0900, true));
		assertEquals(2, DateUtilz.compareChrono(defZdtTokyo, defOt0900, false));

		assertEquals(2, DateUtilz.compareChrono(defLd, defLt, false));
	}
	
	@Test
	public void testToInstance() throws Exception {
		assertEquals(null, DateUtilz.toInstant(null, ZoneId.of("UTC"), 1970, Month.JANUARY, 1));
		
		assertEquals(Instant.ofEpochMilli(0), DateUtilz.toInstant(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.of("UTC")), ZoneId.of("UTC"), 1970, Month.JANUARY, 1));
		assertEquals(Instant.ofEpochMilli(1), DateUtilz.toInstant(ZonedDateTime.ofInstant(Instant.ofEpochMilli(1), ZoneId.of("UTC")), ZoneId.of("UTC"), 1970, Month.JANUARY, 1));
		
		// ZonedDateTime
		// 2020-01-23T01:23:45.678Z = 1579742625678
		assertEquals(Instant.ofEpochMilli(1579742625678L), DateUtilz.toInstant(ZonedDateTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("UTC")), ZoneId.of("UTC"), 1970, Month.JANUARY, 1));
		assertEquals(Instant.ofEpochMilli(1579742625678L), DateUtilz.toInstant(ZonedDateTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("Asia/Tokyo")), ZoneId.of("UTC"), 1970, Month.JANUARY, 1));
		assertEquals(Instant.ofEpochMilli(1579742625678L), DateUtilz.toInstant(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneOffset.of("Z")), ZoneId.of("UTC"), 1970, Month.JANUARY, 1));
		assertEquals(Instant.ofEpochMilli(1579742625678L), DateUtilz.toInstant(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneOffset.of("+09:00")), ZoneId.of("UTC"), 1970, Month.JANUARY, 1));
		
		// OffsetTime
		// 2020-01-23T01:23:45.678Z = 1579742625678
		// 1989-02-03T01:23:45.678Z = 602472225678
		assertEquals(Instant.ofEpochMilli(602472225678L), DateUtilz.toInstant(OffsetTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneOffset.of("Z")), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		assertEquals(Instant.ofEpochMilli(602472225678L), DateUtilz.toInstant(OffsetTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneOffset.of("+09:00")), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		
		// LocalDateTime
		// 2020-01-23T01:23:45.678Z = 1579742625678
		// 2020-01-23T10:23:45.678Z = 1579775025678
		assertEquals(Instant.ofEpochMilli(1579742625678L), DateUtilz.toInstant(LocalDateTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("UTC")), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		assertEquals(Instant.ofEpochMilli(1579775025678L), DateUtilz.toInstant(LocalDateTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("Asia/Tokyo")), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		assertEquals(Instant.ofEpochMilli(1579742625678L), DateUtilz.toInstant(LocalDateTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("Asia/Tokyo")), ZoneId.of("Asia/Tokyo"), 1989, Month.FEBRUARY, 3));
		
		// LocalDate
		// 2020-01-23T00:00:00.000Z = 1579737600000
		// 2020-01-23T00:00:00.000+09:00 = 1579705200000
		assertEquals(Instant.ofEpochMilli(1579737600000L), DateUtilz.toInstant(LocalDate.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("UTC")), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		assertEquals(Instant.ofEpochMilli(1579737600000L), DateUtilz.toInstant(LocalDate.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("Asia/Tokyo")), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		assertEquals(Instant.ofEpochMilli(1579705200000L), DateUtilz.toInstant(LocalDate.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("Asia/Tokyo")), ZoneId.of("Asia/Tokyo"), 1989, Month.FEBRUARY, 3));
		
		// LocalTime
		// 1989-02-03T01:23:45.678Z = 602472225678
		// 1989-02-03T10:23:45.678Z = 602504625678
		assertEquals(Instant.ofEpochMilli(602472225678L), DateUtilz.toInstant(LocalTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("UTC")), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		assertEquals(Instant.ofEpochMilli(602504625678L), DateUtilz.toInstant(LocalTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("Asia/Tokyo")), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		assertEquals(Instant.ofEpochMilli(602472225678L), DateUtilz.toInstant(LocalTime.ofInstant(Instant.ofEpochMilli(1579742625678L), ZoneId.of("Asia/Tokyo")), ZoneId.of("Asia/Tokyo"), 1989, Month.FEBRUARY, 3));
		
		// Year
		// 2012-02-03T00:00:00.000Z = 602472225678
		assertEquals(Instant.ofEpochMilli(1328227200000L), DateUtilz.toInstant(Year.of(2012), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		
		// YearMonth
		// 2012-03-03T00:00:00.000Z = 602472225678
		assertEquals(Instant.ofEpochMilli(1330732800000L), DateUtilz.toInstant(YearMonth.of(2012, Month.MARCH), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		
		// Month
		// 1989-03-03T00:00:00.000Z = 604886400000
		assertEquals(Instant.ofEpochMilli(604886400000L), DateUtilz.toInstant(Month.MARCH, ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		
		// MonthDay
		// 1989-03-04T00:00:00.000Z = 604972800000
		assertEquals(Instant.ofEpochMilli(604972800000L), DateUtilz.toInstant(MonthDay.of(Month.MARCH, 4), ZoneId.of("UTC"), 1989, Month.FEBRUARY, 3));
		
	}
	
	
	private static TemporalAccessor[] join(TemporalAccessor[] t1, TemporalAccessor[] t2) {
		TemporalAccessor[] t3 = new TemporalAccessor[t1.length + t2.length];
		System.arraycopy(t1, 0, t3, 0, t1.length);
		System.arraycopy(t2, 0, t3, t1.length, t2.length);
		return t3;
	}
	
	private static Date date(int year, int month, int date, int hourOfDay, int minute, int second, int msecond, TimeZone timeZone) {
		Calendar cal = Calendar.getInstance(timeZone);
		cal.set(year, month - 1, date, hourOfDay, minute, second);
		cal.set(Calendar.MILLISECOND, msecond);
		return cal.getTime();
	}
}
