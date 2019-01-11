package it.webred.cs.json.orientamentoistruzione.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;

public class OrientamentoIstruzioneController extends JsonExtController<OrientamentoIstruzioneBean> {

	private static final long serialVersionUID = 5069297712933782246L;

	@Override
	public String getDescrizioneScheda() {
		return "Orientamento all'istruzione / formazione";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID;
	}
}
