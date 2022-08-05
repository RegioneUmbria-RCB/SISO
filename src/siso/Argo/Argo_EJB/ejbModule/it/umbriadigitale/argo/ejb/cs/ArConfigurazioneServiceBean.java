package it.umbriadigitale.argo.ejb.cs;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.umbriadigitale.argo.data.cs.data.ArFfFondo;
import it.umbriadigitale.argo.data.cs.data.ArFfLineafin;
import it.umbriadigitale.argo.data.cs.data.ArFfLineafinOrg;
import it.umbriadigitale.argo.data.cs.data.ArFfProgetto;
import it.umbriadigitale.argo.data.cs.data.ArFfProgettoAttivita;
import it.umbriadigitale.argo.data.cs.data.ArFfProgettoOrg;
import it.umbriadigitale.argo.data.cs.data.ArOOrganizzazione;
import it.umbriadigitale.argo.ejb.base.dao.ArConfigurazioneDAO;
import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArAttivitaDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArFondoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArFonteDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArOrganizzazioneDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArProgettoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ProgettiSearchCriteria;

@Stateless
public class ArConfigurazioneServiceBean implements ArConfigurazioneService {
	
	private final String patternFSE = "FSE: ";
	private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	protected final String INIT_DATE = "01/01/0001";
	protected final String END_DATE = "31/12/9999";
	
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
				dao.salvaProgettoOrg(po);
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
	public boolean existsFonteFinanziamento(ArFonteDTO fonte) {
		List<ArFfLineafin> lstFonti =  dao.findFonteByCodice(fonte.getCodiceMemo());
		boolean exists = false;
		if(fonte.getId()!=null && fonte.getId()>0){
			for(ArFfLineafin a : lstFonti){
				if(fonte.getId() != a.getId().longValue())
					exists = true;
			}
		}else exists = !lstFonti.isEmpty();
		return exists;
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
		dao.gestisciAbilitazioneProgetti(progettiSelezionati, Boolean.TRUE);
	}

