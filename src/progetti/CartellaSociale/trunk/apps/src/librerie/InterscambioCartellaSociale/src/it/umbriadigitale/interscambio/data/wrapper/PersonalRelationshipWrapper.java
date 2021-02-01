package it.umbriadigitale.interscambio.data.wrapper;

import it.umbriadigitale.interscambio.enums.EPersonalRelationship;

public class PersonalRelationshipWrapper {
	private EPersonalRelationship personalRelationshipType;
	private String tipologiaRelazioneCode;
	private String comuneResidenzaCode;
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String caregiverText;
	private String genereCode;
	private String dataNascitaText;
	private String comuneNascitaCode;
	
	
	public PersonalRelationshipWrapper(EPersonalRelationship personalRelationshipType, String tipologiaRelazioneCode,
			String comuneResidenzaCode, String codiceFiscale, String nome, String cognome, String caregiverText,
			String genereCode, String dataNascitaText, String comuneNascitaCode) {
		
		this.personalRelationshipType = personalRelationshipType;
		this.tipologiaRelazioneCode = tipologiaRelazioneCode;
		this.comuneResidenzaCode = comuneResidenzaCode;
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.caregiverText = caregiverText;
		this.genereCode = genereCode;
		this.dataNascitaText = dataNascitaText;
		this.comuneNascitaCode = comuneNascitaCode;
	}
	
	
	public EPersonalRelationship getPersonalRelationshipType() {
		return personalRelationshipType;
	}

	public String getTipologiaRelazioneCode() {
		return tipologiaRelazioneCode;
	}

	public String getComuneResidenzaCode() {
		return comuneResidenzaCode;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCaregiverText() {
		return caregiverText;
	}

	public String getGenereCode() {
		return genereCode;
	}

	public String getDataNascitaText() {
		return dataNascitaText;
	}

	public String getComuneNascitaCode() {
		return comuneNascitaCode;
	}
}
