package it.webred.ct.controller.ejbclient.utilities;

import it.webred.ct.controller.ejbclient.utilities.dto.GestioneUtilitiesDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.LogFunzioniDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.InputFunzioneDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.RicercaLogFunzioniDTO;
import it.webred.ct.rulengine.controller.model.RLogFunzioni;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ControllerUtilitiesService{
	
	//Gestione del LOG
	public Long inserisciNuovoLogFunzione(GestioneUtilitiesDTO gu);

	public void updateFineLogFunzione(GestioneUtilitiesDTO gu);

	public List<LogFunzioniDTO> getListaLogFunzioni(RicercaLogFunzioniDTO ric);
	
	//Implementazione delle funzioni
	public void ricalcolaHash(InputFunzioneDTO in);
	
	public void svuotaTabella(InputFunzioneDTO in) throws SQLException;

	
}
