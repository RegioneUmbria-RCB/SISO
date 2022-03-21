package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Remote;

import eu.smartpeg.rilevazionepresenze.data.model.Motivazione;

@Remote
public interface DominiSessionBeanRemote {
	
	public List<Motivazione> findAllMotivazioni();

}
