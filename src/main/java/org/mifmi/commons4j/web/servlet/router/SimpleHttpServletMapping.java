/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.servlet.router;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mifmi.commons4j.io.file.FileUtilz;

public class SimpleHttpServletMapping implements IHttpServletMapping {

	private IHttpRequestMatcher matcher;
	private String servletId;
	
	public SimpleHttpServletMapping(IHttpRequestMatcher matcher, String servletId) {
		this.matcher = matcher;
		this.servletId = servletId;
	}
	
	public IHttpRequestMatcher getMatcher() {
		return this.matcher;
	}
	
	public String getServletId() {
		return this.servletId;
	}

	public RequestDispatcher getRequestDispatcher(ServletContext context, HttpServletRequest request, HttpServletResponse response) {

		boolean match = this.matcher.match(request, response);
		if (!match) {
			return null;
		}
		
		RequestDispatcher dispatcher;
		if (this.servletId.startsWith("/")) {
			dispatcher = request.getRequestDispatcher(this.servletId);
		} else if (this.servletId.startsWith(".")) {
			String basePath = request.getServletPath();
			String path = FileUtilz.getCanonicalPath(FileUtilz.getPath(basePath, this.servletId, '/'), '/');
			dispatcher = request.getRequestDispatcher(path);
		} else {
			dispatcher = context.getNamedDispatcher(this.servletId);
		}
		
		return dispatcher;
	}
}
