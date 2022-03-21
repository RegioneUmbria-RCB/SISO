package it.webred.cs.json.stranieri.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;

public class StranieriController extends JsonExtController<StranieriBean> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDescrizioneScheda() {
		return "Presenza in Italia (per persone di origine straniera)";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.STRANIERI_ID;
	}
	
}
