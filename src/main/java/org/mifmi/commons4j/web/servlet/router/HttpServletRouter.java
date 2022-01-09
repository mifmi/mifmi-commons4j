/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.servlet.router;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpServletRouter {

	private List<IHttpServletMapping> routeList = new ArrayList<>();
	
	public HttpServletRouter() {
	}
	
	public HttpServletRouter addRoute(SimpleHttpRequestMatcher matcher, String servletId) {
		this.routeList.add(new SimpleHttpServletMapping(matcher, servletId));
		return this;
	}

	public HttpServletRouter get(String pathPattern, String servletId) {
		return addRoute(new SimpleHttpRequestMatcher().methods("GET").path(pathPattern), servletId);
	}

	public HttpServletRouter get(Pattern pathPattern, String servletId) {
		return addRoute(new SimpleHttpRequestMatcher().methods("GET").path(pathPattern), servletId);
	}

	public HttpServletRouter post(String pathPattern, String servletId) {
		return addRoute(new SimpleHttpRequestMatcher().methods("POST").path(pathPattern), servletId);
	}

	public HttpServletRouter post(Pattern pathPattern, String servletId) {
		return addRoute(new SimpleHttpRequestMatcher().methods("POST").path(pathPattern), servletId);
	}

	public HttpServletRouter put(String pathPattern, String servletId) {
		return addRoute(new SimpleHttpRequestMatcher().methods("PUT").path(pathPattern), servletId);
	}

	public HttpServletRouter put(Pattern pathPattern, String servletId) {
		return addRoute(new SimpleHttpRequestMatcher().methods("PUT").path(pathPattern), servletId);
	}

	public HttpServletRouter delete(String pathPattern, String servletId) {
		return addRoute(new SimpleHttpRequestMatcher().methods("DELETE").path(pathPattern), servletId);
	}

	public HttpServletRouter delete(Pattern pathPattern, String servletId) {
		return addRoute(new SimpleHttpRequestMatcher().methods("DELETE").path(pathPattern), servletId);
	}
	
	public boolean forward(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean forward = false;
		for (IHttpServletMapping mapping : this.routeList) {
			RequestDispatcher dispatcher = mapping.getRequestDispatcher(context, request, response);
				
			if (dispatcher == null) {
				continue;
			}
			
			dispatcher.forward(request, response);
			forward = true;
			break;
		}
		
		return forward;
	}
}
