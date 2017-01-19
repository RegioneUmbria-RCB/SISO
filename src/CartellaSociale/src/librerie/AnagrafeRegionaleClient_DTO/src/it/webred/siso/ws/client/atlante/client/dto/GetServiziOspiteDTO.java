package it.webred.siso.ws.client.atlante.client.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * classe dto del servizio di sanitarioche fornisce i servizi attivi per un ospite
 */
public class GetServiziOspiteDTO implements Serializable{

       private String dataFine;
       private String dataInizio;
       private String iguCausFineServ;
       private String idEntitaDes;
       private String idEntitaProp;
       private String idMessaggio;

    
    public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getIguCausFineServ() {
		return iguCausFineServ;
	}
	public void setIguCausFineServ(String iguCausFineServ) {
		this.iguCausFineServ = iguCausFineServ;
	}
	public String getIdEntitaDes() {
		return idEntitaDes;
	}
	public void setIdEntitaDes(String idEntitaDes) {
		this.idEntitaDes = idEntitaDes;
	}
	public String getIdEntitaProp() {
		return idEntitaProp;
	}
	public void setIdEntitaProp(String idEntitaProp) {
		this.idEntitaProp = idEntitaProp;
	}
	public String getIdMessaggio() {
		return idMessaggio;
	}
	public void setIdMessaggio(String idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getDataInizioFormatted(){
		try {
			return dateFormatter(this.dataInizio);
		} catch (ParseException e) {
			return this.dataInizio;
		}
	}
	
	public String getDataFineFormatted(){
		try {
			return dateFormatter(this.dataFine);
		} catch (ParseException e) {
			return null;
		}
	}
       
    private String dateFormatter(String sdata) throws ParseException{
    	String  sd = null;
    	if(sdata!=null && !sdata.isEmpty()){
    		String s[] = sdata.split("T");
    		String data = s[0].trim();
    		Date d =  (new SimpleDateFormat("yyyy-MM-dd")).parse(data);
    		sd = new SimpleDateFormat("dd/MM/yyyy").format(d);
    	}
    	return sd;
    }
	
}
