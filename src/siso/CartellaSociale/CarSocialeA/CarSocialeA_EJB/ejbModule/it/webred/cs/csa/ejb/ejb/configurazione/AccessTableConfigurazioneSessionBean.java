package it.webred.cs.csa.ejb.ejb.configurazione;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CatSocialeDAO;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ScuoleSearchCriteria;
import it.webred.cs.csa.ejb.dto.pai.base.CsPaiFaseChiusuraDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.data.model.ArBiInviante;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.ArRelIntCustomIstat;
import it.webred.cs.data.model.ArTClasse;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCComunita;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsCDiarioDove;
import it.webred.cs.data.model.CsCTipoColloquio;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsCfgItTransizione;
import it.webred.cs.data.model.CsCfgParametri;
import it.webred.cs.data.model.CsPaiFaseChiusura;
import it.webred.cs.data.model.CsPaiFaseChiusuraPK;
import it.webred.cs.data.model.CsTbAbitGestProprietario;
import it.webred.cs.data.model.CsTbAbitTitoloGodimento;
import it.webred.cs.data.model.CsTbAnnoScolastico;
import it.webred.cs.data.model.CsTbAssenzaPermesso;
import it.webred.cs.data.model.CsTbBuono;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbContatto;
import it.webred.cs.data.model.CsTbDisabGravita;
import it.webred.cs.data.model.CsTbDisabTipologia;
import it.webred.cs.data.model.CsTbDisponibilita;
import it.webred.cs.data.model.CsTbDurataRicLavoro;
import it.webred.cs.data.model.CsTbEsenzioneRiduzione;
import it.webred.cs.data.model.CsTbFormaGiuridica;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbIcd10;
import it.webred.cs.data.model.CsTbIcd9;
import it.webred.cs.data.model.CsTbIngMercato;
import it.webred.cs.data.model.CsTbInterventiUOL;
import it.webred.cs.data.model.CsTbMacroAttivita;
import it.webred.cs.data.model.CsTbMacroIntervento;
import it.webred.cs.data.model.CsTbMacroSegnal;
import it.webred.cs.data.model.CsTbMicroAttivita;
import it.webred.cs.data.model.CsTbMicroIntervento;
import it.webred.cs.data.model.CsTbMicroSegnal;
import it.webred.cs.data.model.CsTbMotivoChiusuraInt;
import it.webred.cs.data.model.CsTbMotivoChiusuraPai;
import it.webred.cs.data.model.CsTbMotivoSegnal;
import it.webred.cs.data.model.CsTbPermesso;
import it.webred.cs.data.model.CsTbPotesta;
import it.webred.cs.data.model.CsTbProbl;
import it.webred.cs.data.model.CsTbProblematica;
import it.webred.cs.data.model.CsTbProfessione;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.CsTbResponsabilita;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.data.model.CsTbScuolaAnno;
import it.webred.cs.data.model.CsTbSecondoLivello;
import it.webred.cs.data.model.CsTbServComunita;
import it.webred.cs.data.model.CsTbServLuogoStr;
import it.webred.cs.data.model.CsTbServResRetta;
import it.webred.cs.data.model.CsTbServSemiresRetta;
import it.webred.cs.data.model.CsTbSettoreImpiego;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.cs.data.model.CsTbSinaRisposta;
import it.webred.cs.data.model.CsTbSottocartellaDoc;
import it.webred.cs.data.model.CsTbSsProvenienza;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbStatus;
import it.webred.cs.data.model.CsTbTipoAbitazione;
import it.webred.cs.data.model.CsTbTipoAlert;
import it.webred.cs.data.model.CsTbTipoCirc4;
import it.webred.cs.data.model.CsTbTipoComunita;
import it.webred.cs.data.model.CsTbTipoContributo;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.CsTbTipoIndirizzo;
import it.webred.cs.data.model.CsTbTipoIsee;
import it.webred.cs.data.model.CsTbTipoMinore;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.data.model.CsTbTipoPai;
import it.webred.cs.data.model.CsTbTipoProgetto;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.data.model.CsTbTipoRetta;
import it.webred.cs.data.model.CsTbTipoRientriFami;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.data.model.CsTbTutela;
import it.webred.cs.data.model.CsTbUnitaMisura;
import it.webred.cs.data.model.TipoStruttura;
import it.webred.cs.data.model.VArCTariffa;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.data.model.VServiziCustom;
import it.webred.cs.data.model.VStrutturaArea;
import it.webred.cs.data.model.VTipiInterventoUsati;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * EJB per accedere a dati di configurazione dell'installazione: tabelle valori (TB) e similari
 * Non inserire all'interno metodi per accedere a dati sensibili in quanto non sottoposto ad AUDIT
 * */


@Stateless
public class AccessTableConfigurazioneSessionBean extends CarSocialeBaseSessionBean implements AccessTableConfigurazioneSessionBeanRemote {


	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDAO;
	
	@Autowired
	private IterDAO iterDAO;
	
	@Autowired
	private CatSocialeDAO catSocialeDao;
	
	@Autowired
	private IndirizzoDAO indirizzoDAO;

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
    public List<KeyValueDTO> getListaStatoCivile(CeTBaseObject cet) {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
    	List<CsTbStatoCivile> lst = configurazioneDAO.getStatoCivile();
    	for(CsTbStatoCivile v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getCod(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato()!=null && v.getAbilitato());
    		lstItems.add(kv);
    	}
    	return lstItems;
    }
	
	public List<CsTbStatoCivile> getStatoCivile(CeTBaseObject cet){
		return configurazioneDAO.getStatoCivile();
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
    public CsTbStatoCivile getStatoCivileByIdExtCet(BaseDTO dto) {
    	return configurazioneDAO.getStatoCivileByIdExtCet(dto.getEnteId(), (String) dto.getObj());
    }
    
    public CsTbStatoCivile getStatoCivileByDescrizione(BaseDTO dto) {
    	return configurazioneDAO.getStatoCivileByDescrizione((String) dto.getObj());
    }
    
    @Override
	public CsTbTipoRapportoCon getTipoRapportoConByCodice(BaseDTO dto) {
		return configurazioneDAO.getTipoRapportoConByCodice((Long) dto.getObj());
	}
    
    @Override
	public CsTbStatoCivile getStatoCivileByCodice(BaseDTO dto) {
		return configurazioneDAO.getStatoCivileByCodice((String) dto.getObj());
	}
    @Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
    public List<KeyValueDTO> getStatus(CeTBaseObject cet) {
    	List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
    	List<CsTbStatus> lst = configurazioneDAO.getStatus();
    	for(CsTbStatus v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato()!=null && v.getAbilitato()); //??? if("0".equals(status.getAbilitato()))
    		lstItems.add(kv);
    	}
    	return lstItems;
    }
    
    public CsTbStatus getStatusById(BaseDTO dto) {
		if(dto.getObj()!=null && ((String)dto.getObj()).length()>0)
			return configurazioneDAO.getStatusById(new Long((String)dto.getObj()));
		else
			return null;
	}
    
