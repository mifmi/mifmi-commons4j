/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.logic;

import java.lang.reflect.InvocationTargetException;

import org.mifmi.commons4j.transaction.Disposable;
import org.mifmi.commons4j.transaction.Transactional;

public class LogicManager implements Transactional, Disposable {

	private static ThreadLocal<LogicManager> localInstance = new ThreadLocal<LogicManager>();
	
	private LogicContext context;
	
	private Logics logics;
	
	private LogicManager() {
		this.context = new LogicContext();
		this.logics = new Logics();
	}
	
	public static LogicManager getInstance() {
		LogicManager instance = localInstance.get();
		if (instance == null) {
			instance = new LogicManager();
			localInstance.set(instance);
		}
		return instance;
	}
	
	public LogicContext getContext() {
		return context;
	}

	public <T extends Logic> T createLogic(Class<T> logicClass) {
		return createLogic(logicClass, false);
	}
	
	public <T extends Logic> T createLogic(Class<T> logicClass, boolean transactionDisabled) {
		T logic;
		try {
			logic = logicClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException e) {
			throw new LogicException(e);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (IllegalArgumentException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		} catch (NoSuchMethodException e) {
			throw new LogicException(e);
		} catch (SecurityException e) {
			throw new LogicException(e);
		}
		
		logic.setContext(this.context);
		logic.setTransactionDisabled(transactionDisabled);
		
		this.logics.add(logic);
		
		return logic;
	}

	public void commit() {
		this.logics.commit();
	}

	public void rollback() {
		this.logics.rollback();
	}

	public void dispose() {
		this.logics.dispose();
	}
}
