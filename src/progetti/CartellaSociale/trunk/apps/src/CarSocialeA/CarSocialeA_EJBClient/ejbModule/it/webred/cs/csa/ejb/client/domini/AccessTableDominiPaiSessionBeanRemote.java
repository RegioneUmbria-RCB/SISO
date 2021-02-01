package it.webred.cs.csa.ejb.client.domini;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDominioDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableDominiPaiSessionBeanRemote {

	public List<CsPaiAffidoDominioDTO> findByDominio(BaseDTO base);
	//SISO-981 Inizio
	public Integer findSINBADominioByCodice(String codice);
	//SISO-981 Fine
	
}
