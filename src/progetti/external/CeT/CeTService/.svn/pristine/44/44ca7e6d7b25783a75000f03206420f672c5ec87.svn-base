package it.webred.ct.data.access.basic.docfa.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatiGeneraliDocfaDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date dataFornitura;
	private String protocollo;
	private Date dataVariazione;
	private String causale;
	private Integer numUiInSoppressione;
	private Integer numUiInVariazione;
	private Integer numUiInCostituzione;
	
	private String dataFornituraStr;
	private String dataFornituraStr2;
	private String dataVariazioneStr;
	private String dataRegistrazione;
	
	private String elencoUI;
	
	public String getDataFornituraStr() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(dataFornitura);
		}
		catch(Throwable t) {
			return "";
		}
	}
	public void setDataFornituraStr(String dataFornituraStr) {
		this.dataFornituraStr = dataFornituraStr;
	}
	public Date getDataFornitura() {
		return dataFornitura;
	}
	public void setDataFornitura(Date dataFornitura) {
		this.dataFornitura = dataFornitura;
	}
	public Date getDataVariazione() {
		return dataVariazione;
	}
	public void setDataVariazione(Date dataVariazione) {
		this.dataVariazione = dataVariazione;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	public Integer getNumUiInSoppressione() {
		return numUiInSoppressione;
	}
	public void setNumUiInSoppressione(Integer numUiInSoppressione) {
		this.numUiInSoppressione = numUiInSoppressione;
	}
	public Integer getNumUiInVariazione() {
		return numUiInVariazione;
	}
	public void setNumUiInVariazione(Integer numUiInVariazione) {
		this.numUiInVariazione = numUiInVariazione;
	}
	public Integer getNumUiInCostituzione() {
		return numUiInCostituzione;
	}
	public void setNumUiInCostituzione(Integer numUiInCostituzione) {
		this.numUiInCostituzione = numUiInCostituzione;
	}
	public String getDataVariazioneStr() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(dataVariazione);
		}
		catch(Throwable t) {
			return "";
		}
	}
	public void setDataVariazioneStr(String dataVariazioneStr) {
		this.dataVariazioneStr = dataVariazioneStr;
	}
	public String getDataFornituraStr2() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.format(dataFornitura);
		}
		catch(Throwable t) {
			return "";
		}
	}
	public void setDataFornituraStr2(String dataFornituraStr2) {
		this.dataFornituraStr2 = dataFornituraStr2;
	}	
	public String getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getDataRegistrazioneView() {
		if (dataRegistrazione == null) {
			return "-";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdfView = new SimpleDateFormat("dd/MM/yyyy");
			return sdfView.format(sdf.parse(dataRegistrazione));
		}
		catch(Throwable t) {
			return dataRegistrazione;
		}
	}
	public String getElencoUI() {
		return elencoUI;
	}
	public void setElencoUI(String elencoUI) {
		this.elencoUI = elencoUI;
	}
	

}
