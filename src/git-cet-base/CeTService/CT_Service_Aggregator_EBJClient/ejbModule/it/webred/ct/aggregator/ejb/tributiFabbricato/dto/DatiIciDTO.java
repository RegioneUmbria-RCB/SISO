package it.webred.ct.aggregator.ejb.tributiFabbricato.dto;

import it.webred.ct.aggregator.ejb.utils.*;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.catasto.dto.IndirizzoDTO;
import it.webred.ct.data.model.catasto.SiticonduzImmAll;
import it.webred.ct.data.model.catasto.Sitiuiu;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.VTIciSoggAll;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Rappresenta i dati delle denunce Ici della sezione ICI della cartella
public class DatiIciDTO implements Serializable, java.util.Comparator<DatiIciDTO> {
	
	private static final long serialVersionUID = 1L;	
	private String id;
	private String provenienza;
	private String codEnte;
	private String latitudine;
	private String longitudine;
	
	private String sezione;
	private String foglio;
	private String particella;
	private String sub;
	private String annoDenuncia;
	private String numeroDenuncia;
	private String tipoDenuncia;
	private String annoRif;
	private List<VTIciSoggAll> listaSoggetti;//soggetti coinvolti nella dichiarazione
	private String tipologiaCatastale; //=CAR_IMM
	private String categoria;
	private String classe;
	private BigDecimal valImmobile;
	private String valImmobileF;
	private String flImmStorico;
	private IndirizzoIciTarsuDTO indirizzo;
	private BigDecimal perPossesso;
	private String perPossessoF;
	private BigDecimal mesiPossesso;
	private String flAbitPrinc;
	private String flPoss3112;
	private String flAcq;
	private String flCss;
	private String mesiEsenzione;
	private String mesiRiduzione;
	private String flEsenz3112;
	private String flRiduz3112;
	//I successivi non derivano dalla dichiarazione (cioè DALLE TABELLE DELLA BNCA DATI ici) ma da riscontri in banca dati o da calcolo
	private String annoRifConfr;//anno utilizzato per l'acquisizione dei dati-riscontro: coincide con annoRif, se questo è significativo, oppure con annoDdenuncia. Se neanche annoDenuncia è significativo, è la data di sistema 
	private float imponibileIciCalcolato;
	private String imponibileIciCalcolatoStr;
	
	private List<IndirizzoIciTarsuDTO >listaIndirizziDaCatasto;
	private IndirizzoIciTarsuDTO indirizzoDaAnagrafe;
	private List<IndirizzoIciTarsuDTO >listaIndirizziSIT;
	
	private String categoriaDaCatasto;
	private String desCategoriaDaCatasto;
	private String classeDaCatasto;
	
	private String renditaDaCatasto;
	private String perPossessoDaCatasto;
	private String dtIniPossDaCatasto;
	private String dtFinPossDaCatasto; 
	private String flAcqValutatoDaCatasto;
	private String flCssValutatoDaCatasto;
	
