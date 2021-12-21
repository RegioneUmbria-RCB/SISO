package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableCfTempSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCfTemp;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;
import org.springframework.util.StringUtils;


@ManagedBean
@ViewScoped
public class TempCodFiscManagerDlg extends CsUiCompBaseBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected AccessTableCfTempSessionBeanRemote cfTempService = (AccessTableCfTempSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCfTempSessionBean");
	
	String nome;
	String cognome;
	String comuneNascita;
	Date dataNascita;
	String cittadinanza;
		
	List<CsCfTemp> elencoCodiciFiscaliTemporaneiNonAssegnati;	
	List<CsCfTemp> elencoCodiciFiscaliTemporaneiAssegnatiReali;
	
	CsCfTemp csCfTemp;	
	String codiceFiscaleTemporaneoScelto;
	
	boolean saveEnabled = false;
	
	public TempCodFiscManagerDlg(){}
	
	public void onload() throws Exception {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed())	    
	    {	
	    	genOrEditCf();
	    }
	}
	
	public void genOrEditCf() throws Exception
	{
		if(StringUtils.hasText(codiceFiscaleTemporaneoScelto))
		{
			cfGeneration(codiceFiscaleTemporaneoScelto);				
		}
		else
		{
			cfGeneration();			
		}
	}
	private HashMap<String,Object> genAttribmap() throws ValidatorException
	{
		if( !StringUtils.hasText(nome)
    			|| !StringUtils.hasText(cognome)
    			|| dataNascita==null
    			|| !StringUtils.hasText(comuneNascita) )
    	{			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dati non sufficienti per la generazione del CF temporaneo.", 
					"Tutti i seguenti parametri sono necessari: nome, cognome, data e comune di nascita ");
    		
			throw new ValidatorException(msg);
    	}
		
		HashMap<String,Object> attribs= new HashMap<String,Object> ();
				
		attribs.put("nome", nome);
		attribs.put("cognome", cognome);
		attribs.put("comuneNascita", comuneNascita);
		attribs.put("dataNascita", dataNascita);
		attribs.put("cittadinanza", cittadinanza);
		
		return attribs;
	}
	
	public void cfGeneration() throws Exception
	{
		cfGeneration(null);
	}
	
	private void cfGeneration(String cfScelto) throws Exception
	{		     
    	try
    	{
    		HashMap<String,Object> attribs = this.genAttribmap();
		
			BaseDTO dto = new BaseDTO();		
			fillEnte(dto);
			dto.setObj(attribs);		
			
			CsCfTemp newObject = cfTempService.generateNewCfTemp(dto);
			csCfTemp=newObject;
			
			if(StringUtils.hasText(cfScelto))
			{			
				csCfTemp.setId(null);			
				cfScelto=cfScelto.toUpperCase();
				csCfTemp.setCfTemp(cfScelto);	
				codiceFiscaleTemporaneoScelto = cfScelto;
			}
			else
			{
				codiceFiscaleTemporaneoScelto = newObject.getCfTemp();
			}
			
			//verifica esistenza duplicati (stesso cf ma diversi attributi)
			dto.setObj(codiceFiscaleTemporaneoScelto);
			List<CsCfTemp> cfTempList = cfTempService.findCfTempByCfTemp(dto);
			if(cfTempList!=null)
			{
				String attribstr = newObject.getAttributi();
				List<CsCfTemp> cfTempListOthers =  new ArrayList<CsCfTemp>();
				for(CsCfTemp existing: cfTempList)
				{
					if(existing.getAttributi().equalsIgnoreCase(attribstr))
					{
						this.csCfTemp = existing; 
					}
					else
					{
						cfTempListOthers.add(existing);
					}
				}
				if(cfTempListOthers.size()>0)
				{
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "CF temporaneo già esistente.", 
							"esiste già il codice temporaneo ma è assegnato ad altri attributi");		    		
		    		
		    		throw new ValidatorException(msg);
				}
			}
			
			//verifica altri cftemp con gli stessi attributi
			dto.setObj(newObject.getAttributi());
			cfTempList = cfTempService.findCfTempByAttribs(dto);
			elencoCodiciFiscaliTemporaneiNonAssegnati = new ArrayList<CsCfTemp>();
			elencoCodiciFiscaliTemporaneiAssegnatiReali = new ArrayList<CsCfTemp>();		
			if(cfTempList!=null)
			{
				for(CsCfTemp existing: cfTempList)
				{
					if(existing.getCf()==null)
					{
						//esistenza tra i cf temp non chiusi
						elencoCodiciFiscaliTemporaneiNonAssegnati.add(existing);
						//scegliere tra quelli esistenti
						
					}
					else
					{
						//esiste tra i cf temp convertiti in definitivi
						elencoCodiciFiscaliTemporaneiAssegnatiReali.add(existing);
						//scegliere tra quelli esistenti					
					}						
				}									
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
		
	public void saveCfTemp() throws Exception
	{
		if(csCfTemp!=null)
		{
			if(csCfTemp.getId()==null)//edited or generated and to be saved
			{			
				if(StringUtils.hasText(csCfTemp.getCfTemp()))
				{				
					//save to DB the generated cftemp
					BaseDTO dto = new BaseDTO();		
					fillEnte(dto);
					dto.setObj(csCfTemp);
					cfTempService.saveCfTemp(dto);
				}
			}
			RequestContext.getCurrentInstance().closeDialog(this.csCfTemp.getCfTemp());
		}
		else
		{
			RequestContext.getCurrentInstance().closeDialog("");
		}
	}
	public void selectExistingCfTemp(Long id) throws Exception
	{
		BaseDTO dto = new BaseDTO();		
		fillEnte(dto);
		dto.setObj(id.toString());
		this.csCfTemp = cfTempService.findCfTempById(dto);
		
		RequestContext.getCurrentInstance().closeDialog(this.csCfTemp.getCfTemp());
		
	}
	
	public void selectCf(String cf) throws Exception
	{		
		RequestContext.getCurrentInstance().closeDialog(cf);
	}

	
	public List<CsCfTemp> getElencoCodiciFiscaliTemporaneiNonAssegnati() {
		return elencoCodiciFiscaliTemporaneiNonAssegnati;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public CsCfTemp getCsCfTemp() {
		return csCfTemp;
	}

	public void setCsCfTemp(CsCfTemp csCfTemp) {
		this.csCfTemp = csCfTemp;
	}

	
	

	public Converter getConverter()
	{
		return new Converter(){

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String id){
				if(StringUtils.hasText(id))
				{
					BaseDTO dto = new BaseDTO();		
					fillEnte(dto);
					dto.setObj(id);
					
					try {
						return cfTempService.findCfTempById(dto);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) 
			{
				if(value!=null)
				{
					if(value instanceof CsCfTemp)
						return ""+((CsCfTemp)value).getId();
					else
						return value.toString();
				}
				return null;
			}
			
		};
	}

	public String getCodiceFiscaleTemporaneoScelto() {
		return codiceFiscaleTemporaneoScelto;
	}

	public void setCodiceFiscaleTemporaneoScelto(
			String codiceFiscaleTemporaneoScelto) {
		this.codiceFiscaleTemporaneoScelto = codiceFiscaleTemporaneoScelto;
	}

	
	public void setElencoCodiciFiscaliTemporaneiNonAssegnati(
			List<CsCfTemp> elencoCodiciFiscaliTemporanei) {
		this.elencoCodiciFiscaliTemporaneiNonAssegnati = elencoCodiciFiscaliTemporanei;
	}

	public List<CsCfTemp> getElencoCodiciFiscaliTemporaneiAssegnatiReali() {
		return elencoCodiciFiscaliTemporaneiAssegnatiReali;
	}

	public void setElencoCodiciFiscaliTemporaneiAssegnatiReali(
			List<CsCfTemp> elencoCodiciFiscaliTemporaneiAssegnatiReali) {
		this.elencoCodiciFiscaliTemporaneiAssegnatiReali = elencoCodiciFiscaliTemporaneiAssegnatiReali;
	}

	public boolean isSaveEnabled() {
		return saveEnabled;
	}

}