    @Override
    public List<CsTbSchedaMultidim> getParamsSchedaMultidim(BaseDTO dto) {
    	String tipo = (String)dto.getObj();
    	return configurazioneDAO.getParametriSM(tipo);
    }
    
    @Override
    public CsTbSchedaMultidim getParamSchedaMultidim(BaseDTO dto) {
		return configurazioneDAO.getParametroSM((String)dto.getObj(), (String)dto.getObj2());
	}
    
    @Override
    public List<CsTbSinaDomanda> getListaDomandaSina(BaseDTO dto) {
		return configurazioneDAO.getListaDomandaSina();
	}
    
    @AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
    public List<CsTbPermesso> getPermesso(CeTBaseObject cet) {
    	return configurazioneDAO.getPermesso();
    }
    
    //inizio SISO -792
    public List<CsTbAssenzaPermesso> getPermessoSenzaSoggiorno(CeTBaseObject cet) {
    	return configurazioneDAO.getPermessoSenzaTitoloSoggiorno();
    }
    
    public CsTbAssenzaPermesso getAssenzaPermessoById(BaseDTO dto) {
		if(dto.getObj()!=null && ((String)dto.getObj()).length()>0)
			return configurazioneDAO.getAssenzaPermessoById(new Long((String)dto.getObj()));
		else
			return null;
	}
    //fine SISO -792
    public CsTbPermesso getPermessoById(BaseDTO dto) {
		if(dto.getObj()!=null && ((String)dto.getObj()).length()>0)
			return configurazioneDAO.getPermessoById(new Long((String)dto.getObj()));
		else
			return null;
	}
    
    
    public List<CsTbTipoIndirizzo> getTipoIndirizzo(CeTBaseObject cet) {
    	return configurazioneDAO.getTipoIndirizzo();
    }
    
    public List<CsTbTipoContributo> getTipoContributo(CeTBaseObject cet) {
    	return configurazioneDAO.getTipoContributo();
    }
    @Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
    public List<CsTbTipologiaFamiliare> getTipologieFamiliari(CeTBaseObject cet) {
    	return configurazioneDAO.getTipologieFamiliari();
    }
    
	public CsTbTipologiaFamiliare getTipologiaFamiliareById(BaseDTO dto) {
		if(dto.getObj()!=null && ((Long)dto.getObj())>0)
			return configurazioneDAO.getTipologiaFamiliareById((Long)dto.getObj());
		else
			return null;
	}
    
    public List<CsTbResponsabilita> getResponsabilita(CeTBaseObject cet) {
    	return configurazioneDAO.getResponsabilita();
    }
    
    public List<KeyValueDTO> getProblematiche(CeTBaseObject cet) {
    	return configurazioneDAO.getTbItems("CS_TB_PROBLEMATICA");
    }
    
    public CsTbProblematica getProblematicaById(BaseDTO dto) {
    	return configurazioneDAO.getProblematicaById((Long) dto.getObj());
    }
    
    public List<KeyValueDTO> getStesuraRelazioniPer(CeTBaseObject cet) {
    	return configurazioneDAO.getTbItems("CS_TB_STESURA_RELAZIONI_PER");
    }
    
    public List<KeyValueDTO> getTitoliStudio(CeTBaseObject cet) {
    	List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
    	List<CsTbTitoloStudio> lista = configurazioneDAO.getTitoliStudio(Boolean.FALSE);
    	for(CsTbTitoloStudio tb : lista){
    		KeyValueDTO kv = new KeyValueDTO(tb.getId(), tb.getDescrizione());
    		kv.setAbilitato(tb.getAbilitato());
    		lstOut.add(kv);
    	}
    	return lstOut;
    }
    
    public CsTbTitoloStudio getTitoloStudioById(BaseDTO dto) {
    	return configurazioneDAO.getTitoloStudioById((Long) dto.getObj());
    }
    
    public List<CsTbProfessione> getProfessioni(CeTBaseObject cet) {
    	return configurazioneDAO.getProfessioni();
    }
    
    @Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<CsTbCondLavoro> getCondLavoro(CeTBaseObject cet) {
		return configurazioneDAO.getCondLavoro();
	}
    
    @Override
    public TreeMap<String, List<CsTbCondLavoro>> getMappaCondLavoro(CeTBaseObject cet) {
    	Hashtable<String, List<CsTbCondLavoro>> mappa = new Hashtable<String, List<CsTbCondLavoro>>();
    	List<CsTbCondLavoro> lst = configurazioneDAO.getCondLavoro();
    	if(lst!=null){
    		for(CsTbCondLavoro c : lst){
    			String idIngMer = c.getCsTbIngMercato()!=null ? c.getCsTbIngMercato().getId() : "";
    			List<CsTbCondLavoro> lstC = mappa.get(idIngMer);
    			if(lstC==null)
    				lstC=new ArrayList<CsTbCondLavoro>();
    			lstC.add(c);
    			mappa.put(idIngMer, lstC);
    		}
    	}
    	TreeMap<String, List<CsTbCondLavoro>> tree = new TreeMap<String, List<CsTbCondLavoro>>(mappa);
    	return tree;
    }
    
    @Override
    public List<CsTbSettoreImpiego> getSettoreImpiego(CeTBaseObject cet) {
    	return configurazioneDAO.getSettoreImpiego();
    }
    
    @Override
    public List<KeyValueDTO> getTipoContratto(CeTBaseObject cet) {
    	return configurazioneDAO.getTbItems("CS_TB_TIPO_CONTRATTO");
    }
    
    public List<CsTbTutela> getTutele(CeTBaseObject cet) {
    	return configurazioneDAO.getTutele();
    }
    
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    public List<CsTbTitoloStudio> getTbTitoloStudioAbilitato(BaseDTO dto) {
    	return configurazioneDAO.getTitoliStudio(Boolean.TRUE);
   }
    
    public CsTbIcd10 getIcd10ById(BaseDTO dto) {
    	return configurazioneDAO.getIcd10ById((Long) dto.getObj());
    }
    
    public List<String> getIcd10CodIniziali(CeTBaseObject cet) {
    	return configurazioneDAO.getIcd10CodIniziali();
    }
    
    public List<CsTbIcd10> getIcd10ByCodIniziali(BaseDTO dto) {
    	return configurazioneDAO.getIcd10ByCodIniziali((String) dto.getObj());
    }
    
    public CsTbIcd9 getIcd9ById(BaseDTO dto) {
    	return configurazioneDAO.getIcd9ById((Long) dto.getObj());
    }
    
    public List<String> getIcd9CodIniziali(CeTBaseObject cet) {
    	return configurazioneDAO.getIcd9CodIniziali();
    }
    
    public List<CsTbIcd9> getIcd9ByCodIniziali(BaseDTO dto) {
    	return configurazioneDAO.getIcd9ByCodIniziali((String) dto.getObj());
    }
    
