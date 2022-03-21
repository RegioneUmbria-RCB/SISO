package it.escsolution.escwebgis.ecografico.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.util.List;

public class CivicoEcografico extends EscObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pkSequCivico;  
	private String pkSequStocivico;   
	private String ukCivico; 
	private String fkComuniBelf;  
	private String comune;
	private String cap;
	private String fkStrade; 
	private String descrStrada; 
	private String numero;
	private String esp1;
	private String esp2;
	private String esp3;
	private String esp4;
	private String descrizioneCivico; 
	private String tipoCivico;
	private String tipoAccesso; 
	private String latoStrada; 
	private String accessibilita;
	private String foglioCt; 
	private String particellaCt; 
	private String descrDestUso;
	private String dataInizioVal;  
	private String dataFineVal;
	private String fkSezCensimento; 
	private String codiceCensimento; 
	private String dataRecord; 
	private String note;
	
	private String codiceStrada;
	
	private String xcentroid;
	private String ycentroid;
	
	private String latitudine;
	private String longitudine;

	private List<FabbricatoEcografico> lstFabbricati;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPkSequCivico() {
		return pkSequCivico;
	}
	public void setPkSequCivico(String pkSequCivico) {
		this.pkSequCivico = pkSequCivico;
	}
	public String getPkSequStocivico() {
		return pkSequStocivico;
	}
	public void setPkSequStocivico(String pkSequStocivico) {
		this.pkSequStocivico = pkSequStocivico;
	}
	public String getUkCivico() {
		return ukCivico;
	}
	public void setUkCivico(String ukCivico) {
		this.ukCivico = ukCivico;
	}
	public String getFkComuniBelf() {
		return fkComuniBelf;
	}
	public void setFkComuniBelf(String fkComuniBelf) {
		this.fkComuniBelf = fkComuniBelf;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getFkStrade() {
		return fkStrade;
	}
	public void setFkStrade(String fkStrade) {
		this.fkStrade = fkStrade;
	}
	public String getDescrStrada() {
		return descrStrada;
	}
	public void setDescrStrada(String descrStrada) {
		this.descrStrada = descrStrada;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getEsp1() {
		return esp1;
	}
	public void setEsp1(String esp1) {
		this.esp1 = esp1;
	}
	public String getEsp2() {
		return esp2;
	}
	public void setEsp2(String esp2) {
		this.esp2 = esp2;
	}
	public String getEsp3() {
		return esp3;
	}
	public void setEsp3(String esp3) {
		this.esp3 = esp3;
	}
	public String getEsp4() {
		return esp4;
	}
	
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	public void setEsp4(String esp4) {
		this.esp4 = esp4;
	}
	public String getDescrizioneCivico() {
		return descrizioneCivico;
	}
	public void setDescrizioneCivico(String descrizioneCivico) {
		this.descrizioneCivico = descrizioneCivico;
	}
	public String getTipoCivico() {
		return tipoCivico;
	}
	public void setTipoCivico(String tipoCivico) {
		this.tipoCivico = tipoCivico;
	}
	public String getTipoAccesso() {
		return tipoAccesso;
	}
	public void setTipoAccesso(String tipoAccesso) {
		this.tipoAccesso = tipoAccesso;
	}
	public String getLatoStrada() {
		return latoStrada;
	}
	public void setLatoStrada(String latoStrada) {
		this.latoStrada = latoStrada;
	}
	public String getAccessibilita() {
		return accessibilita;
	}
	public void setAccessibilita(String accessibilita) {
		this.accessibilita = accessibilita;
	}
	public String getFoglioCt() {
		return foglioCt;
	}
	public void setFoglioCt(String foglioCt) {
		this.foglioCt = foglioCt;
	}
	public String getParticellaCt() {
		return particellaCt;
	}
	public void setParticellaCt(String particellaCt) {
		this.particellaCt = particellaCt;
	}
	public String getDescrDestUso() {
		return descrDestUso;
	}
	public void setDescrDestUso(String descrDestUso) {
		this.descrDestUso = descrDestUso;
	}
	public String getDataInizioVal() {
		return dataInizioVal;
	}
	public void setDataInizioVal(String dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}
	public String getDataFineVal() {
		return dataFineVal;
	}
	public void setDataFineVal(String dataFineVal) {
		this.dataFineVal = dataFineVal;
	}
	public String getFkSezCensimento() {
		return fkSezCensimento;
	}
	public void setFkSezCensimento(String fkSezCensimento) {
		this.fkSezCensimento = fkSezCensimento;
	}
	public String getCodiceCensimento() {
		return codiceCensimento;
	}
	public void setCodiceCensimento(String codiceCensimento) {
		this.codiceCensimento = codiceCensimento;
	}
	public String getDataRecord() {
		return dataRecord;
	}
	public void setDataRecord(String dataRecord) {
		this.dataRecord = dataRecord;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<FabbricatoEcografico> getLstFabbricati() {
		return lstFabbricati;
	}
	public void setLstFabbricati(List<FabbricatoEcografico> lstFabbricati) {
		this.lstFabbricati = lstFabbricati;
	}
	
	public String getCodiceStrada() {
		return codiceStrada;
	}
	public void setCodiceStrada(String codiceStrada) {
		this.codiceStrada = codiceStrada;
	}
	public String getXcentroid() {
		return xcentroid;
	}
	public void setXcentroid(String xcentroid) {
		this.xcentroid = xcentroid;
	}
	public String getYcentroid() {
		return ycentroid;
	}
	public void setYcentroid(String ycentroid) {
		this.ycentroid = ycentroid;
	}
	
	public String getIdFonte() {
		return "29";
	}

	public String getTipoFonte() {
		return "TOPONOMASTICA";
	}

	public String getDiaKey() {
		if (diaKey != null && !diaKey.equals("")) {
			return diaKey;
		}
		diaKey = "";
		if (id != null && !id.equals("")) {
			diaKey += id;
		}
		return diaKey;
	}
	

}
