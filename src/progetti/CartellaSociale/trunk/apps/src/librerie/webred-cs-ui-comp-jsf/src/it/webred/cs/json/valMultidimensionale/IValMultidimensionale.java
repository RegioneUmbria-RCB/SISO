package it.webred.cs.json.valMultidimensionale;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.TriagePdfDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.barthel.ISchedaBarthel;
import it.webred.cs.json.isee.IIseeJson;

import java.util.HashMap;
import java.util.List;

public interface IValMultidimensionale extends ISchedaValutazione {

	
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto);
	public ReportPdfDTO fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map, List<IIseeJson> lstIsee, ISchedaBarthel barthelMan );
	public void fillTriage(String subReportPath, TriagePdfDTO pdf,List<String> listaSubreport, HashMap<String, Object> map);
	
	public void valorizzaRowList(ValMultidimensionaleRowBean row);
	public void initRowList(CsDValutazione parent, CsDValutazione scheda);
	
/*	public void loadAnagConv();
	public void loadAnagNonConv();
	public void loadAnagAltri();
	public void removeAnaConv();
	public void removeAnaNonConv();
	public void removeAnaAltri();
	public void addAnaCorr();
	
	public void onRowSelectFamAnaConv(SelectEvent event);
    public void onRowUnselectFamAnaConv(UnselectEvent event);
    public void onRowSelectFamAnaNonConv(SelectEvent event);
    public void onRowUnselectFamAnaNonConv(UnselectEvent event);
    public void onRowSelectFamAnaCorr(SelectEvent event);
    public void onRowUnselectFamAnaCorr(UnselectEvent event);
    public void onRowSelectFamAnaNonCorr(SelectEvent event);
    public void onRowUnselectFamAnaNonCorr(UnselectEvent event);
    public void onRowSelectFamAnaAltri(SelectEvent event);
    public void onRowUnselectFamAnaAltri(UnselectEvent event);
    
	public List<String> getPrestazionis();
	public List<String> getDachis();
	public List<String> getPrestDachis();
	
	public List<CsAComponente> getFamComponentes();
	public List<CsAComponente> getSelectedFamComponentes();
	
	public boolean isRemoveAnaConvButton();
	public boolean isAddAnaCorrButton();
	public boolean isRemoveAnaNonConvButton();
	public boolean isAddAnaNonCorrButton();
	public boolean isApriAnaConvButton();
	public boolean isApriAnaNonConvButton();
	public boolean isRemoveAnaAltriButton();
	public boolean isAddAnaAltriButton();
	public boolean isApriAltrifamButton();
	public List<CsAComponente> getFamAltriComponentes();
	public boolean isConvivente();
	public boolean isParente();

	
	Tooltip
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
	public List<SelectItem> getLstItemsAbComposizione();*/


}
