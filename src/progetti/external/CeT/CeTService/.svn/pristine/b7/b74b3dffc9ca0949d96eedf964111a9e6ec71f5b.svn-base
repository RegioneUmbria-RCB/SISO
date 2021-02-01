package it.webred.ct.data.access.basic.ruolo;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.ruolo.dao.RuoloDAO;
import it.webred.ct.data.model.ruolo.SitRuoloTarPdf;
import it.webred.ejb.utility.ClientUtility;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class RuoloServiceBean extends CTServiceBaseBean implements RuoloService {

	@Autowired
	private RuoloDAO ruoloDAO;
	private final String DIR = "PdfRuoloTar";

	@Override
	public List<String> getPercorsiPdfByCfCu(RuoloDataIn dataIn) throws RuoloServiceException{
		List<String> lstPath = new ArrayList<String>();
		try{
			if(dataIn.getCf()!=null && dataIn.getCu()!=null){
				List<SitRuoloTarPdf> lstpdf = ruoloDAO.getListaPdfByCfCu(dataIn.getCf(), new BigDecimal(dataIn.getCu()));	
				lstPath = this.elaboraPathPdf(dataIn.getEnteId(), lstpdf, null);
			}else
				logger.error("Impossibile recuperare la listas di file PDF del ruolo [CF:"+dataIn.getCf()+";CU:"+dataIn.getCu()+"]");
		}catch(NumberFormatException nfe){
			logger.error("Impossibile recuperare la listas di file PDF del ruolo [CF:"+dataIn.getCf()+";CU:"+dataIn.getCu()+"]",nfe);
		}
		
		return lstPath;
		
	}
	
	@Override
	public List<String> getPercorsiPdfByCf(RuoloDataIn dataIn) throws RuoloServiceException{
	
		List<SitRuoloTarPdf> lstpdf = ruoloDAO.getListaPdfByCf(dataIn.getCf());	
		return this.elaboraPathPdf(dataIn.getEnteId(), lstpdf, dataIn.getAnnoNotIn());
	}
	
	private List<String> elaboraPathPdf(String enteId, List<SitRuoloTarPdf> lstpdf, String annoNotIn) throws RuoloServiceException {
		String[] anniNotIn = annoNotIn == null || annoNotIn.trim().equals("") ? null : annoNotIn.trim().split(",");
		List<String> lstPath = new ArrayList<String>();
		String pathBase = this.getDatiDiogeneDir()+
						(this.getDatiDiogeneDir().replace("/", "\\").replace("\\", File.separator).endsWith(File.separator) ? "" : File.separatorChar)+
						enteId+File.separatorChar+DIR;
		for(SitRuoloTarPdf pdf : lstpdf){
			boolean add = true;
			if (anniNotIn != null) {
				for (String anno : anniNotIn) {
					if (pdf.getAnno() != null && anno.equals(pdf.getAnno().toString())) {
						add = false;
						break;
					}
				}
			}
			if (add) {
				String path = pathBase+ File.separatorChar+ pdf.getNomeFile();
				lstPath.add(path);
			}			
		}
		return lstPath;
	}
	
	private String getDatiDiogeneDir() throws RuoloServiceException {
		
		String dir = "";
		
		Context cont;
		try {
			cont = new InitialContext();
			
			ParameterService paramService = (ParameterService) ClientUtility.getEjbInterface("CT_Service","CT_Config_Manager" , "ParameterBaseService");
			
		    ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("dir.files.datiDiogene");
			criteria.setComune(null);
			criteria.setSection(null);
			
			AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
			dir =  amKey.getValueConf();

		} catch (NamingException e) {
			logger.error("Impossibile recuperare il nome del percorso dir.files.datiDiogene"+e.getMessage(),e);
			throw new RuoloServiceException("Impossibile recuperare il nome del percorso dir.files.datiDiogene"+e.getMessage(), e);
		}
		return dir;
	}
	

}
