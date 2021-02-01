package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.StatoCartellaDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsCfgItTransizione;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableIterStepSessionBeanRemote {

	public boolean newIter(IterDTO dto) throws Exception;

	public boolean addIterStep(IterDTO dto) throws Exception;

	public CsCfgItStato findStatoById(IterDTO dto) throws Exception;

	public List<CsCfgItTransizione> getTransizionesByStatoRuolo (IterDTO dto) throws Exception;
	
	public List<CsIterStepByCasoDTO> getIterStepsByCasoDTO(BaseDTO dto) throws Exception ;

	public CsIterStepByCasoDTO getLastIterStepByCasoDTO(BaseDTO dto) throws Exception;

	public List<StatoCartellaDTO> getStatoCasoBySoggetto(BaseDTO d) throws Exception;
	
	public CsItStep getLastIterStepByCaso(IterDTO dto) throws Exception;

	public List<CsCfgItStato> getListaIterStati(CeTBaseObject cet);
	
	//SISO-1297
	public  CsIterStepByCasoDTO getLastIterStepByCasoDTOAnonimo(BaseDTO dto)throws Exception;

}
