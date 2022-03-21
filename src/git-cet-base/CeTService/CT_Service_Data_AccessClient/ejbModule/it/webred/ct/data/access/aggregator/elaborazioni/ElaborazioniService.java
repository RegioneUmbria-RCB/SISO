package it.webred.ct.data.access.aggregator.elaborazioni;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ControlloClassamentoConsistenzaDTO;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ElaborazioniDataIn;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ElaborazioniDataOut;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ParamCalcoloValImponibileDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ElaborazioniService extends Serializable {

	public ElaborazioniDataOut getClassamentoCompatibile(ElaborazioniDataIn dataIn);

	public ControlloClassamentoConsistenzaDTO getControlliClassConsistenzaByDocfaUiu(RicercaOggettoDocfaDTO ro);

	//Lista per tutte le uiu estratte dai dati censuari
	public List<ControlloClassamentoConsistenzaDTO> getControlliClassamentoConsistenzaDocfaUiu(RicercaOggettoDocfaDTO ro);

	public Double calcolaValImponibile(ParamCalcoloValImponibileDTO param);
	
	//Metodi GITOUT WS4.0
	public List<ControlloClassamentoConsistenzaDTO> getDocfaDatiCensuariValori(RicercaOggettoDocfaDTO ro);
	
}
