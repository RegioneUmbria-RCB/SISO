package it.umbriadigitale.soclav.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Risposta_RDC_beneficiari;
import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Risposta_servizio_RDC_Type;
import it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_Type;
import it.umbriadigitale.soclav.model.RdCKeyValueExt;
import it.umbriadigitale.soclav.model.anpal.RdCAnpalBeneficiario;
import it.umbriadigitale.soclav.model.anpal.RdCAnpalSAP;
import it.umbriadigitale.soclav.model.anpal.RdCBeneficiarioPK;
import it.umbriadigitale.soclav.model.anpal.RdCTbCodiciSap;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiLavToSoc;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiLavToSocPK;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiSocToLav;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiSocToLavPK;
import it.umbriadigitale.soclav.repository.AnpalRepository;
import it.umbriadigitale.soclav.repository.CodificheSapRepository;
import it.umbriadigitale.soclav.repository.ConsensoLavToSocRepository;
import it.umbriadigitale.soclav.repository.ConsensoSocToLavRepository;
import it.umbriadigitale.soclav.repository.SAPRepository;
import it.umbriadigitale.soclav.service.dto.anpal.AnpalBeneficiarioDTO;
import it.umbriadigitale.soclav.service.dto.anpal.AnpalDomandaDTO;
import it.umbriadigitale.soclav.service.dto.sap.Lavoratore;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.AltreInformazioni;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.ConoscenzaInformatica;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.FormazioneProfessionale;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.LinguaStraniera;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.TitoliStudio;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi.ListaSpeciale;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.DatiPersonali;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.Indirizzo;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.Recapito;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.esperienza.EsperienzaLavoro;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.politicaattiva.PoliticaAttiva;
import it.umbriadigitale.soclav.service.interfaccia.IAmbitiSocialeService;
import it.umbriadigitale.soclav.service.interfaccia.ICentriImpiegoService;
import it.umbriadigitale.soclav.service.interfaccia.IRdCKeyValueExtService;
import it.umbriadigitale.soclav.util.Convertitore;
import it.umbriadigitale.soclav.util.RdcParameterKey;
import it.umbriadigitale.soclav.wsclient.ClientRdcWS;

@Service
public class CentriImpiegoServiceImpl extends BaseServiceImpl implements ICentriImpiegoService {
	
	@Autowired
	@Qualifier("anpalRepository")
	private AnpalRepository repo;
	
	@Autowired 
	@Qualifier("sapRepository")
	private SAPRepository  sapAnpalRepo;
	
	@Autowired
	@Qualifier("codificheSapRepository")
	private CodificheSapRepository sapRepo;
	
	@Autowired
	@Qualifier("consensoLavToSocRepository")
	private ConsensoLavToSocRepository consensoLavToSocRepo;
	
	@Autowired
	@Qualifier("consensoSocToLavRepository")
	private ConsensoSocToLavRepository consensoSocToLavRepo;
	
	@Autowired
	IAmbitiSocialeService socService; 
	
	@Override
	public RdCAnpalBeneficiario save(RdCAnpalBeneficiario t) {
		return repo.save(t);
	}

	@Override
	public RdCAnpalBeneficiario find(String protocolloINPS, String cf) {
		RdCBeneficiarioPK id = new RdCBeneficiarioPK(protocolloINPS, cf);
		return repo.findOne(id);
	}

	@Override
	public Integer count(List<String> entiAbilitati, int first, int size) {
		Page<RdCAnpalBeneficiario> page = filtraEnti(entiAbilitati, first, size);
		return page.getNumberOfElements();
	}
	
	private Page<RdCAnpalBeneficiario> filtraEnti(List<String> entiAbilitati, int first, int size) {
		Sort sort = new Sort(Sort.Direction.DESC, "cognome");
		Pageable pageable = new PageRequest(first, size, sort);
		Page<RdCAnpalBeneficiario> page;
		if(entiAbilitati.size()==1 && entiAbilitati.get(0).equals("ALL"))
			page = repo.findAllRichiedenti(pageable);
		else	
			page = repo.findRichiedentiByEnte(entiAbilitati, pageable);
		return page;
	}
	
