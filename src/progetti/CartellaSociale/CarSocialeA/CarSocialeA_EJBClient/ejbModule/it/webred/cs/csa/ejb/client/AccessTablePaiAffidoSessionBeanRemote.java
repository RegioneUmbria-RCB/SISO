package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggFamigliaDTO;

import javax.ejb.Remote;

@Remote
public interface AccessTablePaiAffidoSessionBeanRemote {
	
	public CsPaiAffidoDTO findAffidoByDiarioPaiId(BaseDTO dto) throws Exception;
	
	public CsPaiAffidoDTO saveAffido(BaseDTO dto) throws Exception;
	
	public String getDescrizioneDominio(BaseDTO dto);

	public CsPaiAffidoSoggFamigliaDTO findSoggettoByComponente(Long idComponente) throws Exception;

}
