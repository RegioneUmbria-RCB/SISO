package it.webred.ct.data.access.basic.bolliVeicoli;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.bolliVeicoli.dao.BolliVeicoliDAO;
import it.webred.ct.data.access.basic.bolliVeicoli.dto.RicercaBolliVeicoliDTO;
import it.webred.ct.data.model.bolliVeicoli.BolloVeicolo;

@Stateless
public class BolliVeicoliServiceBean extends CTServiceBaseBean implements BolliVeicoliService {

	private static final long serialVersionUID = -7380340298536208665L;
	
	@Autowired
	private BolliVeicoliDAO bolliVeicoliDAO;
	
	@Override
	public List<BolloVeicolo> getBolliVeicoliByCFPI(RicercaBolliVeicoliDTO rbv) {

		List<BolloVeicolo> listaBolliVeicoli = new ArrayList<BolloVeicolo>();
		
		if (rbv != null && rbv.getCodiceFiscalePartitaIva() != null && !rbv.getCodiceFiscalePartitaIva().trim().equalsIgnoreCase(""))
			listaBolliVeicoli = bolliVeicoliDAO.getBolliVeicoliByCFPI(rbv);

		return listaBolliVeicoli;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<BolloVeicolo> getBolliVeicoliByCriteria(RicercaBolliVeicoliDTO rbv){
		return bolliVeicoliDAO.getBolliVeicoliByCriteria( rbv );
	}//-------------------------------------------------------------------------
	
	
}
