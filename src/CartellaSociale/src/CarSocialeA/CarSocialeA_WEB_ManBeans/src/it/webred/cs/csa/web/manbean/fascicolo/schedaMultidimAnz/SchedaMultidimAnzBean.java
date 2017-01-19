package it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SchedaMultidimAnzBean {
	protected boolean viveConiuge;
	protected boolean viveFigli;
	protected Date daQuandoViveFigli;
	protected boolean viveFamiliari;
	protected Date daQuandoViveFamiliari;
	protected boolean viveSolo;
	protected Date daQuandoViveSolo;
	protected boolean viveAltri;
	protected String specificare;
	
	protected ArrayList<AnagraficaMultidimAnzBean> famAnaConvs= new ArrayList<AnagraficaMultidimAnzBean>();

	protected String nFigli;
	protected String nFiglie;
	protected String nSorelle;
	protected String nFratelli;
	protected Integer valFamRating;
	
	protected ArrayList<AnagraficaMultidimAnzBean> famAnaNonConvs= new ArrayList<AnagraficaMultidimAnzBean>();
	
	protected String relAltriSogg;
	protected String relAltriSoggRetr;
	
	protected ArrayList<AnagraficaMultidimAnzBean> famAnaAltris= new ArrayList<AnagraficaMultidimAnzBean>();
	
	protected Integer valReteSocRating;
	
	protected boolean patCard;
	protected String patCardDesc;
	protected boolean patRen;
	protected String patRenDesc;
	protected boolean patAppResp;
	protected String patAppRespDesc;
	protected boolean patAppDig;
	protected String patAppDigDesc;
	protected boolean patAppPsiComp;
	protected String patAppPsiCompDesc;
	protected boolean patSistNer;
	protected String patSistNerDesc;
	protected boolean artArtOst;
	protected String artArtOstDesc;
	protected boolean distVista;
	protected String distVistaDesc;
	protected boolean distUdito;
	protected String distUditoDesc;
	protected boolean distLoco;
	protected String distLocoDesc;
	protected boolean incontinenza;
	protected String incontinenzaDesc;
	protected boolean piagheDecub;
	protected String piagheDecubDesc;
	protected boolean altrePat;
	protected String altrePatDesc;
	protected boolean altreInfo;
	protected String altreInfoDesc;
	protected String noteSanit;
	protected String salFisic;
	protected String valSalutePsi;
	protected Integer valAutoPers;
	protected Integer valAutoDom;
	protected Integer valAutoExDom;
	
	protected String tipAbit;
	protected String ascensore;
	protected String portineria;
	protected String abGod;
	protected String comp;
	protected String riscaldamento;
	protected String gabinetto;
	protected String[] fornitos;
	protected String[] elettrodoms; 
	protected String[] altriProbEsits;
	protected Integer adAbitRating;
	protected String ubAbits;
	
	protected String iseeRipa;
	protected Integer valEconRating;
	protected String momValTipo;
	protected String momValLuogo;
	protected String momValRef;
	protected String momValTel;
	
	protected String[] prestazioniDachis;
	
	public SchedaMultidimAnzBean(){
		
	}

	public boolean isViveConiuge() {
		return viveConiuge;
	}

	public void setViveConiuge(boolean viveConiuge) {
		this.viveConiuge = viveConiuge;
	}

	public boolean isViveFigli() {
		return viveFigli;
	}

	public void setViveFigli(boolean viveFigli) {
		this.viveFigli = viveFigli;
	}

	public Date getDaQuandoViveFigli() {
		return daQuandoViveFigli;
	}

	public void setDaQuandoViveFigli(Date daQuandoViveFigli) {
		this.daQuandoViveFigli = daQuandoViveFigli;
	}

	public boolean isViveFamiliari() {
		return viveFamiliari;
	}

	public void setViveFamiliari(boolean viveFamiliari) {
		this.viveFamiliari = viveFamiliari;
	}

	public boolean isViveSolo() {
		return viveSolo;
	}

	public void setViveSolo(boolean viveSolo) {
		this.viveSolo = viveSolo;
	}

	public Date getDaQuandoViveSolo() {
		return daQuandoViveSolo;
	}

	public void setDaQuandoViveSolo(Date daQuandoViveSolo) {
		this.daQuandoViveSolo = daQuandoViveSolo;
	}

	public Date getDaQuandoViveFamiliari() {
		return daQuandoViveFamiliari;
	}

	public void setDaQuandoViveFamiliari(Date daQuandoViveFamiliari) {
		this.daQuandoViveFamiliari = daQuandoViveFamiliari;
	}

	public boolean isViveAltri() {
		return viveAltri;
	}

	public void setViveAltri(boolean viveAltri) {
		this.viveAltri = viveAltri;
	}

	public String getSpecificare() {
		return specificare;
	}

	public void setSpecificare(String specificare) {
		this.specificare = specificare;
	}

	public ArrayList<AnagraficaMultidimAnzBean> getFamAnaConvs() {
		return famAnaConvs;
	}

	public void setFamAnaConvs(ArrayList<AnagraficaMultidimAnzBean> famAnaConvs) {
		this.famAnaConvs = famAnaConvs;
	}

	public String getnFigli() {
		return nFigli;
	}

	public void setnFigli(String nFigli) {
		this.nFigli = nFigli;
	}

	public String getnFiglie() {
		return nFiglie;
	}

	public void setnFiglie(String nFiglie) {
		this.nFiglie = nFiglie;
	}

	public String getnSorelle() {
		return nSorelle;
	}

	public void setnSorelle(String nSorelle) {
		this.nSorelle = nSorelle;
	}

	public String getnFratelli() {
		return nFratelli;
	}

	public void setnFratelli(String nFratelli) {
		this.nFratelli = nFratelli;
	}

	public Integer getValFamRating() {
		return valFamRating;
	}

	public void setValFamRating(Integer valFamRating) {
		this.valFamRating = valFamRating;
	}

	public ArrayList<AnagraficaMultidimAnzBean> getFamAnaNonConvs() {
		return famAnaNonConvs;
	}

	public void setFamAnaNonConvs(
			ArrayList<AnagraficaMultidimAnzBean> famAnaNonConvs) {
		this.famAnaNonConvs = famAnaNonConvs;
	}

	public String getRelAltriSogg() {
		return relAltriSogg;
	}

	public void setRelAltriSogg(String relAltriSogg) {
		this.relAltriSogg = relAltriSogg;
	}

	public String getRelAltriSoggRetr() {
		return relAltriSoggRetr;
	}

	public void setRelAltriSoggRetr(String relAltriSoggRetr) {
		this.relAltriSoggRetr = relAltriSoggRetr;
	}

	public ArrayList<AnagraficaMultidimAnzBean> getFamAnaAltris() {
		return famAnaAltris;
	}

	public void setFamAnaAltris(ArrayList<AnagraficaMultidimAnzBean> famAnaAltris) {
		this.famAnaAltris = famAnaAltris;
	}

	public Integer getValReteSocRating() {
		return valReteSocRating;
	}

	public void setValReteSocRating(Integer valReteSocRating) {
		this.valReteSocRating = valReteSocRating;
	}

	public boolean isPatCard() {
		return patCard;
	}

	public void setPatCard(boolean patCard) {
		this.patCard = patCard;
	}

	public String getPatCardDesc() {
		return patCardDesc;
	}

	public void setPatCardDesc(String patCardDesc) {
		this.patCardDesc = patCardDesc;
	}

	public boolean isPatRen() {
		return patRen;
	}

	public void setPatRen(boolean patRen) {
		this.patRen = patRen;
	}

	public String getPatRenDesc() {
		return patRenDesc;
	}

	public void setPatRenDesc(String patRenDesc) {
		this.patRenDesc = patRenDesc;
	}

	public boolean isPatAppResp() {
		return patAppResp;
	}

	public void setPatAppResp(boolean patAppResp) {
		this.patAppResp = patAppResp;
	}

	public String getPatAppRespDesc() {
		return patAppRespDesc;
	}

	public void setPatAppRespDesc(String patAppRespDesc) {
		this.patAppRespDesc = patAppRespDesc;
	}

	public boolean isPatAppDig() {
		return patAppDig;
	}

	public void setPatAppDig(boolean patAppDig) {
		this.patAppDig = patAppDig;
	}

	public String getPatAppDigDesc() {
		return patAppDigDesc;
	}

	public void setPatAppDigDesc(String patAppDigDesc) {
		this.patAppDigDesc = patAppDigDesc;
	}

	public boolean isPatAppPsiComp() {
		return patAppPsiComp;
	}

	public void setPatAppPsiComp(boolean patAppPsiComp) {
		this.patAppPsiComp = patAppPsiComp;
	}

	public String getPatAppPsiCompDesc() {
		return patAppPsiCompDesc;
	}

	public void setPatAppPsiCompDesc(String patAppPsiCompDesc) {
		this.patAppPsiCompDesc = patAppPsiCompDesc;
	}

	public boolean isPatSistNer() {
		return patSistNer;
	}

	public void setPatSistNer(boolean patSistNer) {
		this.patSistNer = patSistNer;
	}

	public String getPatSistNerDesc() {
		return patSistNerDesc;
	}

	public void setPatSistNerDesc(String patSistNerDesc) {
		this.patSistNerDesc = patSistNerDesc;
	}

	public boolean isArtArtOst() {
		return artArtOst;
	}

	public void setArtArtOst(boolean artArtOst) {
		this.artArtOst = artArtOst;
	}

	public String getArtArtOstDesc() {
		return artArtOstDesc;
	}

	public void setArtArtOstDesc(String artArtOstDesc) {
		this.artArtOstDesc = artArtOstDesc;
	}

	public boolean isDistVista() {
		return distVista;
	}

	public void setDistVista(boolean distVista) {
		this.distVista = distVista;
	}

	public String getDistVistaDesc() {
		return distVistaDesc;
	}

	public void setDistVistaDesc(String distVistaDesc) {
		this.distVistaDesc = distVistaDesc;
	}

	public boolean isDistUdito() {
		return distUdito;
	}

	public void setDistUdito(boolean distUdito) {
		this.distUdito = distUdito;
	}

	public String getDistUditoDesc() {
		return distUditoDesc;
	}

	public void setDistUditoDesc(String distUditoDesc) {
		this.distUditoDesc = distUditoDesc;
	}

	public boolean isDistLoco() {
		return distLoco;
	}

	public void setDistLoco(boolean distLoco) {
		this.distLoco = distLoco;
	}

	public String getDistLocoDesc() {
		return distLocoDesc;
	}

	public void setDistLocoDesc(String distLocoDesc) {
		this.distLocoDesc = distLocoDesc;
	}

	public boolean isIncontinenza() {
		return incontinenza;
	}

	public void setIncontinenza(boolean incontinenza) {
		this.incontinenza = incontinenza;
	}

	public String getIncontinenzaDesc() {
		return incontinenzaDesc;
	}

	public void setIncontinenzaDesc(String incontinenzaDesc) {
		this.incontinenzaDesc = incontinenzaDesc;
	}

	public boolean isPiagheDecub() {
		return piagheDecub;
	}

	public void setPiagheDecub(boolean piagheDecub) {
		this.piagheDecub = piagheDecub;
	}

	public String getPiagheDecubDesc() {
		return piagheDecubDesc;
	}

	public void setPiagheDecubDesc(String piagheDecubDesc) {
		this.piagheDecubDesc = piagheDecubDesc;
	}

	public boolean isAltrePat() {
		return altrePat;
	}

	public void setAltrePat(boolean altrePat) {
		this.altrePat = altrePat;
	}

	public String getAltrePatDesc() {
		return altrePatDesc;
	}

	public void setAltrePatDesc(String altrePatDesc) {
		this.altrePatDesc = altrePatDesc;
	}

	public boolean isAltreInfo() {
		return altreInfo;
	}

	public void setAltreInfo(boolean altreInfo) {
		this.altreInfo = altreInfo;
	}

	public String getAltreInfoDesc() {
		return altreInfoDesc;
	}

	public void setAltreInfoDesc(String altreInfoDesc) {
		this.altreInfoDesc = altreInfoDesc;
	}

	public String getNoteSanit() {
		return noteSanit;
	}

	public void setNoteSanit(String noteSanit) {
		this.noteSanit = noteSanit;
	}

	public String getTipAbit() {
		return tipAbit;
	}

	public void setTipAbit(String tipAbit) {
		this.tipAbit = tipAbit;
	}

	public String getAscensore() {
		return ascensore;
	}

	public void setAscensore(String ascensore) {
		this.ascensore = ascensore;
	}

	public String getPortineria() {
		return portineria;
	}

	public void setPortineria(String portineria) {
		this.portineria = portineria;
	}

	public String getAbGod() {
		return abGod;
	}

	public void setAbGod(String abGod) {
		this.abGod = abGod;
	}

	public String getComp() {
		return comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public String getRiscaldamento() {
		return riscaldamento;
	}

	public void setRiscaldamento(String riscaldamento) {
		this.riscaldamento = riscaldamento;
	}

	public String getGabinetto() {
		return gabinetto;
	}

	public void setGabinetto(String gabinetto) {
		this.gabinetto = gabinetto;
	}

	public String[] getFornitos() {
		return fornitos;
	}

	public void setFornitos(String[] fornitos) {
		this.fornitos = fornitos;
	}

	public String[] getElettrodoms() {
		return elettrodoms;
	}

	public void setElettrodoms(String[] elettrodoms) {
		this.elettrodoms = elettrodoms;
	}

	public String[] getAltriProbEsits() {
		return altriProbEsits;
	}

	public void setAltriProbEsits(String[] altriProbEsits) {
		this.altriProbEsits = altriProbEsits;
	}

	public Integer getAdAbitRating() {
		return adAbitRating;
	}

	public void setAdAbitRating(Integer adAbitRating) {
		this.adAbitRating = adAbitRating;
	}

	public String getUbAbits() {
		return ubAbits;
	}

	public void setUbAbits(String ubAbits) {
		this.ubAbits = ubAbits;
	}

	public String getIseeRipa() {
		return iseeRipa;
	}

	public void setIseeRipa(String iseeRipa) {
		this.iseeRipa = iseeRipa;
	}

	public Integer getValEconRating() {
		return valEconRating;
	}

	public void setValEconRating(Integer valEconRating) {
		this.valEconRating = valEconRating;
	}

	public String[] getPrestazioniDachis() {
		return prestazioniDachis;
	}

	public void setPrestazioniDachis(String[] prestazioniDachis) {
		this.prestazioniDachis = prestazioniDachis;
	}

	public String getMomValTipo() {
		return momValTipo;
	}

	public void setMomValTipo(String momValTipo) {
		this.momValTipo = momValTipo;
	}

	public String getMomValLuogo() {
		return momValLuogo;
	}

	public void setMomValLuogo(String momValLuogo) {
		this.momValLuogo = momValLuogo;
	}

	public String getMomValRef() {
		return momValRef;
	}

	public void setMomValRef(String momValRef) {
		this.momValRef = momValRef;
	}

	public String getMomValTel() {
		return momValTel;
	}

	public void setMomValTel(String momValTel) {
		this.momValTel = momValTel;
	}

	public String getValSalutePsi() {
		return valSalutePsi;
	}

	public void setValSalutePsi(String valSalutePsi) {
		this.valSalutePsi = valSalutePsi;
	}

	public String getSalFisic() {
		return salFisic;
	}

	public void setSalFisic(String salFisic) {
		this.salFisic = salFisic;
	}

	public Integer getValAutoPers() {
		return valAutoPers;
	}

	public Integer getValAutoDom() {
		return valAutoDom;
	}

	public Integer getValAutoExDom() {
		return valAutoExDom;
	}

	public void setValAutoPers(Integer valAutoPers) {
		this.valAutoPers = valAutoPers;
	}

	public void setValAutoDom(Integer valAutoDom) {
		this.valAutoDom = valAutoDom;
	}

	public void setValAutoExDom(Integer valAutoExDom) {
		this.valAutoExDom = valAutoExDom;
	}

	
	
//	public List<Map<String, Boolean>> getPrestazioniDachiMaps() {
//		return prestazioniDachiMaps;
//	}
//
//	public void setPrestazioniDachiMaps(List<Map<String, Boolean>> prestazioniDachiMaps) {
//		this.prestazioniDachiMaps = prestazioniDachiMaps;
//	}
}
