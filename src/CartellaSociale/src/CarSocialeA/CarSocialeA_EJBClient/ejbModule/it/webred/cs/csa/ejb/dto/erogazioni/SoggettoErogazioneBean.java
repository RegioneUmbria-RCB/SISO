package it.webred.cs.csa.ejb.dto.erogazioni;

import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.ct.data.model.anagrafe.SitDPersona;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class SoggettoErogazioneBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private CsASoggettoLAZY csASoggetto;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String cittadinanza;
	private Integer annoNascita;
	
	private boolean riferimento;
	
	public SoggettoErogazioneBean(boolean riferimento){
		this.cognome="";
		this.nome="";
		this.codiceFiscale="";
		this.riferimento=riferimento;
		this.cittadinanza=null;
	}
	
	public SoggettoErogazioneBean(String cognome, String nome, String cf, String cittadinanza, Date dataNascita, boolean riferimento){
		this.cognome=cognome;
		this.nome = nome;
		this.codiceFiscale = cf;
		this.cittadinanza=cittadinanza;
		
		if(dataNascita!=null){
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(dataNascita);
			this.annoNascita = calendar1.get(Calendar.YEAR);
		}
		this.riferimento = riferimento;
		
	}
	
	public SoggettoErogazioneBean(CsIInterventoEsegMastSogg es){
		this.csASoggetto = es.getCaso()!=null ?  es.getCaso().getCsASoggetto() : null;
		
		this.cognome=es.getCognome();
		this.nome = es.getNome();
		this.codiceFiscale = es.getCf();
		this.cittadinanza = es.getCittadinanza();
		this.annoNascita = es.getAnnoNascita();
		this.riferimento = es.getRiferimento()!=null && es.getRiferimento() ? true : false;
	}
	
	public SoggettoErogazioneBean( CsASoggettoLAZY soggetto, boolean riferimento ) {
		csASoggetto = soggetto;
	
		if(soggetto!=null){
			nome = soggetto.getCsAAnagrafica().getNome();
			cognome = soggetto.getCsAAnagrafica().getCognome();
			codiceFiscale = soggetto.getCsAAnagrafica().getCf();
			annoNascita = getAnno(soggetto.getCsAAnagrafica().getDataNascita());
			cittadinanza = soggetto.getCsAAnagrafica().getCittadinanza();
		}
		this.riferimento = riferimento;
	}
	
	public SoggettoErogazioneBean( SitDPersona persona, boolean riferimento ){
		nome = persona.getNome();
		cognome = persona.getCognome();
		codiceFiscale = persona.getCodfisc();
		annoNascita =  getAnno(persona.getDataNascita());
		cittadinanza = null;
		this.riferimento = riferimento;
	}

	public boolean isValorizzato() {
		return !StringUtils.isBlank(nome) && 
			   !StringUtils.isBlank(cognome) && 
			   !StringUtils.isBlank(codiceFiscale) && !"0000000000000000".equals(codiceFiscale) &&
			   !StringUtils.isBlank(cittadinanza) &&
			   (annoNascita!=null && annoNascita>0);
	}

	private Integer getAnno(Date data){
		if(data!=null){
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(data);
			return calendar1.get(Calendar.YEAR);
		}else return null;
	}
	
	
	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public boolean isRiferimento() {
		return riferimento;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public void setRiferimento(boolean riferimento) {
		this.riferimento = riferimento;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Integer getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(Integer annoNascita) {
		this.annoNascita = annoNascita;
	}
	
}
