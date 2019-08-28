package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableCfTempSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCfTemp;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;


@ManagedBean
@ViewScoped
public class TempCodFiscManagerDlgReplace extends CsUiCompBaseBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected AccessTableCfTempSessionBeanRemote cfTempService = (AccessTableCfTempSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCfTempSessionBean");
	
	String codiceFiscaleTemporaneo;
	String codiceFiscaleDefinitivo;
	
	List<CsCfTemp> cfTempListToReplace ;
	
	List<CsACaso> casiList;
	List<SsScheda> ssSchedeList;
	
	boolean saveEnabled = false;
	
	public TempCodFiscManagerDlgReplace(){}
	
	public void onload() throws Exception {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed())	    
	    {	
	    	editCf();
	    }
	}
	
	
	
	
	public void editCf() throws Exception
	{	     		
    	try
    	{
    		//HashMap<String,Object> attribs = this.genAttribmap();
		
    		BaseDTO dto = new BaseDTO();		
			fillEnte(dto);
			dto.setObj(codiceFiscaleTemporaneo);
			cfTempListToReplace = cfTempService.findCfTempByCfTempNotReplaced(dto);
			if(cfTempListToReplace!=null && cfTempListToReplace.size()>0)	
			{						
				elencoCsCasiCoinvolti(codiceFiscaleTemporaneo);
				
				elencoSsSchedeCoinvolte(codiceFiscaleTemporaneo);
							
			}			
			saveEnabled=true;
    	}
    	catch(ValidatorException ve)
    	{
    		FacesContext facesContext = FacesContext.getCurrentInstance();
    		facesContext.addMessage(null, ve.getFacesMessage());    		
    		saveEnabled=false;
    	}
	}
		
	private void elencoSsSchedeCoinvolte(String cft) throws NamingException {
		this.ssSchedeList = new ArrayList<SsScheda>();
		if(cft!=null) {
			
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
			it.webred.ss.ejb.dto.BaseDTO codiceFiscaleWrapper = new it.webred.ss.ejb.dto.BaseDTO();
			fillEnte(codiceFiscaleWrapper);
			codiceFiscaleWrapper.setObj(cft);
						
			List<SsScheda> schede = schedaService.readSchedeByCF(codiceFiscaleWrapper);
			for(SsScheda s: schede)
			{				
				ssSchedeList.add(s);
			}
			
		}			
			
	}

	private void elencoCsCasiCoinvolti(String cft) {
		this.casiList = new ArrayList<CsACaso>();
		
		if(cft!=null) {						
			
			BaseDTO dto = new BaseDTO();		
			fillEnte(dto);						
			dto.setObj(cft);
			
			AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
			List<CsASoggettoLAZY> soggetti=soggettoService.getSoggettiByCF(dto);
			if(soggetti!=null)
			{
				for(CsASoggettoLAZY s: soggetti)
				{
					CsACaso c = s.getCsACaso();
					casiList.add(c);
				}
			}
		}
		
	}

	//salvataggio finale
	public void replaceCf() throws Exception
	{
		if(cfTempListToReplace!=null)
		{
			
			
			
//			for (CsCfTemp csCfTemp: cfTempListToReplace)
//			{
//				csCfTemp.setCf(codiceFiscaleDefinitivo);
//				
//				//save to DB the generated cftemp
//				BaseDTO dto = new BaseDTO();		
//				fillEnte(dto);
//				dto.setObj(csCfTemp);
//				cfTempService.saveCfTemp(dto);
//			}							
//		
			
		}
		else
		{
		}
			
		//Verificare la presenza di cartelle con stesso codice fiscale
		if(isCodFiscaleInCs(codiceFiscaleDefinitivo) && !this.casiList.isEmpty()) {
			CsUiCompBaseBean.addErrorFromProperties("cf.temp.replace.cartellaEsistente");
		}else
			RequestContext.getCurrentInstance().closeDialog(codiceFiscaleDefinitivo);
	}
//	public void selectExistingCfTemp(Long id) throws Exception
//	{
//		BaseDTO dto = new BaseDTO();		
//		fillEnte(dto);
//		dto.setObj(id.toString());
//		this.csCfTemp = cfTempService.findCfTempById(dto);
//		
//		RequestContext.getCurrentInstance().closeDialog(this.csCfTemp.getCfTemp());
//		
//	}
//	
//	public void selectCf(String cf) throws Exception
//	{		
//		RequestContext.getCurrentInstance().closeDialog(cf);
//	}

	

	public String getCodiceFiscaleTemporaneo() {
		return codiceFiscaleTemporaneo;
	}

	public void setCodiceFiscaleTemporaneo(
			String _codiceFiscaleTemporaneo) {
		this.codiceFiscaleTemporaneo = _codiceFiscaleTemporaneo;
	}

	public boolean isSaveEnabled() {
		return saveEnabled;
	}

	public String getCodiceFiscaleDefinitivo() {
		return codiceFiscaleDefinitivo;
	}

	public void setCodiceFiscaleDefinitivo(String codiceFiscaleDefinitivo) {
		this.codiceFiscaleDefinitivo = codiceFiscaleDefinitivo;
	}

	public List<CsACaso> getCasiList() {
		return casiList;
	}

	public void setCasiList(List<CsACaso> casiList) {
		this.casiList = casiList;
	}

	public List<SsScheda> getSsSchedeList() {
		return ssSchedeList;
	}

	public void setSsSchedeList(List<SsScheda> ssSchedeList) {
		this.ssSchedeList = ssSchedeList;
	}

}
