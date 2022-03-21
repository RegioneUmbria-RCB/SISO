package it.webred.siso.ws.client.anag.marche.client;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonaResult implements Serializable{
	
    private String assistitoId;
	private String cognome;
	private String nome;
	private String sesso;
	private String dataNascita;
	private String istatComNas;
	private String desComNas;
	private String siglaProvNas;
	private String codfisc;
	private String istatNazione;
	private String istatComResidenza;
	private String siglaProvRes;
	private String indirizzoResidenza; //<ResidenzaIndirizzo
	private String descComRes; //ResidenzaDescrizione
	private String capRes; //ResidenzaCap
	private String recaptelefonico ; //RecapitoTelefonico
	private String dataMor; //  <DecessoData/>
	private String desStatoNas;
	private String istatComDomicilio; //DomicilioIstat
	private String DocumentoSanitario;
	private String DocumentoSanitarioScadenza;
	
	private String codStatoNas;
	private String statoCivile;
	private String codIstatCittadinanza;
	
	private String descComDomicilio; //DomicilioDescrizione
	private String indirizzoDomicilio; //DomicilioDescrizione
	
	private String siglaProvDomicilio; //DomicilioProvinciaSigla
	private String capDomicilio; //DomicilioCap
	private String medicoDataScelta;//SceltaData
	private String medicoDataRevoca;//RevocaData
	private String medicoCodiceFiscale; //CodiceFiscale
	private String recaptelefonicoSecondario;
	///dati ultimo medico scelto 
	private String medicoCognomeNome;//CognomeNome
	
	public String getDocumentoSanitario() {
		return DocumentoSanitario;
	}
	public void setDocumentoSanitario(String documentoSanitario) {
		DocumentoSanitario = documentoSanitario;
	}
	public String getDocumentoSanitarioScadenza() {
		return DocumentoSanitarioScadenza;
	}
	public void setDocumentoSanitarioScadenza(String documentoSanitarioScadenza) {
		DocumentoSanitarioScadenza = documentoSanitarioScadenza;
	}

	public String getRecaptelefonicoSecondario() {
		return recaptelefonicoSecondario;
	}
	public void setRecaptelefonicoSecondario(String recaptelefonicoSecondario) {
		this.recaptelefonicoSecondario = recaptelefonicoSecondario;
	}

	public String getAssistitoId() {
		return assistitoId;
	}
	public void setAssistitoId(String assistitoId) {
		this.assistitoId = assistitoId;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Date getDataNascita() {
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date  date = dataNascita!=null ? df.parse(dataNascita) : null;
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getIstatComNas() {
		return istatComNas;
	}
	public void setIstatComNas(String istatComNas) {
		this.istatComNas = istatComNas;
	}
	public String getDesComNas() {
		return desComNas;
	}
	public void setDesComNas(String desComNas) {
		this.desComNas = desComNas;
	}
	public String getSiglaProvNas() {
		return siglaProvNas;
	}
	public void setSiglaProvNas(String siglaProvNas) {
		this.siglaProvNas = siglaProvNas;
	}
	public String getCodfisc() {
		return codfisc;
	}
	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}
	public String getIstatNazione() {
		return istatNazione;
	}
	public void setIstatNazione(String istatNazione) {
		this.istatNazione = istatNazione;
	}
	public String getIstatComResidenza() {
		return istatComResidenza;
	}
	public void setIstatComResidenza(String istatComResidenza) {
		this.istatComResidenza = istatComResidenza;
	}
	public String getSiglaProvRes() {
		return siglaProvRes;
	}
	public void setSiglaProvRes(String siglaProvRes) {
		this.siglaProvRes = siglaProvRes;
	}
	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}
	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}
	public String getDescComRes() {
		return descComRes;
	}
	public void setDescComRes(String descComRes) {
		this.descComRes = descComRes;
	}
	public String getCapRes() {
		return capRes;
	}
	public void setCapRes(String capRes) {
		this.capRes = capRes;
	}
	public String getRecaptelefonico() {
		return recaptelefonico;
	}
	public void setRecaptelefonico(String recaptelefonico) {
		this.recaptelefonico = recaptelefonico;
	}
	public Date getDataMor() {
		if(dataMor==null || dataMor.isEmpty())
			return null;
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date  startDate = df.parse(dataMor);
			return startDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void setDataMor(String dataMor) {
		this.dataMor = dataMor;
	}
	public String getDesStatoNas() {
		return desStatoNas;
	}
	public void setDesStatoNas(String desStatoNas) {
		this.desStatoNas = desStatoNas;
	}
	public String getIstatComDomicilio() {
		return istatComDomicilio;
	}
	public void setIstatComDomicilio(String istatComDomicilio) {
		this.istatComDomicilio = istatComDomicilio;
	}
	public String getCodStatoNas() {
		return codStatoNas;
	}
	public void setCodStatoNas(String codStatoNas) {
		this.codStatoNas = codStatoNas;
	}
	public String getStatoCivile() {
		return statoCivile;
	}
	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}
	public String getCodIstatCittadinanza() {
		return codIstatCittadinanza;
	}
	public void setCodIstatCittadinanza(String codIstatCittadinanza) {
		this.codIstatCittadinanza = codIstatCittadinanza;
	}
	public String getDescComDomicilio() {
		return descComDomicilio;
	}
	public void setDescComDomicilio(String descComDomicilio) {
		this.descComDomicilio = descComDomicilio;
	}
	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}
	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}
	public String getSiglaProvDomicilio() {
		return siglaProvDomicilio;
	}
	public void setSiglaProvDomicilio(String siglaProvDomicilio) {
		this.siglaProvDomicilio = siglaProvDomicilio;
	}
	public String getCapDomicilio() {
		return capDomicilio;
	}
	public void setCapDomicilio(String capDomicilio) {
		this.capDomicilio = capDomicilio;
	}
	public String getMedicoCognomeNome() {
		return medicoCognomeNome;
	}
	public void setMedicoCognomeNome(String medicoCognomeNome) {
		this.medicoCognomeNome = medicoCognomeNome;
	}
	public String getMedicoDataScelta() {
		return medicoDataScelta;
	}
	public void setMedicoDataScelta(String medicoDataScelta) {
		this.medicoDataScelta = medicoDataScelta;
	}
	public String getMedicoDataRevoca() {
		return medicoDataRevoca;
	}
	public void setMedicoDataRevoca(String medicoDataRevoca) {
		this.medicoDataRevoca = medicoDataRevoca;
	}
	public String getMedicoCodiceFiscale() {
		return medicoCodiceFiscale;
	}
	public void setMedicoCodiceFiscale(String medicoCodiceFiscale) {
		this.medicoCodiceFiscale = medicoCodiceFiscale;
	}
	 
	 
}
