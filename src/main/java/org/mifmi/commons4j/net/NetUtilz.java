/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.net;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;

public final class NetUtilz {

	private NetUtilz() {
		// NOP
	}

	public static String matchUri(URI uri, String... uriPatterns) {
		if (uriPatterns == null || uriPatterns.length == 0) {
			return null;
		}
		
		String host = uri.getHost();
		
		for (String uriPattern : uriPatterns) {
			char firstChar = uriPattern.charAt(0);
			if (firstChar == '*') {
				String subUriPattern = uriPattern.substring(1);
				if (host.endsWith(subUriPattern)) {
					return uriPattern;
				}
			} else {
				if (host.equals(uriPattern)) {
					return uriPattern;
				}
			}
		}
		
		InetAddress addr;
		try {
			addr = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			return null;
		}
		String hostAddr = addr.getHostAddress();

		for (String uriPattern : uriPatterns) {
			char lastChar = uriPattern.charAt(uriPattern.length() - 1);
			if (lastChar == '*') {
				String subUriPattern = uriPattern.substring(0, uriPattern.length() - 1);
				if (hostAddr.startsWith(subUriPattern)) {
					return uriPattern;
				}
			} else {
				if (hostAddr.equals(uriPattern)) {
					return uriPattern;
				}
			}
		}
		
		return null;
	}

	public static String matchUri(URI uri, List<String> uriPatterns) {
		if (uriPatterns == null || uriPatterns.isEmpty()) {
			return null;
		}
		
		String host = uri.getHost();
		
		for (String uriPattern : uriPatterns) {
			char firstChar = uriPattern.charAt(0);
			if (firstChar == '*') {
				String subUriPattern = uriPattern.substring(1);
				if (host.endsWith(subUriPattern)) {
					return uriPattern;
				}
			} else {
				if (host.equals(uriPattern)) {
					return uriPattern;
				}
			}
		}
		
		InetAddress addr;
		try {
			addr = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			return null;
		}
		String hostAddr = addr.getHostAddress();

		for (String uriPattern : uriPatterns) {
			char lastChar = uriPattern.charAt(uriPattern.length() - 1);
			if (lastChar == '*') {
				String subUriPattern = uriPattern.substring(0, uriPattern.length() - 1);
				if (hostAddr.startsWith(subUriPattern)) {
					return uriPattern;
				}
			} else {
				if (hostAddr.equals(uriPattern)) {
					return uriPattern;
				}
			}
		}
		
		return null;
	}
}
