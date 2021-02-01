package it.webred.rulengine.brick.loadDwh.load.docfa.tablebean;

import java.io.Serializable;
import java.util.Date;

public class DocfaTablePL10 extends DocfaTable implements Serializable {

	private Date data;
	private String codEnte;
	private String sezione;
	private Long identificativoImmo;
	private String protocolloReg;
	private Date dataReg;
	private String nomePlan;
	private Long progressivo;	
	private Long formato;
	private Long scala;
	
	
	//query di inserimento record
	public static final String INSERT_RECORD = "INSERT INTO PL_DOCFA_1_0 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
		
	public DocfaTablePL10(Date reDataInizioVal, Date reDataFineVal,
			Long reFlagElaborato) {
		super(reDataInizioVal, reDataFineVal, reFlagElaborato);
		// TODO Auto-generated constructor stub
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public String getCodEnte() {
		return codEnte;
	}


	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}


	public String getSezione() {
		return sezione;
	}


	public void setSezione(String sezione) {
		this.sezione = sezione;
	}


	public Long getIdentificativoImmo() {
		return identificativoImmo;
	}


	public void setIdentificativoImmo(Long identificativoImmo) {
		this.identificativoImmo = identificativoImmo;
	}


	public String getProtocolloReg() {
		return protocolloReg;
	}


	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}


	public Date getDataReg() {
		return dataReg;
	}


	public void setDataReg(Date dataReg) {
		this.dataReg = dataReg;
	}


	public String getNomePlan() {
		return nomePlan;
	}


	public void setNomePlan(String nomePlan) {
		this.nomePlan = nomePlan;
	}


	public Long getProgressivo() {
		return progressivo;
	}


	public void setProgressivo(Long progressivo) {
		this.progressivo = progressivo;
	}


	public Long getFormato() {
		return formato;
	}


	public void setFormato(Long formato) {
		this.formato = formato;
	}


	public Long getScala() {
		return scala;
	}


	public void setScala(Long scala) {
		this.scala = scala;
	}
	

}
