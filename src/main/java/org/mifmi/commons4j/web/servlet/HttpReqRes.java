/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import org.mifmi.commons4j.util.BooleanUtilz;
import org.mifmi.commons4j.util.NumberUtilz;
import org.mifmi.commons4j.util.StringUtilz;

public class HttpReqRes {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private boolean isMultipartRequest;
	private Map<String, String[]> parameterMap;
	private Map<String, Part[]> parameterPartMap;

	public HttpReqRes(HttpServletRequest req, HttpServletResponse res) {
		this.request = req;
		this.response = res;
		
		String contentType = req.getContentType();
		this.isMultipartRequest = (contentType != null && contentType.startsWith("multipart/form-data"));
		
		this.parameterMap = null;
		this.parameterPartMap = null;
	}


	public HttpServletRequest request() {
		return this.request;
	}

	public HttpServletResponse response() {
		return this.response;
	}
	
	public boolean isMultipartRequest() {
		return this.isMultipartRequest;
	}
	
	public String header(CharSequence name) {
		return request().getHeader(name.toString());
	}
	
	public Locale getLocale(Locale defaultLocale) {
		if (this.request.getHeader("Accept-Language") == null) {
			return defaultLocale;
		} else {
			return this.request.getLocale();
		}
	}
	
	public Enumeration<Locale> getLocales(Locale defaultLocale) {
		if (this.request.getHeader("Accept-Language") == null) {
			List<Locale> defaultLocales = new ArrayList<Locale>(1);
			if (defaultLocale != null) {
				defaultLocales.add(defaultLocale);
			}
			return Collections.enumeration(defaultLocales);
		} else {
			return this.request.getLocales();
		}
	}
	
	public String[] headers(CharSequence name) {
		Enumeration<String> headers = request().getHeaders(name.toString());
		
		int num = 0;
		while (headers.hasMoreElements()) {
			num++;
		}
		
		String[] headersArray = new String[num];
		int idx = 0;
		while (headers.hasMoreElements()) {
			headersArray[idx++] = headers.nextElement();
		}
		
		return headersArray;
	}
	
	public Set<String> headerNames() {
		Enumeration<String> headerNames = request().getHeaderNames();
		
		int num = 0;
		while (headerNames.hasMoreElements()) {
			num++;
		}
		
		Set<String> headerNamesSet = new LinkedHashSet<String>(num);
		while (headerNames.hasMoreElements()) {
			headerNamesSet.add(headerNames.nextElement());
		}
		
		return headerNamesSet;
	}

	private Map<String, String[]> paramMap() {
		if (this.parameterMap == null) {
			this.parameterMap = Collections.unmodifiableMap(request().getParameterMap());
		}
		return this.parameterMap;
	}
	
	public Set<String> paramNames() {
//		if (isMultipartRequest()) {
//			return paramPartNames();
//		} else {
			return paramMap().keySet();
//		}
	}
	
	public boolean hasParam(CharSequence name) {
//		if (isMultipartRequest()) {
//			return hasParamPart(name);
//		} else {
			if (name == null) {
				throw new NullPointerException("The name parameter must not be null.");
			}
			
			String n = name.toString();
			if (paramMap().containsKey(n)) {
				return true;
			}
			for (String key : paramMap().keySet()) {
				if (key.equalsIgnoreCase(n)) {
					return true;
				}
			}
			
			return false;
//		}
	}

	public String param(CharSequence name) {
		if (isMultipartRequest()) {
			return paramPartAsString(name);
		} else {
			if (name == null) {
				throw new NullPointerException("The name parameter must not be null.");
			}
			
			if (!hasParam(name)) {
				throw new MifmiServletParameterNotFoundException(name.toString());
			}
			return request().getParameter(name.toString());
		}
	}
	public String param(CharSequence name, String defaultValue) {
		if (isMultipartRequest()) {
			return paramPartAsString(name, defaultValue);
		} else {
			if (name == null) {
				throw new NullPointerException("The name parameter must not be null.");
			}
			
			if (!hasParam(name)) {
				return defaultValue;
			}
			return request().getParameter(name.toString());
		}
	}

