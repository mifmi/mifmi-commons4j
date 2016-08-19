/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.app.web.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.mifmi.commons4j.app.message.Message;
import org.mifmi.commons4j.app.message.MessageManager;
import org.mifmi.commons4j.app.message.MifmiMessageException;
import org.mifmi.commons4j.app.valuefilter.MessageValueFilter;
import org.mifmi.commons4j.config.Config;
import org.mifmi.commons4j.config.ResourceBundleConfig;
import org.mifmi.commons4j.valuefilter.ValueFilter;

public abstract class AbstractBasicHttpServlet extends AbstractHttpServlet {

	private static final long serialVersionUID = 1L;

	private ThreadLocal<String> localConfigResourceName = new ThreadLocal<String>();
	
	private ThreadLocal<String> localMessagesResourceName = new ThreadLocal<String>();
	
	private ThreadLocal<Config> localConfig = null;
	
	private ThreadLocal<MessageManager> localMessageManager = null;
	
	private ThreadLocal<String> localResponseMessagesAttributeName = new ThreadLocal<String>();
	
	private ThreadLocal<List<ValueFilter<?, ?>>> localFilters = new ThreadLocal<List<ValueFilter<?, ?>>>();

	
	protected abstract boolean handleError(Throwable e, String level) throws Exception;
	

	@Override
	protected boolean handleError(Throwable e) throws Exception {
		Message message;
		if (e instanceof MifmiMessageException) {
			message = ((MifmiMessageException)e).getMessageObject();
		} else {
			try {
				message = messageObject("default.error");
			} catch (Exception e1) {
				message = null;
			}
		}
		if (message != null) {
			addResponseMessage(message);
		}
		return handleError(e, (message == null)? null : message.getLevel());
	}
	
	protected Locale locale() {
		return request().getLocale();
	}
	
	protected List<Locale> locales() {
		return Collections.list(request().getLocales());
	}
	
	protected void useConfigResource(String resourceName, String attributeName) {
		this.localConfigResourceName.set(resourceName);
		this.localConfig = new ThreadLocal<Config>();
		this.localConfig.set(null);
		
		if (attributeName != null) {
			reqres().setAttribute(attributeName, config().asMap());
		}
	}
	
	protected void useMessagesResource(String resourceName, String attributeName) {
		this.localMessagesResourceName.set(resourceName);
		this.localMessageManager = new ThreadLocal<MessageManager>();
		this.localMessageManager.set(null);

		if (attributeName != null) {
			reqres().setAttribute(attributeName, messageManager().getMessageConfig().asMap());
		}
	}
	
	protected void setResponseMessagesAttributeName(String attributeName) {
		this.localResponseMessagesAttributeName.set(attributeName);
	}
	
	protected String getResponseMessagesAttributeName() {
		return this.localResponseMessagesAttributeName.get();
	}
	
	protected Config config() {
		Config applicationConfig = this.localConfig.get();
		if (applicationConfig == null) {
			String resourceName = this.localConfigResourceName.get();
			if (resourceName == null) {
				throw new IllegalStateException();
			}
			applicationConfig = new ResourceBundleConfig(resourceName, locales());
			this.localConfig.set(applicationConfig);
		}
		return applicationConfig;
	}
	
	protected MessageManager messageManager() {
		if (this.localMessageManager == null) {
			throw new IllegalStateException("Please call #useMessagesResource method before access messages resource.");
		}
		MessageManager messageManager = this.localMessageManager.get();
		if (messageManager == null) {
			String resourceName = this.localMessagesResourceName.get();
			if (resourceName == null) {
				throw new IllegalStateException();
			}
			Config messagesConfig = new ResourceBundleConfig(resourceName, locales());
			messageManager = new MessageManager(messagesConfig);
			this.localMessageManager.set(messageManager);
		}
		return messageManager;
	}
	
	protected String message(String messageId) {
		return messageManager().loadMessage(messageId);
	}
	
	protected String message(String messageId, Object... params) {
		return messageManager().loadMessage(messageId, params);
	}
	
	protected Message messageObject(String messageId, Object... params) {
		return messageManager().loadMessageObject(messageId, params);
	}
	
	private List<ValueFilter<?, ?>> getDefaultFilters() {
		List<ValueFilter<?, ?>> filters = this.localFilters.get();
		if (filters == null) {
			filters = new ArrayList<ValueFilter<?, ?>>();
			this.localFilters.set(filters);
		}
		return filters;
		
	}
	
	protected void addDefaultFilter(ValueFilter<?, ?> filter) {
		getDefaultFilters().add(filter);
	}
	
	protected MessageValueFilter filter(Object value, String labelMessageId) {
		MessageValueFilter filter = new MessageValueFilter(value, labelMessageId, messageManager());
		filter.addAll(getDefaultFilters());
		return filter;
	}
	
	protected List<Message> getResponseMessages() {
		String attributeName = getResponseMessagesAttributeName();
		if (attributeName == null) {
			throw new IllegalStateException();
		}
		
		List<Message> messages = reqres().attribute(attributeName);
		if (messages == null) {
			messages = new ArrayList<Message>();
			reqres().setAttribute(attributeName, messages);
		}
		return messages;
	}
	
	protected void addResponseMessage(Message message) {
		getResponseMessages().add(message);
	}
	
	protected void addResponseMessage(String messageId, Object... params) {
		addResponseMessage(messageObject(messageId, params));
	}
}
