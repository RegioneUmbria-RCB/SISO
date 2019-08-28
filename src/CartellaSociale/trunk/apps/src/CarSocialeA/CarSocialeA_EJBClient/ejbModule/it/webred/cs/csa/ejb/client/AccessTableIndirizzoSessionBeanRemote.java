package it.webred.cs.csa.ejb.client;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAIndirizzo;

import javax.ejb.Remote;

@Remote
public interface AccessTableIndirizzoSessionBeanRemote {

	public CsAIndirizzo getIndirizzoById(BaseDTO dto);

	public void saveIndirizzo(BaseDTO dto);

	public void updateIndirizzo(BaseDTO dto);

	public List<CsAIndirizzo> getIndirizziBySoggetto(BaseDTO dto);

	public void eliminaIndirizziBySoggetto(BaseDTO dto);
	
}
