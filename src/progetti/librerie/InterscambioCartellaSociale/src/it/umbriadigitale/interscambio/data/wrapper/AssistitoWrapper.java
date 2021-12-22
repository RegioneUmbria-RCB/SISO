package it.umbriadigitale.interscambio.data.wrapper;

import java.util.List;

public class AssistitoWrapper {
	private String id;
	private String comuneResidenzaCode;
	private String regioneResidenzaCode;
	private String nazioneResidenzaCode;
	private String comuneDomicilioCode;
	private String numeroTelefono;
	private String numeroCellulare;
	private String email;
	private String tipoDocumentoCode;
	private String estremiDocumento;
	private String nome;
	private String cognome;
	private String genereCode;
	private String dataNascitaText;
	private String statoCivileCode;
	private String titoloStudioCode;
	private String cittadinanzaCode;
	private String secondaCittadinanzaCode;
	private String tesseraTEAM;
	private String codiceFiscale;	// opzionale - da valorizzare quando id non corrisponde al CF
	private String atsResidenzaCode;
	private String asstResidenzaCode;
	private String ambitoResidenzaCode;
	private String comuneNascitaCode;
	private List<PersonalRelationshipWrapper> nucleoFamiliare;
	private List<PersonalRelationshipWrapper> reteSociale;
	
	
	public AssistitoWrapper(String id, String comuneResidenzaCode, String regioneResidenzaCode,
			String nazioneResidenzaCode, String comuneDomicilioCode, String numeroTelefono, String numeroCellulare,
			String email, String tipoDocumentoCode, String estremiDocumento, String nome, String cognome,
			String genereCode, String dataNascitaText, String statoCivileCode, String titoloStudioCode,
			String cittadinanzaCode, String secondaCittadinanzaCode, String tesseraTEAM, String codiceFiscale,
			String atsResidenzaCode, String asstResidenzaCode, String ambitoResidenzaCode, String comuneNascitaCode,
			List<PersonalRelationshipWrapper> nucleoFamiliare, List<PersonalRelationshipWrapper> reteSociale) {
		
		this.id = id;
		this.comuneResidenzaCode = comuneResidenzaCode;
		this.regioneResidenzaCode = regioneResidenzaCode;
		this.nazioneResidenzaCode = nazioneResidenzaCode;
		this.comuneDomicilioCode = comuneDomicilioCode;
		this.numeroTelefono = numeroTelefono;
		this.numeroCellulare = numeroCellulare;
		this.email = email;
		this.tipoDocumentoCode = tipoDocumentoCode;
		this.estremiDocumento = estremiDocumento;
		this.nome = nome;
		this.cognome = cognome;
		this.genereCode = genereCode;
		this.dataNascitaText = dataNascitaText;
		this.statoCivileCode = statoCivileCode;
		this.titoloStudioCode = titoloStudioCode;
		this.cittadinanzaCode = cittadinanzaCode;
		this.secondaCittadinanzaCode = secondaCittadinanzaCode;
		this.tesseraTEAM = tesseraTEAM;
		this.codiceFiscale = codiceFiscale;
		this.atsResidenzaCode = atsResidenzaCode;
		this.asstResidenzaCode = asstResidenzaCode;
		this.ambitoResidenzaCode = ambitoResidenzaCode;
		this.comuneNascitaCode = comuneNascitaCode;
		this.nucleoFamiliare = nucleoFamiliare;
		this.reteSociale = reteSociale;
	}
	
	
	public String getId() {
		return id;
	}

	public String getComuneResidenzaCode() {
		return comuneResidenzaCode;
	}

	public String getRegioneResidenzaCode() {
		return regioneResidenzaCode;
	}

	public String getNazioneResidenzaCode() {
		return nazioneResidenzaCode;
	}

	public String getComuneDomicilioCode() {
		return comuneDomicilioCode;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public String getNumeroCellulare() {
		return numeroCellulare;
	}

	public String getEmail() {
		return email;
	}

	public String getTipoDocumentoCode() {
		return tipoDocumentoCode;
	}

	public String getEstremiDocumento() {
		return estremiDocumento;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getGenereCode() {
		return genereCode;
	}

	public String getDataNascitaText() {
		return dataNascitaText;
	}

	public String getStatoCivileCode() {
		return statoCivileCode;
	}

	public String getTitoloStudioCode() {
		return titoloStudioCode;
	}

	public String getCittadinanzaCode() {
		return cittadinanzaCode;
	}

	public String getSecondaCittadinanzaCode() {
		return secondaCittadinanzaCode;
	}

	public String getTesseraTEAM() {
		return tesseraTEAM;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getAtsResidenzaCode() {
		return atsResidenzaCode;
	}

	public String getAsstResidenzaCode() {
		return asstResidenzaCode;
	}

	public String getAmbitoResidenzaCode() {
		return ambitoResidenzaCode;
	}

	public String getComuneNascitaCode() {
		return comuneNascitaCode;
	}

	public List<PersonalRelationshipWrapper> getNucleoFamiliare() {
		return nucleoFamiliare;
	}

	public List<PersonalRelationshipWrapper> getReteSociale() {
		return reteSociale;
	}
}