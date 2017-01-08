/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.text.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mifmi.commons4j.matcher.AnythingMatcher;
import org.mifmi.commons4j.matcher.BooleanMatcher;
import org.mifmi.commons4j.matcher.ChronoMatcher;
import org.mifmi.commons4j.matcher.IMatcher;
import org.mifmi.commons4j.matcher.NumberMatcher;
import org.mifmi.commons4j.matcher.ObjectMatcher;
import org.mifmi.commons4j.matcher.QuantityMatchType;
import org.mifmi.commons4j.matcher.RegexMatcher;
import org.mifmi.commons4j.matcher.StringMatcher;
import org.mifmi.commons4j.text.token.StringToken;
import org.mifmi.commons4j.text.token.StringTokenParser;
import org.mifmi.commons4j.util.BooleanUtilz;
import org.mifmi.commons4j.util.StringUtilz;

public class NamedFormatter {
	private enum PatternType {
		Static,
		Var,
	}
	private enum VarOptionType {
		None,
		Format,
		Choice,
		Script,
	}
	
	private static class PatternPart {
		public PatternType type;
		public String value;
		public VarOptionType varOptionType;
		public String varOption;
		public List<ChoicePart> varOptionChoiceParts;

		public PatternPart(PatternType type, String value) {
			this(type, value, VarOptionType.None, null);
		}
		
		public PatternPart(PatternType type, String value, VarOptionType varOptionType, String varOption) {
			this.type = type;
			this.value = value;
			this.varOptionType = varOptionType;
			this.varOption = varOption;
			this.varOptionChoiceParts = null;
		}
		
		public PatternPart(PatternType type, String value, List<ChoicePart> varOptionChoicePart) {
			this.type = type;
			this.value = value;
			this.varOptionType = VarOptionType.Choice;
			this.varOption = null;
			this.varOptionChoiceParts = varOptionChoicePart;
		}
	}
	
	private static class ChoicePart {
		public IMatcher<?> matcher;
		public String value;
		
		public ChoicePart(IMatcher<?> matcher, String value) {
			this.matcher = matcher;
			this.value = value;
		}
	}
	

	private static final char ESCAPE_CHAR = '\\';
	private static final char[] ESCAPE_TARGET_CHARS = {'0', 'a', 'b', 'f', 'n', 'r', 't', 'v'};
	private static final char[] ESCAPED_CHARS = {'\0', '\u0007', '\b', '\f', '\n', '\r', '\t', '\u000B'};

	private static final String STRING_PREFIX = "'";
	private static final String STRING_SUFFIX = "'";
	private static final String DATETIME_PREFIX = "#";
	private static final String DATETIME_SUFFIX = "#";
	
	private static final String TOKEN_VAR_SEP_FORMAT = "%";
	private static final String TOKEN_VAR_SEP_CHOISE = "/";
	private static final String TOKEN_VAR_SEP_SCRIPT = ";";
	
	private static final String TOKEN_CHOISE_SEP = "/";
	private static final String TOKEN_CHOISE_PART_SEP = ":";
	private static final String TOKEN_CHOICE_OP_EQ = "=";
	private static final String TOKEN_CHOICE_OP_NEQ = "!=";
	private static final String TOKEN_CHOICE_OP_NEQ_SS1 = "!";
	private static final String TOKEN_CHOICE_OP_EQW = "=*";
	private static final String TOKEN_CHOICE_OP_EQW_SS1 = "*";
	private static final String TOKEN_CHOICE_OP_NEQW = "!=*";
	private static final String TOKEN_CHOICE_OP_NEQW_SS1 = "!*";
	private static final String TOKEN_CHOICE_OP_EQR = "=~";
	private static final String TOKEN_CHOICE_OP_EQR_SS1 = "~";
	private static final String TOKEN_CHOICE_OP_NEQR = "!=~";
	private static final String TOKEN_CHOICE_OP_NEQR_SS1 = "!~";
	private static final String TOKEN_CHOICE_OP_LTE = "<=";
	private static final String TOKEN_CHOICE_OP_GTE = ">=";
	private static final String TOKEN_CHOICE_OP_LT = "<";
	private static final String TOKEN_CHOICE_OP_GT = ">";
	private static final String TOKEN_CHOICE_OPERAND_DEFAULT = "default";
	private static final String TOKEN_CHOICE_OPERAND_NULL = "null";
	private static final String TOKEN_CHOICE_OPERAND_TRUE = "true";
	private static final String TOKEN_CHOICE_OPERAND_FALSE = "false";
	private static final String[][] TOKEN_CHOISE_BLOCKQUOTE_SETS = {
			{STRING_PREFIX, STRING_SUFFIX},
			{DATETIME_PREFIX, DATETIME_SUFFIX}
	};

