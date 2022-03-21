package it.webred.ct.aggregator.ejb;
 
import it.webred.ct.aggregator.ejb.dto.DatiCivicoDTO;
import it.webred.ct.aggregator.ejb.dto.RichiestaDatiCivicoDTO;
import it.webred.ct.aggregator.ejb.dto.SitLicenzeCommercioDTO;
import it.webred.ct.aggregator.ejb.dto.ViaDTO;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiCivicoAnagrafeDTO;
import it.webred.ct.data.access.basic.catasto.CatastoService;
import it.webred.ct.data.access.basic.catasto.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.catasto.dto.InfoPerCategoriaDTO;
import it.webred.ct.data.access.basic.catasto.dto.ParticellaInfoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.indice.civico.CivicoService;
import it.webred.ct.data.access.basic.indice.civico.dto.RicercaCivicoIndiceDTO;
import it.webred.ct.data.access.basic.indice.via.ViaService;
import it.webred.ct.data.access.basic.licenzecommercio.LicenzeCommercioService;
import it.webred.ct.data.access.basic.locazioni.LocazioniService;
import it.webred.ct.data.model.catasto.ConsSoggTab;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.data.model.indice.SitViaUnico;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
 
@Stateless 
public class DatiCivicoServiceBean extends CTServiceAggregatorBaseBean implements DatiCivicoService {
	
	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/CatastoServiceBean")
	private CatastoService catastoService;

	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/AnagrafeServiceBean")
	private AnagrafeService anagrafeService;

	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/LocazioniServiceBean")
	private LocazioniService locazioniService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/LicenzeCommercioServiceBean")
	private LicenzeCommercioService licenzeCommercioService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/ViaServiceBean")
	private ViaService viaService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/CivicoServiceBean")
	private CivicoService civicoService;
	
	
	public DatiCivicoDTO getDatiCivico(RichiestaDatiCivicoDTO input) {
		List<SitViaUnico> listaVieUnico = new ArrayList<SitViaUnico>();
		DatiCivicoDTO info = new DatiCivicoDTO();
		
		try {
			
			if(input.getIdVia()== null){
				
				RicercaCivicoDTO rv = new RicercaCivicoDTO();
				rv.setToponimoVia(input.getToponimo());
				rv.setDescrizioneVia(input.getVia());
				rv.setEnteId(input.getEnteId());
				
				//Estrazione lista ID Via da Sit_Via_Unico e popola listaVie
				listaVieUnico = viaService.getListaViaUnicoByDescr(rv);
			}
			
			if(listaVieUnico.size()==0)
				logger.warn("Via non presente");
			
			if(listaVieUnico.size()==1){
				
				long idViaUnico = listaVieUnico.get(0).getIdVia();
				
				logger.debug("IdViaUnico:"+idViaUnico);
				
				RicercaCivicoIndiceDTO rci = new RicercaCivicoIndiceDTO();
				rci.setEnteId(input.getEnteId());
				rci.setIdVia(Long.toString(idViaUnico));
				rci.setEnteSorgente("4");
				rci.setProgEs("2");
				List<SitViaTotale> listaVieTotaleCat = viaService.getViaTotaleByUnicoFonte(rci);
				
				RicercaCivicoDTO rc = new RicercaCivicoDTO();
				rc.setEnteId(input.getEnteId());
				rc.setCivico(input.getCivico());
				
				List<String> idCatSogg = new ArrayList<String>();
				if(listaVieTotaleCat.size()== 0)
					logger.warn("Via non presente in catasto");
				else{
					
					//Ricerco Dati in Catasto utilizzando PKID_STRA + Desc. Num.Civ.
					rc.setIdVia(listaVieTotaleCat.get(0).getId().getIdDwh());
					List<ParticellaInfoDTO> listap = catastoService.getDatiCivicoCatasto(rc);
					info.setInfoParticelleCivico(listap.toArray(new ParticellaInfoDTO[listap.size()]));
					
					//Estrazione numero titolari per il calcolo di: famiglie residenti proprietarie
					List<ConsSoggTab> titolariCivico = catastoService.getTitolariSuCivicoByPkIdStraCivico(rc);
					
					for(ConsSoggTab s : titolariCivico){
						idCatSogg.add(s.getPkid().toString());
					}
				}
				
				//Ricerco il Civico nelle banche dati utilizzando la Join con Sit_Civico_Totale(FK_VIA + Desc.Num.Civ.)
				rc.setIdVia(Long.toString(idViaUnico));
				
				//Estrazione info anagrafe
				List<String> idCivici = getListaIdCiviciAnagrafe(rc);
				rc.setListaIdCivici(idCivici.toArray(new String[idCivici.size()]));
				rc.setListaId(idCatSogg.toArray(new String[idCatSogg.size()]));
				DatiCivicoAnagrafeDTO datiAnagrafe = anagrafeService.getDatiCivicoAnagrafe(rc);
				
				info.setCountUnder18(datiAnagrafe.getCountUnder18());
				info.setCount18_65(datiAnagrafe.getCount18_65());
				info.setCountOver65(datiAnagrafe.getCountOver65());
				info.setCountFamResidenti(datiAnagrafe.getCountFamResidenti());
				info.setCountFamResidentiProprietarie(datiAnagrafe.getCountFamResidentiProprietarie());
			
				//Numero di contratti di locazione al CIVICO
				int countLocazioni = locazioniService.countLocazioniCivicoAllaData(rc);
				info.setCountLocazioni(countLocazioni);
				
				//Licenze commerciali al CIVICO
				List<SitLicenzeCommercio> listaLic =  licenzeCommercioService.getLicCommercioCivicoAllaDataByIndice(rc);
				List<SitLicenzeCommercioDTO> listaLicDto = new ArrayList<SitLicenzeCommercioDTO>();
				for(SitLicenzeCommercio lic:listaLic){
					SitLicenzeCommercioDTO dto = new SitLicenzeCommercioDTO(lic);
					listaLicDto.add(dto);
				}
				info.setListaLicenzeCommercio(listaLicDto.toArray(new SitLicenzeCommercioDTO[listaLicDto.size()]));
				info.setCountLicenzeCommercio(listaLicDto.size());
			
			}else{
				List<ViaDTO> listaVieRicerca = new ArrayList<ViaDTO>();
				for(SitViaUnico via : listaVieUnico){
					ViaDTO dto = new ViaDTO();
					long idViaUnico = via.getIdVia();
					dto.setIdVia(Long.toString(idViaUnico));
					String desc = via.getSedime()+" "+via.getIndirizzo();
					dto.setDescrizione(desc.trim());
					listaVieRicerca.add(dto);
				}
				
				info.setListaVieRicerca(listaVieRicerca.toArray(new ViaDTO[listaVieRicerca.size()]));
			}
			
		}catch (Throwable t) {
				logger.error("Eccezione: "+t.getMessage(), t);
				throw new DatiCivicoException(t);
		}

		return info;
	}
	
