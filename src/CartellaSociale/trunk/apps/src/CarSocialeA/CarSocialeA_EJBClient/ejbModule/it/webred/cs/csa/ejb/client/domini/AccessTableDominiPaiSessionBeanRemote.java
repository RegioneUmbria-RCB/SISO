package it.webred.cs.csa.ejb.client.domini;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDominioDTO;

import javax.ejb.Remote;

@Remote
public interface AccessTableDominiPaiSessionBeanRemote {

	public List<CsPaiAffidoDominioDTO> findByDominio(BaseDTO base);
	
}
