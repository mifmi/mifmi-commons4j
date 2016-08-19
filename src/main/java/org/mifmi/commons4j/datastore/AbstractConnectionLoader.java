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
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class AbstractConnectionLoader implements ConnectionLoader {
	
	public AbstractConnectionLoader() {
	}
	
	protected static Connection getDataSourceConnection(String name) {
		Connection conn;
		try {
			InitialContext ictx = new InitialContext();
			DataSource ds = (DataSource)ictx.lookup("java:comp/env/" + name);
			conn = ds.getConnection();
		} catch (NamingException e) {
			throw new DatastoreException(e);
		} catch (SQLException e) {
			throw new DatastoreException(e);
		}
		
		return conn;
	}

	protected static Connection getJDBCConnection(
			String driver,
			String url,
			String user,
			String password
			) {

		Connection conn;
		try {
			Class.forName(driver);
			
			if (user == null) {
				conn = DriverManager.getConnection(url);
			} else {
				conn = DriverManager.getConnection(url, user, password);
			}
		} catch (ClassNotFoundException e) {
			throw new DatastoreException(e);
		} catch (SQLException e) {
			throw new DatastoreException(e);
		}
		
		return conn;
	}
}