	public int paramAsInt(CharSequence name) {
		String param = param(name);
		return parseParamAsInt(name, param);
	}
	public int paramAsInt(CharSequence name, int defaultValue) {
		String param = param(name, null);
		if (param == null) {
			return defaultValue;
		}

		return parseParamAsInt(name, param);
	}

	public Integer paramAsIntegerObject(CharSequence name) {
		return paramAsIntegerObject(name, null);
	}
	public Integer paramAsIntegerObject(CharSequence name, Integer defaultValue) {
		String param = param(name, null);
		if (param == null) {
			return defaultValue;
		}

		return parseParamAsIntegerObject(name, param);
	}

	public long paramAsLong(CharSequence name) {
		String param = param(name);
		return parseParamAsLong(null, param);
	}
	public long paramAsLong(CharSequence name, long defaultValue) {
		String param = param(name, null);
		if (param == null) {
			return defaultValue;
		}

		return parseParamAsLong(name, param);
	}

	public Long paramAsLongObject(CharSequence name) {
		return paramAsLongObject(name, null);
	}
	public Long paramAsLongObject(CharSequence name, Long defaultValue) {
		String param = param(name, null);
		if (param == null) {
			return defaultValue;
		}

		return parseParamAsLongObject(name, param);
	}

	public double paramAsDouble(CharSequence name) {
		String param = param(name);
		return parseParamAsDouble(name, param);
	}
	public double paramAsDouble(CharSequence name, double defaultValue) {
		String param = param(name, null);
		if (param == null) {
			return defaultValue;
		}

		return parseParamAsDouble(name, param);
	}

	public Double paramAsDoubleObject(CharSequence name) {
		return paramAsDoubleObject(name, null);
	}
	public Double paramAsDoubleObject(CharSequence name, Double defaultValue) {
		String param = param(name, null);
		if (param == null) {
			return defaultValue;
		}

		return parseParamAsDoubleObject(name, param);
	}

	public boolean paramAsBoolean(CharSequence name) {
		String param = param(name);
		return parseParamAsBoolean(name, param);
	}
	public boolean paramAsBoolean(CharSequence name, boolean defaultValue) {
		String param = param(name, null);
		if (param == null) {
			return defaultValue;
		}

		return parseParamAsBoolean(name, param);
	}

	public Boolean paramAsBooleanObject(CharSequence name) {
		return paramAsBooleanObject(name, null);
	}
	public Boolean paramAsBooleanObject(CharSequence name, Boolean defaultValue) {
		String param = param(name, null);
		if (param == null) {
			return defaultValue;
		}

		return parseParamAsBooleanObject(name, param);
	}

	public String[] params(CharSequence name) {
		if (isMultipartRequest()) {
			return paramPartsAsString(name);
		} else {
			if (name == null) {
				throw new NullPointerException("The name parameter must not be null.");
			}
			
			if (!hasParam(name)) {
				throw new MifmiServletParameterNotFoundException(name.toString());
			}
			return request().getParameterValues(name.toString());
		}
	}
	public String[] params(CharSequence name, String[] defaultValue) {
		if (isMultipartRequest()) {
			return paramPartsAsString(name, defaultValue);
		} else {
			if (name == null) {
				throw new NullPointerException("The name parameter must not be null.");
			}
			
			if (!hasParam(name)) {
				return defaultValue;
			}
			return request().getParameterValues(name.toString());
		}
	}
	
	public Collection<Part> paramParts() {
		Collection<Part> parts;
		try {
			parts = request().getParts();
		} catch (IOException e) {
			throw new MifmiServletException(e);
		} catch (ServletException e) {
			throw new MifmiServletException(e);
		}
		return parts;
	}
	
