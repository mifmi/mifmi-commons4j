/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.logic;

import org.mifmi.commons4j.transaction.Disposable;
import org.mifmi.commons4j.transaction.Transactional;

public abstract class AbstractLogic implements Logic, Transactional, Disposable {

	private LogicContext context;
	
	private boolean transactionDisabled = false;
	
	private boolean transactionFinished = false;
	
	private boolean disposed = false;
	
	
	public abstract void doCommit();
	
	public abstract void doRollback();
	
	public abstract void doDispose();
	
	
	public LogicContext getContext() {
		return context;
	}

	public void setContext(LogicContext context) {
		this.context = context;
	}

	public boolean isTransactionDisabled() {
		return transactionDisabled;
	}

	public void setTransactionDisabled(boolean transactionDisabled) {
		this.transactionDisabled = transactionDisabled;
	}
	
	protected <T> T context(String key) {
		return getContext().getAttribute(key);
	}
	
	protected void setContext(String key, Object value) {
		getContext().setAttribute(key, value);
	}

	public void commit() {
		if (this.transactionDisabled || this.transactionFinished) {
			return;
		}
		doCommit();
		this.transactionFinished = true;
	}

	public void rollback() {
		if (this.transactionDisabled || this.transactionFinished) {
			return;
		}
		doRollback();
		this.transactionFinished = true;
	}

	public void dispose() {
		if (this.disposed) {
			return;
		}
		doDispose();
		this.disposed = true;
	}
}
