package it.marche.regione.pddnica.client;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class RicercaCF implements Serializable {

	  private String codiceFiscale;
      private String dataValidita; //2020-09-09T21:59:00Z</DataValidita>";
	  private String prestazioneDaErogare;
	  private String protocolloDomandaEnteErogatore;
	  private String statodomandaPrestazione;
	  
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDataValidita() {
		if(dataValidita != null) {
			return dateToString(dataValidita);
		}
		return null;
//		return dataValidita;
	}
	/**
	 * 
	 * @param dataValidita deve contenere la data nel formato dd/MM/yyyy
	 */
	public void setDataValidita(String dataValidita) {
		this.dataValidita = dataValidita;
	}
	public String getPrestazioneDaErogare() {
		return prestazioneDaErogare;
	}
	public void setPrestazioneDaErogare(String prestazioneDaErogare) {
		this.prestazioneDaErogare = prestazioneDaErogare;
	}
	public String getProtocolloDomandaEnteErogatore() {
		return protocolloDomandaEnteErogatore;
	}
	public void setProtocolloDomandaEnteErogatore(String protocolloDomandaEnteErogatore) {
		this.protocolloDomandaEnteErogatore = protocolloDomandaEnteErogatore;
	}
	public String getStatodomandaPrestazione() {
		return statodomandaPrestazione;
	}
	public void setStatodomandaPrestazione(String statodomandaPrestazione) {
		this.statodomandaPrestazione = statodomandaPrestazione;
	}
	
	 public void setPrestazioneDaErogareEnum(PrestazioneDaErogare presEnum) {
		 this.prestazioneDaErogare = presEnum.toString();
	 }
	 
	 public void setStatoDomandaPrestazioneEnum(StatoDomanda statoDomandaEnum) {
		 this.statodomandaPrestazione = statoDomandaEnum.toString();
	 }
	 
	 private   String dateToString(String dataSource) {
		 java.util.Date date;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(dataSource);
			 if (date == null) {
				    return null;
				  }
				  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
				  //format.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
				  return format.format(date) + "Z";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
