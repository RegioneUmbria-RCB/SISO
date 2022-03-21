package it.webred.rulengine.brick.dia.bean.tributidocfa;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DocfaReportSoggBean {
	
	protected LinkedHashMap <String, Object> datiReportSogg;
	
	protected String fkIdRep;
	protected String catPkid;
	protected BigDecimal pkCuaa;
	protected String flgPersFisica;
	protected String codiFisc;
	protected String codiPiva;
	protected String ragiSoci;
	protected String nome;
	protected String sesso;
	protected String comuNasc;
	protected Date dataNasc;
	protected Date dataMorte;
	protected String titolo;
	protected BigDecimal percPoss;
	protected Date dataInizioPossesso;
	protected Date dataFinePossesso;
	protected Boolean flgTitolareDataDocfa;
	protected Boolean flgTitolare3112;
	protected Boolean flgResidIndDcf3112;
	protected Boolean flgResidIndCat3112;
	
	protected static String SQL_SOGG_FIELDS = 
		"FK_ID_REP                        VARCHAR2(50 BYTE) NOT NULL, " +
		"CAT_PKID                         NUMBER(9), " +
		"PK_CUAA                          NUMBER(9), " +
		"FLG_PERS_FISICA                  VARCHAR2(1 BYTE), " +
		"CODI_FISC                        VARCHAR2(16 BYTE), " +
		"CODI_PIVA                        VARCHAR2(11 BYTE), " +
		"RAGI_SOCI                        VARCHAR2(2000 BYTE), " +
		"NOME                             VARCHAR2(50 BYTE), " +
		"SESSO                            VARCHAR2(1 BYTE), " +
		"COMU_NASC                        VARCHAR2(4 BYTE), "+
		"DATA_NASC                        DATE, " +
		"DATA_MORTE                       DATE, " +
		"TITOLO                           VARCHAR2(50 BYTE), "+
		"PERC_POSS                        NUMBER(6,2), " +
		"DATA_INIZIO_POSSESSO             DATE, " +
		"DATA_FINE_POSSESSO               DATE, " +
		"FLG_TITOLARE_DATA_DOCFA          VARCHAR2(1 BYTE), " +
		"FLG_TITOLARE_3112                VARCHAR2(1 BYTE), " +
		"FLG_RESID_IND_DCF_3112           VARCHAR2(1 BYTE), " +
		"FLG_RESID_IND_CAT_3112           VARCHAR2(1 BYTE) " ;
		
		
	protected BigDecimal numFamiliari; 
	protected static String SQL_FAMILIARI_FIELDS = "NUM_FAMILIARI                    NUMBER(9) " ;

	protected Boolean flgAnomalia;
	protected ArrayList<String> codAnomalie;
	protected ArrayList<String> annotazioni;
	
	protected static String SQL_ELABORAZIONI_FIELDS = 
		"FLG_ANOMALIA                     VARCHAR2(1 BYTE), " +
		"COD_ANOMALIE                     VARCHAR(200 BYTE), " +
		"ANNOTAZIONI                      VARCHAR2(4000 BYTE), " +
		"DATA_AGGIORNAMENTO               DATE  DEFAULT SYSDATE ";
		

    public DocfaReportSoggBean() {
    	datiReportSogg = new LinkedHashMap <String, Object>();
    	codAnomalie = new ArrayList<String>();
    	annotazioni = new ArrayList<String>();
    }
    
	public void setFkIdRep(String fkIdRep) {
		this.fkIdRep = fkIdRep;
	}

	public String getFkIdRep() {
		return fkIdRep;
	}

	public void setPkCuaa(BigDecimal pkCuaa) {
		this.pkCuaa = pkCuaa;
	}

	public BigDecimal getPkCuaa() {
		return pkCuaa;
	}

	public ArrayList<String> getAnnotazioni() {
		return this.annotazioni;
	}

	public void addAnnotazioni(String annotazioni) {
		this.annotazioni.add(annotazioni);
	}

	public String getCodiFisc() {
		return this.codiFisc;
	}

	public void setCodiFisc(String codiFisc) {
		this.codiFisc = codiFisc;
	}

	public String getCodiPiva() {
		return this.codiPiva;
	}

	public void setCatPkid(String catPkid) {
		this.catPkid = catPkid;
	}

	public void setCodiPiva(String codiPiva) {
		this.codiPiva = codiPiva;
	}

	public String getCatPkid() {
		return catPkid;
	}

	public String getComuNasc() {
		return this.comuNasc;
	}

	public void setComuNasc(String comuNasc) {
		this.comuNasc = comuNasc;
	}

	public Date getDataFinePossesso() {
		return this.dataFinePossesso;
	}

	public void setDataFinePossesso(Date dataFinePossesso) {
		this.dataFinePossesso = dataFinePossesso;
	}

	public Date getDataInizioPossesso() {
		return this.dataInizioPossesso;
	}

	public void setDataInizioPossesso(Date dataInizioPossesso) {
		this.dataInizioPossesso = dataInizioPossesso;
	}

	public void setDatiReportSogg(LinkedHashMap<String, Object> datiReportSogg) {
		this.datiReportSogg = datiReportSogg;
	}

	public void setFlgAnomalia(Boolean flgAnomalia) {
		this.flgAnomalia = flgAnomalia;
	}

	public void setFlgTitolare3112(Boolean flgTitolare3112) {
		this.flgTitolare3112 = flgTitolare3112;
	}

	public void setFlgTitolareDataDocfa(Boolean flgTitolareDataDocfa) {
		this.flgTitolareDataDocfa = flgTitolareDataDocfa;
	}

	public Date getDataMorte() {
		return this.dataMorte;
	}

	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}

	public Date getDataNasc() {
		return this.dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	

	public BigDecimal getNumFamiliari() {
		return numFamiliari;
	}

	public void setNumFamiliari(BigDecimal numFamiliari) {
		this.numFamiliari = numFamiliari;
	}

	public String getFlgPersFisica() {
		return this.flgPersFisica;
	}

	public void setFlgPersFisica(String flgPersFisica) {
		this.flgPersFisica = flgPersFisica;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPercPoss() {
		return this.percPoss;
	}

	public void setPercPoss(BigDecimal percPoss) {
		this.percPoss = percPoss;
	}

	public String getRagiSoci() {
		return this.ragiSoci;
	}

	public void setRagiSoci(String ragiSoci) {
		this.ragiSoci = ragiSoci;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}


	public LinkedHashMap<String, Object> getDatiReportSogg() {
		
		datiReportSogg.put("FK_ID_REP", fkIdRep);
		datiReportSogg.put("CAT_PKID", this.catPkid);
		datiReportSogg.put("PK_CUAA", pkCuaa);
		datiReportSogg.put("CODI_FISC", this.codiFisc);
		datiReportSogg.put("CODI_PIVA", this.codiPiva);
		datiReportSogg.put("FLG_PERS_FISICA", this.flgPersFisica);
		datiReportSogg.put("RAGI_SOCI", ragiSoci);
		datiReportSogg.put("NOME", nome);
		datiReportSogg.put("SESSO", sesso);
		datiReportSogg.put("DATA_NASC", this.dataNasc);
		datiReportSogg.put("DATA_MORTE", dataMorte);
		datiReportSogg.put("COMU_NASC", comuNasc);
		datiReportSogg.put("TITOLO", titolo);
		datiReportSogg.put("PERC_POSS", this.percPoss);
		datiReportSogg.put("DATA_INIZIO_POSSESSO", dataInizioPossesso);
		datiReportSogg.put("DATA_FINE_POSSESSO", dataFinePossesso);
		
		datiReportSogg.put("FLG_TITOLARE_DATA_DOCFA", getBooleanField(flgTitolareDataDocfa));
		datiReportSogg.put("FLG_TITOLARE_3112", getBooleanField(flgTitolare3112));
		datiReportSogg.put("FLG_RESID_IND_DCF_3112", getBooleanField(flgResidIndDcf3112));
		datiReportSogg.put("FLG_RESID_IND_CAT_3112", getBooleanField(flgResidIndCat3112));
		
		datiReportSogg.put("NUM_FAMILIARI", numFamiliari);
		
		datiReportSogg.put("FLG_ANOMALIA", getBooleanField(flgAnomalia));
		
		if(codAnomalie.isEmpty())
			datiReportSogg.put("COD_ANOMALIE", null);
		else{
			String codici = "";
			for(int i=0; i<codAnomalie.size(); i++)
				codici += codAnomalie.get(i) + "|";
			
			datiReportSogg.put("COD_ANOMALIE", codici);
		}
		
		if(annotazioni.isEmpty())
			datiReportSogg.put("ANNOTAZIONI", null);
		else{
			String codici = "";
			for(int i=0; i<annotazioni.size(); i++)
				codici += annotazioni.get(i) + "|";
			
			datiReportSogg.put("ANNOTAZIONI", codici);
		}
		
		return datiReportSogg;
	}
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void addcodAnomalie(String cod){
		codAnomalie.add(cod);
	}
	
	
	protected String getBooleanField(Boolean f){
		String s = null;
		if(f!=null)
			s = f ? "1" : "0";
		return s;
	}

	public void setFlgResidIndDcf3112(Boolean flgResidIndDcf3112) {
		this.flgResidIndDcf3112 = flgResidIndDcf3112;
	}

	public void setFlgResidIndCat3112(Boolean flgResidIndCat3112) {
		this.flgResidIndCat3112 = flgResidIndCat3112;
	}
	
	


}