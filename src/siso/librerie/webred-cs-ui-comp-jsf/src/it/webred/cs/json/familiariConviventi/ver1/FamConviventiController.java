package it.webred.cs.json.familiariConviventi.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.controller.JsonController;
import it.webred.cs.json.controller.JsonExtController;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.stranieri.ver1.StranieriBean;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class FamConviventiController extends JsonExtController<FamConviventiBean>  {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getDescrizioneScheda() {
		return "Familiari Conviventi";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.FAMIGLIA_ID;
	}

}
