package it.webred.cs.json.OrientamentoLavoro.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;

public class OrientamentoLavoroController extends JsonExtController<OrientamentoLavoroBean> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDescrizioneScheda() {
		return "Richiesta servizio Orientamento/Inserimento al Lavoro";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.ORIENTAMENTO_LAVORO_ID;
	}

	

}
