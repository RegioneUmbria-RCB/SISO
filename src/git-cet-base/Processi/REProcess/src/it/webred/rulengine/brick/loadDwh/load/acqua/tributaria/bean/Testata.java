package it.webred.rulengine.brick.loadDwh.load.acqua.tributaria.bean;

import java.util.Date;





public class Testata implements  it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata {

	private String tipoRecord;
	private String codiceIdentificativo;
	private String codiceNumerico;
	private String tipoComunicazione;
	private String protocolloDaSostituire;
	private String codiceFiscale;
	private Date dataDa;
	private Date dataA;
	
	public String getTipoRecord() {
		return tipoRecord;
	}

	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
	}

	public String getCodiceIdentificativo() {
		return codiceIdentificativo;
	}

	public void setCodiceIdentificativo(String codiceIdentificativo) {
		this.codiceIdentificativo = codiceIdentificativo;
	}

	public String getCodiceNumerico() {
		return codiceNumerico;
	}

	public void setCodiceNumerico(String codiceNumerico) {
		this.codiceNumerico = codiceNumerico;
	}

	public String getTipoComunicazione() {
		return tipoComunicazione;
	}

	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}

	public String getProtocolloDaSostituire() {
		return protocolloDaSostituire;
	}

	public void setProtocolloDaSostituire(String protocolloDaSostituire) {
		this.protocolloDaSostituire = protocolloDaSostituire;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataDa() {
		return dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public String getProvenienza() {
		return "A";
	}

}
