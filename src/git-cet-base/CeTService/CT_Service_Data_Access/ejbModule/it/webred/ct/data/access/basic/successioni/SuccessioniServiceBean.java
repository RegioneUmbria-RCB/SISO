package it.webred.ct.data.access.basic.successioni;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.successioni.dao.SuccessioniDAO;
import it.webred.ct.data.access.basic.successioni.dto.RicercaSuccessioniDTO;
import it.webred.ct.data.model.successioni.SuccessioniA;
import it.webred.ct.data.model.successioni.SuccessioniB;
import it.webred.ct.data.model.successioni.SuccessioniC;
import it.webred.ct.data.model.successioni.SuccessioniD;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class SuccessioniServiceBean extends CTServiceBaseBean implements SuccessioniService {
	
	private static final long serialVersionUID = -5053115447139482796L;
	
	@Autowired
	private SuccessioniDAO  successioniDAO;
	
	@Override
	public List<SuccessioniA> getSuccessioniAByPk(RicercaSuccessioniDTO rs) {
		return successioniDAO.getSuccessioniAByPk(rs);
	}

	@Override
	public List<SuccessioniB> getSuccessioniBByPk(RicercaSuccessioniDTO rs) {
		return successioniDAO.getSuccessioniBByPk(rs);
	}

	@Override
	public List<SuccessioniC> getSuccessioniCByPk(RicercaSuccessioniDTO rs) {
		return successioniDAO.getSuccessioniCByPk(rs);
	}

	@Override
	public List<SuccessioniD> getSuccessioniDByPk(RicercaSuccessioniDTO rs) {
		return successioniDAO.getSuccessioniDByPk(rs);
	}

	@Override
	public List<Object[]> getSuccessioniEreditaByParams(RicercaSuccessioniDTO rl) {
		return successioniDAO.getSuccessioniEreditaByParams(rl);
	}
	
}

