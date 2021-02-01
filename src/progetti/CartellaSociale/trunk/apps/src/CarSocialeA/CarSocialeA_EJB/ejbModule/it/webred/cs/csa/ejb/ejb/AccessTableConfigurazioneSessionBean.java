package it.webred.cs.csa.ejb.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CatSocialeDAO;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.csa.ejb.dto.configurazione.SettoreCatSocialeDTO;
import it.webred.cs.data.model.ArBiInviante;
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
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsTbAbitGestProprietario;
import it.webred.cs.data.model.CsTbAbitTitoloGodimento;
import it.webred.cs.data.model.CsTbAssenzaPermesso;
import it.webred.cs.data.model.CsTbBuono;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbContatto;
import it.webred.cs.data.model.CsTbDisabEnte;
import it.webred.cs.data.model.CsTbDisabGravita;
import it.webred.cs.data.model.CsTbDisabTipologia;
import it.webred.cs.data.model.CsTbDisponibilita;
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
import it.webred.cs.data.model.CsTbScuola;
import it.webred.cs.data.model.CsTbSecondoLivello;
import it.webred.cs.data.model.CsTbServComunita;
import it.webred.cs.data.model.CsTbServLuogoStr;
import it.webred.cs.data.model.CsTbServResRetta;
import it.webred.cs.data.model.CsTbServSemiresRetta;
import it.webred.cs.data.model.CsTbSettoreImpiego;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.cs.data.model.CsTbSinaRisposta;
import it.webred.cs.data.model.CsTbSottocartellaDoc;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbStatus;
import it.webred.cs.data.model.CsTbStesuraRelazioniPer;
import it.webred.cs.data.model.CsTbTipoAbitazione;
import it.webred.cs.data.model.CsTbTipoAlert;
import it.webred.cs.data.model.CsTbTipoCirc4;
import it.webred.cs.data.model.CsTbTipoComunita;
import it.webred.cs.data.model.CsTbTipoContratto;
import it.webred.cs.data.model.CsTbTipoContributo;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.CsTbTipoIndirizzo;
import it.webred.cs.data.model.CsTbTipoIsee;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.data.model.CsTbTipoPai;
import it.webred.cs.data.model.CsTbTipoProgetto;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.data.model.CsTbTipoRetta;
import it.webred.cs.data.model.CsTbTipoRientriFami;
import it.webred.cs.data.model.CsTbTipoScuola;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.data.model.CsTbTribStruttura;
import it.webred.cs.data.model.CsTbTutela;
import it.webred.cs.data.model.CsTbUnitaMisura;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

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
    public List<CsTbStatoCivile> getStatoCivile(CeTBaseObject cet) {
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
    public List<CsTbStatus> getStatus(CeTBaseObject cet) {
    	return configurazioneDAO.getStatus();
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
    
    public List<CsTbProblematica> getProblematiche(CeTBaseObject cet) {
    	return configurazioneDAO.getProblematiche();
    }
    
    public CsTbProblematica getProblematicaById(BaseDTO dto) {
    	return configurazioneDAO.getProblematicaById((Long) dto.getObj());
    }
    
    public List<CsTbStesuraRelazioniPer> getStesuraRelazioniPer(CeTBaseObject cet) {
    	return configurazioneDAO.getStesuraRelazioniPer();
    }
    
    public List<CsTbTitoloStudio> getTitoliStudio(CeTBaseObject cet) {
    	return configurazioneDAO.getTitoliStudio();
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
    public List<CsTbTipoContratto> getTipoContratto(CeTBaseObject cet) {
    	return configurazioneDAO.getTipoContratto();
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
   	return configurazioneDAO.getTitoloStudioAbilitato();
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
    
	public List<CsTbTipoPai> getTipoPai(CeTBaseObject cet) {
		return configurazioneDAO.getTipoPai();
    }
    
	public List<CsTbMotivoChiusuraPai> getMotivoChiusuraPai(CeTBaseObject cet) {
		return configurazioneDAO.getMotivoChiusuraPai();
    }
    
	public List<CsTbContatto> getContatti(CeTBaseObject cet) {
    	return configurazioneDAO.getContatti();
    }
	
	public List<CsTbMacroSegnal> getMacroSegnalazioni(CeTBaseObject cet) {
    	return configurazioneDAO.getMacroSegnalazioni();
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
	
	public List<CsTbMotivoSegnal> getMotivoSegnalazioni(CeTBaseObject cet) {
    	return configurazioneDAO.getMotivoSegnalazioni();
    }
	
	public CsTbMotivoSegnal getMotivoSegnalazioneById(BaseDTO dto) {
		return configurazioneDAO.getMotivoSegnalazioneById((Long) dto.getObj());
	}
	
	public List<CsTbDisabEnte> getDisabEnte(CeTBaseObject cet) {
    	return configurazioneDAO.getDisabEnte();
    }
	
	public List<CsTbDisabGravita> getDisabGravita(CeTBaseObject cet) {
    	return configurazioneDAO.getDisabGravita();
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
	public List<CsTbInterventiUOL> getinterventiUOL(CeTBaseObject cet) {
		return configurazioneDAO.getInterventiUOL();
	}
	
	@Override
    public CsTbInterventiUOL getInterventiUOLById(BaseDTO dto) {
    	return configurazioneDAO.getInterventiUOLById((Long) dto.getObj());
    }
	
	@Override
	public List<CsTbTipoCirc4> getTipiCirc4(CeTBaseObject cet) {
		return configurazioneDAO.getTipiCirc4();
	}
	
	@Override
    public CsTbTipoCirc4 getTipoCirc4ById(BaseDTO dto) {
    	return configurazioneDAO.getTipoCirc4ById((Long) dto.getObj());
    }
	
	@Override
	public List<CsTbTipoProgetto> getTipiProgetto(CeTBaseObject cet) {
		return configurazioneDAO.getTipiProgetto();
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
	public List<CsTbScuola> getScuole(CeTBaseObject cet) {
		return configurazioneDAO.getScuole();
	}
	
	@Override
	public List<String> getComuniScuole(CeTBaseObject dto) {
		return configurazioneDAO.getComuniScuole();
	}
	
	@Override
	public List<CsTbScuola> getScuoleByComuneTipo(BaseDTO dto) {
		return configurazioneDAO.getScuoleByComuneTipo((String)dto.getObj3(), (Long)dto.getObj2());
	}
	
	@Override
	public List<CsTbScuola> getScuoleByComuneAnnoTipo(BaseDTO dto) {
		return configurazioneDAO.getScuoleByComuneAnnoTipo((String)dto.getObj(), (Long)dto.getObj2(),(String)dto.getObj3());
	}
	
	@Override
	public List<CsTbTipoScuola> getTipoScuole(CeTBaseObject cet) {
		return configurazioneDAO.getTipoScuole();
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
		String[] codici = {dto.getObj().toString()};
		List<ArTbPrestazioniInps> lst = configurazioneDAO.findArTbPrestazioniInpsByCodice(codici);
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
	public List<CsTbMotivoChiusuraPai> getLstMotivoChiusuraPai(BaseDTO dto) {
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
	public CsTbTitoloStudio getTitoloStudioByDescrizione(BaseDTO dto) {
		return configurazioneDAO.getTitoloStudioByDescrizione((String)dto.getObj());
	}

	@Override
	public CsTbCondLavoro getCondLavoroByDescrizione(BaseDTO dto) {
		return configurazioneDAO.getCondLavoroByDescrizione((String)dto.getObj());
	}

	@Override
	public List<CsTbTribStruttura> getStruttureTribunale(CeTBaseObject bo) {
		return configurazioneDAO.getStruttureTribunale();
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
		return configurazioneDAO.findCodiceDocumentoGed((Long)dto.getObj(), (String)dto.getObj2());
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
		
		String query = this.createQueryUnitaMisura((Long)dto.getObj(), (Long)dto.getObj2());
		
		List<CsTbUnitaMisura> listaUnitaMisura = configurazioneDAO.getUnitaMisuraByIdInterventi(query);
      
		return listaUnitaMisura;
		
	}
	
    protected  String createQueryUnitaMisura(Long idTipoInterventoIstat, Long idTipoInterventoCustom) {
    	String sql = "select um "+
				"from CsTbUnitaMisura um, CsCIntervUm interventi " +
				"where  um.id = interventi.idUnitaMisura ";
    	
	sql+= idTipoInterventoIstat!=null ? " and interventi.idInterventoIstat =  "+ idTipoInterventoIstat : " and interventi.idInterventoIstat is null ";
	sql+= idTipoInterventoCustom!=null ? " and interventi.idInterventoCustom =  "+ idTipoInterventoCustom : " and interventi.idInterventoCustom is null";		

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
	
}
