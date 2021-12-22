package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CatSocialeDAO;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.csa.ejb.dto.configurazione.SettoreCatSocialeDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ScuoleSearchCriteria;
import it.webred.cs.csa.ejb.dto.pai.base.CsPaiFaseChiusuraDTO;
import it.webred.cs.csa.ejb.dto.siru.CampoFseDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.data.DataModelCostanti.CampiFse;
import it.webred.cs.data.model.ArBiInviante;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsCDiarioDove;
import it.webred.cs.data.model.CsCTipoColloquio;
import it.webred.cs.data.model.CsCfgParametri;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsPaiFaseChiusura;
import it.webred.cs.data.model.CsPaiFaseChiusuraPK;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsRelSettoreStruttura;
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
import it.webred.cs.data.model.VStrutturaArea;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.interceptor.ExcludeDefaultInterceptors;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class AccessTableDataStatoCivileSessionBean
 */
@Stateless
public class AccessTableConfigurazioneSessionBean extends CarSocialeBaseSessionBean implements AccessTableConfigurazioneSessionBeanRemote {


	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDAO;
	
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
      
    public List<CsOOrganizzazione> getOrganizzazioni(CeTBaseObject cet) {
    	return configurazioneDAO.getOrganizzazioni();
    }
    
    public List<CsOOrganizzazione> getOrganizzazioniAccesso(CeTBaseObject cet) {
    	return configurazioneDAO.getOrganizzazioniAccesso();
    }
    
    public List<CsOOrganizzazione> getOrganizzazioniAll(CeTBaseObject cet) {
    	return configurazioneDAO.getOrganizzazioniAll();
    }
    
    
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
   public CsOOrganizzazione getOrganizzazioneCapofila(CeTBaseObject cet) {
   	return configurazioneDAO.getOrganizzazioneCapofila();
   }
    
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
   public List<CsTbTitoloStudio> getTbTitoloStudioAbilitato(BaseDTO dto) {
   	return configurazioneDAO.getTitoliStudio(Boolean.TRUE);
   }
    
	public void salvaOrganizzazione(BaseDTO dto) {
		configurazioneDAO.salvaOrganizzazione((CsOOrganizzazione) dto.getObj());
	}
	
	public void updateOrganizzazione(BaseDTO dto) {
		configurazioneDAO.updateOrganizzazione((CsOOrganizzazione) dto.getObj());
	}
	
	public void eliminaOrganizzazione(BaseDTO dto) {
		configurazioneDAO.eliminaOrganizzazione((Long) dto.getObj());
	}
    
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(BaseDTO dto) {
		return configurazioneDAO.findSettoreBASICByOrganizzazione((Long) dto.getObj());
	}
		
	@Override
	public List<SettoreCatSocialeDTO> findSettoreDTOByOrganizzazione(BaseDTO dto){
		List<SettoreCatSocialeDTO> lst = new ArrayList<SettoreCatSocialeDTO>();
		List<CsOSettoreBASIC> lstSettori = findSettoreBASICByOrganizzazione(dto);
		for(CsOSettoreBASIC s : lstSettori){
			SettoreCatSocialeDTO out = new SettoreCatSocialeDTO();
			out.setSettore(s);
			
			List<CsCCategoriaSociale> lstCat = new ArrayList<CsCCategoriaSociale>();
			List<CsRelSettoreCatsoc> rels = catSocialeDao.findRelSettoreCatsocBySettore(s.getId());
			for(CsRelSettoreCatsoc rel : rels)
				lstCat.add(rel.getCsCCategoriaSociale());
			out.setLstCatSociale(lstCat);
			lst.add(out);
		}
		return lst;
	}
	
