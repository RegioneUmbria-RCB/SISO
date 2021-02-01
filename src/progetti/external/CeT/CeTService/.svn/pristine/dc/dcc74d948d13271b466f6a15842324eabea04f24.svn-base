package it.webred.ct.data.access.basic.anagrafe.dto;

import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;

import java.io.Serializable;
import java.util.Date;

public class RicercaSoggettoAnagrafeDTO extends RicercaSoggettoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//n.b. l'idExtComNas corrisponde al codComNas della superclasse
	private String idSogg;//corrisponde ad idExt di SitDPersona: l'identificativo del soggetto
	private String idVarSogg; //corrisponde ad id di SitDPersona: identifica univocamente la riga corrispondente alla variazione anagrafica
	private String idExtDFamiglia; //da SitDPersFam
	
	private Date dtValDa;
	private Date dtValA;
	private Date dtRif;
	
	public RicercaSoggettoAnagrafeDTO() {
		super();
	}
			
	public String getIdSogg() {
		return idSogg;
	}
	public void setIdSogg(String idSogg) {
		this.idSogg = idSogg;
	}
	public String getIdVarSogg() {
		return idVarSogg;
	}
	public void setIdVarSogg(String idVarSogg) {
		this.idVarSogg = idVarSogg;
	}
		
	public Date getDtValDa() {
		return dtValDa;
	}
	public void setDtValDa(Date dtValDa) {
		this.dtValDa = dtValDa;
	}
	public Date getDtValA() {
		return dtValA;
	}
	public void setDtValA(Date dtValA) {
		this.dtValA = dtValA;
	}
	public Date getDtRif() {
		return dtRif;
	}
	public void setDtRif(Date dtRif) {
		this.dtRif = dtRif;
	}
	
	public String getIdExtDFamiglia() {
		return idExtDFamiglia;
	}
	public void setIdExtDFamiglia(String idExtDFamiglia) {
		this.idExtDFamiglia = idExtDFamiglia;
	}

}
