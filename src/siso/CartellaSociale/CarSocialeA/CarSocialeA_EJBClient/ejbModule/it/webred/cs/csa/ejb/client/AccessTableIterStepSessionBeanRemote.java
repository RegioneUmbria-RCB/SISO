package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OrganizzazioneDTO;
import it.webred.cs.csa.ejb.dto.PresaInCaricoDTO;
import it.webred.cs.csa.ejb.dto.StatoCartellaDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.model.CsItStep;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableIterStepSessionBeanRemote {

	public boolean newIter(IterDTO dto) throws Exception;

	public boolean addIterStep(IterDTO dto) throws Exception;

	public List<CsIterStepByCasoDTO> getIterStepsByCasoDTO(BaseDTO dto) throws Exception ;

	public CsIterStepByCasoDTO getLastIterStepByCasoDTO(BaseDTO dto);

	public List<StatoCartellaDTO> getStatoCasoBySoggetto(BaseDTO d) throws Exception;
	
	public PresaInCaricoDTO getLastPICByCaso(BaseDTO d);
	
	public CsItStep getLastIterStepByCaso(BaseDTO dto);
	
	//SISO-1297
	public  OrganizzazioneDTO getEnteLastIterStepByCaso(BaseDTO dto)throws Exception;
	
	public void salvaOperatoreCaso(BaseDTO dto);
	
	public void updateOperatoreCaso(BaseDTO dto);
}
