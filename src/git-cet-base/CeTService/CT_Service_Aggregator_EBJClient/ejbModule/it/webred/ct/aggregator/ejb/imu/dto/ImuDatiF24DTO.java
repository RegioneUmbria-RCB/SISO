package it.webred.ct.aggregator.ejb.imu.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ImuDatiF24DTO extends ImuBaseDTO{

	private static final long serialVersionUID = 1L;
	
	private String annoRif="";
	private String codBel="";
	private String codTri="";
	private String impPag="";   //xx.00
	private String flgRavv="";
	private String datPag="";         //dd/MM/yyyy
	
	public String getAnnoRif() {
		return annoRif;
	}
	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}
	public String getCodBel() {
		return codBel;
	}
	public void setCodBel(String codBel) {
		this.codBel = codBel;
	}
	public String getCodTri() {
		return codTri;
	}
	public void setCodTri(String codTri) {
		this.codTri = codTri;
	}
	public String getImpPag() {
		return impPag;
	}
	public void setImpPag(String impPag) {
		this.impPag = impPag;
	}
	public String getFlgRavv() {
		return flgRavv;
	}
	public void setFlgRavv(String flgRavv) {
		this.flgRavv = flgRavv;
	}
	public String getDatPag() {
		return datPag;
	}
	public void setDatPag(String datPag) {
		this.datPag = datPag;
	}
	
	public String stampaRecord(){
		String r =  annoRif +"|"+codBel +"|"+codTri +"|"+impPag +"|"+flgRavv +"|"+datPag +"|"+ super.stampaRecord();
		return r;
	}
	
	
}