	@Override
	public void disabilitaProgetti(List<Long> progettiSelezionati) {
		dao.gestisciAbilitazioneProgetti(progettiSelezionati, Boolean.FALSE);
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

	@Override
	public List<ArFonteDTO> getListaFonti(ProgettiSearchCriteria sc) {
		List<ArFfLineafin> lst =  dao.getListaFontiFinanziamento(sc, false);
		List<ArFonteDTO> lstOut = new ArrayList<ArFonteDTO>();
		
		for(ArFfLineafin jpa : lst) {
			ArFonteDTO dto = new ArFonteDTO();
			dto.setId(jpa.getId());
			dto.setCodiceMemo(jpa.getCodiceMemo());
			dto.setDescrizione(jpa.getDescrizione());
			dto.setAbilitato(jpa.getAbilitato()); 
			dto.setFondoId(jpa.getArFfFondo().getId());
			dto.setProgettoDefaultId(jpa.getArFfProgetto()!=null ? jpa.getArFfProgetto().getId() : null);
			dto.setDataUltimaModifica(jpa.getDtMod()!=null ? jpa.getDtMod() : jpa.getDtIns());
			dto.setUserUltimaModifica(jpa.getUsrMod()!=null ? jpa.getUsrMod() : jpa.getUserIns());
			dto.setDtInizioVal(jpa.getDataInizioVal());
			dto.setDtFineVal(jpa.getDtFineVal());
			dto.setImporto(jpa.getImporto());
			
			//Visualizzo solo quelle che sono della zona sociale in cui sto configurando (se valorizzata)
			List<ArOrganizzazioneDTO> lstOrganizzazioni = new ArrayList<ArOrganizzazioneDTO>();
			boolean orgZsEsterna=false;
			List<ArFfLineafinOrg> lstFonteOrg = dao.getListaOrganizzazioniFonte(jpa.getId());
			for(ArFfLineafinOrg porg: lstFonteOrg){
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
			
			lstOut.add(dto);
		}
		return lstOut;
	}

	@Override
	public int countFonti(ProgettiSearchCriteria sc) {
		List<ArFfLineafin> lst =  dao.getListaFontiFinanziamento(sc, true);
		return lst.size();
	}

	@Override
	public void salvaFonte(ArFonteDTO source, List<ArOrganizzazioneDTO> toRemove) {
		List<Long> orgToRem = new ArrayList<Long>();
		for(ArOrganizzazioneDTO r : toRemove)
			orgToRem.add(r.getId());
		
		boolean isNew = false;
		ArFfLineafin target = dao.findArFfLineafin(source.getId());
		if(target==null){ 
			target = new ArFfLineafin();
			isNew = true;
		}
		
		target.setId(source.getId());
		
		ArFfFondo fondo = dao.findArFfFondo(source.getFondoId());
		target.setArFfFondo(fondo);
		target.setCodiceMemo(source.getCodiceMemo());
		
		String desc = source.getDescrizione();
		target.setDescrizione(desc);
		target.setAbilitato(source.isAbilitato());

		Date dtInizioVal = null;
		Date dtFineVal = null;
		
		try {
			dtInizioVal = source.getDtInizioVal()!=null ? source.getDtInizioVal() : ddMMyyyy.parse(INIT_DATE);
			dtFineVal = source.getDtFineVal()!=null ? source.getDtFineVal() : ddMMyyyy.parse(END_DATE);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		target.setDataInizioVal(dtInizioVal);
		target.setDtFineVal(dtFineVal);
		
		ArFfProgetto progetto = dao.findArFfProgetto(source.getProgettoDefaultId());
		target.setArFfProgetto(progetto);
		target.setImporto(source.getImporto()!=null ? source.getImporto() : BigDecimal.ZERO);
		if(isNew){
			target.setUserIns(source.getUserUltimaModifica());
			target.setDtIns(source.getDataUltimaModifica());
		}else{
			target.setUsrMod(source.getUserUltimaModifica());
			target.setDtMod(source.getDataUltimaModifica());
		}
		
		//Elimino dalla lista le relazioni non più presenti
		if(target.getId()!=null && !orgToRem.isEmpty()) {
			dao.eliminaOrganizzazioniFonte(target.getId(), orgToRem);
		}
		
		target = dao.salvaFonte(target);
		
		List<ArFfLineafinOrg> lstCurrent = dao.getListaOrganizzazioniFonte(target.getId());
		//Aggiungo le nuove
		List<Long> currOrg = new ArrayList<Long>();
		for(ArFfLineafinOrg po : lstCurrent)
			currOrg.add(po.getArOrganizzazione().getId());
		
		for(ArOrganizzazioneDTO poNew : source.getLstOrganizzazioni()){
			if(!currOrg.contains(poNew.getId())){
				ArFfLineafinOrg po = new ArFfLineafinOrg();
				po.setOrganizzazioneId(poNew.getId());
				po.setLineaFinId(target.getId());
				dao.salvaFonteOrg(po);
			}
		}
	}

	@Override
	public void eliminaFonte(Long id) {
		dao.eliminaOrganizzazioniFonte(id);
		dao.eliminaFonte(id);
	}

	@Override
	public void abilitaFonte(List<Long> fontiSelezionate) {
		dao.gestisciAbilitazioneFonti(fontiSelezionate, Boolean.TRUE);	
	}
	
	@Override
	public void disabilitaFonti(List<Long> fontiSelezionate) {
		dao.gestisciAbilitazioneFonti(fontiSelezionate, Boolean.FALSE);
	}

	@Override
	public List<ArFondoDTO> getListaFondiDTO() {
		List<ArFondoDTO> lstFondi = new ArrayList<ArFondoDTO>();
		List<ArFfFondo> lst = dao.getListaArFfFondo();
		if(lst!= null ) {
			for (ArFfFondo f : lst) {
				ArFondoDTO fondo = new ArFondoDTO();
				fondo.setCodiceMemo(f.getCodiceMemo());
				fondo.setDescrizione(f.getDescrizione());
				fondo.setDataUltimaModifica(f.getDtMod()!=null ? f.getDtMod() : f.getDtIns());
				fondo.setUserUltimaModifica(f.getUsrMod()!=null ? f.getUsrMod() : f.getUserIns());
				fondo.setId(f.getId());
				fondo.setAbilitato(f.getAbilitato());
				lstFondi.add(fondo);
			}
		}
		return lstFondi;
	}

	@Override
	public List<ArProgettoDTO> getListaProgetti(List<Long> idOrganizzazioni) {
		return dao.getListaProgetti(idOrganizzazioni);
	}
}
