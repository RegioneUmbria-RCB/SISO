package it.webred.rulengine.brick.dia.bean.tributidocfa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;


public class DocfaIciReportBean extends DocfaReportBean{
	
	protected BigDecimal imponibileUiuPre;
	
	protected BigDecimal imponibileUiuPost;
	
	protected BigDecimal varImponibileUiu;
	
	protected BigDecimal sumImponibilePre;
	
	protected BigDecimal sumImponibilePost;
	
	protected BigDecimal varPercSumImponibile;
	
	protected String classeDocfa;
	
	protected String classeCatasto;

	protected String classeMin;

	protected String flgAnomaliaClasse;

	protected Boolean flgIciAnteDocfa;

	protected Boolean flgIciPostDocfa;

	protected Boolean flgVarAnteCategoria;

	protected Boolean flgVarAnteClasse;
	
	protected Boolean flgVarAnteRendita;

	protected BigDecimal maxDimRenIci2Dcf;
	
	protected BigDecimal maxAugRenIci2Dcf;
	
	protected BigDecimal maxDimPercRenIci2Dcf;
	
	protected BigDecimal maxAugPercRenIci2Dcf;

	protected Boolean flgVarPostCategoria;

	protected Boolean flgVarPostClasse;

	protected Boolean flgVarPostRendita;
	
	protected BigDecimal maxDimRenDcf2Ici;
	
	protected BigDecimal maxAugRenDcf2Ici;
	
	protected BigDecimal maxDimPercRenDcf2Ici;
	
	protected BigDecimal maxAugPercRenDcf2Ici;

	protected BigDecimal renditaCatasto;

	protected BigDecimal renditaDocfa;
	
	public static String tabReport = "DOCFA_ICI_REPORT";
	
	public static String SQL_CREATE = "CREATE TABLE "+ tabReport+" ( " + 
		SQL_DOCFA_FIELDS                              + ", " +
	    "CLASSE_DOCFA                    VARCHAR2(2 BYTE), " +
	    "RENDITA_DOCFA                   NUMBER(13,2), " +
		"CLASSE_MIN                      VARCHAR2(2 BYTE), " +
		"FLG_ANOMALIA_CLASSE             VARCHAR2(1 BYTE), " +
		SQL_DATI_GENERALI_FIELDS                      + ", " +
		SQL_DIC_DOCFA_FIELDS                          + ", " +
		SQL_CATASTO_FIELDS                            + ",  " +
	    "CLASSE_CATASTO                  VARCHAR2(2 BYTE), " +
	    "RENDITA_CATASTO                 NUMBER(15,3), " +
	    "IMPONIBILE_UIU_PRE              NUMBER(15,2), " +
	    "IMPONIBILE_UIU_POST             NUMBER(15,2), " +
	    "VAR_IMPONIBILE_UIU              NUMBER(15,2), " +
	    "SUM_IMPONIBILE_PRE          	 NUMBER(15,2), " +
	    "SUM_IMPONIBILE_POST         	 NUMBER(15,2), " +
	    "VAR_PERC_SUM_IMPONIBILE         NUMBER(15,2), " +
		"FLG_ICI_ANTE_DOCFA              VARCHAR2(1 BYTE), " +
		"FLG_VAR_ANTE_RENDITA            VARCHAR2(1 BYTE), " +
		"FLG_VAR_ANTE_CATEGORIA          VARCHAR2(1 BYTE), " +
		"FLG_VAR_ANTE_CLASSE             VARCHAR2(1 BYTE), " +
		"MAX_DIM_REN_ICI_2_DCF           NUMBER(13,2), " +	 
		"MAX_AUG_REN_ICI_2_DCF           NUMBER(13,2), " +	 
		"MAX_DIM_PERC_REN_ICI_2_DCF      NUMBER(13,2), " +	 
		"MAX_AUG_PERC_REN_ICI_2_DCF      NUMBER(13,2), " +	 
		//"DOCFA_SUCCESSIVI                VARCHAR2(10 BYTE), " +
		"FLG_ICI_POST_DOCFA              VARCHAR2(1 BYTE), " +
		"FLG_VAR_POST_RENDITA            VARCHAR2(1 BYTE), " +
		"FLG_VAR_POST_CATEGORIA          VARCHAR2(1 BYTE), " +
		"FLG_VAR_POST_CLASSE             VARCHAR2(1 BYTE), " +
		"MAX_DIM_REN_DCF_2_ICI           NUMBER(13,2), " +	 
		"MAX_AUG_REN_DCF_2_ICI           NUMBER(13,2), " +	 
		"MAX_DIM_PERC_REN_DCF_2_ICI      NUMBER(13,2), " +	 
		"MAX_AUG_PERC_REN_DCF_2_ICI      NUMBER(13,2), " +	 
		SQL_ELABORAZIONI_FIELDS  + ") ";
	
