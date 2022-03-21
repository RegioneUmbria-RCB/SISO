package it.webred.cs.csa.ejb.dto.erogazioni;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ErogazioneBaseDTO implements Serializable {
	
	private String tipoIntervento;
	private String organizzazione;
	private BigDecimal numero;
	private Date dataUltimaErogazione;
	
	public String getTipoIntervento() {
		return tipoIntervento;
	}
	public String getOrganizzazione() {
		return organizzazione;
	}
	public BigDecimal getNumero() {
		return numero;
	}
	public Date getDataUltimaErogazione() {
		return dataUltimaErogazione;
	}
	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}
	public void setDataUltimaErogazione(Date dataUltimaErogazione) {
		this.dataUltimaErogazione = dataUltimaErogazione;
	}
	
	
}
