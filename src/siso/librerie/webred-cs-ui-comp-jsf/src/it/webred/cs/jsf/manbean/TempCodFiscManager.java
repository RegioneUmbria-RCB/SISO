package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableCfTempSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCfTemp;
import it.webred.cs.jsf.interfaces.IDatiAna;
import it.webred.cs.jsf.manbean.common.CommonDatiAnaBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.jsf.bean.ComuneBean;
import it.webred.utils.DateFormat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.util.StringUtils;



@ManagedBean
@ViewScoped
public class TempCodFiscManager extends CsUiCompBaseBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected AccessTableCfTempSessionBeanRemote cfTempService = (AccessTableCfTempSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCfTempSessionBean");
	
	IDatiAna anagraficaBean;
	CommonDatiAnaBean datiAnagrafici;
	ComuneBean comuneNascita;
	
	HashMap<String,Boolean> cache = new HashMap<String, Boolean>();
	
	public TempCodFiscManager(){}
	
	
	/**
	 * @return the datiAnagrafici
	 */
	public CommonDatiAnaBean getDatiAnagrafici() {
		return datiAnagrafici;
	}

	/**
	 * @param datiAnagrafici the datiAnagrafici to set
	 */
	public void setDatiAnagrafici(CommonDatiAnaBean datiAnagrafici) {
		this.datiAnagrafici = datiAnagrafici;
	}
	
	public void openDlgReplaceCfAction() throws Exception
	{	
		Map<String,Object> options = new HashMap<String, Object>(); 
		options.put("modal", true);//modal
		
		Map<String,List<String>> params = new HashMap<String,List<String>>(); 
		
		params.put("previousCfTemp", Arrays.asList(datiAnagrafici.getCodiceFiscale()));
		
		RequestContext.getCurrentInstance().openDialog("dlgCodFiscReplace", options, params);
		
	}

	public void openDlgGenerateCfAction() throws Exception
	{	
		Map<String,Object> options = new HashMap<String, Object>(); 
		options.put("modal", true);//modal
		
		Map<String,List<String>> params = new HashMap<String,List<String>>(); 
		params.put("nome", Arrays.asList(datiAnagrafici.getNome()));
		params.put("cognome", Arrays.asList(datiAnagrafici.getCognome()));
		params.put("cittadinanza", Arrays.asList(datiAnagrafici.getCittadinanza()));
		
		if(comuneNascita!=null && StringUtils.hasText(comuneNascita.getCodIstatComune()) )
		{
			params.put("comuneNascita", Arrays.asList(comuneNascita.getCodIstatComune()));
		}
		else
		{			
			params.put("comuneNascita", Arrays.asList("ESTERO"));
		}
		if(datiAnagrafici.getDataNascita()!=null)
		{
			String dataNascitaStr = DateFormat.dateToString(datiAnagrafici.getDataNascita(), "dd/MM/yyyy");			
			params.put("dataNascita", Arrays.asList(dataNascitaStr));
		}
		
		params.put("previousCfTemp", Arrays.asList(datiAnagrafici.getCodiceFiscale()));
		
		RequestContext.getCurrentInstance().openDialog("dlgCodFisc", options, params);
		
	}
	
	public void onCfGenerated(SelectEvent event) 
	{
		String codiceFiscaleTemporaneoGenerato = (String) event.getObject();
		if(StringUtils.hasText(codiceFiscaleTemporaneoGenerato))
		{
			datiAnagrafici.setCodiceFiscale(codiceFiscaleTemporaneoGenerato);
		}
	}
	public void onCfReplaced(SelectEvent event) throws Exception 
	{
		String codiceFiscale = (String) event.getObject();
		if(StringUtils.hasText(codiceFiscale))
		{
			codiceFiscale = codiceFiscale.toUpperCase();
			
			String codiceFiscaleTemporaneo = datiAnagrafici.getCodiceFiscale();
			if(StringUtils.hasText(codiceFiscaleTemporaneo))
			{
				BaseDTO dto = new BaseDTO();		
				fillEnte(dto);
				dto.setObj(codiceFiscaleTemporaneo);
				List<CsCfTemp> cfTempListToReplace = cfTempService.findCfTempByCfTempNotReplaced(dto);
				
				if(cfTempListToReplace!=null)
				{
					for (CsCfTemp csCfTemp: cfTempListToReplace)
					{
						csCfTemp.setCf(codiceFiscale);
						
						//save to DB the generated cf						
						dto.setObj(csCfTemp);
						cfTempService.saveCfTemp(dto);
					}			
				}								
			}
			
			//salva cartella tab anagrafica
			datiAnagrafici.setCodiceFiscale(codiceFiscale);			
			
			Object saveObject =  event.getComponent().getAttributes().get("saveObject");
			String saveMethod = (String) event.getComponent().getAttributes().get("saveMethodName");
			IDatiAna anagraficaBean = (IDatiAna) event.getComponent().getAttributes().get("anagraficaBean");
			if(saveObject!=null && saveMethod!=null)
			{
				//TODO
				saveMethod.toString();
			
			}else if(anagraficaBean!=null){
				anagraficaBean.salva();						
			}
		}
	}
	
	public boolean isTemporaneo(String codiceFiscaleTemporaneo) throws Exception
	{
		Boolean ret = cache.get(codiceFiscaleTemporaneo);
		if(ret==null)
		{
			List<CsCfTemp> cftempList= this.getCfTempList(codiceFiscaleTemporaneo);
			if(cftempList!=null && cftempList.size()>0)
			{
				cache.put(codiceFiscaleTemporaneo, Boolean.TRUE);
				return true;
			}
			
			cache.put(codiceFiscaleTemporaneo, Boolean.FALSE);
			return false;			
		}
		else
		{
			return ret;
		}
				
	}
	

	public List<CsCfTemp> getCfTempList(String codiceFiscaleTemporaneo) throws Exception
	{
		BaseDTO dto = new BaseDTO();		
		fillEnte(dto);
		dto.setObj(codiceFiscaleTemporaneo);
		
		List<CsCfTemp> cftempList= cfTempService.findCfTempByCfTemp(dto);
		return cftempList;
	}	

	public ComuneBean getComuneNascita() {
		return comuneNascita;
	}


	public void setComuneNascita(ComuneBean comuneNascita) {
		this.comuneNascita = comuneNascita;
	}


	public IDatiAna getAnagraficaBean() {
		return anagraficaBean;
	}


	public void setAnagraficaBean(IDatiAna anagraficaBean) {
		this.anagraficaBean = anagraficaBean;
	}
	
	
}
