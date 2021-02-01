package it.webred.siso.ws.ricerca.dto;

import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class PersonaDettaglio implements Serializable{
	
	private String provenienzaRicerca;
    private String identificativo;
    
	private String cognome;
	private String nome;
	private String codfisc;
	private String sesso;
	private Date dataNascita;
	private Date dataMorte; //  <DecessoData/>
	private boolean defunto;
	private boolean emigrato;
	
	private AmTabComuni comuneNascita;
	private AmTabNazioni nazioneNascita;
	
	private String istatNazione;
	
	private String indirizzoResidenza; //<ResidenzaIndirizzo
	private String civicoResidenza;
	private AmTabComuni comuneResidenza;
	private AmTabNazioni nazioneResidenza;
	
	private String indirizzoDomicilio; //DomicilioDescrizione
	private String civicoDomicilio;
	private AmTabComuni comuneDomicilio;
	private AmTabNazioni nazioneDomicilio;

	private String telefono ; //RecapitoTelefonico
	
	private String documentoSanitario;
	private String documentoSanitarioScadenza;

	private String statoCivile;
	//private String codIstatCittadinanza;
	private String cittadinanza;

	private String medicoCodRegionale;
	private Date medicoDataScelta;//SceltaData
	private Date medicoDataRevoca;//RevocaData
	private String medicoCodiceFiscale; //CodiceFiscale
	private String recaptelefonicoSecondario;
	///dati ultimo medico scelto 
	private String medicoCognome;
	private String medicoNome;
	
	public PersonaDettaglio(){}
	
	public PersonaDettaglio(String provenienza){
		this.provenienzaRicerca=provenienza;
	}
	
	public String getProvenienzaRicerca() {
		return provenienzaRicerca;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
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

	public String getCodfisc() {
		return codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getIstatNazione() {
		return istatNazione;
	}

	public void setIstatNazione(String istatNazione) {
		this.istatNazione = istatNazione;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDocumentoSanitario() {
		return documentoSanitario;
	}

	public void setDocumentoSanitario(String documentoSanitario) {
		this.documentoSanitario = documentoSanitario;
	}

	public String getDocumentoSanitarioScadenza() {
		return documentoSanitarioScadenza;
	}

	public void setDocumentoSanitarioScadenza(String documentoSanitarioScadenza) {
		this.documentoSanitarioScadenza = documentoSanitarioScadenza;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}

	public Date getDataMorte() {
		return dataMorte;
	}

	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}

	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}

	public String getStatoCivile() {
		return statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public Date getMedicoDataScelta() {
		return medicoDataScelta;
	}

	public void setMedicoDataScelta(Date medicoDataScelta) {
		this.medicoDataScelta = medicoDataScelta;
	}

	public Date getMedicoDataRevoca() {
		return medicoDataRevoca;
	}

	public void setMedicoDataRevoca(Date medicoDataRevoca) {
		this.medicoDataRevoca = medicoDataRevoca;
	}

	public String getMedicoCodiceFiscale() {
		return medicoCodiceFiscale;
	}

	public void setMedicoCodiceFiscale(String medicoCodiceFiscale) {
		this.medicoCodiceFiscale = medicoCodiceFiscale;
	}

	public String getRecaptelefonicoSecondario() {
		return recaptelefonicoSecondario;
	}

	public void setRecaptelefonicoSecondario(String recaptelefonicoSecondario) {
		this.recaptelefonicoSecondario = recaptelefonicoSecondario;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public String getCivicoDomicilio() {
		return civicoDomicilio;
	}

	public void setCivicoDomicilio(String civicoDomicilio) {
		this.civicoDomicilio = civicoDomicilio;
	}

	public String getMedicoCodRegionale() {
		return medicoCodRegionale;
	}

	public void setMedicoCodRegionale(String medicoCodRegionale) {
		this.medicoCodRegionale = medicoCodRegionale;
	}

	public AmTabComuni getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(AmTabComuni comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public AmTabNazioni getNazioneNascita() {
		return nazioneNascita;
	}

	public void setNazioneNascita(AmTabNazioni nazioneNascita) {
		this.nazioneNascita = nazioneNascita;
	}

	public AmTabComuni getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(AmTabComuni comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public AmTabComuni getComuneDomicilio() {
		return comuneDomicilio;
	}

	public void setComuneDomicilio(AmTabComuni comuneDomicilio) {
		this.comuneDomicilio = comuneDomicilio;
	}

	public AmTabNazioni getNazioneResidenza() {
		return nazioneResidenza;
	}

	public void setNazioneResidenza(AmTabNazioni nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}

	public AmTabNazioni getNazioneDomicilio() {
		return nazioneDomicilio;
	}

	public void setNazioneDomicilio(AmTabNazioni nazioneDomicilio) {
		this.nazioneDomicilio = nazioneDomicilio;
	}
	
	public String getIndirizzoCivicoResidenza(){
		String s = !StringUtils.isBlank(indirizzoResidenza) ? this.indirizzoResidenza : "";
		s+= this.civicoResidenza!=null ? ", "+this.civicoResidenza : "";
		return s.trim();
	}
	
	public String getIndirizzoCivicoDomicilio(){
		String s = !StringUtils.isBlank(indirizzoDomicilio) ? this.indirizzoDomicilio : "";
		s+= this.civicoDomicilio!=null ? ", "+this.civicoDomicilio : "";
		return s.trim();
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public boolean isDefunto() {
		return defunto || (dataMorte!=null && dataMorte.before(new Date()));
	}

	public void setDefunto(boolean defunto) {
		this.defunto = defunto;
	}

	public boolean isEmigrato() {
		return emigrato;
	}

	public void setEmigrato(boolean emigrato) {
		this.emigrato = emigrato;
	}

	public String getMedicoCognome() {
		return medicoCognome;
	}

	public void setMedicoCognome(String medicoCognome) {
		this.medicoCognome = medicoCognome;
	}

	public String getMedicoNome() {
		return medicoNome;
	}

	public void setMedicoNome(String medicoNome) {
		this.medicoNome = medicoNome;
	}

	public void setProvenienzaRicerca(String provenienzaRicerca) {
		this.provenienzaRicerca = provenienzaRicerca;
	}
}
