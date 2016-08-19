/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.io.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.Date;

import org.mifmi.commons4j.util.StringUtilz;

public final class FileUtilz {
	
	private static final char[] FILE_NAME_SEPATATORS = {'/', '\\'};
	private static final char DEFAULT_FILE_NAME_SEPATATOR = '/';
	private static final char FILE_EXTENTION_SEPARATOR = '.';
	
	private FileUtilz() {
	}
	
	public static char getFileNameSeparator(String path) {
		return getFileNameSeparator(path, DEFAULT_FILE_NAME_SEPATATOR);
	}
	
	public static char getFileNameSeparator(String path, char defaultSeparator) {
		int idx = StringUtilz.indexOf(path, FILE_NAME_SEPATATORS);
		char fileNameSeparator;
		if (idx == -1) {
			fileNameSeparator = defaultSeparator;
		} else {
			fileNameSeparator = path.charAt(idx);
		}
		
		return fileNameSeparator;
	}

	public static String getFileName(String path) {
		if (path == null) {
			return null;
		}
		
		int idx = StringUtilz.lastIndexOf(path, FILE_NAME_SEPATATORS);
		if (idx == -1) {
			return path;
		} else {
			return path.substring(idx + 1);
		}
	}
	
	public static String getBasePath(String path) {
		if (path == null) {
			return null;
		}
		
		int idx = path.lastIndexOf(FILE_EXTENTION_SEPARATOR);
		if (idx <= 0) {
			return path;
		} else {
			return path.substring(0, idx);
		}
	}
	
	public static String getBaseName(String path) {
		if (path == null) {
			return null;
		}
		
		String fileName = getFileName(path);
		
		int idx = fileName.lastIndexOf(FILE_EXTENTION_SEPARATOR);
		if (idx <= 0) {
			return fileName;
		} else {
			return path.substring(0, idx);
		}
	}
	
	public static String getExt(String path) {
		if (path == null) {
			return null;
		}
		
		int idx = path.lastIndexOf(FILE_EXTENTION_SEPARATOR);
		if (idx <= 0) {
			return "";
		} else {
			idx++;
			if (path.length() <= idx) {
				return "";
			} else {
				return path.substring(idx);
			}
		}
	}
	
	public static String addSuffix(String path, String suffix) {
		if (path == null) {
			return null;
		}
		if (path.indexOf(FILE_EXTENTION_SEPARATOR) == -1) {
			return path + suffix;
		}
		return getBasePath(path) + suffix + FILE_EXTENTION_SEPARATOR + getExt(path);

	}
	
	public static String getParentFolderPath(String path) {
		if (path == null) {
			return null;
		}

		int idx = StringUtilz.lastIndexOf(path, FILE_NAME_SEPATATORS);
		if (idx <= 0) {
			return "";
		}
		if (path.length() == idx + 1) {
			idx = StringUtilz.lastIndexOf(path, FILE_NAME_SEPATATORS, idx - 1);
			if (idx <= 0) {
				return "";
			}
		}
		return path.substring(0, idx);
	}
	
	public static String getPath(String basePath, String subPath) {
		return getPath(basePath, subPath, DEFAULT_FILE_NAME_SEPATATOR);
	}
	
	public static String getPath(String basePath, String subPath, char separator) {
		if (basePath == null) {
			basePath = "";
		}
		if (subPath == null) {
			subPath = "";
		}
		
		String strSep = String.valueOf(separator);
		
		String path;
		if (basePath.endsWith(strSep)) {
			if (subPath.startsWith(strSep)) {
				// ~/ + /~
				path = basePath + subPath.substring(1);
			} else {
				// ~/ + ~
				path = basePath + subPath;
			}
		} else {
			if (subPath.startsWith(strSep)) {
				// ~ + /~
				path = basePath + subPath;
			} else {
				// ~ + / + ~
				path = basePath + separator + subPath;
			}
		}
		
		return path;
	}
	
	public static String getCanonicalPath(String path, char separator) {

		StringBuilder pathSb = new StringBuilder(path);
		
		int prevSepIdx = -1;
		int sepIdx = -1;
		boolean o = true;
		int d = 0;
		for (int i = 0; i < pathSb.length(); i++) {
			char ch = pathSb.charAt(i);
			if (o && ch == '.') {
				d++;
			} else if (ch == separator) {
				if (o) {
					if (d == 0) {
						prevSepIdx = sepIdx;
						sepIdx = i;
					} else if (d == 1) {
						pathSb.delete(sepIdx + 1, i + 1);
						
						i = sepIdx;
					} else if (d == 2) {
						pathSb.delete(prevSepIdx + 1, i + 1);
						
						i = prevSepIdx;
						sepIdx = prevSepIdx;
						prevSepIdx = StringUtilz.lastIndexOf(pathSb, separator, prevSepIdx - 1);
					} else {
						throw new RuntimeException();
					}
				} else {
					prevSepIdx = sepIdx;
					sepIdx = i;
				}
				o = true;
				d = 0;
			} else {
				o = false;
				d = 0;
			}
		}

		if (o) {
			if (d == 1) {
				pathSb.setLength(sepIdx + 1);
			} else if (d == 2) {
				pathSb.setLength(prevSepIdx + 1);
			}
		}
		
		return pathSb.toString();
	}

	public static void setTimes(Path path, Date time) throws IOException {
		FileTime fileTime = FileTime.fromMillis(time.getTime());
		
		BasicFileAttributeView attrs = Files.getFileAttributeView(path, BasicFileAttributeView.class);
		attrs.setTimes(fileTime, fileTime, fileTime);
	}

	public static void setTimes(Path path, long time) throws IOException {
		FileTime fileTime = FileTime.fromMillis(time);
		
		BasicFileAttributeView attrs = Files.getFileAttributeView(path, BasicFileAttributeView.class);
		attrs.setTimes(fileTime, fileTime, fileTime);
	}

	public static void setTimes(Path path, Date lastModifiedTime, Date lastAccessTime, Date creationTIme) throws IOException {
		BasicFileAttributeView attrs = Files.getFileAttributeView(path, BasicFileAttributeView.class);
		attrs.setTimes(
				(lastModifiedTime == null) ? null : FileTime.fromMillis(lastModifiedTime.getTime()),
				(lastAccessTime == null) ? null : FileTime.fromMillis(lastAccessTime.getTime()),
				(creationTIme == null) ? null : FileTime.fromMillis(creationTIme.getTime())
				);
	}

	public static void setTimes(Path path, long lastModifiedTime, long lastAccessTime, long creationTIme) throws IOException {
		BasicFileAttributeView attrs = Files.getFileAttributeView(path, BasicFileAttributeView.class);
		attrs.setTimes(
				FileTime.fromMillis(lastModifiedTime),
				FileTime.fromMillis(lastAccessTime),
				FileTime.fromMillis(creationTIme)
				);
	}
}
