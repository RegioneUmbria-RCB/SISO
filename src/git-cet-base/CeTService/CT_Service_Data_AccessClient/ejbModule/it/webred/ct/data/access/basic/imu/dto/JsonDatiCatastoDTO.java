package it.webred.ct.data.access.basic.imu.dto;

import java.math.BigDecimal;
import java.net.URLDecoder;


public class JsonDatiCatastoDTO {
	
	private static final long serialVersionUID = 1L;
	
	
	private String codFisc;
	private String denom;
	private String dtNas;
	
	private String sede;
	private String provCat;
	private String tipImm;
	private String tipoImm;
	private String tit;
	private String regime;
	private String ubi;
	private String sez;
	private String foglio;
	private String num;
	private String sub;
	private String partita;
	private String classe;
	
	private String dataInizio;
    private String dataFine;
	private String dataInizioTit;
    private String dataFineTit;
    
    //Informazioni solo catasto immobili
	private String cat;
	private String consistenza;

    //Informazioni solo catasto terreni
    private String redditoDominicale;
    private String redditoAgrario;
    private String area;
    private String qualita;
	
    
    private String idrec;
    private String tipCat;
    private String tipCatOld;
    private String txtTipologia;
    
    private String belfiore;
    private String tasso;
    private String aliquota;
    private String detrazione;
    private Integer ordImmobile;
    private String rendita,renditaC2,renditaC6,renditaC7;
    private String percPoss,quotaC2,quotaC6,quotaC7;
    private Integer mesiPoss,mesiPossC2, mesiPossC6, mesiPossC7;
   
    
    public ValImmobileDTO[] getValori(){
    	
    	ValImmobileDTO[] lst = new ValImmobileDTO[4];
    	
    	ValImmobileDTO vAB = new ValImmobileDTO();
    	ValImmobileDTO vC2 = new ValImmobileDTO();
    	ValImmobileDTO vC6 = new ValImmobileDTO();
    	ValImmobileDTO vC7 = new ValImmobileDTO();
    	
   	
    	vAB.setRendita(rendita!=null ? Double.valueOf(rendita) : null);
    	vC2.setRendita(renditaC2!=null ? Double.valueOf(renditaC2) : null);
    	vC6.setRendita(renditaC6!=null ? Double.valueOf(renditaC6) : null);
    	vC7.setRendita(renditaC7!=null ? Double.valueOf(renditaC7) : null);
    	
    	vAB.setMesiPoss(mesiPoss!=null ? mesiPoss.toString() : null);
    	vC2.setMesiPoss(mesiPossC2!=null ? mesiPossC2.toString() : null);
    	vC6.setMesiPoss(mesiPossC6!=null ? mesiPossC6.toString() : null);
    	vC7.setMesiPoss(mesiPossC7!=null ? mesiPossC7.toString() : null);
    	
    	vAB.setQuotaPoss(percPoss!=null ? Double.valueOf(percPoss): null);
    	vC2.setQuotaPoss(quotaC2!=null ? Double.valueOf(quotaC2) : null);
    	vC6.setQuotaPoss(quotaC6!=null ? Double.valueOf(quotaC6) : null);
    	vC7.setQuotaPoss(quotaC7!=null ? Double.valueOf(quotaC7) : null);
    	
    	vAB.setCod("AB");vAB.setDescCat("Abitazione principale o altri immobili");
    	vC2.setCod("C2");vC2.setDescCat("Pertinenza C2");
    	vC6.setCod("C6");vC6.setDescCat("Pertinenza C6");
    	vC7.setCod("C7");vC7.setDescCat("Pertinenca C7");
    	
    	lst[0]=vAB;
    	lst[1]=vC2;
    	lst[2]=vC6;
    	lst[3]=vC7;
    	
    	return lst;
    	
    }
    
    private String dtInizioVal;
    private String dtFineVal;
    
	public String getDataInizioTit() {
		return dataInizioTit;
	}
	public String getDataFineTit() {
		return dataFineTit;
	}
	public void setDataInizioTit(String dataInizioTit) {
		this.dataInizioTit = dataInizioTit;
	}
	public void setDataFineTit(String dataFineTit) {
		this.dataFineTit = dataFineTit;
	}
	