	@Override
	public List<AnpalDomandaDTO> search(List<String> entiAbilitati, int first, int size) {
		Page<RdCAnpalBeneficiario> page = filtraEnti(entiAbilitati, first, size);
		List<AnpalDomandaDTO> lstDomande = new ArrayList<AnpalDomandaDTO>();
		for(RdCAnpalBeneficiario ben : page.getContent()) {
			List<AnpalBeneficiarioDTO> lstFamiliari = new ArrayList<AnpalBeneficiarioDTO>();
			AnpalDomandaDTO domanda = new AnpalDomandaDTO();
			domanda.setCfRichiedente(ben.getCfRichiedente());
			domanda.setProtocolloINPS(ben.getId().getProtocolloINPSCod());
			domanda.setStatoDomandaINPS(ben.getStatoCod());
			domanda.setDataDomanda(ben.getDataDomanda());
			
			AnpalBeneficiarioDTO richiedente = this.popolaAnagrafica(ben);
			domanda.setRichiedente(richiedente);
			
			/*Recupero la lista di familiari*/
			List<RdCAnpalBeneficiario> fami = repo.findFamiliari(ben.getCfRichiedente(), ben.getId().getProtocolloINPSCod());
			
			lstFamiliari.add(richiedente);
			for(RdCAnpalBeneficiario fam : fami) {
				AnpalBeneficiarioDTO famBen = this.popolaAnagrafica(fam);
				famBen.setVisualizzaDatiSociale(canViewDatiSociale(famBen.getDatipersonali().getCodicefiscale(), famBen.getResidenza().getCodcomune()));
				lstFamiliari.add(famBen);
			}
			domanda.setFamiliari(lstFamiliari);
			
			lstDomande.add(domanda);
		}
		
	      return lstDomande;
	}
	
	private AnpalBeneficiarioDTO popolaAnagrafica(RdCAnpalBeneficiario ben) {
		
		AnpalBeneficiarioDTO out = new AnpalBeneficiarioDTO();
		
		DatiPersonali datipersonali = new DatiPersonali();
		datipersonali.setCognome(ben.getCognome());
		datipersonali.setNome(ben.getNome());
		datipersonali.setCodicefiscale(ben.getId().getCf());
		datipersonali.setSesso(ben.getSesso());
		datipersonali.setDatanascita(ben.getDataNascita());
		
		datipersonali.setCodcomune(ben.getNascitaLuogoCod());
		datipersonali.setDesLuogoNascita(ben.getNascitaLuogoDes());
		
		String cittadinanza = findDecodifica("CITTADINANZA", ben.getCittadinanzaCod());
		datipersonali.setCodcittadinanza(ben.getCittadinanzaCod());
		datipersonali.setDesCittadinanza(cittadinanza);
		out.setDatipersonali(datipersonali);
		
		Recapito recapito = new Recapito();
		recapito.setEmail(ben.getEmail());
		recapito.setTelefono(ben.getTelefono());
		out.setRecapiti(recapito);
		
		
		Indirizzo residenza = new Indirizzo(ben.getResidenzaIndirizzo(), ben.getResidenzaCap(), ben.getResidenzaComuneCod(), ben.getResidenzaComuneDes());
		Indirizzo domicilio = new Indirizzo(ben.getDomicilioIndirizzo(), ben.getDomicilioCap(), ben.getDomicilioComuneCod(), ben.getDomicilioComuneDes());
		out.setResidenza(residenza);
		out.setDomicilio(domicilio);
		out.setRichiedente(ben.isRichiedente());
		out.setCodSAP(ben.getCodSap());
		out.setCpiDenominazione(!StringUtils.isBlank(ben.getResidenzaCPICod()) ? ben.getCpi().getDenominazione() : null);
		out.setDataDecorrenzaBeneficio(ben.getDataDecorrenzaBen());
		out.setUltimaModifica(ben.getDtMod()!=null ? ben.getDtMod() : ben.getDtIns());
		out.setVisualizzaDatiSociale(canViewDatiSociale(datipersonali.getCodicefiscale(), ben.getResidenzaComuneCod()));
				
	    RdcConsensiLavToSoc consenso = this.findLavToSoc(datipersonali.getCodicefiscale(), residenza.getCodcomune());
	    if(consenso!=null) out.setConsensoRilasciato(consenso.getFlagConsenso());
		return out;
	}