	private List<String> getListaIdCiviciAnagrafe(RicercaCivicoDTO rc){
		List<String> ids = new ArrayList<String>();
		
		try{
			RicercaCivicoIndiceDTO rci = new RicercaCivicoIndiceDTO();
			rci.setCivico(rc.getCivico());
			rci.setIdVia(rc.getIdVia());
			rci.setEnteId(rc.getEnteId());
			rci.setEnteSorgente("1");
			rci.setProgEs("1");
			ids = civicoService.getListaIdCiviciByViaUnicoDesc(rci);
		
		}catch (Throwable t) {
			logger.error("Eccezione: "+t.getMessage(), t);
			throw new DatiCivicoException(t);
		}
		return ids;
	}
	
/*	public DatiCivicoDTO getDatiCivico(RichiestaDatiCivicoDTO input) {
			
		DatiCivicoDTO info = new DatiCivicoDTO();
		RicercaCivicoDTO rc = new RicercaCivicoDTO();
		rc.setToponimoVia(input.getToponimo());
		rc.setDescrizioneVia(input.getVia());
		rc.setCivico(input.getCivico());
		rc.setEnteId(input.getEnteId());
		
		try {
		
			//Estrazioni info catasto
			DatiCivicoCatastoDTO datiCatasto = catastoService.getDatiCivicoCatasto(rc);
			
			List<ParticellaInfoDTO> listap = datiCatasto.getInfoParticelleCivico();
			info.setInfoParticelleCivico(listap.toArray(new ParticellaInfoDTO[listap.size()]));
			
			//Estrazione numero famiglie residenti proprietarie
			List<ConsSoggTab> titolariCivico = datiCatasto.getTitolariSuCivico();
			List<String> idCatSogg = new ArrayList<String>();
			for(ConsSoggTab s : titolariCivico){
				idCatSogg.add(s.getPkid().toString());
			}
			
			//Estrazione info anagrafe
			rc.setListaId(idCatSogg.toArray(new String[idCatSogg.size()]));
			DatiCivicoAnagrafeDTO datiAnagrafe = anagrafeService.getDatiCivicoAnagrafe(rc);
			
			info.setCountUnder18(datiAnagrafe.getCountUnder18());
			info.setCount18_65(datiAnagrafe.getCount18_65());
			info.setCountOver65(datiAnagrafe.getCountOver65());
			info.setCountFamResidenti(datiAnagrafe.getCountFamResidenti());
			info.setCountFamResidentiProprietarie(datiAnagrafe.getCountFamResidentiProprietarie());
		
			
			//Numero di contratti di locazione al CIVICO
			int countLocazioni = locazioniService.countLocazioniCivicoAllaData(rc);
			info.setCountLocazioni(countLocazioni);
			
			//Licenze commerciali al CIVICO
			List<SitLicenzeCommercio> listaLic =  licenzeCommercioService.getLicCommercioCivicoAllaData(rc);
			List<SitLicenzeCommercioDTO> listaLicDto = new ArrayList<SitLicenzeCommercioDTO>();
			for(SitLicenzeCommercio lic:listaLic){
				SitLicenzeCommercioDTO dto = new SitLicenzeCommercioDTO(lic);
				listaLicDto.add(dto);
			}
			info.setListaLicenzeCommercio(listaLicDto.toArray(new SitLicenzeCommercioDTO[listaLic.size()]));
			
			int countLicenzeCommercio = licenzeCommercioService.countLicCommercioCivicoAllaData(rc);
			info.setCountLicenzeCommercio(countLicenzeCommercio);
			
			
		}catch (Throwable t) {
				logger.error("Eccezione: "+t.getMessage(), t);
				throw new DatiCivicoException(t);
			}

		return info;
	}*/