	public static String[] SQL_INDICI = 
		{"CREATE INDEX IDX_DOCFA_ICI_REP_ID_REP ON DOCFA_ICI_REPORT (ID_REP) LOGGING NOPARALLEL",
		
		"CREATE INDEX IDX_DOCFA_ICI_REP_LPAD1 ON DOCFA_ICI_REPORT (LPAD(FOGLIO,4,'0'), LPAD(PARTICELLA,5,'0')) LOGGING NOPARALLEL",
		
		"CREATE INDEX IDX_DOCFA_ICI_REP_PROT ON DOCFA_ICI_REPORT (PROTOCOLLO_DOCFA) LOGGING NOPARALLEL",
		
		"CREATE INDEX IDX_DOCFA_ICI_REP_PROTUP ON DOCFA_ICI_REPORT (UPPER(PROTOCOLLO_DOCFA)) LOGGING NOPARALLEL",
		
		"CREATE INDEX SOCFA_ICI_REP_IND1 ON DOCFA_ICI_REPORT (FLG_ELABORATO) LOGGING NOPARALLEL",
		
		"CREATE INDEX SOCFA_ICI_REP_IND2 ON DOCFA_ICI_REPORT (FORNITURA) LOGGING NOPARALLEL"} ;
	
	public DocfaIciReportBean() {
		super();
	}
	
	
	public  ArrayList<String> getGetNumberFields() {
		
		ArrayList<String> numberFields = super.getGetNumberFields();
		
		numberFields.add("RENDITA_DOCFA");
		numberFields.add("RENDITA_CATASTO");
		
		numberFields.add("IMPONIBILE_UIU_PRE");
		numberFields.add("IMPONIBILE_UIU_POST");
		numberFields.add("VAR_IMPONIBILE_UIU");
		
		numberFields.add("SUM_IMPONIBILE_PRE");
		numberFields.add("SUM_IMPONIBILE_POST");
		
		numberFields.add("VAR_PERC_SUM_IMPONIBILE");
		
		numberFields.add("MAX_DIM_REN_ICI_2_DCF");
		numberFields.add("MAX_AUG_REN_ICI_2_DCF");
		numberFields.add("MAX_DIM_PERC_REN_ICI_2_DCF");
		numberFields.add("MAX_AUG_PERC_REN_ICI_2_DCF");
		
		numberFields.add("MAX_DIM_REN_DCF_2_ICI");
		numberFields.add("MAX_AUG_REN_DCF_2_ICI");
		numberFields.add("MAX_DIM_PERC_REN_DCF_2_ICI");
		numberFields.add("MAX_AUG_PERC_REN_DCF_2_ICI");
		
		return numberFields;
	}
		
	
	
