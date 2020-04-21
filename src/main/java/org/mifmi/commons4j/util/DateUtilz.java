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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.zone.ZoneRulesException;
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
	
	private static final Map<String, String[]> COUNTRY_TZ_MAP;
	private static final Map<String, String> LANGUAGE_DEFAULT_COUNTRY_MAP;
	static {
		// cf. https://www.iana.org/time-zones  (tzdata2019c.tar.gz/zone.tab)
		COUNTRY_TZ_MAP = new HashMap<>(247);
		COUNTRY_TZ_MAP.put("AD", new String[] {
			"Europe/Andorra",	// +4230+00131
		});
		COUNTRY_TZ_MAP.put("AE", new String[] {
			"Asia/Dubai",	// +2518+05518
		});
		COUNTRY_TZ_MAP.put("AF", new String[] {
			"Asia/Kabul",	// +3431+06912
		});
		COUNTRY_TZ_MAP.put("AG", new String[] {
			"America/Antigua",	// +1703-06148
		});
		COUNTRY_TZ_MAP.put("AI", new String[] {
			"America/Anguilla",	// +1812-06304
		});
		COUNTRY_TZ_MAP.put("AL", new String[] {
			"Europe/Tirane",	// +4120+01950
		});
		COUNTRY_TZ_MAP.put("AM", new String[] {
			"Asia/Yerevan",	// +4011+04430
		});
		COUNTRY_TZ_MAP.put("AO", new String[] {
			"Africa/Luanda",	// -0848+01314
		});
		COUNTRY_TZ_MAP.put("AQ", new String[] {
			"Antarctica/McMurdo",	// -7750+16636, New Zealand time - McMurdo, South Pole
			"Antarctica/Casey",	// -6617+11031, Casey
			"Antarctica/Davis",	// -6835+07758, Davis
			"Antarctica/DumontDUrville",	// -6640+14001, Dumont-d'Urville
			"Antarctica/Mawson",	// -6736+06253, Mawson
			"Antarctica/Palmer",	// -6448-06406, Palmer
			"Antarctica/Rothera",	// -6734-06808, Rothera
			"Antarctica/Syowa",	// -690022+0393524, Syowa
			"Antarctica/Troll",	// -720041+0023206, Troll
			"Antarctica/Vostok",	// -7824+10654, Vostok
		});
		COUNTRY_TZ_MAP.put("AR", new String[] {
			"America/Argentina/Buenos_Aires",	// -3436-05827, Buenos Aires (BA, CF)
			"America/Argentina/Cordoba",	// -3124-06411, Argentina (most areas: CB, CC, CN, ER, FM, MN, SE, SF)
			"America/Argentina/Salta",	// -2447-06525, Salta (SA, LP, NQ, RN)
			"America/Argentina/Jujuy",	// -2411-06518, Jujuy (JY)
			"America/Argentina/Tucuman",	// -2649-06513, Tucuman (TM)
			"America/Argentina/Catamarca",	// -2828-06547, Catamarca (CT); Chubut (CH)
			"America/Argentina/La_Rioja",	// -2926-06651, La Rioja (LR)
			"America/Argentina/San_Juan",	// -3132-06831, San Juan (SJ)
			"America/Argentina/Mendoza",	// -3253-06849, Mendoza (MZ)
			"America/Argentina/San_Luis",	// -3319-06621, San Luis (SL)
			"America/Argentina/Rio_Gallegos",	// -5138-06913, Santa Cruz (SC)
			"America/Argentina/Ushuaia",	// -5448-06818, Tierra del Fuego (TF)
		});
		COUNTRY_TZ_MAP.put("AS", new String[] {
			"Pacific/Pago_Pago",	// -1416-17042
		});
		COUNTRY_TZ_MAP.put("AT", new String[] {
			"Europe/Vienna",	// +4813+01620
		});
		COUNTRY_TZ_MAP.put("AU", new String[] {
			"Australia/Sydney",	// -3352+15113, New South Wales (most areas)  (** Change from original order **)
			"Australia/Lord_Howe",	// -3133+15905, Lord Howe Island
			"Antarctica/Macquarie",	// -5430+15857, Macquarie Island
			"Australia/Hobart",	// -4253+14719, Tasmania (most areas)
			"Australia/Currie",	// -3956+14352, Tasmania (King Island)
			"Australia/Melbourne",	// -3749+14458, Victoria
			"Australia/Broken_Hill",	// -3157+14127, New South Wales (Yancowinna)
			"Australia/Brisbane",	// -2728+15302, Queensland (most areas)
			"Australia/Lindeman",	// -2016+14900, Queensland (Whitsunday Islands)
			"Australia/Adelaide",	// -3455+13835, South Australia
			"Australia/Darwin",	// -1228+13050, Northern Territory
			"Australia/Perth",	// -3157+11551, Western Australia (most areas)
			"Australia/Eucla",	// -3143+12852, Western Australia (Eucla)
		});
		COUNTRY_TZ_MAP.put("AW", new String[] {
			"America/Aruba",	// +1230-06958
		});
		COUNTRY_TZ_MAP.put("AX", new String[] {
			"Europe/Mariehamn",	// +6006+01957
		});
		COUNTRY_TZ_MAP.put("AZ", new String[] {
			"Asia/Baku",	// +4023+04951
		});
		COUNTRY_TZ_MAP.put("BA", new String[] {
			"Europe/Sarajevo",	// +4352+01825
		});
		COUNTRY_TZ_MAP.put("BB", new String[] {
			"America/Barbados",	// +1306-05937
		});
		COUNTRY_TZ_MAP.put("BD", new String[] {
			"Asia/Dhaka",	// +2343+09025
		});
		COUNTRY_TZ_MAP.put("BE", new String[] {
			"Europe/Brussels",	// +5050+00420
		});
		COUNTRY_TZ_MAP.put("BF", new String[] {
			"Africa/Ouagadougou",	// +1222-00131
		});
		COUNTRY_TZ_MAP.put("BG", new String[] {
			"Europe/Sofia",	// +4241+02319
		});
		COUNTRY_TZ_MAP.put("BH", new String[] {
			"Asia/Bahrain",	// +2623+05035
		});
		COUNTRY_TZ_MAP.put("BI", new String[] {
			"Africa/Bujumbura",	// -0323+02922
		});
		COUNTRY_TZ_MAP.put("BJ", new String[] {
			"Africa/Porto-Novo",	// +0629+00237
		});
		COUNTRY_TZ_MAP.put("BL", new String[] {
			"America/St_Barthelemy",	// +1753-06251
		});
		COUNTRY_TZ_MAP.put("BM", new String[] {
			"Atlantic/Bermuda",	// +3217-06446
		});
		COUNTRY_TZ_MAP.put("BN", new String[] {
			"Asia/Brunei",	// +0456+11455
		});
		COUNTRY_TZ_MAP.put("BO", new String[] {
			"America/La_Paz",	// -1630-06809
		});
		COUNTRY_TZ_MAP.put("BQ", new String[] {
			"America/Kralendijk",	// +120903-0681636
		});
		COUNTRY_TZ_MAP.put("BR", new String[] {
			"America/Sao_Paulo",	// -2332-04637, Brazil (southeast: GO, DF, MG, ES, RJ, SP, PR, SC, RS)  (** Change from original order **)
			"America/Noronha",	// -0351-03225, Atlantic islands
			"America/Belem",	// -0127-04829, Para (east); Amapa
			"America/Fortaleza",	// -0343-03830, Brazil (northeast: MA, PI, CE, RN, PB)
			"America/Recife",	// -0803-03454, Pernambuco
			"America/Araguaina",	// -0712-04812, Tocantins
			"America/Maceio",	// -0940-03543, Alagoas, Sergipe
			"America/Bahia",	// -1259-03831, Bahia
			"America/Campo_Grande",	// -2027-05437, Mato Grosso do Sul
			"America/Cuiaba",	// -1535-05605, Mato Grosso
			"America/Santarem",	// -0226-05452, Para (west)
			"America/Porto_Velho",	// -0846-06354, Rondonia
			"America/Boa_Vista",	// +0249-06040, Roraima
			"America/Manaus",	// -0308-06001, Amazonas (east)
			"America/Eirunepe",	// -0640-06952, Amazonas (west)
			"America/Rio_Branco",	// -0958-06748, Acre
		});
		COUNTRY_TZ_MAP.put("BS", new String[] {
			"America/Nassau",	// +2505-07721
		});
		COUNTRY_TZ_MAP.put("BT", new String[] {
			"Asia/Thimphu",	// +2728+08939
		});
		COUNTRY_TZ_MAP.put("BW", new String[] {
			"Africa/Gaborone",	// -2439+02555
		});
		COUNTRY_TZ_MAP.put("BY", new String[] {
			"Europe/Minsk",	// +5354+02734
		});
		COUNTRY_TZ_MAP.put("BZ", new String[] {
			"America/Belize",	// +1730-08812
		});
		COUNTRY_TZ_MAP.put("CA", new String[] {
			"America/Toronto",	// +4339-07923, Eastern - ON, QC (most areas) (** Change from original order **)
			"America/St_Johns",	// +4734-05243, Newfoundland; Labrador (southeast)
			"America/Halifax",	// +4439-06336, Atlantic - NS (most areas); PE
			"America/Glace_Bay",	// +4612-05957, Atlantic - NS (Cape Breton)
			"America/Moncton",	// +4606-06447, Atlantic - New Brunswick
			"America/Goose_Bay",	// +5320-06025, Atlantic - Labrador (most areas)
			"America/Blanc-Sablon",	// +5125-05707, AST - QC (Lower North Shore)
			"America/Nipigon",	// +4901-08816, Eastern - ON, QC (no DST 1967-73)
			"America/Thunder_Bay",	// +4823-08915, Eastern - ON (Thunder Bay)
			"America/Iqaluit",	// +6344-06828, Eastern - NU (most east areas)
			"America/Pangnirtung",	// +6608-06544, Eastern - NU (Pangnirtung)
			"America/Atikokan",	// +484531-0913718, EST - ON (Atikokan); NU (Coral H)
			"America/Winnipeg",	// +4953-09709, Central - ON (west); Manitoba
			"America/Rainy_River",	// +4843-09434, Central - ON (Rainy R, Ft Frances)
			"America/Resolute",	// +744144-0944945, Central - NU (Resolute)
			"America/Rankin_Inlet",	// +624900-0920459, Central - NU (central)
			"America/Regina",	// +5024-10439, CST - SK (most areas)
			"America/Swift_Current",	// +5017-10750, CST - SK (midwest)
			"America/Edmonton",	// +5333-11328, Mountain - AB; BC (E); SK (W)
			"America/Cambridge_Bay",	// +690650-1050310, Mountain - NU (west)
			"America/Yellowknife",	// +6227-11421, Mountain - NT (central)
			"America/Inuvik",	// +682059-1334300, Mountain - NT (west)
			"America/Creston",	// +4906-11631, MST - BC (Creston)
			"America/Dawson_Creek",	// +5946-12014, MST - BC (Dawson Cr, Ft St John)
			"America/Fort_Nelson",	// +5848-12242, MST - BC (Ft Nelson)
			"America/Vancouver",	// +4916-12307, Pacific - BC (most areas)
			"America/Whitehorse",	// +6043-13503, Pacific - Yukon (south)
			"America/Dawson",	// +6404-13925, Pacific - Yukon (north)
		});
		COUNTRY_TZ_MAP.put("CC", new String[] {
			"Indian/Cocos",	// -1210+09655
		});
		COUNTRY_TZ_MAP.put("CD", new String[] {
			"Africa/Kinshasa",	// -0418+01518, Dem. Rep. of Congo (west)
			"Africa/Lubumbashi",	// -1140+02728, Dem. Rep. of Congo (east)
		});
		COUNTRY_TZ_MAP.put("CF", new String[] {
			"Africa/Bangui",	// +0422+01835
		});
		COUNTRY_TZ_MAP.put("CG", new String[] {
			"Africa/Brazzaville",	// -0416+01517
		});
		COUNTRY_TZ_MAP.put("CH", new String[] {
			"Europe/Zurich",	// +4723+00832
		});
		COUNTRY_TZ_MAP.put("CI", new String[] {
			"Africa/Abidjan",	// +0519-00402
		});
		COUNTRY_TZ_MAP.put("CK", new String[] {
			"Pacific/Rarotonga",	// -2114-15946
		});
		COUNTRY_TZ_MAP.put("CL", new String[] {
			"America/Santiago",	// -3327-07040, Chile (most areas)
			"America/Punta_Arenas",	// -5309-07055, Region of Magallanes
			"Pacific/Easter",	// -2709-10926, Easter Island
		});
		COUNTRY_TZ_MAP.put("CM", new String[] {
			"Africa/Douala",	// +0403+00942
		});
		COUNTRY_TZ_MAP.put("CN", new String[] {
			"Asia/Shanghai",	// +3114+12128, Beijing Time
			"Asia/Urumqi",	// +4348+08735, Xinjiang Time
		});
		COUNTRY_TZ_MAP.put("CO", new String[] {
			"America/Bogota",	// +0436-07405
		});
		COUNTRY_TZ_MAP.put("CR", new String[] {
			"America/Costa_Rica",	// +0956-08405
		});
		COUNTRY_TZ_MAP.put("CU", new String[] {
			"America/Havana",	// +2308-08222
		});
		COUNTRY_TZ_MAP.put("CV", new String[] {
			"Atlantic/Cape_Verde",	// +1455-02331
		});
		COUNTRY_TZ_MAP.put("CW", new String[] {
			"America/Curacao",	// +1211-06900
		});
		COUNTRY_TZ_MAP.put("CX", new String[] {
			"Indian/Christmas",	// -1025+10543
		});
		COUNTRY_TZ_MAP.put("CY", new String[] {
			"Asia/Nicosia",	// +3510+03322, Cyprus (most areas)
			"Asia/Famagusta",	// +3507+03357, Northern Cyprus
		});
		COUNTRY_TZ_MAP.put("CZ", new String[] {
			"Europe/Prague",	// +5005+01426
		});
		COUNTRY_TZ_MAP.put("DE", new String[] {
			"Europe/Berlin",	// +5230+01322, Germany (most areas)
			"Europe/Busingen",	// +4742+00841, Busingen
		});
		COUNTRY_TZ_MAP.put("DJ", new String[] {
			"Africa/Djibouti",	// +1136+04309
		});
		COUNTRY_TZ_MAP.put("DK", new String[] {
			"Europe/Copenhagen",	// +5540+01235
		});
		COUNTRY_TZ_MAP.put("DM", new String[] {
			"America/Dominica",	// +1518-06124
		});
		COUNTRY_TZ_MAP.put("DO", new String[] {
			"America/Santo_Domingo",	// +1828-06954
		});
		COUNTRY_TZ_MAP.put("DZ", new String[] {
			"Africa/Algiers",	// +3647+00303
		});
		COUNTRY_TZ_MAP.put("EC", new String[] {
			"America/Guayaquil",	// -0210-07950, Ecuador (mainland)
			"Pacific/Galapagos",	// -0054-08936, Galapagos Islands
		});
		COUNTRY_TZ_MAP.put("EE", new String[] {
			"Europe/Tallinn",	// +5925+02445
		});
		COUNTRY_TZ_MAP.put("EG", new String[] {
			"Africa/Cairo",	// +3003+03115
		});
		COUNTRY_TZ_MAP.put("EH", new String[] {
			"Africa/El_Aaiun",	// +2709-01312
		});
		COUNTRY_TZ_MAP.put("ER", new String[] {
			"Africa/Asmara",	// +1520+03853
		});
		COUNTRY_TZ_MAP.put("ES", new String[] {
			"Europe/Madrid",	// +4024-00341, Spain (mainland)
			"Africa/Ceuta",	// +3553-00519, Ceuta, Melilla
			"Atlantic/Canary",	// +2806-01524, Canary Islands
		});
		COUNTRY_TZ_MAP.put("ET", new String[] {
			"Africa/Addis_Ababa",	// +0902+03842
		});
		COUNTRY_TZ_MAP.put("FI", new String[] {
			"Europe/Helsinki",	// +6010+02458
		});
		COUNTRY_TZ_MAP.put("FJ", new String[] {
			"Pacific/Fiji",	// -1808+17825
		});
		COUNTRY_TZ_MAP.put("FK", new String[] {
			"Atlantic/Stanley",	// -5142-05751
		});
		COUNTRY_TZ_MAP.put("FM", new String[] {
			"Pacific/Chuuk",	// +0725+15147, Chuuk/Truk, Yap
			"Pacific/Pohnpei",	// +0658+15813, Pohnpei/Ponape
			"Pacific/Kosrae",	// +0519+16259, Kosrae
		});
		COUNTRY_TZ_MAP.put("FO", new String[] {
			"Atlantic/Faroe",	// +6201-00646
		});
		COUNTRY_TZ_MAP.put("FR", new String[] {
			"Europe/Paris",	// +4852+00220
		});
		COUNTRY_TZ_MAP.put("GA", new String[] {
			"Africa/Libreville",	// +0023+00927
		});
		COUNTRY_TZ_MAP.put("GB", new String[] {
			"Europe/London",	// +513030-0000731
		});
		COUNTRY_TZ_MAP.put("GD", new String[] {
			"America/Grenada",	// +1203-06145
		});
		COUNTRY_TZ_MAP.put("GE", new String[] {
			"Asia/Tbilisi",	// +4143+04449
		});
		COUNTRY_TZ_MAP.put("GF", new String[] {
			"America/Cayenne",	// +0456-05220
		});
		COUNTRY_TZ_MAP.put("GG", new String[] {
			"Europe/Guernsey",	// +492717-0023210
		});
		COUNTRY_TZ_MAP.put("GH", new String[] {
			"Africa/Accra",	// +0533-00013
		});
		COUNTRY_TZ_MAP.put("GI", new String[] {
			"Europe/Gibraltar",	// +3608-00521
		});
		COUNTRY_TZ_MAP.put("GL", new String[] {
			"America/Godthab",	// +6411-05144, Greenland (most areas)
			"America/Danmarkshavn",	// +7646-01840, National Park (east coast)
			"America/Scoresbysund",	// +7029-02158, Scoresbysund/Ittoqqortoormiit
			"America/Thule",	// +7634-06847, Thule/Pituffik
		});
		COUNTRY_TZ_MAP.put("GM", new String[] {
			"Africa/Banjul",	// +1328-01639
		});
		COUNTRY_TZ_MAP.put("GN", new String[] {
			"Africa/Conakry",	// +0931-01343
		});
		COUNTRY_TZ_MAP.put("GP", new String[] {
			"America/Guadeloupe",	// +1614-06132
		});
		COUNTRY_TZ_MAP.put("GQ", new String[] {
			"Africa/Malabo",	// +0345+00847
		});
		COUNTRY_TZ_MAP.put("GR", new String[] {
			"Europe/Athens",	// +3758+02343
		});
		COUNTRY_TZ_MAP.put("GS", new String[] {
			"Atlantic/South_Georgia",	// -5416-03632
		});
		COUNTRY_TZ_MAP.put("GT", new String[] {
			"America/Guatemala",	// +1438-09031
		});
		COUNTRY_TZ_MAP.put("GU", new String[] {
			"Pacific/Guam",	// +1328+14445
		});
		COUNTRY_TZ_MAP.put("GW", new String[] {
			"Africa/Bissau",	// +1151-01535
		});
		COUNTRY_TZ_MAP.put("GY", new String[] {
			"America/Guyana",	// +0648-05810
		});
		COUNTRY_TZ_MAP.put("HK", new String[] {
			"Asia/Hong_Kong",	// +2217+11409
		});
		COUNTRY_TZ_MAP.put("HN", new String[] {
			"America/Tegucigalpa",	// +1406-08713
		});
		COUNTRY_TZ_MAP.put("HR", new String[] {
			"Europe/Zagreb",	// +4548+01558
		});
		COUNTRY_TZ_MAP.put("HT", new String[] {
			"America/Port-au-Prince",	// +1832-07220
		});
		COUNTRY_TZ_MAP.put("HU", new String[] {
			"Europe/Budapest",	// +4730+01905
		});
		COUNTRY_TZ_MAP.put("ID", new String[] {
			"Asia/Jakarta",	// -0610+10648, Java, Sumatra
			"Asia/Pontianak",	// -0002+10920, Borneo (west, central)
			"Asia/Makassar",	// -0507+11924, Borneo (east, south); Sulawesi/Celebes, Bali, Nusa Tengarra; Timor (west)
			"Asia/Jayapura",	// -0232+14042, New Guinea (West Papua / Irian Jaya); Malukus/Moluccas
		});
		COUNTRY_TZ_MAP.put("IE", new String[] {
			"Europe/Dublin",	// +5320-00615
		});
		COUNTRY_TZ_MAP.put("IL", new String[] {
			"Asia/Jerusalem",	// +314650+0351326
		});
		COUNTRY_TZ_MAP.put("IM", new String[] {
			"Europe/Isle_of_Man",	// +5409-00428
		});
		COUNTRY_TZ_MAP.put("IN", new String[] {
			"Asia/Kolkata",	// +2232+08822
		});
		COUNTRY_TZ_MAP.put("IO", new String[] {
			"Indian/Chagos",	// -0720+07225
		});
		COUNTRY_TZ_MAP.put("IQ", new String[] {
			"Asia/Baghdad",	// +3321+04425
		});
		COUNTRY_TZ_MAP.put("IR", new String[] {
			"Asia/Tehran",	// +3540+05126
		});
		COUNTRY_TZ_MAP.put("IS", new String[] {
			"Atlantic/Reykjavik",	// +6409-02151
		});
		COUNTRY_TZ_MAP.put("IT", new String[] {
			"Europe/Rome",	// +4154+01229
		});
		COUNTRY_TZ_MAP.put("JE", new String[] {
			"Europe/Jersey",	// +491101-0020624
		});
		COUNTRY_TZ_MAP.put("JM", new String[] {
			"America/Jamaica",	// +175805-0764736
		});
		COUNTRY_TZ_MAP.put("JO", new String[] {
			"Asia/Amman",	// +3157+03556
		});
		COUNTRY_TZ_MAP.put("JP", new String[] {
			"Asia/Tokyo",	// +353916+1394441
		});
		COUNTRY_TZ_MAP.put("KE", new String[] {
			"Africa/Nairobi",	// -0117+03649
		});
		COUNTRY_TZ_MAP.put("KG", new String[] {
			"Asia/Bishkek",	// +4254+07436
		});
		COUNTRY_TZ_MAP.put("KH", new String[] {
			"Asia/Phnom_Penh",	// +1133+10455
		});
		COUNTRY_TZ_MAP.put("KI", new String[] {
			"Pacific/Tarawa",	// +0125+17300, Gilbert Islands
			"Pacific/Enderbury",	// -0308-17105, Phoenix Islands
			"Pacific/Kiritimati",	// +0152-15720, Line Islands
		});
		COUNTRY_TZ_MAP.put("KM", new String[] {
			"Indian/Comoro",	// -1141+04316
		});
		COUNTRY_TZ_MAP.put("KN", new String[] {
			"America/St_Kitts",	// +1718-06243
		});
		COUNTRY_TZ_MAP.put("KP", new String[] {
			"Asia/Pyongyang",	// +3901+12545
		});
		COUNTRY_TZ_MAP.put("KR", new String[] {
			"Asia/Seoul",	// +3733+12658
		});
		COUNTRY_TZ_MAP.put("KW", new String[] {
			"Asia/Kuwait",	// +2920+04759
		});
		COUNTRY_TZ_MAP.put("KY", new String[] {
			"America/Cayman",	// +1918-08123
		});
		COUNTRY_TZ_MAP.put("KZ", new String[] {
			"Asia/Almaty",	// +4315+07657, Kazakhstan (most areas)
			"Asia/Qyzylorda",	// +4448+06528, Qyzylorda/Kyzylorda/Kzyl-Orda
			"Asia/Qostanay",	// +5312+06337, Qostanay/Kostanay/Kustanay
			"Asia/Aqtobe",	// +5017+05710, Aqtobe/Aktobe
			"Asia/Aqtau",	// +4431+05016, Mangghystau/Mankistau
			"Asia/Atyrau",	// +4707+05156, Atyrau/Atirau/Gur'yev
			"Asia/Oral",	// +5113+05121, West Kazakhstan
		});
		COUNTRY_TZ_MAP.put("LA", new String[] {
			"Asia/Vientiane",	// +1758+10236
		});
		COUNTRY_TZ_MAP.put("LB", new String[] {
			"Asia/Beirut",	// +3353+03530
		});
		COUNTRY_TZ_MAP.put("LC", new String[] {
			"America/St_Lucia",	// +1401-06100
		});
		COUNTRY_TZ_MAP.put("LI", new String[] {
			"Europe/Vaduz",	// +4709+00931
		});
		COUNTRY_TZ_MAP.put("LK", new String[] {
			"Asia/Colombo",	// +0656+07951
		});
		COUNTRY_TZ_MAP.put("LR", new String[] {
			"Africa/Monrovia",	// +0618-01047
		});
		COUNTRY_TZ_MAP.put("LS", new String[] {
			"Africa/Maseru",	// -2928+02730
		});
		COUNTRY_TZ_MAP.put("LT", new String[] {
			"Europe/Vilnius",	// +5441+02519
		});
		COUNTRY_TZ_MAP.put("LU", new String[] {
			"Europe/Luxembourg",	// +4936+00609
		});
		COUNTRY_TZ_MAP.put("LV", new String[] {
			"Europe/Riga",	// +5657+02406
		});
		COUNTRY_TZ_MAP.put("LY", new String[] {
			"Africa/Tripoli",	// +3254+01311
		});
		COUNTRY_TZ_MAP.put("MA", new String[] {
			"Africa/Casablanca",	// +3339-00735
		});
		COUNTRY_TZ_MAP.put("MC", new String[] {
			"Europe/Monaco",	// +4342+00723
		});
		COUNTRY_TZ_MAP.put("MD", new String[] {
			"Europe/Chisinau",	// +4700+02850
		});
		COUNTRY_TZ_MAP.put("ME", new String[] {
			"Europe/Podgorica",	// +4226+01916
		});
		COUNTRY_TZ_MAP.put("MF", new String[] {
			"America/Marigot",	// +1804-06305
		});
		COUNTRY_TZ_MAP.put("MG", new String[] {
			"Indian/Antananarivo",	// -1855+04731
		});
		COUNTRY_TZ_MAP.put("MH", new String[] {
			"Pacific/Majuro",	// +0709+17112, Marshall Islands (most areas)
			"Pacific/Kwajalein",	// +0905+16720, Kwajalein
		});
		COUNTRY_TZ_MAP.put("MK", new String[] {
			"Europe/Skopje",	// +4159+02126
		});
		COUNTRY_TZ_MAP.put("ML", new String[] {
			"Africa/Bamako",	// +1239-00800
		});
		COUNTRY_TZ_MAP.put("MM", new String[] {
			"Asia/Yangon",	// +1647+09610
		});
		COUNTRY_TZ_MAP.put("MN", new String[] {
			"Asia/Ulaanbaatar",	// +4755+10653, Mongolia (most areas)
			"Asia/Hovd",	// +4801+09139, Bayan-Olgiy, Govi-Altai, Hovd, Uvs, Zavkhan
			"Asia/Choibalsan",	// +4804+11430, Dornod, Sukhbaatar
		});
		COUNTRY_TZ_MAP.put("MO", new String[] {
			"Asia/Macau",	// +221150+1133230
		});
		COUNTRY_TZ_MAP.put("MP", new String[] {
			"Pacific/Saipan",	// +1512+14545
		});
		COUNTRY_TZ_MAP.put("MQ", new String[] {
			"America/Martinique",	// +1436-06105
		});
		COUNTRY_TZ_MAP.put("MR", new String[] {
			"Africa/Nouakchott",	// +1806-01557
		});
		COUNTRY_TZ_MAP.put("MS", new String[] {
			"America/Montserrat",	// +1643-06213
		});
		COUNTRY_TZ_MAP.put("MT", new String[] {
			"Europe/Malta",	// +3554+01431
		});
		COUNTRY_TZ_MAP.put("MU", new String[] {
			"Indian/Mauritius",	// -2010+05730
		});
		COUNTRY_TZ_MAP.put("MV", new String[] {
			"Indian/Maldives",	// +0410+07330
		});
		COUNTRY_TZ_MAP.put("MW", new String[] {
			"Africa/Blantyre",	// -1547+03500
		});
		COUNTRY_TZ_MAP.put("MX", new String[] {
			"America/Mexico_City",	// +1924-09909, Central Time
			"America/Cancun",	// +2105-08646, Eastern Standard Time - Quintana Roo
			"America/Merida",	// +2058-08937, Central Time - Campeche, Yucatan
			"America/Monterrey",	// +2540-10019, Central Time - Durango; Coahuila, Nuevo Leon, Tamaulipas (most areas)
			"America/Matamoros",	// +2550-09730, Central Time US - Coahuila, Nuevo Leon, Tamaulipas (US border)
			"America/Mazatlan",	// +2313-10625, Mountain Time - Baja California Sur, Nayarit, Sinaloa
			"America/Chihuahua",	// +2838-10605, Mountain Time - Chihuahua (most areas)
			"America/Ojinaga",	// +2934-10425, Mountain Time US - Chihuahua (US border)
			"America/Hermosillo",	// +2904-11058, Mountain Standard Time - Sonora
			"America/Tijuana",	// +3232-11701, Pacific Time US - Baja California
			"America/Bahia_Banderas",	// +2048-10515, Central Time - Bahia de Banderas
		});
		COUNTRY_TZ_MAP.put("MY", new String[] {
			"Asia/Kuala_Lumpur",	// +0310+10142, Malaysia (peninsula)
			"Asia/Kuching",	// +0133+11020, Sabah, Sarawak
		});
		COUNTRY_TZ_MAP.put("MZ", new String[] {
			"Africa/Maputo",	// -2558+03235
		});
		COUNTRY_TZ_MAP.put("NA", new String[] {
			"Africa/Windhoek",	// -2234+01706
		});
		COUNTRY_TZ_MAP.put("NC", new String[] {
			"Pacific/Noumea",	// -2216+16627
		});
		COUNTRY_TZ_MAP.put("NE", new String[] {
			"Africa/Niamey",	// +1331+00207
		});
		COUNTRY_TZ_MAP.put("NF", new String[] {
			"Pacific/Norfolk",	// -2903+16758
		});
		COUNTRY_TZ_MAP.put("NG", new String[] {
			"Africa/Lagos",	// +0627+00324
		});
		COUNTRY_TZ_MAP.put("NI", new String[] {
			"America/Managua",	// +1209-08617
		});
		COUNTRY_TZ_MAP.put("NL", new String[] {
			"Europe/Amsterdam",	// +5222+00454
		});
		COUNTRY_TZ_MAP.put("NO", new String[] {
			"Europe/Oslo",	// +5955+01045
		});
		COUNTRY_TZ_MAP.put("NP", new String[] {
			"Asia/Kathmandu",	// +2743+08519
		});
		COUNTRY_TZ_MAP.put("NR", new String[] {
			"Pacific/Nauru",	// -0031+16655
		});
		COUNTRY_TZ_MAP.put("NU", new String[] {
			"Pacific/Niue",	// -1901-16955
		});
		COUNTRY_TZ_MAP.put("NZ", new String[] {
			"Pacific/Auckland",	// -3652+17446, New Zealand (most areas)
			"Pacific/Chatham",	// -4357-17633, Chatham Islands
		});
		COUNTRY_TZ_MAP.put("OM", new String[] {
			"Asia/Muscat",	// +2336+05835
		});
		COUNTRY_TZ_MAP.put("PA", new String[] {
			"America/Panama",	// +0858-07932
		});
		COUNTRY_TZ_MAP.put("PE", new String[] {
			"America/Lima",	// -1203-07703
		});
		COUNTRY_TZ_MAP.put("PF", new String[] {
			"Pacific/Tahiti",	// -1732-14934, Society Islands
			"Pacific/Marquesas",	// -0900-13930, Marquesas Islands
			"Pacific/Gambier",	// -2308-13457, Gambier Islands
		});
		COUNTRY_TZ_MAP.put("PG", new String[] {
			"Pacific/Port_Moresby",	// -0930+14710, Papua New Guinea (most areas)
			"Pacific/Bougainville",	// -0613+15534, Bougainville
		});
		COUNTRY_TZ_MAP.put("PH", new String[] {
			"Asia/Manila",	// +1435+12100
		});
		COUNTRY_TZ_MAP.put("PK", new String[] {
			"Asia/Karachi",	// +2452+06703
		});
		COUNTRY_TZ_MAP.put("PL", new String[] {
			"Europe/Warsaw",	// +5215+02100
		});
		COUNTRY_TZ_MAP.put("PM", new String[] {
			"America/Miquelon",	// +4703-05620
		});
		COUNTRY_TZ_MAP.put("PN", new String[] {
			"Pacific/Pitcairn",	// -2504-13005
		});
		COUNTRY_TZ_MAP.put("PR", new String[] {
			"America/Puerto_Rico",	// +182806-0660622
		});
		COUNTRY_TZ_MAP.put("PS", new String[] {
			"Asia/Gaza",	// +3130+03428, Gaza Strip
			"Asia/Hebron",	// +313200+0350542, West Bank
		});
		COUNTRY_TZ_MAP.put("PT", new String[] {
			"Europe/Lisbon",	// +3843-00908, Portugal (mainland)
			"Atlantic/Madeira",	// +3238-01654, Madeira Islands
			"Atlantic/Azores",	// +3744-02540, Azores
		});
		COUNTRY_TZ_MAP.put("PW", new String[] {
			"Pacific/Palau",	// +0720+13429
		});
		COUNTRY_TZ_MAP.put("PY", new String[] {
			"America/Asuncion",	// -2516-05740
		});
		COUNTRY_TZ_MAP.put("QA", new String[] {
			"Asia/Qatar",	// +2517+05132
		});
		COUNTRY_TZ_MAP.put("RE", new String[] {
			"Indian/Reunion",	// -2052+05528
		});
		COUNTRY_TZ_MAP.put("RO", new String[] {
			"Europe/Bucharest",	// +4426+02606
		});
		COUNTRY_TZ_MAP.put("RS", new String[] {
			"Europe/Belgrade",	// +4450+02030
		});
		COUNTRY_TZ_MAP.put("RU", new String[] {
			"Europe/Moscow",	// +554521+0373704, MSK+00 - Moscow area  (** Change from original order **)
			"Europe/Kaliningrad",	// +5443+02030, MSK-01 - Kaliningrad
			"Europe/Kirov",	// +5836+04939, MSK+00 - Kirov
			"Europe/Astrakhan",	// +4621+04803, MSK+01 - Astrakhan
			"Europe/Volgograd",	// +4844+04425, MSK+01 - Volgograd
			"Europe/Saratov",	// +5134+04602, MSK+01 - Saratov
			"Europe/Ulyanovsk",	// +5420+04824, MSK+01 - Ulyanovsk
			"Europe/Samara",	// +5312+05009, MSK+01 - Samara, Udmurtia
			"Asia/Yekaterinburg",	// +5651+06036, MSK+02 - Urals
			"Asia/Omsk",	// +5500+07324, MSK+03 - Omsk
			"Asia/Novosibirsk",	// +5502+08255, MSK+04 - Novosibirsk
			"Asia/Barnaul",	// +5322+08345, MSK+04 - Altai
			"Asia/Tomsk",	// +5630+08458, MSK+04 - Tomsk
			"Asia/Novokuznetsk",	// +5345+08707, MSK+04 - Kemerovo
			"Asia/Krasnoyarsk",	// +5601+09250, MSK+04 - Krasnoyarsk area
			"Asia/Irkutsk",	// +5216+10420, MSK+05 - Irkutsk, Buryatia
			"Asia/Chita",	// +5203+11328, MSK+06 - Zabaykalsky
			"Asia/Yakutsk",	// +6200+12940, MSK+06 - Lena River
			"Asia/Khandyga",	// +623923+1353314, MSK+06 - Tomponsky, Ust-Maysky
			"Asia/Vladivostok",	// +4310+13156, MSK+07 - Amur River
			"Asia/Ust-Nera",	// +643337+1431336, MSK+07 - Oymyakonsky
			"Asia/Magadan",	// +5934+15048, MSK+08 - Magadan
			"Asia/Sakhalin",	// +4658+14242, MSK+08 - Sakhalin Island
			"Asia/Srednekolymsk",	// +6728+15343, MSK+08 - Sakha (E); North Kuril Is
			"Asia/Kamchatka",	// +5301+15839, MSK+09 - Kamchatka
			"Asia/Anadyr",	// +6445+17729, MSK+09 - Bering Sea
		});
		COUNTRY_TZ_MAP.put("UA", new String[] {
			"Europe/Kiev",	// +5026+03031, Ukraine (most areas)  (** Change from original order **)
			"Europe/Simferopol",	// +4457+03406, MSK+00 - Crimea
			"Europe/Uzhgorod",	// +4837+02218, Ruthenia
			"Europe/Zaporozhye",	// +4750+03510, Zaporozh'ye/Zaporizhia; Lugansk/Luhansk (east)
		});
		COUNTRY_TZ_MAP.put("RW", new String[] {
			"Africa/Kigali",	// -0157+03004
		});
		COUNTRY_TZ_MAP.put("SA", new String[] {
			"Asia/Riyadh",	// +2438+04643
		});
		COUNTRY_TZ_MAP.put("SB", new String[] {
			"Pacific/Guadalcanal",	// -0932+16012
		});
		COUNTRY_TZ_MAP.put("SC", new String[] {
			"Indian/Mahe",	// -0440+05528
		});
		COUNTRY_TZ_MAP.put("SD", new String[] {
			"Africa/Khartoum",	// +1536+03232
		});
		COUNTRY_TZ_MAP.put("SE", new String[] {
			"Europe/Stockholm",	// +5920+01803
		});
		COUNTRY_TZ_MAP.put("SG", new String[] {
			"Asia/Singapore",	// +0117+10351
		});
		COUNTRY_TZ_MAP.put("SH", new String[] {
			"Atlantic/St_Helena",	// -1555-00542
		});
		COUNTRY_TZ_MAP.put("SI", new String[] {
			"Europe/Ljubljana",	// +4603+01431
		});
		COUNTRY_TZ_MAP.put("SJ", new String[] {
			"Arctic/Longyearbyen",	// +7800+01600
		});
		COUNTRY_TZ_MAP.put("SK", new String[] {
			"Europe/Bratislava",	// +4809+01707
		});
		COUNTRY_TZ_MAP.put("SL", new String[] {
			"Africa/Freetown",	// +0830-01315
		});
		COUNTRY_TZ_MAP.put("SM", new String[] {
			"Europe/San_Marino",	// +4355+01228
		});
		COUNTRY_TZ_MAP.put("SN", new String[] {
			"Africa/Dakar",	// +1440-01726
		});
		COUNTRY_TZ_MAP.put("SO", new String[] {
			"Africa/Mogadishu",	// +0204+04522
		});
		COUNTRY_TZ_MAP.put("SR", new String[] {
			"America/Paramaribo",	// +0550-05510
		});
		COUNTRY_TZ_MAP.put("SS", new String[] {
			"Africa/Juba",	// +0451+03137
		});
		COUNTRY_TZ_MAP.put("ST", new String[] {
			"Africa/Sao_Tome",	// +0020+00644
		});
		COUNTRY_TZ_MAP.put("SV", new String[] {
			"America/El_Salvador",	// +1342-08912
		});
		COUNTRY_TZ_MAP.put("SX", new String[] {
			"America/Lower_Princes",	// +180305-0630250
		});
		COUNTRY_TZ_MAP.put("SY", new String[] {
			"Asia/Damascus",	// +3330+03618
		});
		COUNTRY_TZ_MAP.put("SZ", new String[] {
			"Africa/Mbabane",	// -2618+03106
		});
		COUNTRY_TZ_MAP.put("TC", new String[] {
			"America/Grand_Turk",	// +2128-07108
		});
		COUNTRY_TZ_MAP.put("TD", new String[] {
			"Africa/Ndjamena",	// +1207+01503
		});
		COUNTRY_TZ_MAP.put("TF", new String[] {
			"Indian/Kerguelen",	// -492110+0701303
		});
		COUNTRY_TZ_MAP.put("TG", new String[] {
			"Africa/Lome",	// +0608+00113
		});
		COUNTRY_TZ_MAP.put("TH", new String[] {
			"Asia/Bangkok",	// +1345+10031
		});
		COUNTRY_TZ_MAP.put("TJ", new String[] {
			"Asia/Dushanbe",	// +3835+06848
		});
		COUNTRY_TZ_MAP.put("TK", new String[] {
			"Pacific/Fakaofo",	// -0922-17114
		});
		COUNTRY_TZ_MAP.put("TL", new String[] {
			"Asia/Dili",	// -0833+12535
		});
		COUNTRY_TZ_MAP.put("TM", new String[] {
			"Asia/Ashgabat",	// +3757+05823
		});
		COUNTRY_TZ_MAP.put("TN", new String[] {
			"Africa/Tunis",	// +3648+01011
		});
		COUNTRY_TZ_MAP.put("TO", new String[] {
			"Pacific/Tongatapu",	// -2110-17510
		});
		COUNTRY_TZ_MAP.put("TR", new String[] {
			"Europe/Istanbul",	// +4101+02858
		});
		COUNTRY_TZ_MAP.put("TT", new String[] {
			"America/Port_of_Spain",	// +1039-06131
		});
		COUNTRY_TZ_MAP.put("TV", new String[] {
			"Pacific/Funafuti",	// -0831+17913
		});
		COUNTRY_TZ_MAP.put("TW", new String[] {
			"Asia/Taipei",	// +2503+12130
		});
		COUNTRY_TZ_MAP.put("TZ", new String[] {
			"Africa/Dar_es_Salaam",	// -0648+03917
		});
		COUNTRY_TZ_MAP.put("UG", new String[] {
			"Africa/Kampala",	// +0019+03225
		});
		COUNTRY_TZ_MAP.put("UM", new String[] {
			"Pacific/Midway",	// +2813-17722, Midway Islands
			"Pacific/Wake",	// +1917+16637, Wake Island
		});
		COUNTRY_TZ_MAP.put("US", new String[] {
			"America/New_York",	// +404251-0740023, Eastern (most areas)
			"America/Detroit",	// +421953-0830245, Eastern - MI (most areas)
			"America/Kentucky/Louisville",	// +381515-0854534, Eastern - KY (Louisville area)
			"America/Kentucky/Monticello",	// +364947-0845057, Eastern - KY (Wayne)
			"America/Indiana/Indianapolis",	// +394606-0860929, Eastern - IN (most areas)
			"America/Indiana/Vincennes",	// +384038-0873143, Eastern - IN (Da, Du, K, Mn)
			"America/Indiana/Winamac",	// +410305-0863611, Eastern - IN (Pulaski)
			"America/Indiana/Marengo",	// +382232-0862041, Eastern - IN (Crawford)
			"America/Indiana/Petersburg",	// +382931-0871643, Eastern - IN (Pike)
			"America/Indiana/Vevay",	// +384452-0850402, Eastern - IN (Switzerland)
			"America/Chicago",	// +415100-0873900, Central (most areas)
			"America/Indiana/Tell_City",	// +375711-0864541, Central - IN (Perry)
			"America/Indiana/Knox",	// +411745-0863730, Central - IN (Starke)
			"America/Menominee",	// +450628-0873651, Central - MI (Wisconsin border)
			"America/North_Dakota/Center",	// +470659-1011757, Central - ND (Oliver)
			"America/North_Dakota/New_Salem",	// +465042-1012439, Central - ND (Morton rural)
			"America/North_Dakota/Beulah",	// +471551-1014640, Central - ND (Mercer)
			"America/Denver",	// +394421-1045903, Mountain (most areas)
			"America/Boise",	// +433649-1161209, Mountain - ID (south); OR (east)
			"America/Phoenix",	// +332654-1120424, MST - Arizona (except Navajo)
			"America/Los_Angeles",	// +340308-1181434, Pacific
			"America/Anchorage",	// +611305-1495401, Alaska (most areas)
			"America/Juneau",	// +581807-1342511, Alaska - Juneau area
			"America/Sitka",	// +571035-1351807, Alaska - Sitka area
			"America/Metlakatla",	// +550737-1313435, Alaska - Annette Island
			"America/Yakutat",	// +593249-1394338, Alaska - Yakutat
			"America/Nome",	// +643004-1652423, Alaska (west)
			"America/Adak",	// +515248-1763929, Aleutian Islands
			"Pacific/Honolulu",	// +211825-1575130, Hawaii
		});
		COUNTRY_TZ_MAP.put("UY", new String[] {
			"America/Montevideo",	// -345433-0561245
		});
		COUNTRY_TZ_MAP.put("UZ", new String[] {
			"Asia/Samarkand",	// +3940+06648, Uzbekistan (west)
			"Asia/Tashkent",	// +4120+06918, Uzbekistan (east)
		});
		COUNTRY_TZ_MAP.put("VA", new String[] {
			"Europe/Vatican",	// +415408+0122711
		});
		COUNTRY_TZ_MAP.put("VC", new String[] {
			"America/St_Vincent",	// +1309-06114
		});
		COUNTRY_TZ_MAP.put("VE", new String[] {
			"America/Caracas",	// +1030-06656
		});
		COUNTRY_TZ_MAP.put("VG", new String[] {
			"America/Tortola",	// +1827-06437
		});
		COUNTRY_TZ_MAP.put("VI", new String[] {
			"America/St_Thomas",	// +1821-06456
		});
		COUNTRY_TZ_MAP.put("VN", new String[] {
			"Asia/Ho_Chi_Minh",	// +1045+10640
		});
		COUNTRY_TZ_MAP.put("VU", new String[] {
			"Pacific/Efate",	// -1740+16825
		});
		COUNTRY_TZ_MAP.put("WF", new String[] {
			"Pacific/Wallis",	// -1318-17610
		});
		COUNTRY_TZ_MAP.put("WS", new String[] {
			"Pacific/Apia",	// -1350-17144
		});
		COUNTRY_TZ_MAP.put("YE", new String[] {
			"Asia/Aden",	// +1245+04512
		});
		COUNTRY_TZ_MAP.put("YT", new String[] {
			"Indian/Mayotte",	// -1247+04514
		});
		COUNTRY_TZ_MAP.put("ZA", new String[] {
			"Africa/Johannesburg",	// -2615+02800
		});
		COUNTRY_TZ_MAP.put("ZM", new String[] {
			"Africa/Lusaka",	// -1525+02817
		});
		COUNTRY_TZ_MAP.put("ZW", new String[] {
			"Africa/Harare",	// -1750+03103
		});
		
		
		// cf. Locale#getAvailableLocales()
		LANGUAGE_DEFAULT_COUNTRY_MAP = new HashMap<>(43);
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("ar", "EG");  // Arabic - Egypt
		// "AE"  // United Arab Emirates
		// "BH"  // Bahrain
		// "DZ"  // Algeria
		// "IQ"  // Iraq
		// "JO"  // Jordan
		// "KW"  // Kuwait
		// "LB"  // Lebanon
		// "LY"  // Libya
		// "MA"  // Morocco
		// "QA"  // Qatar
		// "OM"  // Oman
		// "SA"  // Saudi Arabia
		// "SD"  // Sudan
		// "SY"  // Syria
		// "TN"  // Tunisia
		// "YE"  // Yemen
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("be", "BY");  // Belarusian - Belarus
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("bg", "BG");  // Bulgarian - Bulgaria
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("ca", "ES");  // Catalan - Spain
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("cs", "CZ");  // Czech - Czech Republic
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("da", "DK");  // Danish - Denmark
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("de", "DE");  // German - Germany
		// "AT"  // Austria
		// "CH"  // Switzerland
		// "GR"  // Greece
		// "LU"  // Luxembourg
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("el", "CY");  // Greek - Cyprus
		// "GR"  // Greece
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("en", "US");  // English - United States
		// "AU"  // Australia
		// "CA"  // Canada
		// "GB"  // United Kingdom
		// "IE"  // Ireland
		// "IN"  // India
		// "MT"  // Malta
		// "NZ"  // New Zealand
		// "PH"  // Philippines
		// "SG"  // Singapore
		// "ZA"  // South Africa
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("es", "ES");  // Spanish - Spain
		// "AR"  // Argentina
		// "BO"  // Bolivia
		// "CL"  // Chile
		// "CO"  // Colombia
		// "CR"  // Costa Rica
		// "CU"  // Cuba
		// "DO"  // Dominican Republic
		// "EC"  // Ecuador
		// "GT"  // Guatemala
		// "HN"  // Honduras
		// "MX"  // Mexico
		// "NI"  // Nicaragua
		// "PA"  // Panama
		// "PE"  // Peru
		// "PR"  // Puerto Rico
		// "PY"  // Paraguay
		// "SV"  // El Salvador
		// "US"  // United States
		// "UY"  // Uruguay
		// "VE"  // Venezuela
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("et", "EE");  // Estonian - Estonia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("fi", "FI");  // Finnish - Finland
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("fr", "FR");  // French - France
		// "BE"  // Belgium
		// "CA"  // Canada
		// "CH"  // Switzerland
		// "LU"  // Luxembourg
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("ga", "IE");  // Irish - Ireland
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("hi", "IN");  // Hindi - India
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("hr", "HR");  // Croatian - Croatia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("hu", "HU");  // Hungarian - Hungary
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("in", "ID");  // Indonesian - Indonesia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("is", "IS");  // Icelandic - Iceland
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("it", "IT");  // Italian - Italy
		// "CH"  // Switzerland
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("iw", "IL");  // Hebrew - Israel
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("ja", "JP");  // Japanese - Japan
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("ko", "KR");  // Korean - South Korea
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("lt", "LT");  // Lithuanian - Lithuania
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("lv", "LV");  // Latvian - Latvia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("mk", "MK");  // Macedonian - Macedonia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("ms", "MY");  // Malay - Malaysia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("mt", "MT");  // Maltese - Malta
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("nl", "NL");  // Dutch - Netherlands
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("no", "NO");  // Norwegian - Norway
		// "BE"  // Belgium
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("pl", "PL");  // Polish - Poland
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("pt", "PT");  // Portuguese - Portugal
		// "BR"  // Brazil
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("ro", "RO");  // Romanian - Romania
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("ru", "RU");  // Russian - Russia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("sk", "SK");  // Slovak - Slovakia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("sl", "SI");  // Slovenian - Slovenia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("sq", "AL");  // Albanian - Albania
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("sr", "BA");  // Serbian - Bosnia and Herzegovina
		// "CS"  // Serbia and Montenegro
		// "ME"  // Montenegro
		// "RS"  // Serbia
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("sv", "SE");  // Swedish - Sweden
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("th", "TH");  // Thai - Thailand
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("tr", "TR");  // Turkish - Turkey
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("uk", "UA");  // Ukrainian - Ukraine
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("vi", "VN");  // Vietnamese - Vietnam
		LANGUAGE_DEFAULT_COUNTRY_MAP.put("zh", "CN");  // Chinese - China
		// "HK"  // Hong Kong
		// "SG"  // Singapore
		// "TW"  // Taiwan
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
		String timeZoneID = localeToTimeZoneID(locale);
		
		if (timeZoneID == null) {
			return null;
		}
		
		try {
			ZoneId zoneId = ZoneId.of(timeZoneID);
			return TimeZone.getTimeZone(zoneId);
		} catch (ZoneRulesException e) {
			return null;
		}
	}
	
	public static String localeToTimeZoneID(Locale locale) {
		if (locale == null) {
			return null;
		}
		
		String countryCode = locale.getCountry();
		if (countryCode.isEmpty()) {
			String languageCode = locale.getLanguage();
			countryCode = LANGUAGE_DEFAULT_COUNTRY_MAP.get(languageCode);
		}
		
		return countryToTimeZoneID(countryCode);
	}

	public static String countryToTimeZoneID(String countryCode) {
		if (countryCode == null || countryCode.isEmpty()) {
			return null;
		}
		
		String[] tzIDs = COUNTRY_TZ_MAP.get(countryCode);
		if (tzIDs == null) {
			return null;
		}
		
		return tzIDs[0];
	}
	
	public static TimeZone[] localeToTimeZones(Locale locale) {
		String[] timeZoneIDs = localeToTimeZoneIDs(locale);
		
		if (timeZoneIDs == null) {
			return null;
		}
		
		TimeZone[] timeZones = new TimeZone[timeZoneIDs.length];
		for (int i = 0; i < timeZoneIDs.length; i++) {
			String tzID = timeZoneIDs[i];
			try {
				ZoneId zoneId = ZoneId.of(tzID);
				timeZones[i] = TimeZone.getTimeZone(zoneId);
			} catch (ZoneRulesException e) {
				return null;
			}
		}
		return timeZones;
	}
	
	public static String[] localeToTimeZoneIDs(Locale locale) {
		if (locale == null) {
			return null;
		}
		
		String countryCode = locale.getCountry();
		if (countryCode.isEmpty()) {
			String languageCode = locale.getLanguage();
			countryCode = LANGUAGE_DEFAULT_COUNTRY_MAP.get(languageCode);
		}
		
		return countryToTimeZoneIDs(countryCode);
	}

	public static String[] countryToTimeZoneIDs(String countryCode) {
		if (countryCode == null || countryCode.isEmpty()) {
			return null;
		}
		
		String[] tzIDs = COUNTRY_TZ_MAP.get(countryCode);
		if (tzIDs == null) {
			return null;
		}
		
		String[] newTzIDs = new String[tzIDs.length];
		System.arraycopy(tzIDs, 0, newTzIDs, 0, tzIDs.length);
		return newTzIDs;
	}


	/**
	 * Parse COUNTRY_TZ_MAP date/time string with multiple patterns.
	 * Locale is used Locale.US.
	 * TimeZone is used UTC.
	 * 
	 * @param str the date/time string to be parsed
	 * @param strict when true, parsing is strict
	 * @param parsePatterns date patterns
	 * @return COUNTRY_TZ_MAP date parsed from the string
	 */
	public static Date parseDate(String str, boolean strict, String... parsePatterns) {
		return parseDate(str, strict, null, null, parsePatterns);
	}
	
	/**
	 * Parse COUNTRY_TZ_MAP date/time string with multiple patterns.
	 * 
	 * @param str the date/time string to be parsed
	 * @param strict when true, parsing is strict
	 * @param timeZone the TimeZone of date/time. Locale.US if argument is null
	 * @param locale the Locale of date/time. UTC if argument is null
	 * @param parsePatterns date patterns
	 * @return COUNTRY_TZ_MAP date parsed from the string
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
	 * Parse COUNTRY_TZ_MAP date/time string with multiple patterns.
	 * 
	 * @param str the date/time string to be parsed
	 * @param calendar the Calendar of date/time. Calendar.getInstance() if argument is null
	 * @param locale the Locale of date/time. UTC if argument is null
	 * @param parsePatterns date patterns
	 * @return COUNTRY_TZ_MAP date parsed from the string
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