	public void salvaSettore(BaseDTO dto) {
		CsOSettore sett = (CsOSettore) dto.getObj();
		if(sett.getCsAAnaIndirizzo() != null && sett.getCsAAnaIndirizzo().getId() == null) {
			CsAAnaIndirizzo anaInd = indirizzoDAO.saveAnaIndirizzo(sett.getCsAAnaIndirizzo());
			sett.setCsAAnaIndirizzo(anaInd);
		}
		configurazioneDAO.salvaSettore( sett );
		
		//Aggancio le categorie sociali
		for(String idCat : (List<String>)dto.getObj2()){
			dto.setObj(idCat);
			catSocialeDao.salvaRelSettoreCatsoc(sett.getId(), new Long(idCat), dto.getUserId());
		}
	}
	
	public void updateSettore(BaseDTO dto) {
	    configurazioneDAO.updateSettore(dto.getObj());
	}
	
	public void eliminaSettore(BaseDTO dto) {
		Long idSettore=null;
		if(dto.getObj() instanceof CsOSettore)
		  idSettore = ((CsOSettore) dto.getObj()).getId();
		else if(dto.getObj() instanceof CsOSettoreBASIC)
		  idSettore = ((CsOSettoreBASIC) dto.getObj()).getId();	
		
		configurazioneDAO.eliminaSettore(idSettore);
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
	public List<CsOOrganizzazione> getOrganizzazioniByCodCatastale(BaseDTO dto) {
		return configurazioneDAO.getOrganizzazioneByCodCatastale((String)dto.getObj());
	}
	
	@Override
	public CsOOrganizzazione getOrganizzazioneByCodFittizio(BaseDTO dto) {
		return configurazioneDAO.getOrganizzazioneByCodFittizio((String)dto.getObj());
	}

	@Override
	public List<CsOSettore> getSettoreAll(CeTBaseObject cet) {
		return configurazioneDAO.getSettoreAll();
	}
	
	@Override
	public List<CsOSettore> getSettoreRiunione(CeTBaseObject cet) {
		return configurazioneDAO.getSettoreRiunione();
	}

	@Override
	public List<CsOSettore> getSettoriDatiSociali(CeTBaseObject cet) {
		return configurazioneDAO.getSettoriDatiSociali();
	}
	
	@Override
	public CsOSettore getSettoreById(BaseDTO dto) {
		if(dto.getObj()!=null && (Long)dto.getObj()!=0)
			return configurazioneDAO.getSettoreById((Long)dto.getObj());
		else
			return null;
	}

	@Override
	public List<CsTbCittadinanzaAcq> getCittadinanzeAcquisite(CeTBaseObject cet) {
		return configurazioneDAO.getCittadinanzeAcquisite();
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
	public List<CsTbMicroAttivita> getMicroAttivita(CeTBaseObject cet) {
    	return configurazioneDAO.getMicroAttivita();
    }
	
	@Override
	public CsTbMicroAttivita getMicroAttivitaById(BaseDTO dto) {
		return configurazioneDAO.getMicroAttivitaById((Long) dto.getObj());
	}

	@Override
	public List<CsTbMicroAttivita> getMicroAttivitaByIdMacroAttivita(BaseDTO dto) {
    	return configurazioneDAO.getMicroAttivitaByIdMacroAttivita((Long) dto.getObj());
    }

	
	@Override
	public CsTbMacroAttivita getMacroAttivitaById(BaseDTO dto) {
		return configurazioneDAO.getMacroAttivitaById((Long) dto.getObj());
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
	public CsOOrganizzazione getOrganizzazioneById(BaseDTO dto) {
		return configurazioneDAO.getOrganizzazioneById((Long)dto.getObj());
	}
	
	@Override
	public List<CsTbTipoAlert> getTipiAlert(CeTBaseObject cet){
		return configurazioneDAO.getTipiAlert();
	}
	
	@Override
	public List<CsTbFormaGiuridica> getFormeGiuridiche(CeTBaseObject cet){
		return configurazioneDAO.getFormeGiuridiche();
	}

	@Override
	public String findCodFormProgetto(BaseDTO dto) {
		return configurazioneDAO.findCodFormProgetto((String)dto.getObj(), (Long)dto.getObj2(), (Long)dto.getObj3(), (Long)dto.getObj4());
	}
	
	@Override
	public List<CsCTipoColloquio> getTipoColloquios(BaseDTO dto) throws Exception {
		List<CsCTipoColloquio> tipoColloquios = configurazioneDAO.findAllTipoColloquios();
		return tipoColloquios;
	}

	@Override
	public List<CsCDiarioDove> getDiarioDoves(BaseDTO dto) {
		List<CsCDiarioDove> diarioDoves = configurazioneDAO.findAllDiarioDoves();
		return diarioDoves;
	}

	@Override
	public List<CsCDiarioConchi> getDiarioConchis(BaseDTO dto) {
		List<CsCDiarioConchi> diarioConchis = configurazioneDAO.findAllDiarioConchis();
		return diarioConchis;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<CsTbTipoAbitazione> getListaTipoAbitazione(BaseDTO dto){
		return configurazioneDAO.getListaTipoAbitazione();
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<CsTbAbitTitoloGodimento> getListaTitoloGod(BaseDTO dto){
		return configurazioneDAO.getListaTitoloGod();
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
	public List<CsTbAbitGestProprietario> getListaGestProprietario(BaseDTO dto) {
		return configurazioneDAO.getListaGestProprietario();
	}

	//SISO-812
	@Override
	public List<Boolean> getFlagNascondiInformazioniFlag(BaseDTO dto) {
		return configurazioneDAO.getFlagNascondiInformazioniFlagByCasoId((Long) dto.getObj());
		
	}
	//SISO-1172
	public List<KeyValueDTO> getLstMotivoChiusuraPai(BaseDTO dto) {
		List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
		List<CsTbMotivoChiusuraPai> lst = configurazioneDAO.getMotiviChiusuraPai((Long) dto.getObj());
    	for(CsTbMotivoChiusuraPai v : lst){
    		KeyValueDTO kv = new KeyValueDTO(v.getId(), v.getDescrizione());
    		kv.setAbilitato(v.isAbilitato());
    		lstItems.add(kv);
    	}
    	return lstItems;
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
	public CsTbTitoloStudio getTitoloStudioByDescrizione(BaseDTO dto) {
		return configurazioneDAO.getTitoloStudioByDescrizione((String)dto.getObj());
	}

	@Override
	public CsTbCondLavoro getCondLavoroByDescrizione(BaseDTO dto) {
		return configurazioneDAO.getCondLavoroByDescrizione((String)dto.getObj());
	}

	@Override
	public List<KeyValueDTO> getStruttureTribunale(CeTBaseObject bo) {
		return configurazioneDAO.getTbItems("CS_TB_TRIB_STRUTTURA");
	}
	
	@Override
	public List<CsTbTipoPai> findListaProgettiPai(CeTBaseObject bo){
		return configurazioneDAO.findListaProgettiPai();
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
	public List<CsTbSecondoLivello> getListaSecondoLivello(CeTBaseObject cet) {
		return configurazioneDAO.getTipiListaSecondoLivello();
	}

	
	@Override
	public String findCodiceSinbaMotivoChiusura(BaseDTO b) {
		return configurazioneDAO.findCodiceSinbaMotivoChiusura((String)b.getObj());
	}
	
	//#ROMACAPITALE inizio
	@Override
	public List<Long> findIdSettoriByInterventoISTATandInterventoCustom(BaseDTO dto) {
		return configurazioneDAO.findIdSettoriByInterventoISTATandInterventoCustom((Long) dto.getObj(), (Long)dto.getObj2());
	}	

	@Override
	public List<Long> findIdSettoriByInterventoCustom(BaseDTO dto) {
		return configurazioneDAO.findIdSettoriByInterventoCustom((Long) dto.getObj());
	}

	@Override
	public List<CsOSettore> findSettoriById(List<Long> lst) {		
		return configurazioneDAO.findSettoriById(lst);
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
	public List<ArFfProgetto> findProgettiByBelfioreOrganizzazione(BaseDTO dto) {
		String belfiore = (String) dto.getObj();
		String tipoProgetto = (String) dto.getObj2();
		return configurazioneDAO.findProgettiByBelfioreOrganizzazione(belfiore, tipoProgetto);
	}
	
	@Override
	public ArFfProgetto getProgettiById(BaseDTO dto) {				
		return configurazioneDAO.getProgettoById((Long)dto.getObj());
	}
	
	@Override
	public ArFfProgettoAttivita getProgettoAttivitaById(BaseDTO dto) {				
		return configurazioneDAO.getProgettoAttivitaById((Long)dto.getObj());
	}
	
	@Override
	public ConfigurazioneFseDTO loadCampiFse(BaseDTO dto){
		HashMap<String, CampoFseDTO> mappa = null;
		ConfigurazioneFseDTO conf = null;
		Long progettoId = (Long)dto.getObj();
		
		if(progettoId!=null){
			mappa = configurazioneDAO.loadCampiFseByIdProgetto(progettoId);
			
			conf = new ConfigurazioneFseDTO();
			conf.setAzRagioneSociale(setCampoFse(mappa, CampiFse.AZIENDA_RAGIONE_SOCIALE));
			conf.setAzCf(setCampoFse(mappa, CampiFse.AZIENDA_CF));
			conf.setAzPi(setCampoFse(mappa, CampiFse.AZIENDA_PI));
			conf.setAzVia(setCampoFse(mappa, CampiFse.AZIENDA_VIA));
			conf.setAzComune(setCampoFse(mappa, CampiFse.AZIENDA_COMUNE));
			conf.setAzCodAteco(setCampoFse(mappa, CampiFse.AZIENDA_COD_ATECO));
			conf.setAzFormaGiuridica(setCampoFse(mappa, CampiFse.AZIENDA_FORMA_GIURIDICA));
			conf.setAzDimensione(setCampoFse(mappa, CampiFse.AZIENDA_DIMENSIONE));
			
			conf.setLavoroOrario(setCampoFse(mappa, CampiFse.LAVORO_ORARIO));
			conf.setLavoroTipo(setCampoFse(mappa, CampiFse.LAVORO_TIPO));
			
			conf.setPagIban(setCampoFse(mappa, CampiFse.PAG_IBAN));
			conf.setPagResDom(setCampoFse(mappa, CampiFse.PAG_RES_DOM));
			
			conf.setAnnoTitoloStudio(setCampoFse(mappa, CampiFse.ANNO_TITOLO_STUDIO));
			conf.setInattivoAltroCorso(setCampoFse(mappa, CampiFse.INATTIVO_ALTRO_CORSO));
			conf.setDurataRicercaLavoro(setCampoFse(mappa, CampiFse.DURATA_RICERCA_LAVORO));
			
			conf.setDataSottoscrizione(setCampoFse(mappa, CampiFse.DATA_SOTTOSCRIZIONE));
			conf.setSoggettoAttuatore(setCampoFse(mappa, CampiFse.SOGGETTO_ATTUATORE));
		}
		return conf;
	}
	
	private CampoFseDTO setCampoFse(HashMap<String, CampoFseDTO> mappa, String chiave){
		if(mappa.get(chiave)!=null)
			return mappa.get(chiave);
		else{
			CampoFseDTO empty = new CampoFseDTO();
			empty.setAbilitato(false);
			empty.setObbligatorio(false);
			return empty;
		}
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
	public CsRelSettoreStruttura findSettoreByIdStruttura(BaseDTO dto) {
		return configurazioneDAO.findSettoreByIdStruttura((Long)dto.getObj());
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
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsOSettore findSettoreByRelStruttura(BaseDTO dto) {
		return configurazioneDAO.findSettoreByRelStruttura((Long)dto.getObj());
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

}