	public String getDenom() {
		return denom;
	}
	public String getSede() {
		return sede;
	}
	public String getProvCat() {
		return provCat;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public String getDtNas() {
		return dtNas;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public void setDtNas(String dtNas) {
		this.dtNas = dtNas;
	}
	public String getTipImm() {
		return tipImm;
	}
	public String getTit() {
		return tit;
	}
	public String getUbi() {
		return ubi;
	}
	public String getFoglio() {
		return foglio;
	}
	public String getNum() {
		return num;
	}
	public String getSub() {
		return sub;
	}
	public String getCat() {
		return cat;
	}
	public String getClasse() {
		return classe;
	}

	public void setDenom(String denom) {
		this.denom = denom;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	public Integer getOrdImmobile() {
		return ordImmobile;
	}
	public void setOrdImmobile(Integer ordImmobile) {
		this.ordImmobile = ordImmobile;
	}
	public Integer getMesiPossC2() {
		return mesiPossC2;
	}
	public void setMesiPossC2(Integer mesiPossC2) {
		this.mesiPossC2 = mesiPossC2;
	}
	public Integer getMesiPossC6() {
		return mesiPossC6;
	}
	public void setMesiPossC6(Integer mesiPossC6) {
		this.mesiPossC6 = mesiPossC6;
	}
	public Integer getMesiPossC7() {
		return mesiPossC7;
	}
	public void setMesiPossC7(Integer mesiPossC7) {
		this.mesiPossC7 = mesiPossC7;
	}
	public void setProvCat(String provCat) {
		this.provCat = provCat;
	}
	public void setTipImm(String tipImm) {
		this.tipImm = tipImm;
	}
	public void setTit(String tit) {
		this.tit = tit;
	}
	public void setUbi(String ubi) {
		this.ubi = ubi;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getDtInizioVal() {
		return dtInizioVal;
	}
	public void setDtInizioVal(String dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}
	public String getDtFineVal() {
		return dtFineVal;
	}
	public void setDtFineVal(String dtFineVal) {
		this.dtFineVal = dtFineVal;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getConsistenza() {
		return consistenza;
	}
	public String getRendita() {
		return rendita;
	}
	public String getPartita() {
		return partita;
	}
	public String getRedditoDominicale() {
		return redditoDominicale;
	}
	public String getRedditoAgrario() {
		return redditoAgrario;
	}
	public String getArea() {
		return area;
	}
	public String getQualita() {
		return qualita;
	}
	public void setRedditoDominicale(String redditoDominicale) {
		this.redditoDominicale = redditoDominicale;
	}
	public void setRedditoAgrario(String redditoAgrario) {
		this.redditoAgrario = redditoAgrario;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setQualita(String qualita) {
		this.qualita = qualita;
	}
	public void setConsistenza(String consistenza) {
		this.consistenza = consistenza;
	}
	public void setRendita(String rendita) {
		this.rendita = rendita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public String getPercPoss() {
		return percPoss;
	}
	public void setPercPoss(String percPoss) {
		this.percPoss = percPoss;
	}

	public String stampaRecord(){
		String r ="";
		if(tipoImm.equals("F"))
			r =codFisc+"|"+denom+"|"+ dtNas +"|"+ sede+"|"+provCat+"|"+tipImm+"|"+tit+"|"+regime+"|"+ubi+"|"+foglio+"|"+num+"|"+sub+"|"+
				cat+"|"+classe+"|"+consistenza+"|"+rendita+"|"+partita+"|"+percPoss+"|"+dataInizio+"|"+
				dataFine+"|"+dataInizioTit+"|"+dataFineTit;
		else
			r =codFisc+"|"+denom+"|"+ dtNas +"|"+ sede+"|"+provCat+"|"+tipImm+"|"+tit+"|"+regime+"|"+ubi+"|"+foglio+"|"+num+"|"+sub+"|"+
				qualita+"|"+classe+"|"+redditoDominicale+"|"+redditoAgrario+"|"+rendita+"|"+area+"|"+
				partita+"|"+percPoss+"|"+dataInizio+"|"+dataFine+"|"+dataInizioTit+"|"+dataFineTit;
		
		r += "|"+this.txtTipologia;
		
		return r;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public String getRegime() {
		return regime;
	}
	public void setRegime(String regime) {
		this.regime = regime;
	}
	public String getTipoImm() {
		return tipoImm;
	}
	public void setTipoImm(String tipoImm) {
		this.tipoImm = tipoImm;
	}
	public String getSez() {
		return sez;
	}
	public void setSez(String sez) {
		this.sez = sez;
	}
	public String getIdrec() {
		return idrec;
	}
	public void setIdrec(String idrec) {
		this.idrec = idrec;
	}
	public String getTipCat() {
		return tipCat;
	}
	public void setTipCat(String tipCat) {
		this.tipCat = tipCat;
	}
	public String getTipCatOld() {
		return tipCatOld;
	}
	public void setTipCatOld(String tipCatOld) {
		this.tipCatOld = tipCatOld;
	}
	public String getTxtTipologia() {
		return txtTipologia;
	}
	public void setTxtTipologia(String txtTipologia) {
		this.txtTipologia = txtTipologia;
	}
	public Integer getMesiPoss() {
		return mesiPoss;
	}
	public void setMesiPoss(Integer mesiPoss) {
		this.mesiPoss = mesiPoss;
	}
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public String getTasso() {
		return tasso;
	}
	public void setTasso(String tasso) {
		this.tasso = tasso;
	}
	public String getDetrazione() {
		return detrazione;
	}
	public void setDetrazione(String detrazione) {
		this.detrazione = detrazione;
	}
	public String getRenditaC2() {
		return renditaC2;
	}
	public void setRenditaC2(String renditaC2) {
		this.renditaC2 = renditaC2;
	}
	public String getRenditaC6() {
		return renditaC6;
	}
	public void setRenditaC6(String renditaC6) {
		this.renditaC6 = renditaC6;
	}
	public String getRenditaC7() {
		return renditaC7;
	}
	public void setRenditaC7(String renditaC7) {
		this.renditaC7 = renditaC7;
	}
	public String getQuotaC2() {
		return quotaC2;
	}
	public void setQuotaC2(String quotaC2) {
		this.quotaC2 = quotaC2;
	}
	public String getQuotaC6() {
		return quotaC6;
	}
	public void setQuotaC6(String quotaC6) {
		this.quotaC6 = quotaC6;
	}
	public String getQuotaC7() {
		return quotaC7;
	}
	public void setQuotaC7(String quotaC7) {
		this.quotaC7 = quotaC7;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAliquota() {
		return aliquota;
	}
	public void setAliquota(String aliquota) {
		this.aliquota = aliquota;
	}

}
