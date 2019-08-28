package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCComunita;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Andrea
 *
 */
@Remote
public interface AccessTableComunitaSessionBeanRemote {
	
	public List<CsCComunita> findComunitaByDescTipo(BaseDTO dto) throws Exception;
}