package it.umbriadigitale.interscambio.constants;

/**
 * Raccoglie tutti i codici OID utilizzati come default nei vari elementi
 * 
 * @see	{@link it.umbriadigitale.interscambio.enums}	Alcuni OID sono salvati direttamente nelle Enum di riferimento
 */
public interface OIDDefaults {
	// Request
	public static final String OID_MESSAGE_ID = "2.16.840.1.113883.2.9.2.30.3.2.4.3";
	public static final String OID_ACCEPT_ACK_CODE = "AcknowledgementType HL7";
	public static final String OID_RECEIVER_ID = "2.16.840.1.113883.2.9.2.30.3.2.4.3";
	public static final String OID_SENDER_ID = "2.16.840.1.113883.2.9.2.30.3.2.4.3";
	
	
	/* OID Comuni */
	public static final String OID_ADMINISTRATIVE_GENDER_CODE = "2.16.840.1.113883.5.1";
	public static final String OID_MARITAL_STATUS_CODE = "2.16.840.1.113883.5.2";
	public static final String OID_EDUCATION_LEVEL_CODE = "2.16.840.1.113883.2.9.6.2.8";
	
	// 3.3.4.1 Prestazione Erogata
	public static final String OID_CODICE_PRESTAZIONE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.17";
	public static final String OID_LUOGO_PRESTAZIONE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.18";
	public static final String OID_PROFILO_PROFESSIONALE_OPERATORE_EROGANTE_CODE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.4.18";
	public static final String OID_CODICE_FISCALE_OPERATORE_EROGANTE = "2.16.840.1.113883.2.9.4.3.2";

	
	/* EncounterEvent proprietà comuni */
	public static final String OID_IDENTIFICATIVO_FASE = "2.16.840.1.113883.2.9.2.30.3.2";
	public static final String OID_ENCOUNTEREVENT_CODE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.1";
	public static final String OID_CODICE_ESITO_FASE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.2";
	
	
	/* 3.3.1 EncounterEvent "Accesso e Orientamento" */
	
	// subject.patient.subjectOf.identityDocument
	public static final String OID_ESTREMI_DOCUMENTO = "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.7";
	public static final String OID_TIPO_DOCUMENTO = "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.6";
	
	// subject.patient.subjectOf.patientPerson
	public static final String OID_PATIENTPERSON_CITTADINANZA = "2.16.840.1.113883.2.9.6.2.5";
	public static final String OID_PATIENTPERSON_TESSERA_TEAM = "2.16.840.1.113883.2.9.4.1.4";
	public static final String OID_PATIENTPERSON_CODICE_FISCALE = "2.16.840.1.113883.2.9.4.1.2";
	public static final String OID_PATIENTPERSON_COMUNE_NASCITA_CODE = "2.16.840.1.113883.2.9.4.2.3";
	
	// subject.patient.subjectOf.patientPerson.personalRelationship
	public static final String OID_PATIENTPERSON_PERSONALRELATIONSHIP_CODICE_FISCALE = "2.16.840.1.113883.2.9.4.3.2";
	
	// consultant (MMG/PDF - Medico/Pediatra)
	public static final String OID_MMGPDF_CODICE_FISCALE = "2.16.840.1.113883.2.9.4.3.2";
	
	// consultant (3.3.1.3 Assistente Personale)
	public static final String OID_TIPOLOGIA_ASSITENTE_PERSONALE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.24";
	public static final String OID_ASSITENTE_PERSONALE_CODICE_FISCALE = "2.16.840.1.113883.2.9.4.3.2";
	
	// reason.need
	public static final String OID_BISOGNO_ESPRESSO_CODE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.9";
	public static final String OID_TIPOLOGIA_ENTE_SEGNALANTE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.5";
	
	// reason.need.informant.assignedEntity
	public static final String OID_CODICE_FISCALE_SEGNALANTE = "2.16.840.1.113883.2.9.4.3.2";
	
	// reason.need.informant.assignedEntity.representedOrganization
	public static final String OID_CODICE_ENTE_SEGNALANTE = "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.20";
	
	
	/* 3.3.3 EncounterEvent "Elaborazione del progetto individuale" */
	
	// persistentInformation2.carePlan
	public static final String OID_CODICE_PROGETTO = "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.1";
	
	// persistentInformation2.carePlan.finalGoal
	public static final String OID_CODICE_OBIETTIVO = "2.16.840.1.113883.2.9.2.30.3.2.3.1.3.9";
}
