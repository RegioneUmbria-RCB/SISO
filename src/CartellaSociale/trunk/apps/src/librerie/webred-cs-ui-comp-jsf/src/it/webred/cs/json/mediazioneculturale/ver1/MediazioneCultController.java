package it.webred.cs.json.mediazioneculturale.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;

public class MediazioneCultController extends JsonExtController<MediazioneCultBean> {

	private static final long serialVersionUID = -7634367175748820120L;

	@Override
	public String getDescrizioneScheda() {
		return "Mediazione Culturale";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID;
	}
}