	public String getDatiCivico(String richiestaDatiCivicoDTO) {
		String response = null;
		
		String jaxbObjectPkgReference = "it.webred.ct.service.jaxb.intTerritorio.datiCivico.request";
		
		try {
			
			//parsing da xml a jaxb
			it.webred.ct.service.jaxb.intTerritorio.datiCivico.request.DatiCivico input = 
				(it.webred.ct.service.jaxb.intTerritorio.datiCivico.request.DatiCivico)xml2Jaxb(
													richiestaDatiCivicoDTO,jaxbObjectPkgReference);
			
			//da jaxb a DTO
			RichiestaDatiCivicoDTO rdDTO = this.jaxb2DTO(input);
			
			//chiamata metodo
			DatiCivicoDTO dcDTO = getDatiCivico(rdDTO);
			
			//da DTO a jaxb
			it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.DatiCivico dc = this.dto2Jaxb(dcDTO);
			
			//parsing da jaxb a xml
			response = jaxb2Xml(dc);
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
		}
		
		
		return response;
	}
	
	
	private RichiestaDatiCivicoDTO jaxb2DTO(it.webred.ct.service.jaxb.intTerritorio.datiCivico.request.DatiCivico input) {
		logger.info("Creazione DTO richiesta da oggetto jaxb");
		
		RichiestaDatiCivicoDTO rd = new RichiestaDatiCivicoDTO();
		
		rd.setEnteId(input.getInfoUtente().getEnteId());
		rd.setUserId(input.getInfoUtente().getUserId());
		
		rd.setCivico(input.getRichiesta().getCivico());
		rd.setToponimo(input.getRichiesta().getToponimo());
		rd.setVia(input.getRichiesta().getVia());
		rd.setIdVia(input.getRichiesta().getIdVia());
		
		logger.info("DTO creato ["+rd.getClass().getName()+"]");
		
		return rd;
	}
	
	
	
