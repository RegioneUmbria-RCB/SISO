package it.webred.cs.json.barthel;

import java.util.HashMap;
import java.util.List;

import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.json.ISchedaValutazione;

public interface ISchedaBarthel extends ISchedaValutazione{
	
	public void fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map);

	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto);
}
