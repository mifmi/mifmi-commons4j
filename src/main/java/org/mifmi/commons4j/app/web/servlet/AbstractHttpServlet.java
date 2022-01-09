/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.app.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mifmi.commons4j.web.servlet.HttpReqRes;
import org.mifmi.commons4j.web.servlet.MifmiServletException;

public abstract class AbstractHttpServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ThreadLocal<HttpReqRes> localReqRes = new ThreadLocal<HttpReqRes>();
	
	protected void doInit() throws Exception {
	}

	protected void doDestroy() throws Exception {
	}
	
	protected void preService() throws Exception {
	}
	
	protected void postService() throws Exception {
	}
	
	protected void doGet() throws Exception {
		super.doGet(request(), response());
	}
	
	protected void doPost() throws Exception {
		super.doPost(request(), response());
	}
	
	protected void doPut() throws Exception {
		super.doPut(request(), response());
	}
	
	protected void doDelete() throws Exception {
		super.doDelete(request(), response());
	}
	
	protected boolean handleError(Throwable e) throws Exception {
		response().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return false;
	}

	private void initService(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpReqRes reqRes = new HttpReqRes(request, response);
		this.localReqRes.set(reqRes);
		
		doInit();
	}
	
	private void destroyService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		doDestroy();
		
//		this.localRequest.remove();
//		this.localResponse.remove();
//		this.localIsMultipartRequest.remove();
//		this.localParameterMap.remove();
//		this.localParameterPartMap.remove();
	}
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			initService(request, response);
			try {
				preService();
				super.service(request, response);
			} finally {
				postService();
			}
		} catch (Throwable e) {
			try {
				handleError(e);
			} catch (RuntimeException e1) {
				throw e1;
			} catch (ServletException e1) {
				throw e1;
			} catch (IOException e1) {
				throw e1;
			} catch (Exception e1) {
				throw new MifmiServletException(e1);
			}
		} finally {
			try {
				destroyService(request, response);
			} catch (Throwable e) {
				try {
					handleError(e);
				} catch (RuntimeException e1) {
					throw e1;
				} catch (ServletException e1) {
					throw e1;
				} catch (IOException e1) {
					throw e1;
				} catch (Exception e1) {
					throw new MifmiServletException(e1);
				}
			}
		}
	}
	
	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doGet();
		} catch (RuntimeException e) {
			throw e;
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new MifmiServletException(e);
		}
	}

	@Override
	protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doPost();
		} catch (RuntimeException e) {
			throw e;
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new MifmiServletException(e);
		}
	}

	@Override
	protected final void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doPut();
		} catch (RuntimeException e) {
			throw e;
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new MifmiServletException(e);
		}
	}
	
	@Override
	protected final void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doDelete();
		} catch (RuntimeException e) {
			throw e;
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new MifmiServletException(e);
		}
	}

	@Override
	protected final void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Class<?> clazz = this.getClass();
		boolean allowGet = (getDeclaredMethod(clazz, AbstractHttpServlet.class, "doGet") != null);
		boolean allowHead = allowGet;
		boolean allowPost = (getDeclaredMethod(clazz, AbstractHttpServlet.class, "doPost") != null);
		boolean allowPut = (getDeclaredMethod(clazz, AbstractHttpServlet.class, "doPut") != null);
		boolean allowDelete = (getDeclaredMethod(clazz, AbstractHttpServlet.class, "doDelete") != null);
		boolean allowTrace = true;
		boolean allowOptions = true;
		
		StringBuilder allowSb = new StringBuilder();
		if (allowGet) {
			allowSb.append("GET");
		}
		if (allowHead) {
			if (allowSb.length() != 0) {
				allowSb.append(", ");
			}
			allowSb.append("HEAD");
		}
		if (allowPost) {
			if (allowSb.length() != 0) {
				allowSb.append(", ");
			}
			allowSb.append("POST");
		}
		if (allowPut) {
			if (allowSb.length() != 0) {
				allowSb.append(", ");
			}
			allowSb.append("PUT");
		}
		if (allowDelete) {
			if (allowSb.length() != 0) {
				allowSb.append(", ");
			}
			allowSb.append("DELETE");
		}
		if (allowTrace) {
			if (allowSb.length() != 0) {
				allowSb.append(", ");
			}
			allowSb.append("TRACE");
		}
		if (allowOptions) {
			if (allowSb.length() != 0) {
				allowSb.append(", ");
			}
			allowSb.append("OPTIONS");
		}
		
		response().setHeader("Allow", allowSb.toString());
	}

	@Override
	protected final void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doOptions(request, response);
	}
	
	private static Method getDeclaredMethod(Class<?> clazz, Class<?> searchBreakClazz, String name, Class<?>... parameterTypes) {

		if (searchBreakClazz != null) {
			if (clazz.equals(searchBreakClazz)) {
				return null;
			}
		}
		
		Method method;
		try {
			method = clazz.getDeclaredMethod(name, parameterTypes);
		} catch (NoSuchMethodException e) {
			if (clazz.equals(Object.class)) {
				return null;
			}
			
			return getDeclaredMethod(clazz.getSuperclass(), searchBreakClazz, name, parameterTypes);
		}
		
		return method;
	}

	protected HttpReqRes reqres() {
		return this.localReqRes.get();
	}

	protected HttpServletRequest request() {
		return reqres().request();
	}

	protected HttpServletResponse response() {
		return reqres().response();
	}
	
	protected void forward(CharSequence path) {
		reqres().forward(path);
	}
	
	protected void redirect(CharSequence path) {
		reqres().redirect(path);
	}
}
