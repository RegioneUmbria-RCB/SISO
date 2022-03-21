package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import eu.smartpeg.rilevazionepresenze.data.model.Area;
import eu.smartpeg.rilevazionepresenze.ejb.dao.AreeDAO;

/**
 * Session Bean implementation class StruttureBean
 */
@Stateless
public class AreeSessionBean implements AreeSessionBeanRemote {

	@Autowired
	private AreeDAO areeDao;

	/**
	 * Default constructor.
	 */
	public AreeSessionBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Area> findAll() {
		return areeDao.getAree();
	}

	@Override
	public Area findAreaById(Long idArea) {
		return areeDao.findAreaById(idArea);

	}

	@Override
	public Area salva(Area area) {
		try {
			Area res = areeDao.saveArea(area);
			return res;
		} catch (Exception e) {

		}

		return area;
	}

	@Override
	public void elimina(Area area) throws Exception {
		try {
			areeDao.eliminaArea(area);
		}
		catch (Exception ex) {
			// logger.error("Errore eliminaStruttura " + e.getMessage(), e);
			throw ex;
		}
	}

	@Override
	public String validaArea(Area area) {
		String messaggio = "";
		if (area.getDescrizione().isEmpty()) {
			return "La descrizione dell'area è obbligatoria";
		}

		return messaggio;
	}
}