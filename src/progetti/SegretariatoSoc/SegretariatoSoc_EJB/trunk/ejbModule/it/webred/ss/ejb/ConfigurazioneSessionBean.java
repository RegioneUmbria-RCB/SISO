package it.webred.ss.ejb;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ss.dao.ConfigurazioneDAO;
import it.webred.ss.dao.SsSchedaDAO;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.client.ConfigurazioneSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;

/**
 * Session Bean implementation class SsSchedaSessionBean
 */
@Stateless
public class ConfigurazioneSessionBean implements ConfigurazioneSessionBeanRemote {
	private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private ConfigurazioneDAO dao;

	protected static Logger logger = Logger.getLogger("segretariatosoc.log");

	@Override
	public void salvaUfficio(BaseDTO dto) {
		SsUfficio u = (SsUfficio) dto.getObj();
		if(u.getId()==null)
			u.setAbilitato(true);

		dao.salvaUfficio(u);
	}

	@Override
	public void salvaPuntoContatto(BaseDTO dto) {
		SsPuntoContatto pc = (SsPuntoContatto) dto.getObj();
		if(pc.getId()==null) pc.setAbilitato(true);
		dao.salvaPuntoContatto(pc);
	}

	@Override
	public void eliminaUfficio(BaseDTO dto) {
		SsUfficio u = (SsUfficio) dto.getObj();
		dao.eliminaUfficio(u);
	}

	@Override
	public List<SsOOrganizzazione> getOrganizzazioniAccesso(CeTBaseObject cet) {
		return dao.getOrganzzazioniAccesso();
	}

	@Override
	public List<SsUfficio> getUffici(CeTBaseObject cet) {
		return dao.getUffici();
	}

	@Override
	public List<SsPuntoContatto> getPuntiContatto(CeTBaseObject cet) {
		return dao.getPuntiContatto();
	}

	@Override
	public void eliminaPuntoContatto(BaseDTO dto) {
		SsPuntoContatto pc = (SsPuntoContatto) dto.getObj();
		dao.eliminaPuntoContatto(pc);
	}

	@Override
	public List<SsRelUffPcontOrg> getRelazioni(CeTBaseObject cet) {
		return dao.getRelazioni();
	}

	@Override
	public List<SsRelUffPcontOrg> verificaRelazioni(SsRelUffPcontOrg rel) {
		return dao.verificaRelazioni(rel);
	}

	@Override
	public void attivaRelazione(BaseDTO dto) {

		SsRelUffPcontOrgPK key = (SsRelUffPcontOrgPK)dto.getObj();
		SsRelUffPcontOrg rel = new SsRelUffPcontOrg();
		rel.setId(key);
		rel.setAbilitato(Boolean.TRUE);

		dao.salvaRelazione(rel);
	}
	
	@Override
	public void disattivaRelazione(BaseDTO dto) {

		SsRelUffPcontOrgPK key = (SsRelUffPcontOrgPK)dto.getObj();
		SsRelUffPcontOrg rel = new SsRelUffPcontOrg();
		rel.setId(key);
		rel.setAbilitato(Boolean.FALSE);

		dao.salvaRelazione(rel);
	}

	@Override
	public void eliminaRelazione(BaseDTO dto) {
		SsRelUffPcontOrg rel = (SsRelUffPcontOrg) dto.getObj();
		dao.eliminaRelazione(rel);
	}

	@Override
	public void gestisciAttivazioneUfficio(BaseDTO dto) {
		SsUfficio ufficio = (SsUfficio)dto.getObj();
		boolean abilitato = (Boolean)dto.getObj2();
		ufficio.setAbilitato(abilitato);
		dao.salvaUfficio(ufficio);
		dao.gestisciAttivazioneRelazioniUfficio(ufficio.getId(), abilitato);
		
	}
	
	@Override
	public void gestisciAttivazionePuntoContatto(BaseDTO dto) {
		SsPuntoContatto pc = (SsPuntoContatto)dto.getObj();
		boolean abilitato = (Boolean)dto.getObj2();
		pc.setAbilitato(abilitato);
		dao.salvaPuntoContatto(pc);
		dao.gestisciAttivazioneRelPContatto(pc.getId(), abilitato);
		
	}
}
