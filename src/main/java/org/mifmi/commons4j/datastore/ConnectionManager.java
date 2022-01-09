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
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.mifmi.commons4j.transaction.Transactional;

public class ConnectionManager implements Transactional {
	private static ThreadLocal<ConnectionManager> localInstance = new ThreadLocal<ConnectionManager>();

	private static ConnectionLoader defaultConnectionLoader = new DataSourceConnectionLoader();
	
	private ConnectionLoader connectionLoader = null;
	
	private Map<String, Connection> connectionMap = new HashMap<String, Connection>();

	private ConnectionManager() {
		this.connectionLoader = defaultConnectionLoader;
	}

	private ConnectionManager(ConnectionLoader connectionLoader) {
		this.connectionLoader = connectionLoader;
	}
	
	public static ConnectionManager getInstance() {
		ConnectionManager instance = localInstance.get();
		if (instance == null) {
			instance = new ConnectionManager();
			localInstance.set(instance);
		}
		return instance;
	}
	
	public static void setDefaultConnectionLoader(ConnectionLoader connectionLoader) {
		defaultConnectionLoader = connectionLoader;
	}
	
	public void setConnectionLoader(ConnectionLoader connectionLoader) {
		this.connectionLoader = connectionLoader;
	}

	public Connection getConnection() {
		return getConnection(null);
	}
	public Connection getConnection(boolean autoCommit) {
		return getConnection(null, autoCommit);
	}

	public Connection getConnection(String name) {
		return getConnection(name, null);
	}
	public Connection getConnection(String name, boolean autoCommit) {
		return getConnection(name, null, autoCommit);
	}

	public Connection getConnection(String name, String transactionName) {
		return getConnection(name, transactionName, false);
	}
	public Connection getConnection(String name, String transactionName, boolean autoCommit) {
		
		if (name == null) {
			name = "default";
		}
		if (transactionName == null) {
			transactionName = "";
		}
		String connectionMapName = name + "#" + transactionName;
		
		Connection conn = this.connectionMap.get(connectionMapName);
		try {
			if (conn != null && conn.isClosed()) {
				conn = null;
			}
		} catch (SQLException e) {
			conn = null;
		}
		if (conn == null) {
			ConnectionLoader loader = this.connectionLoader;
			if (loader == null) {
				loader = defaultConnectionLoader;
			}
			conn = loader.getConnection(name);
			
			this.connectionMap.put(connectionMapName, conn);
		}
		
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			throw new DatastoreException(e);
		}
		
		return conn;
	}
	
	public Collection<Connection> getConnections() {
		return this.connectionMap.values();
	}
	
	public void rollback() {
		RuntimeException ex = null;
		for (Connection conn : this.connectionMap.values()) {
			try {
				if (!conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e) {
				if (ex != null) {
					ex = new DatastoreException(e);
				}
			} catch (RuntimeException e) {
				if (ex != null) {
					ex = e;
				}
			}
		}
		
		if (ex != null) {
			throw ex;
		}
	}
	
	public void commit() {
		RuntimeException ex = null;
		for (Connection conn : this.connectionMap.values()) {
			try {
				if (!conn.isClosed()) {
					conn.commit();
				}
			} catch (SQLException e) {
				if (ex != null) {
					ex = new DatastoreException(e);
				}
			} catch (RuntimeException e) {
				if (ex != null) {
					ex = e;
				}
			}
		}
		
		if (ex != null) {
			throw ex;
		}
	}
	
	public void close() {
		RuntimeException ex = null;
		for (Connection conn : this.connectionMap.values()) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				if (ex != null) {
					ex = new DatastoreException(e);
				}
			} catch (RuntimeException e) {
				if (ex != null) {
					ex = e;
				}
			}
		}
		this.connectionMap.clear();
		
		if (ex != null) {
			throw ex;
		}
	}
}
