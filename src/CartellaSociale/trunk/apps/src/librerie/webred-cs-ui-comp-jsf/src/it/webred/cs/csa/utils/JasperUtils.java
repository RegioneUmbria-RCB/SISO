package it.webred.cs.csa.utils;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JasperUtils extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sessionTmpPath = null;

	public void esportaReport(String filename, String jrxmlPath, List<String> jrxmlSubPath, Map<String, Object> reportParams, JRDataSource datasource) throws Exception {
					
		if(datasource == null)
			datasource = new JREmptyDataSource();
		
		if(jrxmlSubPath != null && !jrxmlSubPath.isEmpty()) {		
			//setto una directory personale per l'utente dove saranno salvati i .jasper
			String sessionId = getSession().getId();
			String first = jrxmlSubPath.get(0);
			int idx = first.lastIndexOf("/");
			sessionTmpPath = first.substring(0, idx) + "/" + sessionId;
			File directory = new File(sessionTmpPath);
			directory.mkdirs();
			reportParams.put("SUBREPORT_DIR", sessionTmpPath + "/");
			reportParams.put("SUBREPORT_JASPER", sessionTmpPath + first.substring(idx).replace("jrxml", "jasper"));
			
			creaSubReport(jrxmlSubPath, null);
		}
		
		JasperDesign jasperDesign = JRXmlLoader.load(jrxmlPath);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportParams, datasource);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

		byte[] content = baos.toByteArray();
		doDownload(filename, content);
		
		//pulisco i file temporanei del report
		if(sessionTmpPath != null) {
			File directory = new File(sessionTmpPath);
			if(directory.exists())
				delete(directory);
		}
	}
	
	private void creaSubReport(List<String> jrxmlSubPath, Map<String, Object> reportParams) throws JRException, IOException {

		for(String subReportPath: jrxmlSubPath)
			creaJasper(subReportPath, reportParams);

	}
	
	private void creaJasper(String jrxmlPath, Map<String, Object> jasperParams) throws JRException, IOException {
		
		JasperDesign jasperDesign = JRXmlLoader.load(jrxmlPath);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperFillManager.fillReport(jasperReport, jasperParams);

		// write report
		String nomeSubreport = jrxmlPath.substring(jrxmlPath.lastIndexOf("/"));
		String jasperPath = sessionTmpPath + "/" + nomeSubreport.substring(0, nomeSubreport.lastIndexOf('.')) + ".jasper";
		byte[] fileData = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(jasperReport);
		fileData = bos.toByteArray();
		os.close();
		bos.close();
		FileOutputStream fos = new FileOutputStream(jasperPath);
		fos.write(fileData);
		fos.close();
		
	}
	
	private void doDownload(String fileName, byte[] content) throws Exception {
			
		try {
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) faces
					.getExternalContext().getResponse();
			
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".pdf");
			response.setHeader("Pragma", "");
			response.setHeader("Cache-Control", "no-store,max-age=0,must-revalidate");
			
			ServletOutputStream out = response.getOutputStream();
			out.write(content);
			out.flush();

			response.setContentLength(content.length);
			out.close();
			faces.responseComplete();			
		}
		catch (Exception ex) {
			throw ex;
		}

	}
	
    public static void delete(File file) throws IOException{
     
    	if(file.isDirectory()){
 
    		//directory is empty, then delete it
    		if(file.list().length==0){    
    		   file.delete();
    		}else{
 
    		   //list all the directory contents
        	   String files[] = file.list();
 
        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp);
 
        	      //recursive delete
        	     delete(fileDelete);
        	   }
 
        	   //check the directory again, if empty then delete it
        	   if(file.list().length==0){
           	     file.delete();
        	   }
    		}
 
    	}else{
    		//if file, then delete it
    		file.delete();
    	}
    }
		
}
