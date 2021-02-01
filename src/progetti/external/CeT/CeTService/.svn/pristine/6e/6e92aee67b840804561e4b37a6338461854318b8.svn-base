package it.webred.ct.data.access.basic.locazioni.dao;

import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.locazioni.LocazioniServiceException;
import it.webred.ct.data.access.basic.locazioni.dto.RicercaLocazioniDTO;
import it.webred.ct.data.model.locazioni.LocazioniA;
import it.webred.ct.data.model.locazioni.LocazioniB;
import it.webred.ct.data.model.locazioni.LocazioniI;

import java.util.List;

public interface LocazioniDAO {
	public List<LocazioniA> getLocazioniByCF(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public List<LocazioniA> getLocazioniByInquilinoCF(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public List<LocazioniA> getLocazioniOggByKey(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public LocazioniB getLocazioneSoggByKey(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public LocazioniB getLocazioneSoggByKeyCodFisc(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public List<LocazioniB> getInquiliniByOgg(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public List<LocazioniA> getLocazioniCivicoAllaData(RicercaCivicoDTO rc);
	public List<LocazioniI> getImmobiliByKey(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	//GITOUT WS7
	public List<LocazioniA> getLocazioniByCoord(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public List<Object[]> getLocazioniByCriteria(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public List<LocazioniB> getLocazioneSoggByChiave(RicercaLocazioniDTO rl) throws LocazioniServiceException;
	public List<Object[]> getLocazioniByParams(RicercaLocazioniDTO rl) throws LocazioniServiceException;
}