	private it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.DatiCivico dto2Jaxb(DatiCivicoDTO dcDTO) {
		logger.info("Creazione oggetto jaxb da DTO risposta");
		
		it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.ObjectFactory of = 
			new it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.ObjectFactory();
		
		it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.DatiCivico dc = of.createDatiCivico();
		it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.UserInfo u = of.createUserInfo();
		it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.Risposta r = of.createRisposta();
		//it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.RicercaCivico rc = of.createRicercaCivico();
		
		//info utente
		//dc.setInfoUtente(u);
		//u.setEnteId(dcDTO.getEnteId());
		//u.setUserId(dcDTO.getUserId());
		
		dc.setRisposta(r);
		r.setAvgOMImq(dcDTO.getAvgOMImq());
		r.setCount1865(dcDTO.getCount18_65());
		r.setCountFamResidenti(dcDTO.getCountFamResidenti());
		r.setCountFamResidentiProprietarie(dcDTO.getCountFamResidentiProprietarie());
		r.setCountLicenzeCommercio(dcDTO.getCountLicenzeCommercio());
		r.setCountLocazioni(dcDTO.getCountLocazioni());
		r.setCountOver65(dcDTO.getCountOver65());
		r.setCountUnder18(dcDTO.getCountUnder18());
		
		//vie
		ViaDTO[] vieDTO = dcDTO.getListaVieRicerca();
		if(vieDTO != null) {
			
			for(ViaDTO v: vieDTO) {
				it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.InfoVia iv = of.createInfoVia();
				
				iv.setIdVia(v.getIdVia());
				iv.setDescrizione(v.getDescrizione());
				
				r.getVie().add(iv);
			}
		}
		
		//RicercaCivico
		/*
		RicercaCivicoDTO rcDTO = dcDTO.getCivico();
		if(rcDTO != null) {
			r.setCivico(rc);
			rc.setCivico(rcDTO.getCivico());
			rc.setDataRif(rcDTO.getDataRif().getTime());
			rc.setDescrizioneVia(rcDTO.getDescrizioneVia());
			rc.setIdCivico(rcDTO.getIdVia());
			rc.setToponimoVia(rcDTO.getToponimoVia());
			
			String[] listaId = rcDTO.getListaId();
			if(listaId != null) {
				for(String s: listaId) {
					rc.getListaId().add(s);
				}
			}
		}
		*/
		
		//infoParticelleCivico;
		ParticellaInfoDTO[] piDTO = dcDTO.getInfoParticelleCivico();
		if(piDTO != null) {
			for(ParticellaInfoDTO p: piDTO) {
				it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.ParticellaInfo pi = of.createParticellaInfo();
				it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.ParticellaKey pk = of.createParticellaKey();
				
				pi.setParticella(pk);
				pk.setFoglio(p.getFoglio());
				pk.setIdSezc(p.getIdSezc());
				pk.setParticella(p.getParticella());
				
				IndirizzoDTO[] iDTO = p.getIndirizzi();
				if(iDTO != null) {
					for(IndirizzoDTO i: iDTO) {
						it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.Indirizzo ii = of.createIndirizzo();
						ii.setNumCivico(i.getNumCivico());
						ii.setStrada(i.getStrada());
						pi.getIndirizzi().add(ii);
					}
				}
				
				InfoPerCategoriaDTO[] ipcDTO = p.getInfoPerCategoria();
				if(ipcDTO != null) {
					for(InfoPerCategoriaDTO ipc: ipcDTO) {
						it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.InfoPerCategoria iippcc = of.createInfoPerCategoria();
						iippcc.setCategoria(ipc.getCategoria());
						iippcc.setConsistenzaTot(ipc.getConsistenzaTot());
						iippcc.setCountUiu(ipc.getCountUiu());
						pi.getInfoPerCategoria().add(iippcc);
					}
				}
				
				String[] subalterni = p.getSubalterni();
				if(subalterni != null) {
					for(String s: subalterni) {
						pi.getSubalterni().add(s);
					}
				}
			}
		}
		
		//listaLicenzeCommercio
		SitLicenzeCommercioDTO[] listaLicenzeCommercio =  dcDTO.getListaLicenzeCommercio();
		if(listaLicenzeCommercio != null) {
			for(SitLicenzeCommercioDTO slcDTO: listaLicenzeCommercio) {
				it.webred.ct.service.jaxb.intTerritorio.datiCivico.response.SitLicenzeCommercio slc = of.createSitLicenzeCommercio();
				slc.setAnnoProtocollo(slcDTO.getAnnoProtocollo());
				slc.setCarattere(slcDTO.getCarattere());
				slc.setCivico(slcDTO.getCivico());
				slc.setCivico2(slcDTO.getCivico2());
				slc.setCivico3(slcDTO.getCivico3());
				slc.setCodiceFabbricato(slcDTO.getCodiceFabbricato());
				slc.setCtrHash(slcDTO.getFoglio());
				
				if(slcDTO.getDataFineSospensione() != null)
					slc.setDataFineSospensione(slcDTO.getDataFineSospensione().getTime());
				
				if(slcDTO.getDataInizioSospensione() != null)
				slc.setDataInizioSospensione(slcDTO.getDataInizioSospensione().getTime());
				
				if(slcDTO.getDataRilascio() != null)
					slc.setDataRilascio(slcDTO.getDataRilascio().getTime());
				
				if(slcDTO.getDataValidita() != null)
					slc.setDataValidita(slcDTO.getDataValidita().getTime());
				
				if(slcDTO.getDtExpDato() != null)
					slc.setDtExpDato(slcDTO.getDtExpDato().getTime());
				
				if(slcDTO.getDtFineDato() != null)
					slc.setDtFineDato(slcDTO.getDtFineDato().getTime());
				
				if(slcDTO.getDtFineVal() != null)
					slc.setDtFineVal(slcDTO.getDtFineVal().getTime());
				
				if(slcDTO.getDtInizioDato() != null)
					slc.setDtInizioDato(slcDTO.getDtInizioDato().getTime());
				
				if(slcDTO.getDtInizioVal() != null) 
					slc.setDtInizioVal(slcDTO.getDtInizioVal().getTime());
				
				slc.setFlagDtValDato(slcDTO.getFlagDtValDato());
				slc.setFoglio(slcDTO.getFoglio());
				slc.setNote(slcDTO.getNote());
				slc.setNumero(slcDTO.getNumero());
				slc.setNumeroProtocollo(slcDTO.getNumeroProtocollo());
				slc.setParticella(slcDTO.getParticella());				
				slc.setProvenienza(slcDTO.getProvenienza());
				slc.setRagSoc(slcDTO.getRagSoc());
				slc.setRiferimAtto(slcDTO.getRiferimAtto());
				slc.setSezioneCatastale(slcDTO.getSezioneCatastale());
				slc.setStato(slcDTO.getStato());
				slc.setSubalterno(slcDTO.getSubalterno());
				slc.setSuperficieMinuto(slcDTO.getSuperficieMinuto());
				slc.setSuperficieTotale(slcDTO.getSuperficieTotale());
				slc.setTipologia(slcDTO.getTipologia());
				slc.setZona(slcDTO.getZona());
				
				
				r.getListaLicenzeCommercio().add(slc);
			}
		}
		
		logger.info("Oggeto jaxb creato ["+dc.getClass().getName()+"]");
		
		return dc;
	}
	
}
