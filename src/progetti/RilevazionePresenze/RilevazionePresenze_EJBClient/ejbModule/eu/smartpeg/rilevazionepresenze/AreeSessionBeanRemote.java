package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Remote;

import eu.smartpeg.rilevazionepresenze.data.model.Area;

@Remote
public interface AreeSessionBeanRemote {

	public List<Area> findAll();

	Area findAreaById(Long idArea);

	Area salva(Area area);

	void elimina(Area area) throws Exception;

	String validaArea(Area area);
}