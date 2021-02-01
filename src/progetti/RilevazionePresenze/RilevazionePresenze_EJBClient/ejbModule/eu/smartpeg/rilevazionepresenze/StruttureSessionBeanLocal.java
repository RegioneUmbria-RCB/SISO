package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Local;

import eu.smartpeg.rilevazionepresenze.data.model.Struttura;

@Local
public interface StruttureSessionBeanLocal {
	
	public List<Struttura> findAll();
	
}
