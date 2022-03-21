package it.umbriadigitale.interscambio.enums;

public enum EPersonalRelationship {
	NUCLEO_FAMILIARE("2.16.840.1.113883.2.9.2.30.3.2.3.1.1.22"),
	RETE_SOCIALE("2.16.840.1.113883.2.9.2.30.3.2.3.1.1.23");
	
	
	private String code_CodeSystem;
	
	
	private EPersonalRelationship(String code_CodeSystem) {
		this.code_CodeSystem = code_CodeSystem;
	}
	
	
	public String getCode_CodeSystem() {
		return code_CodeSystem;
	}
}
