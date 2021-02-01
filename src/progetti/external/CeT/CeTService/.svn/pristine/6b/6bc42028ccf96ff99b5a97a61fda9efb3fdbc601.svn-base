package it.webred.ct.data.access.basic.locazioni;

import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.locazioni.dto.RicercaLocazioniDTO;
import it.webred.ct.data.model.locazioni.LocazioniA;
import it.webred.ct.data.model.locazioni.LocazioniB;
import it.webred.ct.data.model.locazioni.LocazioniI;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface LocazioniService {
	public List<LocazioniA> getLocazioniByCF(RicercaLocazioniDTO rl) ; 
	public LocazioniB getLocazioneSoggByKey(RicercaLocazioniDTO rl) ; 
	public List<LocazioniA> getLocazioniOggByKey(RicercaLocazioniDTO rl);
	public List<LocazioniB> getInquiliniByOgg(RicercaLocazioniDTO rl) ;
	
	public List<LocazioniA> getLocazioniCivicoAllaData(RicercaCivicoDTO rc);
	public int countLocazioniCivicoAllaData(RicercaCivicoDTO rc);
	public List<LocazioniI> getImmobiliByKey(RicercaLocazioniDTO rl);
	public LocazioniB getLocazioneSoggByKeyCodFisc(RicercaLocazioniDTO rl);
	//GITOUT WS7
	public List<LocazioniA> getLocazioniByCoord(RicercaLocazioniDTO rl);
	public List<Object[]> getLocazioniByCriteria(RicercaLocazioniDTO rl);
	public List<LocazioniB> getLocazioneSoggByChiave(RicercaLocazioniDTO rl);
	public List<Object[]> getLocazioniByParams(RicercaLocazioniDTO rl);
}
