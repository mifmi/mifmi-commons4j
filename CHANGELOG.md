
## 0.3.1 (2022-01-09)
Add:

  - Add JitPack setting

## 0.3.0 (2022-01-09)
Changed:

  - Migrate Servlet from Java EE to Jakarta EE

## 0.2.10 (2021-05-29)
Add:

  - Add large number support for NumerUtilz English number methods

## 0.2.9 (2021-02-13)
Add:

  - Add ExceptionUtilz class
  - Add DateUtilz#toInstant method

Fixed:

  - Fix bug of HTMLUtilz#escapeHTML5Fully

## 0.2.8 (2021-02-01)

Fixed:

  - Fix bug of NumberUtilz#parseEnNumShortScaleUnsignedPart

## 0.2.7 (2021-01-24)

Add:
  - Add AnyLocaleResourceBundleControl
  - Add #fileLastModified to JSP TagLib
  - Add #fileExists to JSP TagLib

Fixed:

  - Fix bug of NumberUtilz#toEnNumShortScale

## 0.2.6 (2020-07-18)

Add:
  - Add Unicode escape sequence u{XXXXXX} pattern to unescape method

Changed:

  - Change return spec. Return [""] if StringUtilz#split() param is empty string

Fixed:

  - Fix bug of tag escaping in JSP TagLib

## 0.2.5 (2020-05-10)

Add:

  - Add HTMLUtilz.escapeHTML5Fully(String) method
  - Add JavaScript escaping feature to JSP TagLib
  - Add NumberUtilz#getIntegerPart, #getDecimalPart, #addDigit method
  - Add StringUtils#count(String, String...) method
  - Add StringUtilz#swapCase, #capitalize, #initials method

Changed:

  - Update TimeZone mappings of DateUtilz#localeToTimeZone(Locale)
  - Remove NumberUtilz#parseJPNum(String jpNum, RoundingMode decimalRoundingMode, int fixedDecimalScale)
  - Change StringUtilz#replaceAll, #startsWith, #endsWith, #split method signature

## 0.2.4 (2020-04-11)

Add:

  - Add HTMLUtilz class

Changed:

  - Change NumberUtilz#toJPNum/parseJPNum methods. Throws exception if parse error occurred.

## 0.2.3 (2020-04-07)

Add:

  - Add DateUtilz#parseDate(String, Calendar, Locale, String...) method

## 0.2.2 (2020-04-05)

Add:

  - Add decimal number parsing feature to NumberUtilz#parseEnNumShortScale

Changed:

  - Change return type of NumberUtilz#parseJPNum, BigInteger to BigDecimal  [Breaking Change]
  - Change Daiji option of NumberUtilz#toJPNum to strict specification  [Breaking Change]

## 0.2.1 (2020-03-29)

Add:

  - Add NumberUtilz#toEnNumShortScale and NumberUtilz#parseEnNumShortScale methods

## 0.2.0 (2017-01-08)

Add:

  - Add .text.format.NamedFormatter class
  - Add .swing.ObjectTableModel class
  - Add .swing.AdjustableImageIcon class
  - Add .util.EnvUtilz class
  - Add FileUtilz#getPath(Path basePath, String... subPaths) method
  - Add #startsWith and #endsWith methods to StringUtilz
  - Add #toString method for matcher classes

Changed:

  - Change file separator '/' to File.separatorChar in FileUtilz
  - Remove #loadFromAppConfig method from Config interface.

Fixed:

  - Fix Config#getAsString; When config value is null then return not null but ""
  - Remove unnecessary dependence 'javax.servlet'
  - Fix some bugs

## 0.1.0 (2016-11-25)

Features:

  - Release first version
