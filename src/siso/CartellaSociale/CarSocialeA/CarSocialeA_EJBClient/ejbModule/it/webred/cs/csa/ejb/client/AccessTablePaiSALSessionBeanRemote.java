package it.webred.cs.csa.ejb.client;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.CsPaiSalDTO; 

@Remote
public interface AccessTablePaiSALSessionBeanRemote {

	public CsPaiSalDTO saveSAL(BaseDTO dto);
	public CsPaiSalDTO findSalByDiarioPaiId(BaseDTO dto);
}