	public List<CsTbTipoRapportoCon> getTipoRapportoConParenti(CeTBaseObject cet) {
		
		List<CsTbTipoRapportoCon> lst = configurazioneDAO.getTipoRapportoConParenti();
		this.spostaAltroInFondo(lst);
		return lst;
    }
	
	public List<CsTbTipoRapportoCon> getTipoRapportoConConoscenti(CeTBaseObject cet) {
		List<CsTbTipoRapportoCon> lst = configurazioneDAO.getTipoRapportoConConoscenti();
		this.spostaAltroInFondo(lst);
		return lst;
    }
	
	private void spostaAltroInFondo(List<CsTbTipoRapportoCon> lst){
		boolean trovato = false;
		int i=0;
		
		while(!trovato && i<lst.size()){
			CsTbTipoRapportoCon tr = lst.get(i);
			if(tr.getDescrizione().startsWith("Altro"))
				trovato = true;
			else
				i++;
		}
		if(trovato){
			CsTbTipoRapportoCon tr =lst.remove(i);
			lst.add(tr);
		}
	}
	
    public List<CsTbPotesta> getPotesta(CeTBaseObject cet) {
    	return configurazioneDAO.getPotesta();
    }
    
	public List<CsTbDisponibilita> getDisponibilita(CeTBaseObject cet) {
    	return configurazioneDAO.getDisponibilita();
    }
    
	@Override
	public List<KeyValueDTO> getDurataRicLavoro(CeTBaseObject cet) {
		List<CsTbDurataRicLavoro> lstIn = configurazioneDAO.getDurataRicLavoro();
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		for (CsTbDurataRicLavoro cs : lstIn) {
			KeyValueDTO kv = new KeyValueDTO(cs.getId(), cs.getDescrizione());
			kv.setAbilitato(cs.getAbilitato());
			lstOut.add(kv);
		}
		return lstOut;
    }
	
	public List<KeyValueDTO> getTipoPai(CeTBaseObject cet) {
		List<CsTbTipoPai> lstIn = configurazioneDAO.getTipoPai();
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		for (CsTbTipoPai cs : lstIn) {
			KeyValueDTO kv = new KeyValueDTO(cs.getId(), cs.getDescrizione());
			kv.setAbilitato(cs.getAbilitato());
			lstOut.add(kv);
		}
		return lstOut;
    }
    
	public List<CsTbMotivoChiusuraPai> getMotivoChiusuraPai(CeTBaseObject cet) {
		return configurazioneDAO.getMotivoChiusuraPai();
    }
    
	public List<CsTbContatto> getContatti(CeTBaseObject cet) {
    	return configurazioneDAO.getContatti();
    }
	
	public List<KeyValueDTO> getMacroSegnalazioni(CeTBaseObject cet) {
		return configurazioneDAO.getTbItems("CS_TB_MACRO_SEGNAL");
    }
	
	public CsTbMacroSegnal getMacroSegnalazioneById(BaseDTO dto) {
		return configurazioneDAO.getMacroSegnalazioneById((Long) dto.getObj());
	}
	
	public List<CsTbMicroSegnal> getMicroSegnalazioni(CeTBaseObject cet) {
    	return configurazioneDAO.getMicroSegnalazioni();
    }
	
	public CsTbMicroSegnal getMicroSegnalazioneById(BaseDTO dto) {
		return configurazioneDAO.getMicroSegnalazioneById((Long) dto.getObj());
	}
	
	public List<KeyValueDTO> getMotivoSegnalazioni(CeTBaseObject cet) {
		return configurazioneDAO.getTbItems("CS_TB_MOTIVO_SEGNAL");
    }
	
	public CsTbMotivoSegnal getMotivoSegnalazioneById(BaseDTO dto) {
		return configurazioneDAO.getMotivoSegnalazioneById((Long) dto.getObj());
	}
	
	public List<KeyValueDTO> getDisabEnte(CeTBaseObject cet) {
    	return configurazioneDAO.getTbItems("CS_TB_DISAB_ENTE");
    }
	
	public List<KeyValueDTO> getDisabGravita(CeTBaseObject cet) {
		return configurazioneDAO.getTbItems("CS_TB_DISAB_GRAVITA");
    }
	
    public CsTbDisabGravita getDisabGravitaById(BaseDTO dto) {
    	return configurazioneDAO.getDisabGravitaById((Long) dto.getObj());
    }
	
	public List<CsTbDisabTipologia> getDisabTipologia(CeTBaseObject cet) {
    	return configurazioneDAO.getDisabTipologia();
    }
	
    public CsTbDisabTipologia getDisabTipologiaById(BaseDTO dto) {
    	return configurazioneDAO.getDisabTipologiaById((Long) dto.getObj());
    }
	
	public List<CsTbServComunita> getServComunita(CeTBaseObject cet) {
    	return configurazioneDAO.getServComunita();
    }
	
	public List<CsTbServLuogoStr> getServLuogoStr(CeTBaseObject cet) {
    	return configurazioneDAO.getServLuogoStr();
    }
	
	public List<CsTbServResRetta> getServResRetta(CeTBaseObject cet) {
    	return configurazioneDAO.getServResRetta();
    }
	
	public List<CsTbServSemiresRetta> getServSemiresRetta(CeTBaseObject cet) {
    	return configurazioneDAO.getServSemiresRetta();
    }
	
	public List<CsTbBuono> getBuoni(CeTBaseObject cet) {
    	return configurazioneDAO.getBuoni();
    }
	
	public List<CsTbEsenzioneRiduzione> getEsenzioniRiduzioni(CeTBaseObject cet) {
    	return configurazioneDAO.getEsenzioniRiduzioni();
    }
	
	
	public List<CsTbMotivoChiusuraInt> getMotiviChiusuraIntervento(CeTBaseObject cet) {
		return configurazioneDAO.getMotiviChiusuraIntervento();
	}
	
	public CsTbMotivoChiusuraInt getMotivoChiusuraIntervento(BaseDTO cet) {
		String id = (String)cet.getObj();
		if(id!=null && id.length()>0)
			return configurazioneDAO.getMotivoChiusuraIntervento(new Long((String)cet.getObj()));
		else
			return null;
	}

	@Override
	public List<CsTbTipoDiario> getTipiDiario(CeTBaseObject cet) {
		return configurazioneDAO.getTipiDiario();
	}

	@Override
	public List<CsTbTipoRetta> getTipiRetta(CeTBaseObject cet) {
		return configurazioneDAO.getTipiRetta();
	}

	@Override
	public List<CsTbTipoRientriFami> getTipiRientriFami(CeTBaseObject cet) {
		return configurazioneDAO.getTipiRientriFami();
	}

	@Override
	public List<CsTbTipoComunita> getTipiComunita(CeTBaseObject cet) {
		return configurazioneDAO.getTipiComunita();
	}
	
	@Override
	public CsTbTipoComunita findTipoComunitaByDesc(BaseDTO cet) {
		return configurazioneDAO.getTipoComunitaByDesc((String)cet.getObj());
	}