	public  LinkedHashMap<String, Object> getDati() {
		LinkedHashMap<String, Object> dati = super.getDati();
		
		//dati.put("DOCFA_SUCCESSIVI", docfaSuccessivi);
		
		dati.put("CLASSE_CATASTO",classeCatasto);
		dati.put("CLASSE_DOCFA", classeDocfa);
		dati.put("CLASSE_MIN", classeMin);
		dati.put("RENDITA_CATASTO", renditaCatasto);
		dati.put("RENDITA_DOCFA", renditaDocfa);
		
		dati.put("IMPONIBILE_UIU_PRE", imponibileUiuPre);
		dati.put("IMPONIBILE_UIU_POST", imponibileUiuPost);
		dati.put("VAR_IMPONIBILE_UIU", varImponibileUiu);
	
		dati.put("FLG_ICI_ANTE_DOCFA", getBooleanField(flgIciAnteDocfa));
		dati.put("FLG_VAR_ANTE_CATEGORIA", getBooleanField(flgVarAnteCategoria));
		dati.put("FLG_VAR_ANTE_CLASSE", getBooleanField(flgVarAnteClasse));
		dati.put("FLG_VAR_ANTE_RENDITA", getBooleanField(flgVarAnteRendita));
		dati.put("MAX_DIM_REN_ICI_2_DCF", getMaxDimRenIci2Dcf());
		dati.put("MAX_AUG_REN_ICI_2_DCF", getMaxAugRenIci2Dcf());
		dati.put("MAX_DIM_PERC_REN_ICI_2_DCF", getMaxDimPercRenIci2Dcf());
		dati.put("MAX_AUG_PERC_REN_ICI_2_DCF", getMaxAugPercRenIci2Dcf());
		
		dati.put("FLG_ICI_POST_DOCFA", getBooleanField(flgIciPostDocfa));
		dati.put("FLG_VAR_POST_CATEGORIA", getBooleanField(flgVarPostCategoria));
		dati.put("FLG_VAR_POST_CLASSE", getBooleanField(flgVarPostClasse));
		dati.put("FLG_VAR_POST_RENDITA", getBooleanField(flgVarPostRendita));
		dati.put("MAX_DIM_REN_DCF_2_ICI", getMaxDimRenDcf2Ici());
		dati.put("MAX_AUG_REN_DCF_2_ICI", getMaxAugRenDcf2Ici());
		dati.put("MAX_DIM_PERC_REN_DCF_2_ICI", getMaxDimPercRenDcf2Ici());
		dati.put("MAX_AUG_PERC_REN_DCF_2_ICI", getMaxAugPercRenDcf2Ici());
		
		dati.put("FLG_ANOMALIA_CLASSE", flgAnomaliaClasse);
		
		return dati;
	}

	public String getClasseCatasto() {
		return classeCatasto;
	}

	public void setClasseCatasto(String classeCatasto) {
		this.classeCatasto = classeCatasto;
	}

	public String getClasseDocfa() {
		return classeDocfa;
	}

	public void setClasseDocfa(String classeDocfa) {
		this.classeDocfa = classeDocfa;
	}

	public String getClasseMin() {
		return classeMin;
	}

	public void setClasseMin(String classeMin) {
		this.classeMin = classeMin;
	}

	public Boolean getFlgIciAnteDocfa() {
		return flgIciAnteDocfa;
	}

	public void setFlgIciAnteDocfa(Boolean flgIciAnteDocfa) {
		this.flgIciAnteDocfa = flgIciAnteDocfa;
	}

	public Boolean getFlgIciPostDocfa() {
		return flgIciPostDocfa;
	}

	public void setFlgIciPostDocfa(Boolean flgIciPostDocfa) {
		this.flgIciPostDocfa = flgIciPostDocfa;
	}

	public Boolean getFlgVarAnteCategoria() {
		return flgVarAnteCategoria;
	}

	public void setFlgVarAnteCategoria(Boolean flgVarAnteCategoria) {
		this.flgVarAnteCategoria = flgVarAnteCategoria;
	}

	public Boolean getFlgVarAnteClasse() {
		return flgVarAnteClasse;
	}

	public void setFlgVarAnteClasse(Boolean flgVarAnteClasse) {
		this.flgVarAnteClasse = flgVarAnteClasse;
	}

	public Boolean getFlgVarAnteRendita() {
		return flgVarAnteRendita;
	}

	public void setFlgVarAnteRendita(Boolean flgVarAnteRendita) {
		this.flgVarAnteRendita = flgVarAnteRendita;
	}

	public Boolean getFlgVarPostCategoria() {
		return flgVarPostCategoria;
	}

	public BigDecimal getMaxDimRenIci2Dcf() {
		return maxDimRenIci2Dcf;
	}

	public void setMaxDimRenIci2Dcf(BigDecimal maxDimRenIci2Dcf) {
		this.maxDimRenIci2Dcf = maxDimRenIci2Dcf;
	}

	public BigDecimal getMaxAugRenIci2Dcf() {
		return maxAugRenIci2Dcf;
	}

	public void setMaxAugRenIci2Dcf(BigDecimal maxAugRenIci2Dcf) {
		this.maxAugRenIci2Dcf = maxAugRenIci2Dcf;
	}

	public BigDecimal getMaxDimRenDcf2Ici() {
		return maxDimRenDcf2Ici;
	}

	public void setMaxDimRenDcf2Ici(BigDecimal maxDimRenDcf2Ici) {
		this.maxDimRenDcf2Ici = maxDimRenDcf2Ici;
	}

