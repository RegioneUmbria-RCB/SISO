package it.webred.cs.json.orientamentoistruzione;

import it.webred.cs.json.ISchedaValutazione;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.model.SelectItem;

public interface IOrientamentoIstruzione extends ISchedaValutazione {
	void preValorizzaDati(BigDecimal idTitoloStudio, List<SelectItem> lstTitoliStudio, String linguaItaLivello);
}
