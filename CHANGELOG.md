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
