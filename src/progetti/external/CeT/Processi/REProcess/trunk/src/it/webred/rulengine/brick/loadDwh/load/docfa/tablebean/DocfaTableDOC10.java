package it.webred.rulengine.brick.loadDwh.load.docfa.tablebean;

import java.io.Serializable;
import java.util.Date;

public class DocfaTableDOC10 extends DocfaTable implements Serializable{
	
	private String codEnte;
	private Date data;
	private String protocollo;
	private String tipoRecord;
	private String rigaDettaglio;

	
	//query di inserimento record
	public static final String INSERT_RECORD = "INSERT INTO DOC_DOCFA_1_0 VALUES (?,?,?,?,?,?,?,?)";
	
	
	public DocfaTableDOC10(Date reDataInizioVal, Date reDataFineVal,
			Long reFlagElaborato) {
		
		super(reDataInizioVal, reDataFineVal, reFlagElaborato);
		// TODO Auto-generated constructor stub
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getTipoRecord() {
		return tipoRecord;
	}

	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
	}

	public String getRigaDettaglio() {
		return rigaDettaglio;
	}

	public void setRigaDettaglio(String rigaDettaglio) {
		this.rigaDettaglio = rigaDettaglio;
	}
	
	
}
