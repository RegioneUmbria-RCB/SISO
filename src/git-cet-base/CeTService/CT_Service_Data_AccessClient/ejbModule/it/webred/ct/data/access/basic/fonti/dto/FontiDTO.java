package it.webred.ct.data.access.basic.fonti.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FontiDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date dataRifInizio;
	private Date dataRifAggiornamento;
	
	public Date getDataRifInizio() {
		return dataRifInizio;
	}
	public void setDataRifInizio(Date dataRifInizio) {
		this.dataRifInizio = dataRifInizio;
	}
	public Date getDataRifAggiornamento() {
		return dataRifAggiornamento;
	}
	public void setDataRifAggiornamento(Date dataRifAggiornamento) {
		this.dataRifAggiornamento = dataRifAggiornamento;
	}
	
}