	private Map<String, Part[]> paramPartMap() {
		if (this.parameterPartMap == null) {
			Collection<Part> paramParts = paramParts();
			Map<String, Part[]> tempMap = new LinkedHashMap<>(paramParts.size());
			for (Part paramPart : paramParts) {
				String name = paramPart.getName();
				Part[] parts = tempMap.get(name);
				if (parts == null) {
					parts = new Part[1];
					parts[0] = paramPart;
				} else {
					Part[] newParts = new Part[parts.length + 1];
					System.arraycopy(parts, 0, newParts, 0, parts.length);
					newParts[parts.length] = paramPart;
					parts = newParts;
				}
				tempMap.put(name, parts);
			}
			this.parameterPartMap = Collections.unmodifiableMap(tempMap);
		}
		return this.parameterPartMap;
	}
	
	public Set<String> paramPartNames() {
		return paramPartMap().keySet();
	}
	
	public boolean hasParamPart(CharSequence name) {
		if (name == null) {
			throw new NullPointerException("The name parameter must not be null.");
		}
		
		String n = name.toString();
		if (paramPartMap().containsKey(n)) {
			return true;
		}
		for (String key : paramPartMap().keySet()) {
			if (key.equalsIgnoreCase(n)) {
				return true;
			}
		}
		return false;
	}
	public Part paramPart(CharSequence name) {
		Part part = paramPart(name, null);
		
		if (part == null) {
			throw new MifmiServletParameterNotFoundException(name.toString());
		}
		
		return part;
	}
	
	public Part paramPart(CharSequence name, Part defaultValue) {
		if (name == null) {
			throw new NullPointerException("The name parameter must not be null.");
		}
		
		if (!hasParamPart(name)) {
			return defaultValue;
		}
		
		Part part;
		try {
			part = request().getPart(name.toString());
		} catch (IOException e) {
			throw new MifmiServletException(e);
		} catch (ServletException e) {
			throw new MifmiServletException(e);
		}
		return part;
	}
	
	public Part[] paramParts(CharSequence name) {
		if (name == null) {
			throw new NullPointerException("The name parameter must not be null.");
		}
		
		String n = name.toString();
		Part[] parts = paramPartMap().get(n);
		if (parts != null) {
			return parts;
		}
		for (Map.Entry<String, Part[]> keyValue : paramPartMap().entrySet()) {
			if (keyValue.getKey().equalsIgnoreCase(n)) {
				return keyValue.getValue();
			}
		}

		throw new MifmiServletParameterNotFoundException(name.toString());
	}
	
	public Part[] paramParts(CharSequence name, Part[] defaultValue) {
		if (name == null) {
			throw new NullPointerException("The name parameter must not be null.");
		}

		String n = name.toString();
		Part[] parts = paramPartMap().get(n);
		if (parts != null) {
			return parts;
		}
		for (Map.Entry<String, Part[]> keyValue : paramPartMap().entrySet()) {
			if (keyValue.getKey().equalsIgnoreCase(n)) {
				return keyValue.getValue();
			}
		}

		return defaultValue;
	}

	private String paramPartAsString(CharSequence name) {
		return paramPartAsString(name, Integer.MAX_VALUE);
	}

	private String paramPartAsString(CharSequence name, int maxLength) {
		Part part = paramPart(name);
		String charset = request().getCharacterEncoding();
		String val = getPartAsString(part, charset, maxLength);
		return val;
	}

	private String paramPartAsString(CharSequence name, String defaultValue) {
		return paramPartAsString(name, defaultValue, Integer.MAX_VALUE);
	}

	private String paramPartAsString(CharSequence name, String defaultValue, int maxLength) {
		Part part = paramPart(name, null);
		if (part == null) {
			return defaultValue;
		}
		String charset = request().getCharacterEncoding();
		String val = getPartAsString(part, charset, maxLength);
		return val;
	}

	private String[] paramPartsAsString(CharSequence name) {
		return paramPartsAsString(name, Integer.MAX_VALUE);
	}
	
