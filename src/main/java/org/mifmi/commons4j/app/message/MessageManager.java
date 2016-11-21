/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.app.message;

import org.mifmi.commons4j.config.Config;
import org.mifmi.commons4j.util.StringUtilz;

public class MessageManager {
	
	public static final String DEFAULT_LEVEL_KEY_PATTERN = "{0}.level";
	public static final String DEFAULT_MESSAGE_KEY_PATTERN = "{0}";
	public static final String DEFAULT_DETAIL_KEY_PATTERN = "{0}.detail";

	private Config messageConfig;
	private String levelKeyPattern;
	private String messageKeyPattern;
	private String detailKeyPattern;
	
	public MessageManager(Config messageConfig) {
		this.messageConfig = messageConfig;
		this.levelKeyPattern = DEFAULT_LEVEL_KEY_PATTERN;
		this.messageKeyPattern = DEFAULT_MESSAGE_KEY_PATTERN;
		this.detailKeyPattern = DEFAULT_DETAIL_KEY_PATTERN;
	}

	public Config getMessageConfig() {
		return messageConfig;
	}

	public String loadMessage(String messageKey) {
		return this.messageConfig.getAsString(messageKey);
	}

	public String loadMessage(String messageKey, String defaultValue) {
		return this.messageConfig.getAsString(messageKey, defaultValue);
	}

	public String loadMessage(String messageKey, Object... params) {
		return StringUtilz.format(this.messageConfig.getAsString(messageKey), params);
	}

	public String loadMessage(String messageKey, String defaultValue, Object... params) {
		return StringUtilz.format(this.messageConfig.getAsString(messageKey, defaultValue), params);
	}

	public Message loadMessageObject(String messageId, Object... params) {
		
		String levelKey = StringUtilz.format(this.levelKeyPattern, messageId);
		String messageKey = StringUtilz.format(this.messageKeyPattern, messageId);
		String detailKey = StringUtilz.format(this.detailKeyPattern, messageId);
		
		Message message = new Message();
		message.setMessageId(messageId);
		message.setLevel(loadMessage(levelKey));
		message.setMessage(loadMessage(messageKey, params));
		message.setDetail(loadMessage(detailKey, (String)null, params));
		
		return message;
	}
}