	private String descFPS;
 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<IndirizzoIciTarsuDTO> getListaIndirizziSIT() {
		return listaIndirizziSIT;
	}
	public void setListaIndirizziSIT(List<IndirizzoIciTarsuDTO> listaIndirizziSIT) {
		this.listaIndirizziSIT = listaIndirizziSIT;
	}
	public float getImponibileIciCalcolato() {
		return imponibileIciCalcolato;
	}
	public void setImponibileIciCalcolato(float imponibileIciCalcolato) {
		this.imponibileIciCalcolato = imponibileIciCalcolato;
	}
	public String getImponibileIciCalcolatoStr() {
		return imponibileIciCalcolatoStr;
	}
	public void setImponibileIciCalcolatoStr(String imponibileIciCalcolatoStr) {
		this.imponibileIciCalcolatoStr = imponibileIciCalcolatoStr;
	}
	public List<IndirizzoIciTarsuDTO> getListaIndirizziDaCatasto() {
		return listaIndirizziDaCatasto;
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
	
	public String getFlAcqValutatoDaCatasto() {
		return flAcqValutatoDaCatasto;
	}
	public void setFlAcqValutatoDaCatasto(String flAcqValutatoDaCatasto) {
		this.flAcqValutatoDaCatasto = flAcqValutatoDaCatasto;
	}
	
	public String getFlCssValutatoDaCatasto() {
		return flCssValutatoDaCatasto;
	}
	public void setFlCssValutatoDaCatasto(String flCssValutatoDaCatasto) {
		this.flCssValutatoDaCatasto = flCssValutatoDaCatasto;
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
	public String getAnnoDenuncia() {
		return annoDenuncia;
	}
	public void setAnnoDenuncia(String annoDenuncia) {
		this.annoDenuncia = annoDenuncia;
	}
	public String getNumeroDenuncia() {
		return numeroDenuncia;
	}
	public void setNumeroDenuncia(String numeroDenuncia) {
		this.numeroDenuncia = numeroDenuncia;
	}
	public String getTipoDenuncia() {
		return tipoDenuncia;
	}
	public void setTipoDenuncia(String tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}
	public String getAnnoRif() {
		return annoRif;
	}
	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}
	public String getTipologiaCatastale() {
		return tipologiaCatastale;
	}
	public void setTipologiaCatastale(String tipologiaCatastale) {
		this.tipologiaCatastale = tipologiaCatastale;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public BigDecimal getValImmobile() {
		return valImmobile;
	}
	public void setValImmobile(BigDecimal valImmobile) {
		this.valImmobile = valImmobile;
	}
	public void setValImmobileF(String valImmobileF) {
		this.valImmobileF = valImmobileF;
	}
	public String getValImmobileF() {
		return valImmobileF;
	}
	public String getFlImmStorico() {
		return flImmStorico;
	}
	public void setFlImmStorico(String flImmStorico) {
		this.flImmStorico = flImmStorico;
	}
	public IndirizzoIciTarsuDTO getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(IndirizzoIciTarsuDTO indirizzo) {
		this.indirizzo = indirizzo;
	}
	public BigDecimal getPerPossesso() {
		return perPossesso;
	}
	public void setPerPossesso(BigDecimal perPossesso) {
		this.perPossesso = perPossesso;
	}
	public void setPerPossessoF(String perPossessoF) {
		this.perPossessoF = perPossessoF;
	}
	public String getPerPossessoF() {
		return perPossessoF;
	}
	public BigDecimal getMesiPossesso() {
		return mesiPossesso;
	}
	public void setMesiPossesso(BigDecimal mesiPossesso) {
		this.mesiPossesso = mesiPossesso;
	}
	
	public String getFlPoss3112() {
		return flPoss3112;
	}
	public void setFlPoss3112(String flPoss3112) {
		this.flPoss3112 = flPoss3112;
	}
	public String getFlAbitPrinc() {
		return flAbitPrinc;
	}
	public void setFlAbitPrinc(String flAbitPrinc) {
		this.flAbitPrinc = flAbitPrinc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFlAcq() {
		return flAcq;
	}
	public void setFlAcq(String flAcq) {
		this.flAcq= flAcq;
	}
	public String getFlCss() {
		return flCss;
	}
	public void setFlCss(String flCss) {
		this.flCss = flCss;
	}
	public String getMesiEsenzione() {
		return mesiEsenzione;
	}
	public void setMesiEsenzione(String mesiEsenzione) {
		this.mesiEsenzione = mesiEsenzione;
	}
	public String getMesiRiduzione() {
		return mesiRiduzione;
	}
	public void setMesiRiduzione(String mesiRiduzione) {
		this.mesiRiduzione = mesiRiduzione;
	}
	public String getFlEsenz3112() {
		return flEsenz3112;
	}
	public void setFlEsenz3112(String flEsenz3112) {
		this.flEsenz3112 = flEsenz3112;
	}
	public String getFlRiduz3112() {
		return flRiduz3112;
	}
	public void setFlRiduz3112(String flRiduz3112) {
		this.flRiduz3112 = flRiduz3112;
	}
	public List<VTIciSoggAll> getListaSoggetti() {
		return listaSoggetti;
	}
	public void setListaSoggetti(List<VTIciSoggAll> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}
	
	public String getAnnoRifConfr() {
		return annoRifConfr;
	}
	public void setAnnoRifConfr(String annoRifConfr) {
		this.annoRifConfr = annoRifConfr;
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
	
	public String getDesCategoriaDaCatasto() {
		return desCategoriaDaCatasto;
	}
	public void setDesCategoriaDaCatasto(String desCategoriaDaCatasto) {
		this.desCategoriaDaCatasto = desCategoriaDaCatasto;
	}
	public void valorizzaDatiDichiarazione(String codEnte, SitTIciOggetto ogg, List<VTIciSoggAll> listaSoggetti) {
		String foglio=""; String numero=""; String sub="";
		foglio=ogg.getFoglio() !=null ? ogg.getFoglio().trim() : "" ;
		numero=ogg.getNumero()!=null ? ogg.getNumero().trim() : "" ;
		sub=ogg.getSub()!=null ? ogg.getSub().trim() : "" ;
		if (foglio.equals("") && numero.equals("") && sub.equals(""))
			// Se tutti e tre non ci sono si visualizza la string vuota 
			this.setDescFPS("");
		else
			this.setDescFPS(foglio + " / " + numero + " / " + sub );
		this.provenienza=ogg.getProvenienza();
		this.id=ogg.getId();
		this.sezione=ogg.getSez();
		this.codEnte=codEnte;
		
		this.foglio= foglio;
		this.particella=numero;
		this.sub=sub;
		
		this.annoDenuncia=ogg.getYeaDen();
		this.numeroDenuncia=ogg.getNumDen();
		this.tipoDenuncia=ogg.getTipDen();
		this.annoRif=ogg.getYeaRif();
		this.tipologiaCatastale=ogg.getCarImm();
		this.categoria=ogg.getCat();
		this.classe=ogg.getCls();
		this.valImmobile=ogg.getValImm();
		if(ogg.getValImm()!=null)
			this.valImmobileF = StringUtility.DFEURO.format(ogg.getValImm());
		else
			this.valImmobileF = "-";
		this.flImmStorico=ogg.getFlgImmSto();
		this.perPossesso=ogg.getPrcPoss();
		this.flPoss3112=ogg.getFlgPos3112();
		if (ogg.getPrcPoss()!=null)
			this.perPossessoF = StringUtility.DFEURO.format(ogg.getPrcPoss());
		else
			this.perPossessoF = "-";
		this.mesiPossesso=ogg.getMesiPos();
		this.flAbitPrinc=ogg.getFlgAbiPri3112();
		this.flAcq=ogg.getFlgAcq();
		this.flCss=ogg.getFlgCss();
		IndirizzoIciTarsuDTO indirizzo= new IndirizzoIciTarsuDTO();
		indirizzo.valorizza(ogg);
		this.setIndirizzo(indirizzo);
		this.listaSoggetti=listaSoggetti;
		
		if (ogg.getMesiEse()!=null)
			this.mesiEsenzione=ogg.getMesiEse().toString();
		if (ogg.getMesiRid()!=null)
			this.mesiRiduzione=ogg.getMesiRid().toString();
		if(ogg.getFlgEse3112()!=null)
			this.flEsenz3112=ogg.getFlgEse3112();
		if(ogg.getFlgRid3112()!=null)
			this.flRiduz3112=ogg.getFlgRid3112();
	}	
	
	public void valorizzaDatiCatasto(Sitiuiu ui, SiticonduzImmAll datiTitImm, List<IndirizzoDTO> listaInd, List<IndirizzoDTO> listaIndSIT ) {
		this.categoriaDaCatasto="-";
		this.classeDaCatasto="-";
		this.renditaDaCatasto="-";
		if (ui != null)  {
			if(ui.getCategoria() != null)
				this.categoriaDaCatasto=ui.getCategoria();
			if(ui.getClasse() != null)
				this.classeDaCatasto=ui.getClasse();
			if(ui.getRendita()  != null) {
				this.renditaDaCatasto= StringUtility.DFEURO.format(ui.getRendita()) ;
				if(ui.getCategoria() != null)
					this.imponibileIciCalcolato=IciUtilsFunctions.getImponibileIci(ui.getRendita().floatValue(), ui.getCategoria());
				    this.imponibileIciCalcolatoStr=StringUtility.DFEURO.format(this.imponibileIciCalcolato);
			}
		}
		this.perPossessoDaCatasto="-";
		this.dtIniPossDaCatasto="-";
		this.dtFinPossDaCatasto="-";
		this.flAcqValutatoDaCatasto="-";
		this.flCssValutatoDaCatasto="-";
		if (datiTitImm!= null)  {
			this.perPossessoDaCatasto=StringUtility.DFEURO.format(datiTitImm.getPercPoss());
			if(datiTitImm.getDataInizio() !=null)
				this.dtIniPossDaCatasto=DateUtility.formatta(datiTitImm.getDataInizio(), DateUtility.FMT_DATE_VIS);
			if (datiTitImm.getId().getDataFine() != null)
				this.dtFinPossDaCatasto=DateUtility.formatta(datiTitImm.getId().getDataFine(), DateUtility.FMT_DATE_VIS);
			int anno=0;
			try  {
				anno=Integer.parseInt(this.annoRifConfr);
			}catch (NumberFormatException nfe){	}
			Date dtRif = DateUtility.dataInizioFineAnno(anno, "F");
			Date dtIniAnnoRif = DateUtility.dataInizioFineAnno(anno, "I");
			if(datiTitImm.getDataInizio() != null)  {
				if (dtIniAnnoRif.compareTo(datiTitImm.getDataInizio()) <= 0 &&
					datiTitImm.getDataInizio().compareTo(dtRif) <= 0) {
					this.flAcqValutatoDaCatasto="S";//acquistato nell'anno
				}else
					this.flAcqValutatoDaCatasto="N";
			}
			if(datiTitImm.getId().getDataFine()!= null)  {
				if (dtIniAnnoRif.compareTo(datiTitImm.getId().getDataFine()) <= 0 &&
					datiTitImm.getId().getDataFine().compareTo(dtRif) <= 0) {
					this.flCssValutatoDaCatasto="S";//ceduto nell'anno
				}else
					this.flCssValutatoDaCatasto="N";
				
			}
		}
		//indirizzi catasto
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
	
	public void valorizzaDatiSintesi(SitTIciOggetto ogg) {
		this.sezione=ogg.getSez() !=null ? ogg.getSez().trim() : "" ;
		this.foglio=ogg.getFoglio() !=null ? ogg.getFoglio().trim() : "" ;
		this.particella=ogg.getNumero()!=null ? ogg.getNumero().trim() : "" ;
		this.sub=ogg.getSub()!=null ? ogg.getSub().trim() : "" ;
		this.annoRif = ogg.getYeaRif() != null ? ogg.getYeaRif() .toString(): "";
		this.annoDenuncia = ogg.getYeaDen() != null ? ogg.getYeaDen() .toString(): "";
		this.categoria = ogg.getCat() != null ? ogg.getCat(): "";
		this.valImmobile=ogg.getValImm();
		if (ogg.getValImm() !=null )
			this.valImmobileF=StringUtility.DFEURO.format(ogg.getValImm());
		else
			this.valImmobileF="";
	}
	
	public int compare(DatiIciDTO arg0, DatiIciDTO arg1) {
	   int retVal=0;
	   if (arg0==null && arg1==null)
		  return 0;
	   if (arg0==null && arg1 !=null)
		  return -1;
	   if (arg0!=null && arg1 ==null)
		  return 1;
	   if (arg0.getAnnoRifConfr() !=null && arg1.getAnnoRifConfr() !=null) {
		   return  arg0.getAnnoRifConfr().compareTo(arg1.getAnnoRifConfr());
	   }

	   if (arg0.getAnnoRif()==null && arg0.getAnnoDenuncia()== null &&
		   arg1.getAnnoRif()==null && arg1.getAnnoDenuncia()== null) {
		  return 0;
	   }
	   if (arg0.getAnnoRif()==null && arg0.getAnnoDenuncia()== null &&
		  (arg1.getAnnoRif()!=null || arg1.getAnnoDenuncia()!= null)) {
		  return -1;
	   } 
	   if ((arg0.getAnnoRif()!=null || arg0.getAnnoDenuncia() != null) &&
		    arg1.getAnnoRif()==null && arg1.getAnnoDenuncia()== null) {
		  return 1;
	   } 
	   if (arg0.getAnnoRif()!=null && arg1.getAnnoRif()!=null) {
		  retVal = arg0.getAnnoRif().compareTo(arg1.getAnnoRif());
	   }else
		  retVal = arg0.getAnnoDenuncia().compareTo(arg1.getAnnoDenuncia());
	  
	   return retVal; 
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
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
