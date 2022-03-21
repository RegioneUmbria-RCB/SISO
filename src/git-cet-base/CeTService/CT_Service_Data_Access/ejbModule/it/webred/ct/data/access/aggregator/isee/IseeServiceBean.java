package it.webred.ct.data.access.aggregator.isee;

import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.data.access.aggregator.isee.dao.IseeDAO;
import it.webred.ct.data.access.aggregator.isee.dto.InfoIseeDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeCalcDataIn;
import it.webred.ct.data.access.aggregator.isee.dto.IseeCespiteDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeDataIn;
import it.webred.ct.data.access.aggregator.isee.dto.IseeLocazioneDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseePersonaDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeRedditoDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeRedditoModelloDTO;
import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.anagrafe.dao.AnagrafeDAO;
import it.webred.ct.data.access.basic.anagrafe.dto.AnagraficaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.catasto.dao.CatastoDAO;
import it.webred.ct.data.access.basic.catasto.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaSoggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.TerrenoPerSoggDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.ici.IciService;
import it.webred.ct.data.access.basic.ici.dto.RicercaSoggettoIciDTO;
import it.webred.ct.data.access.basic.indice.civico.dao.CivicoDAO;
import it.webred.ct.data.access.basic.indice.civico.dto.RicercaCivicoIndiceDTO;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.locazioni.dao.LocazioniDAO;
import it.webred.ct.data.access.basic.locazioni.dto.RicercaLocazioniDTO;
import it.webred.ct.data.access.basic.redditi.dao.RedditiDAO;
import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditi.dto.KeyTrascodificaDTO;
import it.webred.ct.data.model.anagrafe.SitComune;
import it.webred.ct.data.model.anagrafe.SitDCivicoV;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.anagrafe.SitProvincia;
import it.webred.ct.data.model.catasto.ConsSoggTab;
import it.webred.ct.data.model.catasto.SiticonduzImmAll;
import it.webred.ct.data.model.catasto.Sitiuiu;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.SitTIciSogg;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.isee.IseeAnnoDich;
import it.webred.ct.data.model.locazioni.LocazioneBPK;
import it.webred.ct.data.model.locazioni.LocazioniA;
import it.webred.ct.data.model.locazioni.LocazioniB;
import it.webred.ct.data.model.locazioni.LocazioniI;
import it.webred.ct.data.model.redditi.RedDatiAnagrafici;
import it.webred.ct.data.model.redditi.RedDescrizione;
import it.webred.ct.data.model.redditi.RedRedditiDichiarati;
import it.webred.ct.data.model.redditi.RedRedditiDichiaratiPK;
import it.webred.ct.data.model.redditi.RedTrascodifica;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
//@WebService
//@SOAPBinding(style=Style.RPC)
public class IseeServiceBean extends CTServiceBaseBean implements IseeService  {
	
	@Autowired
	private IseeDAO iseeDAO;
	
	@Autowired
	private AnagrafeDAO anagrafeDAO;
	
	@Autowired
	private CatastoDAO catastoDAO;
	
	@Autowired
	private CivicoDAO civicoDAO;
	
	@Autowired
	private LocazioniDAO  locazioniDAO;
	
