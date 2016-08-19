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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class NoEncodeUrlFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
		// NOP
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(response instanceof HttpServletResponse)) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpServletResponse res = (HttpServletResponse)response;
		
		HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(res) {

			@Override
			public String encodeRedirectURL(String url) {
				return url;
			}

			@Override
			public String encodeRedirectUrl(String url) {
				return url;
			}

			@Override
			public String encodeURL(String url) {
				return url;
			}

			@Override
			public String encodeUrl(String url) {
				return url;
			}
		};

		chain.doFilter(request, wrappedResponse);
	}

	public void destroy() {
		// NOP
	}
}
