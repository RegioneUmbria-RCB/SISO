package it.webred.ct.data.access.basic.versamenti.iciDM;

import it.webred.ct.data.access.basic.versamenti.iciDM.dto.IciDmDataIn;
import it.webred.ct.data.access.basic.versamenti.iciDM.dto.VersamentoIciDmDTO;
import it.webred.ct.data.access.basic.versamenti.iciDM.dto.ViolazioneIciDmDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface VersIciDmService {

	public List<VersamentoIciDmDTO> getListaVersamentiByCodFis(IciDmDataIn dataIn) throws VersIciDmServiceException;
	
	public List<VersamentoIciDmDTO> getListaVersamentiByCodFisAnno(IciDmDataIn dataIn) throws VersIciDmServiceException;
	
	public List<ViolazioneIciDmDTO> getListaViolazioniByCodFis(IciDmDataIn dataIn) throws VersIciDmServiceException;

	public VersamentoIciDmDTO getVersamentoById(IciDmDataIn dataIn) throws VersIciDmServiceException;

	public ViolazioneIciDmDTO getViolazioneById(IciDmDataIn dataIn) throws VersIciDmServiceException;

}
