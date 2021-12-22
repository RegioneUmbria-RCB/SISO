package it.webred.cs.json.intermediazione.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;

public class IntermediazioneController extends JsonExtController<IntermediazioneBean> {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getDescrizioneScheda() {
		return "Intermediazione Abitativa";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.INTERMEDIAZIONE_AB_ID;
	}
	

}
