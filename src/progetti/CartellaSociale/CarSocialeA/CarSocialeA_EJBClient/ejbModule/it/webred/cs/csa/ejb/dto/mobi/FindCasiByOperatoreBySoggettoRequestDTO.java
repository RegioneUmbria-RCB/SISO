package it.webred.cs.csa.ejb.dto.mobi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.webred.cs.data.model.view.VMobiIntErog;

public class FindCasiByOperatoreBySoggettoRequestDTO implements Serializable {




	/**
	 * 
	 */
	private static final long serialVersionUID = 7609974482932118973L;
	private String username; ////non me lo passa il client lo calcolo dal token
	//username dell'operatore che sta facendo la richiesta
	
	//passati dal client
	private String ricercaSoggetto; //nome cognome || cognome nome || cognome || cf
	//*****************************
	
	private List<BigDecimal> idSettori; //non me lo passa il client lo calcolo dal token
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<BigDecimal> getIdSettori() {
		return idSettori;
	}
	public void setIdSettori(List<BigDecimal> idSettori) {
		this.idSettori = idSettori;
	}
	public String getRicercaSoggetto() {
		return ricercaSoggetto;
	}
	public void setRicercaSoggetto(String ricercaSoggetto) {
		this.ricercaSoggetto = ricercaSoggetto;
	}
	
		
}
