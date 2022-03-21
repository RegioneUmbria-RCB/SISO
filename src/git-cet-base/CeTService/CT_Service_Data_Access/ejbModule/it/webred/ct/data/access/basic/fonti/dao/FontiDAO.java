package it.webred.ct.data.access.basic.fonti.dao;

import it.webred.ct.data.access.basic.fonti.FontiDataIn;
import it.webred.ct.data.access.basic.fonti.dto.FontiDTO;

public interface FontiDAO {

	public FontiDTO getDateRifFonteProps(FontiDataIn in);
	
	public FontiDTO getDateRifFonteTracciaDate(FontiDataIn in);
	
}
