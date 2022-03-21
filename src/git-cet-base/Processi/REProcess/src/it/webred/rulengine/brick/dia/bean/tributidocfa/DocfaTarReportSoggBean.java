package it.webred.rulengine.brick.dia.bean.tributidocfa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

public class DocfaTarReportSoggBean extends DocfaReportSoggBean{
	
	protected Boolean flgTitTarUiuAnteDcf;
	protected String flgTitTarUiuPostDcf;
	protected Boolean flgTitTarCivAnteDcf;
	protected String flgTitTarCivPostDcf;
	protected Boolean flgTitTarAnteDcf;
	protected String flgTitTarPostDcf;
	
	protected Boolean flgFamTarUiuAnteDcf;
	protected String flgFamTarUiuPostDcf;
	protected Boolean flgFamTarCivAnteDcf;
	protected String flgFamTarCivPostDcf;
	protected Boolean flgFamTarAnteDcf;
	protected String flgFamTarPostDcf;
	
	/*protected Boolean flgTarAnteDocfa;
	protected String flgTarPostDocfa;*/
	
	public static String tabReport = "DOCFA_TAR_REPORT_SOGG";
	
	public static String SQL_CREATE = "CREATE TABLE "+ tabReport +" ( " +
		    SQL_SOGG_FIELDS + ", " +
		    "FLG_TIT_TAR_UIU_ANTE             VARCHAR2(1 BYTE), " +
			"FLG_TIT_TAR_UIU_POST             VARCHAR2(1 BYTE), " +
			"FLG_TIT_TAR_CIV_ANTE             VARCHAR2(1 BYTE), " +
			"FLG_TIT_TAR_CIV_POST             VARCHAR2(1 BYTE), " +
			"FLG_TIT_TAR_ANTE                 VARCHAR2(1 BYTE), " +
			"FLG_TIT_TAR_POST                 VARCHAR2(1 BYTE), " +
			SQL_FAMILIARI_FIELDS    + ", "+
			"FLG_FAM_TAR_UIU_ANTE             VARCHAR2(1 BYTE), " +
			"FLG_FAM_TAR_UIU_POST             VARCHAR2(1 BYTE), " +
			"FLG_FAM_TAR_CIV_ANTE             VARCHAR2(1 BYTE), " +
			"FLG_FAM_TAR_CIV_POST             VARCHAR2(1 BYTE), " +	
			"FLG_FAM_TAR_ANTE                 VARCHAR2(1 BYTE), " +
			"FLG_FAM_TAR_POST                 VARCHAR2(1 BYTE), " +
			SQL_ELABORAZIONI_FIELDS +")";
		
	/*private String SQL_CREATE_DOCFA_TAR_REPORT_SOGG = "CREATE TABLE "
		+ this.tabReportSogg + "( "
		+ "FK_ID_REP                          VARCHAR2(50 BYTE) NOT NULL, "
		+ "CAT_PKID                         	NUMBER(9), "
		+ "PK_CUAA                         	NUMBER(9), "
		+ "CODI_FISC                      	VARCHAR2(16 BYTE), "
		+ "CODI_PIVA                       	VARCHAR2(11 BYTE), "
		+ "FLG_PERS_FISICA                	VARCHAR2(1 BYTE), "
		+ "RAGI_SOCI                       	VARCHAR2(2000 BYTE), "
		+ "NOME                            	VARCHAR2(50 BYTE), "
		+ "SESSO                           	VARCHAR2(1 BYTE), "
		+ "DATA_NASC                       	DATE, "
		+ "DATA_MORTE                      	DATE, "
		+ "COMU_NASC                       	VARCHAR2(4 BYTE), "
		+ "PERC_POSS                       	NUMBER(6,2), "
		+ "DATA_INIZIO_POSSESSO             DATE, "
		+ "DATA_FINE_POSSESSO              	DATE, "
		+ "FLG_TITOLARE_DATA_DOCFA        	VARCHAR2(1 BYTE), "
		+ "FLG_TITOLARE_3112        	    VARCHAR2(1 BYTE), "
		+ "FLG_RESID_IND_DCF_DATADCF    	VARCHAR2(1 BYTE), "
		+ "FLG_RESID_IND_CAT_DATADCF 	    VARCHAR2(1 BYTE), "
		+ "FLG_TAR_ANTE_DOCFA               VARCHAR2(1 BYTE), "
		+ "FLG_TAR_POST_DOCFA               VARCHAR2(1 BYTE), "
		+ "FLG_ANOMALIA                   	VARCHAR2(1 BYTE), "
		+ "DATA_AGGIORNAMENTO               DATE  DEFAULT SYSDATE, " 
		+ "COD_ANOMALIE                     VARCHAR(200 BYTE), " 
		+ "ANNOTAZIONI                     	VARCHAR2(4000 BYTE))";*/
	
	public static String[] SQL_INDICI = 
	{"CREATE INDEX IDX_DOCFA_TAR_REP_SOG1 ON DOCFA_TAR_REPORT_SOGG (FK_ID_REP) LOGGING NOPARALLEL"};
	
