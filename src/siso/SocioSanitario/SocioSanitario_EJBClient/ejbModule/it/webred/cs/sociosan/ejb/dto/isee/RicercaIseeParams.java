package it.webred.cs.sociosan.ejb.dto.isee;

import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class RicercaIseeParams extends CeTBaseObject{

	private static final long serialVersionUID = 1L;
	private String cf;
    private Date dataValidita;
    private String codPrestazione;
    private String idCasoErogazione;
   
	public String getCf() {
		return cf;
	}



	public void setCf(String cf) {
		this.cf = cf;
	}



	public Date getDataValidita() {
		return dataValidita;
	}



	public void setDataValidita(Date dataValidita) {
		this.dataValidita = dataValidita;
	}



	public String getCodPrestazione() {
		return codPrestazione;
	}



	public void setCodPrestazione(String codPrestazione) {
		this.codPrestazione = codPrestazione;
	}



	public String getIdCasoErogazione() {
		return idCasoErogazione;
	}



	public void setIdCasoErogazione(String idCasoErogazione) {
		this.idCasoErogazione = idCasoErogazione;
	}


	public String stampaParametri(){
		String s = "PARAMETRI INTERROGAZIONE ISEE "
				+ "DATA VALIDITA["+dataValidita+"]"
				+ "CF["+cf+"]"
				+ "COD.PRESTAZIONE["+codPrestazione+"]"
				+ "ID CASO/EROGAZIONE["+idCasoErogazione+"]";
		return s;
	}
   
}
