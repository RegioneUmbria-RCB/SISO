package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Remote;

import eu.smartpeg.rilevazionepresenze.data.model.Area;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;

@Remote
public interface StruttureSessionBeanRemote {

	 public List<Struttura> findAll();
	 Struttura salva(Struttura struttura);
	 void eliminaStruttura(Struttura struttura) throws Exception;
	 void eliminaArea(Struttura struttura, Area area) throws Exception;
	 public List<Area> findAllAreas();
	 String validaStruttura(Struttura struttura);
	Struttura findStrutturaById(Long idStruttura);
	 
}
	
