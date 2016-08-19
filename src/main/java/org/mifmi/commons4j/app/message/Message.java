/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.app.message;

import java.util.Map;

public class Message {

	private String messageId;
	private String level;
	private String message;
	private String detail;
	private Map<String, Object> extInfo;

	public Message() {
		this(null, null, null, null);
	}
	
	public Message(String messageId, String level, String summary, String detail) {
		this(messageId, level, summary, detail, null);
	}
	
	public Message(String messageId, String level, String summary, String detail, Map<String, Object> extInfo) {
		this.messageId = messageId;
		this.level = level;
		this.message = summary;
		this.detail = detail;
		this.extInfo = extInfo;
	}
	
	public String getMessageId() {
		return messageId;
	}
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Map<String, Object> getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(Map<String, Object> extInfo) {
		this.extInfo = extInfo;
	}

	@Override
	public String toString() {
		return "messageId=" + this.messageId
				+ ", level=" + this.level
				+ ", message=" + this.message
				+ ", detail=" + this.detail
				+ ", extInfo=" + this.extInfo;
	}
}
