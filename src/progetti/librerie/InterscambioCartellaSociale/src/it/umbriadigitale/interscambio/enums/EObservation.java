package it.umbriadigitale.interscambio.enums;

public enum EObservation {
	// usati in "Elaborazione del progetto individuale"
	FREQUENZA_PRESTAZIONI					(EObservationElementContentType.CODE_VALUE, "FRPR", "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.4", EObservationValueType.ANY),
	SERVIZIO_SOCIALE_PROFESSIONALE			(EObservationElementContentType.CODE_VALUE, "SESP", "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.5", EObservationValueType.ANY),
	INTEGRAZIONE_CON_ASSISTENZA_SANITARIA	(EObservationElementContentType.CODE_VALUE, "INAS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.6", EObservationValueType.ANY),
	VALORE_PROGETTO							(EObservationElementContentType.CODE_VALUE, "VAPR", "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.7", EObservationValueType.MO),
	COMPARTECIPAZIONE_ECONOMICA_ASSISTITO	(EObservationElementContentType.CODE_VALUE, "COEA", "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.8", EObservationValueType.MO),
	
	// usati in "Erogazione del servizio"
	UDO_SOCIALE								(EObservationElementContentType.CODE_VALUE, "UDOS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.1", EObservationValueType.CD),
	SOGGETTO_EROGATORE						(EObservationElementContentType.CODE_VALUE, "SOER", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.3", EObservationValueType.CD),
	MODALITA_EROGAZIONE						(EObservationElementContentType.CODE_VALUE, "MOER", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.4", EObservationValueType.CD),
	AREA_INTERVENTO_SOCIALE					(EObservationElementContentType.CODE_VALUE, "ARIS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.5", EObservationValueType.CD),
	SERVIZIO_EROGATO						(EObservationElementContentType.CODE_VALUE, "SEER", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.6", EObservationValueType.CD),
	DATA_ATTIVAZIONE_SERVIZIO				(EObservationElementContentType.CODE_VALUE, "DAAS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.7", EObservationValueType.ANY),
	DATA_CONCLUSIONE_SERVIZIO				(EObservationElementContentType.CODE_VALUE, "DACS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.8", EObservationValueType.ANY),
	DATA_INIZIO_SOSPENSIONE					(EObservationElementContentType.CODE_VALUE, "DAIS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.9", EObservationValueType.ANY),
	DATA_FINE_SOSPENSIONE					(EObservationElementContentType.CODE_VALUE, "DAFS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.10", EObservationValueType.ANY),
	MOTIVAZIONE_SOSPENSIONE					(EObservationElementContentType.CODE_VALUE, "MOSO", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.11", EObservationValueType.CD),
	VALORE_MENSILE_BUONO_SOCIALE			(EObservationElementContentType.CODE_VALUE, "VMBS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.12", EObservationValueType.MO),
	TIPOLOGIA_VOUCHER_SOCIALE				(EObservationElementContentType.CODE_VALUE, "TIVS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.13", EObservationValueType.CD),
	VALORE_VOUCHER							(EObservationElementContentType.CODE_VALUE, "VAVO", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.14", EObservationValueType.MO),
	IMPORTO_EROGATO							(EObservationElementContentType.CODE_VALUE, "IMER", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.15", EObservationValueType.MO),
	RIPROGRAMMAZIONE_RISORSE_ECONOMICHE		(EObservationElementContentType.CODE_VALUE, "RIRE", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.16", EObservationValueType.MO),
	
	// usati in "Valutazione finale e conclusione del processo di aiuto"
	DATA_VALUTAZIONE_FINALE					(EObservationElementContentType.CODE_VALUE, "DAVF", "2.16.840.1.113883.2.9.2.30.3.2.3.1.5.1", EObservationValueType.ANY),
	VALUTAZIONE_FINALE						(EObservationElementContentType.CODE_TEXT, "VAFI", "2.16.840.1.113883.2.9.2.30.3.2.3.1.5.2", null),
	MOTIVAZIONE_CHIUSURA_PROGETTO			(EObservationElementContentType.CODE_VALUE, "MOCP", "2.16.840.1.113883.2.9.2.30.3.2.3.1.5.3", EObservationValueType.ANY),
	RISORSE_ECONOMICHE_FINALI				(EObservationElementContentType.CODE_VALUE, "RIEF", "2.16.840.1.113883.2.9.2.30.3.2.3.1.5.4", EObservationValueType.MO),
	/**
	 * Risultati raggiunti
	 * <p>
	 * <i>da non confondere con {@link #RISULTATI_NON_RAGGIUNTI}
	 */
	RISULTATI_RAGGIUNTI						(EObservationElementContentType.CODE_TEXT, "RIRA", "2.16.840.1.113883.2.9.2.30.3.2.3.1.5.5", null),
	/**
	 * Risultati non raggiunti
	 * <p>
	 * <i>da non confondere con {@link #RISULTATI_RAGGIUNTI}
	 */
	RISULTATI_NON_RAGGIUNTI					(EObservationElementContentType.CODE_TEXT, "RINR", "2.16.840.1.113883.2.9.2.30.3.2.3.1.5.6", null),
	NUOVO_PROGETTO							(EObservationElementContentType.CODE_VALUE, "NUPR", "2.16.840.1.113883.2.9.2.30.3.2.3.1.5.7", EObservationValueType.CD)
	;

	
	private EObservation(EObservationElementContentType elementContentType, String code_Code, String code_CodeSystem, EObservationValueType value_Type) {
		this.elementContentType = elementContentType;
		this.code_Code = code_Code;
		this.code_CodeSystem = code_CodeSystem;
		this.value_Type = value_Type;
	}
	
	
	private EObservationElementContentType elementContentType;
	private String code_Code;
	private String code_CodeSystem;
	private EObservationValueType value_Type;
	
	// elenco dei possibili value_Type
	private enum EObservationValueType {
		ANY,
		CD,
		MO;
	}
	
	// indica che tipo di elemento reference viene creato
	public enum EObservationElementContentType {
		CODE_VALUE,
		CODE_TEXT;
	}
	
	
	public EObservationElementContentType getElementContentType() {
		return elementContentType;
	}

	public String getCode_Code() {
		return code_Code;
	}

	public String getCode_CodeSystem() {
		return code_CodeSystem;
	}

	public String getValue_Type() {
		return value_Type.name();
	}
}
