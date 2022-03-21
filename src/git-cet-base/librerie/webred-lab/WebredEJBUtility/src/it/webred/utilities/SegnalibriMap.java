package it.webred.utilities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

public class SegnalibriMap extends HashMap<String, String> implements Serializable {

	private static final long serialVersionUID = 1L;

	public String replaceTokens(String source) {

		if (source != null) {
			for (Iterator<String> iterator = this.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String value = StringUtils.trimToEmpty(this.get(key));

				source = source.replace(key, value);
			}
		}

		return source;
	}
}
