package it.umbriadigitale.argo.ejb.cs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.umbriadigitale.argo.data.cs.data.ArFfProgetto;
import it.umbriadigitale.argo.data.cs.data.ArFfProgettoAttivita;
import it.umbriadigitale.argo.data.cs.data.ArFfProgettoOrg;
import it.umbriadigitale.argo.data.cs.data.ArOOrganizzazione;
import it.umbriadigitale.argo.ejb.base.dao.ArConfigurazioneDAO;
import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArAttivitaDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArOrganizzazioneDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArProgettoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ProgettiSearchCriteria;

@Stateless
public class ArConfigurazioneServiceBean implements ArConfigurazioneService {
	
	private final String patternFSE = "FSE: ";

	protected static Logger logger = Logger.getLogger("argo.log");
	
	@Autowired
	private ArConfigurazioneDAO dao;

	@Override
	public int countProgetti(ProgettiSearchCriteria sc){
		List<ArFfProgetto> lista = dao.getListaProgetti(sc, true);
		return lista.size();
	}
	
	@Override
	public List<ArProgettoDTO> getListaProgetti(ProgettiSearchCriteria sc) {
		List<ArFfProgetto> lst =  dao.getListaProgetti(sc, false);
		List<ArProgettoDTO> lstOut = new ArrayList<ArProgettoDTO>();
		List<ArAttivitaDTO> lstAttivita = new ArrayList<ArAttivitaDTO>();
		
		for(ArFfProgetto jpa : lst) {
			lstAttivita = new ArrayList<ArAttivitaDTO>();
			ArProgettoDTO dto = new ArProgettoDTO();
			dto.setId(jpa.getId());
			dto.setCodiceMemo(jpa.getCodiceMemo());
			dto.setDescrizione(jpa.getDescrizione());
			dto.setAbilitato(jpa.getAbilitato());
			dto.setDataUltimaModifica(jpa.getDtMod()!=null ? jpa.getDtMod() : jpa.getDtIns());
			dto.setUserUltimaModifica(jpa.getUsrMod()!=null ? jpa.getUsrMod() : jpa.getUserIns());
			dto.setFse(jpa.getDescrizione().startsWith(patternFSE));
			
			//Visualizzo solo quelle che sono della zona sociale in cui sto configurando (se valorizzata)
			List<ArOrganizzazioneDTO> lstOrganizzazioni = new ArrayList<ArOrganizzazioneDTO>();
			boolean orgZsEsterna=false;
			List<ArFfProgettoOrg> lstProgettoOrg = dao.getListaOrganizzazioniProgetto(jpa.getId());
			for(ArFfProgettoOrg porg: lstProgettoOrg){
					ArOrganizzazioneDTO o = new ArOrganizzazioneDTO();
					
					ArOOrganizzazione org = porg.getArOrganizzazione();
					o.setId(org.getId());
					o.setDescrizione(org.getNome());
					o.setCodRouting(org.getBelfiore());
					o.setZonaSociale(org.getZonaNome());
					o.setAbilitato(org.getAbilitato());
					
					if(StringUtils.isBlank(sc.getZonaSociale()) || org.getZonaNome().equalsIgnoreCase(sc.getZonaSociale()))
						lstOrganizzazioni.add(o);
					else orgZsEsterna=true;
			 }
			dto.setLstOrganizzazioni(lstOrganizzazioni);
			dto.setAltreOrganizzazioni(orgZsEsterna);
			
			//TODO SISO-1263 Popolare lista attività
			List<ArFfProgettoAttivita> lstAttivitaJpa = dao.getListaAttivitaProgetto(jpa.getId());
			if(lstAttivitaJpa!= null ) {
				for (ArFfProgettoAttivita patt : lstAttivitaJpa) {
					lstAttivita.add(toDTO(patt,jpa));
				}
			}
						
			dto.setLstAttivita(lstAttivita);
			
			lstOut.add(dto);
		}
		return lstOut;
	}
	


