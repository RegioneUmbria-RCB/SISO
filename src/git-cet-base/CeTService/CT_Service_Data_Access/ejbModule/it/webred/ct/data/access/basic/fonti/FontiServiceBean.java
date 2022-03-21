package it.webred.ct.data.access.basic.fonti;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.fonti.dao.FontiDAO;
import it.webred.ct.data.access.basic.fonti.dto.FontiDTO;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless 
public class FontiServiceBean extends CTServiceBaseBean implements FontiService {
	@Autowired
	private FontiDAO fontiDAO;
	private static final long serialVersionUID = 1L;
	
	@Override
	public FontiDTO getDateRiferimentoFonte(FontiDataIn in) {
		
		logger.debug("getDateRiferimentoFonte "+in.getIdFonte()+" da Properties");
		return fontiDAO.getDateRifFonteProps(in);			
	}
	
	@Override
	public FontiDTO getDateRifFonteTracciaDate(FontiDataIn in) {
		
		logger.debug("getDateRiferimentoFonte "+in.getIdFonte()+" da TRACCIA_DATE");
		return fontiDAO.getDateRifFonteTracciaDate(in);
				
	}
	
}
