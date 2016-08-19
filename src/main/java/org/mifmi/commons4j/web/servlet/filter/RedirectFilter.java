/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.servlet.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mifmi.commons4j.util.BooleanUtilz;

public class RedirectFilter implements Filter {

	private String from;
	private String to;
	
	private boolean regex;
	private Pattern fromPattern;

	private boolean temporary;
	
	public void init(FilterConfig config) throws ServletException {
		String configFrom = config.getInitParameter("from");
		String configTo = config.getInitParameter("to");
		String configRegex = config.getInitParameter("regex");
		String configTemporary = config.getInitParameter("temporary");
		
		this.regex = BooleanUtilz.toBoolean(configRegex);
		this.temporary = BooleanUtilz.toBoolean(configTemporary);
		
		this.from = configFrom;
		this.to = configTo;
		
		if (this.regex) {
			this.fromPattern = Pattern.compile(configFrom);
		} else {
			this.fromPattern = null;
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(response instanceof HttpServletResponse)) {
			chain.doFilter(request, response);
			return;
		}
		
		if (this.from != null && !this.from.isEmpty()) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			
			String url = req.getRequestURL().toString();
			
			String redirectUrl = null;
			if (this.regex) {
				Matcher matcher = this.fromPattern.matcher(url);
				redirectUrl = matcher.replaceAll(this.to);
			} else {
				redirectUrl = url.replace(this.from, this.to);
			}
			
			if (redirectUrl != null && !url.equals(redirectUrl)) {
				int status;
				if (this.temporary) {
					status = HttpServletResponse.SC_MOVED_TEMPORARILY;
				} else {
					status = HttpServletResponse.SC_MOVED_PERMANENTLY;
				}
				
				String queryString = req.getQueryString();
				if (queryString != null) {
					redirectUrl = redirectUrl + "?" + queryString;
				}
				
				// Redirect
				res.setStatus(status);
				res.setHeader("Location", redirectUrl);
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	public void destroy() {
		// NOP
	}
}