	public RdcConsensiLavToSoc findLavToSoc(String cf, String ente) {
		RdcConsensiLavToSocPK pk = new RdcConsensiLavToSocPK();
		pk.setCf(cf.toUpperCase());
		pk.setCodEnteTo(ente.toUpperCase());
		return consensoLavToSocRepo.findOne(pk);
	}
	
	private String findDecodifica(String tabella, String codice) {
		RdCTbCodiciSap decode = null;
		if(!StringUtils.isBlank(codice))
		  decode = this.sapRepo.findDecodifica(tabella, codice);
		return decode!=null ? decode.getDescrizione() : codice;
	}
		
	private HashMap<String, String> findMappaTabella(String tabella){
		HashMap<String,String> mappa = new HashMap<String, String>();
		List<RdCTbCodiciSap> decodes = this.sapRepo.findCodiciByTabella(tabella);
		for(RdCTbCodiciSap decode : decodes) {
			mappa.put(decode.getId().getCodice(), decode.getDescrizione());
		}
		return mappa;
	}
	
	@Override
	public Lavoratore find(String codSap) {
		RdCAnpalSAP sap = sapAnpalRepo.findOne(codSap);
		Lavoratore lavoratore = null;
		String xmlSap = sap!=null && !StringUtils.isBlank(sap.getSap()) ? sap.getSap() : null;
		if(xmlSap!=null) {
			lavoratore = new Lavoratore();
			try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Lavoratore.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(xmlSap);
	        lavoratore = (Lavoratore) unmarshaller.unmarshal(reader);
	        
	        String cittadinanza = findDecodifica("CITTADINANZA", lavoratore.getDatianagrafici().getDatipersonali().getCodcittadinanza());
	        lavoratore.getDatianagrafici().getDatipersonali().setDesCittadinanza(cittadinanza);
	        
	        String luogoNascita = findDecodifica("COMUNE O NAZIONE", lavoratore.getDatianagrafici().getDatipersonali().getCodcomune());
	        lavoratore.getDatianagrafici().getDatipersonali().setDesLuogoNascita(luogoNascita);
	        
	        if(lavoratore.getDatianagrafici().getResidenza()!=null) {
	        	String descomune = findDecodifica("COMUNE O NAZIONE", lavoratore.getDatianagrafici().getResidenza().getCodcomune());
	        	lavoratore.getDatianagrafici().getResidenza().setDescomune(descomune);
	        }
	        
	        if(lavoratore.getDatianagrafici().getDomicilio()!=null) {
	        	String descomune = findDecodifica("COMUNE O NAZIONE", lavoratore.getDatianagrafici().getDomicilio().getCodcomune());
	        	lavoratore.getDatianagrafici().getDomicilio().setDescomune(descomune);
	        }
	        
	        String statooccupazionale = findDecodifica("STATO IN ANAGRAFE", lavoratore.getDatiamministrativi().getStatoinanagrafe().getCodstatooccupazionale());
	        lavoratore.getDatiamministrativi().getStatoinanagrafe().setDesstatooccupazionale(statooccupazionale);

	        String status = findDecodifica("CONDIZIONE STATUS", lavoratore.getDatiamministrativi().getStatoinanagrafe().getCodstatus());
	        lavoratore.getDatiamministrativi().getStatoinanagrafe().setDesstatus(status);
	        
	        String descategoria297 = findDecodifica("CATEGORIE297", lavoratore.getDatiamministrativi().getStatoinanagrafe().getCategoria297());
	        lavoratore.getDatiamministrativi().getStatoinanagrafe().setDescategoria297(descategoria297);
	        
	        String disoccupazione = findDecodifica("INGRESSO DISOCCUPAZIONE", lavoratore.getDatiamministrativi().getPeriodidisoccupazione().getTipoingresso());
	        lavoratore.getDatiamministrativi().getPeriodidisoccupazione().setDestipoingresso(disoccupazione);
	        
	        
	        HashMap<String, String> mappaListeSpeciali = findMappaTabella("LISTESPECIALI");
	        HashMap<String, String> mappaProvince = findMappaTabella("PROVINCIA");
	        for(ListaSpeciale ls : lavoratore.getDatiamministrativi().getListespeciali_lst().getListespeciali()) {
	        	ls.setDestipolista(mappaListeSpeciali.get(ls.getTipolista()));
	        	ls.setDesprovincia(mappaProvince.get(ls.getCodprovincia()));
	        }
	        
	        if(lavoratore.getDatiamministrativi().getAltreinformazioni()!=null) {
	        	String catprotette = findDecodifica("CATEGORIE PROTETTE", lavoratore.getDatiamministrativi().getAltreinformazioni().getCodcatprotette());
	        	lavoratore.getDatiamministrativi().getAltreinformazioni().setDescatprotette(catprotette);
	        }
	                
	        for(EsperienzaLavoro ls : lavoratore.getEsperienzelavoro_lst().getEsperienzalavoro()) {
	        	String descp2011 = this.findDecodifica("CP2011", ls.getCodprofessione());
	        	ls.setDescodprofessione(descp2011);
	        	
	    		String desateco = !"00.00.00".equalsIgnoreCase(ls.getAzienda().getCodateco()) ? findDecodifica("ATECO2007", ls.getAzienda().getCodateco()) : "-";
	        	ls.getAzienda().setDesateco(desateco);
	        	
		        if(ls.getLuogolavoro()!=null) {
		        	String descomune = findDecodifica("COMUNE O NAZIONE", ls.getLuogolavoro().getCodcomune());
		        	ls.getLuogolavoro().setDescomune(descomune);
		        }
		        
		        String destipocontratto = this.findDecodifica("TIPO CONTRATTI", ls.getTiporapporto().getTipocontratto());
		        ls.getTiporapporto().setDestipocontratto(destipocontratto);
		        
		        //TODO: implementare - tag non presente in XML di prova
		        String descatinquadramento = this.findDecodifica("GRADO CONTRATTUALE", null);
		        ls.getTiporapporto().setDesCatInquadramento(descatinquadramento);
		        
		        //TODO: implementare - decodifica non presente (Tabella Modalità di lavoro)
		        String desmodlavoro = this.findDecodifica("MODALITA LAVORO", ls.getModalitalavoro().getCodmodalitalavoro());
		        ls.getModalitalavoro().setDesmodalitalavoro(desmodlavoro);
	        }
	        
	        for(TitoliStudio ls : lavoratore.getAllegato().getTitolistudio_lst().getTitolostudio()) {
	        	ls.setDeslivelloistruzione(this.findDecodifica("LIVELLO DI STUDIO", ls.getCodlivelloistruzione()));
	        	ls.setDescorsostudio(this.findDecodifica("TITOLO DI STUDIO", ls.getCorsostudio()));
	        }
	 
	        HashMap<String, String> mappaRegioni = findMappaTabella("REGIONI PROVINCE AUTONOME");
	        HashMap<String, String> mappaAttestazioni = findMappaTabella("ATTESTAZIONI");
	        HashMap<String, String> mappaDurata = findMappaTabella("TIPOLOGIA DURATA");
	        for(FormazioneProfessionale ls : lavoratore.getAllegato().getFormazioneprofessionale_lst().getFormazioneprofessionale()){
	        	ls.setDescertificazioniattestati(mappaAttestazioni.get(ls.getCertificazioniattestati()));
	        	ls.setDesregione(mappaRegioni.get(ls.getCodregione()));
	        	ls.setDestipologiadurata(mappaDurata.get(ls.getCodtipologiadurata()));	 
	        }
	
	        HashMap<String, String> mappaLingua = findMappaTabella("LINGUE");
	        HashMap<String, String> mappaLivLingua = findMappaTabella("LINGUE CONOSCENZA");
	        for(LinguaStraniera ls : lavoratore.getAllegato().getLinguestraniere_lst().getLinguastraniera()) {
	        	ls.setDeslingua(mappaLingua.get(ls.getCodlingua()));
	        	ls.setDeslivelloletto(mappaLivLingua.get(ls.getCodlivelloletto()));
	        	ls.setDeslivelloscritto(mappaLivLingua.get(ls.getCodlivelloscritto()));
	        	ls.setDeslivelloparlato(mappaLivLingua.get(ls.getCodlivelloparlato()));
	        }
	        
	        HashMap<String, String> mappaLivInformatica = findMappaTabella("INFORMATICAGRADOCONOSC");
	        for(ConoscenzaInformatica ls : lavoratore.getAllegato().getConoscenzeinformatiche_lst().getConoscenzainformatica()) {
	        	String desinformatica = this.findDecodifica("INFORMATICACONOSC", ls.getCodconoscenzainformatica());
	        	ls.setDesconoscenzainformatica(desinformatica);
	        	ls.setDesgrado(mappaLivInformatica.get(ls.getCodgrado()));
	        }
	        
	        HashMap<String, String> mappaAlbi = findMappaTabella("ALBI");
	        HashMap<String, String> mappaPatenti = findMappaTabella("PATENTI GUIDA");
	        HashMap<String, String> mappaAbilitazioni = findMappaTabella("ABILITAZIONI");
	        for(AltreInformazioni ls : lavoratore.getAllegato().getAltreinformazioni_lst().getAltreinformazioni()) {
	        	//TODO:ALBI
	        	//TODO:Abilitazioni
	        	ls.setDespatenteguida(mappaPatenti.get(ls.getCodpatenteguida()));
	        	ls.setDesabilitazione(mappaAbilitazioni.get(null));
	        	ls.setDesalbo(mappaAlbi.get(null));
	        }
	        
	        /*Sezione 1.5 - Dati invio*/
	        
	        String entetitolare = findDecodifica("CPI - OPERATORI ABILITATI GG", lavoratore.getDatiinvio().getCodiceentetit());
	        lavoratore.getDatiinvio().setDesentetit(entetitolare);
	        
	        String variazione = findDecodifica("TIPO VARIAZIONE", lavoratore.getDatiinvio().getTipovariazione());
	        lavoratore.getDatiinvio().setDestipovariazione(variazione);
	        
	        /*Sezione 1.6 - Interventi di Politiche Attive*/
	        HashMap<String, String> mappaAttivita = findMappaTabella("TIPO ATTIVITA");
	        HashMap<String, String> mappaProgetti = findMappaTabella("TIPO PROGETTI");
	        HashMap<String, String> mappaEventi = findMappaTabella("EVENTI POLITICA");
	        //HashMap<String, String> mappaPolitica = findMappaTabella("IDENTIFICATIVO POLITICA");
	        for(PoliticaAttiva ls : lavoratore.getPolitiche_attive_lst().getPolitiche_attive()) {
	        	ls.setDesTipo_attivita(mappaAttivita.get(ls.getTipo_attivita()));
	        	ls.setDesTitolo_progetto(mappaProgetti.get(ls.getTitolo_progetto()));
	        	ls.setDesTipologia_durata(mappaDurata.get(ls.getTipologia_durata()));
	        	ls.getUltimo_evento().setDesEvento(mappaEventi.get(ls.getUltimo_evento().getEvento()));
	        	ls.setDes_ente_promotore(findDecodifica("CPI - OPERATORI ABILITATI GG", ls.getCodice_ente_promotore()));
	        }
	        	        
			}catch(Exception e) {
				System.out.println("Errore conversione SAP:"+e.getMessage());
			}
		}
		return lavoratore;
	}

	@Override
	public boolean canViewDatiSociale(String cf, String ente) {
		boolean val = false;
		if(!StringUtils.isBlank(cf) && !StringUtils.isBlank(ente)) {
			RdcConsensiSocToLavPK pk = new RdcConsensiSocToLavPK();
			pk.setCf(cf.toUpperCase());
			pk.setCodEnteFrom(ente.toUpperCase());
			RdcConsensiSocToLav consenso = consensoSocToLavRepo.findOne(pk);
			val = (consenso!=null && consenso.getFlagConsenso()!=null && consenso.getFlagConsenso().booleanValue());
		}
		return val;
	}
	
	@Override 
	public RdcConsensiLavToSoc salvaConsenso(String cf, Boolean val, String ente) {
		RdcConsensiLavToSoc consenso = null;
		if(val!=null && !StringUtils.isBlank(cf)) {
			RdcConsensiLavToSocPK pk = new RdcConsensiLavToSocPK();
			pk.setCf(cf.toUpperCase());
			pk.setCodEnteTo(ente.toUpperCase());
			consenso = consensoLavToSocRepo.findOne(pk);
			if(consenso==null) {
				consenso = new RdcConsensiLavToSoc();
				consenso.setId(pk);
				consenso.setDtIns(new Date());
			}else {
				consenso.setDtMod(new Date());
			}
			consenso.setFlagConsenso(val);
			consenso = consensoLavToSocRepo.save(consenso);
		}
		return consenso;
	}
	
	@Override 
	public String aggiornaNucleoFamiliare(Risposta_servizio_RDC_Type  risposta, boolean estraiSap) {
		String msgResult = "";
		ClientRdcWS clientWS = new ClientRdcWS();
		
	    String url =  getGlobalParameter(RdcParameterKey.WS_SAP_ESTRAZIONE_URL);
		String username = getGlobalParameter(RdcParameterKey.WS_SAP_USR);
		String pwd = getGlobalParameter(RdcParameterKey.WS_SAP_PWD);
		boolean sapAbilitata = !StringUtils.isEmpty(url) && !StringUtils.isEmpty(username) && !StringUtils.isEmpty(pwd);
		if(!sapAbilitata)
			logger.warn("Impossibile sincronizzare dati SAP: url o credenziali non impostate.");
		
		for(it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Beneficiario_Type beneficiari : risposta.getBeneficiari().getBeneficiario()) {
			
			java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			RdCBeneficiarioPK id = new RdCBeneficiarioPK( beneficiari.getCod_protocollo_inps() ,beneficiari.getCod_fiscale());
			RdCAnpalBeneficiario  t =  this.find(id.getProtocolloINPSCod(),id.getCf());
			
			if(t == null || t.getId() == null) {
				t = new RdCAnpalBeneficiario();
				t.setDtIns( currentDate );
				t.setId(id);
			}
			else {
				t.setDtMod(currentDate);
			}
				
			t.setCfRichiedente(beneficiari.getCod_fiscale_richiedente());
			t.setCittadinanzaCod(beneficiari.getCod_cittadinanza());
			t.setCodSap(beneficiari.getCod_sap());
			t.setCodStatoDomandaInps(beneficiari.getCod_stato());
			t.setCognome(beneficiari.getDes_cognome());
			if(beneficiari.getDtt_rendicontazione() != null) //da valutare se rinominare variabile e campo database
				t.setDataDecorrenzaBen(Convertitore.StringToDate(beneficiari.getDtt_rendicontazione()));
			if(beneficiari.getDtt_domanda() != null)
				t.setDataDomanda(Convertitore.StringToDate(beneficiari.getDtt_domanda()));
			if(beneficiari.getDtt_nascita() != null)
				t.setDataNascita(Convertitore.StringToDate(beneficiari.getDtt_nascita()));
			t.setDomicilioCap(beneficiari.getCod_cap_domicilio());
			t.setDomicilioComuneCod(beneficiari.getCod_comune_domicilio());
			t.setDomicilioComuneDes(beneficiari.getDes_comune_domicilio());
			t.setDomicilioIndirizzo(beneficiari.getDes_indirizzo_domicilio());
			
			 t.setEmail(beneficiari.getDes_email());
			 t.setNascitaLuogoCod(beneficiari.getCod_comune_nascita() );
			 t.setNascitaLuogoDes(beneficiari.getDes_comune_nascita());
			 t.setNome(beneficiari.getDes_nome());
			 t.setResidenzaCap(beneficiari.getCod_cap_residenza());
			 t.setResidenzaComuneCod(beneficiari.getCod_comune_residenza());
			 t.setResidenzaComuneDes( beneficiari.getDes_comune_residenza() );
			 //t.setResidenzaCPICod(); //assente
			 t.setResidenzaIndirizzo(beneficiari.getDes_indirizzo_residenza());
			 t.setRuolo(beneficiari.getCod_ruolo_beneficiario());
			 t.setSesso(beneficiari.getCod_sesso());
			 //t.setStatoCod(beneficiari.getCod_stato()); 
			 t.setTelefono( beneficiari.getDes_telefono());
			  
			 this.save(t);
			 
			 //Recuperare SAP e salvare
	         if(!StringUtils.isEmpty(t.getCodSap()) && sapAbilitata && estraiSap) {
	        	 Risposta_richiestaSAP_Type esitoSAP =  clientWS.estraiSAP(url, username, pwd , t.getCodSap());
	        	if(esitoSAP!=null && !StringUtils.isEmpty(esitoSAP.getSAP()))
	        		salvaSAP(t.getCodSap(), esitoSAP.getSAP());
			}
		}
		return msgResult;
	}

	private void salvaSAP(String codSap, String json) {
		boolean aggiorna = false;
		if(!StringUtils.isEmpty(json)) {
			RdCAnpalSAP sap = null;
		    Date today = new Date();
			sap = sapAnpalRepo.findOne(codSap);
			if(sap==null) {
				sap = new RdCAnpalSAP();
				sap.setId(codSap);
				sap.setSap(json);
				sap.setDtIns(today);
				aggiorna = true;
			}else if(!json.equals(sap.getSap())) {
				sap.setDtMod(today);
				sap.setSap(json);
				aggiorna = true;
			}
	
			if(aggiorna)
				sapAnpalRepo.save(sap);
		}
	}

	@Override
	public void aggiornaFlussoANPAL() {
		ClientRdcWS clientWS = new ClientRdcWS();
		Risposta_RDC_beneficiari result = null;
		try {
		    String url =  getGlobalParameter(RdcParameterKey.WS_RICERCA_NUM_PROT_URL);
		    String username = getGlobalParameter(RdcParameterKey.WS_RICERCA_USR);
		    String pwd = getGlobalParameter(RdcParameterKey.WS_RICERCA_PWD);
		  
		    if(!StringUtils.isEmpty(url) && !StringUtils.isEmpty(username) && !StringUtils.isEmpty(pwd)) {
				List<String> lstProtocolliINPS = socService.loadListaProtocolli();
				for(String protocollo : lstProtocolliINPS) {
					result =  clientWS.estraiNucleoFamiliare(url, username, pwd, null, protocollo);
					this.aggiornaNucleoFamiliare(result.getRisposta_RDC_beneficiari(), true);
				}
		    }else {
		    	logger.warn("Impossibile elaborare flusso ANPAL: url o credenziali non specificate");
		    }
		}catch(Throwable e) {
			logger.error("Errore elaborazione aggiornaFlussoANPAL:"+e.getMessage(), e);
		}
	}
		
}