	@Override
	public List<ArOrganizzazioneDTO> getListaOrganizzazioniFuoriZona(String zonaSociale) {
		List<ArOrganizzazioneDTO> lstOrganizzazioniDTO = new ArrayList<ArOrganizzazioneDTO>();
		
		List<ArOOrganizzazione> lstOrganizzazioni = dao.getListaOrganizzazioniFuoriZona(zonaSociale);
		for (ArOOrganizzazione org:lstOrganizzazioni){
			ArOrganizzazioneDTO o = this.fillOrganizzazioneDTO(org);
			lstOrganizzazioniDTO.add(o);
		}
	
		return lstOrganizzazioniDTO;
	}

	
	
	@Override
	public List<ArOrganizzazioneDTO> getListaOrganizzazioniDTO(String zonaSociale) {
		List<ArOrganizzazioneDTO> lstOrganizzazioniDTO = new ArrayList<ArOrganizzazioneDTO>();
		
		List<ArOOrganizzazione> lstOrganizzazioni = dao.getOrganizzazioni();
		boolean orgZsEsterna=false;
		for (ArOOrganizzazione org:lstOrganizzazioni){
			
			ArOrganizzazioneDTO o = this.fillOrganizzazioneDTO(org);
			
			if(StringUtils.isBlank(zonaSociale) || org.getZonaNome().equalsIgnoreCase(zonaSociale))
				lstOrganizzazioniDTO.add(o);
//			else orgZsEsterna=true;
		}
	
		return lstOrganizzazioniDTO;
	}
	
	private ArOrganizzazioneDTO fillOrganizzazioneDTO(ArOOrganizzazione org){
		ArOrganizzazioneDTO o = new ArOrganizzazioneDTO();
		o.setId(org.getId());
		o.setDescrizione(org.getNome());
		o.setCodRouting(org.getBelfiore());
		o.setZonaSociale(org.getZonaNome());
		o.setAbilitato(org.getAbilitato());
		return o;
	}
	
	@Override
	public  ArOrganizzazioneDTO getOrganizzazioneById(Long idOrganizzazione) {
		ArOrganizzazioneDTO organizzazione = null;
		ArOOrganizzazione source = dao.getOrganizzazioneById(idOrganizzazione);
		organizzazione = toDTO(source);
		return organizzazione;
	}
	
	public static ArAttivitaDTO toDTO(ArFfProgettoAttivita source, ArFfProgetto prog){
		ArAttivitaDTO target = new ArAttivitaDTO();
		BeanUtils.copyProperties(source, target);
		target.setDataUltimaModifica(source.getDtIns());
		target.setProgettoId(prog.getId());
		target.setProgettoDesc(prog.getDescrizione());
		return target;
	}
	public static ArFfProgettoAttivita toEntity(ArAttivitaDTO source,  ArFfProgetto prog){
		ArFfProgettoAttivita target = new ArFfProgettoAttivita();
		BeanUtils.copyProperties(source, target);
		target.setProgettoId(prog.getId());
		target.setDtIns(source.getDataUltimaModifica());
		target.setUsrIns(source.getUserUltimaModifica());
		return target;
	}

