package it.webred.rulengine.brick.dia.bean.tributidocfa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DocfaTarReportBean extends DocfaReportBean{

	protected BigDecimal maxSupTarsuAnte;
	
	protected BigDecimal minSupTarsuAnte;
	
	protected String consCalc;

	//protected Boolean flgAnomaliaCons;

	protected Boolean flgTarAnteDocfa;

	protected String flgTarPostDocfa;
	
	protected Boolean flgSupTarPostMagPre;
	
	protected Boolean flgSupTarPostMinPre;
	
	protected Boolean flgSupTarPostMagC340;
	
	protected Boolean flgSupTarPostMinC340;

	protected String supAvgMax;

	protected String supAvgMin;

	protected BigDecimal supCatasto;

	protected String supDocfaCens;

	protected BigDecimal supDocfaTarsu;
	
	protected BigDecimal supC340Abc;
	
	protected BigDecimal supDipendenzeEscl;

	public static String tabReport = "DOCFA_TAR_REPORT";
	
	public static String SQL_CREATE = "CREATE TABLE "+ tabReport+" ( " + 
			SQL_DOCFA_FIELDS                              + ", " +
			"SUP_DOCFA_CENS                  VARCHAR2(5 BYTE), " +
			"SUP_C340_ABC                    NUMBER(10,2), " +
			"SUP_DOCFA_TARSU                 NUMBER(10,2), " +
			"SUP_DIPENDENZE_ESCL             NUMBER(10,2), " +
			"SUP_AVG_MIN                     VARCHAR2(10 BYTE), " +
			"SUP_AVG_MAX                     VARCHAR2(10 BYTE), " +
			"CONS_CALC                       VARCHAR2(7 BYTE), " +
			SQL_DATI_GENERALI_FIELDS                      + ", " +
			SQL_DIC_DOCFA_FIELDS                          + ", " +
			SQL_CATASTO_FIELDS                            + ",  " +
			"SUP_CATASTO                     NUMBER(10,2), " +
			"FLG_TAR_ANTE_DOCFA              VARCHAR2(1 BYTE), " + 
			"FLG_TAR_POST_DOCFA              VARCHAR2(1 BYTE), " +
			"FLG_SUP_TAR_POST_MAG_PRE        VARCHAR2(1 BYTE), " +
			"FLG_SUP_TAR_POST_MIN_PRE        VARCHAR2(1 BYTE), " +
			"FLG_SUP_TAR_POST_MAG_C340       VARCHAR2(1 BYTE), " +
			"FLG_SUP_TAR_POST_MIN_C340       VARCHAR2(1 BYTE), " +
			SQL_ELABORAZIONI_FIELDS  + ") ";
	
	public static String[] SQL_INDICI = 
		{"CREATE INDEX IDX_DOCFA_TAR_REP_ID_REP ON DOCFA_TAR_REPORT (ID_REP) LOGGING NOPARALLEL" ,
		"CREATE INDEX IDX_DOCFA_TAR_REP_LPAD1 ON DOCFA_TAR_REPORT (LPAD(FOGLIO,4,'0'), LPAD(PARTICELLA,5,'0')) LOGGING NOPARALLEL" ,
		"CREATE INDEX IDX_DOCFA_TAR_REP_PROT ON DOCFA_TAR_REPORT (PROTOCOLLO_DOCFA) LOGGING NOPARALLEL" ,
		"CREATE INDEX IDX_DOCFA_TAR_REP_PROTUP ON DOCFA_TAR_REPORT (UPPER(PROTOCOLLO_DOCFA)) LOGGING NOPARALLEL",
		"CREATE INDEX SOCFA_TAR_REP_IND2 ON DOCFA_TAR_REPORT (FORNITURA) LOGGING NOPARALLEL"};

	public LinkedHashMap<String, Object> getDati() {
	
		this.dati = super.getDati();
		
		dati.put("SUP_CATASTO", this.supCatasto);
		dati.put("SUP_DOCFA_CENS", this.supDocfaCens);
		dati.put("SUP_C340_ABC", this.supC340Abc);
		dati.put("SUP_DOCFA_TARSU", this.supDocfaTarsu);
		dati.put("SUP_DIPENDENZE_ESCL", this.supDipendenzeEscl);
		
		dati.put("FLG_TAR_ANTE_DOCFA", getBooleanField(flgTarAnteDocfa));
		dati.put("FLG_TAR_POST_DOCFA", this.flgTarPostDocfa);
		
		dati.put("FLG_SUP_TAR_POST_MAG_PRE", getBooleanField(flgSupTarPostMagPre));
		dati.put("FLG_SUP_TAR_POST_MIN_PRE", getBooleanField(flgSupTarPostMinPre));
		dati.put("FLG_SUP_TAR_POST_MAG_C340",getBooleanField(flgSupTarPostMagC340));
		dati.put("FLG_SUP_TAR_POST_MIN_C340",getBooleanField(flgSupTarPostMinC340));
		
		//Controlli classamento
		dati.put("SUP_AVG_MIN", supAvgMin );
		dati.put("SUP_AVG_MAX", supAvgMax );
		dati.put("CONS_CALC",   consCalc);

	//	dati.put("FLG_ANOMALIA_CONS", getBooleanField(flgAnomaliaCons));
		
		return dati;
	}

	public ArrayList<String> getGetNumberFields() {
		
		ArrayList<String> numberFields = getGetNumberFields();
		
		numberFields.add("SUP_DOCFA_TARSU");
		numberFields.add("SUP_C340_ABC");
		numberFields.add("SUP_DIPENDENZE_ESCL");
		
		return numberFields;
		
	}
	
	public String getConsCalc() {
		return consCalc;
	}

	public void setConsCalc(String consCalc) {
		this.consCalc = consCalc;
	}

	public Boolean getFlgTarAnteDocfa() {
		return flgTarAnteDocfa;
	}

	public void setFlgTarAnteDocfa(Boolean flgTarAnteDocfa) {
		this.flgTarAnteDocfa = flgTarAnteDocfa;
	}

	public String getFlgTarPostDocfa() {
		return flgTarPostDocfa;
	}

	public void setFlgTarPostDocfa(String flgTarPostDocfa) {
		/*if ( flgTarPostDocfa20!= null)
			this.flgTarPostDocfa = flgTarPostDocfa20 ? "1" : "0";
		else if (flgTarPostDocfaDS != null)
			this.flgTarPostDocfa = flgTarPostDocfaDS ? "2" : "0";
		else
			this.flgTarPostDocfa =  null;*/
		this.flgTarPostDocfa = flgTarPostDocfa;
	}

	public String getSupAvgMax() {
		return supAvgMax;
	}

	public void setSupAvgMax(String supAvgMax) {
		this.supAvgMax = supAvgMax;
	}

	public String getSupAvgMin() {
		return supAvgMin;
	}

	public void setSupAvgMin(String supAvgMin) {
		this.supAvgMin = supAvgMin;
	}

	public BigDecimal getSupCatasto() {
		return supCatasto;
	}

	public void setSupCatasto(BigDecimal supCatasto) {
		this.supCatasto = supCatasto;
	}

	public String getSupDocfaCens() {
		return supDocfaCens;
	}

	public void setSupDocfaCens(String supDocfaCens) {
		this.supDocfaCens = supDocfaCens;
	}

	public BigDecimal getMaxSupTarsuAnte() {
		return maxSupTarsuAnte;
	}

	public void setMaxSupTarsuAnte(BigDecimal maxSupTarsuAnte) {
		this.maxSupTarsuAnte = maxSupTarsuAnte;
	}

	public Boolean getFlgSupTarPostMagPre() {
		return flgSupTarPostMagPre;
	}

	public void setFlgSupTarPostMagPre(Boolean flgSupTarPostMagPre) {
		this.flgSupTarPostMagPre = flgSupTarPostMagPre;
	}

	public Boolean getFlgSupTarPostMinPre() {
		return flgSupTarPostMinPre;
	}

	public void setFlgSupTarPostMinPre(Boolean flgSupTarPostMinPre) {
		this.flgSupTarPostMinPre = flgSupTarPostMinPre;
	}

	public Boolean getFlgSupTarPostMagC340() {
		return flgSupTarPostMagC340;
	}

	public void setFlgSupTarPostMagC340(Boolean flgSupTarPostMagC340) {
		this.flgSupTarPostMagC340 = flgSupTarPostMagC340;
	}

	public Boolean getFlgSupTarPostMinC340() {
		return flgSupTarPostMinC340;
	}

	public void setFlgSupTarPostMinC340(Boolean flgSupTarPostMinC340) {
		this.flgSupTarPostMinC340 = flgSupTarPostMinC340;
	}
	
	public BigDecimal getMinSupTarsuAnte() {
		return minSupTarsuAnte;
	}

	public void setMinSupTarsuAnte(BigDecimal minSupTarsuAnte) {
		this.minSupTarsuAnte = minSupTarsuAnte;
	}

	public BigDecimal getSupDocfaTarsu() {
		return supDocfaTarsu;
	}

	public void setSupDocfaTarsu(BigDecimal supDocfaTarsu) {
		this.supDocfaTarsu = supDocfaTarsu;
	}


	public BigDecimal getSupC340Abc() {
		return supC340Abc;
	}

	public void setSupC340Abc(BigDecimal supC340Abc) {
		this.supC340Abc = supC340Abc;
	}
	
	public BigDecimal getSupDipendenzeEscl() {
		return supDipendenzeEscl;
	}

	public void setSupDipendenzeEscl(BigDecimal supDipendenzeEscl) {
		this.supDipendenzeEscl = supDipendenzeEscl;
	}

}