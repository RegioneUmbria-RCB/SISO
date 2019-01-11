package it.webred.cs.json.valSinba;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.TriagePdfDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.barthel.ISchedaBarthel;
import it.webred.cs.json.isee.IIseeJson;

import java.util.HashMap;
import java.util.List;

public interface IValSinba extends ISchedaValutazione {

	
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto);
	public ReportPdfDTO fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map, List<IIseeJson> lstIsee, ISchedaBarthel barthelMan );
	
	public void valorizzaRowList(ValSinbaRowBean row);
	public void initRowList(CsDValutazione parent, CsDValutazione scheda);

}
