package it.webred.ct.aggregator.ejb.tributiFabbricato.dto;

import it.webred.ct.aggregator.ejb.utils.*;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.catasto.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.tarsu.dto.SoggettoTarsuDTO;
import it.webred.ct.data.model.catasto.SiticonduzImmAll;
import it.webred.ct.data.model.catasto.Sitiuiu;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatiTarsuDTO implements Serializable {
	private static final long serialVersionUID = 1L;	
	private String id;
	private String provenienza;
	private String codEnte;
	
	private String sezione;
	private String foglio;
	private String particella;
	private String sub;
	private String desClasse; // DES_CLS_RSU
	private String desTipOgg;
	private String supTot;
	private String dtIniPos;
	private String dtFinPos;
	private IndirizzoIciTarsuDTO indirizzo;
	private List<SoggettoTarsuDTO> listaSoggetti;//soggetti coinvolti nella dichiarazione
	//I successivi non derivano dalla dichiarazione (cioè DALLE TABELLE DELLA BANCA DATI TARSU) ma da riscontri in banca dati
	private String desTipOggDaCatasto;
	private String dtIniPossDaCatasto;
	private String dtFinPossDaCatasto; 
	
	private List<IndirizzoIciTarsuDTO >listaIndirizziDaCatasto;
	private IndirizzoIciTarsuDTO indirizzoDaAnagrafe;
	private List<IndirizzoIciTarsuDTO >listaIndirizziSIT;
	//
	private String categoriaDaCatasto;
	private String classeDaCatasto;
	private String superficieDaCatasto;
	private String superficieC340;
	private String renditaDaCatasto;
	private String perPossessoDaCatasto;
	
	private String descrDtFinPos;
	
	private String latitudine;
	private String longitudine;
	
	public String getSuperficieC340() {
		return superficieC340;
	}
	public void setSuperficieC340(String superficieC340) {
		this.superficieC340 = superficieC340;
	}

	private String descFPS;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	
	public String getSupTot() {
		return supTot;
	}
	public void setSupTot(String supTot) {
		this.supTot = supTot;
	}
	public String getDtIniPos() {
		return dtIniPos;
	}
	public void setDtIniPos(String dtIniPos) {
		this.dtIniPos = dtIniPos;
	}
	public String getDtFinPos() {
		return dtFinPos;
	}
	public void setDtFinPos(String dtFinPos) {
		this.dtFinPos = dtFinPos;
	}
	public String getDtIniPossDaCatasto() {
		return dtIniPossDaCatasto;
	}
	public void setDtIniPossDaCatasto(String dtIniPossDaCatasto) {
		this.dtIniPossDaCatasto = dtIniPossDaCatasto;
	}
	public String getDtFinPossDaCatasto() {
		return dtFinPossDaCatasto;
	}
	public void setDtFinPossDaCatasto(String dtFinPossDaCatasto) {
		this.dtFinPossDaCatasto = dtFinPossDaCatasto;
	}
	public String getSuperficieDaCatasto() {
		return superficieDaCatasto;
	}
	public void setSuperficieDaCatasto(String superficieDaCatasto) {
		this.superficieDaCatasto = superficieDaCatasto;
	}
	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public String getRenditaDaCatasto() {
		return renditaDaCatasto;
	}
	public void setRenditaDaCatasto(String renditaDaCatasto) {
		this.renditaDaCatasto = renditaDaCatasto;
	}
	public String getPerPossessoDaCatasto() {
		return perPossessoDaCatasto;
	}
	public void setPerPossessoDaCatasto(String perPossessoDaCatasto) {
		this.perPossessoDaCatasto = perPossessoDaCatasto;
	}
	public void setDescFPS(String descFPS) {
		this.descFPS = descFPS;
	}
	public String getDescFPS() {
		return descFPS;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getDesClasse() {
		return desClasse;
	}
	public void setDesClasse(String desClasse) {
		this.desClasse = desClasse;
	}
	public String getDesTipOgg() {
		return desTipOgg;
	}
	public void setDesTipOgg(String desTipOgg) {
		this.desTipOgg = desTipOgg;
	}
	
		
	public List<SoggettoTarsuDTO> getListaSoggetti() {
		return listaSoggetti;
	}
	public void setListaSoggetti(List<SoggettoTarsuDTO> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}
	
	 public IndirizzoIciTarsuDTO getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(IndirizzoIciTarsuDTO indirizzo) {
		this.indirizzo = indirizzo;
	}
	public List<IndirizzoIciTarsuDTO> getListaIndirizziDaCatasto() {
		return listaIndirizziDaCatasto;
	}
	
	public String getDesTipOggDaCatasto() {
		return desTipOggDaCatasto;
	}
	public void setDesTipOggDaCatasto(String desTipOggDaCatasto) {
		this.desTipOggDaCatasto = desTipOggDaCatasto;
	}
	public String getCategoriaDaCatasto() {
		return categoriaDaCatasto;
	}
	public void setCategoriaDaCatasto(String categoriaDaCatasto) {
		this.categoriaDaCatasto = categoriaDaCatasto;
	}
	public String getClasseDaCatasto() {
		return classeDaCatasto;
	}
	public void setClasseDaCatasto(String classeDaCatasto) {
		this.classeDaCatasto = classeDaCatasto;
	}
	
	public void setListaIndirizziDaCatasto(
			List<IndirizzoIciTarsuDTO> listaIndirizziDaCatasto) {
		this.listaIndirizziDaCatasto = listaIndirizziDaCatasto;
	}
	
	public IndirizzoIciTarsuDTO getIndirizzoDaAnagrafe() {
		return indirizzoDaAnagrafe;
	}
	public void setIndirizzoDaAnagrafe(IndirizzoIciTarsuDTO indirizzoDaAnagrafe) {
		this.indirizzoDaAnagrafe = indirizzoDaAnagrafe;
	}
	
	public List<IndirizzoIciTarsuDTO> getListaIndirizziSIT() {
		return listaIndirizziSIT;
	}
	public void setListaIndirizziSIT(List<IndirizzoIciTarsuDTO> listaIndirizziSIT) {
		this.listaIndirizziSIT = listaIndirizziSIT;
	}
	public void valorizzaDatiDichiarazione(String codEnte, SitTTarOggetto ogg, List<SoggettoTarsuDTO> listaSoggetti) {
		
		this.id = ogg.getId();
		this.provenienza=ogg.getProvenienza();
		this.codEnte=codEnte;
		
		if (ogg.getSez()!=null)
			this.sezione=ogg.getSez();
		if(ogg.getFoglio()!=null)
			this.foglio=ogg.getFoglio();
		if(ogg.getNumero()!=null)
			this.particella=ogg.getNumero();
		if(ogg.getSub()!=null)
			this.sub=ogg.getSub();
		if (ogg.getDesClsRsu() != null)
			this.desClasse=ogg.getDesClsRsu().trim();
		this.desTipOgg=ogg.getDesTipOgg();
	    this.supTot ="";
	    if (ogg.getSupTot()!=null)
	    	this.supTot= StringUtility.DF.format(ogg.getSupTot()) ;
	    this.dtIniPos="";
	    this.dtFinPos="";
	    if (ogg.getDatIni()!=null)
	    	this.dtIniPos=DateUtility.formatta(ogg.getDatIni(), DateUtility.FMT_DATE_VIS);
	    if (ogg.getDatFin()!=null)
	    	this.dtFinPos=DateUtility.formatta(ogg.getDatFin(), DateUtility.FMT_DATE_VIS);
	    IndirizzoIciTarsuDTO indirizzo= new IndirizzoIciTarsuDTO();
		indirizzo.valorizza(ogg);
		this.setIndirizzo(indirizzo);
		this.listaSoggetti=listaSoggetti;
		if(ogg.getDesClsRsu()!=null)
			this.desClasse=ogg.getDesClsRsu();
		if(ogg.getDesTipOgg()!=null)
			this.desTipOgg=ogg.getDesTipOgg();
	}
	
	public void valorizzaDatiCatasto(Sitiuiu ui, SiticonduzImmAll datiTitImm, List<IndirizzoDTO> listaInd, List<IndirizzoDTO> listaIndSIT ) {
		this.categoriaDaCatasto="-";
		this.classeDaCatasto="-";
		this.renditaDaCatasto="-";
		this.superficieDaCatasto="";
		this.dtIniPossDaCatasto="-";
		this.dtFinPossDaCatasto="-";
		this.perPossessoDaCatasto="-";
		if (ui != null)  {
			this.categoriaDaCatasto=ui.getCategoria();
			this.classeDaCatasto=ui.getClasse();
			if (ui.getRendita() !=null)
				this.renditaDaCatasto = StringUtility.DFEURO.format(ui.getRendita()) ;
			if (ui.getSupCat() !=null)
				this.superficieDaCatasto = StringUtility.DF.format(ui.getSupCat()) ;
		}
		if (datiTitImm!= null)  {
			if (ui!=null && ui.getSupCat() !=null)
				this.perPossessoDaCatasto = StringUtility.DF.format(datiTitImm.getPercPoss()) ;
			if (datiTitImm.getDataInizio()!=null)
			    this.dtIniPossDaCatasto=DateUtility.formatta(datiTitImm.getDataInizio(), DateUtility.FMT_DATE_VIS);
			if (datiTitImm.getId().getDataFine()!=null)
			    this.dtFinPossDaCatasto=DateUtility.formatta(datiTitImm.getId().getDataFine(), DateUtility.FMT_DATE_VIS);
		}
		//indirizzi
		if (listaInd !=null && listaInd.size() >0 ) {
			listaIndirizziDaCatasto = new ArrayList<IndirizzoIciTarsuDTO>();
			for (IndirizzoDTO indCat : listaInd) {
				IndirizzoIciTarsuDTO ind=new IndirizzoIciTarsuDTO();  
				ind.valorizza(indCat);
				listaIndirizziDaCatasto.add(ind);
			}
		}
		//indirizzi SIT
		if (listaIndSIT !=null && listaIndSIT.size() >0 ) {
			listaIndirizziSIT = new ArrayList<IndirizzoIciTarsuDTO>();
			for (IndirizzoDTO indCat : listaIndSIT) {
				IndirizzoIciTarsuDTO ind=new IndirizzoIciTarsuDTO();  
				ind.valorizza(indCat);
				listaIndirizziSIT.add(ind);
			}
		}
	}
	
	public void valorizzaIndirizzoAnagrafe(IndirizzoAnagrafeDTO indAna){
		IndirizzoIciTarsuDTO ind = new IndirizzoIciTarsuDTO();  
		ind.valorizza(indAna);
		this.setIndirizzoDaAnagrafe(ind);
		
	}
	public String getDescrDtFinPos() {
		return (dtFinPos==null || "".equals(dtFinPos)) ? "ATTUALE" : dtFinPos;
	}
	
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	
}
