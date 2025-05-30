package it.webred.cs.csa.ejb.client;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Remote;

@Remote
public interface AccessTableIndirizzoSessionBeanRemote {

	public CsAIndirizzo getIndirizzoById(BaseDTO dto);

	public void saveIndirizzo(BaseDTO dto);

	public void updateIndirizzo(BaseDTO dto);

	public List<CsAIndirizzo> getIndirizziBySoggetto(BaseDTO dto);

	public void eliminaIndirizziBySoggetto(BaseDTO dto);
	
	public CsAAnaIndirizzo getIndirizzoResidenza(BaseDTO dto);

	public CsAAnaIndirizzo getIndirizzoDomicilio(BaseDTO dto);

	public List<KeyValueDTO> getListaComuniResidenza(CeTBaseObject cet);
	
	public List<KeyValueDTO> getListaNazioniResidenza(CeTBaseObject cet);

}
