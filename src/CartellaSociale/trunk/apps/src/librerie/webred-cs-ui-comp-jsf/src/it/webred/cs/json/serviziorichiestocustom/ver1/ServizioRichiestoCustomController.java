package it.webred.cs.json.serviziorichiestocustom.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;
import it.webred.cs.json.mediazioneculturale.ver1.MediazioneCultBean;

public class ServizioRichiestoCustomController extends JsonExtController<ServizioRichiestoCustomBean> {
	private static final long serialVersionUID = 3253586155368295644L;

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.RICHIESTA_SERVIZIO_ID;
	}

	@Override
	public String getDescrizioneScheda() {
		return "Richiesta servizio";
	}

}