	private String[] paramPartsAsString(CharSequence name, int maxLength) {
		Part[] parts = paramParts(name);
		String charset = request().getCharacterEncoding();
		String[] vals = new String[parts.length];
		for (int i = 0; i < parts.length; i++) {
			String val = getPartAsString(parts[i], charset, maxLength);
			vals[i] = val;
		}
		return vals;
	}

	private String[] paramPartsAsString(CharSequence name, String[] defaultValue) {
		return paramPartsAsString(name, defaultValue, Integer.MAX_VALUE);
	}
	
	private String[] paramPartsAsString(CharSequence name, String[] defaultValue, int maxLength) {
		Part[] parts = paramParts(name, null);
		if (parts == null) {
			return defaultValue;
		}
		String charset = request().getCharacterEncoding();
		String[] vals = new String[parts.length];
		for (int i = 0; i < parts.length; i++) {
			String val = getPartAsString(parts[i], charset, maxLength);
			vals[i] = val;
		}
		return vals;
	}

	public String paramPartFileName(CharSequence name) {
		Part part = paramPart(name);
		String fileName = getPartFileName(part);
		return fileName;
	}

	public static String getPartAsString(Part part, String charset, int maxLength) {
		InputStream in = null;
		try {
			int len = (int)Math.min(part.getSize(), (long)maxLength);
			if (len <= 0) {
				return "";
			}
			in = part.getInputStream();
			byte[] buf = new byte[len];
			int loadLen = in.read(buf);
			String val = new String(buf, 0, loadLen, charset);
			return val;
		} catch (IOException e) {
			throw new MifmiServletException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new MifmiServletException(e);
				}
			}
		}
	}

	public static String getPartFileName(Part part) {
		if (part == null) {
			return null;
		}
		String contentDiposition = part.getHeader("Content-Disposition");
		String[] contentDipositionParts = contentDiposition.split(";\\s*");
		String fileName = null;
		for (String contentDipositionPart : contentDipositionParts) {
			if (contentDipositionPart.startsWith("filename")) {
				int idx = contentDipositionPart.indexOf('=');
				if (idx == -1) {
					continue;
				}
				fileName = contentDipositionPart.substring(idx + 1).trim();
				int len = fileName.length();
				if (0 < len) {
					int beginIdx = (fileName.charAt(0) == '"') ? 1 : 0;
					int endIdx = len - ((fileName.charAt(len - 1) == '"') ? 1 : 0);
					fileName = fileName.substring(beginIdx, endIdx);
					fileName = StringUtilz.unescape(fileName, '\\', new char[]{'"'}, null);
				}
			}
		}
		return fileName;
	}
	
	public static boolean isEmptyPartFile(Part part) {
		if (part == null) {
			return true;
		}
		String fileName = getPartFileName(part);
		if (fileName == null || fileName.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public String pathInfo() {
		return request().getPathInfo();
	}

	public String pathParam() {
		String value = pathParam(null);
		if (value == null) {
			throw new MifmiServletParameterNotFoundException();
		}
		
		return value;
	}
	public String pathParam(String defaultValue) {
		String pathInfo = pathInfo();
		if (pathInfo == null || pathInfo.isEmpty() || pathInfo.equals("/")) {
			return defaultValue;
		}
		
		String pathParam;
		if (pathInfo.startsWith("/")) {
			pathParam = pathInfo.substring(1);
		} else {
			pathParam = pathInfo;
		}
		
		return pathParam;
	}

	public String[] pathParams() {
		String[] pathParams = pathParams(null);
		if (pathParams == null) {
			throw new MifmiServletParameterNotFoundException();
		}
		
		return pathParams;
	}
	public String[] pathParams(String[] defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}
		
		String[] pathParams = StringUtilz.split(pathParam, '/');
		
		return pathParams;
	}

	public int pathParamAsInt() {
		String pathParam = pathParam();
		return parseParamAsInt(null, pathParam);
	}
	public int pathParamAsInt(int defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}

		return parseParamAsInt(null, pathParam);
	}

	public Integer pathParamAsIntegerObject() {
		return pathParamAsIntegerObject(null);
	}
	public Integer pathParamAsIntegerObject(Integer defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}

		return parseParamAsIntegerObject(null, pathParam);
	}

	public long pathParamAsLong() {
		String pathParam = pathParam();
		return parseParamAsLong(null, pathParam);
	}
	public long pathParamAsLong(long defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}

		return parseParamAsLong(null, pathParam);
	}

	public Long pathParamAsLongObject() {
		return pathParamAsLongObject(null);
	}
	public Long pathParamAsLongObject(Long defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}

		return parseParamAsLongObject(null, pathParam);
	}

	public double pathParamAsDouble() {
		String pathParam = pathParam();
		return parseParamAsDouble(null, pathParam);
	}
	public double pathParamAsDouble(double defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}

		return parseParamAsDouble(null, pathParam);
	}

	public Double pathParamAsDoubleObject() {
		return pathParamAsDoubleObject(null);
	}
	public Double pathParamAsDoubleObject(Double defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}

		return parseParamAsDoubleObject(null, pathParam);
	}

	public boolean pathParamAsBoolean() {
		String pathParam = pathParam();
		return parseParamAsBoolean(null, pathParam);
	}
	public boolean pathParamAsBoolean(boolean defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}

		return parseParamAsBoolean(null, pathParam);
	}

	public Boolean pathParamAsBooleanObject() {
		return pathParamAsBooleanObject(null);
	}
	public Boolean pathParamAsBooleanObject(Boolean defaultValue) {
		String pathParam = pathParam(null);
		if (pathParam == null) {
			return defaultValue;
		}

		return parseParamAsBooleanObject(null, pathParam);
	}
	

	public String cookie(CharSequence name) {
		return cookie(name, null);
	}
	public String cookie(CharSequence name, String defaultValue) {
		return cookie(name, defaultValue, false);
	}
	public String cookie(CharSequence name, String defaultValue, boolean rawValue) {
		String nameStr = name.toString();
		Cookie[] cookies = request().getCookies();
		if (cookies == null) {
			return defaultValue;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(nameStr)) {
				String value = cookie.getValue();
				if (rawValue) {
					return value;
				} else {
					if (value == null || value.isEmpty()) {
						return value;
					}
					try {
						return URLDecoder.decode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						return value;
					}
				}
			}
		}
		return defaultValue;
	}
	
	public void setCookie(CharSequence name, CharSequence value) {
		setCookie(name, value, -1);
	}

	public void setCookie(CharSequence name, CharSequence value, int expiry) {
		setCookie(name, value, expiry, false, false);
	}
	
	public void setCookie(CharSequence name, CharSequence value, int expiry, String path, String domain) {
		setCookie(name, value, expiry, false, false, path, domain);
	}
	
	public void setCookie(CharSequence name, CharSequence value, int expiry, boolean httpOnly, boolean secure) {
		setCookie(name, value, expiry, httpOnly, secure, null, null);
	}
	
	public void setCookie(CharSequence name, CharSequence value, int expiry, boolean httpOnly, boolean secure, String path, String domain) {
		Cookie cookie = new Cookie(name.toString(), (value == null) ? "" : value.toString());
		cookie.setMaxAge(expiry);
		cookie.setHttpOnly(httpOnly);
		cookie.setSecure(secure);
		if (path != null) {
			cookie.setPath(path);
		}
		if (domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setVersion(0);
		setCookie(cookie);
	}
	
	public void setCookie(Cookie cookie) {
		response().addCookie(cookie);
	}
	
	public void removeCookie(CharSequence name) {
		setCookie(name, "", 0);
	}
	
	public HttpSession getSession(boolean create) {
		return request().getSession(create);
	}
	
	public <T> T session(CharSequence name) {
		return (T)getSession(true).getAttribute(name.toString());
	}
	
	public void setSession(CharSequence name, Object value) {
		getSession(true).setAttribute(name.toString(), value);
	}
	
	public void removeSession(CharSequence name) {
		getSession(true).removeAttribute(name.toString());
	}
	
	public <T> T attribute(CharSequence name) {
		return (T)request().getAttribute(name.toString());
	}
	
	public void setAttribute(CharSequence name, Object value) {
		request().setAttribute(name.toString(), value);
	}
	
	public void removeAttribute(CharSequence name) {
		request().removeAttribute(name.toString());
	}
	
	public void forward(CharSequence path) {
		try {
			request().getRequestDispatcher(path.toString()).forward(request(), response());
		} catch (ServletException e) {
			throw new MifmiServletException(e);
		} catch (IOException e) {
			throw new MifmiServletException(e);
		}
	}
	
	public void redirect(CharSequence path) {
		try {
			response().sendRedirect(path.toString());
		} catch (IOException e) {
			throw new MifmiServletException(e);
		}
	}
	

	private int parseParamAsInt(CharSequence paramName, Object value) {
		try {
			return NumberUtilz.toInt(value);
		} catch (Exception e) {
			if (paramName == null) {
				throw new MifmiServletParameterParseException(e);
			} else {
				throw new MifmiServletParameterParseException(paramName.toString(), e);
			}
		}
	}
	
	private Integer parseParamAsIntegerObject(CharSequence paramName, Object value) {
		try {
			return NumberUtilz.toIntegerObject(value);
		} catch (Exception e) {
			if (paramName == null) {
				throw new MifmiServletParameterParseException(e);
			} else {
				throw new MifmiServletParameterParseException(paramName.toString(), e);
			}
		}
	}
	
	private long parseParamAsLong(CharSequence paramName, Object value) {
		try {
			return NumberUtilz.toLong(value);
		} catch (Exception e) {
			if (paramName == null) {
				throw new MifmiServletParameterParseException(e);
			} else {
				throw new MifmiServletParameterParseException(paramName.toString(), e);
			}
		}
	}
	
	private Long parseParamAsLongObject(CharSequence paramName, Object value) {
		try {
			return NumberUtilz.toLongObject(value);
		} catch (Exception e) {
			if (paramName == null) {
				throw new MifmiServletParameterParseException(e);
			} else {
				throw new MifmiServletParameterParseException(paramName.toString(), e);
			}
		}
	}
	
	private double parseParamAsDouble(CharSequence paramName, Object value) {
		try {
			return NumberUtilz.toDouble(value);
		} catch (Exception e) {
			if (paramName == null) {
				throw new MifmiServletParameterParseException(e);
			} else {
				throw new MifmiServletParameterParseException(paramName.toString(), e);
			}
		}
	}
	
	private Double parseParamAsDoubleObject(CharSequence paramName, Object value) {
		try {
			return NumberUtilz.toDoubleObject(value);
		} catch (Exception e) {
			if (paramName == null) {
				throw new MifmiServletParameterParseException(e);
			} else {
				throw new MifmiServletParameterParseException(paramName.toString(), e);
			}
		}
	}
	
	private boolean parseParamAsBoolean(CharSequence paramName, Object value) {
		try {
			return BooleanUtilz.toBoolean(value);
		} catch (Exception e) {
			if (paramName == null) {
				throw new MifmiServletParameterParseException(e);
			} else {
				throw new MifmiServletParameterParseException(paramName.toString(), e);
			}
		}
	}
	
	private boolean parseParamAsBooleanObject(CharSequence paramName, Object value) {
		try {
			return BooleanUtilz.toBooleanObject(value);
		} catch (Exception e) {
			if (paramName == null) {
				throw new MifmiServletParameterParseException(e);
			} else {
				throw new MifmiServletParameterParseException(paramName.toString(), e);
			}
		}
	}
}
