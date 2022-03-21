package it.webred.ct.data.access.basic.indice.ricerca;
 
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.indice.ricerca.dao.IndiceCorrelazioneDAO;
import it.webred.ct.data.access.basic.indice.ricerca.dao.IndiceCorrelazioneIndirettaDAO;
import it.webred.ct.data.access.basic.indice.ricerca.util.IndiceKeyUtil;
import it.webred.ct.data.model.annotation.IndiceKey;
import it.webred.ct.data.model.catasto.Siticivi;
import it.webred.ct.data.model.concedilizie.SitCConcIndirizzi;
import it.webred.ct.data.model.concedilizie.SitCConcessioni;
import it.webred.ct.data.model.concedilizie.SitCConcessioniCatasto;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.VTIciCiviciAll;
import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;
import it.webred.ct.data.model.tarsu.VTTarCiviciAll;


import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;


@Stateless
public class IndiceCorrelazioneServiceBean extends CTServiceBaseBean implements IndiceCorrelazioneService {

	@Autowired
	private IndiceCorrelazioneDAO indiceDAO;
	@Autowired
	private IndiceCorrelazioneIndirettaDAO indiceCorIndDAO;
		
	
	@Override
	public List<Object>  getSoggettiCorrelati(RicercaIndiceDTO soggetto) {
		logger.debug("Ricerca correlazione soggetti");
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		
		Map<String, String> classInfo = keyUtil.getEntityInfo(soggetto.getObj());
		
		logger.debug("Sorgente ["+classInfo.get(IndiceKeyUtil.SORGENTE)+"] - Sep ["+classInfo.get(IndiceKeyUtil.SEP)+"]");
		logger.debug("progEs ["+soggetto.getProgressivoEs() + "] destFkEnteSorgente [" + soggetto.getDestFonte() + "] destProgEs [" + soggetto.getDestProgressivoEs()); 
				
		String key = keyUtil.createKey(soggetto.getObj(), classInfo.get(IndiceKeyUtil.SEP));
		
		logger.debug("ID_DWH Key ["+key+"]");
		List<IndicePK> indexList = indiceDAO.getSoggettoTotalePK(key, classInfo.get(IndiceKeyUtil.SORGENTE), soggetto.getProgressivoEs(), soggetto.getDestFonte(), soggetto.getDestProgressivoEs()); 
	
		return createResult(indexList, "s");
		
	}

	@Override
	public List<Object>  getVieCorrelate(RicercaIndiceDTO via) {
		logger.debug("Ricerca correlazione vie");
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		
		Map<String, String> classInfo = keyUtil.getEntityInfo(via.getObj());
		
		logger.debug("Sorgente ["+classInfo.get(IndiceKeyUtil.SORGENTE)+"] - Sep ["+classInfo.get(IndiceKeyUtil.SEP)+"]");

		String key = keyUtil.createKey(via.getObj(), classInfo.get(IndiceKeyUtil.SEP));
		
		logger.debug("ID_DWH Key ["+key+"]");
		List<IndicePK> indexList = indiceDAO.getViaTotalePK(key, classInfo.get(IndiceKeyUtil.SORGENTE), via.getProgressivoEs(), via.getDestFonte(), via.getDestProgressivoEs()); 
	
		return createResult(indexList, "v");
	}
	
	@Override
	public List<Object>  getFabbricatiCorrelati(RicercaIndiceDTO fabbr) {
		logger.debug("Ricerca correlazione fabbricati");
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		
		Map<String, String> classInfo = keyUtil.getEntityInfo(fabbr.getObj());
		
		logger.debug("Sorgente ["+classInfo.get(IndiceKeyUtil.SORGENTE)+"] - Sep ["+classInfo.get(IndiceKeyUtil.SEP)+"]");

		String key = keyUtil.createKey(fabbr.getObj(), classInfo.get(IndiceKeyUtil.SEP));
		
		logger.debug("ID_DWH Key ["+key+"]");
		List<IndicePK> indexList = indiceDAO.getFabbricatoTotalePK(key, classInfo.get(IndiceKeyUtil.SORGENTE), fabbr.getProgressivoEs(), fabbr.getDestFonte(), fabbr.getDestProgressivoEs()); 
	
		return createResult(indexList, "f");
	}
	