	public LinkedHashMap<String,Object> getDatiReportSogg() {
		this.datiReportSogg = super.getDatiReportSogg();
		
		datiReportSogg.put("FLG_TIT_TAR_UIU_ANTE", getBooleanField(this.flgTitTarUiuAnteDcf));
		datiReportSogg.put("FLG_TIT_TAR_CIV_ANTE", getBooleanField(this.flgTitTarCivAnteDcf));
		datiReportSogg.put("FLG_TIT_TAR_ANTE", getBooleanField(this.flgTitTarAnteDcf));
		
		datiReportSogg.put("FLG_TIT_TAR_UIU_POST", flgTitTarUiuPostDcf);
		datiReportSogg.put("FLG_TIT_TAR_CIV_POST", flgTitTarCivPostDcf);
		datiReportSogg.put("FLG_TIT_TAR_POST", flgTitTarPostDcf);
		
		datiReportSogg.put("FLG_FAM_TAR_UIU_ANTE", getBooleanField(this.flgFamTarUiuAnteDcf));
		datiReportSogg.put("FLG_FAM_TAR_CIV_ANTE", getBooleanField(this.flgFamTarCivAnteDcf));
		datiReportSogg.put("FLG_FAM_TAR_ANTE", getBooleanField(this.flgFamTarAnteDcf));
		
		datiReportSogg.put("FLG_FAM_TAR_UIU_POST", flgFamTarUiuPostDcf);
		datiReportSogg.put("FLG_FAM_TAR_CIV_POST", flgFamTarCivPostDcf);
		datiReportSogg.put("FLG_FAM_TAR_POST", flgFamTarPostDcf);
		
		return datiReportSogg;
	}


	public Boolean getFlgTitTarUiuAnteDcf() {
		return flgTitTarUiuAnteDcf;
	}


	public void setFlgTitTarUiuAnteDcf(Boolean flgTitTarUiuAnteDcf) {
		this.flgTitTarUiuAnteDcf = flgTitTarUiuAnteDcf;
	}


	public String getFlgTitTarUiuPostDcf() {
		return flgTitTarUiuPostDcf;
	}


	public void setFlgTitTarUiuPostDcf(String flgTitTarUiuPostDcf) {
		this.flgTitTarUiuPostDcf = flgTitTarUiuPostDcf;
	}


	public Boolean getFlgTitTarCivAnteDcf() {
		return flgTitTarCivAnteDcf;
	}


	public void setFlgTitTarCivAnteDcf(Boolean flgTitTarCivAnteDcf) {
		this.flgTitTarCivAnteDcf = flgTitTarCivAnteDcf;
	}


	public String getFlgTitTarCivPostDcf() {
		return flgTitTarCivPostDcf;
	}


	public void setFlgTitTarCivPostDcf(String flgTitTarCivPostDcf) {
		this.flgTitTarCivPostDcf = flgTitTarCivPostDcf;
	}


	public Boolean getFlgTitTarAnteDcf() {
		return flgTitTarAnteDcf;
	}


	public void setFlgTitTarAnteDcf(Boolean flgTitTarAnteDcf) {
		this.flgTitTarAnteDcf = flgTitTarAnteDcf;
	}


	public String getFlgTitTarPostDcf() {
		return flgTitTarPostDcf;
	}


	public void setFlgTitTarPostDcf(String flgTitTarPostDcf) {
		this.flgTitTarPostDcf = flgTitTarPostDcf;
	}


	public Boolean getFlgFamTarUiuAnteDcf() {
		return flgFamTarUiuAnteDcf;
	}


	public void setFlgFamTarUiuAnteDcf(Boolean flgFamTarUiuAnteDcf) {
		this.flgFamTarUiuAnteDcf = flgFamTarUiuAnteDcf;
	}


	public String getFlgFamTarUiuPostDcf() {
		return flgFamTarUiuPostDcf;
	}


	public void setFlgFamTarUiuPostDcf(String flgFamTarUiuPostDcf) {
		this.flgFamTarUiuPostDcf = flgFamTarUiuPostDcf;
	}


	public Boolean getFlgFamTarCivAnteDcf() {
		return flgFamTarCivAnteDcf;
	}


	public void setFlgFamTarCivAnteDcf(Boolean flgFamTarCivAnteDcf) {
		this.flgFamTarCivAnteDcf = flgFamTarCivAnteDcf;
	}


	public String getFlgFamTarCivPostDcf() {
		return flgFamTarCivPostDcf;
	}


	public void setFlgFamTarCivPostDcf(String flgFamTarCivPostDcf) {
		this.flgFamTarCivPostDcf = flgFamTarCivPostDcf;
	}


	public Boolean getFlgFamTarAnteDcf() {
		return flgFamTarAnteDcf;
	}


	public void setFlgFamTarAnteDcf(Boolean flgFamTarAnteDcf) {
		this.flgFamTarAnteDcf = flgFamTarAnteDcf;
	}


	public String getFlgFamTarPostDcf() {
		return flgFamTarPostDcf;
	}


	public void setFlgFamTarPostDcf(String flgFamTarPostDcf) {
		this.flgFamTarPostDcf = flgFamTarPostDcf;
	}


	
    
    
    
}