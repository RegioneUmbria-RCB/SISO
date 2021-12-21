package it.webred.cs.csa.ejb.client.domini;

import java.util.List;

import it.webred.cs.csa.ejb.dto.siru.SiruDominioDTO;

import javax.ejb.Remote;

@Remote
public interface AccessTableDominiSiruSessionBeanRemote {

	public SiruDominioDTO findById(String chiave, String descrizione);
	
	public SiruDominioDTO findByIdSiso(String chiave, String idSiso);
	
	public SiruDominioDTO findByDesc(String chiave, String id);
	
	public List<SiruDominioDTO> findAll(String chiave);  //SISO-850
	
}
