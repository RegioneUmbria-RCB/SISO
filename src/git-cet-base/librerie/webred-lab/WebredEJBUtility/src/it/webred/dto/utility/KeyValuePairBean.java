package it.webred.dto.utility;

/**
 * @author Andrea
 *
 */
public class KeyValuePairBean<TKEY, TVALUE> {

	private TKEY key;
	private TVALUE value;
	
	public KeyValuePairBean(TKEY key, TVALUE value) {
		this.key = key;
		this.value = value;
	}
	
	public TKEY getKey() {
		return key;
	}
	public void setKey(TKEY key) {
		this.key = key;
	}
	public TVALUE getValue() {
		return value;
	}
	public void setValue(TVALUE value) {
		this.value = value;
	}
}

