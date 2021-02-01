package it.webred.rulengine.brick.loadDwh.load.docfa.tablebean;

import java.io.Serializable;
import java.util.Date;

public class DocfaTableDM10 extends DocfaTable implements Serializable {
		
	private Date data;
	private String codEnte;
	private String sezione;
	private Long identificativoImmo;
	private String protocolloReg;
	private Date dataReg;
	private Long progressivoPol;
	private Long superficie;
	private String ambiente;
	private Long altezza;
	private Long altezzaMax;
	
	
	//query di inserimento record
	public static final String INSERT_RECORD = "INSERT INTO DM_DOCFA_1_0 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	public DocfaTableDM10(Date reDataInizioVal, Date reDataFineVal,
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

	public Long getProgressivoPol() {
		return progressivoPol;
	}

	public void setProgressivoPol(Long progressivoPol) {
		this.progressivoPol = progressivoPol;
	}

	public Long getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Long superficie) {
		this.superficie = superficie;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Long getAltezza() {
		return altezza;
	}

	public void setAltezza(Long altezza) {
		this.altezza = altezza;
	}

	public Long getAltezzaMax() {
		return altezzaMax;
	}

	public void setAltezzaMax(Long altezzaMax) {
		this.altezzaMax = altezzaMax;
	}

	
}
