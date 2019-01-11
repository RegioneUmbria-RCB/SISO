package it.webred.ss.web.bean.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;


@ManagedBean
@ViewScoped
public class ReportManager {
	
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");
	
	private JasperPrint jasperprint;
	
	public void onPrintClick(){
    	
		initPDFScheda();  
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	    httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");  
	    ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = httpServletResponse.getOutputStream();  
			JasperExportManager.exportReportToPdfStream(jasperprint, servletOutputStream);     	    
		} catch (IOException e) {
			logger.error(e);
		} catch (JRException e) {
			logger.error(e);
		}
		FacesContext.getCurrentInstance().responseComplete(); 
    	
    }
	
	private void initPDFScheda(){
    	
    	String  reportPath =  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/report1.jrxml");
    	try {
			JasperReport report = JasperCompileManager.compileReport(reportPath);
    	
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("date", new Date());
			parameters.put("operator", "Pippo");
			parameters.put("mode", "modalita' ok");
    	
			jasperprint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
    	
    	} catch (JRException e) {
    		logger.error(e);
		}
    }

}
