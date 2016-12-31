/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Helper methods for working with environment.
 * 
 * @author mozq
 */
public final class EnvUtilz {

	private EnvUtilz() {
		// NOP
	}
	
	public static Path getAppDataDir() {
		String osName = System.getProperty("os.name").toLowerCase();

		Path path;
		if (osName.startsWith("windows")) {
			String roamingAppData = System.getenv("AppData");
			if (roamingAppData != null && !roamingAppData.isEmpty()) {
				// C:/Users/username/AppData/Local
				// C:/Users/username/AppData/Roaming
				// C:/Documents and Settings/username/Local Settings/Application Data
				// C:/Documents and Settings/username/Application Data
				path = Paths.get(roamingAppData);
			} else {
				path = Paths.get(System.getProperty("user.home"), "Application Data");
			}
		} else if (osName.startsWith("mac")) {
			// /Users/username/Library/Application Support
			path = Paths.get(System.getProperty("user.home"), "Library", "Application Support");
		} else {
			path = null;
		}
		
		if (path == null || !Files.exists(path)) {
			// /home/username/.config/
			path = Paths.get(System.getProperty("user.home"), ".config");
		}
		
		if (path == null || !Files.exists(path)) {
			// /home/username/
			path = Paths.get(System.getProperty("user.home"));
		}
		
		return path;
	}
	
	public static Path getConfigDir() {
		String osName = System.getProperty("os.name").toLowerCase();

		Path path;
		if (osName.startsWith("windows")) {
			String roamingAppData = System.getenv("AppData");
			if (roamingAppData != null && !roamingAppData.isEmpty()) {
				// C:/Users/username/AppData/Local
				// C:/Users/username/AppData/Roaming
				// C:/Documents and Settings/username/Local Settings/Application Data
				// C:/Documents and Settings/username/Application Data
				path = Paths.get(roamingAppData);
			} else {
				path = Paths.get(System.getProperty("user.home"), "Application Data");
			}
		} else if (osName.startsWith("mac")) {
			// /Users/username/Library/Preferences
			path = Paths.get(System.getProperty("user.home"), "Library", "Preferences");
		} else {
			path = null;
		}
		
		if (path == null || !Files.exists(path)) {
			// /home/username/.config/
			path = Paths.get(System.getProperty("user.home"), ".config");
		}
		
		if (path == null || !Files.exists(path)) {
			// /home/username/
			path = Paths.get(System.getProperty("user.home"));
		}
		
		return path;
	}
	
	public static Path getTempDir() {
		return Paths.get(System.getProperty("java.io.tmpdir"));
	}
	
	public static Path getUserHomeDir() {
		return Paths.get(System.getProperty("user.home"));
	}
}
