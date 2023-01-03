package it.webred.ss.ejb;

import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
import it.webred.ss.dao.ConfigurazioneDAO;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.client.ConfigurazioneSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.OperatoreDTO;
import it.webred.ss.ejb.dto.OrganizzazioneDTO;

/**
 * Session Bean implementation class SsSchedaSessionBean
 */
@Stateless
public class ConfigurazioneSessionBean implements ConfigurazioneSessionBeanRemote {

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
	
	/*LETTURA*/
	@Override
	public List<SsUfficio> readUffici(BaseDTO dto) {
		return dao.readUffici();
	}

	@Override
	public SsUfficio readUfficio(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readUfficio(id);
	}

	@Override
	public boolean esistonoPContatto(BaseDTO dto) {
		try{
			Long id = (Long)dto.getObj();
			List<SsRelUffPcontOrg> lst = dao.readUffPcontByOrganizzazione(id);
			return lst!=null && lst.size()>0;
		}catch(Exception e){
			logger.error("esistonoPContatto",e);
		}
		return false;
	}
	
	@Override
	public List<OperatoreDTO> getListaOperatori(BaseDTO dto){
		return dao.getListaOperatori(dto.getOrganizzazione(), (Long)dto.getObj());
	}
	
	@Override
	public List<SsOOrganizzazione> readOrganizzazioniZona(BaseDTO dto) {
		return dao.getListaOrganizzazioniZona();
	}

	@Override
	public List<SsOOrganizzazione> readOrganizzazioniAltre(BaseDTO dto) {
		return dao.getListaOrganizzazioniAltre();
	}
	
	@Override
	public SsOOrganizzazione readSsOOrganizzazioniById(BaseDTO dto) {
		return dao.readSsOrganizzazione(dto.getOrganizzazione());
	}
	
	@Override
	public List<SsRelUffPcontOrg> readUfficiOrganizzazione(BaseDTO dto) {
		return dao.readUffPcontByOrganizzazione(dto.getOrganizzazione());
	}
	
	@Override
	public OrganizzazioneDTO findOrganizzazioneDestInvio(BaseDTO dto) {
		boolean zs = (Boolean)dto.getObj();
		OrganizzazioneDTO dest = null;
		if(zs){
			SsOOrganizzazione o  = dao.findOrganizzazione(dto.getOrganizzazione());
			if(o!=null){
				dest = new OrganizzazioneDTO();
				dest.setId(o.getId());
				dest.setBelfiore(o.getCodCatastale());
				dest.setNome(o.getNome()); //Non valorizzo la ZS
			}
		}else
			dest = dao.readArOrganizzazione(dto.getOrganizzazione());
		return dest;
	}
	
	@Override
	public List<SsRelUffPcontOrg> readUffPcontByOrganizzazione(BaseDTO dto){
		return dao.readUffPcontByOrganizzazione(new Long(dto.getOrganizzazione()));
	}
	
	@Override
	public SsRelUffPcontOrg getSsRelUffPcontOrg(BaseDTO dto){
		return dao.getSsRelUffPcontOrg((SsRelUffPcontOrgPK)dto.getObj());
	}

	@Override
	public List<SsTipoScheda> readTipiScheda(BaseDTO dto) {
		return dao.readTipiScheda();
	}

	@Override
	public SsTipoScheda readTipoSchedaByTipo(BaseDTO dto) {
		String tipo = (String)dto.getObj();
		return dao.readTipoSchedaByTipo(tipo);
	}
	
	@Override
	public SsTipoScheda readTipoSchedaById(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readTipoSchedaById(id);
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<String>  readInterventiTrascodifiche(BaseDTO dto) {
		return dao.readInterventiTrascodifiche() ;
	}

}
