package it.umbriadigitale.interscambio.data.wrapper;

public class AttenderWrapper {
	private String id;
	private String code;
	
	
	public AttenderWrapper(String id, String code) {
		this.id = id;
		this.code = code;
	}
	
	
	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
}
