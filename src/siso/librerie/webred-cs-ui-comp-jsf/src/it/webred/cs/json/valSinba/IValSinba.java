package it.webred.cs.json.valSinba;

import it.webred.cs.csa.ejb.dto.SinbaDTO;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.valSinba.ver1.tabs.DatiAffidamentoMan;
import it.webred.cs.json.valSinba.ver1.tabs.DatiDisabilitaMan;
import it.webred.cs.json.valSinba.ver1.tabs.DatiFamigliaMan;
import it.webred.cs.json.valSinba.ver1.tabs.DatiGeneraliMan;
import it.webred.cs.json.valSinba.ver1.tabs.DatiSegnalazioniMan;

import java.util.HashMap;
import java.util.List;

public interface IValSinba extends ISchedaValutazione {

	public void visualizzaPnlAggiornaPrestazioni();
	
	public DatiGeneraliMan getDatiGen();
	
	public DatiFamigliaMan getDatiFam();
	
	public DatiDisabilitaMan getDatiDis();
	
	public DatiSegnalazioniMan getDatiSegn();
	
	public DatiAffidamentoMan getDatiAff();
  
//	public void initRowList(CsDValutazione parent, CsDValutazione scheda);
	
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto);
	public ReportPdfDTO fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map, CsDSinba bean );
	
	public void valorizzaRowList(ValSinbaRowBean row);
	public void initRowList(CsDSinba scheda);
	public void init(CsDSinba val);
	public void init(CsASoggettoLAZY soggetto);
	public SinbaDTO fillExportDTO();

	public void initCopia(ISchedaValutazione bean);
	public Boolean codiciPrestazioneDaAggiornare(boolean compareSize);
	public List<String> getCodiciPrestazioniDb();
	public List<String> getCodiciPrestazioniJson();

	public boolean allineaCodiciPrestazione();

	boolean isCopia();

	public void onChangeDisabile();

	public void aggiungiComponenteFamiglia();

	
	
}