	@Override
	public CsTbTipoRetta getTipoRetta(BaseDTO cet) {
		return configurazioneDAO.getTipoRetta((Long)cet.getObj());
	}
	
	@Override
	public CsTbTipoContributo getTipoContributo(BaseDTO cet) {
		return configurazioneDAO.getTipoContributo((Long)cet.getObj());
	}
	
	@Override
	public CsTbTipoRientriFami getTipoRientriFami(BaseDTO cet) {
		return configurazioneDAO.getTipoRientriFami((Long)cet.getObj());
	}

	@Override
	public List<CsTbTipoOperatore> getTipiOperatore(CeTBaseObject cet) {
		return configurazioneDAO.getTipiOperatore();
	}
	
	@Override
	public List<KeyValueDTO> getinterventiUOL(CeTBaseObject cet) {
		List<CsTbInterventiUOL> lstIn = configurazioneDAO.getInterventiUOL();
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		for (CsTbInterventiUOL cs : lstIn) {
			KeyValueDTO kv = new KeyValueDTO(cs.getId(), cs.getDescrizione());
			kv.setAbilitato(cs.getAbilitato());
			lstOut.add(kv);
		}
		return lstOut;
	}
	
	@Override
    public CsTbInterventiUOL getInterventiUOLById(BaseDTO dto) {
    	return configurazioneDAO.getInterventiUOLById((Long) dto.getObj());
    }
	
	@Override
	public List<KeyValueDTO> getTipiCirc4(CeTBaseObject cet) {
		List<CsTbTipoCirc4> lstIn = configurazioneDAO.getTipiCirc4();
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		for (CsTbTipoCirc4 cs : lstIn) {
			KeyValueDTO kv = new KeyValueDTO(cs.getId(), cs.getDescrizione());
			kv.setAbilitato(cs.getAbilitato());
			lstOut.add(kv);
		}
		return lstOut;
	}
	
	@Override
    public CsTbTipoCirc4 getTipoCirc4ById(BaseDTO dto) {
    	return configurazioneDAO.getTipoCirc4ById((Long) dto.getObj());
    }
	
	@Override
	public List<KeyValueDTO> getTipiProgetto(CeTBaseObject cet) {
		List<KeyValueDTO>listaProgetti = new ArrayList<KeyValueDTO>();
	    List<CsTbTipoProgetto> lista = configurazioneDAO.getTipiProgetto();
		for (CsTbTipoProgetto cs : lista) {
			KeyValueDTO si = new KeyValueDTO(cs.getId(), cs.getDescrizione());
			si.setAbilitato(cs.isAbilitato());
			listaProgetti.add(si);
		}
		return listaProgetti;
	}
	
	@Override
    public CsTbTipoProgetto getTipoProgettoById(BaseDTO dto) {
    	return configurazioneDAO.getTipoProgettoById((Long) dto.getObj());
    }
	
	@Override
    public CsCfgParametri getParametro(BaseDTO dto) {
    	return configurazioneDAO.getParametro((String) dto.getObj(), (String) dto.getObj2());
    }
	
	@Override
	public List<KeyValueDTO> getAnniScolastici(CeTBaseObject cet) {
		List<KeyValueDTO> lista = new ArrayList<KeyValueDTO>();
	    List<CsTbAnnoScolastico> lst = configurazioneDAO.getAnniScolastici();
		for (CsTbAnnoScolastico cs : lst) {
			KeyValueDTO si = new KeyValueDTO(cs.getId(), cs.getDescrizione());
			si.setAbilitato(cs.getAbilitato());
			lista.add(si);
		}
		return lista;
	}

	@Override
	public List<KeyValueDTO> getComuniScuole(CeTBaseObject dto) {
		List<KeyValueDTO> lista = new ArrayList<KeyValueDTO>();
	    List<String> lst =  configurazioneDAO.getComuniScuole();
	    for (String cs : lst) {
	    	KeyValueDTO si = new KeyValueDTO(cs, cs);
			lista.add(si);
		}
		return lista;
	}

	@Override
	public List<KeyValueDTO> getTipoScuole(CeTBaseObject cet) {
		return configurazioneDAO.getTbItems("CS_TB_TIPO_SCUOLA");
	}
	
	@Override
	public CsTbCondLavoro getCondLavoroById(BaseDTO dto) {
		if(dto.getObj()!=null && ((String)dto.getObj()).length()>0)
			return configurazioneDAO.getCondLavoro(new Long((String)dto.getObj()));
		else
			return null;
	}

	@Override
	public CsTbProfessione getProfessioneById(BaseDTO dto) {
		if(dto.getObj()!=null && ((String)dto.getObj()).length()>0)
			return configurazioneDAO.getProfessione(new Long((String)dto.getObj()));
		else
			return null;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<KeyValueDTO> getGruppiVulnerabili(CeTBaseObject cet) {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbGVulnerabile> lst =  configurazioneDAO.getGrVulnerabilita();
    	for(CsTbGVulnerabile v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato()!=null && v.getAbilitato().booleanValue());
    		lstItems.add(kv);
    	}
    	return lstItems;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<CsTbGVulnerabile> getGruppiVulnerab(CeTBaseObject cet) {
		return configurazioneDAO.getGrVulnerabilita();
	}
		
	@Override
	public CsTbGVulnerabile getGrVulnerabileById(BaseDTO dto) {
		if(dto.getObj()!=null && ((String)dto.getObj()).length()>0)
			return configurazioneDAO.getGrVulnerabile((String)dto.getObj());
		else
			return null;
	}
	
	@Override
	public CsTbDisponibilita getDisponibilitaById(BaseDTO dto){
		return configurazioneDAO.getDisponibilitaById((Long)dto.getObj());
	}


	@Override
	public List<KeyValueDTO> getCittadinanzeAcquisite(CeTBaseObject cet) {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbCittadinanzaAcq> lst =  configurazioneDAO.getCittadinanzeAcquisite();
    	for(CsTbCittadinanzaAcq v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato()!=null && v.getAbilitato().booleanValue());
    		lstItems.add(kv);
    	}
    	return lstItems;
	}
	
	//TODO TASK SISO 1044 spostata qui la creazione dei dati necessari alla creazione della  scheda triage
	public  HashMap<String, List<TriageItemDTO>> getTriageValueMap()
	{
			return configurazioneDAO.getTriageValueMap();
	}
	
	@Override
	public CsTbCittadinanzaAcq getCittadinanzaAcqById(BaseDTO dto) {
		if(dto.getObj()!=null && (Long)dto.getObj()!=0)
			return configurazioneDAO.getCittadinanzaAcq((Long)dto.getObj());
		else
			return null;
	}

	@Override
	public CsTbSettoreImpiego getSettoreImpiegoById(BaseDTO dto) {
		if(dto.getObj()!=null && (Long)dto.getObj()!=0)
			return configurazioneDAO.getSettoreImpiegoById((Long)dto.getObj());
		else
			return null;
	}
	
