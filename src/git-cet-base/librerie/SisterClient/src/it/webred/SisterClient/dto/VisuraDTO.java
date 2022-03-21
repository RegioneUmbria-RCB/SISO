package it.webred.SisterClient.dto;

import java.io.Serializable;
import java.util.List;

public class VisuraDTO implements Serializable {
	
	private String page;
	
	private String tipoRichiesta;
	private String cognome;
	private String nome;
	private String dataNasc;
	private String comuneNasc;
	private String cf;
	private String motivazione;
	
	private String messaggio;
	
	private List<ProvinciaDTO> listaProv;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTipoRichiesta() {
		return tipoRichiesta;
	}

	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
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

	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getComuneNasc() {
		return comuneNasc;
	}

	public void setComuneNasc(String comuneNasc) {
		this.comuneNasc = comuneNasc;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public List<ProvinciaDTO> getListaProv() {
		return listaProv;
	}

	public void setListaProv(List<ProvinciaDTO> listaProv) {
		this.listaProv = listaProv;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
}
