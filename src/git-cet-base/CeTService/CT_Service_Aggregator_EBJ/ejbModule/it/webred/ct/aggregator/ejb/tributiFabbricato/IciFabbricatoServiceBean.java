package it.webred.ct.aggregator.ejb.tributiFabbricato;

import it.webred.ct.aggregator.ejb.tributiFabbricato.dto.DatiIciDTO;
import it.webred.ct.aggregator.ejb.tributiFabbricato.IciFabbricatoService;
import it.webred.ct.aggregator.ejb.utils.DateUtility;
import it.webred.ct.aggregator.ejb.utils.IciUtilsFunctions;
import it.webred.ct.aggregator.ejb.utils.StringUtility;
import it.webred.ct.data.access.basic.catasto.CatastoService;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.CommonService;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.ici.IciService;
import it.webred.ct.data.access.basic.ici.dto.RicercaOggettoIciDTO;
import it.webred.ct.data.access.basic.indice.ricerca.IndiceCorrelazioneService;
import it.webred.ct.data.access.basic.indice.ricerca.KeyFabbricatoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.RicercaIndiceDTO;
import it.webred.ct.data.model.catasto.Sitiuiu;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.VTIciCiviciAll;
import it.webred.ct.data.model.ici.VTIciSoggAll;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class IciFabbricatoServiceBean implements IciFabbricatoService{

	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/CommonServiceBean")
	private CommonService  commonService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/CatastoServiceBean")
	private CatastoService catastoService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/IciServiceBean")
	private IciService iciService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/IndiceCorrelazioneServiceBean")
	private IndiceCorrelazioneService indiceService;	
	
	
	public List<DatiIciDTO> getDatiIciCiviciDelFabbricato(RicercaOggettoDTO ro) {
		List<DatiIciDTO> listaDatiIci=new ArrayList<DatiIciDTO>();
		
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(ro.getEnteId());
		cet.setUserId(ro.getUserId());
		cet.setSessionId(ro.getSessionId());
		
		String codEnte = commonService.getEnte(cet).getCodent();
		KeyFabbricatoDTO keyFabbr = new KeyFabbricatoDTO(cet, codEnte, ro.getSezione(), ro.getFoglio(), ro.getParticella(), new Date());
		
		String TRIBUTI_ENTE_SORGENTE = "2";
		String TRIBUTI_ICI_TIPO_INFO_OGG="2";		
		List lista  = this.getCiviciCorrelatiFromFabbricato(keyFabbr, null, TRIBUTI_ENTE_SORGENTE, TRIBUTI_ICI_TIPO_INFO_OGG);
		if (lista==null || lista.size() == 0)
			return listaDatiIci;
		List<VTIciCiviciAll> listaT =(List<VTIciCiviciAll>)lista;
		RicercaOggettoIciDTO roi = new RicercaOggettoIciDTO();
		roi.setEnteId(ro.getEnteId());
		roi.setListaCivIci(listaT);
		roi.setProvenienza(ro.getProvenienza());
		roi.setDtRif(ro.getDtRif());
		List<SitTIciOggetto> listaOggI = iciService.getListaOggettiAiCiviciIci(roi);
		
		if (listaOggI==null)
			return listaDatiIci;
		DatiIciDTO datiIci=null;
		String sezione=""; String foglio=""; String numero=""; 
		String key= "";
		if (ro.getSezione()!=null )  
			key =StringUtility.removeLeadingZero(ro.getSezione().trim()) + "|";
		key  += StringUtility.removeLeadingZero(ro.getFoglio().trim()) + "|" + StringUtility.removeLeadingZero(ro.getParticella().trim());
		String currKey="";
		//VOGLIO AVERE GLI OGGETTI AI CIVICI DEL FABBRICATO NON ATTRIBUITI ALLA CHIAVE FABBRICATO, QUINDI SCARTO GLI OGGETTI CON FOGLIO,PARTICELLA VALORIZZATI E CORRISPONDENTI AL FABBRICATO  
		for (SitTIciOggetto ogg : listaOggI) {
			currKey="";
			sezione=ogg.getSez() !=null ? StringUtility.removeLeadingZero(ogg.getSez().trim()) : "" ;
			foglio=ogg.getFoglio() !=null ? StringUtility.removeLeadingZero(ogg.getFoglio().trim()) : "" ;
			numero=ogg.getNumero()!=null ? StringUtility.removeLeadingZero(ogg.getNumero().trim()) : "" ;
			if (ro.getSezione()!= null && !sezione.equals("")) 
				currKey=sezione +  "|" ;
			currKey += foglio+ "|" + numero;
  		    if (currKey.equals(key) )
				continue;
			
  		    datiIci=  valDatiIci(codEnte, ogg, ro.getEnteId(), ro.getUserId());
			
			//Valorizzazione parametri latitudine, longitudine
			RicercaOggettoCatDTO roCat=null;
			if (!foglio.equals("") && !numero.equals("")){
				
				roCat = new RicercaOggettoCatDTO();
				
				roCat.setEnteId(ro.getEnteId());
				roCat.setUserId(ro.getUserId());
				roCat.setFoglio(foglio);
				roCat.setParticella(numero);
				roCat.setCodEnte(codEnte);
				
				String[] latLon = catastoService.getLatitudineLongitudine(roCat);
			
				datiIci.setLatitudine(latLon[0]);
				datiIci.setLongitudine(latLon[1]);
			
			}
			
			listaDatiIci.add(datiIci );
		}		
		return listaDatiIci;
		
		
	}
	

	private DatiIciDTO valDatiIci(String codEnte, SitTIciOggetto ogg , String enteId, String userId) {
		DatiIciDTO datiIci =new DatiIciDTO();
		int anno =0;
		try {anno=Integer.parseInt(ogg.getYeaRif()); } catch (NumberFormatException nfe){}
		if (anno==0) {
			try {anno=Integer.parseInt(ogg.getYeaDen()); } catch (NumberFormatException nfe){}
		}
		RicercaOggettoIciDTO roi=new RicercaOggettoIciDTO(null,ogg.getIdExt());
		roi.setEnteId(enteId);
		roi.setUserId(userId);
		List<VTIciSoggAll> listaSoggetti=iciService.getListaSoggettiByOgg(roi);
		datiIci.valorizzaDatiDichiarazione(codEnte, ogg, listaSoggetti);
		datiIci.setAnnoRifConfr(anno+"");
		return datiIci;
	}
	
	
	public List<Object> getCiviciCorrelatiFromFabbricato(KeyFabbricatoDTO keyFabbr,String progEs, String destFonte, String destProgEs) {
		RicercaIndiceDTO ri= new RicercaIndiceDTO();
		ri.setEnteId(keyFabbr.getEnteId());
		ri.setUserId(keyFabbr.getUserId());
		ri.setObj(keyFabbr);
		ri.setProgressivoEs(progEs);
		ri.setDestFonte(destFonte); //
		ri.setDestProgressivoEs(destProgEs);//per test correlazione tarsu
		List<Object> lista = indiceService.getCiviciCorrelatiFromFabbricato(ri);
		return lista;
	}
	
	public List<DatiIciDTO> getDatiIciFabbricato(RicercaOggettoDTO ro) {
		List<DatiIciDTO> listaIciFF=new ArrayList<DatiIciDTO>();
		DatiIciDTO datiIci=null;
		RicercaOggettoCatDTO roCat=null;
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(ro.getEnteId());
		cet.setUserId(ro.getUserId());
		String codEnte = commonService.getEnte(cet).getCodent();
		List<SitTIciOggetto> listaOgg= iciService.getOggettiByFabbricato(ro);
		String key ="";String currKey="";String subKey=""; String subKeyPrec="";
		String sezione="";String foglio=""; String numero=""; String sub ="";
		String cat="";String catPrec="";
		String annoDen=""; String annoRif="";
		for (SitTIciOggetto ogg : listaOgg) {
			sezione=ogg.getSez() !=null ? ogg.getSez().trim() : "" ;
			foglio=ogg.getFoglio() !=null ? ogg.getFoglio().trim() : "" ;
			numero=ogg.getNumero()!=null ? ogg.getNumero().trim() : "" ;
			sub=ogg.getSub()!=null ? ogg.getSub().trim() : "" ;
			cat= ogg.getCat()!=null ? ogg.getCat().trim() : "" ;
			annoRif = ogg.getYeaRif() != null ? ogg.getYeaRif() .toString(): "";
			annoDen = ogg.getYeaDen() != null ? ogg.getYeaDen() .toString(): "";
			currKey=sezione + "|"+ foglio + "|" + numero + "|" + sub;   
			subKey= annoRif + "|" + annoDen;
			if (!key.equals(currKey) || (subKey.equals(subKeyPrec) && !cat.equals(catPrec) ) ){
				datiIci=new DatiIciDTO();
				datiIci.valorizzaDatiSintesi(ogg);
				//dati da reperire/calcolare al di fuori della banca dati ici
				int anno=0;//anno di riferimento per la verifica dei dati
				try  {
					anno=Integer.parseInt(ogg.getYeaRif());
				}catch (NumberFormatException nfe){	}
				if (anno ==0) {
					try  {
						anno=Integer.parseInt(ogg.getYeaDen());
					}catch (NumberFormatException nfe){	}
				}
				if (anno==0){
					Date dataSys = new Date();
					anno=DateUtility.annoData(dataSys);
				}
				datiIci.setAnnoRifConfr(anno+"");
				Date dtRif = DateUtility.dataInizioFineAnno(anno, "I");
				int subNum=-1;
				try {subNum=Integer.parseInt(sub);} catch(NumberFormatException nfe) {}
				if (!foglio.equals("") && !numero.equals("") && !sub.equals("") && subNum !=0 )  {
					//dai dati catastali immobile
					roCat = new RicercaOggettoCatDTO(codEnte,ogg.getFoglio(), 
							ogg.getNumero(), ogg.getSub(),dtRif);
					if (!sezione.equals(""))
						roCat.setSezione(sezione);
					roCat.setEnteId(ro.getEnteId());
					roCat.setUserId(ro.getUserId());
					Sitiuiu ui = catastoService.getDatiUiAllaData(roCat);
					if (ui !=null) {
						if (ui.getRendita() !=null) {
							datiIci.setRenditaDaCatasto(StringUtility.DFEURO.format(ui.getRendita()));
							datiIci.setCategoriaDaCatasto(ui.getCategoria());
							float imponibileIci = IciUtilsFunctions.getImponibileIci(ui.getRendita().floatValue(), ui.getCategoria());
							if (imponibileIci >0) {
								datiIci.setImponibileIciCalcolato(imponibileIci);
								datiIci.setImponibileIciCalcolatoStr(StringUtility.DFEURO.format(imponibileIci ));
							}else
								datiIci.setImponibileIciCalcolatoStr("");
								
						}else
							datiIci.setRenditaDaCatasto("");
					}
				}
				listaIciFF.add(datiIci);
				key=currKey;
				subKeyPrec=subKey;
				catPrec=cat;
			}
		}
		return listaIciFF;
	}
	
	


	public List<DatiIciDTO> getDatiIciUI(RicercaOggettoDTO ro) {
		
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(ro.getEnteId());
		cet.setUserId(ro.getUserId());
		cet.setSessionId(ro.getSessionId());
		
		String codEnte = commonService.getEnte(cet).getCodent();
		
		List<DatiIciDTO> listaIciUI=new ArrayList<DatiIciDTO>();
		DatiIciDTO datiIci=null;
		List<SitTIciOggetto> listaOgg= iciService.getOggettiByUI(ro);
		RicercaOggettoIciDTO roi=null;
		List<VTIciSoggAll> listaSoggetti = null;
		for (SitTIciOggetto ogg : listaOgg) {
			int anno =0;
			try {anno=Integer.parseInt(ogg.getYeaRif()); } catch (NumberFormatException nfe){}
			if (ro.getAnnoRif() != null && !ro.getAnnoRif().equals("")) {//se è valorizzato,, si considerano solo quelle nell'anno
				if (ogg.getYeaRif()!=null && !ogg.getYeaRif().equals("") && anno!= 0) {
					if (!ro.getAnnoRif().equals(ogg.getYeaRif()) )
						break;
				}
			}
			if (anno==0) {
				try {anno=Integer.parseInt(ogg.getYeaDen()); } catch (NumberFormatException nfe){}
				if (ro.getAnnoRif() != null && !ro.getAnnoRif().equals("")) {//se è valorizzato,, si considerano solo quelle nell'anno
					if (ogg.getYeaDen()!=null && !ogg.getYeaDen().equals("") && anno!= 0) {
						if (!ro.getAnnoRif().equals(ogg.getYeaDen())) 
							break;
					}
				}	
			}
			datiIci = new DatiIciDTO();
			roi=new RicercaOggettoIciDTO(null,ogg.getIdExt());
			roi.setEnteId(ro.getEnteId());
			roi.setUserId(ro.getUserId());
			listaSoggetti=iciService.getListaSoggettiByOgg(roi);
			datiIci.valorizzaDatiDichiarazione(codEnte, ogg, listaSoggetti);
			datiIci.setAnnoRifConfr(anno+"");
			listaIciUI.add(datiIci);
			
		}
		return listaIciUI;
	}
	
}
