/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.transaction;

import java.util.List;

public final class TransactionUtilz {

	private TransactionUtilz() {
		// NOP
	}

	public static void commit(List<? extends Transactional> transactions) {
		commit(transactions, 0);
	}

	public static void rollback(List<? extends Transactional> transactions) {
		rollback(transactions, transactions.size() - 1);
	}

	public static void init(List<? extends Initializable> transactions) {
		init(transactions, 0);
	}

	public static void dispose(List<? extends Disposable> transactions) {
		dispose(transactions, 0);
	}
	
	private static void commit(List<? extends Transactional> transactions, int beginIndex) {
		int i = beginIndex;
		try {
			for ( ; i < transactions.size(); i++) {
				Transactional transaction = transactions.get(i);
				transaction.commit();
			}
		} catch (RuntimeException e) {
			rollback(transactions, i);
			throw e;
		}
	}

	private static void rollback(List<? extends Transactional> transactions, int beginIndex) {
		RuntimeException firstException = null;
		for (int i = beginIndex; 0 <= i; i--) {
			try {
				Transactional transaction = transactions.get(i);
				transaction.rollback();
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

	private static void init(List<? extends Initializable> transactions, int beginIndex) {
		RuntimeException firstException = null;
		for (int i = beginIndex; i < transactions.size(); i++) {
			try {
				Initializable transaction = transactions.get(i);
				transaction.init();
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

	private static void dispose(List<? extends Disposable> transactions, int beginIndex) {
		RuntimeException firstException = null;
		for (int i = beginIndex; i < transactions.size(); i++) {
			try {
				Disposable transaction = transactions.get(i);
				transaction.dispose();
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
}