	public BigDecimal getMaxDimPercRenIci2Dcf() {
		return maxDimPercRenIci2Dcf;
	}

	public void setMaxDimPercRenIci2Dcf(BigDecimal maxDimPercRenIci2Dcf) {
		this.maxDimPercRenIci2Dcf = maxDimPercRenIci2Dcf;
	}

	public void setRenditaCatasto(BigDecimal renditaCatasto) {
		this.renditaCatasto = renditaCatasto;
	}

	public void setRenditaDocfa(BigDecimal renditaDocfa) {
		this.renditaDocfa = renditaDocfa;
	}

	public BigDecimal getMaxAugPercRenIci2Dcf() {
		return maxAugPercRenIci2Dcf;
	}

	public void setMaxAugPercRenIci2Dcf(BigDecimal maxAugPercRenIci2Dcf) {
		this.maxAugPercRenIci2Dcf = maxAugPercRenIci2Dcf;
	}

	public BigDecimal getMaxDimPercRenDcf2Ici() {
		return maxDimPercRenDcf2Ici;
	}

	public void setMaxDimPercRenDcf2Ici(BigDecimal maxDimPercRenDcf2Ici) {
		this.maxDimPercRenDcf2Ici = maxDimPercRenDcf2Ici;
	}

	public BigDecimal getMaxAugPercRenDcf2Ici() {
		return maxAugPercRenDcf2Ici;
	}

	public void setMaxAugPercRenDcf2Ici(BigDecimal maxAugPercRenDcf2Ici) {
		this.maxAugPercRenDcf2Ici = maxAugPercRenDcf2Ici;
	}

	public BigDecimal getMaxAugRenDcf2Ici() {
		return maxAugRenDcf2Ici;
	}

	public void setMaxAugRenDcf2Ici(BigDecimal maxAugRenDcf2Ici) {
		this.maxAugRenDcf2Ici = maxAugRenDcf2Ici;
	}

	public void setFlgVarPostCategoria(Boolean flgVarPostCategoria) {
		this.flgVarPostCategoria = flgVarPostCategoria;
	}

	public Boolean getFlgVarPostClasse() {
		return flgVarPostClasse;
	}

	public void setFlgVarPostClasse(Boolean flgVarPostClasse) {
		this.flgVarPostClasse = flgVarPostClasse;
	}

	public Boolean getFlgVarPostRendita() {
		return flgVarPostRendita;
	}

	public void setFlgVarPostRendita(Boolean flgVarPostRendita) {
		this.flgVarPostRendita = flgVarPostRendita;
	}

	public void setFlgAnomaliaClasse(String flgAnomaliaClasse) {
		this.flgAnomaliaClasse = flgAnomaliaClasse;
	}

	public String getFlgAnomaliaClasse() {
		return flgAnomaliaClasse;
	}

	public BigDecimal getRenditaCatasto() {
		return renditaCatasto;
	}

	public BigDecimal getRenditaDocfa() {
		return renditaDocfa;
	}


	public BigDecimal getImponibileUiuPre() {
		return imponibileUiuPre;
	}


	public void setImponibileUiuPre(BigDecimal imponibileUiuPre) {
		this.imponibileUiuPre = imponibileUiuPre;
	}


	public BigDecimal getImponibileUiuPost() {
		return imponibileUiuPost;
	}


	public void setImponibileUiuPost(BigDecimal imponibileUiuPost) {
		this.imponibileUiuPost = imponibileUiuPost;
	}


	public BigDecimal getSumImponibilePre() {
		return sumImponibilePre;
	}


	public void setSumImponibilePre(BigDecimal sumImponibilePre) {
		this.sumImponibilePre = sumImponibilePre;
	}


	public BigDecimal getSumImponibilePost() {
		return sumImponibilePost;
	}


	public void setSumImponibilePost(BigDecimal sumImponibilePost) {
		this.sumImponibilePost = sumImponibilePost;
	}


	public BigDecimal getVarPercSumImponibile() {
		return varPercSumImponibile;
	}


	public void setDimPercSumImponibile(BigDecimal varPercSumImponibile) {
		this.varPercSumImponibile = varPercSumImponibile;
	}

	public BigDecimal getVarImponibileUiu() {
		return varImponibileUiu;
	}


	public void setVarImponibileUiu(BigDecimal varImponibileUiu) {
		this.varImponibileUiu = varImponibileUiu;
	}


	
}
	