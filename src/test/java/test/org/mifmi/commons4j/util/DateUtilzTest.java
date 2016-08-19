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

import org.junit.Test;
import org.mifmi.commons4j.util.DateUtilz;

public class DateUtilzTest {

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
	
	private TemporalAccessor[] join(TemporalAccessor[] t1, TemporalAccessor[] t2) {
		TemporalAccessor[] t3 = new TemporalAccessor[t1.length + t2.length];
		System.arraycopy(t1, 0, t3, 0, t1.length);
		System.arraycopy(t2, 0, t3, t1.length, t2.length);
		return t3;
	}
}
