package it.webred.cs.csa.web.manbean.listacasi;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogInterventoRowBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.FiltroCasi;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.interfaces.IListaCasi;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.utilities.CommonUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class ListaCasiBean extends CsUiCompBaseBean implements IListaCasi{

	private String widgetVar = "listaCasiVar";
	private LazyListaCasiModel lazyListaCasiModel;
	private DatiCasoBean selectedCaso;
	private List<SelectItem> listaStati;
	private List<SelectItem> listaOperatori;
	private List<SelectItem> listaCondLavoro;
	private List<SelectItem> listaTitStudio;
	private boolean renderTipoOperatore=false;
	
	
	//SISO-641
	String sortField;
	SortOrder sortOrder; 
	Map filters;
		
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;

	public ListaCasiBean() {
		lazyListaCasiModel = new LazyListaCasiModel();
		//SISO-641
    	this.sortField = sortField;
    	this.sortOrder = sortOrder;
    	this.filters = filters;
	}
	
	@Override
	public IterDialogMan getIterDialogMan() {
		return iterDialogMan;
	}

	public void setIterDialogMan(IterDialogMan iterDialogMan) {
		this.iterDialogMan = iterDialogMan;
	}

	@Override
	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}
	
	public DatiCasoBean getSelectedCaso() {
		return selectedCaso;
	}

	public void setSelectedCaso(DatiCasoBean selectedCaso) {
		this.selectedCaso = selectedCaso;
	}

	public  LazyDataModel<DatiCasoBean> getLazyListaCasiModel() {
		return lazyListaCasiModel;
	}

	public void setLazyListaCasiModel( LazyDataModel<DatiCasoBean> lazyListaCasiModel) {
		this.lazyListaCasiModel = (LazyListaCasiModel)lazyListaCasiModel;
	}

	@PostConstruct
	public void onPostConstruct() throws NumberFormatException, NamingException {
		doLoadListaStati();
		doLoadListaOperatori();
		doLoadListaTitoliStudio();
		doLoadListaCondLavoro();
		String sIdCaso = getRequest().getParameter("IDCASO");
		if( CommonUtils.isNotEmptyString(sIdCaso) )
			iterDialogMan.openDialog(Long.parseLong(sIdCaso));
	}
	
	@Override
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	//loadListaCasi();
	        }
	    };
	}

	@Override
	public void rowDeselect() {
		lazyListaCasiModel = new LazyListaCasiModel();
	}

	public void setListaStati(List<SelectItem> listaStati) {
		this.listaStati = listaStati;
	}
	
		
	private void doLoadListaStati(){
		listaStati = new ArrayList<SelectItem>();
		try{
			AccessTableIterStepSessionBeanRemote service = 
					(AccessTableIterStepSessionBeanRemote)ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsCfgItStato> lst = service.getListaIterStati(cet);
			for(CsCfgItStato cfg : lst){
				SelectItem si = new SelectItem(cfg.getId(),cfg.getNome());
		        listaStati.add(si);
			}
		}catch(Exception e){
			logger.error("Errore caricamento lista stati ITER",e);
		}
	}
	
	private void doLoadListaTitoliStudio(){
		listaTitStudio = new ArrayList<SelectItem>();
		try{
			AccessTableConfigurazioneSessionBeanRemote confService = 
					(AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb("AccessTableConfigurazioneSessionBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsTbTitoloStudio> lst = confService.getTitoliStudio(cet);
			for (CsTbTitoloStudio obj : lst) 
				listaTitStudio.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				
		}catch(Exception e){
			logger.error("Errore caricamento lista TITOLI STUDIO",e);
		}
	}
	
	private void doLoadListaCondLavoro(){
		listaCondLavoro = new ArrayList<SelectItem>();
		try{
			AccessTableConfigurazioneSessionBeanRemote confService = 
					(AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			TreeMap<String, List<CsTbCondLavoro>> tree = confService.getMappaCondLavoro(cet);
			for(String str : tree.keySet()){
				List<CsTbCondLavoro> lst = tree.get(str);
				if (lst != null && !lst.isEmpty()) {
					String labelGroup = lst.get(0).getCsTbIngMercato().getDescrizione();
					SelectItemGroup gr = new SelectItemGroup(labelGroup);
					List<SelectItem> siList = new ArrayList<SelectItem>();
					for (CsTbCondLavoro obj : lst) {
						SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
						if(labelGroup==null || labelGroup.trim().isEmpty())
							siList.add(si);
						else
							listaCondLavoro.add(si);
					}
					if(labelGroup==null || labelGroup.trim().isEmpty()){
						gr.setSelectItems(siList.toArray(new SelectItem[siList.size()]));
						listaCondLavoro.add(gr);
					}
				}		
			}		
		}catch(Exception e){
			logger.error("Errore caricamento lista CONDIZIONE LAVORO ",e);
		}
	}
	
	private void doLoadListaOperatori(){
		listaOperatori = new ArrayList<SelectItem>();
		CsOOperatore operatore = getCurrentOpSettore().getCsOOperatore();
		Long settoreId = getCurrentOpSettore().getCsOSettore().getId();
		if(!CsUiCompBaseBean.checkPermesso(DataModelCostanti.PermessiCaso.ITEM,DataModelCostanti.PermessiCaso.VISUALIZZAZIONE_CASI_SETTORE)){
			listaOperatori.add(new SelectItem(operatore.getId(),this.getDenominazioneOperatore(operatore)));
		}else{
			try{
				if (settoreId != 0L) {
					AccessTableOperatoreSessionBeanRemote operatoreSessionBean = (AccessTableOperatoreSessionBeanRemote) 
							ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
			
					OperatoreDTO opDto = new OperatoreDTO();
					fillEnte(opDto);
					opDto.setIdSettore(settoreId);
					for (CsOOperatoreSettore it : operatoreSessionBean.findOperatoreSettoreBySettore(opDto) ) {
					    CsOOperatore op = it.getCsOOperatore();
					    SelectItem si = new SelectItem(op.getId(), this.getDenominazioneOperatore(op));
						listaOperatori.add(si);
					}
				}
			}catch(Exception e){
				logger.error("Errore caricamento lista operatori Settore",e);
			}
	  }
	}
	
	@Override
	public List<SelectItem> getListaStati() {
		// 2018-02-01 Smartpeg - già presente in PostConstruct; rimosso per Maintenance
//		if(listaStati.isEmpty())
//			doLoadListaStati();
		
		return listaStati;
	}
	
	@Override
	public List<SelectItem> getListaOperatori(){
		// 2018-02-01 Smartpeg - già presente in PostConstruct; rimosso per Maintenance
//		if(listaOperatori.isEmpty())
//			doLoadListaOperatori();
		
		return listaOperatori;	
	}
	
	@Override
	public List<SelectItem> getListaTitStudio(){
		// 2018-02-01 Smartpeg - già presente in PostConstruct; rimosso per Maintenance
//		if(listaTitStudio.isEmpty())
//			doLoadListaTitoliStudio();
		
		return listaTitStudio;	
	}
	
	@Override
	public List<SelectItem> getListaCondLavoro(){
		// 2018-02-01 Smartpeg - già presente in PostConstruct; rimosso per Maintenance
//		if(listaCondLavoro.isEmpty())
//			doLoadListaCondLavoro();
		
		return listaCondLavoro;	
	}
	
	public String getSelTipoOperatore(){
		return (String)getSession().getAttribute(FiltroCasi.TIPO_OPERATORE);
	}
	
	public void setSelTipoOperatore(String tipo){
		getSession().setAttribute(FiltroCasi.TIPO_OPERATORE, tipo);
	}

	public String getSelStato() {
		return (String)getSession().getAttribute(FiltroCasi.STATO);
	}

	public void setSelStato(String selStato) {
		getSession().setAttribute(FiltroCasi.STATO, selStato);
	}
	
	public Long getSelTitStudio() {
		return (Long)getSession().getAttribute(FiltroCasi.STUDIO);
	}

	public void setSelTitStudio(Long sel) {
		getSession().setAttribute(FiltroCasi.STUDIO, sel);
	}
	
	public Long getSelCondLavoro() {
		return (Long)getSession().getAttribute(FiltroCasi.LAVORO);
	}

	public void setSelCondLavoro(Long sel) {
		getSession().setAttribute(FiltroCasi.LAVORO, sel);
	}
	
	public String getSelTutela() {
		return (String)getSession().getAttribute(FiltroCasi.TUTELA);
	}

	public void setSelTutela(String sel) {
		getSession().setAttribute(FiltroCasi.TUTELA, sel);
	}

	public String getSelOperatore() {
		return (String)getSession().getAttribute(FiltroCasi.OPERATORE);
	}

	public void setSelOperatore(String selOperatore) {
		getSession().setAttribute(FiltroCasi.OPERATORE, selOperatore);
		if(selOperatore!=null && !selOperatore.isEmpty())
			this.renderTipoOperatore=true;
		else{
			this.renderTipoOperatore=false;
			this.setSelTipoOperatore("TUTTI");
		}
	}
	
	public void setSelDatiTribunale(String[] dati){
		getSession().setAttribute(FiltroCasi.TRIBUNALE, dati);
	}
	public String[] getSelDatiTribunale(){
		return (String[])getSession().getAttribute(FiltroCasi.TRIBUNALE);
	}
	
	@Override
	public void clearFilters(){
		this.setSelOperatore(null);
		this.setSelStato(null);
		this.setSelCondLavoro(null);
		this.setSelTitStudio(null);
		this.setSelTutela(null);
		this.setSelDatiTribunale(null);
	}

	@Override
	public boolean isRenderTipoOperatore() {
		return renderTipoOperatore;
	}
	
	/* #SISO-641
	 * 
	 * Non potendo sfruttare il dataExporter di PrimeFaces 4 (poiché non è possibile personalizzare la funzione
	 * di export delle singole colonne), si procede a generare un file Excel direttamente con Apache POI. */
	private static final int EXCEL_COLUMN_INDEX_ID = 0;
	private static final int EXCEL_COLUMN_INDEX_COGNOME_NOME = 1;
	private static final int EXCEL_COLUMN_DATA_NASCITA= 2;
	private static final int EXCEL_COLUMN_INDEX_CODICE_FISCALE = 3;
	private static final int EXCEL_COLUMN_INDEX_OPERATORI = 4;
	private static final int EXCEL_COLUMN_INDEX_CAT_SOCIALE = 5;
	private static final int EXCEL_COLUMN_INDEX_INT = 6;
	private static final int EXCEL_COLUMN_INDEX_EROG= 7;
	private static final int EXCEL_COLUMN_INDEX_DATA_APERTURA = 8;
	private static final int EXCEL_COLUMN_INDEX_STATO = 9;

	
	private CellStyle exportCellStyle;
	
	private void createAndPopulateCell(Row row, int columnIndex, String value) {
		Cell cell = row.createCell(columnIndex);
		cell.setCellStyle(exportCellStyle);
		
		cell.setCellValue(value);
	}

	public void excelExportListaCasi() throws IOException {
//		System.out.println("******** EXCEL EXPORT LISTA CASI ********");
		
		// creo il file Excel
		HSSFWorkbook workbook = new HSSFWorkbook();
				
		/* Imposto il CellStyle da utilizzare per questo documento.
		* 
		* wrapText = true permette l'inserimento di contenuti su più righe all'interno delle celle. */
		exportCellStyle = workbook.createCellStyle();
		exportCellStyle.setWrapText(true);
				
		// creo il (solo) foglio del documento
		HSSFSheet sheet = workbook.createSheet("ListaCasi");
				
		// creo la riga di intestazione e popolo i campi a mano
		HSSFRow headerRow = sheet.createRow(0);
		
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_ID, "ID");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_COGNOME_NOME, "Cognome - Nome");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_DATA_NASCITA, "Data nascita");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_CODICE_FISCALE, "Codice fiscale");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_OPERATORI, "Operatori");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_CAT_SOCIALE, "Cat. sociale");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_INT, "Int.");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_EROG, "Erog.");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DATA_APERTURA, "Data apertura");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_STATO, "Stato");
		
		
		
		//contatore righe tabella erogazioni interventi
		int casoRows = 0;
		//contatore righe excel
		int excelRow = 0;
		//totale righe da esportare
		int rowCount = lazyListaCasiModel.getRowCount();
		
		List<DatiCasoBean> listaCasi = lazyListaCasiModel.load(casoRows, lazyListaCasiModel.getPageSize(), lazyListaCasiModel.sortField, lazyListaCasiModel.sortOrder, lazyListaCasiModel.filters);
		
		while(casoRows < rowCount){
			
			
			for(DatiCasoBean rowListaCasi : listaCasi){
				excelRow++;
				
				// creo la i-esima riga, con l'altezza predefinita (in modo che si capisca se una cella ha più righe di contenuto)
				HSSFRow row = sheet.createRow(excelRow);
			    row.setHeightInPoints(30);
				
				// popolo le colonne (replicando di fatto la logica della view)
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_ID,
						rowListaCasi.getSoggetto().getCsACaso().getIdentificativo().toString());
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_COGNOME_NOME,
						rowListaCasi.getSoggetto().getCsAAnagrafica().getCognome()+ " "+rowListaCasi.getSoggetto().getCsAAnagrafica().getNome());
				
				createAndPopulateCell(row, EXCEL_COLUMN_DATA_NASCITA,
						dataNascitaCasoValueExtraction(rowListaCasi.getSoggetto().getCsAAnagrafica().getDataNascita()));
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_CODICE_FISCALE,
						rowListaCasi.getSoggetto().getCsAAnagrafica().getCf());
			
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_OPERATORI,
						DescrOperatoreValueExtraction(rowListaCasi));
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_CAT_SOCIALE,
						rowListaCasi.getCategoriaPrevalente());
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_INT,
						rowListaCasi.getnInterventi());
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_EROG,
						ErogPresenzaValueExtraction(rowListaCasi.getListaErogazioni()));
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DATA_APERTURA,
						dataAperturaCasoValueExtraction(rowListaCasi.getDataApertura()));
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_STATO,
						lastIterStepInfoValueExtraction(rowListaCasi));
				
			}
			casoRows += lazyListaCasiModel.getPageSize();
			listaCasi = lazyListaCasiModel.load(excelRow, lazyListaCasiModel.getPageSize(), lazyListaCasiModel.sortField, lazyListaCasiModel.sortOrder, lazyListaCasiModel.filters);
		}
		
		
		// imposto la larghezza delle colonne
		for (int i = 0; i <= 12; i++) {
			sheet.setColumnWidth(i, 20 * 256);	// la larghezza è in 1/256 di carattere
			}
		

		// scateno la response con il download dell'Excel creato
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		String fileName = "listaCasi.xls";
		String contentType = "application/vnd.ms-excel";
		
		ec.responseReset();
		ec.setResponseContentType(contentType);
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		OutputStream responseOutputStream = ec.getResponseOutputStream();
		workbook.write(responseOutputStream);	// scrivo il file direttamente sull'outputStream senza salvarlo
		
		fc.responseComplete();
				
				
		
	}
	
	private String dataAperturaCasoValueExtraction(Date dataAperturaCaso) {
		SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd/MM/yyyy");
		
		return dataAperturaCaso == null ? "" : ddmmyyyy.format(dataAperturaCaso);
	}

	private String dataNascitaCasoValueExtraction(Date dataNascitaCaso) {
		SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd/MM/yyyy");
		
		return dataNascitaCaso == null ? "" : ddmmyyyy.format(dataNascitaCaso);
	}
	
	private String ErogPresenzaValueExtraction(List<ErogazioneBaseDTO> listaErogazioni){
		return listaErogazioni.size()>0?"Sì":"No";
	}
	
	private String DescrOperatoreValueExtraction(DatiCasoBean caso){
		if(caso.getOperatore()!= null)
		return caso.getOperatore().getDenominazione().isEmpty()?"":caso.getOperatore().getDenominazione();
		else return "";
	}
	
	private String lastIterStepInfoValueExtraction(DatiCasoBean caso){
		String ret="";
		if(caso.getLastIterStepInfo()!=null){
			if(!(caso.getLastIterStepInfo().getNomeStato().isEmpty())&&!(caso.getLastIterStepInfo().getDataCreazione().isEmpty())){
				ret=caso.getLastIterStepInfo().getNomeStato()+"-"+caso.getLastIterStepInfo().getDataCreazione();
			}
		}
		return ret;
	}
}
