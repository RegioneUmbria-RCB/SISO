package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Remote;

import eu.smartpeg.rilevazionepresenze.data.model.Area;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.data.model.TipoStruttura;

@Remote
public interface StruttureSessionBeanRemote {

	public List<Struttura> findAll();

	Struttura salva(Struttura struttura);

	void eliminaStruttura(Struttura struttura) throws Exception;

	void eliminaArea(Struttura struttura, Area area) throws Exception;

	public List<Area> findAllAreas();

	public String validaStruttura(Struttura struttura);

	public Struttura findStrutturaById(Long idStruttura);

	public Struttura findStrutturaByCodBelfFittizio(String codBelfioreFittizio);

	public List<Struttura> findStruttureByTipoFunzione(Long idTipoFunzione);

	public TipoStruttura findTipoStruttura(Long idTipoFunzione);
	 
}
	
