/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.text.token;

public class StringToken {

	private String token;
	private int tokenEndIndex;
	private String[] blockSet;
	private String terminator;
	private int terminatorEndIndex;
	private boolean eos;
	
	public StringToken(String token, int tokenEndIndex, String[] blockSet,  String terminator, int terminatorEndIndex, boolean eos) {
		this.token = token;
		this.tokenEndIndex = tokenEndIndex;
		this.blockSet = blockSet;
		this.terminator = terminator;
		this.terminatorEndIndex = terminatorEndIndex;
		this.eos = eos;
	}

	public String getToken() {
		return token;
	}

	public int getTokenEndIndex() {
		return tokenEndIndex;
	}

	public String[] getBlockSet() {
		return blockSet;
	}

	public String getTerminator() {
		return terminator;
	}

	public int getTerminatorEndIndex() {
		return terminatorEndIndex;
	}

	public boolean isEos() {
		return eos;
	}

	@Override
	public String toString() {
		return this.token;
	}

}