	@Override
	public List<Object> getOggettiCorrelati(RicercaIndiceDTO oggetto) {
		logger.debug("Ricerca correlazione oggetti");
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		
		Map<String, String> classInfo = keyUtil.getEntityInfo(oggetto.getObj());
		
		logger.debug("Sorgente ["+classInfo.get(IndiceKeyUtil.SORGENTE)+"] - Sep ["+classInfo.get(IndiceKeyUtil.SEP)+"]");

		String key = keyUtil.createKey(oggetto.getObj(), classInfo.get(IndiceKeyUtil.SEP));
		
		logger.debug("ID_DWH Key ["+key+"]");
		List<IndicePK> indexList = indiceDAO.getOggettoTotalePK(key, classInfo.get(IndiceKeyUtil.SORGENTE), oggetto.getProgressivoEs(), oggetto.getDestFonte(), oggetto.getDestProgressivoEs()); 
	
		return createResult(indexList, "o");
	}

	private List<Object> createResult(List<IndicePK> indexList, String prefix) {
		List<Object> result = new ArrayList<Object>();
		
		Properties mappingProps = new Properties();
		
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();			
			URL url = loader.getResource("it/webred/ct/data/access/basic/indice/ricerca/indice_mapping.properties");
			logger.debug("URL ["+url+"]");
			mappingProps.load(url.openStream());
		} catch (Exception e) {
			logger.error("Unable read properties file", e);			
			return result;
		}
		
		for (IndicePK indice : indexList) {
			String propKey = prefix + "_" + indice.getFkEnteSorgente() + "_" + indice.getProgEs();
			
			String className = mappingProps.getProperty(propKey).trim();
			
			logger.debug("Mapping: Key ["+propKey+"] - Class name ["+className+"]");
			
			IndiceKeyUtil keyUtil = new IndiceKeyUtil();
			
			Object o = keyUtil.createObject(className, indice.getIdDwh(), String.valueOf(indice.getFkEnteSorgente()), String.valueOf(indice.getProgEs()));

			keyUtil.print(o);
			
			result.add(o);
		}
		
