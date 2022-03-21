package it.webred.rulengine.brick.dia.bean.tributidocfa;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DocfaIciReportSoggBean extends DocfaReportSoggBean{
	
	protected Boolean flgTitIciUiuAnteDcf;
	protected Boolean flgTitIciUiuPostDcf;
	protected Boolean flgTitIciCivAnteDcf;
	protected Boolean flgTitIciCivPostDcf;
	protected Boolean flgTitIciAnteDcf;
	protected Boolean flgTitIciPostDcf;
	
	/*protected Boolean flgFamIciUiuAnteDcf;
	protected Boolean flgFamIciUiuPostDcf;
	protected Boolean flgFamIciCivAnteDcf;
	protected Boolean flgFamIciCivPostDcf;
	protected Boolean flgFamIciAnteDcf;
	protected Boolean flgFamIciPostDcf;*/
	
	public static String tabReport = "DOCFA_ICI_REPORT_SOGG";
	
	public static String SQL_CREATE = "CREATE TABLE "+ tabReport +" ( " +
	    SQL_SOGG_FIELDS + ", " +
		"FLG_TIT_ICI_UIU_ANTE             VARCHAR2(1 BYTE), " +
		"FLG_TIT_ICI_UIU_POST             VARCHAR2(1 BYTE), " +
		"FLG_TIT_ICI_CIV_ANTE             VARCHAR2(1 BYTE), " +
		"FLG_TIT_ICI_CIV_POST             VARCHAR2(1 BYTE), " +
		"FLG_TIT_ICI_ANTE                 VARCHAR2(1 BYTE), " +
		"FLG_TIT_ICI_POST                 VARCHAR2(1 BYTE), " +
		SQL_FAMILIARI_FIELDS    + ", "+
	/*	"FLG_FAM_ICI_UIU_ANTE             VARCHAR2(1 BYTE), " +
		"FLG_FAM_ICI_UIU_POST             VARCHAR2(1 BYTE), " +
		"FLG_FAM_ICI_CIV_ANTE             VARCHAR2(1 BYTE), " +
		"FLG_FAM_ICI_CIV_POST             VARCHAR2(1 BYTE), " +	
		"FLG_FAM_ICI_ANTE                 VARCHAR2(1 BYTE), " +
		"FLG_FAM_ICI_POST                 VARCHAR2(1 BYTE), " +*/
		SQL_ELABORAZIONI_FIELDS +")";
	
	public static String[] SQL_INDICI = 
	{"CREATE INDEX IDX_DOCFA_ICI_REP_SOG1 ON DOCFA_ICI_REPORT_SOGG(FK_ID_REP) LOGGING NOPARALLEL"};
	
	public LinkedHashMap<String, Object> getDatiReportSogg() {
		this.datiReportSogg = super.getDatiReportSogg();
		
		datiReportSogg.put("FLG_TIT_ICI_UIU_ANTE", getBooleanField(flgTitIciUiuAnteDcf));
		datiReportSogg.put("FLG_TIT_ICI_UIU_POST", getBooleanField(flgTitIciUiuPostDcf));
		datiReportSogg.put("FLG_TIT_ICI_CIV_ANTE", getBooleanField(flgTitIciCivAnteDcf));
		datiReportSogg.put("FLG_TIT_ICI_CIV_POST", getBooleanField(flgTitIciCivPostDcf));
		datiReportSogg.put("FLG_TIT_ICI_ANTE", getBooleanField(flgTitIciAnteDcf));
		datiReportSogg.put("FLG_TIT_ICI_POST", getBooleanField(flgTitIciPostDcf));
		
		/*datiReportSogg.put("FLG_FAM_ICI_UIU_ANTE", getBooleanField(flgFamIciUiuAnteDcf));
		datiReportSogg.put("FLG_FAM_ICI_UIU_POST", getBooleanField(flgFamIciUiuPostDcf));
		datiReportSogg.put("FLG_FAM_ICI_CIV_ANTE", getBooleanField(flgFamIciCivAnteDcf));
		datiReportSogg.put("FLG_FAM_ICI_CIV_POST", getBooleanField(flgFamIciCivPostDcf ));
		datiReportSogg.put("FLG_FAM_ICI_ANTE", getBooleanField(flgFamIciAnteDcf));
		datiReportSogg.put("FLG_FAM_ICI_POST", getBooleanField(flgFamIciPostDcf));
		*/
		return datiReportSogg;
	}


	public Boolean getFlgTitIciUiuAnteDcf() {
		
		return flgTitIciUiuAnteDcf;
	}

	public void setFlgTitIciUiuAnteDcf(Boolean flgTitIciUiuAnteDcf) {
		this.flgTitIciUiuAnteDcf = flgTitIciUiuAnteDcf;
	}

	public Boolean getFlgTitIciUiuPostDcf() {
		return flgTitIciUiuPostDcf;
	}

	public void setFlgTitIciUiuPostDcf(Boolean flgTitIciUiuPostDcf) {
		this.flgTitIciUiuPostDcf = flgTitIciUiuPostDcf;
	}

	public Boolean getFlgTitIciCivAnteDcf() {
		return flgTitIciCivAnteDcf;
	}

	public void setFlgTitIciCivAnteDcf(Boolean flgTitIciCivAnteDcf) {
		this.flgTitIciCivAnteDcf = flgTitIciCivAnteDcf;
	}

	public Boolean getFlgTitIciCivPostDcf() {
		return flgTitIciCivPostDcf;
	}

	public void setFlgTitIciCivPostDcf(Boolean flgTitIciCivPostDcf) {
		this.flgTitIciCivPostDcf = flgTitIciCivPostDcf;
	}

	public Boolean getFlgTitIciAnteDcf() {
		return flgTitIciAnteDcf;
	}

	public void setFlgTitIciAnteDcf(Boolean flgTitIciAnteDcf) {
		this.flgTitIciAnteDcf = flgTitIciAnteDcf;
	}

	public Boolean getFlgTitIciPostDcf() {
		return flgTitIciPostDcf;
	}

	public void setFlgTitIciPostDcf(Boolean flgTitIciPostDcf) {
		this.flgTitIciPostDcf = flgTitIciPostDcf;
	}
	
}
	
	
	