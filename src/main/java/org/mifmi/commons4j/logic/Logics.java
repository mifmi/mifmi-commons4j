/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.logic;

import java.util.Collection;
import java.util.LinkedList;

import org.mifmi.commons4j.transaction.Disposable;
import org.mifmi.commons4j.transaction.Transactional;

public class Logics implements Transactional, Disposable {

	private LinkedList<Logic> logics;

	public Logics() {
		this.logics = new LinkedList<Logic>();
	}
	
	public Logics(Collection<Logic> logics) {
		if (logics == null) {
			throw new NullPointerException();
		}
		this.logics = new LinkedList<Logic>(logics);
	}
	
	public Logics(Logic... logics) {
		if (logics == null) {
			throw new NullPointerException();
		}
		this.logics = new LinkedList<Logic>();
		for (Logic logic : logics) {
			this.logics.add(logic);
		}
	}

	public Logics add(Logic logic) {
		this.logics.add(logic);
		return this;
	}

	public Logics addAll(Collection<Logic> logics) {
		this.logics.addAll(logics);
		return this;
	}
	
	public Logics remove(Logic logic) {
		this.logics.remove(logic);
		return this;
	}
	
	public int size() {
		return this.logics.size();
	}

	public void setTransactionEnabled(boolean transactionEnabled) {
		throw new UnsupportedOperationException();
	}
	
	public void commit() {
		int i = 0;
		try {
			for ( ; i < this.logics.size(); i++) {
				Logic logic = this.logics.get(i);
				logic.commit();
			}
		} catch (RuntimeException e) {
			rollback(i);
			throw e;
		}
	}

	public void rollback() {
		rollback(this.logics.size() - 1);
	}

	private void rollback(int beginIndex) {
		RuntimeException firstException = null;
		for (int i = beginIndex; 0 <= i; i--) {
			try {
				Logic logic = this.logics.get(i);
				logic.rollback();
			} catch (RuntimeException e) {
				if (firstException == null) {
					firstException = e;
				}
			}
		}
		if (firstException != null) {
			throw firstException;
		}
	}

	public void dispose() {
		RuntimeException firstException = null;
		for (int i = 0; i < this.logics.size(); i++) {
			try {
				Logic logic = this.logics.get(i);
				logic.dispose();
			} catch (RuntimeException e) {
				if (firstException == null) {
					firstException = e;
				}
			}
		}
		if (firstException != null) {
			throw firstException;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			dispose();
		} finally {
			super.finalize();
		}
	}
}
