package eu.smartpeg.rilevazionepresenze.web.dto;

import java.math.BigDecimal;
import java.util.Date;


import it.webred.jsf.bean.SessoBean;

public class AnagraficaDTO {

	
	private String cognome;
	private String nome;
	private Date dataNascita;
	private String codiceFiscale;
	private SessoBean datiSesso=new SessoBean();
	private boolean comNascNonValido;
	private String residenza;
	
	private String cittadinanza;
	private Boolean flgReferente;
	
	private String descrLuogoConseguito;

	private Long idStruttura;
	private Long idAreaStruttura;
	private String unitaAbitativa;

	private BigDecimal idOrdineScuola;
	private Long idTitoloStudio;
	private String idLuogoConseguito;
	private Long idCondizioneLavorativa;
	
	private Long idVulnerabilita;

	private String note;

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
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public SessoBean getDatiSesso() {
		return datiSesso;
	}

	public void setDatiSesso(SessoBean datiSesso) {
		this.datiSesso = datiSesso;
	}


	public boolean isComNascNonValido() {
		return comNascNonValido;
	}

	public void setComNascNonValido(boolean comNascNonValido) {
		this.comNascNonValido = comNascNonValido;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Boolean getFlgReferente() {
		return flgReferente;
	}

	public void setFlgReferente(Boolean flgReferente) {
		this.flgReferente = flgReferente;
	}

	public String getDescrLuogoConseguito() {
		return descrLuogoConseguito;
	}

	public void setDescrLuogoConseguito(String descrLuogoConseguito) {
		this.descrLuogoConseguito = descrLuogoConseguito;
	}

	public Long getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(Long idStruttura) {
		this.idStruttura = idStruttura;
	}

	public Long getIdAreaStruttura() {
		return idAreaStruttura;
	}

	public void setIdAreaStruttura(Long idAreaStruttura) {
		this.idAreaStruttura = idAreaStruttura;
	}

	public String getUnitaAbitativa() {
		return unitaAbitativa;
	}

	public void setUnitaAbitativa(String unitaAbitativa) {
		this.unitaAbitativa = unitaAbitativa;
	}

	public BigDecimal getIdOrdineScuola() {
		return idOrdineScuola;
	}

	public void setIdOrdineScuola(BigDecimal idOrdineScuola) {
		this.idOrdineScuola = idOrdineScuola;
	}

	public Long getIdTitoloStudio() {
		return idTitoloStudio;
	}

	public void setIdTitoloStudio(Long idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}

	public String getIdLuogoConseguito() {
		return idLuogoConseguito;
	}

	public void setIdLuogoConseguito(String idLuogoConseguito) {
		this.idLuogoConseguito = idLuogoConseguito;
	}

	public Long getIdCondizioneLavorativa() {
		return idCondizioneLavorativa;
	}

	public void setIdCondizioneLavorativa(Long idCondizioneLavorativa) {
		this.idCondizioneLavorativa = idCondizioneLavorativa;
	}

	public Long getIdVulnerabilita() {
		return idVulnerabilita;
	}

	public void setIdVulnerabilita(Long idVulnerabilita) {
		this.idVulnerabilita = idVulnerabilita;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	
}