		return result;
	}

	private Object createResult(IndicePK indice, String prefix) {
		Object result = new Object();
		
		Properties mappingProps = new Properties();
		
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();			
			URL url = loader.getResource("it/webred/ct/data/access/basic/indice/ricerca/indice_mapping.properties");
			logger.debug("URL ["+url+"]");
			mappingProps.load(url.openStream());
		} catch (Exception e) {
			logger.error("Unable read properties file", e);			
			return result;
		}
			
		String propKey = prefix + "_" + indice.getFkEnteSorgente() + "_" + indice.getProgEs();
		
		String className = mappingProps.getProperty(propKey).trim();
		
		logger.debug("Mapping: Key ["+propKey+"] - Class name ["+className+"]");
		
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		
		Object o = keyUtil.createObject(className, indice.getIdDwh(), String.valueOf(indice.getFkEnteSorgente()), String.valueOf(indice.getProgEs()));

		keyUtil.print(o);
		
		result = o;
		
		
		return result;
	}
	
	@Override
	public List<Object>  getCiviciCorrelati(RicercaIndiceDTO civ) {
		logger.debug("Ricerca correlazione civici");
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		
		Map<String, String> classInfo = keyUtil.getEntityInfo(civ.getObj());
		
		logger.debug("Sorgente ["+classInfo.get(IndiceKeyUtil.SORGENTE)+"] - Sep ["+classInfo.get(IndiceKeyUtil.SEP)+"]");

		String key = keyUtil.createKey(civ.getObj(), classInfo.get(IndiceKeyUtil.SEP));
		
		logger.debug("ID_DWH Key ["+key+"]");
		List<IndicePK> indexList = indiceDAO.getCivicoTotalePK(key, classInfo.get(IndiceKeyUtil.SORGENTE), civ.getProgressivoEs(), civ.getDestFonte(), civ.getDestProgressivoEs()); 
	
		return createResult(indexList, "c");
		
		
	}
	@Override
	public List<Object> getOggettiCorrelatiById(RicercaIndiceDTO oggetto) {
		if (!(oggetto.getObj() instanceof IndicePK)) {
			logger.debug("Il campo obj di RicercaIndiceDTO deve essere del tipo IndicePK");
			return new ArrayList<Object>();
		}else {
			IndicePK indicePk = (IndicePK)oggetto.getObj();
			IndicePK obj = indiceDAO.getOggettoTotalePKByKey(indicePk.getIdDwh(),indicePk.getFkEnteSorgente()+"", indicePk.getProgEs()+"");
			RicercaIndiceDTO ri = new RicercaIndiceDTO();
			ri.setEnteId(oggetto.getEnteId());
			ri.setProgressivoEs(indicePk.getProgEs()+"");
			ri.setDestFonte(oggetto.getDestFonte());
			ri.setDestProgressivoEs(oggetto.getDestProgressivoEs());
			ri.setObj(createResult(obj, "o"));
			return getOggettiCorrelati(ri);
		}
	}
	

	//METODO PER AVERE LE CORRELAZIONI FRA CIVICI A PARTIRE DAL FABBRICATO (CIOE' DAI CIVICI DEL FABBRICATO )
	@Override
	public List<Object> getCiviciCorrelatiFromFabbricato(RicercaIndiceDTO rf) {
		if (rf.getDestFonte()==null || rf.getDestFonte().equals("") || 
			rf.getDestProgressivoEs()==null || rf.getDestProgressivoEs().equals("")	)
			throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONE INDIRETTA FABBRICATO-CIVICI. KEY FABB: " + ((KeyFabbricatoDTO)rf.getObj()).stringValue() + "MANCANO I PARAMETRI DestFonte-DestProgressivoEs ");
		/* Si deve specificare ls coppia destFonte/destProgressivo per cui acquisire le correlazioni. 
		 * Se serve l'acquisizone di TUTTE correlazioni occorre implementare opportunamente il metodo getCorrelazioniCiviciFromPkidCivi: 
		 * in fase di eliminazione dei duplicati l'eliminazione va fatta nell'ambito di ciascun valore della coppia destFonte/destProgressivo
		 */
		List<Object> listaCorrelati = new ArrayList<Object>(); 
		KeyFabbricatoDTO keyFabbr= (KeyFabbricatoDTO)rf.getObj();
		List<BigDecimal> listaPKIDCivici = indiceCorIndDAO.getCiviciFromFabbricato(keyFabbr);
		return getCorrelazioniCiviciFromPkidCivi(listaPKIDCivici, rf);
	}
	
	//METODO PER AVERE LE CORRELAZIONI FRA CIVICI A PARTIRE DALL'UI (CIOE' DAI CIVICI DELL' UI)
	@Override
	public List<Object> getCiviciCorrelatiFromUI(RicercaIndiceDTO rf) {
		if (rf.getDestFonte()==null || rf.getDestFonte().equals("") || 
			rf.getDestProgressivoEs()==null || rf.getDestProgressivoEs().equals("")	)
			throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONE INDIRETTA UI-CIVICI. KEY UI: " +  ((KeyUIDTO)rf.getObj()).stringValue() + "MANCANO I PARAMETRI DestFonte-DestProgressivoEs ");
		/*
		 * Si deve specificare ls coppia destFonte/destProgressivo per cui acquisire le correlazioni 
		 * Se serve l'acquisizione di TUTTE correlazioni occorre implementare opportunamente il metodo getCorrelazioniCiviciFromPkidCivi
		 */
		List<Object> listaCorrelati = new ArrayList<Object>(); 
		KeyUIDTO keyUI= (KeyUIDTO)rf.getObj();
		List<BigDecimal> listaPKIDCivici = indiceCorIndDAO.getCiviciFromUI(keyUI);
		return getCorrelazioniCiviciFromPkidCivi(listaPKIDCivici, rf);
	}
	
	//METODO PER AVERE LE CORRELAZIONI SIA PER OGGETTO CHE PER CIVICO DATA LA CHIAVE DELL'OGGETTO, IN UN'UNICA LISTA, ELIMINATI I DUPLICATI
	//@Override
	public List<Object> getCorrelazioniOggettiFromUI(RicercaIndiceDTO rf) {
		Hashtable<String, List<Object>> ht = getCorrelazioniOggettiFromUI(rf, false);
		List<Object> listaCorr = ht.get(LISTA_CORRELAZIONI_OGGETTI);
		return listaCorr;
	}
	
	//METODO PER AVERE LE CORRELAZIONI SIA PER OGGETTO CHE PER CIVICO DATA LA CHIAVE DELL'OGGETTO. UTILIZZABILE ANCHE PER AVERE DUE LISTE SEPARATE
	@Override
	public Hashtable<String, List<Object>> getCorrelazioniOggettiFromUI(RicercaIndiceDTO rf, boolean dueListe) {
		Hashtable<String, List<Object>> htCorr = new Hashtable<String, List<Object>>();
		List<Object> listaCorrelati = new ArrayList<Object>();
		if (rf.getDestFonte()==null || rf.getDestFonte().equals("") || 
			rf.getDestProgressivoEs()==null || rf.getDestProgressivoEs().equals("")	)
				throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONI OGGETTI UI (getCorrelazioniOggettiFromUI())-KEY UI: " +  ((KeyUIDTO)rf.getObj()).stringValue() +" MANCANO I PARAMETRI DestFonte-DestProgressivoEs ");
		logger.debug("getCorrelazioniFromUI(). KEY UI: " +  ((KeyUIDTO)rf.getObj()).stringValue() + " FONTE/PROG: " + rf.getDestFonte()+ "/" + rf.getDestProgressivoEs());
		//validazione dei destFonte/desProgressivoES
		Properties mappingProps = getProperties();
		String classNameCivico = mappingProps.getProperty("c" + "_" + rf.getDestFonte() + "_" + rf.getDestProgressivoEs());
		String classNameOggetto = mappingProps.getProperty("o" + "_" + rf.getDestFonte() + "_" + rf.getDestProgressivoEs());
		if (classNameCivico==null ) 
			throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONI OGGETTI UI (getCorrelazioniOggettiFromUI()-KEY UI: " +  ((KeyUIDTO)rf.getObj()).stringValue() +" MAPPING NON TROVATO PER " + "c" + "_" + rf.getDestFonte() + "_" + rf.getDestProgressivoEs());
		if (classNameOggetto==null ) 
			throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONI OGGETTI UI (getCorrelazioniOggettiFromUI()-KEY UI: " +  ((KeyUIDTO)rf.getObj()).stringValue() +" MAPPING NON TROVATO PER " + "o" + "_" + rf.getDestFonte() + "_" + rf.getDestProgressivoEs());
		
		KeyUIDTO keyUi = (KeyUIDTO)rf.getObj();
		
		//acquisizione correlazioni
		List<IndicePK> indexListOggetti = indiceDAO.getOggettoTotalePKFromUI(keyUi, rf.getDestFonte(), rf.getDestProgressivoEs());
		//List<Object> listaOggettiCorrelati = createResult(indexListOggetti, "o");
		List listaOggettiCorrelati = createResult(indexListOggetti, "o");
		logger.debug("ACQUISITE LE CORRELAZIONI DIRETTE U.I.--> OGGETTI. ELEMENTI NUMERO: "+ listaOggettiCorrelati.size());
		//RECUPERO LA PRATICA, NEI CASI IN CUI LA RICERCA DIRETTA NON LA RESTITUISCE  
		if (classNameOggetto.equals("it.webred.ct.data.model.ici.SitCConcessioniCatasto")){
			List<SitCConcessioniCatasto> listaConcCat = (List<SitCConcessioniCatasto>)listaOggettiCorrelati;
			listaOggettiCorrelati =getCorrelazioniConcessioniFromCatasto(listaConcCat, rf);
				
		}
		//recupero le correlazioni indirette, attraverso i civici
		List<BigDecimal> listaPKIDCivici = indiceCorIndDAO.getCiviciFromUI(keyUi);
		List listaCiviciCorrelati = getCorrelazioniCiviciFromPkidCivi(listaPKIDCivici, rf);
		//NESSUN CIVICO TROVATO--> RESTITUISCO SOLO LE CORRELAZIONI DIRETTE
		if (listaCiviciCorrelati == null ||listaCiviciCorrelati.size() == 0 ) {
			if (dueListe) {
				htCorr.put(LISTA_CORRELAZIONI_OGGETTI_DIRETTE, listaOggettiCorrelati);
				htCorr.put(LISTA_CORRELAZIONI_OGGETTI_INDIRETTE, null);
				return htCorr;
			}else {
				htCorr.put(LISTA_CORRELAZIONI_OGGETTI, listaOggettiCorrelati);
				return htCorr;
			}
			
		}
		
		//ora "converto" le correlazioni civici nello stesso tipo delle correlazioni oggetti
		if (classNameCivico.equals(classNameOggetto)) {
			listaCiviciCorrelati= (List<Object> )listaCiviciCorrelati;
			logger.debug("ACQUISITE LE CORRELAZIONI U.I.--> CIVICI --> OGGETTI. ELEMENTI NUMERO: "+ listaCiviciCorrelati.size());
		} else {
			if (classNameCivico.equals("it.webred.ct.data.model.ici.VTIciCiviciAll")  ) {
				List<VTIciCiviciAll> listaCiviciIci = (List<VTIciCiviciAll>) listaCiviciCorrelati;
				listaCiviciCorrelati = getCorrelazioniOggettiIciFromCivici(listaCiviciIci, rf);
				logger.debug("ACQUISITE LE CORRELAZIONI U.I.--> CIVICI --> OGGETTI ICI. ELEMENTI NUMERO: "+ listaCiviciCorrelati.size());
			}else if (classNameCivico.equals("it.webred.ct.data.model.ici.SitCConcIndirizzi")  ) {
				List<SitCConcIndirizzi> listaCiviciConc = (List<SitCConcIndirizzi>) listaCiviciCorrelati;
				listaCiviciCorrelati = getCorrelazioniConcessioniFromCivici(listaCiviciConc, rf);
				logger.debug("ACQUISITE LE CORRELAZIONI U.I.--> CIVICI --> CONCESSIONE. ELEMENTI NUMERO: "+ listaCiviciCorrelati.size());
			}
			//if {}: implementare per gestire gli altri casi....
			else
				throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONI UI (getCorrelazioniFromUI()).KEY UI: " +  ((KeyUIDTO)rf.getObj()).stringValue() +" NON E' POSSIBILE RECUPERARE LE CORRELAZIONI COMUNI CIVICI-OGGETTO PER FONTE/PROG_ES: " + rf.getDestFonte() + "/" + rf.getDestProgressivoEs());
		}
		if (dueListe) {
			htCorr.put(LISTA_CORRELAZIONI_OGGETTI_DIRETTE, listaOggettiCorrelati);
			htCorr.put(LISTA_CORRELAZIONI_OGGETTI_INDIRETTE, listaCiviciCorrelati);
			return htCorr;
		}
		
		//ora va fatto il merge far le due liste
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		Map<String, String> classInfo = listaOggettiCorrelati.size() > 0 ? keyUtil.getEntityInfo(listaOggettiCorrelati.get(0)) : null;
		if (classInfo==null)
			classInfo = listaCiviciCorrelati.size() > 0 ? keyUtil.getEntityInfo(listaCiviciCorrelati.get(0)) : null;
		if (classInfo==null)
			return htCorr;
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		for (Object ele: listaOggettiCorrelati) { 
			String key =  keyUtil.createKey(ele, classInfo.get(IndiceKeyUtil.SEP));
			ht.put(key,ele);
		}
		for (Object ele: listaCiviciCorrelati) { 
			String key =  keyUtil.createKey(ele, classInfo.get(IndiceKeyUtil.SEP));
			ht.put(key,ele);
		}
		listaCorrelati.addAll(ht.values());
		
		htCorr.put(LISTA_CORRELAZIONI_OGGETTI, listaCorrelati);
		return htCorr;
	}
	
	private List<Object> getCorrelazioniCiviciFromPkidCivi (List<BigDecimal> listaPKIDCivici, RicercaIndiceDTO rf) {
		List<Object> listaCorrelati = new ArrayList<Object>(); 
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		Map<String, String> classInfo = null;
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		for (BigDecimal civico: listaPKIDCivici){
			Siticivi siticivi= new Siticivi();
			siticivi.setPkidCivi(civico);
			rf.setObj(siticivi);
			rf.setProgressivoEs("2");
			List<Object> lista = getCiviciCorrelati(rf);
			if (lista.size() == 0)
				continue;
			if (classInfo == null)
				classInfo = keyUtil.getEntityInfo(lista.get(0));
			for (Object ele: lista) { 
				String key =  keyUtil.createKey(ele, classInfo.get(IndiceKeyUtil.SEP));
				ht.put(key,ele);
			}
		}
		listaCorrelati.addAll(ht.values());
		return listaCorrelati;
	}
	
	private Properties getProperties() {
		Properties mappingProps = new Properties();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();			
			URL url = loader.getResource("it/webred/ct/data/access/basic/indice/ricerca/indice_mapping.properties");
			logger.debug("URL ["+url+"]");
			mappingProps.load(url.openStream());
		} catch (Exception e) {
			logger.error("Unable read properties file", e);			
			return null;
		}
		return mappingProps; 
	}

	public List<Object> getCorrelazioniOggettiIciFromCivici(List<VTIciCiviciAll> listaCiviciIci, RicercaIndiceDTO rf) {
		List<Object> listaCorrelati = new ArrayList<Object>(); 
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		Map<String, String> classInfo = null;
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		for (VTIciCiviciAll civ: listaCiviciIci) {
			List<String> listaIdOggIci = indiceCorIndDAO.getOggettiIciFromCivicoIci(civ);
			if (listaIdOggIci.size() == 0)
				continue;
			for (String idOggIci: listaIdOggIci) { 
				//SitTIciOgg ogg = keyUtil.createObject(className, idOggIci, rf.getDestFonte(), rf.getDestProgressivoEs() );
				SitTIciOggetto ogg  = new SitTIciOggetto();
				ogg.setId(idOggIci);
				ht.put(idOggIci,ogg);
			}
			
		}
		listaCorrelati.addAll(ht.values());
		return listaCorrelati;
	}
	public List<Object> getCorrelazioniOggettiTarsuFromCivici(List<VTTarCiviciAll> listaCiviciTarsu, RicercaIndiceDTO rf) {
		List<Object> listaCorrelati = new ArrayList<Object>(); 
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		Map<String, String> classInfo = null;
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		for (VTTarCiviciAll civ: listaCiviciTarsu) {
			List<String> listaIdOggTar = indiceCorIndDAO.getOggettiTarsuFromCivicoTarsu(civ);
			if (listaIdOggTar.size() == 0)
				continue;
			for (String idOggTar: listaIdOggTar) { 
				//SitTIciOgg ogg = keyUtil.createObject(className, idOggIci, rf.getDestFonte(), rf.getDestProgressivoEs() );
				SitTTarOggetto ogg  = new SitTTarOggetto();
				ogg.setId(idOggTar);
				ht.put(idOggTar,ogg);
			}
			
		}
		listaCorrelati.addAll(ht.values());
		return listaCorrelati;
	}
	
	public List<Object> getCorrelazioniConcessioniFromCivici(List<SitCConcIndirizzi> listaCiviciConc, RicercaIndiceDTO rf) {
		List<Object> listaCorrelati = new ArrayList<Object>(); 
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		Map<String, String> classInfo = null;
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		for (SitCConcIndirizzi civ: listaCiviciConc) {
			List<String> listaIdConc = indiceCorIndDAO.getConcessioniFromIndirizzoConcessioni(civ);
			if (listaIdConc.size() == 0)
				continue;
			for (String idConc: listaIdConc) { 
				SitCConcessioni ogg  = new SitCConcessioni();
				ogg.setId(idConc);
				ht.put(idConc,ogg);
			}
			
		}
		listaCorrelati.addAll(ht.values());
		return listaCorrelati;
	}
	
	public List<Object> getCorrelazioniConcessioniFromCatasto(List<SitCConcessioniCatasto> listaConcCatasto, RicercaIndiceDTO rf) {
		List<Object> listaCorrelati = new ArrayList<Object>(); 
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		Map<String, String> classInfo = null;
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		for (SitCConcessioniCatasto conCat: listaConcCatasto) {
			List<String> listaIdConc = indiceCorIndDAO.getConcessioniFromDatiCatastoConcessioni(conCat);
			if (listaIdConc.size() == 0)
				continue;
			for (String idConc: listaIdConc) { 
				SitCConcessioni ogg  = new SitCConcessioni();
				ogg.setId(idConc);
				ht.put(idConc,ogg);
			}
			
		}
		listaCorrelati.addAll(ht.values());
		return listaCorrelati;
	}
	//METODO PER AVERE LE CORRELAZIONI SIA PER FABBRICATO CHE PER CIVICO DATA LA CHIAVE DEL FABBRICATO. UTILIZZABILE ANCHE PER AVERE DUE LISTE SEPARATE
	@Override
	public Hashtable<String, List<Object>> getCorrelazioniFabbricatoFromFabbricato(RicercaIndiceDTO rf, boolean dueListe) {
		Hashtable<String, List<Object>> htCorr = new Hashtable<String, List<Object>>();
		List<Object> listaCorrelati = new ArrayList<Object>();
		if (rf.getDestFonte()==null || rf.getDestFonte().equals("") || 
			rf.getDestProgressivoEs()==null || rf.getDestProgressivoEs().equals("")	)
				throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONI FABBRICATO (getCorrelazioniFabbricatoFromFabbricato())-KEY : " +  ((KeyFabbricatoDTO)rf.getObj()).stringValue() +" MANCANO I PARAMETRI DestFonte-DestProgressivoEs ");
		logger.debug("getCorrelazioniFabbricatoFromFabbricato(). KEY: " +  ((KeyFabbricatoDTO)rf.getObj()).stringValue() + " FONTE/PROG: " + rf.getDestFonte()+ "/" + rf.getDestProgressivoEs());
		//validazione dei destFonte/desProgressivoES
		Properties mappingProps = getProperties();
		String classNameCivico = mappingProps.getProperty("c" + "_" + rf.getDestFonte() + "_" + rf.getDestProgressivoEs());
		String classNameOggetto = mappingProps.getProperty("f" + "_" + rf.getDestFonte() + "_" + rf.getDestProgressivoEs());
		if (classNameCivico==null ) 
			throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONI OGGETTI UI (getCorrelazioniOggettiFromUI()-KEY UI: " +  ((KeyFabbricatoDTO)rf.getObj()).stringValue() +" MAPPING NON TROVATO PER " + "c" + "_" + rf.getDestFonte() + "_" + rf.getDestProgressivoEs());
		if (classNameOggetto==null ) 
			throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONI OGGETTI UI (getCorrelazioniOggettiFromUI()-KEY UI: " +  ((KeyFabbricatoDTO)rf.getObj()).stringValue() +" MAPPING NON TROVATO PER " + "f" + "_" + rf.getDestFonte() + "_" + rf.getDestProgressivoEs());
		
		KeyFabbricatoDTO keyFabbr = (KeyFabbricatoDTO)rf.getObj();
		
		//acquisizione correlazioni
		List<IndicePK> indexListOggetti = indiceDAO.getFabbricatoTotalePKFromUI(keyFabbr, rf.getDestFonte(), rf.getDestProgressivoEs());
		//List<Object> listaFabbricatoCorrelati = createResult(indexListOggetti, "f");
		List listaFabbricatoCorrelati = createResult(indexListOggetti, "f");
		logger.debug("ACQUISITE LE CORRELAZIONI DIRETTE OGG. FABBRICATO--> OGG. FABBRICATO. ELEMENTI NUMERO: "+ listaFabbricatoCorrelati.size());
		//RECUPERO LA PRATICA, NEI CASI IN CUI LA RICERCA DIRETTA NON LA RESTITUISCE  
		if (classNameOggetto.equals("it.webred.ct.data.model.ici.SitCConcessioniCatasto")){
			List<SitCConcessioniCatasto> listaConcCat = (List<SitCConcessioniCatasto>)listaFabbricatoCorrelati;
			listaFabbricatoCorrelati =getCorrelazioniConcessioniFromCatasto(listaConcCat, rf);
				
		}
		//recupero le correlazioni indirette, attraverso i civici
		List<BigDecimal> listaPKIDCivici = indiceCorIndDAO.getCiviciFromFabbricato(keyFabbr);
		List listaCiviciCorrelati = getCorrelazioniCiviciFromPkidCivi(listaPKIDCivici, rf);
		if (classNameCivico.equals(classNameOggetto)) {
			listaCiviciCorrelati= (List<Object> )listaCiviciCorrelati;
			logger.debug("ACQUISITE LE CORRELAZIONI FABBRICATO.--> CIVICI --> FABBRICATO. ELEMENTI NUMERO: "+ listaCiviciCorrelati.size());
		} else if (classNameCivico.equals("it.webred.ct.data.model.ici.VTIciCiviciAll")  ) {
			List<VTIciCiviciAll> listaCiviciIci = (List<VTIciCiviciAll>) listaCiviciCorrelati;
			listaCiviciCorrelati = getCorrelazioniOggettiIciFromCivici(listaCiviciIci, rf); 
			logger.debug("ACQUISITE LE CORRELAZIONI ICI  OGG. FABBRICATO.--> CIVICI --> OGG. FABBRICATO ICI. ELEMENTI NUMERO: "+ listaCiviciCorrelati.size());
		} else if (classNameCivico.equals("it.webred.ct.data.model.tarsu.VTTarCiviciAll")  ) {
			List<VTTarCiviciAll> listaCiviciTar = (List<VTTarCiviciAll>) listaCiviciCorrelati;
			listaCiviciCorrelati = getCorrelazioniOggettiTarsuFromCivici(listaCiviciTar, rf); 
			logger.debug("ACQUISITE LE CORRELAZIONI TARSU OGG. FABBRICATO.--> CIVICI --> OGG. FABBRICATO TARSU. ELEMENTI NUMERO: "+ listaCiviciCorrelati.size());
		}else if (classNameCivico.equals("it.webred.ct.data.model.concedilizie.SitCConcIndirizzi")  ) {
			List<SitCConcIndirizzi> listaCiviciConc = (List<SitCConcIndirizzi>) listaCiviciCorrelati;
			listaCiviciCorrelati = getCorrelazioniConcessioniFromCivici(listaCiviciConc, rf);
			logger.debug("ACQUISITE LE CORRELAZIONI FABBRICATO.--> CIVICI --> CONC-EDIL. FABBRICATO SUI CIVICI ELEMENTI NUMERO: "+ listaCiviciCorrelati.size());
		}
		//else if {}: implementare per gestire gli altri casi....
		else
				throw new IndiceCorrelazioneServiceException("RICERCA CORRELAZIONI FABBRICATO (getCorrelazioniFabbricatoFromFabbricato()).KEY FABB: " +  ((KeyFabbricatoDTO)rf.getObj()).stringValue() +" NON E' POSSIBILE RECUPERARE LE CORRELAZIONI COMUNI CIVICI-OGGETTO PER FONTE/PROG_ES: " + rf.getDestFonte() + "/" + rf.getDestProgressivoEs());
		logger.debug("l--------------------------------- " );
		if (dueListe) {
			htCorr.put(LISTA_CORRELAZIONI_FABBRICATO_DIRETTE, listaFabbricatoCorrelati);
			htCorr.put(LISTA_CORRELAZIONI_FABBRICATO_INDIRETTE, listaCiviciCorrelati);
			return htCorr;
		}
		logger.debug("++++++++++++++++++++++++++++++++++ " );
		//ora va fatto il merge far le due liste
		IndiceKeyUtil keyUtil = new IndiceKeyUtil();
		Map<String, String> classInfo = listaFabbricatoCorrelati.size() > 0 ? keyUtil.getEntityInfo(listaFabbricatoCorrelati.get(0)) : null;
		logger.debug("listaFabbricatoCorrelati.size(): "+ listaFabbricatoCorrelati.size() );
		logger.debug("listaCiviciCorrelati.size(): "+ listaCiviciCorrelati.size() );
		if (classInfo==null)
			classInfo = listaCiviciCorrelati.size() > 0 ? keyUtil.getEntityInfo(listaCiviciCorrelati.get(0)) : null;
		if (classInfo==null)
			return htCorr;
		logger.debug("class info: "+ classInfo);
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		for (Object ele: listaFabbricatoCorrelati) { 
			String key =  keyUtil.createKey(ele, classInfo.get(IndiceKeyUtil.SEP));
			ht.put(key,ele);
		}
		for (Object ele: listaCiviciCorrelati) { 
			String key =  keyUtil.createKey(ele, classInfo.get(IndiceKeyUtil.SEP));
			ht.put(key,ele);
		}
		listaCorrelati.addAll(ht.values());
		
		htCorr.put(LISTA_CORRELAZIONI_FABBRICATO, listaCorrelati);
		return htCorr;
	}

	


		
	
}