	private static final Pattern DATETIME_VALUE_PATTERN = Pattern.compile("^[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}(?:\\.[0-9]{1,9})?[\\+\\-][0-9]{1,2}:[0-9]{1,2}$");
	private static final Pattern DATE_VALUE_PATTERN = Pattern.compile("^[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}$");
	private static final Pattern TIME_VALUE_PATTERN = Pattern.compile("^[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}(?:\\.[0-9]{1,9})?[\\+\\-][0-9]{1,2}:[0-9]{1,2}$");
	private static final Pattern NUMBER_VALUE_PATTERN = Pattern.compile("^[\\+\\-]?[0-9]+(?:\\.[0-9]+)?$");
	
	
	private String pattern;
	private String varPrefix;
	private String varSuffix;
	private TimeZone timeZone;
	private Locale locale;
	private List<PatternPart> parsedPattern;

	public NamedFormatter(String pattern) {
		this(pattern, "${", "}");
	}
	
	public NamedFormatter(String pattern, String varPrefix, String varSuffix) {
		this(pattern, varPrefix, varSuffix, ESCAPE_CHAR);
	}
	
	public NamedFormatter(String pattern, String varPrefix, String varSuffix, char escapeChar) {
		this.pattern = pattern;
		this.varPrefix = varPrefix;
		this.varSuffix = varSuffix;
		this.timeZone = TimeZone.getDefault();
		this.locale = Locale.getDefault();
		this.parsedPattern = Collections.unmodifiableList(parsePattern(this.pattern, this.varPrefix, this.varSuffix, escapeChar, this.locale, this.timeZone));
	}
	
