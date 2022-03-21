package it.webred.ct.data.access.basic.indice;

import it.webred.ct.config.model.AmFonteTipoinfo;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.indice.dao.IndiceBaseDAO;
import it.webred.ct.data.model.indice.SitEnteSorgente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;


import org.springframework.beans.factory.annotation.Autowired;

public class IndiceBaseService extends CTServiceBaseBean implements Serializable{
		
	private IndiceBaseDAO indiceBaseDAO;

	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ParameterBaseService")
	protected ParameterService parameterService;

	
	protected final static HashMap<String, String> statoMap = new HashMap<String, String>()
	{
		{
		put("N", "Nuovo");
		put("A", "Aggiornato");
		put("C", "Convalidato");
		}
	};
	
	protected Date getCurrentDate(){

		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public List<SitEnteSorgente> getListaEntiSorgenti(IndiceDataIn indDataIn){
				
		List<SitEnteSorgente> lista = null;
		
		try{
			lista = indiceBaseDAO.getListaEntiSorgenti();
		}catch (Throwable t) {			
			throw new IndiceServiceException(t);
		}
			
		return lista;
	
	}
	
	public SitEnteSorgente getEnteSorgente(IndiceDataIn indDataIn){
				
		try{
			
			return indiceBaseDAO.getEnteSorgente(((Long) indDataIn.getObj()).longValue());
		}catch (Throwable t) {			
			throw new IndiceServiceException(t);
		}
	
	}
	
	protected AmFonteTipoinfo getFonteTipoinfo(Integer idEnte, BigDecimal prog) {
			
		AmFonteTipoinfo fonte = new AmFonteTipoinfo();

		try{
			
			fonte = parameterService.getFonteTipoInfo(idEnte, prog);
		
		}catch (Throwable t) {			
			logger.error("", t);
		}
		
		return fonte;
			
	}
	
	public IndiceBaseDAO getIndiceBaseDAO() {
		return indiceBaseDAO;
	}

	public void setIndiceBaseDAO(IndiceBaseDAO indiceBaseDAO) {
		this.indiceBaseDAO = indiceBaseDAO;
	}	
	
	
}
