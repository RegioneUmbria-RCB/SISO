package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAIndirizzo;

import javax.ejb.Remote;

@Remote
public interface AccessTablePersonaCiviciSessionBeanRemote {
	
    public CsAIndirizzo getIndirizzoResidenzaByNomeCiv(BaseDTO dto);

}
