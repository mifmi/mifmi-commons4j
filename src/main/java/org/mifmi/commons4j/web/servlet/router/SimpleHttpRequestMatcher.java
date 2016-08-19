/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.servlet.router;

import java.util.Locale;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mifmi.commons4j.matcher.IMatcher;
import org.mifmi.commons4j.matcher.StringMatcher;

public class SimpleHttpRequestMatcher implements IHttpRequestMatcher {
	
	private String[] methods = null;
	
	private IMatcher<String> hostMatcher = null;
	private Pattern hostRegex = null;
	
	private Integer port = null;
	
	private IMatcher<String> pathMatcher = null;
	private Pattern pathRegex = null;

	public SimpleHttpRequestMatcher() {
	}

	public SimpleHttpRequestMatcher methods(String... methods) {
		this.methods = methods;
		return this;
	}
	
	public SimpleHttpRequestMatcher host(String host) {
		this.hostMatcher = StringMatcher.compileWildcard(host, true, Locale.ENGLISH);
		return this;
	}
	
	public SimpleHttpRequestMatcher host(Pattern host) {
		this.hostRegex = host;
		return this;
	}
	
	public SimpleHttpRequestMatcher port(int port) {
		this.port = Integer.valueOf(port);
		return this;
	}
	
	public SimpleHttpRequestMatcher path(String path) {
		this.pathMatcher = StringMatcher.compileWildcard(path, true, Locale.ENGLISH);
		return this;
	}
	
	public SimpleHttpRequestMatcher path(Pattern path) {
		this.pathRegex = path;
		return this;
	}
	
	public boolean match(HttpServletRequest request, HttpServletResponse response) {
		// Method
		if (this.methods != null && this.methods.length != 0) {
			String reqMethod = request.getMethod();
			boolean match = false;
			for (String method : this.methods) {
				if (method.equals(reqMethod)) {
					match = true;
					break;
				}
			}
			if (!match) {
				return false;
			}
		}
		
		// Host
		if (this.hostMatcher != null) {
			String reqHost = request.getRemoteHost();
			if (!this.hostMatcher.matches(reqHost)) {
				return false;
			}
		}
		if (this.hostRegex != null) {
			String reqHost = request.getRemoteHost();
			if (!this.hostRegex.matcher(reqHost).matches()) {
				return false;
			}
		}
		
		// Port
		if (this.port != null) {
			int reqPort = request.getServerPort();
			if (this.port.intValue() != reqPort) {
				return false;
			}
		}
		
		// Path
		if (this.pathMatcher != null) {
			String reqPath = request.getServletPath();
			String reqPathInfo = request.getPathInfo();
			if (reqPathInfo != null) {
				reqPath += reqPathInfo;
			}
			if (!this.pathMatcher.matches(reqPath)) {
				return false;
			}
		}
		if (this.pathRegex != null) {
			String reqPath = request.getServletPath();
			String reqPathInfo = request.getPathInfo();
			if (reqPathInfo != null) {
				reqPath += reqPathInfo;
			}
			if (!this.pathRegex.matcher(reqPath).matches()) {
				return false;
			}
		}
		
		return true;
	}
}
