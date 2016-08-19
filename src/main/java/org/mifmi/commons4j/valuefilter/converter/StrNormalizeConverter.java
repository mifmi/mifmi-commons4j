/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.converter;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class StrNormalizeConverter extends AbstractConverter<CharSequence, String> {

	private Form form;
	
	public StrNormalizeConverter() {
		this(Form.NFC);
	}
	
	public StrNormalizeConverter(Form form) {
		this.form = form;
	}

	public <R extends String> R convert(CharSequence value) {
		return cast(convertRaw(value));
	}
	
	public Object convertObject(Object value) {
		
		CharSequence charSeq;
		if (value instanceof CharSequence) {
			charSeq = (CharSequence)value;
		} else {
			charSeq = value.toString();
		}
		
		return convertRaw(charSeq);
	}

	private String convertRaw(CharSequence value) {
		if (value == null) {
			return null;
		}
		
		CharSequence charSeq = value;
		
		return Normalizer.normalize(charSeq, this.form);
	}

	@Override
	public String toString() {
		return "form=" + this.form.name();
	}
}
