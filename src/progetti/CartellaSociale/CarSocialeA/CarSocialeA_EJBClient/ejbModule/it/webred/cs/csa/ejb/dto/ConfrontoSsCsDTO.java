package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsDValutazione;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class ConfrontoSsCsDTO implements Serializable {

	private String nome;
	private String cognome;
	private String cf;
	private String cittadinanza;
	private BigDecimal identificativo;
	private String residenzaVia;
	private String residenzaCivico;
	private String residenzaComune;
	private String residenzaProv;
	private String residenzaNazione;
	private CsDValutazione abitazione;
	private CsDValutazione stranieri;
	private String statoCivile;
	private Boolean senzaFissaDimora;
	private String lavoro;
	private String professione;
	private CsACaso caso;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	
	public BigDecimal getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(BigDecimal identificativo) {
		this.identificativo = identificativo;
	}
	/*	public String getResidenzaVia() {
		return residenzaVia;
	}*/
	public void setResidenzaVia(String residenzaVia) {
		this.residenzaVia = residenzaVia;
	}
/*	public String getResidenzaCivico() {
		return residenzaCivico;
	}*/
	public void setResidenzaCivico(String residenzaCivico) {
		this.residenzaCivico = residenzaCivico;
	}
	public String getResidenzaComune() {
		return residenzaComune;
	}
	public void setResidenzaComune(String residenzaComune) {
		this.residenzaComune = residenzaComune;
	}
	public String getResidenzaNazione() {
		return residenzaNazione;
	}
	public void setResidenzaNazione(String residenzaNazione) {
		this.residenzaNazione = residenzaNazione;
	}
	public CsDValutazione getAbitazione() {
		return abitazione;
	}
	public void setAbitazione(CsDValutazione abitazione) {
		this.abitazione = abitazione;
	}
	public CsDValutazione getStranieri() {
		return stranieri;
	}
	public void setStranieri(CsDValutazione stranieri) {
		this.stranieri = stranieri;
	}
	public CsACaso getCaso() {
		return caso;
	}
	public void setCaso(CsACaso caso) {
		this.caso = caso;
	}
	public String getStatoCivile() {
		return statoCivile;
	}
	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}
	public Boolean getSenzaFissaDimora() {
		return senzaFissaDimora;
	}
	public void setSenzaFissaDimora(Boolean senzaFissaDimora) {
		this.senzaFissaDimora = senzaFissaDimora;
	}
	public String getResidenzaProv() {
		return residenzaProv;
	}
	public void setResidenzaProv(String residenzaProv) {
		this.residenzaProv = residenzaProv;
	}
	public String getLavoro() {
		return lavoro;
	}
	public void setLavoro(String lavoro) {
		this.lavoro = lavoro;
	}
	public String getProfessione() {
		return professione;
	}
	public void setProfessione(String professione) {
		this.professione = professione;
	}
	public String getResidenzaIndirizzo(){
		String ing =  this.residenzaVia!=null && !residenzaVia.isEmpty() ? residenzaVia : "";
		ing+= this.residenzaCivico!=null && !this.residenzaCivico.isEmpty() ? ", "+residenzaCivico : "";
		return ing;
	}
	
}
