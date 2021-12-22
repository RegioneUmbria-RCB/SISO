package it.webred.cs.csa.ejb.dto.pai;

import java.io.Serializable;
import java.util.Date;

public class PaiSintesiDTO implements Serializable {
	
	private Long diarioId;
	private String tipo;
	private Date dtAttivazione;
	private Date dtChiusura;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getDtAttivazione() {
		return dtAttivazione;
	}
	public void setDtAttivazione(Date dtAttivazione) {
		this.dtAttivazione = dtAttivazione;
	}
	public Date getDtChiusura() {
		return dtChiusura;
	}
	public void setDtChiusura(Date dtChiusura) {
		this.dtChiusura = dtChiusura;
	}
	public Long getDiarioId() {
		return diarioId;
	}
	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}
	
}
