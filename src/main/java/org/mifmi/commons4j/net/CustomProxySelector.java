/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.net;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;

public class CustomProxySelector extends ProxySelector {
	
	private ProxySelector defaultProxySelector;
	
	private List<Proxy> proxyList;
	
	private List<String> exceptionHosts;

	public CustomProxySelector(List<Proxy> proxyList, List<String> exceptionHosts) {
		this.defaultProxySelector = ProxySelector.getDefault();
		this.proxyList = proxyList;
		this.exceptionHosts = exceptionHosts;
	}

	@Override
	public List<Proxy> select(URI uri) {
		if (NetUtilz.matchUri(uri, this.exceptionHosts) == null) {
			return this.proxyList;
		} else {
			return this.defaultProxySelector.select(uri);
		}
	}

	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		this.defaultProxySelector.connectFailed(uri, sa, ioe);
	}
}
