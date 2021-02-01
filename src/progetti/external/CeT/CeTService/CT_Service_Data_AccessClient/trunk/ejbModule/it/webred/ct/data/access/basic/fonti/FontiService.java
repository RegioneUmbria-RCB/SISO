package it.webred.ct.data.access.basic.fonti;

import it.webred.ct.data.access.basic.fonti.dto.FontiDTO;

import javax.ejb.Remote;

@Remote
public interface FontiService {

	public FontiDTO getDateRiferimentoFonte(FontiDataIn in);

	public FontiDTO getDateRifFonteTracciaDate(FontiDataIn in);
	
}
