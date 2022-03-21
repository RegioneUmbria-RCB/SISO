package it.webred.rulengine.brick.dia.bean.tributidocfa;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DocfaReportBean {
	
	protected LinkedHashMap<String, Object> dati;
	private  ArrayList<String> dateFields;
	private  ArrayList<String> numberFields;
	
	//Dati Dichiarazione Docfa
	protected String idRep;
	protected Date fornitura;
	protected String protocolloDocfa;
	protected String tipoOperDocfa;
	protected String dataDocfa;
	protected String codEnte;
	protected String sezione;
	protected String foglio;
	protected String particella;
	protected String subalterno;
	protected String prefissoViaDocfa;
	protected String viaDocfa;
	protected String civiciDocfa;
	protected String categoriaDocfa;
	protected String consistenzaDocfa;
	protected static String SQL_DOCFA_FIELDS = 
		"ID_REP                          VARCHAR2(50 BYTE) NOT NULL, " +
		"FORNITURA                       DATE, " +
		"PROTOCOLLO_DOCFA                VARCHAR2(9 BYTE) , " +
		"TIPO_OPER_DOCFA                 VARCHAR2(1 BYTE) , " +
		"DATA_DOCFA                      VARCHAR2(8 BYTE) , " +
		"COD_ENTE                        VARCHAR2(4 BYTE) , " +
		"SEZIONE                         VARCHAR2(3 BYTE) , " +
		"FOGLIO                          VARCHAR2(4 BYTE) , " +
		"PARTICELLA                      VARCHAR2(5 BYTE) , " +
		"SUBALTERNO                      VARCHAR2(4 BYTE) , " +
		"PREFISSO_VIA_DOCFA              VARCHAR2(30 BYTE), " +
		"VIA_DOCFA                       VARCHAR2(50 BYTE), " +
		"CIVICI_DOCFA                    VARCHAR2(18 BYTE), " +
		"CATEGORIA_DOCFA                 VARCHAR2(3 BYTE) , " +
		"CONSISTENZA_DOCFA               VARCHAR2(7 BYTE)   " ;
	
	//Dati Generali Docfa
	protected Date dataVariazione;
	protected String causaleDocfa;
	protected Integer uiuCostituite;
	protected Integer uiuSoppresse;
	protected Integer uiuVariate;
	protected static String SQL_DATI_GENERALI_FIELDS = 
		"DATA_VARIAZIONE                 DATE, " +
		"CAUSALE_DOCFA                   VARCHAR2(3 BYTE), " +
	    "UIU_SOPPRESSE                   NUMBER(3), " +
	    "UIU_COSTITUITE                  NUMBER(3), " +
	    "UIU_VARIATE                     NUMBER(3)  " ;
	
	//Dati Dichiarante Docfa
	protected String nomeDichiarante;
	protected String cognomeDichiarante;
	protected String viaResDichiarante;
	protected String civicoResDichiarante;
	protected String comuneResDichiarante;
	protected String capResDichiarante;
	protected String provResDichiarante;
	protected static String SQL_DIC_DOCFA_FIELDS = 	    
		"DIC_COGNOME                     VARCHAR2(24 BYTE), " +
		"DIC_NOME                        VARCHAR2(20 BYTE), " +
		"DIC_RES_COM                     VARCHAR2(25 BYTE), " +
		"DIC_RES_PROV                    VARCHAR2(3 BYTE), " +
		"DIC_RES_INDIR                   VARCHAR2(35 BYTE), " +
		"DIC_RES_NUMCIV                  VARCHAR2(5 BYTE), " +
	    "DIC_RES_CAP                     VARCHAR2(5 BYTE) ";
	
	//Da Catasto 
	protected Boolean flgUiuCatasto;
	protected Boolean flgIndirizzoInToponomastica;
	protected BigDecimal codViaCatasto;                         //pkid_stra da SITIDSTR
	protected String prefissoViaCatasto;
	protected String descViaCatasto;
	protected String civicoCatasto;
	protected String categoriaCatasto;
	protected static String SQL_CATASTO_FIELDS = 
		"FLG_UIU_CATASTO                 VARCHAR2(1 BYTE), " +
		"FLG_INDIRIZZO_IN_TOPONOMASTICA  VARCHAR2(1 BYTE), " +
		"COD_VIA_CATASTO                 NUMBER (9), " +
		"PREFISSO_VIA_CATASTO            VARCHAR2 (30 Byte), " +
		"VIA_CATASTO                     VARCHAR2 (70 Byte), " +
		"CIVICO_CATASTO                  VARCHAR2(100 BYTE), " +
		"CATEGORIA_CATASTO               VARCHAR2(3 BYTE) " ;
	
	//Elaborazioni
	protected String flgDocfaSNoC;
	protected String docfaInAnno;
	protected String docfaContemporanei;
	protected String dataDocfaSuccessivo;
	protected Boolean flgElaborato;
	protected Boolean flgAnomalie;
	protected ArrayList<String> codAnomalie;
	protected ArrayList<String> annotazioni;
	protected static String SQL_ELABORAZIONI_FIELDS = 
	    "FLG_DOCFA_S_NO_C                VARCHAR2(1 BYTE), " +
		"DOCFA_IN_ANNO                   VARCHAR2(10 BYTE), " +
		"DOCFA_CONTEMPORANEI             VARCHAR2(10 BYTE), " +
		"DATA_DOCFA_SUCCESSIVO           VARCHAR2(8 BYTE), " +
		"FLG_ELABORATO                   VARCHAR2(1 BYTE), " +
		"FLG_ANOMALIE                    VARCHAR2(1 BYTE), " +
		"COD_ANOMALIE                    VARCHAR(200 BYTE),  " +
		"ANNOTAZIONI                     VARCHAR2(4000 BYTE), "+
		"DATA_AGGIORNAMENTO              DATE  DEFAULT SYSDATE " ;


	public DocfaReportBean(){
		dati = new LinkedHashMap <String, Object>();
		dateFields = new ArrayList<String>();
		numberFields = new ArrayList<String>();
		
		codAnomalie = new ArrayList<String>();
		annotazioni = new ArrayList<String>();
	}
	
	

	public  ArrayList<String> getGetDateFields() {
		
		dateFields.add("FORNITURA");
		dateFields.add("DATA_VARIAZIONE");
		dateFields.add("DATA_AGGIORNAMENTO");
		
		return dateFields;
	}

	public  ArrayList<String> getGetNumberFields() {
		
		numberFields.add("UIU_SOPPRESSE");
		numberFields.add("UIU_COSTITUITE");
		numberFields.add("UIU_VARIATE");
		numberFields.add("COD_VIA_CATASTO");
		
		return numberFields;
	}

	public LinkedHashMap<String, Object> getDati() {
		
		dati.put("ID_REP", idRep);
		dati.put("FORNITURA", this.fornitura);
		dati.put("PROTOCOLLO_DOCFA", this.protocolloDocfa);
		dati.put("TIPO_OPER_DOCFA", this.tipoOperDocfa);
		dati.put("COD_ENTE", codEnte);
		dati.put("SEZIONE", sezione.equalsIgnoreCase("-") ? null : sezione);
		dati.put("FOGLIO", this.foglio);
		dati.put("PARTICELLA", this.particella);
		dati.put("SUBALTERNO", this.subalterno);
		
		dati.put("DOCFA_IN_ANNO", this.docfaInAnno);
		dati.put("DOCFA_CONTEMPORANEI", this.docfaContemporanei);
		dati.put("DATA_DOCFA_SUCCESSIVO", this.dataDocfaSuccessivo);
		
		//Docfa Dati Censuari
		dati.put("DATA_DOCFA", this.dataDocfa);
		dati.put("CATEGORIA_DOCFA", this.categoriaDocfa);
		dati.put("CONSISTENZA_DOCFA", consistenzaDocfa);
		dati.put("PREFISSO_VIA_DOCFA", this.prefissoViaDocfa);
		dati.put("VIA_DOCFA", this.viaDocfa);
		dati.put("CIVICI_DOCFA", this.civiciDocfa);
		
		//Docfa Dati Generali
		dati.put("DATA_VARIAZIONE", this.dataVariazione);
		dati.put("UIU_SOPPRESSE", this.uiuSoppresse);
		dati.put("UIU_COSTITUITE", this.uiuCostituite);
		dati.put("UIU_VARIATE", uiuVariate);
		dati.put("FLG_DOCFA_S_NO_C", this.flgDocfaSNoC);
		dati.put("CAUSALE_DOCFA", this.causaleDocfa);
		
		//Docfa Dichiaranti
		dati.put("DIC_NOME", this.nomeDichiarante);
		dati.put("DIC_COGNOME", this.cognomeDichiarante);
		dati.put("DIC_RES_INDIR", this.viaResDichiarante);
		dati.put("DIC_RES_NUMCIV", this.civicoResDichiarante);
		dati.put("DIC_RES_COM", this.comuneResDichiarante);
		dati.put("DIC_RES_CAP", this.capResDichiarante);
		dati.put("DIC_RES_PROV", this.provResDichiarante);
				
		
		//Dati Catasto
		dati.put("FLG_UIU_CATASTO", getBooleanField(flgUiuCatasto));
		dati.put("CATEGORIA_CATASTO", categoriaCatasto);
		dati.put("FLG_INDIRIZZO_IN_TOPONOMASTICA", getBooleanField(flgIndirizzoInToponomastica));
		dati.put("COD_VIA_CATASTO", codViaCatasto);
		dati.put("PREFISSO_VIA_CATASTO", prefissoViaCatasto);
		dati.put("VIA_CATASTO", descViaCatasto);
		dati.put("CIVICO_CATASTO", civicoCatasto);
			
		dati.put("FLG_ANOMALIE", getBooleanField(flgAnomalie));
		dati.put("FLG_ELABORATO", getBooleanField(flgElaborato));
		
		if(codAnomalie.isEmpty())
			dati.put("COD_ANOMALIE", null);
		else{
			String codici = "";
			for(int i=0; i<codAnomalie.size(); i++)
				codici += codAnomalie.get(i) + "|";
			
			dati.put("COD_ANOMALIE", codici);
		}
		
		if(annotazioni.isEmpty())
			dati.put("ANNOTAZIONI", null);
		else{
			String a = "";
			for(int i=0; i<annotazioni.size(); i++)
				a += annotazioni.get(i) + "@";
			
			dati.put("ANNOTAZIONI", a);
		}
		
		return dati;
	}

	public String getCivicoResDichiarante() {
		return civicoResDichiarante;
	}

	public void setCivicoResDichiarante(String civicoResDichiarante) {
		this.civicoResDichiarante = civicoResDichiarante;
	}

	public String getCapResDichiarante() {
		return capResDichiarante;
	}

	public void setCapResDichiarante(String capResDichiarante) {
		this.capResDichiarante = capResDichiarante;
	}


	public String getCategoriaCatasto() {
		return categoriaCatasto;
	}

	public void setCategoriaCatasto(String categoriaCatasto) {
		this.categoriaCatasto = categoriaCatasto;
	}

	public String getCategoriaDocfa() {
		return categoriaDocfa;
	}

	public void setCategoriaDocfa(String categoriaDocfa) {
		this.categoriaDocfa = categoriaDocfa;
	}

	public String getCausaleDocfa() {
		return causaleDocfa;
	}

	public void setCausaleDocfa(String causaleDocfa) {
		this.causaleDocfa = causaleDocfa;
	}

	public String getCiviciDocfa() {
		return civiciDocfa;
	}

	public void setCiviciDocfa(String civiciDocfa) {
		this.civiciDocfa = civiciDocfa;
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public BigDecimal getCodViaCatasto() {
		return codViaCatasto;
	}

	public void setCodViaCatasto(BigDecimal codViaCatasto) {
		this.codViaCatasto = codViaCatasto;
	}

	public String getDescViaCatasto() {
		return descViaCatasto;
	}

	public void setDescViaCatasto(String descViaCatasto) {
		this.descViaCatasto = descViaCatasto;
	}

	public String getCivicoCatasto() {
		return civicoCatasto;
	}

	public void setCivicoCatasto(String civicoCatasto) {
		this.civicoCatasto = civicoCatasto;
	}

	public String getDataDocfa() {
		return dataDocfa;
	}

	public void setDataDocfa(String dataDocfa) {
		this.dataDocfa = dataDocfa;
	}

	public String getPrefissoViaCatasto() {
		return prefissoViaCatasto;
	}

	public void setPrefissoViaCatasto(String prefissoViaCatasto) {
		this.prefissoViaCatasto = prefissoViaCatasto;
	}

	public String getDataDocfaSuccessivo() {
		return dataDocfaSuccessivo;
	}

	public void setDataDocfaSuccessivo(String dataDocfaSuccessivo) {
		this.dataDocfaSuccessivo = dataDocfaSuccessivo;
	}

	public String getDocfaContemporanei() {
		return docfaContemporanei;
	}

	public void setDocfaContemporanei(String docfaContemporanei) {
		this.docfaContemporanei = docfaContemporanei;
	}

	public String getDocfaInAnno() {
		return docfaInAnno;
	}

	public void setDocfaInAnno(String docfaInAnno) {
		this.docfaInAnno = docfaInAnno;
	}

	public void setFlgAnomalie(Boolean flgAnomalie) {
		this.flgAnomalie = flgAnomalie;
	}

	public void setFlgDocfaSNoC(String flgDocfaSNoC) {
		this.flgDocfaSNoC = flgDocfaSNoC;
	}

	public void setFlgElaborato(Boolean flgElaborato) {
		this.flgElaborato = flgElaborato;
	}

	public void setFlgIndirizzoInToponomastica(Boolean flgIndirizzoInToponomastica) {
		this.flgIndirizzoInToponomastica = flgIndirizzoInToponomastica;
	}

	public void setFlgUiuCatasto(Boolean flgUiuCatasto) {
		this.flgUiuCatasto = flgUiuCatasto;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public Date getFornitura() {
		return fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public String getIdRep() {
		return idRep;
	}

	public void setIdRep(String idRep) {
		this.idRep = idRep;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getPrefissoViaDocfa() {
		return prefissoViaDocfa;
	}

	public void setPrefissoViaDocfa(String prefissoViaDocfa) {
		this.prefissoViaDocfa = prefissoViaDocfa;
	}

	public String getProtocolloDocfa() {
		return protocolloDocfa;
	}

	public void setProtocolloDocfa(String protocolloDocfa) {
		this.protocolloDocfa = protocolloDocfa;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public Boolean getFlgAnomalie() {
		return flgAnomalie;
	}

	public String getFlgDocfaSNoC() {
		return flgDocfaSNoC;
	}

	public Boolean getFlgElaborato() {
		return flgElaborato;
	}

	public Boolean getFlgIndirizzoInToponomastica() {
		return flgIndirizzoInToponomastica;
	}

	public Boolean getFlgUiuCatasto() {
		return flgUiuCatasto;
	}

	public void setUiuCostituite(Integer uiuCostituite) {
		this.uiuCostituite = uiuCostituite;
	}

	public void setUiuSoppresse(Integer uiuSoppresse) {
		this.uiuSoppresse = uiuSoppresse;
	}

	public void setUiuVariate(Integer uiuVariate) {
		this.uiuVariate = uiuVariate;
	}

	public String getTipoOperDocfa() {
		return tipoOperDocfa;
	}

	public void setTipoOperDocfa(String tipoOperDocfa) {
		this.tipoOperDocfa = tipoOperDocfa;
	}

	public String getViaDocfa() {
		return viaDocfa;
	}

	public void setViaDocfa(String viaDocfa) {
		this.viaDocfa = viaDocfa;
	}
	
	protected String getBooleanField(Boolean f){
		String s = null;
		if(f!=null)
			s = f ? "1" : "0";
		return s;
	}

	public void addcodAnomalie(String cod){
		if(!codAnomalie.contains(cod))
			codAnomalie.add(cod);
	}
	
	public void addAnnotazioni(String a){
		annotazioni.add(a);
	}
	
	public ArrayList getCodAnomalie() {
		return codAnomalie;
	}

	public String getNomeDichiarante() {
		return nomeDichiarante;
	}

	public void setNomeDichiarante(String nomeDichiarante) {
		this.nomeDichiarante = nomeDichiarante;
	}

	public String getCognomeDichiarante() {
		return cognomeDichiarante;
	}

	public void setCognomeDichiarante(String cognomeDichiarante) {
		this.cognomeDichiarante = cognomeDichiarante;
	}

	public String getViaResDichiarante() {
		return viaResDichiarante;
	}

	public void setViaResDichiarante(String viaResDichiarante) {
		this.viaResDichiarante = viaResDichiarante;
	}

	public String getComuneResDichiarante() {
		return comuneResDichiarante;
	}

	public void setComuneResDichiarante(String comuneResDichiarante) {
		this.comuneResDichiarante = comuneResDichiarante;
	}

	public String getProvResDichiarante() {
		return provResDichiarante;
	}

	public void setProvResDichiarante(String provResDichiarante) {
		this.provResDichiarante = provResDichiarante;
	}

	public Integer getUiuCostituite() {
		return uiuCostituite;
	}

	public Integer getUiuSoppresse() {
		return uiuSoppresse;
	}

	public Integer getUiuVariate() {
		return uiuVariate;
	}
	
	public String getConsistenzaDocfa() {
		return consistenzaDocfa;
	}

	public void setConsistenzaDocfa(String consistenzaDocfa) {
		this.consistenzaDocfa = consistenzaDocfa;
	}

	public Date getDataVariazione() {
		return dataVariazione;
	}

	public void setDataVariazione(Date dataVariazione) {
		this.dataVariazione = dataVariazione;
	}

}
