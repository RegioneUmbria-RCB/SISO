package it.webred.cs.csa.web.manbean.export.fse;

import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.fse.ListaFseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.FiltroFse;
import it.webred.cs.data.DataModelCostanti.PermessiCartella;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

@ManagedBean
@ViewScoped
public class ListaFseBean extends CsUiCompBaseBean{

	private AccessTableDatiPorSessionBeanRemote porService = (AccessTableDatiPorSessionBeanRemote) getCarSocialeEjb("AccessTableDatiPorSessionBean");
	private String widgetVar = "listaFseVar";
	private LazyListaFseModel lazyListaFseModel;
	private ListaFseDTO selectedFse;
	private List<SelectItem> listaTipiFse;
	private List<SelectItem> listaProgetti;
	private List<SelectItem> listaComuniResidenza;
	private final String TR_MARCHE_STATO_ESTERO = "100008";
	private boolean canViewListaAmbito;
	private String headerText;
	
	public ListaFseDTO getSelectedFse() {
		return selectedFse;
	}

	public void setSelectedFse(ListaFseDTO selectedFse) {
		this.selectedFse = selectedFse;
	}

	public ListaFseBean() {
		lazyListaFseModel = new LazyListaFseModel();    	
	}

	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}


	@PostConstruct
	public void onPostConstruct() throws NumberFormatException, NamingException {
		doLoadListaComuniResidenza();
		doLoadListaTipiFse();
		doLoadListaProgetti();
		String labelComune = " - "+getLabelOrganizzazione()+" di "+this.getNomeEnte();
		canViewListaAmbito = CsUiCompBaseBean.checkPermesso(PermessiCartella.ITEM, PermessiCartella.VISUALIZZAZIONE_LISTA_FSE_ZS);
		headerText = "Prestazioni FSE";
		headerText += canViewListaAmbito ? " - Zona sociale "+this.getZonaSociale() : labelComune;
		
		this.onChangeFlagExtractFirst();		
	}
	
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	//loadListaCasi();
	        }
	    };
	}

	public void rowDeselect() {
		this.selectedFse=null;
	}

	
	private void doLoadListaTipiFse(){
		listaTipiFse = new ArrayList<SelectItem>();
		try{
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			listaTipiFse = convertiLista(porService.findListaTipiFse(cet));
		}catch(Exception e){
			logger.error("Errore caricamento lista tipi FSE",e);
		}
	}
	
	private void doLoadListaProgetti(){
		listaProgetti = new ArrayList<SelectItem>();
		try{
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			listaProgetti = convertiLista(porService.findListaProgettiUsati(cet));
		}catch(Exception e){
			logger.error("Errore caricamento lista Progetti",e);
		}
	}
	
	private void doLoadListaComuniResidenza(){
		listaComuniResidenza = new ArrayList<SelectItem>();
		try{
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			listaComuniResidenza = convertiLista(porService.getListaComuniResidenzaUsati(cet));
		}catch(Exception e){
			logger.error("Errore caricamento lista COMUNI RESIDENZA",e);
		}
	}
	
	public List<SelectItem> getListaComuniResidenza() {
		return listaComuniResidenza;
	}

	public void setListaComuniResidenza(List<SelectItem> listaComuniResidenza) {
		this.listaComuniResidenza = listaComuniResidenza;
	}

	public LazyListaFseModel getLazyListaFseModel() {
		return lazyListaFseModel;
	}

	public void setLazyListaFseModel(LazyListaFseModel lazyListaFseModel) {
		this.lazyListaFseModel = lazyListaFseModel;
	}

	public List<SelectItem> getListaTipiFse() {
		return listaTipiFse;
	}

	public void setListaTipiFse(List<SelectItem> listaTipiFse) {
		this.listaTipiFse = listaTipiFse;
	}

	public List<SelectItem> getListaProgetti() {
		return listaProgetti;
	}

	public void setListaProgetti(List<SelectItem> listaProgetti) {
		this.listaProgetti = listaProgetti;
	}

	public String getSelComuneResidenza() {
		return (String)getSession().getAttribute(FiltroFse.RESIDENZA_COMUNE);
	}

	public void setSelComuneResidenza(String selResidenza) {
		getSession().setAttribute(FiltroFse.RESIDENZA_COMUNE, selResidenza);
	}
	
	public Long[] getSelTipoFse() {
		return (Long[])getSession().getAttribute(FiltroFse.TIPO);
	}

	public void setSelTipoFse(Long[] sel) {
		getSession().setAttribute(FiltroFse.TIPO, sel);
	}
	
	public Date getDataSottoscrizioneDa() {
		return (Date)getSession().getAttribute(FiltroFse.DATA_DA);
	}

	public void setDataSottoscrizioneDa(Date sel) {
		getSession().setAttribute(FiltroFse.DATA_DA, sel);
	}
	
	public Date getDataSottoscrizioneA() {
		return (Date)getSession().getAttribute(FiltroFse.DATA_A);
	}

	public void setDataSottoscrizioneA(Date sel) {
		getSession().setAttribute(FiltroFse.DATA_A, sel);
	}
	
	public void setExtractFirst(boolean sel){
		getSession().setAttribute(FiltroFse.EXTRACT_FIRST, sel);
	}
	
	public boolean isExtractFirst(){
		Object val = getSession().getAttribute(FiltroFse.EXTRACT_FIRST);
		return val!=null ? (Boolean)val : Boolean.FALSE;
	}
	
	public void clearFilters(){
		this.clearParametriFiltroFse();
		this.onChangeFlagExtractFirst();		
	}
	
	public void onChangeFlagExtractFirst(){
		boolean val = this.isExtractFirst();
		if(val){
			Long[] sel = {1l,3l};
			this.setSelTipoFse(sel);
		}
	}

	
	/*
	 * 
	 * Non potendo sfruttare il dataExporter di PrimeFaces 4 (poiché non è possibile personalizzare la funzione
	 * di export delle singole colonne), si procede a generare un file Excel direttamente con Apache POI. */
	private static final int EXCEL_COLUMN_INDEX_CODICE_PROGETTO = 0;
	private static final int EXCEL_COLUMN_INDEX_CODICE_FISCALE = 1;
	private static final int EXCEL_COLUMN_INDEX_NOME = 2;
	private static final int EXCEL_COLUMN_INDEX_COGNOME = 3;
	private static final int EXCEL_COLUMN_INDEX_DATA_NASCITA = 4;
	private static final int EXCEL_COLUMN_INDEX_COMUNE_NASCITA_ISTAT = 5;
	private static final int EXCEL_COLUMN_INDEX_COMUNE_NASCITA_DESC = 6;
	private static final int EXCEL_COLUMN_INDEX_COD_STATO = 7; 

	private static final int EXCEL_COLUMN_INDEX_RESIDENZA_COD_ISTAT =  8;
	private static final int EXCEL_COLUMN_INDEX_RESIDENZA_INDIRIZZO =  9;
	private static final int EXCEL_COLUMN_INDEX_RESIDENZA_CAP =  10;
	
	private static final int EXCEL_COLUMN_INDEX_DOMICILIO_COD_ISTAT =  11;
	private static final int EXCEL_COLUMN_INDEX_DOMICILIO_INDIRIZZO =  12;
	private static final int EXCEL_COLUMN_INDEX_DOMICILIO_CAP =  13;
	
	private static final int EXCEL_COLUMN_INDEX_TELEFONO =  14;
	private static final int EXCEL_COLUMN_INDEX_CELLULARE =  15;
	private static final int EXCEL_COLUMN_INDEX_EMAIL =  16;
	
	private static final int EXCEL_COLUMN_INDEX_TIT_COD =  17;
	private static final int EXCEL_COLUMN_INDEX_TCOND_COD_TIPOCONDOCCUP =  18;
	private static final int EXCEL_COLUMN_INDEX_PERIODO_DISOCC =  19;
	
	//Condizioni vulnerabilità
	private static final int EXCEL_COLUMN_INDEX_01_TCONV =  20;
	private static final int EXCEL_COLUMN_INDEX_02_TCONV =  21;
	private static final int EXCEL_COLUMN_INDEX_03_TCONV =  22;
	private static final int EXCEL_COLUMN_INDEX_04_TCONV =  23;
	private static final int EXCEL_COLUMN_INDEX_05_TCONV =  24;
	private static final int EXCEL_COLUMN_INDEX_06_TCONV =  25;
	private static final int EXCEL_COLUMN_INDEX_07_TCONV =  26;
	private static final int EXCEL_COLUMN_INDEX_08_TCONV =  27;
	private static final int EXCEL_COLUMN_INDEX_09_TCONV =  28;
	private static final int EXCEL_COLUMN_INDEX_10_TCONV =  29;	
	
	private static final int EXCEL_COLUMN_INDEX_DATA_ISCRIZIONE = 30;
	private static final int EXCEL_COLUMN_DENOMINAZIONE_PROGETTO = 31;
	private static final int EXCEL_COLUMN_SOGGETTO_ATTUATORE = 32;
	
	private CellStyle exportCellStyle;
	private CellStyle dateCellStyle;


	private void createAndPopulateCell(Row row, int columnIndex, String value) {
		Cell cell = row.createCell(columnIndex);
		cell.setCellStyle(exportCellStyle);
		
		cell.setCellValue(value);
	}
	
	private void createAndPopulateIntegerCell(Row row, int columnIndex, String value) {
		Cell cell = row.createCell(columnIndex);
		cell.setCellStyle(exportCellStyle);
		
		Integer numero = numberValueExtraction(value);
		if(numero!=null) 
			cell.setCellValue(numero);
		else 
			cell.setCellValue(value);
	}
	
	private void createAndPopulateCell(Row row, int columnIndex, Date value) {
		Cell cell = row.createCell(columnIndex);
		cell.setCellStyle(dateCellStyle);
		
		cell.setCellValue(value);
	}

	public void excelExportListaPorFse() throws IOException {
		// creo il file Excel
		HSSFWorkbook workbook = new HSSFWorkbook();
				
		/* Imposto il CellStyle da utilizzare per questo documento.
		* 
		* wrapText = true permette l'inserimento di contenuti su più righe all'interno delle celle. */
		exportCellStyle = workbook.createCellStyle();
		exportCellStyle.setWrapText(true);
				
		dateCellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
		
		
		// creo il (solo) foglio del documento
		HSSFSheet sheet = workbook.createSheet("ListaCasi");
				
		// creo la riga di intestazione e popolo i campi a mano
		HSSFRow headerRow = sheet.createRow(0);
		
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_CODICE_PROGETTO, "CODICE_PROGETTO");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_CODICE_FISCALE, "CODICE_FISCALE");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_NOME , "NOME");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_COGNOME, "COGNOME");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DATA_NASCITA, "DATA_NASCITA");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_COMUNE_NASCITA_ISTAT,"COMUNE_NASCITA_COD_ISTAT");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_COMUNE_NASCITA_DESC, "COMUNE_NASCITA_DESC");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_COD_STATO, "STA_COD_STATO_PK");

		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_RESIDENZA_COD_ISTAT, "COMUNE_RESIDENZA_COD_ISTAT");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_RESIDENZA_INDIRIZZO, "RESIDENZA_INDIRIZZO");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_RESIDENZA_CAP, "RESIDENZA_CAP");
		
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DOMICILIO_COD_ISTAT, "COMUNE_DOMICILIO_COD_ISTAT");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DOMICILIO_INDIRIZZO, "DOMICILIO_INDIRIZZO");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DOMICILIO_CAP, "DOMICILIO_CAP");
		
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TELEFONO, "TELEFONO");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_CELLULARE, "CELLULARE");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_EMAIL, "EMAIL");
		
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TIT_COD, "TIT_COD_PK");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TCOND_COD_TIPOCONDOCCUP, "TCOND_COD_TIPOCONDOCCUP_PK");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_PERIODO_DISOCC, "PERIODO_DISOCC");
		
		//Condizioni vulnerabilità
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_01_TCONV, "01_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_02_TCONV, "02_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_03_TCONV, "03_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_04_TCONV, "04_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_05_TCONV, "05_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_06_TCONV, "06_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_07_TCONV, "07_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_08_TCONV, "08_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_09_TCONV, "09_TCONV");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_10_TCONV, "10_TCONV");	
		
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DATA_ISCRIZIONE, "DATA_ISCRIZIONE");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_DENOMINAZIONE_PROGETTO, "DENOMINAZIONE PROGETTO");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_SOGGETTO_ATTUATORE, "SOGGETTO ATTUATORE");
		
		//contatore righe tabella erogazioni interventi
		int casoRows = 0;
		//contatore righe excel
		int excelRow = 0;
		//totale righe da esportare
		int rowCount = lazyListaFseModel.getRowCount();
		
		List<ListaFseDTO> lista = lazyListaFseModel.load(casoRows, lazyListaFseModel.getPageSize(), lazyListaFseModel.sortField, lazyListaFseModel.sortOrder, lazyListaFseModel.filters);
		
		while(casoRows < rowCount){
			for(ListaFseDTO rowFse : lista){
				excelRow++;
				
				// creo la i-esima riga, con l'altezza predefinita (in modo che si capisca se una cella ha più righe di contenuto)
				HSSFRow row = sheet.createRow(excelRow);
			    row.setHeightInPoints(30);
				
				// popolo le colonne (replicando di fatto la logica della view)	
			    createAndPopulateIntegerCell(row, EXCEL_COLUMN_INDEX_CODICE_PROGETTO, rowFse.getProgettoCod());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_CODICE_FISCALE, rowFse.getCf());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_NOME , rowFse.getNome());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_COGNOME, rowFse.getCognome());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DATA_NASCITA, rowFse.getDataNascita());
				
				String codComuneNascita = rowFse.getNascitaComuneIstat();
				if(StringUtils.isBlank(codComuneNascita) && !StringUtils.isBlank(rowFse.getNascitaNazioneIstat()))
					codComuneNascita = TR_MARCHE_STATO_ESTERO;
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_COMUNE_NASCITA_ISTAT, codComuneNascita);
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_COMUNE_NASCITA_DESC, rowFse.getNascitaComuneDesc());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_COD_STATO, rowFse.getCittadinanzaCod());

				String codComuneRes = rowFse.getResidenzaComuneIstat();
				if(StringUtils.isBlank(codComuneRes) && !StringUtils.isBlank(rowFse.getResidenzaNazioneIstat()))
					codComuneRes = TR_MARCHE_STATO_ESTERO;
					
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_RESIDENZA_COD_ISTAT, codComuneRes);
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_RESIDENZA_INDIRIZZO, rowFse.getResidenzaIndirizzo());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_RESIDENZA_CAP, rowFse.getResidenzaCap());
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DOMICILIO_COD_ISTAT, rowFse.getDomicilioComuneIstat());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DOMICILIO_INDIRIZZO, rowFse.getDomicilioIndirizzo());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DOMICILIO_CAP, rowFse.getDomicilioCap());
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TELEFONO, rowFse.getTelefono());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_CELLULARE, rowFse.getCellulare());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_EMAIL, rowFse.getEmail());
				
				createAndPopulateIntegerCell(row, EXCEL_COLUMN_INDEX_TIT_COD, rowFse.getTitoloStudioCod());
				createAndPopulateIntegerCell(row, EXCEL_COLUMN_INDEX_TCOND_COD_TIPOCONDOCCUP, rowFse.getCondOccupazioneCod());
				createAndPopulateIntegerCell(row, EXCEL_COLUMN_INDEX_PERIODO_DISOCC, rowFse.getPeriodoDisoccupazione());
				
				//Condizioni vulnerabilità
				createAndPopulateIntegerCell(row, EXCEL_COLUMN_INDEX_01_TCONV, rowFse.getGrVulnerabile());
				
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DATA_ISCRIZIONE, rowFse.getDataSottoscrizione());
				String nomeProgetto = rowFse.getProgettoDenominazione()!=null ? rowFse.getProgettoDenominazione().replaceFirst(DataModelCostanti.patternFSE, "") : null;
				createAndPopulateCell(row, EXCEL_COLUMN_DENOMINAZIONE_PROGETTO, nomeProgetto);
				createAndPopulateCell(row, EXCEL_COLUMN_SOGGETTO_ATTUATORE, rowFse.getSoggettoAttuatore());
				
			}
			casoRows += lazyListaFseModel.getPageSize();
			lista = lazyListaFseModel.load(excelRow, lazyListaFseModel.getPageSize(), lazyListaFseModel.sortField, lazyListaFseModel.sortOrder, lazyListaFseModel.filters);
		}
		
		
		// imposto la larghezza delle colonne
		for (int i = 0; i <= 12; i++) {
			sheet.setColumnWidth(i, 20 * 256);	// la larghezza è in 1/256 di carattere
			}
		

		// scateno la response con il download dell'Excel creato
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		String fileName = "listaPorFse.xls";
		String contentType = "application/vnd.ms-excel";
		
		ec.responseReset();
		ec.setResponseContentType(contentType);
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		OutputStream os = null;
		os = ec.getResponseOutputStream();
		workbook.write(os);	// scrivo il file direttamente sull'outputStream senza salvarlo
		
		fc.responseComplete();
	}
	
	private String dataValueExtraction(Date data) {
		SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd/MM/yyyy");
		
		return data == null ? "" : ddmmyyyy.format(data);
	}
	
	private Integer numberValueExtraction(String s) {
		Integer i = null;
		try {
			i = Integer.valueOf(s);
		}catch(NumberFormatException nfe){
			logger.warn("Impossibile convertire in intero il seguente valore:"+s);
		}
		
		return i;
	}

	public boolean isCanViewListaAmbito() {
		return canViewListaAmbito;
	}

	public void setCanViewListaAmbito(boolean canViewListaAmbito) {
		this.canViewListaAmbito = canViewListaAmbito;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	
}