	@Autowired 
	private RedditiDAO redditiDAO;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/IciServiceBean")
	private IciService iciService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ParameterBaseService")
	private ParameterService parameterService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
	private static final Map<String, String> TIPO_MODELLO_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, String>() {{ 
		        put("3", "730");
		        put("5", "750");
		        put("6", "760");
		        put("8", "760BIS");
		        put("S", "770S");
		        put("U", "UNICO");
		    }});
	
	private static final Map<String, String> REDDITI_OK_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, String>() {{ 
		        put("PL075001", "Addizionale comunale all'IRPEF dovuta (dichiarante)");
		        put("PL075002", "Addizionale comunale all'IRPEF dovuta (coniuge)");
		        put("PL071002", "Reddito imponibile (coniuge)");
		        put("PL071001", "Reddito imponibile (dichiarante)");
		        put("PL002002", "Totale reddito agrario (coniuge)");
		        put("PL002001", "Totale reddito agrario (dichiarante)");
		        put("PL001002", "Totale reddito dominicale (coniuge)");
		        put("PL001001", "Totale reddito dominicale (dichiarante)");
		        put("PL003002", "Totale reddito fabbricati (coniuge)");
		        put("PL003001", "Totale reddito fabbricati (dichiarante)");
		        put("RB035008", "Totale imponibile fabbricati");
		        put("RA052010", "Totale reddito agrario imponibile");
		        put("RA052009", "Totale reddito dominicale imponibile");
		        put("RN006002", "Reddito imponibile");
		        put("TN004002", "Reddito imponibile");
		        put("RN016001", "Reddito imponibile");
		        put("RB035009", "Totale imponibile fabbricati");
		        put("RA052010", "Totale reddito agrario imponibile");
		        put("DB001011", "Addizionale comunale all'IRPEF - saldo 2009");
		        put("RV010002", "Addizionale comunale all'Irpef dovuta - importo");
		        put("RN004001", "Reddito imponibile");
		        //put("RV001001", "Reddito imponibile");
		        put("RB011008", "Totale imponibile fabbricati");
		        put("RA011010", "Totale reddito agrario imponibile");
		        put("RA011009", "Totale reddito dominicale imponibile");
		    }});
	
	private static final Map<Integer, String> OGGETTO_LOCAZIONE_MAP = 
		    Collections.unmodifiableMap(new HashMap<Integer, String>() {{ 
		        put(1, "Fondi Rustici");
		        put(2, "Immobili Urbani");
		        put(3, "Altri Immobili");
		        put(4, "Leasing immobili abitativi");
		        put(5, "Leasing immobili strumentali");
		        put(6, "Leasing immobili strumentali con esercizio dell'opzione per l'assoggettamento all'IVA");
		        put(7, "Leasing di altro tipo");
		        put(8, "Locazione di fabbricati abitativi effettuata da costruttori");
		        put(9, "Locazione di immobili strumentali");
		        put(10, "Locazione immobili strumentali con esercizio dell'opzione per l'assoggettamento all'IVA");
		    }});
	
	private static final Map<String, String> TIPOSOGGETTO_LOCAZIONE_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, String>() {{ 
		        put("A", "Inquilino");
		        put("D", "Proprietario");
		    }});

	@Override
	public List<IseeAnnoDich> getAnnoDich(CeTBaseObject cet){
		return iseeDAO.getAnnoDich();
	}
	
	//@WebMethod
	public InfoIseeDTO getInfoIseeWS(@WebParam(name = "token") String token, @WebParam(name = "codiceFiscale")  String codiceFiscale, @WebParam(name = "enteId") String enteId, @WebParam(name = "annoRedditi") Integer annoRedditi){

		if (token==null || codiceFiscale==null || enteId==null || annoRedditi ==null)
			return null;
		IseeDataIn di = new IseeDataIn();
		di.setAnno(annoRedditi);
		di.setCodiceFiscale(codiceFiscale);
		di.setEnteId(enteId);
		di.setSessionId(token);
		di.setUserId(token);
		return getInfoIsee(di);
	}
	
	@Override
	public InfoIseeDTO getInfoIsee(IseeDataIn dataIn){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		logger.info("IseeServiceBean-getInfoIsee per CF:"+dataIn.getCodiceFiscale());
		
		InfoIseeDTO dto = new InfoIseeDTO();
		List<IseePersonaDTO> listaPersone = new ArrayList<IseePersonaDTO>();
		boolean isProprieta = false;
		boolean isLocazione = false;
		SiticonduzImmAll abitazioneProprieta = null;
		RicercaSoggettoAnagrafeDTO rs = new RicercaSoggettoAnagrafeDTO();
		rs.setCodFis(dataIn.getCodiceFiscale());
		
		//recupero parametri utili
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("provenienza.dati.ici");
		criteria.setComune(dataIn.getEnteId());
		criteria.setSection("param.comune");
		
		AmKeyValueExt provenienzaExt = parameterService.getAmKeyValueExt(criteria);
		if(provenienzaExt==null)
			logger.warn("IseeServiceBean-getInfoIsee: paremtro provenienza.dati.ici NON IMPOSTATO per "+dataIn.getEnteId()+"!!!");
		
		//recupero la famiglia
		RicercaSoggettoAnagrafeDTO rsa = new RicercaSoggettoAnagrafeDTO();
		rsa.setCodFis(dataIn.getCodiceFiscale());
		List<SitDPersona> listaFamiglia = anagrafeDAO.getFamigliaByCF(rsa);
		
		//prendo gli indirizzi residenza del soggetto
		List<AnagraficaDTO> listaAna = anagrafeDAO.getAnagrafeByCF(rs);
		
		/**** INFO PER OGNI COMPONENTE ****/
		RicercaSoggettoDTO ricercaDto = new RicercaSoggettoDTO();
		ricercaDto.setEnteId(dataIn.getEnteId());
		
		RicercaSoggettoCatDTO rsc = new RicercaSoggettoCatDTO();
		rsc.setCodEnte(dataIn.getEnteId());
		rsc.setDtVal(new Date());
		List<SiticonduzImmAll> listaImmProprietaTOT = new ArrayList<SiticonduzImmAll>();

		RicercaLocazioniDTO rl = new RicercaLocazioniDTO();
		rl.setDtRif(new Date());
		List<LocazioniA> listaLocazioniTOT = new ArrayList<LocazioniA>();
				
		HashMap<String, SitTIciOggetto> listaIciOggettoTot = new HashMap<String, SitTIciOggetto>();
		RicercaSoggettoIciDTO rsi = new RicercaSoggettoIciDTO();
		
		logger.info("IseeServiceBean-getInfoIsee: elaborazione "+listaFamiglia.size()+" componenti familiari di "+dataIn.getCodiceFiscale());
		for(SitDPersona p: listaFamiglia){
			
			IseePersonaDTO personaDto = new IseePersonaDTO();
			
			//info aggiuntive persona
			ricercaDto.setCodFis(p.getCodfisc());
			List<AnagraficaDTO> anagrafeList = anagrafeDAO.getAnagrafeByCF(ricercaDto);
			personaDto.setPersona(p);
			if(anagrafeList.size() > 0){
				AnagraficaDTO anag = anagrafeList.get(0);
				SitComune comuneNascita = anag.getComuneNascita();
				SitProvincia provNascita = anag.getProvNascita();
				if(comuneNascita != null){
					personaDto.setComuneNascita(comuneNascita.getDescrizione());
					if(provNascita==null){
						/*Ripristinato 29/07/2015, lasciata la gestione al recupero in luoghiService
						String codComNascita = comuneNascita.getIdOrig().startsWith("0") ? comuneNascita.getIdOrig().replaceFirst("0", "") : comuneNascita.getIdOrig();
						*/
						String codComNascita = comuneNascita.getIdOrig();
						//Tabella SIT_PROVINCIA è vuota o non contiene il valore - recupero da AM_TAB_COMUNI utilizzando idOrig
						//(Apparentemente sembra corrispondere al Cod.Istat (eliminando uno 0 iniziale)
						AmTabComuni amComuneNascita = luoghiService.getComuneItaByIstat(codComNascita);
						if(amComuneNascita!=null)
							personaDto.setProvinciaNascita(amComuneNascita.getSiglaProv());
					}
				}
				if(anag.getProvNascita() != null)
					personaDto.setProvinciaNascita(provNascita.getSigla());

				
				if(anag.getPersona() != null && anag.getPersona().getDataEmi() == null && anag.getComuneResidenza() != null){
					AmTabComuni comRes = luoghiService.getComuneItaByIstat(anag.getComuneResidenza().getCodIstat());
					if(comRes != null){
						personaDto.setComuneResidenza(comRes.getDenominazione());
						personaDto.setProvinciaResidenza(comRes.getSiglaProv());
					}
				}else if (anag.getComuneResidenzaEmig() != null){
					personaDto.setComuneResidenza(anag.getComuneResidenzaEmig().getDescrizione());
					personaDto.setProvinciaResidenza(anag.getProvResidenzaEmig()==null?null:anag.getProvResidenzaEmig().getSigla());
					AmTabComuni comEmi = luoghiService.getComuneItaByIstat(anag.getComuneResidenzaEmig().getIdOrig());
					if(comEmi!=null){
						personaDto.setComuneResidenza(comEmi.getDenominazione());
						personaDto.setProvinciaResidenza(comEmi.getSiglaProv());
					}
					
				}
				if(anag.getComuneResidenza() != null){
					personaDto.setComuneResidenza(anag.getComuneResidenza().getDescrizione());
				}
				if(anag.getVia() != null)
					personaDto.setViaResidenza((anag.getVia().getViasedime() != null? anag.getVia().getViasedime() + " ": "") + anag.getVia().getDescrizione());
				if(anag.getCivico() != null){
					personaDto.setCapResidenza(anag.getCivico().getCap() != null? anag.getCivico().getCap().toString():null);
					personaDto.setCivicoResidenza((anag.getCivico().getCivLiv1()!= null? anag.getCivico().getCivLiv1(): "") +
					" " + (anag.getCivico().getCivLiv2() != null? anag.getCivico().getCivLiv2(): "") +
					" " + (anag.getCivico().getCivLiv3() != null? anag.getCivico().getCivLiv3(): ""));
				}
			}
			
			//proprietà del nucleo familiare
			rsc.setCodFis(p.getCodfisc());
			List<ConsSoggTab> listConsSogg = catastoDAO.getSoggettoByCF(rsc);
			for(ConsSoggTab consSogg: listConsSogg){
			
				rsc.setIdSogg(consSogg.getPkCuaa());
				listaImmProprietaTOT.addAll(catastoDAO.getImmobiliByIdSoggAllaData(rsc));
				
			}
			
			//locazioni del nucleo familiare
			rl.setCodFis(p.getCodfisc());
			List<LocazioniA> listaLocazioni = locazioniDAO.getLocazioniByCF(rl);
			List<IseeLocazioneDTO> listaIseeLocazioniDTO = new ArrayList<IseeLocazioneDTO>();
			for(LocazioniA locA: listaLocazioni) {
				IseeLocazioneDTO locDTO = new IseeLocazioneDTO();
				LocazioneBPK key = new LocazioneBPK();
				key.setUfficio(locA.getUfficio());
				key.setAnno(locA.getAnno());
				key.setSerie(locA.getSerie());
				key.setNumero(locA.getNumero());
				rl.setKey(key);
				LocazioniB locB = locazioniDAO.getLocazioneSoggByKeyCodFisc(rl);
				List<LocazioniI> listaLocazioniImm = locazioniDAO.getImmobiliByKey(rl);
				locDTO.setLocazioneA(locA);
				locDTO.setLocazioneB(locB);
				locDTO.setListaLocImmobili(listaLocazioniImm);
				locDTO.setTipoSoggetto(TIPOSOGGETTO_LOCAZIONE_MAP.get(locB.getTipoSoggetto().trim().toUpperCase()));
				if(locA.getOggettoLocazione() != null)
					locDTO.setOggettoLocazione(OGGETTO_LOCAZIONE_MAP.get(new Integer(locA.getOggettoLocazione())));
				if(locA.getImportoCanone() != null) {
					BigDecimal importoCanone = new BigDecimal(locA.getImportoCanone());
					importoCanone = new BigDecimal(importoCanone.doubleValue() / 100);
					locDTO.setImportoCanone(importoCanone);
				}
				listaIseeLocazioniDTO.add(locDTO);
			}
			personaDto.setListaLocazioni(listaIseeLocazioniDTO);
			listaLocazioniTOT.addAll(listaLocazioni);
			
			//redditi
			RicercaSoggettoDTO rsFam = new RicercaSoggettoDTO();
			rsFam.setCodFis(p.getCodfisc());
			List<RedDatiAnagrafici> listaRedAnaPersona = redditiDAO.getListaSoggettiByCF(rsFam);
			personaDto.setListaRedditi(new ArrayList<IseeRedditoDTO>());
			//lista di redditi divisi per modello
			List<IseeRedditoModelloDTO> listaRedditiModelloIseePersona = new ArrayList<IseeRedditoModelloDTO>();
			String tipoModelloLast = null;
			KeySoggDTO ksDto = new KeySoggDTO();
			ksDto.setCodFis(p.getCodfisc());
			if(dataIn.isRichiediUltimiRedditiDisp()) {
				String anno = redditiDAO.getAnnoUltimiRedditiByCF(ksDto);
				if(anno != null)
					ksDto.setAnno(anno);
			} else ksDto.setAnno(dataIn.getAnno().toString());
			for(RedDatiAnagrafici r: listaRedAnaPersona){
				ksDto.setIdeTelematico(r.getIdeTelematico());
				List<RedRedditiDichiarati> lista = redditiDAO.getRedditiByKeyAnno(ksDto);
				RedDescrizione des = redditiDAO.getDescrizioneByKey(ksDto);		
				KeyTrascodificaDTO  keyTras;
				RedTrascodifica tras;
				int i = 0;
				if (lista != null && !lista.isEmpty())  {
					List<IseeRedditoDTO> listaRedditiIssePersona = new ArrayList<IseeRedditoDTO>();					
					for (RedRedditiDichiarati ele: lista) {
						IseeRedditoDTO redditoDTO = new IseeRedditoDTO();
						keyTras=new KeyTrascodificaDTO();
						keyTras.setTipoModello(des.getId().getTipoModello());
						keyTras.setAnnoImposta(ele.getId().getAnnoImposta());
						keyTras.setCodiceRiga(ele.getId().getCodiceQuadro());
						tras = redditiDAO.getTrascodificaByKey(keyTras);
						RedRedditiDichiaratiPK id = new RedRedditiDichiaratiPK();
						id.setCodiceFiscaleDic(ksDto.getCodFis());
						id.setIdeTelematico(ksDto.getIdeTelematico());
						id.setAnnoImposta(ele.getId().getAnnoImposta());
						id.setCodiceQuadro(ele.getId().getCodiceQuadro() );
						//formatto il valore
						String valoreC = ele.getValoreContabile();
						if(valoreC != null && !valoreC.trim().equals("")){
							if(tras.getFlgImporto().equals(new BigDecimal(1)) ){
								if(!valoreC.contains(",")){
									Double d = Double.parseDouble(valoreC.trim());
									d = new Double(d.doubleValue() / tras.getCentDivisore().intValue());
									valoreC = new DecimalFormat("#,##0.00").format(d);
								}
							}else{
								try {
									valoreC = Integer.valueOf(valoreC.trim()).toString();
								} catch (NumberFormatException e) {
									valoreC = valoreC.trim();
								}
							}
						}
						ele.setValoreContabile(valoreC);
						redditoDTO.setReddito(ele);
						redditoDTO.setDescrizione(tras.getDescrizione());
						redditoDTO.setTipoModello(TIPO_MODELLO_MAP.get(tras.getId().getTipoModello()));
						listaRedditiIssePersona.add(redditoDTO);
					}
					personaDto.getListaRedditi().addAll(listaRedditiIssePersona);
					
					if(tipoModelloLast != null && tipoModelloLast.equals(listaRedditiIssePersona.get(0).getTipoModello())) {
						//aggiungo redditi al modello prec
						listaRedditiModelloIseePersona.get(i).getListaRedditi().addAll(listaRedditiIssePersona);
					} else {
						//se diverso aggiungo nuovo modello
						IseeRedditoModelloDTO iseeRedditoModelloDTO = new IseeRedditoModelloDTO();
						iseeRedditoModelloDTO.setListaRedditi(listaRedditiIssePersona);
						iseeRedditoModelloDTO.setDescrizione("" +
								"Lista redditi Modello: " + listaRedditiIssePersona.get(0).getTipoModello() +
								" Anno: " + listaRedditiIssePersona.get(0).getReddito().getId().getAnnoImposta());
						listaRedditiModelloIseePersona.add(iseeRedditoModelloDTO);
					}
					tipoModelloLast = listaRedditiIssePersona.get(0).getTipoModello();
					i++;
					
				}
			}
			personaDto.setListaRedditiByModello(listaRedditiModelloIseePersona);
						
			//ici
			rsi.setEnteId(dataIn.getEnteId());
			rsi.setCodFis(p.getCodfisc());
			List<SitTIciSogg> listaSoggetto = iciService.getListaSogg(rsi);
			List<SitTIciOggetto> listaIciOggetto = new ArrayList<SitTIciOggetto>();
			for(SitTIciSogg sogg: listaSoggetto){
				
				rsi.setIdSoggIci(sogg.getId());
				listaIciOggetto.addAll(iciService.getListaDistintaOggettiByIdSogg(rsi));
				for(SitTIciOggetto o: listaIciOggetto){
					if(listaIciOggettoTot.get(o.getId()) == null)
						listaIciOggettoTot.put(o.getId(), o);
				}
			}
			
			//cespiti
			Date dtVal;
			List<IseeCespiteDTO> listaCespiti = new ArrayList<IseeCespiteDTO>();			
			try {
				//recupero comune provincia
				AmTabComuni com = luoghiService.getComuneItaByBelfiore(dataIn.getEnteId());
				
				List<SiticonduzImmAll> listaImm = new ArrayList<SiticonduzImmAll>();
				List<TerrenoPerSoggDTO> listaTer = new ArrayList<TerrenoPerSoggDTO>();
				dtVal = sdf.parse("31/12/" + dataIn.getAnno());
				rsc.setDtVal(dtVal);
				List<ConsSoggTab> listConsSoggTab = catastoDAO.getSoggettoByCF(rsc);
				for(ConsSoggTab consSogg: listConsSoggTab){
				
					rsc.setIdSogg(consSogg.getPkCuaa());
					
					List<SiticonduzImmAll> l1 = catastoDAO.getImmobiliByIdSoggAllaData(rsc);
					if(l1 != null)
						listaImm.addAll(l1);
					List<TerrenoPerSoggDTO> l2 = catastoDAO.getTerreniByIdSoggAllaData(rsc);
					if(l2 != null)
						listaTer.addAll(l2);
				}
				
				RicercaOggettoDTO ro = new RicercaOggettoDTO();
				RicercaOggettoCatDTO cro = new RicercaOggettoCatDTO();
				ro.setEnteId(dataIn.getEnteId());
				if(provenienzaExt != null)
					ro.setProvenienza(provenienzaExt.getValueConf());
				
				cro.setEnteId(dataIn.getEnteId());
				cro.setDtVal(dtVal);
				for(SiticonduzImmAll oggetto: listaImm){
					
					ro.setFoglio(String.valueOf(oggetto.getId().getFoglio()));
					ro.setParticella(String.valueOf(oggetto.getId().getParticella()));
					ro.setSub(String.valueOf(oggetto.getId().getUnimm()));
					
					cro.setFoglio(String.valueOf(oggetto.getId().getFoglio()));
					cro.setParticella(String.valueOf(oggetto.getId().getParticella()));
					cro.setUnimm(String.valueOf(oggetto.getId().getUnimm()));
					List<SitTIciOggetto> listaIci = iciService.getOggettiByUI(ro);
					IseeCespiteDTO cespite = new IseeCespiteDTO();
					cespite.setFoglio(String.valueOf(oggetto.getId().getFoglio()));
					cespite.setParticella(String.valueOf(oggetto.getId().getParticella()));
					cespite.setSub(String.valueOf(oggetto.getId().getUnimm()));
					cespite.setQuotaPosseduta(oggetto.getPercPoss());
					cespite.setTipoPatrimonio("FABBRICATO");
					if(listaIci != null && listaIci.size() != 0){
						cespite.setIdOggettoIci(listaIci.get(0).getId());
						cespite.setQuotaPosseduta(listaIci.get(0).getPrcPoss());
						//cespite.setValoreICI(listaIci.get(0).getValImm());
						cespite.setIndirizzo(listaIci.get(0).getDesInd());
						cespite.setAnnoDenuncia(listaIci.get(0).getYeaDen());
						cespite.setNumeroDenuncia(listaIci.get(0).getNumDen());
						cespite.setFlagAbiPri3112(listaIci.get(0).getFlgAbiPri3112());
						cespite.setCivico(listaIci.get(0).getNumCiv());
					}
					Sitiuiu sitiUiu = catastoDAO.getDatiUiAllaData(cro);
					if(sitiUiu != null)
						cespite.setRenditaCatastale(sitiUiu.getRendita());
					cespite.setComune(com.getDenominazione());
					cespite.setProvincia(com.getSiglaProv());
					listaCespiti.add(cespite);
				}
				
				for(TerrenoPerSoggDTO oggetto: listaTer){
					
					ro.setFoglio(String.valueOf(oggetto.getFoglio()));
					ro.setParticella(oggetto.getParticella());
					ro.setSub(oggetto.getSubalterno());
					cro.setFoglio(String.valueOf(oggetto.getFoglio()));
					cro.setParticella(oggetto.getParticella());
					cro.setUnimm(oggetto.getSubalterno());
					List<SitTIciOggetto> listaIci = iciService.getOggettiByUI(ro);
					IseeCespiteDTO cespite = new IseeCespiteDTO();
					cespite.setFoglio(String.valueOf(String.valueOf(oggetto.getFoglio())));
					cespite.setParticella(oggetto.getParticella());
					cespite.setSub(oggetto.getSubalterno());
					cespite.setTipoPatrimonio("TERRENO");
					if(listaIci != null && listaIci.size() != 0){
						cespite.setIdOggettoIci(listaIci.get(0).getId());
						cespite.setQuotaPosseduta(listaIci.get(0).getPrcPoss());
						//cespite.setValoreICI(listaIci.get(0).getValImm());
						cespite.setIndirizzo(listaIci.get(0).getDesInd());
						cespite.setCivico(listaIci.get(0).getNumCiv());
					}
					Sitiuiu sitiUiu = catastoDAO.getDatiUiAllaData(cro);
					if(sitiUiu != null)
						cespite.setRenditaCatastale(sitiUiu.getRendita());
					cespite.setComune(com.getDenominazione());
					cespite.setProvincia(com.getSiglaProv());
					listaCespiti.add(cespite);
				}
			} catch (ParseException e) {
				throw new IseeServiceException(e);
			}
			
			personaDto.setListaCespiti(listaCespiti);
			
			listaPersone.add(personaDto);
		}
		dto.setListaFamiglia(listaPersone);
		/*************** FINE INFO PER OGNI COMPONENTE *******************/
		
		logger.info("IseeServiceBean-getInfoIsee: elaborazione "+listaAna.size()+" indirizzi di "+dataIn.getCodiceFiscale());
		if(listaAna != null && listaAna.size() > 0){
		
			/****INIZIO VERIFICA ABITAZIONE*****/
			HashMap<String,SiticonduzImmAll> indirizzo_immobile = new HashMap<String,SiticonduzImmAll>();
			if(listaImmProprietaTOT != null && listaImmProprietaTOT.size() > 0){
			
				//prendo gli indirizzi degli immobili familiari
				List<IndirizzoDTO> listaIndirizziTOT = new ArrayList<IndirizzoDTO>();
				RicercaOggettoCatDTO ro = new RicercaOggettoCatDTO();
				ro.setCodEnte(dataIn.getEnteId());
				for(SiticonduzImmAll immobile: listaImmProprietaTOT){
					
					ro.setFoglio(String.valueOf(immobile.getId().getFoglio()));
					ro.setParticella(immobile.getId().getParticella());
					ro.setUnimm(String.valueOf(immobile.getId().getUnimm()));
					List<IndirizzoDTO> listaTemp = catastoDAO.getLocalizzazioneCatastale(ro);
					for(IndirizzoDTO indirizzo: listaTemp){
						indirizzo_immobile.put(indirizzo.getIdImmobile() + "|" + indirizzo.getProgressivo() + "|" + indirizzo.getSeq() 
								+ "|" + dataIn.getEnteId() + "|" + (indirizzo.getSezione()!=null?indirizzo.getSezione():""), immobile);
					}
					listaIndirizziTOT.addAll(listaTemp);
					
				}
				
				//associo i dati della demografia con quelli del catasto tramite correlazione
				List<SitCivicoTotale> listaCiviciTotale = new ArrayList<SitCivicoTotale>();
				HashMap<String,SiticonduzImmAll> civico_immobile = new HashMap<String,SiticonduzImmAll>();
				RicercaCivicoIndiceDTO rci = new RicercaCivicoIndiceDTO();
				rci.setEnteSorgente("4");
				rci.setProgEs("4");
				rci.setDestEnteSorgente("1");
				rci.setDestProgEs("1");
				for(IndirizzoDTO indirizzo: listaIndirizziTOT){
					
					String idDwh = indirizzo.getIdImmobile() + "|" + indirizzo.getProgressivo() + "|" + indirizzo.getSeq() 
							+ "|" + dataIn.getEnteId() + "|" + (indirizzo.getSezione()!=null?indirizzo.getSezione():""); 
					rci.setIdCivico(idDwh);
					try {
						List<SitCivicoTotale> listaTemp = civicoDAO.getListaCivTotaleByCivicoFonte(rci);
						for(SitCivicoTotale ct: listaTemp){
							civico_immobile.put(ct.getId().getIdDwh(), indirizzo_immobile.get(idDwh));
						}
						listaCiviciTotale.addAll(listaTemp);
					} catch (IndiceDAOException e) {
						throw new IseeServiceException(e);
					}
					
				}
				
				//confronto gli ID
				for(AnagraficaDTO anagrDTO: listaAna){
					SitDCivicoV civicoDemog = anagrDTO.getCivico();
					String civicoDemogId = civicoDemog!=null ? civicoDemog.getId() : null;
					if(civicoDemogId!=null){
						for(SitCivicoTotale civicoTot: listaCiviciTotale){
							if(civicoDemogId.equals(civicoTot.getId().getIdDwh())){
								isProprieta = true;
								abitazioneProprieta = civico_immobile.get(civicoTot.getId().getIdDwh());
								break;
							}
								
						}
					}
				}
			}
			
			if(listaLocazioniTOT != null && listaLocazioniTOT.size() > 0){
			
				//associo i dati della demografia con quelli delle locazioni tramite correlazione
				List<SitCivicoTotale> listaCiviciTotale = new ArrayList<SitCivicoTotale>();
				RicercaCivicoIndiceDTO rci = new RicercaCivicoIndiceDTO();
				rci.setEnteSorgente("5");
				rci.setProgEs("1");
				rci.setDestEnteSorgente("1");
				rci.setDestProgEs("1");
				HashMap<String,String> civi_canone = new HashMap<String,String>();
				for(LocazioniA locazione: listaLocazioniTOT){
					
					String idDwh = locazione.getUfficio() + "|" + locazione.getAnno() + "|" + locazione.getSerie() + "|"
							+ locazione.getNumero() + "|" + (locazione.getSottonumero()!=null?locazione.getSottonumero():"") + "|"
							+ (locazione.getProgNegozio()!=null?locazione.getProgNegozio():""); 
					rci.setIdCivico(idDwh);
					try {
						List<SitCivicoTotale> lista = civicoDAO.getListaCivTotaleByCivicoFonte(rci);
						for(SitCivicoTotale c: lista)
							civi_canone.put(c.getId().getIdDwh(), locazione.getImportoCanone());
						listaCiviciTotale.addAll(lista);
					} catch (IndiceDAOException e) {
						throw new IseeServiceException(e);
					}
					
				}
				
				//confronto gli ID
				for(AnagraficaDTO anagrDTO: listaAna){
					SitDCivicoV civicoDemog = anagrDTO.getCivico();
					String civicoDemogId = civicoDemog!=null ? civicoDemog.getId() : null;
					if(civicoDemogId!=null){
						for(SitCivicoTotale civicoTot: listaCiviciTotale){
							if(civicoDemogId.equals(civicoTot.getId().getIdDwh())){
								isLocazione = true;
								String importoCanone = civi_canone.get(civicoTot.getId().getIdDwh());
								if(importoCanone != null)
									dto.setCanoneAnnualeLocazione(new BigDecimal(importoCanone));
								break;
							}
								
						}
					}
				}
			}
			/****FINE VERIFICA ABITAZIONE*****/
		}
		dto.setAbitazioneLocazione(isLocazione);
		dto.setAbitazioneProprieta(isProprieta);
		
		/****INIZIO VALORE ICI PROPRIETA*****/
		SitTIciOggetto oggettoProprieta = null;
		if(abitazioneProprieta != null){
			
			//confronto oggetti ici con l'abitazione di proprietà
			String foglio = String.valueOf(abitazioneProprieta.getId().getFoglio()).replaceFirst("^0+(?!$)", "");
			String particella = abitazioneProprieta.getId().getParticella().replaceFirst("^0+(?!$)", "");
			String sub = String.valueOf(abitazioneProprieta.getId().getSub()==null||abitazioneProprieta.getId().getSub().trim().equals("")?
					abitazioneProprieta.getId().getUnimm():abitazioneProprieta.getId().getSub()).replaceFirst("^0+(?!$)", "");
			double sommaIci = 0D;
			Iterator it = listaIciOggettoTot.keySet().iterator();
			while(it.hasNext()) {
				String key = (String) it.next();
				SitTIciOggetto oggetto = listaIciOggettoTot.get(key);
				
				String foglio2 = oggetto.getFoglio().replaceFirst("^0+(?!$)", "");
				String particella2 = oggetto.getNumero().replaceFirst("^0+(?!$)", ""); 
				String sub2 = oggetto.getSub().replaceFirst("^0+(?!$)", "");
				if(provenienzaExt != null && oggetto.getProvenienza().equals(provenienzaExt.getValueConf()) && 
						foglio.equals(foglio2) && particella.equals(particella2)
						&& sub.equals(sub2)){
					
					dto.setIciAbitazioneProprieta(oggetto.getValImm());
					oggettoProprieta = oggetto;
					
				}
				
				sommaIci += toDoubleValue(oggetto.getValImm());
			}
			if(dto.getIciAbitazioneProprieta() != null)
				sommaIci -= toDoubleValue(dto.getIciAbitazioneProprieta());
			dto.setIciAltriImmobili(new BigDecimal(sommaIci));
			
			//ora setto il flag abiatazione nella lista cespiti dei componenti
			for(IseePersonaDTO personaDTO: dto.getListaFamiglia()){
				for(IseeCespiteDTO cespiteDTO: personaDTO.getListaCespiti()){
					if(oggettoProprieta.getId().equals(cespiteDTO.getIdOggettoIci())){
						cespiteDTO.setAbitazione(true);
						break;
					}
				}
			}
		}
		/****FINE VALORE ICI PROPRIETA*****/
		
		return dto;
	}
	
	@Override
	public InfoIseeDTO aggiornaSomme(InfoIseeDTO infoIsee){
		double sommaRedditi = 0D;
		double sommaMobiliare = 0D;
		double sommaAltriIci = 0D;
		double mutuoResiduo = 0D;
		BigDecimal iciAbitazioneProprieta = new BigDecimal(0);
		
		for(IseePersonaDTO personaDTO: infoIsee.getListaFamiglia()){
			sommaRedditi += toDoubleValue(personaDTO.getRedditiIrpef()) + toDoubleValue(personaDTO.getRedditiIrap());
			sommaMobiliare += toDoubleValue(personaDTO.getPatrimonioMobiliare());
			
			for(IseeCespiteDTO cespiteDTO: personaDTO.getListaCespiti()){
				if(cespiteDTO.isAbitazione()){
					iciAbitazioneProprieta = cespiteDTO.getValoreICI();
					mutuoResiduo += toDoubleValue(cespiteDTO.getQuotaMutuoResidua());
				}else sommaAltriIci += toDoubleValue(cespiteDTO.getValoreICI());
			}
		}
		 //aggiorno i valori che saranno utilizzati solo da lato web
		infoIsee.setSommaRedditiFamiglia(new BigDecimal(sommaRedditi));
		infoIsee.setSommaPatrimonioMobiliare(new BigDecimal(sommaMobiliare));
		infoIsee.setIciAbitazioneProprieta(iciAbitazioneProprieta);
		infoIsee.setMutuoResiduoAbitazione(new BigDecimal(mutuoResiduo));
		infoIsee.setIciAltriImmobili(new BigDecimal(sommaAltriIci));
		
		return infoIsee;
	}
	
	@Override
	public IseeDTO getCalcoloIsee(InfoIseeDTO infoIsee){
		IseeCalcDataIn calc = new IseeCalcDataIn();
		double sommaRedditi = 0D;
		double sommaMobiliare = 0D;
		double immobiliNonAb = 0D;
		double immobiliAb = 0D;
		double residuoMutuoAb = 0D;
		
		for(IseePersonaDTO personaDTO: infoIsee.getListaFamiglia()){
			sommaRedditi += toDoubleValue(personaDTO.getRedditiIrpef()) + toDoubleValue(personaDTO.getRedditiIrap());
			sommaMobiliare += toDoubleValue(personaDTO.getPatrimonioMobiliare());
			
			for(IseeCespiteDTO cespiteDTO: personaDTO.getListaCespiti()){
				if(cespiteDTO.isAbitazione()){
					immobiliAb += toDoubleValue(cespiteDTO.getValoreICI());
					residuoMutuoAb += toDoubleValue(cespiteDTO.getQuotaMutuoResidua());
				}else immobiliNonAb += toDoubleValue(cespiteDTO.getValoreICI());
			}
		}
		 //setto i valori per il calcolo
		calc.setSommaRedditi(new BigDecimal(sommaRedditi));
		calc.setSommaPatrimonioMobiliare(new BigDecimal(sommaMobiliare));
		calc.setRendimentoMedioTitoli(infoIsee.getRendimentoMedioTitoli());
		calc.setCanoneLocazione(infoIsee.getCanoneAnnualeLocazione());
		calc.setImmobiliAb(new BigDecimal(immobiliAb));
		calc.setImmobiliNonAb(new BigDecimal(immobiliNonAb));
		calc.setResiduoMutuoAb(new BigDecimal(residuoMutuoAb));
		calc.setNumComponenti(infoIsee.getListaFamiglia().size());
		calc.setNumHnd(infoIsee.getNumHnd());
		calc.setFlagUnicoGenitore(infoIsee.isFlagUnicoGenitore());
		calc.setFlagLavoroAmboGenitori(infoIsee.isFlagLavoroAmboGenitori());
		
		return doCalcolo(calc);
	}
	
	private double toDoubleValue(BigDecimal entry){
		double ret = 0D;
		if(entry != null)
			ret = entry.doubleValue();
		
		return ret;
	}
	
	private int toIntValue(Integer entry){
		int ret = 0;
		if(entry != null)
			ret = entry.intValue();
		
		return ret;
	}
	
	private IseeDTO doCalcolo(IseeCalcDataIn dataIn){
		IseeDTO dto = new IseeDTO();
		
		double detrazioneCanone;
		double rendimentoPatrimonioMobiliare;
		double isr;
		double detrazioneImmobili;
		double immobiliTotale;
		double isp;
		double detrazioneBeniMobili;
		double ISEE = 0;
		double scalaEquivalenza = 0.0;
		double ISE = 0;
		
		// determina detrazione su canone locazione
		detrazioneCanone = Math.min(toDoubleValue(dataIn.getCanoneLocazione()), toDoubleValue(dataIn.getSogliaMaxCanone())); 
		
		// calcola rendimento patrimonio mobiliare
		rendimentoPatrimonioMobiliare = 
				Math.round(toDoubleValue(dataIn.getSommaPatrimonioMobiliare()) * toDoubleValue(dataIn.getRendimentoMedioTitoli())) / 100.0d;

		// verifica se la detrazione canone supera il reddito sommato al rendimento patrimonio mobiliare
		detrazioneCanone = Math.min(detrazioneCanone, toDoubleValue(dataIn.getSommaRedditi()) + rendimentoPatrimonioMobiliare);
		
		// calcolo ISR
		isr = toDoubleValue(dataIn.getSommaRedditi()) + rendimentoPatrimonioMobiliare - detrazioneCanone;
		
		// determina la detrazione sugli immobili
		if ( toDoubleValue(dataIn.getResiduoMutuoAb()) > toDoubleValue(dataIn.getSogliaMinAbitazione()))
			detrazioneImmobili = Math.min(toDoubleValue(dataIn.getResiduoMutuoAb()),toDoubleValue(dataIn.getImmobiliAb()));
		else
			detrazioneImmobili = Math.min(toDoubleValue(dataIn.getSogliaMinAbitazione()),toDoubleValue(dataIn.getImmobiliAb()));
			
		immobiliTotale = toDoubleValue(dataIn.getImmobiliNonAb()) + toDoubleValue(dataIn.getImmobiliAb());

		// calcola ISP e determina detrazione sui beni mobiliari
		if ( toDoubleValue(dataIn.getSommaPatrimonioMobiliare()) > toDoubleValue(dataIn.getSogliaMaxDetrazione())) {
			isp = 
				immobiliTotale - detrazioneImmobili + toDoubleValue(dataIn.getSommaPatrimonioMobiliare()) - toDoubleValue(dataIn.getSogliaMaxDetrazione());
			detrazioneBeniMobili = toDoubleValue(dataIn.getSogliaMaxDetrazione());
		}
		else {
			isp = immobiliTotale - detrazioneImmobili;
			detrazioneBeniMobili = toDoubleValue(dataIn.getSommaPatrimonioMobiliare());
		}

		// calcola ISE
		ISE = isr + Math.round(isp * toDoubleValue(dataIn.getMoltiplicatoreISP()) * 100.0d) / 100.0d;
		
		// determina il valore della scala di equivalenza
		switch (toIntValue(dataIn.getNumComponenti())) {
			case 1:	scalaEquivalenza = 1;break;
			case 2: 	scalaEquivalenza = 1.57;break;
			case 3: 	scalaEquivalenza = 2.04;break;
			case 4: 	scalaEquivalenza = 2.46;break;
			case 5: 	scalaEquivalenza = 2.85;break;
			//default: ScalaEquivalenza = 2.85 + 0.35 * (NumComponenti - 5);break;
		}
		
		if (toIntValue(dataIn.getNumComponenti()) > 5) scalaEquivalenza = Math.round((2.85 + 0.35 * (toIntValue(dataIn.getNumComponenti()) - 5))*100.0d)/100.0d;
		
		if (toIntValue(dataIn.getNumComponenti()) > 0 ) {
			if (dataIn.isFlagUnicoGenitore())
				scalaEquivalenza = Math.round((scalaEquivalenza + 0.2)*100.0d)/100.0d;
			
			scalaEquivalenza = Math.round((scalaEquivalenza + 0.5 * (dataIn.getNumHnd()!=null?dataIn.getNumHnd().intValue():0))*100.0d)/100.0d;	
		
			if (dataIn.isFlagLavoroAmboGenitori())
				scalaEquivalenza = Math.round((scalaEquivalenza + 0.2)*100.0d)/100.0d;
		
			// calcola ISEE
			ISEE = Math.round((ISE / scalaEquivalenza) * 100.0d) / 100.0d;
		
			// assegna i valori ai campi della form
			dto.setIsee(new BigDecimal(ISEE).setScale(2, RoundingMode.HALF_EVEN));
			dto.setScalaEquivalenza(new BigDecimal(scalaEquivalenza).setScale(2, RoundingMode.HALF_EVEN));
			dto.setIse(new BigDecimal(ISE).setScale(2, RoundingMode.HALF_EVEN));
		}
		
		return dto;
	}
}