	public NamedFormatter setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
		return this;
	}
	
	public NamedFormatter setLocale(Locale locale) {
		this.locale = locale;
		return this;
	}
	
	public String format(Map<String, Object> vars) {
		return format(varName -> {
			return vars.get(varName);
		});
	}
	
	public String format(Function<String, Object> varsMapper) {
		StringBuilder sb = new StringBuilder();
		for (PatternPart patternPart : this.parsedPattern) {
			switch (patternPart.type) {
			case Static:
				sb.append(patternPart.value);
				break;
			case Var:
				Object varVal = varsMapper.apply(patternPart.value);
				if (varVal != null) {
					if (varVal instanceof Function) {
						varVal = ((Function)varVal).apply(patternPart.value);
					}
				}
				String strVal;
				switch (patternPart.varOptionType) {
				case Format:
					String pattern = patternPart.varOption;
					if (varVal != null && pattern != null && !pattern.isEmpty()) {
						if (pattern.contains("%")) {
							// Format with java.util.Formatter
							if (varVal instanceof Date) {
								Calendar cal = Calendar.getInstance(this.timeZone, this.locale);
								cal.setTime((Date)varVal);
								varVal = cal;
							}
							try (Formatter formatter = new Formatter(sb, this.locale)) {
								int cnt = countChar(pattern, '%');
								formatter.format(this.locale, pattern, toArray(varVal, cnt));
							}
							strVal = null;
						} else {
							// Format with java.text.Format or java.time.format.DateTimeFormatter
							if (varVal instanceof TemporalAccessor || varVal instanceof Date) {
								DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern, this.locale);
								dateFormatter.withZone(this.timeZone.toZoneId());
								if (varVal instanceof TemporalAccessor) {
									strVal = dateFormatter.format((TemporalAccessor)varVal);
								} else {
									strVal = dateFormatter.format(
											ZonedDateTime.ofInstant(((Date)varVal).toInstant(), this.timeZone.toZoneId())
											);
								}
							} else if (varVal instanceof Number) {
								DecimalFormat decimalFormat = new DecimalFormat(pattern);
								strVal = decimalFormat.format((Number)varVal);
							} else {
								throw new IllegalStateException(varVal.toString());
							}
						}
					} else {
						strVal = stringnize(varVal);
					}
					break;
				case Choice:
					List<ChoicePart> choiceParts = patternPart.varOptionChoiceParts;
					boolean matched = false;
					strVal = null;
					for (ChoicePart choicePart : choiceParts) {
						Class<?> clazz = choicePart.matcher.getType();
						if (clazz == String.class) {
							String strVarVal = stringnize(varVal);
							matched = ((IMatcher<String>)choicePart.matcher).matches(strVarVal);
						} else if (clazz == Number.class) {
							String strVarVal = stringnize(varVal);
							Number numVarVal = new BigDecimal(strVarVal);
							matched = ((IMatcher<Number>)choicePart.matcher).matches(numVarVal);
						} else if (clazz == TemporalAccessor.class) {
							TemporalAccessor temporalVarVal = toTemporal(varVal, this.timeZone);
							matched = ((IMatcher<TemporalAccessor>)choicePart.matcher).matches(temporalVarVal);
						} else if (clazz == Boolean.class) {
							Boolean boolVarVal = BooleanUtilz.toBooleanObject(varVal);
							matched = ((IMatcher<Boolean>)choicePart.matcher).matches(boolVarVal);
						} else if (clazz == Object.class) {
							matched = ((IMatcher<Object>)choicePart.matcher).matches(varVal);
						} else {
							throw new IllegalStateException(clazz.getName());
						}
						
						if (matched) {
							strVal = choicePart.value;
							break;
						}
					}
					
					if (!matched) {
						strVal = null;
					}
					break;
				case Script:
					String script = patternPart.varOption;
					if (script != null && !script.isEmpty()) {
						ScriptEngineManager manager = new ScriptEngineManager();
						ScriptEngine engine = manager.getEngineByName("JavaScript");
						engine.put("$_", varVal);
						Object retVal;
						try {
							retVal = engine.eval(script);
						} catch (ScriptException e) {
							throw new IllegalArgumentException(e);
						}
						strVal = stringnize(retVal);
					} else {
						strVal = stringnize(varVal);
					}
					break;
				case None:
					strVal = stringnize(varVal);
					break;
				default:
					throw new IllegalStateException(patternPart.varOptionType.name());
				}
				if (strVal != null) {
					sb.append(strVal);
				}
				break;
			default:
				throw new IllegalStateException(patternPart.type.toString());
			}
		}
		
		return sb.toString();
	}

	private String stringnize(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}
	
	private static TemporalAccessor toTemporal(Object obj, TimeZone timeZone) {
		if (obj == null) {
			return null;
		}
		
		if (obj instanceof TemporalAccessor) {
			return (TemporalAccessor)obj;
		} else if (obj instanceof Long) {
			Long millis = (Long)obj;
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), timeZone.toZoneId());
		} else if (obj instanceof Date) {
			Date date = (Date)obj;
			return LocalDateTime.ofInstant(date.toInstant(), timeZone.toZoneId());
		} else if (obj instanceof GregorianCalendar) {
			GregorianCalendar calendar = (GregorianCalendar)obj;
			return calendar.toZonedDateTime();
		} else if (obj instanceof Calendar) {
			Calendar calendar = (Calendar)obj;
			return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
		}
		
		String str = obj.toString();
		return toTemporal(str);
	}
	private static TemporalAccessor toTemporal(String str) {
		if (str == null) {
			return null;
		}
		
		return parseTemporal(
				str,
				ZonedDateTime::parse, OffsetDateTime::parse, LocalDateTime::parse,
				LocalDate::parse, LocalTime::parse, OffsetTime::parse,
				YearMonth::parse, MonthDay::parse, Year::parse,
				Month::valueOf,
				DayOfWeek::valueOf
				);
	}
		
	
	private static TemporalAccessor parseTemporal(String str, Function<String, TemporalAccessor>... parsers) {
		RuntimeException exception = null;
		
		for (Function<String, TemporalAccessor> parser : parsers) {
			try {
				return parser.apply(str);
			} catch (RuntimeException e) {
				exception = e;
				continue;
			}
		}
		
		if (exception != null) {
			throw exception;
		}
		
		return null;
	}

	private static List<PatternPart> parsePattern(String pattern, String varPrefix, String varSuffix, char escapeChar, Locale locale, TimeZone timeZone) {
		List<PatternPart> parsedPattern = new ArrayList<>();
		
		if (pattern == null || pattern.isEmpty()) {
			return parsedPattern;
		}
		
		StringTokenParser parser = new StringTokenParser(pattern)
				.setEscape(escapeChar, ESCAPE_TARGET_CHARS, ESCAPED_CHARS);
		
		do {
			StringToken staticToken = parser.nextToken(varPrefix);
			String staticVal = staticToken.getToken();
			if (!staticVal.isEmpty()) {
				parsedPattern.add(new PatternPart(PatternType.Static, staticVal));
			}
			
			if (staticToken.getTerminator().equals(varPrefix)) {
				parser.setEscape(escapeChar, ESCAPE_TARGET_CHARS, ESCAPED_CHARS);
				StringToken varToken = parser.nextToken(TOKEN_VAR_SEP_FORMAT, TOKEN_VAR_SEP_CHOISE, TOKEN_VAR_SEP_SCRIPT, varSuffix);
				String varName = varToken.getToken();
				String varSep = varToken.getTerminator();
				
				if (varSep.equals(TOKEN_VAR_SEP_FORMAT)) {
					StringToken formatToken = parser.nextToken(varSuffix);
					String format = formatToken.getToken();
					parsedPattern.add(new PatternPart(PatternType.Var, varName, VarOptionType.Format, format));
				} else if (varSep.equals(TOKEN_VAR_SEP_CHOISE)) {
					List<ChoicePart> choiceList = new ArrayList<>();
					
					parser.setEscape(escapeChar);
					do {
						parser.setBlockquote(TOKEN_CHOISE_BLOCKQUOTE_SETS, false, false);
						StringToken choiceConditionToken = parser.nextToken(TOKEN_CHOISE_PART_SEP);
						
						parser.setBlockquote(null, false, false);
						StringToken choiceValueToken = parser.nextToken(TOKEN_CHOISE_SEP, varSuffix);

						String choiceCondition = choiceConditionToken.getToken();
						String choiceValue = choiceValueToken.getToken();

						QuantityMatchType matchType;
						String choiceOperand;
						boolean wild = false;
						boolean regex = false;
						boolean not = false;
						if (choiceCondition.isEmpty()) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = TOKEN_CHOICE_OPERAND_DEFAULT;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_GTE)) {
							matchType = QuantityMatchType.GreaterEquals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_GTE.length());
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_GT)) {
							matchType = QuantityMatchType.GreaterThan;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_GT.length());
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_LTE)) {
							matchType = QuantityMatchType.LessEquals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_LTE.length());
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_LT)) {
							matchType = QuantityMatchType.LessThan;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_LT.length());
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_EQW)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_EQW.length());
							wild = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_EQW_SS1)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_EQW_SS1.length());
							wild = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_NEQW)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_NEQW.length());
							wild = true;
							not = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_NEQW_SS1)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_NEQW_SS1.length());
							wild = true;
							not = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_EQR)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_EQR.length());
							regex = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_EQR_SS1)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_EQR_SS1.length());
							regex = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_NEQR)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_NEQR.length());
							regex = true;
							not = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_NEQR_SS1)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_NEQR_SS1.length());
							regex = true;
							not = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_NEQ)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_NEQ.length());
							not = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_NEQ_SS1)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_NEQ_SS1.length());
							not = true;
						} else if (choiceCondition.startsWith(TOKEN_CHOICE_OP_EQ)) {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition.substring(TOKEN_CHOICE_OP_EQ.length());
						} else {
							matchType = QuantityMatchType.Equals;
							choiceOperand = choiceCondition;
						}
						
						IMatcher<?> matcher;
						if (choiceOperand.equals(TOKEN_CHOICE_OPERAND_DEFAULT)) {
							// Default
							if (matchType != QuantityMatchType.Equals) {
								throw new IllegalArgumentException(matchType.name());
							}
							matcher = new AnythingMatcher<Object>();
						} else if (choiceOperand.equals(TOKEN_CHOICE_OPERAND_NULL)) {
							// null
							if (matchType != QuantityMatchType.Equals) {
								throw new IllegalArgumentException(matchType.name());
							}
							matcher = new ObjectMatcher<Object>(null);
						} else if (choiceOperand.equals(TOKEN_CHOICE_OPERAND_TRUE) || choiceOperand.equals(TOKEN_CHOICE_OPERAND_FALSE)) {
							// Boolean
							if (matchType != QuantityMatchType.Equals) {
								throw new IllegalArgumentException(matchType.name());
							}
							Boolean val = Boolean.parseBoolean(choiceOperand);
							matcher = new BooleanMatcher(val);
						} else if (StringUtilz.surroundsWith(choiceOperand, STRING_PREFIX, STRING_SUFFIX)) {
							// String
							if (matchType != QuantityMatchType.Equals) {
								throw new IllegalArgumentException(matchType.name());
							}
							
							String str;
							StringToken token = new StringTokenParser(choiceOperand)
									.setEscape(ESCAPE_CHAR, ESCAPE_TARGET_CHARS, ESCAPED_CHARS)
									.setTrim(false)
									.seek(1)
									.nextToken(STRING_SUFFIX);
							if (token.isEos()) {
								str = token.getToken();
							} else {
								str = StringUtilz.unescape(choiceOperand, ESCAPE_CHAR, ESCAPE_TARGET_CHARS, ESCAPED_CHARS);
							}
							
							if (wild) {
								matcher = StringMatcher.compileWildcard(str, false, locale);
							} else if (regex) {
								matcher = new RegexMatcher(Pattern.compile(str));
							} else {
								matcher = new StringMatcher(str);
							}
						} else if (StringUtilz.surroundsWith(choiceOperand, DATETIME_PREFIX, DATETIME_SUFFIX)) {
							// DateTime
							String str;
							StringToken token = new StringTokenParser(choiceOperand)
									.setEscape(ESCAPE_CHAR, ESCAPE_TARGET_CHARS, ESCAPED_CHARS)
									.setTrim(false)
									.seek(1)
									.nextToken(DATETIME_SUFFIX);
							if (token.isEos()) {
								str = token.getToken();
							} else {
								str = StringUtilz.unescape(choiceOperand, ESCAPE_CHAR, ESCAPE_TARGET_CHARS, ESCAPED_CHARS);
							}
							
							TemporalAccessor date = toTemporal(str);
							matcher = new ChronoMatcher(date, matchType, true);
						} else if (NUMBER_VALUE_PATTERN.matcher(choiceOperand).matches()) {
							// Number
							BigDecimal number = new BigDecimal(choiceOperand);
							matcher = new NumberMatcher(number, matchType);
						} else if (DATETIME_VALUE_PATTERN.matcher(choiceOperand).matches()
								|| DATE_VALUE_PATTERN.matcher(choiceOperand).matches()
								|| TIME_VALUE_PATTERN.matcher(choiceOperand).matches()
								) {
							// DateTime
							TemporalAccessor date = toTemporal(choiceOperand);
							matcher = new ChronoMatcher(date, matchType, true);
						} else {
							// String
							String str = StringUtilz.unescape(choiceOperand, ESCAPE_CHAR, ESCAPE_TARGET_CHARS, ESCAPED_CHARS);
							
							if (wild) {
								matcher = StringMatcher.compileWildcard(str, false, locale);
							} else if (regex) {
								matcher = new RegexMatcher(Pattern.compile(str));
							} else {
								matcher = new StringMatcher(str);
							}
						}
						
						if (not) {
							matcher = matcher.not();
						}
						
						choiceList.add(new ChoicePart(matcher, choiceValue));
						
						if (choiceValueToken.getTerminator().equals(varSuffix)) {
							parsedPattern.add(new PatternPart(PatternType.Var, varName, choiceList));
							break;
						}
					} while (true);
				} else if (varSep.equals(TOKEN_VAR_SEP_SCRIPT)) {
					
				} else if (varSep.equals(varSuffix)) {
					parsedPattern.add(new PatternPart(PatternType.Var, varName));
				} else {
					throw new IllegalArgumentException(varSep);
				}
			}
		} while(!parser.getLastToken().isEos());
		
		return parsedPattern;
	}
	
	private static int countChar(CharSequence str, char ch) {
		if (str == null) {
			return 0;
		}
		
		int count = 0;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (str.charAt(i) == ch) {
				count++;
			}
		}
		return count;
	}
	
	private static Object[] toArray(Object value, int arraySize) {
		Object[] array = new Object[arraySize];
		for (int i = 0; i < arraySize; i++) {
			array[i] = value;
		}
		return array;
	}
}
