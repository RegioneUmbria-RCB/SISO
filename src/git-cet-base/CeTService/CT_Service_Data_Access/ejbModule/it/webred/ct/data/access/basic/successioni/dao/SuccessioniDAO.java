package it.webred.ct.data.access.basic.successioni.dao;


import it.webred.ct.data.access.basic.successioni.SuccessioniServiceException;
import it.webred.ct.data.access.basic.successioni.dto.RicercaSuccessioniDTO;
import it.webred.ct.data.model.successioni.SuccessioniA;
import it.webred.ct.data.model.successioni.SuccessioniB;
import it.webred.ct.data.model.successioni.SuccessioniC;
import it.webred.ct.data.model.successioni.SuccessioniD;

import java.util.List;

public interface SuccessioniDAO {

	public List<SuccessioniA> getSuccessioniAByPk(RicercaSuccessioniDTO rs) throws SuccessioniServiceException;
	public List<SuccessioniB> getSuccessioniBByPk(RicercaSuccessioniDTO rs) throws SuccessioniServiceException;
	public List<SuccessioniC> getSuccessioniCByPk(RicercaSuccessioniDTO rs) throws SuccessioniServiceException;
	public List<SuccessioniD> getSuccessioniDByPk(RicercaSuccessioniDTO rs) throws SuccessioniServiceException;
	public List<Object[]> getSuccessioniEreditaByParams(RicercaSuccessioniDTO rl) throws SuccessioniServiceException;

}



