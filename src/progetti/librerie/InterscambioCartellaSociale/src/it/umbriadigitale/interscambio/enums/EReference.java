package it.umbriadigitale.interscambio.enums;

public enum EReference {
	
	// usati in "Accesso e orientamento"
	TIPOLOGIA_RICHIESTA					(EReferenceElementContentType.CODE_VALUE, "TIRI", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.3", EReferenceValueType.CD),
	MODALITA_DI_CONTATTO				(EReferenceElementContentType.CODE_VALUE, "MOCO", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.3", EReferenceValueType.CD),
	AMBITO_INTERVENTO_RICHIESTO			(EReferenceElementContentType.CODE_VALUE, "AMIR", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.10", EReferenceValueType.CD),
	COMPOSIZIONE_NUCLEO_FAMILIARE		(EReferenceElementContentType.CODE_VALUE, "CONF", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.11", EReferenceValueType.CD),
	NUMEROSITA_NUCLEO_FAMILIARE			(EReferenceElementContentType.CODE_VALUE, "NUNF", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.12", EReferenceValueType.CD),
	NUMEROSITA_RETE_SOCIALE				(EReferenceElementContentType.CODE_VALUE, "NURS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.13", EReferenceValueType.CD),
	PROVENIENZA							(EReferenceElementContentType.CODE_VALUE, "PROV", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.14", EReferenceValueType.CD),
	AREA_REDDITUALE						(EReferenceElementContentType.CODE_VALUE, "ARRE", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.15", EReferenceValueType.CD),
	DSU									(EReferenceElementContentType.CODE_VALUE, "DSU0", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.16", EReferenceValueType.CD),
	TIPOLOGIA_ISEE						(EReferenceElementContentType.CODE_VALUE, "TIIS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.16", EReferenceValueType.CD),
	ISEE								(EReferenceElementContentType.CODE_VALUE, "ISEE", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.18", EReferenceValueType.MO),
	OCCUPAZIONE_ASSISTITO				(EReferenceElementContentType.CODE_VALUE, "OCAS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.19", EReferenceValueType.CD),
	CODICE_ENTE_RILEVATORE				(EReferenceElementContentType.CODE_VALUE, "COER", "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.20", EReferenceValueType.CD),
	DENOMINAZIONE_ENTE_RILEVATORE		(EReferenceElementContentType.CODE_VALUE, "DEER", null, EReferenceValueType.ANY),
	
	// usati in "Valutazione del bisogno"
	AREE_INTERVENTO_SOCIALE				(EReferenceElementContentType.CODE_VALUE, "AMIR", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.2", EReferenceValueType.CD),
	ESENZIONI_TICKET					(EReferenceElementContentType.CODE_VALUE, "ESTI", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.3", EReferenceValueType.CD),
	/**
	 * Denominazione struttura socio sanitarie che ha già in carico la persona
	 */
	DENOMINAZIONE_SSS_PERSONA_CARICO	(EReferenceElementContentType.CODE_TEXT, "DSGI", null, null),
	/**
	 * Servizi sociosanitari già erogati all'assistito
	 */
	SERVIZI_SOCIOSANITARI_GIA_EROGATI	(EReferenceElementContentType.CODE_TEXT, "SSGE", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.5", null),
	INVALIDITA							(EReferenceElementContentType.CODE_VALUE, "INVA", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.6", EReferenceValueType.CD),
	INDENNITA_DI_FREQUENZA				(EReferenceElementContentType.CODE_VALUE, "INFR", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.7", EReferenceValueType.CD),
	PREVIDENZE_SOCIALI_INPS				(EReferenceElementContentType.CODE_VALUE, "PRSI", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.8", EReferenceValueType.CD),
	/**
	 * Assegno pensione invalidità civile
	 */
	ASSEGNO_PENSIONE_INVALIDITA_CIVILE	(EReferenceElementContentType.CODE_VALUE, "APIC", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.9", EReferenceValueType.CD),
	/**
	 * Utente con patologia psichiatrica
	 */
	UTENTE_CON_PATOLOGIA_PSICHIATRICA	(EReferenceElementContentType.CODE_VALUE, "UTPP", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.10", EReferenceValueType.CD),
	PATOLOGIE_CORRENTI					(EReferenceElementContentType.CODE_TEXT, "PACO", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.11", null),
	PORTATORE_PROTESI_E_AUSILI			(EReferenceElementContentType.CODE_VALUE, "POPA", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.12", EReferenceValueType.CD),
	MOTIVAZIONE_AFFIDO					(EReferenceElementContentType.CODE_VALUE, "MOAF", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.13", EReferenceValueType.CD),
	/**
	 * Valutazione necessità di cure sanitarie
	 */
	VALUTAZIONE_NECESSITA_CURE			(EReferenceElementContentType.CODE_VALUE, "VNCS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.14", EReferenceValueType.CD),
	/**
	 * Necessità di interventi sociosanitari
	 * <p>
	 * <i>da non confondere con {@link #NECESSITA_INTERVENTI_SOCIALI}
	 */
	NECESSITA_INTERVENTI_SOCIOSANITARI	(EReferenceElementContentType.CODE_VALUE, "NISS", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.15", EReferenceValueType.CD),
	/**
	 * Necessità di interventi sociali
	 * <p>
	 * <i>da non confondere con {@link #NECESSITA_INTERVENTI_SOCIOSANITARI}
	 */
	NECESSITA_INTERVENTI_SOCIALI		(EReferenceElementContentType.CODE_VALUE, "NISO", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.16", EReferenceValueType.CD),
	/**
	 * Fonte di derivazione della valutazione
	 */
	FONTE_DERIVAZIONE_VALUTAZIONE		(EReferenceElementContentType.CODE_VALUE, "FODV", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.17", EReferenceValueType.CD),
	CODICE_PROTESI_AUSILIO				(EReferenceElementContentType.CODE_VALUE, "COPA", "2.16.840.1.113883.2.9.2.30.3.2.3.1.2.18", EReferenceValueType.CD),
	
	// usati in "Erogazione del servizio"
	DENOMINAZIONE_STRUTTURA_FUORI_RETE	(EReferenceElementContentType.CODE_TEXT, "DSFR", "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.2", null)
	;
	
	
	private EReference(EReferenceElementContentType elementContentType, String code_Code, String code_CodeSystem, EReferenceValueType value_Type) {
		this.elementContentType = elementContentType;
		this.code_Code = code_Code;
		this.code_CodeSystem = code_CodeSystem;
		this.value_Type = value_Type;
	}

	private EReferenceElementContentType elementContentType;
	private String code_Code;
	private String code_CodeSystem;
	private EReferenceValueType value_Type;
	
	// elenco dei possibili value_Type
	private enum EReferenceValueType {
		ANY,
		CD,
		MO;
	}
	
	// indica che tipo di elemento reference viene creato
	public enum EReferenceElementContentType {
		CODE_VALUE,
		CODE_TEXT;
	}
	
	
	/* Metodi di utilità */
	public boolean isValueTypeMO() {
		return value_Type.equals(EReferenceValueType.MO);
	}
	
	/* Getters */
	public EReferenceElementContentType getElementContentType() {
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
