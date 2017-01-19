package it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz.barthel;

import java.util.HashMap;
import java.util.List;

import it.webred.cs.json.ISchedaValutazione;

public interface ISchedaBarthel extends ISchedaValutazione{
	
	public void fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map);
}
