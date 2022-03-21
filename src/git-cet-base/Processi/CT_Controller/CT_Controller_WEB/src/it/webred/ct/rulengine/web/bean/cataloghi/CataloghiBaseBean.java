package it.webred.ct.rulengine.web.bean.cataloghi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.data.access.basic.common.CommonService;
import it.webred.ct.data.access.basic.pgt.PGTService;
import it.webred.ct.rulengine.service.bean.UtilService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;
import it.webred.ct.rulengine.web.bean.abcomandi.AbComandiBean;

public class CataloghiBaseBean extends ControllerBaseBean{
	
	protected UtilService utilService = (UtilService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "UtilServiceBean");
	
	protected PGTService pgtService = (PGTService) getEjb(
			"CT_Service", "CT_Service_Data_Access", "PGTServiceBean");
	
	protected ParameterService paramService = (ParameterService) getEjb(
			"CT_Service", "CT_Config_Manager", "ParameterBaseService");
	
	private static Logger logger = Logger.getLogger(CataloghiBaseBean.class
			.getName());

	protected String getRootPathDatiShape(){
		String path = null;
	
		try{
		  
          ParameterSearchCriteria criteria = new ParameterSearchCriteria();
  		    	criteria.setKey("dir.shape.files");
  			criteria.setComune(null);
  			criteria.setSection(null);
  		
  			return doGetListaKeyValue(criteria) ;
		}catch(Exception e){
			logger.error("Directory per upload file shpe non trovata");
		}
		
		return path;
	}
	
	private String doGetListaKeyValue(ParameterSearchCriteria criteria)
	{
		try
		{
			if (paramService==null)
			{
				logger.warn("ParameterService == NULL");
				return "";
			}
			
			List<AmKeyValueDTO> l = paramService.getListaKeyValue(criteria);
			if (l!=null && l.size()>0)
				return ((AmKeyValueDTO)l.get(0)).getAmKeyValueExt().getValueConf();
			else
			{
				logger.warn(" LISTA RITORNO DA parameterService.getListaKeyValue VUOTA");
				return "";
			}
		}
		catch (Exception ex)
		{
			logger.error(" doGetListaKeyValue ERRORE = " + ex.getMessage());
			return "";
		}

	}
	protected String getTimeStamp(){
		
		Date date = this.getCurrentDate();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.format(date);
	}
	protected Date getCurrentDate(){

		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
}
