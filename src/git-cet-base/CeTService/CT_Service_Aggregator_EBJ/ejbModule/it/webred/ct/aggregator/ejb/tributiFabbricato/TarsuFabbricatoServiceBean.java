package it.webred.ct.aggregator.ejb.tributiFabbricato;

import it.webred.ct.aggregator.ejb.tributiFabbricato.dto.DatiTarsuDTO;
import it.webred.ct.aggregator.ejb.tributiFabbricato.TarsuFabbricatoService;
import it.webred.ct.aggregator.ejb.utils.StringUtility;
import it.webred.ct.data.access.basic.catasto.CatastoService;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.CommonService;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.IndiceCorrelazioneService;
import it.webred.ct.data.access.basic.indice.ricerca.KeyFabbricatoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.RicercaIndiceDTO;
import it.webred.ct.data.access.basic.tarsu.TarsuService;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaOggettoTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.SoggettoTarsuDTO;
import it.webred.ct.data.model.catasto.Sitiuiu;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;
import it.webred.ct.data.model.tarsu.VTTarCiviciAll;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.utils.GenericTuples;
import it.webred.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TarsuFabbricatoServiceBean implements TarsuFabbricatoService {
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/CatastoServiceBean")
	private CatastoService catastoService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/CommonServiceBean")
	private CommonService  commonService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/TarsuServiceBean")
	private TarsuService tarsuService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/IndiceCorrelazioneServiceBean")
	private IndiceCorrelazioneService indiceService;
	

	private List<DatiTarsuDTO> valDatiTarsu(List<SitTTarOggetto> listaOgg, String enteId, String userId) {
		List<DatiTarsuDTO> listaDatiTarsu=new ArrayList<DatiTarsuDTO>() ;
		DatiTarsuDTO datiTarsu=null;
		for (SitTTarOggetto ogg : listaOgg) {
			datiTarsu =  valDatiTarsu(ogg,enteId, userId);
			listaDatiTarsu.add(datiTarsu );
		}		
		return listaDatiTarsu;
	}
	
	private DatiTarsuDTO valDatiTarsu(SitTTarOggetto ogg, String enteId, String userId) {
		DatiTarsuDTO datiTarsu=new DatiTarsuDTO();
		
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(enteId);
		cet.setUserId(userId);
		String codEnte = commonService.getEnte(cet).getCodent();
	
		String sezione=""; String foglio=""; String numero=""; String sub="";
		sezione=ogg.getSez() !=null ? ogg.getSez().trim() : "" ;
		foglio=ogg.getFoglio() !=null ? ogg.getFoglio().trim() : "" ;
		numero=ogg.getNumero()!=null ? ogg.getNumero().trim() : "" ;
		sub=ogg.getSub()!=null ? ogg.getSub().trim() : "" ;
		
		if (foglio.equals("") && numero.equals("") && sub.equals(""))
			// Se tutti e tre non ci sono si visualizza la string vuota 
			datiTarsu.setDescFPS("");
		else
			datiTarsu.setDescFPS(foglio + " / " + numero + " / " + sub);
        //soggetti coinvolti
		List<SoggettoTarsuDTO> listaSoggetti = null;
		RicercaOggettoTarsuDTO rt= new RicercaOggettoTarsuDTO();
		rt.setEnteId(enteId);
		rt.setUserId(userId);
		rt.setIdExtOgg(ogg.getIdExt());
		listaSoggetti= tarsuService.getListaSoggettiDichiarazioneTarsu(rt);
		
		datiTarsu.valorizzaDatiDichiarazione(codEnte, ogg, listaSoggetti);
		//dati da catasto
		RicercaOggettoCatDTO roCat=null;
		Date dtRif = new Date();
		if (!foglio.equals("") && !numero.equals("") && !sub.equals(""))  {
			//dai dati catastali immobile
			roCat = new RicercaOggettoCatDTO(codEnte,ogg.getFoglio(), 
					ogg.getNumero(), ogg.getSub(),dtRif);
			if (!sezione.equals(""))
				roCat.setSezione(sezione);
			roCat.setEnteId(enteId);
			roCat.setUserId(userId);
			Sitiuiu ui = catastoService.getDatiUiAllaData(roCat);
			if (ui!=null) {
				//calcolo superficie utile ai fini Tarsu
				BigDecimal supTarsuC340 = catastoService.calcolaSupUtileTarsuC30(roCat);
				if (supTarsuC340 !=null)
					datiTarsu.setSuperficieC340( StringUtility.DF.format(supTarsuC340) );
			}
			
		} 
		
		
		if (!foglio.equals("") && !numero.equals("")){
			
			roCat = new RicercaOggettoCatDTO();
			
			roCat.setEnteId(enteId);
			roCat.setUserId(userId);
			roCat.setFoglio(foglio);
			roCat.setParticella(numero);
			roCat.setCodEnte(codEnte);
			
			String[] latLon = catastoService.getLatitudineLongitudine(roCat);
		
			datiTarsu.setLatitudine(latLon[0]);
			datiTarsu.setLongitudine(latLon[1]);
		
		}
		
		return datiTarsu;
	}
	
	public List<DatiTarsuDTO> getDatiTarsuCiviciDelFabbricato(RicercaOggettoDTO ro) {
		List<DatiTarsuDTO> listaDatiTarsu=new ArrayList<DatiTarsuDTO>() ;
		
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(ro.getEnteId());
		cet.setUserId(ro.getUserId());
		cet.setSessionId(ro.getSessionId());
		
		String codEnte = commonService.getEnte(cet).getCodent();
		
		KeyFabbricatoDTO keyFabbr = new KeyFabbricatoDTO(cet, codEnte, ro.getSezione(), ro.getFoglio(), ro.getParticella(), new Date());
		String TRIBUTI_ENTE_SORGENTE = "2";
		String TRIBUTI_TARSU_TIPO_INFO_OGG= "3";
		List lista = this.getCiviciCorrelatiFromFabbricato(keyFabbr, null, TRIBUTI_ENTE_SORGENTE, TRIBUTI_TARSU_TIPO_INFO_OGG);
		if(lista==null  || lista.size()==0)
			return listaDatiTarsu;
		
		List<VTTarCiviciAll> listaT =(List<VTTarCiviciAll>)lista;
		RicercaOggettoTarsuDTO rt = new RicercaOggettoTarsuDTO();
		rt.setEnteId(ro.getEnteId());
		rt.setListaCivTarsu(listaT);
		rt.setProvenienza(ro.getProvenienza());
		rt.setDtRif(ro.getDtRif());
		List<SitTTarOggetto> listaOggT = tarsuService.getListaOggettiAiCiviciTarsu(rt);
		if (listaOggT==null)
			return listaDatiTarsu;
		DatiTarsuDTO datiTarsu=null;
		String sezione=""; String foglio=""; String numero=""; 
		String key= "";
		if (ro.getSezione()!=null )  
			key =StringUtility.removeLeadingZero(ro.getSezione().trim()) + "|";
		key  += StringUtility.removeLeadingZero(ro.getFoglio().trim()) + "|" + StringUtility.removeLeadingZero(ro.getParticella().trim());
		String currKey="";
		//VOGLIO AVERE GLI OGGETTI AI CIVICI DEL FABBRICATO NON ATTRIBUITI ALLA CHIAVE FABBRICATO, 
		//QUINDI SCARTO GLI OGGETTI CON FOGLIO,PARTICELLA VALORIZZATI E CORRISPONDENTI AL FABBRICATO  
		for (SitTTarOggetto ogg : listaOggT) {
			currKey="";
			sezione=ogg.getSez() !=null ? StringUtility.removeLeadingZero(ogg.getSez().trim()) : "" ;
			foglio=ogg.getFoglio() !=null ? StringUtility.removeLeadingZero(ogg.getFoglio().trim()) : "" ;
			numero=ogg.getNumero()!=null ? StringUtility.removeLeadingZero(ogg.getNumero().trim()) : "" ;
			if (ro.getSezione()!= null && !sezione.equals("")) 
				currKey=sezione +  "|" ;
			currKey += foglio+ "|" + numero;

			if (currKey.equals(key) )
				continue;
			
			datiTarsu =  valDatiTarsu(ogg,ro.getEnteId(), ro.getUserId());
			listaDatiTarsu.add(datiTarsu );
		}		
		return listaDatiTarsu;
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
	
	
	public List<DatiTarsuDTO> getDatiTarsuFabbricato(RicercaOggettoDTO ro) {
		List<DatiTarsuDTO> listaDatiTarsu=new ArrayList<DatiTarsuDTO>() ;
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(ro.getEnteId());
		cet.setUserId(ro.getUserId());
		String codEnte = commonService.getEnte(cet).getCodent();
		//ricerca gli oggetti tARSU
		List<SitTTarOggetto> listaOgg = tarsuService.getListaOggettiByFabbricato(ro);
		if (listaOgg==null)
			return listaDatiTarsu;
		listaDatiTarsu = new ArrayList<DatiTarsuDTO> ();
		String key ="";String currKey="";
		DatiTarsuDTO datiTarsu=null;
		RicercaOggettoTarsuDTO rt =null;
		List<SoggettoTarsuDTO> listaSoggetti = null;
		RicercaOggettoCatDTO roCat=null;
		String sezione="";String foglio=""; String numero=""; String sub ="";
		for (SitTTarOggetto ogg : listaOgg) {
			//una sola riga nella lista per ciascuna coordinata
			sezione=ogg.getSez() !=null ? ogg.getSez().trim() : "" ;
			foglio=ogg.getFoglio() !=null ? ogg.getFoglio().trim() : "" ;
			numero=ogg.getNumero()!=null ? ogg.getNumero().trim() : "" ;
			sub=ogg.getSub()!=null ? ogg.getSub().trim() : "" ;
			currKey=sezione + "|"+ foglio + "|" + numero + "|" + sub;
			if (!key.equals(currKey)){
				//SI PRENDE LA RIGA RELATIVA ALLA DATA FINE POSSESSO più RECENTE, PER CIASCUNA U.I.
					
				rt= new RicercaOggettoTarsuDTO();
				rt.setEnteId(ro.getEnteId());
				rt.setUserId(ro.getUserId());
				rt.setIdExtOgg(ogg.getIdExt());
				listaSoggetti= tarsuService.getListaSoggettiDichiarazioneTarsu(rt);
				datiTarsu =new DatiTarsuDTO();
				if (foglio.equals("") && numero.equals("") && sub.equals(""))
					// Se tutti e tre non ci sono si visualizza la string vuota 
					datiTarsu.setDescFPS("");
				else
					datiTarsu.setDescFPS(foglio + " / " + numero + " / " + sub);

				datiTarsu.valorizzaDatiDichiarazione(codEnte, ogg, listaSoggetti);
				
				Date dtRif = new Date();
				if (!foglio.equals("") && !numero.equals("") && !sub.equals(""))  {
					//dai dati catastali immobile
					roCat = new RicercaOggettoCatDTO(codEnte,ogg.getFoglio(), 
							ogg.getNumero(), ogg.getSub(),dtRif);
					if (!sezione.equals(""))
						roCat.setSezione(sezione);
					roCat.setEnteId(ro.getEnteId());
					roCat.setUserId(ro.getUserId());
					Sitiuiu ui = catastoService.getDatiUiAllaData(roCat);
					if (ui!=null) {
						//calcolo superficie utile ai fini Tarsu
						BigDecimal supTarsuC340 = catastoService.calcolaSupUtileTarsuC30(roCat);
						if (supTarsuC340 !=null)
							datiTarsu.setSuperficieC340( StringUtility.DF.format(supTarsuC340) );
					}
				}
				listaDatiTarsu.add(datiTarsu );
				key=currKey;
			} 
		}		
		
		return listaDatiTarsu;
	}
	
	

	public List<DatiTarsuDTO> getDatiTarsuUI(RicercaOggettoDTO ro) {
		List<DatiTarsuDTO> listaDatiTarsu=new ArrayList<DatiTarsuDTO>() ;
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(ro.getEnteId());
		cet.setUserId(ro.getUserId());
		//String codEnte = commonService.getEnte(cet).getCodent();
		//ricerca gli oggetti tARSU
		List<SitTTarOggetto> listaOgg = tarsuService.getListaOggettiByUI(ro);
		if (listaOgg==null)
			return listaDatiTarsu;
		listaDatiTarsu = valDatiTarsu(listaOgg, ro.getEnteId(), ro.getEnteId());
		return listaDatiTarsu;
	}

}
