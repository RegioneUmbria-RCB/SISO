package it.webred.ss.web.bean.report;

import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.tb.CsOSettoreLIGHT;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.StreamedContent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

public class ReportBaseBean extends SegretariatoSocBaseBean{
	
	protected static final String DIRIMAGES = "/images/";
	protected static final String PDF_PATH = "/reports/temp.pdf";
	protected JasperPrint jasperprint;
	
	protected void initLoghiReport(HashMap<String, Object> parameters){
	    	String dirAppImages  =  FacesContext.getCurrentInstance().getExternalContext().getRealPath(DIRIMAGES)+File.separatorChar;
			String dirLoghi = this.getPathLoghi();
			
			File logo = new File(dirLoghi + "logo_bw.png");
		if(logo.exists())
			parameters.put("pathLogoComune", dirLoghi + "logo_bw.png");
		else {
			logger.error("Attenzione: Il logo 'logo_bw.png' non è presente per il comune "+this.getPreselectedBelfioreOrg());
			parameters.put("pathLogoComune", dirAppImages + "logo_bw.png");
		}
		
		//Valorizzo il titolo
		parameters.put("pathTitolo", dirAppImages + this.getLogoTitolo());
		
		parameters.put("pathMale",   dirAppImages + "male.png");
		parameters.put("pathFemale", dirAppImages + "female.png");
		
	}
	
	public StreamedContent getFile(){return null;};
	
	/*Metodi per l'export PDF della Lista*/
	 public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		 Document pdf = (Document) document;
         pdf.setPageSize(PageSize.A4.rotate());
         pdf.open();
         pdf.setMargins(10, 10, 10, 10);
         HashMap<String, Object> parameters = new HashMap<String, Object>();

			this.initLoghiReport(parameters);
		//	HeaderFooter hf = new HeaderFooter(new Phrase(this.getLabelSegrSociale()),true);
			
		//	pdf.setHeader(hf);
			Image imgLogoComune = Image.getInstance((String)parameters.get("pathLogoComune"));
			imgLogoComune.scalePercent(50);
			imgLogoComune.setAlignment(Image.LEFT);
			Image imgTitolo = Image.getInstance((String)parameters.get("pathTitolo"));
			imgTitolo.scalePercent(70);
			imgTitolo.setAlignment(Image.MIDDLE);
			
