package it.webred.cs.json.valSinba.ver1.tabs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatiSegnalazioniBean {
	
	private int fonte;
	private int valutazioneMinore;
	private Date dataSegnalazione;
	private int valutazioneFamiglia;
	private int autoritaGiudiziaria;
	private int provvedimentoGiudiziario;
	private int autoritaProvvedimento;
	private Date dataProvvedimento;
	private int tipoProvvedimento;
	private int potestaTutela;
	
	
	public DatiSegnalazioniBean() {
		
	}


	public int getFonte() {
		return fonte;
	}


	public void setFonte(int fonte) {
		this.fonte = fonte;
	}


	public int getValutazioneMinore() {
		return valutazioneMinore;
	}


	public void setValutazioneMinore(int valutazioneMinore) {
		this.valutazioneMinore = valutazioneMinore;
	}


	public Date getDataSegnalazione() {
		return dataSegnalazione;
	}


	public void setDataSegnalazione(Date dataSegnalazione) {
		this.dataSegnalazione = dataSegnalazione;
	}


	public int getValutazioneFamiglia() {
		return valutazioneFamiglia;
	}


	public void setValutazioneFamiglia(int valutazioneFamiglia) {
		this.valutazioneFamiglia = valutazioneFamiglia;
	}


	public int getAutoritaGiudiziaria() {
		return autoritaGiudiziaria;
	}


	public void setAutoritaGiudiziaria(int autoritaGiudiziaria) {
		this.autoritaGiudiziaria = autoritaGiudiziaria;
	}


	public int getProvvedimentoGiudiziario() {
		return provvedimentoGiudiziario;
	}


	public void setProvvedimentoGiudiziario(int provvedimentoGiudiziario) {
		this.provvedimentoGiudiziario = provvedimentoGiudiziario;
	}


	public int getAutoritaProvvedimento() {
		return autoritaProvvedimento;
	}


	public void setAutoritaProvvedimento(int autoritaProvvedimento) {
		this.autoritaProvvedimento = autoritaProvvedimento;
	}


	public Date getDataProvvedimento() {
		return dataProvvedimento;
	}


	public void setDataProvvedimento(Date dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}


	public int getTipoProvvedimento() {
		return tipoProvvedimento;
	}


	public void setTipoProvvedimento(int tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}


	public int getPotestaTutela() {
		return potestaTutela;
	}


	public void setPotestaTutela(int potestaTutela) {
		this.potestaTutela = potestaTutela;
	}

	
}