	@Override
	public CsTbFormaGiuridica getFormaGiuridicaById(BaseDTO dto) {
		if(dto.getObj()!=null)
			return configurazioneDAO.getFormaGiuridicaById((String)dto.getObj());
		else
			return null;
	}

	@Override
	public CsTbIngMercato getIngMercatoById(BaseDTO dto) {
		if(dto.getObj()!=null)
			return configurazioneDAO.getIngMercatoById((String)dto.getObj());
		else
			return null;
	}

	@Override
	public List<CsTbSottocartellaDoc> getTipoCartelle(BaseDTO dto){
		return configurazioneDAO.getTipoCartelle();
	}

	@Override
	public CsTbSottocartellaDoc getTipoCartellaById(BaseDTO dto) {
		if(dto.getObj()!=null && (Long)dto.getObj()!=0)
			return configurazioneDAO.getTipoCartellaById((Long)dto.getObj());
		else
			return null;
	}	

	@Override
	public List<CsTbUnitaMisura> getCsTbUnitaMisura(BaseDTO dto) {
		return configurazioneDAO.getCsTbUnitaMisura();
	}
	
	@Override
	public CsTbMicroAttivita getMicroAttivitaById(BaseDTO dto) {
		return configurazioneDAO.getMicroAttivitaById((Long) dto.getObj());
	}
	
	@Override
	public List<CsTbMacroAttivita> getMacroAttivita(CeTBaseObject cet) {
    	return configurazioneDAO.getMacroAttivita();
    }

	@Override
	public List<CsTbProbl> getProbl(CeTBaseObject dto) {
		return configurazioneDAO.getProbl();		
	}

	@Override
	public CsTbProbl getProblById(BaseDTO dto) {
		return configurazioneDAO.getProblById((Long) dto.getObj());
	}

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
   public CsTbTipoRapportoCon mappaRelazioneParentale(BaseDTO dto){
    	return configurazioneDAO.mappaRelazioneParentale((String)dto.getObj(), dto.getEnteId());
    }
	    
	@Override
	public List<CsTbTipoAlert> getTipiAlert(CeTBaseObject cet){
		return configurazioneDAO.getTipiAlert();
	}
	
