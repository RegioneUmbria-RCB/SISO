package it.webred.ct.data.access.basic.locazioni;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.locazioni.dao.LocazioniDAO;
import it.webred.ct.data.access.basic.locazioni.dto.RicercaLocazioniDTO;
import it.webred.ct.data.model.locazioni.LocazioniA;
import it.webred.ct.data.model.locazioni.LocazioniB;
import it.webred.ct.data.model.locazioni.LocazioniI;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class LocazioniServiceBean extends CTServiceBaseBean implements	LocazioniService {
	
	private static final long serialVersionUID = -5053115447139482796L;
	
	@Autowired
	private LocazioniDAO  locazioniDAO;
	@Override
	public List<LocazioniA> getLocazioniByCF(RicercaLocazioniDTO rl) {
		return locazioniDAO.getLocazioniByCF(rl);
	}
	@Override
	public List<LocazioniA> getLocazioniOggByKey(RicercaLocazioniDTO rl) {
		return locazioniDAO.getLocazioniOggByKey(rl);
	}
	@Override
	public LocazioniB getLocazioneSoggByKey(RicercaLocazioniDTO rl) {
		return locazioniDAO.getLocazioneSoggByKey(rl);
	}
	@Override
	public LocazioniB getLocazioneSoggByKeyCodFisc(RicercaLocazioniDTO rl) {
		return locazioniDAO.getLocazioneSoggByKeyCodFisc(rl);
	}
	@Override
	public List<LocazioniB> getInquiliniByOgg(RicercaLocazioniDTO rl) {
		return locazioniDAO.getInquiliniByOgg(rl);
	}
	@Override
	public List<LocazioniA> getLocazioniCivicoAllaData(RicercaCivicoDTO rc){
		return locazioniDAO.getLocazioniCivicoAllaData(rc);
	}
	@Override
	public int countLocazioniCivicoAllaData(RicercaCivicoDTO rc){
		return locazioniDAO.getLocazioniCivicoAllaData(rc).size();
	}
	@Override
	public List<LocazioniI> getImmobiliByKey(RicercaLocazioniDTO rl) {
		return locazioniDAO.getImmobiliByKey(rl);
	}
	//GITOUT WS7
	@Override
	public List<LocazioniA> getLocazioniByCoord(RicercaLocazioniDTO rl) {
		return locazioniDAO.getLocazioniByCoord(rl);
	}//-------------------------------------------------------------------------
	
	@Override
	public List<Object[]> getLocazioniByCriteria(RicercaLocazioniDTO rl) {
		return locazioniDAO.getLocazioniByCriteria(rl);
	}//-------------------------------------------------------------------------

	@Override
	public List<LocazioniB> getLocazioneSoggByChiave(RicercaLocazioniDTO rl) {
		return locazioniDAO.getLocazioneSoggByChiave(rl);
	}//-------------------------------------------------------------------------
	
	@Override
	public List<Object[]> getLocazioniByParams(RicercaLocazioniDTO rl) {
		return locazioniDAO.getLocazioniByParams(rl);
	}//-------------------------------------------------------------------------
	
}
