/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.datastore;

import java.sql.Connection;

public class JDBCConnectionLoader extends AbstractConnectionLoader {
	private String driver;
	private String url;
	private String user;
	private String password;

	public JDBCConnectionLoader(
			String driver,
			String url
			) {
		this(driver, url, null, null);
	}
	public JDBCConnectionLoader(
			String driver,
			String url,
			String user,
			String password
			) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public Connection getConnection(String name) {
		return getJDBCConnection(driver, url, user, password);
	}

}
