package it.webred.ct.data.access.basic.condono;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.condono.dao.CondonoDAO;
import it.webred.ct.data.access.basic.condono.dto.RicercaCondonoDTO;
import it.webred.ct.data.model.condono.CondonoPratica;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless 
public class CondonoServiceBean extends CTServiceBaseBean implements CondonoService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CondonoDAO condonoDAO;

	@Override
	public List<CondonoPratica> getCondoni(RicercaCondonoDTO rc) {
		return condonoDAO.getCondoni(rc);
	}
	
	@Override
	public CondonoPratica getPraticaCondono(RicercaCondonoDTO rc)
	{
		return condonoDAO.getPraticaCondono(rc);
	}
}