	public static ArOrganizzazioneDTO toDTO(ArOOrganizzazione source){
		ArOrganizzazioneDTO target = new ArOrganizzazioneDTO();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	@Override
	public void salvaProgetto(ArProgettoDTO source, List<ArOrganizzazioneDTO> toRemove) throws Exception {
		List<Long> orgToRem = new ArrayList<Long>();
		for(ArOrganizzazioneDTO r : toRemove)
			orgToRem.add(r.getId());
		
		boolean isNew = false;
		ArFfProgetto target = dao.findArFfProgetto(source.getId());
		if(target==null){ 
			target = new ArFfProgetto();
			isNew = true;
		}
		
		target.setId(source.getId());
		target.setCodiceMemo(source.getCodiceMemo());
		
		String desc = source.getDescrizione();
		if (source.isFse() && !source.getDescrizione().startsWith(patternFSE))	//se viene selezionato il check FSE va concatenato il prefisso FSE come da specifica
			desc = patternFSE + desc;
		
		if(!source.isFse() && source.getDescrizione().startsWith(patternFSE))
			desc = desc.replaceFirst(patternFSE, "");
		
		target.setDescrizione(desc);
		target.setAbilitato(source.isAbilitato());
		if(isNew){
			target.setUserIns(source.getUserUltimaModifica());
			target.setDtIns(source.getDataUltimaModifica());
		}else{
			target.setUsrMod(source.getUserUltimaModifica());
			target.setDtMod(source.getDataUltimaModifica());
		}
		
		//Elimino dalla lista le relazioni non più presenti
		if(target.getId()!=null && !orgToRem.isEmpty()) {
			dao.eliminaOrganizzazioniProgetto(target.getId(), orgToRem);
		}
		
		target = dao.salvaProgetto(target);
		
		List<ArFfProgettoOrg> lstCurrent = dao.getListaOrganizzazioniProgetto(target.getId());
		//Aggiungo le nuove
		List<Long> currOrg = new ArrayList<Long>();
		for(ArFfProgettoOrg po : lstCurrent)
			currOrg.add(po.getArOrganizzazione().getId());
		
		for(ArOrganizzazioneDTO poNew : source.getLstOrganizzazioni()){
			if(!currOrg.contains(poNew.getId())){
				ArFfProgettoOrg po = new ArFfProgettoOrg();
				//ArOOrganizzazione o = dao.getOrganizzazioneById(poNew.getId());
				po.setAbilitato(true);
				po.setOrganizzazioneId(poNew.getId());
				po.setProgettoId(target.getId());
				po.setDtIns(source.getDataUltimaModifica());
				po.setUsrIns(source.getUserUltimaModifica());
				dao.salvaProgettoOr(po);
			}
		}
	}

	@Override
	public void salvaAttivita(ArAttivitaDTO attivitaDTO) throws Exception {
		
		ArFfProgettoAttivita attivitaDaSalvare = new ArFfProgettoAttivita();
	    ArFfProgetto prog = dao.findArFfProgetto(attivitaDTO.getProgettoId());
        attivitaDaSalvare= toEntity(attivitaDTO, prog);
		attivitaDaSalvare = dao.salvaAttivita(attivitaDaSalvare);
	}

	@Override
	public void eliminaAttivita(Long attivitaId){
		dao.eliminaAttivita(attivitaId);
	}
	
	@Override
	public void eliminaProgetto(Long progettoId){
		dao.eliminaOrganizzazioniProgetto(progettoId);
		dao.eliminaProgetto(progettoId);
	}

	@Override
	public boolean existsProgetto(ArProgettoDTO progetto) {
		List<ArFfProgetto> lstProgetti = dao.findProgettoByCodiceProgetto(progetto.getCodiceMemo());
		boolean exists = false;
		if(progetto.getId()!=null && progetto.getId()>0){
			for(ArFfProgetto a : lstProgetti){
				if(progetto.getId() != a.getId().longValue())
					exists = true;
			}
		}else exists = !lstProgetti.isEmpty();
		return exists;
	}

	@Override
	public boolean existsAttivita(ArAttivitaDTO attivita) {
		List<ArFfProgettoAttivita> lstAttivita = dao.findAttivitaByCodiceProgetto(attivita.getCodice(), attivita.getProgettoId());
		boolean exists = false;
		if(attivita.getId()>0){
			for(ArFfProgettoAttivita a : lstAttivita){
				if(attivita.getId() != a.getId().longValue())
					exists = true;
			}
		}else exists = !lstAttivita.isEmpty();
		return exists;
	}

	@Override
	public void abilitaProgetti(List<Long> progettiSelezionati) {
		dao.gestisciAbilitazione(progettiSelezionati, Boolean.TRUE);
	}

	@Override
	public void disabilitaProgetti(List<Long> progettiSelezionati) {
		dao.gestisciAbilitazione(progettiSelezionati, Boolean.FALSE);
	}



	@Override
	public List<ArAttivitaDTO> getListaAttivita(Long idProgetto) {
		
		List<ArAttivitaDTO> lstAttivita = new ArrayList<ArAttivitaDTO>();
		if(idProgetto!=null){
			ArFfProgetto p = dao.findArFfProgetto(idProgetto);
			List<ArFfProgettoAttivita> lstAttivitaJpa = dao.getListaAttivitaProgetto(idProgetto);
			if(lstAttivitaJpa!= null ) {
				for (ArFfProgettoAttivita patt : lstAttivitaJpa) {
					lstAttivita.add(toDTO(patt, p));
				}
			}
		}
		return lstAttivita;
	}

}
