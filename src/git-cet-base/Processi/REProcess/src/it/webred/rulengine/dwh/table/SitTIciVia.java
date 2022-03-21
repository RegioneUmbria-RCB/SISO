package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

public class SitTIciVia extends TabellaDwhMultiProv {
		
	private String idOrigViaU;
	private String prefisso;
	private String descrizione;
	private String prefissoAlt;
	private String descrizioneAlt;
	private String tipo;
	private DataDwh dtIni;
	
	public String getIdOrigViaU() {
		return idOrigViaU;
	}

	public void setIdOrigViaU(String idOrigViaU) {
		this.idOrigViaU = idOrigViaU;
	}

	public String getPrefisso() {
		return prefisso;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getPrefissoAlt() {
		return prefissoAlt;
	}

	public void setPrefissoAlt(String prefissoAlt) {
		this.prefissoAlt = prefissoAlt;
	}

	public String getDescrizioneAlt() {
		return descrizioneAlt;
	}

	public void setDescrizioneAlt(String descrizioneAlt) {
		this.descrizioneAlt = descrizioneAlt;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public DataDwh getDtIni() {
		return dtIni;
	}

	public void setDtIni(DataDwh dtIni) {
		this.dtIni = dtIni;
	}

	public String getValueForCtrHash() {		
		return this.idOrigViaU +
		this.prefisso +
		this.descrizione +
		this.prefissoAlt +
		this.descrizioneAlt +
		this.tipo +
		this.dtIni.getDataFormattata();
	}
	
}
