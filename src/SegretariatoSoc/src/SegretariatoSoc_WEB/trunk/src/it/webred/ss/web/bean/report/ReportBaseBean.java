package it.webred.ss.web.bean.report;

import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

import net.sf.jasperreports.engine.JasperPrint;

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
			logger.error("Attenzione: Il logo 'logo_bw.png' non è presente per il comune "+this.getPreselectedOrganizzazione());
			parameters.put("pathLogoComune", dirAppImages + "logo_bw.png");
		}
		
		//Valorizzo il titolo
		parameters.put("pathTitolo", dirAppImages + this.getLogoTitolo());
		
	}
	
	public StreamedContent getFile(){return null;};
	
	/*Metodi per l'export PDF della Lista*/
	 public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
         Document pdf = (Document) document;
         pdf.open();
         pdf.setPageSize(PageSize.A4);
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
	   

}
