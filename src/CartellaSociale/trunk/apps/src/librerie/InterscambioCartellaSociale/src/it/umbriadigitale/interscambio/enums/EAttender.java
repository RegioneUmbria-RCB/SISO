package it.umbriadigitale.interscambio.enums;

public enum EAttender {
	// usati in "Valutazione del bisogno"
	RESPONSABILE_VALUTAZIONE	("2.16.840.1.113883.2.9.4.3.2", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.1"),
	CASE_MANAGER				("2.16.840.1.113883.2.9.4.3.2", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.18"),
	
	// usati in "Elaborazione del progetto individuale"
	RESPONSABILE_PROGETTO		("2.16.840.1.113883.2.9.4.3.2", "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.2"),
	RESPONSABILE_MONITORAGGIO	("2.16.840.1.113883.2.9.4.3.2", "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.3"),
	
	// usati in "Valutazione finale e conclusione del processo di aiuto"
	RESPONSABILE_CHIUSURA		("2.16.840.1.113883.2.9.4.3.2", null)
	;
	
	private String id_Root;
	private String code_CodeSystem;
	
	
	private EAttender(String id_Root, String code_CodeSystem) {
		this.id_Root = id_Root;
		this.code_CodeSystem = code_CodeSystem;
	}
	
	
	public String getId_Root() {
		return id_Root;
	}

	public String getCode_CodeSystem() {
		return code_CodeSystem;
	}
}
