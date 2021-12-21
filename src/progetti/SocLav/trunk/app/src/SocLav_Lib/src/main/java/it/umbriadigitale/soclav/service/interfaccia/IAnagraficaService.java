package it.umbriadigitale.soclav.service.interfaccia;

import it.umbriadigitale.soclav.model.SlAnagrafica;
//import it.umbriadigitale.soclav.wsclient.EsitoOp;

public interface IAnagraficaService {

	public SlAnagrafica save(SlAnagrafica t);
	public SlAnagrafica find(Long id);
	//public EsitoOp scaricaAggiornaNucleoFamiliare(String URLWS);
	//public EsitoOp scaricaAggiornaSAP(String URLWS, String numProtINPS,  String codiceFiscale);
}
