package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.*;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.Query;

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
	private IndirizzoDAO indirizzoDAO;

    public AccessTableConfigurazioneSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    public List<CsTbStatoCivile> getStatoCivile(CeTBaseObject cet) {
    	return configurazioneDAO.getStatoCivile();
    }
    
    public CsTbStatoCivile getStatoCivileByIdOrigCet(BaseDTO dto) {
    	return configurazioneDAO.getStatoCivileByIdOrigCet(dto.getEnteId(), (String) dto.getObj());
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
    
    public List<CsTbPermesso> getPermesso(CeTBaseObject cet) {
    	return configurazioneDAO.getPermesso();
    }
    
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
    
    public List<CsOOrganizzazione> getOrganizzazioniBelfiore(CeTBaseObject cet) {
    	return configurazioneDAO.getOrganizzazioniBelfiore();
    }
    
    public List<CsOOrganizzazione> getOrganizzazioniAll(CeTBaseObject cet) {
    	return configurazioneDAO.getOrganizzazioniAll();
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
	
	public void salvaSettore(BaseDTO dto) {
		CsOSettore sett = (CsOSettore) dto.getObj();
		if(sett.getCsAAnaIndirizzo() != null && sett.getCsAAnaIndirizzo().getId() == null) {
			CsAAnaIndirizzo anaInd = indirizzoDAO.saveAnaIndirizzo(sett.getCsAAnaIndirizzo());
			sett.setCsAAnaIndirizzo(anaInd);
		}
		configurazioneDAO.salvaSettore( sett );
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
	public CsOOrganizzazione getOrganizzazioneByBelfiore(BaseDTO dto) {
		return configurazioneDAO.getOrganizzazioneByBelfiore((String)dto.getObj());
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
	public CsTbTipoRapportoCon getTipoRapportoDaRelazPar(BaseDTO cet) {
		return configurazioneDAO.getTipoRapportoDaRelazPar((String) cet.getObj(), cet.getEnteId());
	}

	@Override
	public List<CsTbMapTipoRapGit2Cs> caricaMappaRelazioniParentaliEnte(CeTBaseObject cet) {
		return configurazioneDAO.caricaMappaRelazioniParentaliEnte(cet.getEnteId());
	}
	
}
