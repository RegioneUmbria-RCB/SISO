package it.webred.siso.ws.client.anag.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonaFindResult {
	private String idPaziente;
	
	private String cognome;
	private String nome;
	private String dataNascita;
	private String codfisc;
	private String dataMor;
	private String sesso;
	private String desStatoNas;
	private String istatComNas;
	private String desComNas;
	private String siglaProvNas;
	private String codStatoNas;
	private String statoCivile;
	private String codiceRegionaleMedico;
	private String codIstatCittadinanza;
	
	private String indirizzoResidenza;
	private String indirizzoDomicilio;
	private String istatComResidenza;
	
	private String civicoResidenza;
	private String civicoDomicilio;
	private String istatComDomicilio;
	
	private String numeroTesseraSanitaria;

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

	public Date getDataNascita() {
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			Date  startDate = df.parse(dataNascita);
			return startDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getIdPaziente() {
		return idPaziente;
	}

	public void setIdPaziente(String idPaziente) {
		this.idPaziente = idPaziente;
	}

	

	public Date getDataMor() {
		if(dataMor==null || dataMor.isEmpty())
			return null;
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
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

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getDesStatoNas() {
		return desStatoNas;
	}

	public void setDesStatoNas(String desStatoNas) {
		this.desStatoNas = desStatoNas;
	}

	public String getDesComNas() {
		return desComNas;
	}

	public void setDesComNas(String desComNas) {
		this.desComNas = desComNas;
	}

	public String getCodStatoNas() {
		return codStatoNas;
	}

	public void setCodStatoNas(String codStatoNas) {
		this.codStatoNas = codStatoNas;
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

	public String getStatoCivile() {
		return statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public String getCodiceRegionaleMedico() {
		return codiceRegionaleMedico;
	}

	public void setCodiceRegionaleMedico(String codiceRegionaleMedico) {
		this.codiceRegionaleMedico = codiceRegionaleMedico;
	}

	public String getCodIstatCittadinanza() {
		return codIstatCittadinanza;
	}

	public void setCodIstatCittadinanza(String codIstatCittadinanza) {
		this.codIstatCittadinanza = codIstatCittadinanza;
	}

	public String getIstatComNas() {
		return istatComNas;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}

	public String getIstatComResidenza() {
		return istatComResidenza;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public String getCivicoDomicilio() {
		return civicoDomicilio;
	}

	public String getIstatComDomicilio() {
		return istatComDomicilio;
	}

	public void setIstatComNas(String istatComNas) {
		this.istatComNas = istatComNas;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}

	public void setIstatComResidenza(String istatComResidenza) {
		this.istatComResidenza = istatComResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public void setCivicoDomicilio(String civicoDomicilio) {
		this.civicoDomicilio = civicoDomicilio;
	}

	public void setIstatComDomicilio(String istatComDomicilio) {
		this.istatComDomicilio = istatComDomicilio;
	}

	public String getNumeroTesseraSanitaria() {
		return numeroTesseraSanitaria;
	}

	public void setNumeroTesseraSanitaria(String numeroTesseraSanitaria) {
		this.numeroTesseraSanitaria = numeroTesseraSanitaria;
	}

	

	
}
