package it.webred.cs.json.abitazione.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;

public class AbitazioneController extends JsonExtController<AbitazioneBean>  {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getDescrizioneScheda() {
		return "Abitazione";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.ABITAZIONE_ID;
	}

}
