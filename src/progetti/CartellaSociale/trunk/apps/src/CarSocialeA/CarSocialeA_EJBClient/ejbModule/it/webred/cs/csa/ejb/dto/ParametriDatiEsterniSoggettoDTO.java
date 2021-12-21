package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParametriDatiEsterniSoggettoDTO  implements Serializable{

	 
	private static final long serialVersionUID = 1L;
	private String  dataApertura = null;
	private String	dataChiusura = null;
	private String	indirizzo = null;
	private String	tipoPrestazione = null;
	private String	entitaServizio = null;
	private String  codicePrestazione = null;
	private String  codiceFiscale = null;
	private String statoDomanda = null;
	
	
	
 
	public String getStatoDomanda() {
		return statoDomanda;
	}


	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
	}


	public ParametriDatiEsterniSoggettoDTO(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodicePrestazione() {
		return codicePrestazione;
	}

	public void setCodicePrestazione(String codicePrestazione) {
		this.codicePrestazione = codicePrestazione;
	}

	public Date getDataApertura() {
		if(this.dataApertura != null) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try {
				return df.parse(dataApertura);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return null;
	}

	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}

	public Date getDataChiusura() {
		if(this.dataChiusura != null) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try {
				return df.parse(dataChiusura);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return null;
	}

	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTipoPrestazione() {
		return tipoPrestazione;
	}

	public void setTipoPrestazione(String tipoPrestazione) {
		this.tipoPrestazione = tipoPrestazione;
	}

	public String getEntitaServizio() {
		return entitaServizio;
	}

	public void setEntitaServizio(String entitaServizio) {
		this.entitaServizio = entitaServizio;
	}

	
	public ParametriDatiEsterniSoggettoDTO() {
	}

	 
	
	 
 

}
