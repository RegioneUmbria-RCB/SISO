package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableDatiEsterniSoggettoSessionBeanRemote {

	public void aggiungiContenutiPerSoggetto(BaseDTO dto);

	public List<DatiEsterniSoggettoViewDTO> getDatiEsterniSoggetto(BaseDTO dto);

	public Boolean existsPrestazione(BaseDTO bDto);

}