	@Override
	public List<KeyValueDTO> getFormeGiuridiche(CeTBaseObject cet){
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbFormaGiuridica> lst = configurazioneDAO.getFormeGiuridiche();
		for(CsTbFormaGiuridica v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato());
    		lstItems.add(kv);
    	}
    	return lstItems;
	}

	@Override
	public String findCodFormProgetto(BaseDTO dto) {
		return configurazioneDAO.findCodFormProgetto((String)dto.getObj(), (Long)dto.getObj2(), (Long)dto.getObj3(), (Long)dto.getObj4());
	}
	
	@Override
	public List<KeyValueDTO> getTipoColloquios(CeTBaseObject cet) throws Exception {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsCTipoColloquio> lst = configurazioneDAO.findAllTipoColloquios();
		for(CsCTipoColloquio v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato());
    		lstItems.add(kv);
    	}
    	return lstItems;
	}

	@Override
	public List<KeyValueDTO> getDiarioDoves(CeTBaseObject cet) {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsCDiarioDove> lst = configurazioneDAO.findAllDiarioDoves();
		for(CsCDiarioDove v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato());
    		lstItems.add(kv);
    	}
    	return lstItems;
	}

	@Override
	public List<KeyValueDTO> getDiarioConchis(CeTBaseObject cet) {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsCDiarioConchi> lst = configurazioneDAO.findAllDiarioConchis();
		for(CsCDiarioConchi v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato());
    		lstItems.add(kv);
    	}
    	return lstItems;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<KeyValueDTO> getListaTipoAbitazione(CeTBaseObject cet){
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbTipoAbitazione> lst =  configurazioneDAO.getListaTipoAbitazione();
		for(CsTbTipoAbitazione v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato());
    		lstItems.add(kv);
    	}
		return lstItems;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<KeyValueDTO> getListaTitoloGod(CeTBaseObject cet){
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbAbitTitoloGodimento> lst = configurazioneDAO.getListaTitoloGod();
		for(CsTbAbitTitoloGodimento v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato());
    		lstItems.add(kv);
    	}
		return lstItems;
	}
		
	@Override
	public ArTbPrestazioniInps findArTbPrestazioniInpsByCodice(BaseDTO dto) { 
		List<ArTbPrestazioniInps> lst = new ArrayList<ArTbPrestazioniInps>();
		if(dto.getObj()!=null){
			String[] codici = {dto.getObj().toString()};
			lst = configurazioneDAO.findArTbPrestazioniInpsByCodice(codici);
		}
		return lst!=null && !lst.isEmpty() ? lst.get(0) : null;
	} 
	//FINE MOD-RL

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<KeyValueDTO> getListaGestProprietario(CeTBaseObject cet) {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbAbitGestProprietario> lst = configurazioneDAO.getListaGestProprietario();
		for(CsTbAbitGestProprietario v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato());
    		lstItems.add(kv);
    	}
		return lstItems;
	}

	//SISO-1172
	public List<KeyValueDTO> getLstMotivoChiusuraPai(BaseDTO dto) {
		return configurazioneDAO.getMotiviChiusuraPai((Long) dto.getObj());
	}
	//SISO-1172 FINE
	public List<ArTbPrestazioniInps> getPrestazioniInpsSinBa(BaseDTO dto) {
		return configurazioneDAO.getPrestazioniInpsFromAreaId(5);
	}
	
	@Override
	public List<CsTbMacroIntervento> readPDSMacro(BaseDTO dto) {
		return configurazioneDAO.readMacroDb((Long)dto.getObj());
	}
	
	@Override
	public List<CsTbMicroIntervento> readPDSMicro(BaseDTO dto) {				
		return configurazioneDAO.readMicroDb((Long) dto.getObj());
	}

	@Override
	public List<KeyValueDTO> getStruttureTribunale(CeTBaseObject bo) {
		return configurazioneDAO.getTbItems("CS_TB_TRIB_STRUTTURA");
	}

	//SISO-1160
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public ArBiInviante findInviante(BaseDTO dto){
		return configurazioneDAO.findArBiInviante((String)dto.getObj(), (Long)dto.getObj2());
	}

	@Override
	public List<CsTbTipoIsee> getListaTipoIsee(CeTBaseObject cet) {
		return configurazioneDAO.getTipiIsee();
	}

	@Override
	public List<KeyValueDTO> getListaSecondoLivello(CeTBaseObject cet) {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbSecondoLivello> lst = configurazioneDAO.getTipiListaSecondoLivello();
    	for(CsTbSecondoLivello v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.getAbilitato()!=null && v.getAbilitato());
    		lstItems.add(kv);
    	}
    	return lstItems;
	}

	
	@Override
	public String findCodiceSinbaMotivoChiusura(BaseDTO b) {
		return configurazioneDAO.findCodiceSinbaMotivoChiusura((String)b.getObj());
	}
	


	@Override
	public List<KeyValueDTO>  findStruttura(CeTBaseObject cet){
		return configurazioneDAO.findStruttura();
	}
	@Override
	public List<KeyValueDTO>  findArea(BaseDTO dto){
		return configurazioneDAO.findArea((Long)dto.getObj());
	}

	@Override
	public List<KeyValueDTO> findAllArea(CeTBaseObject cet) {
		return configurazioneDAO.findAllArea();
	}
	
	//recupero codiceDocumentoGed
	@Override
	public String findCodiceDocumentoGed(BaseDTO dto) {
		return configurazioneDAO.findCodiceDocumentoGed((String)dto.getObj());
	}
	//#ROMACAPITALE fine

	@Override
	public CsTbProgettoAltro getProgettoAltroById(BaseDTO dto) {
    	return configurazioneDAO.getProgettoAltroById((Long) dto.getObj());
    }
	
	@Override
	public CsTbProgettoAltro getProgettoAltroByDescrizione(String descrizione) {
    	return configurazioneDAO.getProgettoAltroByDescrizione(descrizione);
    }
	//SISO-1207
	@Override
	public List<CsTbUnitaMisura> getCsTbUnitaMisuraByInterventoIstatCustom(BaseDTO dto) {
		/*
		 * Nel sistema si deve restringere la possibilità di selezionare le unità misura una volta selezionato un determinato servizio da erogare. Creare una nuova tabella CS_C_INTERV_UM (nello schema CS) che permetta di specificare la corretta UM per un determinato servizio selezionato. 
		 * Le UM devono essere selezionate in ordine di priorità:
			-UM per la coppia di tipi interventi ISTAT/CUSTOM
			-UM indicate per tipo intervento CUSTOM e ISTAT null
			-UM per tipo intervento ISTAT
		*/
		List<CsTbUnitaMisura> listaUnitaMisura = new ArrayList<CsTbUnitaMisura>();
		Long idIstat = (Long)dto.getObj();
		Long idCustom = (Long)dto.getObj2();
		
		/*-UM per la coppia di tipi interventi ISTAT/CUSTOM*/
		if(idIstat!=null && idCustom!=null && idIstat>0 && idCustom>0){
			String query1 = this.createQueryUnitaMisura(idIstat, idCustom );
			listaUnitaMisura = configurazioneDAO.getUnitaMisuraByIdInterventi(query1);
		}
		
		/*-UM indicate per tipo intervento CUSTOM e ISTAT null*/
		if(listaUnitaMisura.isEmpty() && idCustom!=null && idCustom > 0){
			String query2 = this.createQueryUnitaMisura(null, idCustom);
			listaUnitaMisura = configurazioneDAO.getUnitaMisuraByIdInterventi(query2);
		}
		
		/*-UM per tipo intervento ISTAT*/
		if(listaUnitaMisura.isEmpty() && idIstat!=null && idIstat > 0){
			String query3 = this.createQueryUnitaMisura(idIstat, null);
			listaUnitaMisura = configurazioneDAO.getUnitaMisuraByIdInterventi(query3);
		}
		
		return listaUnitaMisura;
		
	}
	
	 protected  String createQueryUnitaMisura(Long idTipoInterventoIstat, Long idTipoInterventoCustom) {
	    	String sql = "select um "+
					"from CsTbUnitaMisura um, CsCIntervUm interventi " +
					"where  um.id = interventi.idUnitaMisura ";
	    	
		sql+= idTipoInterventoIstat!=null && idTipoInterventoIstat>0 ? " and interventi.idInterventoIstat =  "+ idTipoInterventoIstat : " and interventi.idInterventoIstat is null ";
		sql+= idTipoInterventoCustom!=null && idTipoInterventoCustom>0 ? " and interventi.idInterventoCustom =  "+ idTipoInterventoCustom : " and interventi.idInterventoCustom is null";		

		return sql;
	}

   
	@Override
	public CsTbTipoIsee getTipoIsee(BaseDTO cet) {
		return configurazioneDAO.getTipoIsee((Long)cet.getObj());
	}

	@Override
	public void salva2Livello(BaseDTO dto) {
		String nome = (String)dto.getObj();
		configurazioneDAO.salva2Livello(nome);
		
	}
	//SISO-1278
	@Override
	public List<CsTbSinaRisposta> getCsTbSinaRispostaByDomandaId(BaseDTO dto) {
		return configurazioneDAO.getCsTbSinaRispostaByDomandaId((Long) dto.getObj());
	}
	@Override
	public List<SelectItem> getSsProvenienza(CeTBaseObject cet) {
		List<SelectItem> lstProvenienza = new ArrayList<SelectItem>();
		for(CsTbSsProvenienza val : configurazioneDAO.getSsProvenienza()){
			if(val.getAbilitato()) 
				lstProvenienza.add(new SelectItem(val.getId(), val.getDescrizione()));
		}
		return lstProvenienza;
	}
	
	//SISO-1275

	@Override
	public CsPaiFaseChiusuraDTO getPaiFaseChiusuraById(BaseDTO dto) {
		// TODO Auto-generated method stub
		CsPaiFaseChiusuraDTO toReturn = new CsPaiFaseChiusuraDTO();
		CsPaiFaseChiusura  res = configurazioneDAO.getPaiFaseChiusuraById((CsPaiFaseChiusuraPK)dto.getObj());
		toReturn = toDTO(res);
		return toReturn;
	}

    public static CsPaiFaseChiusuraDTO toDTO(CsPaiFaseChiusura source){
		
		if(source == null){
			return null;
		}
		CsPaiFaseChiusuraDTO target = new CsPaiFaseChiusuraDTO();
		
		target.setFase(source.getId().getFase());
		target.setMotivoChiusura(source.getId().getMotivoChiusura());
		target.setTipoPai(source.getId().getTipoPai());
//    	BeanUtils.copyProperties(source, target);
//    	
    	return target;
    }
    
    @Override
	public CsTbMotivoChiusuraPai getMotivoChiusuraPaiById(BaseDTO dto) {
		// TODO Auto-generated method stub

    	CsTbMotivoChiusuraPai  res = configurazioneDAO.getMotivoChiusuraPaiById((Long) dto.getObj());
		
		return res;
	}
    
	@Override
	public List<KeyValueDTO> searchNomiScuole(ScuoleSearchCriteria dto) {
		 List<KeyValueDTO> listaNomi = new ArrayList<KeyValueDTO>();
		 List<CsTbScuolaAnno> lstCs = new ArrayList<CsTbScuolaAnno>();
		 boolean searchByAnno = dto.getAnno()!=null && dto.getAnno()>0;
		if(searchByAnno)
			lstCs = configurazioneDAO.getScuoleByComuneAnnoTipo(dto.getComune(), dto.getTipo(), dto.getAnno());
		else
			lstCs = configurazioneDAO.getScuoleByComuneTipo(dto.getComune(), dto.getTipo());
		
		for(CsTbScuolaAnno cs: lstCs){
			String descrizione = cs.getCsTbScuola().getDescrizione();
			if(!searchByAnno)
				descrizione += " - a.s. "+cs.getCsTbAnnoScolastico().getDescrizione();
			KeyValueDTO si = new KeyValueDTO(cs.getId().getScuolaId(), descrizione);
			si.setAbilitato(cs.getAbilitato());
			listaNomi.add(si);
		}
		return listaNomi;
	}
	@Override
	public CsTbTipoOperatore getTipoOperatoreById(BaseDTO dto) {
		return configurazioneDAO.getTipoOperatore((Long)dto.getObj());
	}

	@Override
	public Boolean esisteAlmenoUnMotivoChiusura(BaseDTO dto) {
		// TODO Auto-generated method stub
		Boolean esiste = false;
		esiste = configurazioneDAO.getPaiFaseChiusuraByTipoPaiId((Long) dto.getObj());
		return esiste;
	}
	
	@Override
	public List<KeyValueDTO> findProgettiByBelfioreOrganizzazione(BaseDTO dto) {
		String belfiore = (String) dto.getObj();
		String tipoProgetto = (String) dto.getObj2();
		Long idProgettoSelected = (Long) dto.getObj3();
		return configurazioneDAO.findProgettiByBelfioreOrganizzazione(belfiore, tipoProgetto, idProgettoSelected, true);
	}
	
	@Override
	public ArFfProgetto getProgettoById(BaseDTO dto) {				
		return configurazioneDAO.getProgettoById((Long)dto.getObj());
	}
	
	@Override
	public ArFfProgettoAttivita getProgettoAttivitaById(BaseDTO dto) {				
		return configurazioneDAO.getProgettoAttivitaById((Long)dto.getObj());
	}
	
	@Override
	public ConfigurazioneFseDTO loadCampiFse(BaseDTO dto){
		Long progettoId = (Long)dto.getObj();
		return configurazioneDAO.loadCampiFse(progettoId);
	}

	@Override
	public List<VStrutturaArea> findAllStruttura(CeTBaseObject cet) {
		return configurazioneDAO.findAllStruttura();
	}

	@Override
	public List<TipoStruttura> getLstTipoStrutturaByTipoFunzione(BaseDTO dto) {
		 List<TipoStruttura> lstTipoStruttura =	configurazioneDAO.getLstTipoStrutturaByTipoFunzione((Long) dto.getObj());
		return lstTipoStruttura;
	}

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<KeyValueDTO> getListaTipoMinore(CeTBaseObject cet) throws Exception {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbTipoMinore> lst = configurazioneDAO.getListaTipoMinore();
    	for(CsTbTipoMinore v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getTipoMinore());
    		kv.setAbilitato(true);
    		lstItems.add(kv);
    	}
    	return lstItems;
	}

	@Override
	public CsTbTipoMinore getTipoMinoreById(BaseDTO dto) {
			return configurazioneDAO.getTipoMinoreById((Long) dto.getObj());
	
	}
	

	@Override
	public boolean verificaUsoArProgettoAttivita(BaseDTO dto){
		Long idOrg = (Long)dto.getObj();
		Long idProgetto = (Long)dto.getObj2();
		Long idAttivita = (Long)dto.getObj3();
		
		return configurazioneDAO.verificaUsoArProgettoAttivita(idOrg, idProgetto, idAttivita);
	}

	@Override
	public Long findIdProgettoPaiByDesc(BaseDTO dto) {
		CsTbTipoPai tp = configurazioneDAO.findIdProgettoPaiByDesc((String) dto.getObj());
		return tp!=null ? tp.getId() : null;
	}

	@Override
	public CsTbTipoPai findTipoPaiById(BaseDTO dto) {
		return configurazioneDAO.findCsTbTipoPaiById((Long)dto.getObj());
	}

	@Override
	public CsTbTipoIndirizzo getTipoIndirizzoById(BaseDTO dto) {
		return configurazioneDAO.findCsTbTipoIndirizzoById((Long)dto.getObj());
	}

	@Override
	public CsTbDurataRicLavoro findDurataRicLavoroById(BaseDTO dto) {
		return configurazioneDAO.findDurataRicLavoroById((Long)dto.getObj());
	}

	@Override
	public CsCDiarioConchi findDiarioConchi(BaseDTO dto) {
		return configurazioneDAO.getDiarioConChi((Long)dto.getObj());
	}
	
	@Override
	public List<CsCDiarioConchi> findDiarioConchisByIds(BaseDTO dto){
		return configurazioneDAO.getDiarioConChi((List<Long>) dto.getObj());
	}
	
	@Override
	public List<CsCComunita> findComunitaByDescTipo(BaseDTO dto) throws Exception {		
		return configurazioneDAO.findComunitaByDescTipo((String)dto.getObj());
	
	}
	

	/*ITER*/
	@Override
	public CsCfgItStato findStatoById(IterDTO dto) throws Exception {
		return iterDAO.findStatoById(dto.getIdStato());
	}

	@Override
	public List<CsCfgItTransizione> getTransizionesByStatoRuolo(IterDTO dto) throws Exception {
		return configurazioneDAO.findTransizionesByStatoRuolo(dto.getIdStato(), dto.getOpRuolo());
	}

	@Override
	public boolean existsTransizioneTraStati(BaseDTO dto){
		List<?> lst =  configurazioneDAO.findTransizionesByStatoDaA((Long)dto.getObj(), (Long)dto.getObj2());
		return !lst.isEmpty();
	}

	
	@Override
	public List<KeyValueDTO> getListaIterStati(CeTBaseObject cet){
		return configurazioneDAO.getListaIterStati();
	}
	
	
	/*INTERVENTI*/
	@Override
	public List<VLineaFin> findAllOrigineFinanziamenti(BaseDTO dto) {		
		return configurazioneDAO.getLineeFinanziamentoByEnte(dto.getEnteId());
	}
	
	@Override
	public List<KeyValueDTO> findAllTipiIntervento(CeTBaseObject dto) {
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		List<CsCTipoIntervento> lst = configurazioneDAO.findTipiIntervento(false);
		for(CsCTipoIntervento c : lst){
			KeyValueDTO kv = new KeyValueDTO(c.getId(), c.getDescrizione());
			kv.setAbilitato(c.getAbilitato()!=null ? c.getAbilitato().booleanValue() : false);
			lstOut.add(kv);
		}
		return lstOut;
	}
	
	@Override
	public List<KeyValueDTO> findTipiInterventoAbilitati(BaseDTO dto) {
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		List<CsCTipoIntervento> lst = configurazioneDAO.findTipiIntervento(true);
		String tipo = (String)dto.getObj();
		for(CsCTipoIntervento c : lst){
			if(StringUtils.isBlank(tipo) || tipo.equalsIgnoreCase(c.getTipo())){
				KeyValueDTO kv = new KeyValueDTO(c.getId(), c.getDescrizione());
				kv.setAbilitato(true);
				lstOut.add(kv);
			}
		}
		return lstOut;
	}

	@Override
	public List<CsCTipoIntervento> findTipiInterventoSettoreCatSoc(InterventoDTO dto) {
		List<Long> lst = dto.getLstIdCatSoc();
		if ((lst == null || lst.isEmpty()) && dto.getIdCatsoc() != null) {
			lst = new ArrayList<Long>();
			lst.add(dto.getIdCatsoc());
		}
		return configurazioneDAO.findTipiInterventoSettoreCatsoc(dto.getIdSettore(), lst);
	}
	
	@Override
	public CsCTipoIntervento getTipoInterventoById(BaseDTO dto){
		return configurazioneDAO.getTipoInterventoById((Long)dto.getObj());
	}
	
	@Override
	public List<VGerrarchiaServizi> findAllNodesTipoIntervento(CeTBaseObject cet) {
		return configurazioneDAO.findAllNodesTipoIntervento();
	}

	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	@Override
	public List<CsCTipoInterventoCustom> findTipiIntCustom(CeTBaseObject dto) {
		return configurazioneDAO.findTipiIntCustom();
	}

	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	@Override
	public List<it.webred.cs.csa.ejb.dto.rest.InterventoDTO> findTipiIntCustomConfigurazione(BaseDTO dto) {
		List<CsCTipoInterventoCustom> listTipoIntCustom = configurazioneDAO.findTipiIntCustom();
		List<it.webred.cs.csa.ejb.dto.rest.InterventoDTO> listInterventoDTO = new ArrayList<it.webred.cs.csa.ejb.dto.rest.InterventoDTO>();
		for(CsCTipoInterventoCustom c : listTipoIntCustom){
			it.webred.cs.csa.ejb.dto.rest.InterventoDTO interventoDTO = new it.webred.cs.csa.ejb.dto.rest.InterventoDTO();
			interventoDTO.setId(c.getId());
			interventoDTO.setDescrizione(c.getDescrizione());
			
			listInterventoDTO.add(interventoDTO);
		}
		return listInterventoDTO;
	}
	
	@Override
	public CsCTipoInterventoCustom findTipoInterventoCustomById(BaseDTO dto) {
		return configurazioneDAO.findTipoIntCustomById((Long) dto.getObj());
	}

	@Override
	public CsCCategoriaSociale findCatSocialeByDescrizione(BaseDTO dto) {
		return configurazioneDAO.findCatSocialeByDescrizione((String) dto.getObj());
	}
	
	@Override
	public List<KeyValueDTO> findTipiInterventoRecenti(BaseDTO dto) {
		return configurazioneDAO.getTipiInterventoRecentiList();
	}
	
	@Override
	public List<KeyValueDTO> findTipiInterventoCustomRecenti(BaseDTO dto) {
		return configurazioneDAO.getTipiInterventoCustomRecentiList();
	}
	//SISO-1162
	@Override
	public List<KeyValueDTO> findTipiInterventoInps(BaseDTO dto) {
		return configurazioneDAO.getTipiInterventoInpsList();
	}
	
	@Override
	public List<VTipiInterventoUsati> findAllInterventiRecenti(BaseDTO dto) {
		return configurazioneDAO.getInterventiRecentiList();
	}
	
	//INIZIO MOD-RL
	@Override
	public List<ArRelClassememoPresInps> findArRelClassememoPresInpbyTipoInterventoId(BaseDTO dto) { 
		return configurazioneDAO.findArRelClassememoPresInpbyTipoInterventoId((Long)dto.getObj());
	}  
	
	@Override
    @AuditSaltaValidazioneSessionID
	public CsCfgAttrUnitaMisura findAttrUnitaMisura(BaseDTO dto) {
		return configurazioneDAO.findAttrUnitaMisura((Long)dto.getObj(), (Long)dto.getObj2());
	}
	
	   //SISO-469
    @Override
    public List<VArCTariffa> findTariffe(BaseDTO dto){
    	 Long orgTitolare = (Long)dto.getObj();
    	 Long unitaMisuraId = (Long)dto.getObj2();
    	 Long intCustomId = (Long)dto.getObj3();
    	return  configurazioneDAO.findTariffe(orgTitolare, unitaMisuraId, intCustomId);
    }
    
	@Override
	public InformativaDTO findInformativa(BaseDTO dto) {
		return configurazioneDAO.findInformativa((Long)dto.getObj());
	}

   public List<ArRelIntCustomIstat> findInterventoIstatByInterventoCustom (BaseDTO dto) { 
		return configurazioneDAO.findArRelIntCustomIstatbyTipoInterventoId((Long)dto.getObj());
	 } 
   public List<ArTClasse> findInterventoIstatByCodice (BaseDTO dto) { 
		return configurazioneDAO.findArTClasseByTipoInterventoId((String)dto.getObj());
	 } 
   
   @Override
   public List<ArTClasse> findArTClasseAll (CeTBaseObject dto) { 
		return configurazioneDAO.findArTClasseAll();
	} 
	
    //SISO-1110
    public List<VServiziCustom> findAreaTInterventoById(BaseDTO bdto){
   		return  configurazioneDAO.findAreaTInterventoById((Long)bdto.getObj());  
    }
    public List<VServiziCustom> findAllServiziCustoByInterventoAndAreatId(BaseDTO bdto){
   	 return  configurazioneDAO.findAllServiziCustoByInterventoAndAreatId((Long)bdto.getObj(), (Integer)bdto.getObj2()); 
   	 
    }
 
    @SuppressWarnings("unchecked")
	public List<VServiziCustom> findDettaglioInterventobyAreaTId(BaseDTO bdto){
   	 List<String> areeId = (List<String>) bdto.getObj();
   	 return  configurazioneDAO.findDettaglioInterventobyAreaTId(areeId); 
   	 
    }

	public List<VServiziCustom> findAreaTInterventoByIdeAreaTSoggetto(BaseDTO bdto){
   	 Long interventoCustomId = (Long)bdto.getObj();
    	 List<String> areeT = (List<String>) bdto.getObj2();
    	 return  configurazioneDAO.findAreaTInterventoByIdeAreaTSoggetto(interventoCustomId,areeT); 
    	 
     }
	
	@Override
	public HashMap<Long, ErogStatoCfgDTO> findConfigIntEsegByTipoIntervento(BaseDTO bdto){
		Long tipoInterventoId = (Long) bdto.getObj();
		return configurazioneDAO.findConfigIntEsegByTipoIntervento(tipoInterventoId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CsCfgIntEsegStato> getListaIntEsegStatoByTipiStato(BaseDTO bDto) {
		List<String> obj = (List<String>) bDto.getObj();
		return configurazioneDAO.getListaIntEsegStatoByTipiStato(obj, (Long) bDto.getObj2());
	}

	@Override
	public CsTbSinaRisposta findSinaRisposta(BaseDTO dto) {
		return configurazioneDAO.findSinaRisposta((Long)dto.getObj());
	}

}