/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.text.token;

import org.mifmi.commons4j.util.StringUtilz;

public class StringTokenParser {
	private String str;
	private int index;
	private int endIdx;
	private int escapeChar;
	private char[] escapeTargetChars;
	private char[] escapedChars;
	private String[][] blockquoteSets;
	private boolean blockquoteStart;
	private boolean terminateBlockEnd;
	private boolean trim;
	
	private StringToken lastToken = null;

	public StringTokenParser(String str) {
		this(str, -1);
	}
	
	public StringTokenParser(String str, int endIdx) {
		this.str = str;
		this.index = 0;
		this.endIdx = (endIdx < 0) ? str.length() : endIdx;
		
		this.escapeChar = -1;
		this.escapeTargetChars = null;
		this.escapedChars = null;
		
		this.blockquoteSets = null;
		this.terminateBlockEnd = false;
		
		this.trim = false;
	}
	
	public StringTokenParser setEscape(int escapeChar) {
		return setEscape(escapeChar, null, null);
	}
	
	public StringTokenParser setEscape(int escapeChar, char[] escapeTargetChars, char[] escapedChars) {
		if (escapeTargetChars == null && escapedChars == null) {
			// NOP
		} else if (escapeTargetChars.length == escapedChars.length) {
			// NOP
		} else {
			throw new IllegalArgumentException();
		}
		
		this.escapeChar = escapeChar;
		this.escapeTargetChars = escapeTargetChars;
		this.escapedChars = escapedChars;
		return this;
	}
	
	public StringTokenParser setBlockquote(String[][] blockquoteSets, boolean blockquoteStart, boolean terminateBlockEnd) {
		this.blockquoteSets = blockquoteSets;
		this.blockquoteStart = blockquoteStart;
		this.terminateBlockEnd = terminateBlockEnd;
		return this;
	}
	
	public StringTokenParser setTrim(boolean trim) {
		this.trim = trim;
		return this;
	}
	
	public StringTokenParser seek(int index) {
		this.index = index;
		return this;
	}
	
	public StringTokenParser offset(int offsetIndex) {
		seek(getCurrentIndex() + offsetIndex);
		return this;
	}
	
	public StringTokenParser skipWhitespaces() {
		for (int i = this.index; i < this.endIdx; i++) {
			char ch = this.str.charAt(i);
			this.index = i;
			if (!Character.isWhitespace(ch)) {
				break;
			}
		}
		
		return this;
	}
	
	public int getCurrentIndex() {
		return this.index;
	}
	
	public char getCurrentChar() {
		return charAt(getCurrentIndex());
	}
	
	public char charAt(int index) {
		return this.str.charAt(index);
	}
	
	public boolean isNext(String next) {
		return this.str.startsWith(next, index);
	}

	public StringToken getLastToken() {
		return lastToken;
	}

	public StringToken nextToken(String... terminators) {
		return nextToken(0, terminators);
	}
	public StringToken nextToken(int terminatorOffset, String... terminators) {
		if (this.trim) {
			skipWhitespaces();
		}
		
		StringToken token = readToken(this.str, this.index, this.endIdx, this.escapeChar, this.escapeTargetChars, this.escapedChars, this.blockquoteSets, this.blockquoteStart, this.terminateBlockEnd, this.trim, terminatorOffset, terminators);
		this.index = token.getTerminatorEndIndex();
		this.lastToken = token;
		
		return token;
	}

	private static StringToken readToken(String str, int beginIdx, int endIdx, int escapeChar, char[] escapeTargetChars, char[] escapedChars, String[][] blockquoteSets, boolean blockquoteStart, boolean terminateBlockEnd, boolean trim, int terminatorOffset, String... terminators) {
		int maxIdx = endIdx - 1;
		StringBuilder sb = new StringBuilder(endIdx - beginIdx);
		int blockquoteSetIdx = -1;
		for (int i = beginIdx; i < endIdx; i++) {
			char ch = str.charAt(i);
			if (ch == escapeChar) {
				if (i == maxIdx) {
					throw new IllegalArgumentException();
				}
				
				char nch = str.charAt(++i);
				
				if (escapeTargetChars == null) {
					sb.append(ch);
					sb.append(nch);
				} else if (escapedChars == null) {
					sb.append(nch);
				} else {
					boolean matched = false;
					for (int eIdx = 0; eIdx < escapeTargetChars.length; eIdx++) {
						if (nch == escapeTargetChars[eIdx]) {
							sb.append(escapedChars[eIdx]);
							matched = true;
							break;
						}
					}
					if (!matched) {
						sb.append(nch);
					}
				}
			} else {
				if (blockquoteSets != null) {
					if (blockquoteSetIdx != -1) {
						// Block end
						String[] blockquoteSet = blockquoteSets[blockquoteSetIdx];
						String blockEndStr = (blockquoteSet.length < 2) ? blockquoteSet[0] : blockquoteSet[blockquoteSet.length - 1];
						
						if (str.startsWith(blockEndStr, i)) {
							if (terminateBlockEnd) {
								return new StringToken(StringUtilz.toString(sb, trim), i, blockquoteSet, blockEndStr, i + blockEndStr.length(), (endIdx <= i + blockEndStr.length()));
							}
							blockquoteSetIdx = -1;
							i += blockEndStr.length() - 1;
						}
					} else if (!blockquoteStart || i == beginIdx) {
						// Block start
						for (int idx = 0; idx < blockquoteSets.length; idx++) {
							String blockStartStr = blockquoteSets[idx][0];
							if (str.startsWith(blockStartStr, i)) {
								blockquoteSetIdx = idx;
								i += blockStartStr.length() - 1;
								break;
							}
						}
					} else {
						// NOP
					}
				}

				if (blockquoteSetIdx == -1 && terminators != null && terminators.length != 0 && beginIdx + terminatorOffset <= i) {
					for (String delimiter : terminators) {
						if (str.startsWith(delimiter, i)) {
							return new StringToken(StringUtilz.toString(sb, trim), i, null, delimiter, i + delimiter.length(), (endIdx <= i + delimiter.length()));
						}
					}
				}
				
				sb.append(ch);
			}

			if (i == maxIdx) {
				return new StringToken(StringUtilz.toString(sb, trim), endIdx, null, "", endIdx, true);
			}
		}
		
		return new StringToken(StringUtilz.toString(sb, trim), endIdx, null, "", endIdx, true);
	}

	@Override
	public String toString() {
		return this.str;
	}
}
