package it.webred.cs.json.valMultidimensionale;

import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.TriagePdfDTO;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.barthel.ISchedaBarthel;
import it.webred.cs.json.isee.IIseeJson;

public interface IValMultidimensionale extends ISchedaValutazione {

	
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto, boolean loadDatiComuni);
	public ReportPdfDTO fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map, List<IIseeJson> lstIsee, ISchedaBarthel barthelMan );
	public void fillTriage(String subReportPath, TriagePdfDTO pdf,List<String> listaSubreport, HashMap<String, Object> map);
	
	public void valorizzaRowList(ValMultidimensionaleRowBean row);
	public void initRowList(CsDValutazione parent, CsDValutazione scheda);
	
	public List<CsAComponente> getFamNonConvComponentes();
	public List<CsAComponente> getFamConvComponentes();
	public List<CsAComponente> getFamAltriComponentes();
	
	public List<CsAComponente> getDialogFamComponentes();
	public List<CsAComponente> getDialogSelectedFamComponentes();
	public void setDialogSelectedFamComponentes(List<CsAComponente> dialogSelectedFamComponentes);

/*
	public void loadAnagConv();
	public void loadAnagNonConv();
	public void loadAnagAltri();

	public void removeAnaConv();
	public void removeAnaNonConv();
	public void removeAnaAltri();
	public void addAnaCorr();
*/
    
	public List<String> getPrestazionis();
	public List<String> getDachis();
	public List<String> getPrestDachis();
	
	/*Tooltip*/
	public List<CsTbSchedaMultidim> getInfoValReteSocTooltip();
	public List<CsTbSchedaMultidim> getInfoSalFisicTooltip();
	public List<CsTbSchedaMultidim> getInfoAdAbitTooltip();
	public List<CsTbSchedaMultidim> getInfoUbAbitTooltip();
	public List<CsTbSchedaMultidim> getInfoValFamTooltip();
	
	public List<SelectItem> getLstItemsTipoMomValutazione();
	public List<SelectItem> getLstItemsSaluteFisica();
	
	public List<SelectItem> getLstItemsAbTipo();	
	public List<SelectItem> getLstItemsUbAbitazione();
	public List<SelectItem> getLstItemsAbBagno();
	public List<SelectItem> getLstItemsAbFornito();
	public List<SelectItem> getLstItemsAbElettrodomestici();
	public List<SelectItem> getLstItemsAbAltriProblemi();
	public List<SelectItem> getLstItemsAbTitolo();
	public List<SelectItem> getLstItemsAbComposizione();

}
