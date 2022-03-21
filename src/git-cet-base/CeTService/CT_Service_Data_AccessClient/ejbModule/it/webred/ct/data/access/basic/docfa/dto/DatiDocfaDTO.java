package it.webred.ct.data.access.basic.docfa.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatiDocfaDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date fornitura;
	private String protocolloReg;
	private String dataRegistrazione;
	private String desTipoOperazione;
	private String indirizzoUiuCompleto;
	
	
	private String dataFornituraStr;
	private String dataFornituraStr2;
	
	
	public Date getFornitura() {
		return fornitura;
	}
	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}
	public String getProtocolloReg() {
		return protocolloReg;
	}
	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}
	public String getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getDesTipoOperazione() {
		return desTipoOperazione;
	}
	public void setDesTipoOperazione(String desTipoOperazione) {
		this.desTipoOperazione = desTipoOperazione;
	}
	public String getIndirizzoUiuCompleto() {
		return indirizzoUiuCompleto;
	}
	public void setIndirizzoUiuCompleto(String indirizzoUiuCompleto) {
		this.indirizzoUiuCompleto = indirizzoUiuCompleto;
	}

	



	public void setDataFornituraStr(String dataFornituraStr) {
		this.dataFornituraStr = dataFornituraStr;
	}
	
	public String getDataFornituraStr() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(fornitura);
		}
		catch(Throwable t) {
			return "";
		}
	}
	public String getDataFornituraStr2() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.format(fornitura);
		}
		catch(Throwable t) {
			return "";
		}
	}
	public void setDataFornituraStr2(String dataFornituraStr2) {
		this.dataFornituraStr2 = dataFornituraStr2;
	}
	
}