			pdf.add(imgLogoComune);	
			pdf.add(new Phrase("\n"));
			pdf.add(imgTitolo);
			pdf.add(new Phrase("\n"));	
 }
	 public void postProcessXLS(Object document) throws IOException, BadElementException, DocumentException {
		  HSSFWorkbook wb = (HSSFWorkbook) document;
		  
		  DataFormat format = wb.createDataFormat();
	      CellStyle dataStyle = wb.createCellStyle();
	      dataStyle.setDataFormat(format.getFormat("dd/mm/yyyy"));
	      
	      CellStyle hhmmStyle = wb.createCellStyle();
	      hhmmStyle.setDataFormat(format.getFormat("dd/mm/yyyy hh:mm"));
	      
	      CellStyle integerStyle = wb.createCellStyle();
	      //String codeInt = BuiltinFormats.getBuiltinFormat(1);
	      integerStyle.setDataFormat(format.getFormat("0"));
		  
		  int numberOfSheets = wb.getNumberOfSheets();
		    for (int i = 0; i < numberOfSheets; i++) {
		    	HSSFSheet sheet = wb.getSheetAt(i);
		    	// auto size delle colonne troppo lento
		    	sheet.setColumnWidth(0, 5 * 256); // ID
		    	sheet.setColumnWidth(1, 15 * 256); //Data Accesso
		    	sheet.getRow(0).getCell(1).setCellValue("Data Accesso");
		    	sheet.setColumnWidth(2, 20 * 256);
		    	sheet.setColumnWidth(3, 15 * 256);//Data Nascita 
		    	sheet.setColumnWidth(4, 20 * 256);
		    	sheet.setColumnWidth(5, 27 * 256);
		    	sheet.setColumnWidth(6, 23 * 256);
		    	sheet.setColumnWidth(7, 35 * 256);
		    	sheet.setColumnWidth(8, 10 * 256);
		    	sheet.setColumnWidth(9, 20 * 256);
		    	sheet.setColumnWidth(10, 20 * 256);
		    	
		    	Iterator<Row> rowIterator = sheet.iterator();
		    	i=0;
		        while (rowIterator.hasNext()) {
		          Row row = rowIterator.next();
		          Iterator<Cell> cellIterator = row.cellIterator();
		          int j = 0;
		          if(i>0){
			          while(cellIterator.hasNext()){
			        	  Cell currentCell = cellIterator.next();
			        	  String value = currentCell.getStringCellValue();
				        	  if (j == 0){ //ID
									currentCell.setCellValue(Integer.parseInt(value));
									currentCell.setCellStyle(integerStyle);
						      }
				        	  if (j==1 || j == 3){ //DATA ACCESSO e DATA NASCITA
						    	try {
									currentCell.setCellValue(ddMMyyyy.parse(value));
									currentCell.setCellStyle(dataStyle);
								} catch (ParseException e) {}
				        	 }
				        	 if (j == 10){ //ULTIMA MODIFICA
						    	try {
									currentCell.setCellValue(ddMMyyyyhhmm.parse(value));
									currentCell.setCellStyle(hhmmStyle);
								} catch (ParseException e) {}	
					          }
				      
				          j++;
			          }
			      }
		          i++;
		        }
		    }
		  
	  }  
	 public void postProcessXLSInviate(Object document) throws IOException, BadElementException, DocumentException {
		  HSSFWorkbook wb = (HSSFWorkbook) document;
		  
		  DataFormat format = wb.createDataFormat();
	      CellStyle dataStyle = wb.createCellStyle();
	      dataStyle.setDataFormat(format.getFormat("dd/mm/yyyy"));
	      
	      CellStyle hhmmStyle = wb.createCellStyle();
	      hhmmStyle.setDataFormat(format.getFormat("dd/mm/yyyy hh:mm"));
	      
	      CellStyle integerStyle = wb.createCellStyle();
	      //String codeInt = BuiltinFormats.getBuiltinFormat(1);
	      integerStyle.setDataFormat(format.getFormat("0"));
		  
		  int numberOfSheets = wb.getNumberOfSheets();
		    for (int i = 0; i < numberOfSheets; i++) {
		    	HSSFSheet sheet = wb.getSheetAt(i);
		    	// auto size delle colonne troppo lento
		    	sheet.setColumnWidth(0, 5 * 256); // ID
		    	sheet.setColumnWidth(1, 15 * 256); //
		    	sheet.setColumnWidth(2, 15 * 256);
		    	sheet.setColumnWidth(3, 20 * 256);
		    	sheet.setColumnWidth(4, 15 * 256);
		    	sheet.setColumnWidth(5, 15 * 256);
		    	sheet.setColumnWidth(6, 23 * 256);
		    	sheet.setColumnWidth(7, 30 * 256);
		    	sheet.setColumnWidth(8, 15 * 256);
		    	sheet.setColumnWidth(9, 20 * 256);
		    	sheet.setColumnWidth(10, 35 * 256);
		    	
		    	Iterator<Row> rowIterator = sheet.iterator();
		    	i=0;
		        while (rowIterator.hasNext()) {
		          Row row = rowIterator.next();
		          Iterator<Cell> cellIterator = row.cellIterator();
		          int j = 0;
		          if(i>0){
			          while(cellIterator.hasNext()){
			        	  Cell currentCell = cellIterator.next();
			        	  String value = currentCell.getStringCellValue();
				        	  if (j == 0){ //ID
									currentCell.setCellValue(Integer.parseInt(value));
									currentCell.setCellStyle(integerStyle);
						      }
				        	  if (j==4 || j == 8){ //DATA ACCESSO e DATA NASCITA
						    	try {
									currentCell.setCellValue(ddMMyyyy.parse(value));
									currentCell.setCellStyle(dataStyle);
								} catch (ParseException e) {}
				        	 }
				        	 if (j == 1 || j==2){ //DATA INVIO e DATA RICEZIONE
						    	try {
									currentCell.setCellValue(ddMMyyyyhhmm.parse(value));
									currentCell.setCellStyle(hhmmStyle);
								} catch (ParseException e) {}	
					          }
				      
				          j++;
			          }
			      }
		          i++;
		        }
		    	
		    }
		  
	  }  
  protected String getInterlocutoreDesc(SsSchedaAccesso accesso){
		String accompagnatore = accesso.getAccompagnatore()!=null ? accesso.getAccompagnatore() : "non specificato";
		String interlocutore = accesso.getInterlocutore();
		interlocutore += (accesso.getUtenteAccompagnato()!=null && accesso.getUtenteAccompagnato())? " (accompagnato da: "+accompagnatore+" )" : "";
		interlocutore += (accesso.getUtentePresenteInformato()!=null && accesso.getUtentePresenteInformato()) ? " (utente presente o informato)" :"";
		
		return format(interlocutore);
  }	 
  
  protected String getMotivoDesc(SsSchedaAccesso accesso){
		CsOSettoreLIGHT settore = accesso.getSettoreInviante();
		String inviante = settore!=null ? format(settore.getNome()) : "";
		
		String motivo = accesso.getMotivo();
		motivo += settore!=null ? " "+inviante : "";
		motivo += accesso.getMotivoDesc()!=null ? ": "+accesso.getMotivoDesc() : "";
		return format(motivo);
  }
  
}
