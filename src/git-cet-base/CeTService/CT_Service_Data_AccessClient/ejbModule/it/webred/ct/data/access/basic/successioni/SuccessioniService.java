package it.webred.ct.data.access.basic.successioni;

import it.webred.ct.data.access.basic.successioni.dto.RicercaSuccessioniDTO;
import it.webred.ct.data.model.successioni.SuccessioniA;
import it.webred.ct.data.model.successioni.SuccessioniB;
import it.webred.ct.data.model.successioni.SuccessioniC;
import it.webred.ct.data.model.successioni.SuccessioniD;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SuccessioniService {
	
	public List<SuccessioniA> getSuccessioniAByPk(RicercaSuccessioniDTO rs); 
	public List<SuccessioniB> getSuccessioniBByPk(RicercaSuccessioniDTO rs);
	public List<SuccessioniC> getSuccessioniCByPk(RicercaSuccessioniDTO rs);
	public List<SuccessioniD> getSuccessioniDByPk(RicercaSuccessioniDTO rs);
	public List<Object[]> getSuccessioniEreditaByParams(RicercaSuccessioniDTO rl);
	

}

