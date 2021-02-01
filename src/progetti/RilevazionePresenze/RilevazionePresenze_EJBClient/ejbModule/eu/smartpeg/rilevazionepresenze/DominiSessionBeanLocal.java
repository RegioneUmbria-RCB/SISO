package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Local;

import eu.smartpeg.rilevazionepresenze.data.model.Motivazione;

@Local
public interface DominiSessionBeanLocal {

	public List<Motivazione> findAllMotivazioni();

}