/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.app.valuefilter;

import java.util.Collection;

import org.mifmi.commons4j.app.message.Message;
import org.mifmi.commons4j.app.message.MessageManager;
import org.mifmi.commons4j.app.message.MifmiMessageException;
import org.mifmi.commons4j.valuefilter.InvalidValueException;
import org.mifmi.commons4j.valuefilter.ValueFilter;
import org.mifmi.commons4j.valuefilter.ValueFilters;
import org.mifmi.commons4j.valuefilter.validator.Validator;

public class MessageValueFilter {
	
	private ValueFilters filters;

	private Object value;
	
	private String labelMessageId;
	
	private MessageManager messageManager;
	
	public MessageValueFilter(Object value, String labelMessageId, MessageManager messageManager) {
		this.filters = new ValueFilters();
		this.value = value;
		this.labelMessageId = labelMessageId;
		this.messageManager = messageManager;
	}

	public MessageValueFilter add(ValueFilter<?, ?> filter) {
		this.filters.add(filter);
		return this;
	}

	public MessageValueFilter addAll(Collection<ValueFilter<?, ?>> filters) {
		this.filters.addAll(filters);
		return this;
	}
	
	public MessageValueFilter remove(ValueFilter<?, ?> filter) {
		this.filters.remove(filter);
		return this;
	}
	
	public int size() {
		return this.filters.size();
	}

	public <T> T value() {
		try {
			return this.filters.filter(this.value);
		} catch (InvalidValueException e) {
			String messageId = e.getValidationResult();
			String labelName = this.messageManager.loadMessage(this.labelMessageId);
			Object[] parameters = ((Validator<?>)e.getValueFilter()).getParameters();
			
			Object[] messageParams = new Object[2 + parameters.length];
			int idx = 0;
			messageParams[idx++] = labelName;
			messageParams[idx++] = e.getValue();
			for (Object parameter : parameters) {
				messageParams[idx++] = parameter;
			}
			
			Message message = this.messageManager.loadMessageObject(messageId, messageParams);
			
			throw new MifmiMessageException(message, e);
		}
	}
}
